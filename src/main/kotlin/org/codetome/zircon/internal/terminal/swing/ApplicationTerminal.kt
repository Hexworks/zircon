package org.codetome.zircon.internal.terminal.swing

import org.codetome.zircon.api.Modifiers
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.terminal.config.CursorStyle.*
import org.codetome.zircon.api.terminal.config.DeviceConfiguration
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.codetome.zircon.internal.font.impl.FontSettings
import org.codetome.zircon.internal.terminal.ApplicationListener
import org.codetome.zircon.internal.terminal.InternalTerminal
import java.awt.*
import java.util.*

/**
 * This is the class implements the [InternalTerminal] for the java 2d world. It maintains
 * most of the external terminal state and also the main back buffer that is copied to the component
 * area on doRender operations.
 */
@Suppress("unused")
abstract class ApplicationTerminal(
        private val deviceConfiguration: DeviceConfiguration,
        private val terminal: InternalTerminal)
    : InternalTerminal by terminal, ApplicationListener {

    private var enableInput = false
    private var hasBlinkingText = deviceConfiguration.isCursorBlinking
    private var blinkOn = true
    private var lastBufferUpdateScrollPosition: Int = 0
    private var blinkTimer = Timer("BlinkTimer", true)
    private var resizeHappened = false

    /**
     * Used when requiring the total height of the terminal component, in pixels.
     */
    abstract fun getHeight(): Int

    /**
     * Used when requiring the total width of the terminal component, in pixels.
     */
    abstract fun getWidth(): Int

    abstract fun drawFontTextureRegion(fontTextureRegion: FontTextureRegion, x: Int, y: Int)

    abstract fun drawCursor(character: TextCharacter, x: Int, y: Int)

    @Synchronized
    override fun doCreate() {
        blinkTimer.schedule(object : TimerTask() {
            override fun run() {
                blinkOn = !blinkOn
                if (hasBlinkingText) {
                    doRender()
                }
            }
        }, deviceConfiguration.blinkLengthInMilliSeconds, deviceConfiguration.blinkLengthInMilliSeconds)
        enableInput = true
    }

    @Synchronized
    override fun doDispose() {
        EventBus.emit(EventType.Input, KeyStroke.EOF_STROKE)
        blinkTimer.cancel()
        enableInput = false
    }

    override fun doResize(width: Int, height: Int) {
        val terminalSize = Size.of(
                columns = width / getSupportedFontSize().columns,
                rows = height / getSupportedFontSize().rows)
        terminal.setSize(terminalSize)
        resizeHappened = true
    }

    @Synchronized
    override fun doRender() {
        var needToRedraw = hasBlinkingText.or(resizeHappened)
        val font = getCurrentFont() // we get the font at the start because it might be changed by external force

        if (isDirty()) {
            needToRedraw = true
        }
        if (needToRedraw) {
            val cursorPosition = terminal.getCursorPosition()
            var foundBlinkingCharacters = deviceConfiguration.isCursorBlinking

            terminal.forEachDirtyCell { (position, textCharacter) ->
                val atCursorLocation = cursorPosition == position
                val drawCursor = shouldDrawCursor(atCursorLocation)
                if (textCharacter.getModifiers().contains(Modifiers.BLINK)) {
                    foundBlinkingCharacters = true
                }
                drawCharacter(
                        character = textCharacter,
                        columnIndex = position.column,
                        rowIndex = position.row,
                        font = font,
                        drawCursor = drawCursor)
            }
            this.hasBlinkingText = foundBlinkingCharacters || deviceConfiguration.isCursorBlinking
        }
    }

    private fun drawCharacter(
            character: TextCharacter,
            columnIndex: Int,
            rowIndex: Int,
            font: Font,
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
                    drawFontTextureRegion(fontToUse.fetchRegionForChar(fixedChar), x, y)
                }
            }
        }

        if (drawCursor) {
            drawCursor(character, x, y)
        }
    }

    private fun shouldDrawCursor(atCursorLocation: Boolean) = atCursorLocation
            && isCursorVisible()                                 // User settings override everything
            && (!deviceConfiguration.isCursorBlinking || // Always doRender if the cursor isn't blinking
            deviceConfiguration.isCursorBlinking && blinkOn)     // If the cursor is blinking, only doRender when blinkOn is true
}