package org.codetome.zircon.terminal.swing

import org.codetome.zircon.api.*
import org.codetome.zircon.api.factory.TextColorFactory
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.terminal.config.CursorStyle.*
import org.codetome.zircon.api.terminal.config.DeviceConfiguration
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.codetome.zircon.internal.terminal.InternalTerminal
import java.awt.*
import java.awt.image.BufferedImage
import java.util.*

/**
 * This is the class that does the heavy lifting for [SwingTerminalCanvas]. It maintains
 * most of the external terminal state and also the main back buffer that is copied to the component
 * area on draw operations.
 */
@Suppress("unused")
abstract class Java2DTerminalImplementation(
        private val deviceConfiguration: DeviceConfiguration,
        private val font: Font<BufferedImage>,
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
     * Used to find out the oldfont height, in pixels.
     */
    abstract fun getFontHeight(): Int

    /**
     * Used to find out the oldfont width, in pixels.
     */
    abstract fun getFontWidth(): Int

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
    fun getPreferredSize() = Dimension(
            getFontWidth() * terminal.getBoundableSize().columns,
            getFontHeight() * terminal.getBoundableSize().rows)

    /**
     * Updates the back buffer (if necessary) and draws it to the component's surface.
     */
    @Synchronized
    fun draw(graphics: Graphics2D) {
        var needToRedraw = hasBlinkingText

        // Detect resize
        if (resizeHappened()) {
            fillLeftoverSpaceWithBlack(graphics)
            val terminalSize = Size.of(
                    columns = getWidth() / getFontWidth(),
                    rows = getHeight() / getFontHeight())
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
                val characterWidth = getFontWidth()
                val foregroundColor = deriveTrueForegroundColor(textCharacter)
                val backgroundColor = deriveTrueBackgroundColor(textCharacter, atCursorLocation)
                val drawCursor = atCursorLocation && (!deviceConfiguration.isCursorBlinking || //Always draw if the cursor isn't blinking
                        deviceConfiguration.isCursorBlinking && blinkOn)    //If the cursor is blinking, only draw when blinkOn is true
                if (textCharacter.getModifiers().contains(Modifiers.BLINK)) {
                    foundBlinkingCharacters = true
                }

                drawCharacter(graphics,
                        textCharacter,
                        position.column,
                        position.row,
                        foregroundColor,
                        backgroundColor,
                        characterWidth,
                        drawCursor)
            }

            this.hasBlinkingText = foundBlinkingCharacters || deviceConfiguration.isCursorBlinking
        }

        fillLeftoverSpaceWithBlack(graphics)

        this.lastComponentWidth = getWidth()
        this.lastComponentHeight = getHeight()
        graphics.dispose()
    }

    private fun fillLeftoverSpaceWithBlack(graphics: Graphics) {
        // Take care of the left-over area at the bottom and right of the component where no character can fit
        graphics.color = Color.BLACK

        val leftoverWidth = getWidth() % getFontWidth()
        if (leftoverWidth > 0) {
            graphics.fillRect(getWidth() - leftoverWidth, 0, leftoverWidth, getHeight())
        }

        val leftoverHeight = getHeight() % getFontHeight()
        if (leftoverHeight > 0) {
            graphics.fillRect(0, getHeight() - leftoverHeight, getWidth(), leftoverHeight)
        }
    }

    private fun drawCharacter(
            graphics: Graphics,
            character: TextCharacter,
            columnIndex: Int,
            rowIndex: Int,
            foregroundColor: Color,
            backgroundColor: Color,
            characterWidth: Int,
            drawCursor: Boolean) {

        val x = columnIndex * getFontWidth()
        val y = rowIndex * getFontHeight()

        val fixedChar = character
                .withBackgroundColor(TextColorFactory.fromAWTColor(backgroundColor))
                .withForegroundColor(TextColorFactory.fromAWTColor(foregroundColor))
        graphics.drawImage(font.fetchRegionForChar(fixedChar), x, y, null)
        fetchOverlayZIntersection(Position.of(columnIndex, rowIndex)).forEach {
            if (it.isNotEmpty()) {
                graphics.drawImage(font.fetchRegionForChar(it), x, y, null)
            }
        }

        if (drawCursor) {
            graphics.color = deviceConfiguration.cursorColor.toAWTColor()
            if (deviceConfiguration.cursorStyle === UNDER_BAR) {
                graphics.fillRect(x, y + getFontHeight() - 3, characterWidth, 2)
            } else if (deviceConfiguration.cursorStyle === VERTICAL_BAR) {
                graphics.fillRect(x, y + 1, 2, getFontHeight() - 2)
            }
        }
    }


    private fun deriveTrueForegroundColor(character: TextCharacter): Color {
        val foregroundColor = character.getForegroundColor()
        val backgroundColor = character.getBackgroundColor()
        val blink = character.isBlinking()

        if (blink && blinkOn) {
            return backgroundColor.toAWTColor()
        } else {
            return foregroundColor.toAWTColor()
        }
    }

    private fun deriveTrueBackgroundColor(character: TextCharacter, atCursorLocation: Boolean): Color {
        val foregroundColor = character.getForegroundColor()
        var backgroundColor = character.getBackgroundColor()
        var reverse = false
        if (isCursorVisible() && atCursorLocation) {
            if (deviceConfiguration.cursorStyle === REVERSED && (!deviceConfiguration.isCursorBlinking || !blinkOn)) {
                reverse = true
            } else if (deviceConfiguration.cursorStyle === FIXED_BACKGROUND) {
                backgroundColor = deviceConfiguration.cursorColor
            }
        }
        if (reverse) {
            return foregroundColor.toAWTColor()
        } else {
            return backgroundColor.toAWTColor()
        }
    }

    @Synchronized
    override fun flush() {
        draw()
    }

    private fun resizeHappened() = getWidth() != lastComponentWidth || getHeight() != lastComponentHeight

}