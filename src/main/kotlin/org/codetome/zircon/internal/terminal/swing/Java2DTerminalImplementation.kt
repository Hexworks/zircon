package org.codetome.zircon.internal.terminal.swing

import org.codetome.zircon.api.Modifiers
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.terminal.config.CursorStyle.*
import org.codetome.zircon.api.terminal.config.DeviceConfiguration
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.codetome.zircon.internal.font.impl.FontSettings
import org.codetome.zircon.internal.terminal.InternalTerminal
import java.awt.*
import java.awt.image.BufferedImage
import java.util.*

/**
 * This is the class implements the [InternalTerminal] for the java 2d world. It maintains
 * most of the external terminal state and also the main back buffer that is copied to the component
 * area on draw operations.
 */
@Suppress("unused")
abstract class Java2DTerminalImplementation(
        private val deviceConfiguration: DeviceConfiguration,
        private val terminal: InternalTerminal)
    : InternalTerminal by terminal {

    private var enableInput = false
    private var hasBlinkingText = deviceConfiguration.isCursorBlinking
    private var blinkOn = true
    private var lastBufferUpdateScrollPosition: Int = 0
    private var lastComponentWidth: Int = 0
    private var lastComponentHeight: Int = 0

    private var blinkTimer = Timer("BlinkTimer", true)

    /**
     * Used when requiring the total height of the terminal component, in pixels.
     */
    abstract fun getHeight(): Int

    /**
     * Used when requiring the total width of the terminal component, in pixels.
     */
    abstract fun getWidth(): Int

    /**
     * Called by the terminal implementation when it would like the OS to schedule a draw of the
     * window.
     */
    abstract fun draw()

    @Synchronized
    fun onCreated() {
        blinkTimer.schedule(object : TimerTask() {
            override fun run() {
                blinkOn = !blinkOn
                if (hasBlinkingText) {
                    draw()
                }
            }
        }, deviceConfiguration.blinkLengthInMilliSeconds, deviceConfiguration.blinkLengthInMilliSeconds)
        enableInput = true
        EventBus.subscribe(EventType.Draw, {
            draw()
        })
    }

    @Synchronized
    fun onDestroyed() {
        EventBus.emit(EventType.Input, KeyStroke.EOF_STROKE)
        blinkTimer.cancel()
        enableInput = false
    }

    /**
     * Calculates the preferred size of this terminal.
     */
    @Synchronized
    fun getPreferredSize() = Dimension(
            getSupportedFontSize().columns * terminal.getBoundableSize().columns,
            getSupportedFontSize().rows * terminal.getBoundableSize().rows)

    /**
     * Updates the back buffer (if necessary) and draws it to the component's surface.
     */
    @Synchronized
    fun draw(graphics: Graphics2D) {
        var needToRedraw = hasBlinkingText
        val font = getCurrentFont() // we get the font at the start because it might be changed by external force

        // Detect resize
        if (resizeHappened()) {
            fillLeftoverSpaceWithBlack(graphics)
            val terminalSize = Size.of(
                    columns = getWidth() / getSupportedFontSize().columns,
                    rows = getHeight() / getSupportedFontSize().rows)
            terminal.setSize(terminalSize)
            needToRedraw = true
        }
        if (isDirty()) {
            needToRedraw = true
        }
        if (needToRedraw) {
            val cursorPosition = terminal.getCursorPosition()
            var foundBlinkingCharacters = deviceConfiguration.isCursorBlinking

            graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED)
            graphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE)
            graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY)
            graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF)
            graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED)

            terminal.forEachDirtyCell { (position, textCharacter) ->
                val atCursorLocation = cursorPosition == position
                val characterWidth = getSupportedFontSize().columns
                val drawCursor = shouldDrawCursor(atCursorLocation)
                if (textCharacter.getModifiers().contains(Modifiers.BLINK)) {
                    foundBlinkingCharacters = true
                }
                drawCharacter(
                        graphics = graphics,
                        character = textCharacter,
                        columnIndex = position.column,
                        rowIndex = position.row,
                        characterWidth = characterWidth,
                        font = font,
                        drawCursor = drawCursor)
            }
            this.hasBlinkingText = foundBlinkingCharacters || deviceConfiguration.isCursorBlinking
        }

        fillLeftoverSpaceWithBlack(graphics)

        this.lastComponentWidth = getWidth()
        this.lastComponentHeight = getHeight()
        graphics.dispose()
    }

    private fun drawCharacter(
            graphics: Graphics,
            character: TextCharacter,
            columnIndex: Int,
            rowIndex: Int,
            characterWidth: Int,
            font: Font<BufferedImage>,
            drawCursor: Boolean) {

        val x = columnIndex * getSupportedFontSize().columns
        val y = rowIndex * getSupportedFontSize().rows

        listOf(Pair(font, character))
                .plus(fetchOverlayZIntersection(Position.of(columnIndex, rowIndex)))
                .forEach { (fontOverride, tc) ->
            // TODO: test font
            val fontToUse = if(fontOverride === FontSettings.NO_FONT) {
                font
            } else {
                fontOverride
            }
            if (tc.isNotEmpty()) {
                if (tc.isBlinking() && blinkOn) {
                    tc.withForegroundColor(tc.getBackgroundColor())
                            .withBackgroundColor(tc.getForegroundColor())
                } else {
                    tc
                }.let { fixedChar ->
                    graphics.drawImage(fontToUse.fetchRegionForChar(fixedChar), x, y, null)
                }
            }
        }

        if (drawCursor) {
            graphics.color = deviceConfiguration.cursorColor.toAWTColor()
            when (deviceConfiguration.cursorStyle) {
                USE_CHARACTER_FOREGROUND -> {
                    graphics.color = character.getForegroundColor().toAWTColor()
                    graphics.fillRect(x, y, getSupportedFontSize().columns, getSupportedFontSize().rows)
                }
                FIXED_BACKGROUND -> graphics.fillRect(x, y, getSupportedFontSize().columns, getSupportedFontSize().rows)
                UNDER_BAR -> graphics.fillRect(x, y + getSupportedFontSize().rows - 3, characterWidth, 2)
                VERTICAL_BAR -> graphics.fillRect(x, y + 1, 2, getSupportedFontSize().rows - 2)
            }
        }
    }

    private fun shouldDrawCursor(atCursorLocation: Boolean) = atCursorLocation
            && isCursorVisible()                                 // User settings override everything
            && (!deviceConfiguration.isCursorBlinking || // Always draw if the cursor isn't blinking
            deviceConfiguration.isCursorBlinking && blinkOn)     // If the cursor is blinking, only draw when blinkOn is true

    private fun fillLeftoverSpaceWithBlack(graphics: Graphics) {
        // Take care of the left-over area at the bottom and right of the component where no character can fit
        graphics.color = Color.BLACK

        val leftoverWidth = getWidth() % getSupportedFontSize().columns
        if (leftoverWidth > 0) {
            graphics.fillRect(getWidth() - leftoverWidth, 0, leftoverWidth, getHeight())
        }

        val leftoverHeight = getHeight() % getSupportedFontSize().rows
        if (leftoverHeight > 0) {
            graphics.fillRect(0, getHeight() - leftoverHeight, getWidth(), leftoverHeight)
        }
    }

    @Synchronized
    override fun flush() {
        draw()
    }

    private fun resizeHappened() = getWidth() != lastComponentWidth || getHeight() != lastComponentHeight

}