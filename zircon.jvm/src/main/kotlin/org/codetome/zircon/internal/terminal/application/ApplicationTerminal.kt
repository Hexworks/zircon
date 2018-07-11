package org.codetome.zircon.internal.terminal.application

import org.codetome.zircon.api.*
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.interop.Modifiers
import org.codetome.zircon.api.terminal.DeviceConfiguration
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.Event
import org.codetome.zircon.internal.font.impl.FontSettings
import org.codetome.zircon.internal.terminal.ApplicationListener
import org.codetome.zircon.internal.terminal.InternalTerminal
import java.util.*

/**
 * This is the class implements the [InternalTerminal] for the java 2d world. It maintains
 * most of the external terminal state and also the main back buffer that is copied to the component
 * area on render operations.
 */
@Suppress("unused")
abstract class ApplicationTerminal(
        private val deviceConfiguration: DeviceConfiguration,
        private val terminal: InternalTerminal,
        private val checkDirty: Boolean = true)
    : InternalTerminal by terminal, ApplicationListener {

    private var hasBlinkingText = deviceConfiguration.isCursorBlinking
    private var blinkOn = true
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

    abstract fun drawFontTextureRegion(fontTextureRegion: FontTextureRegion<*>, x: Int, y: Int)

    abstract fun drawCursor(character: TextCharacter, x: Int, y: Int)

    @Synchronized
    override fun doCreate() {
        onShutdown { doDispose() }
        blinkTimer.schedule(object : TimerTask() {
            override fun run() {
                try {
                    blinkOn = !blinkOn
                    if (hasBlinkingText) {
                        doRender()
                        flush()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }, deviceConfiguration.blinkLengthInMilliSeconds, deviceConfiguration.blinkLengthInMilliSeconds)
    }

    @Synchronized
    override fun doRender() {
        var needToRedraw = hasBlinkingText.or(resizeHappened)
        resizeHappened = false
        val font = getCurrentFont() // we get the font at the start because it might be changed by external force

        if (isDirty()) {
            needToRedraw = true
        }
        if (needToRedraw.or(checkDirty.not())) {
            val cursorPosition = terminal.getCursorPosition()
            var foundBlinkingCharacters = deviceConfiguration.isCursorBlinking

            val func = { cell: Cell ->
                val (position, textCharacter) = cell
                val atCursorLocation = cursorPosition == position
                val drawCursor = shouldDrawCursor(atCursorLocation)
                if (textCharacter.getModifiers().contains(Modifiers.BLINK)) {
                    foundBlinkingCharacters = true
                }
                drawCharacter(
                        character = textCharacter,
                        xIdx = position.x,
                        yIdx = position.y,
                        font = font,
                        drawCursor = drawCursor)
            }

            if (checkDirty) terminal.forEachDirtyCell(func) else terminal.forEachCell(func)
            this.hasBlinkingText = foundBlinkingCharacters || deviceConfiguration.isCursorBlinking
        }
    }

    @Synchronized
    override fun doDispose() {
        EventBus.broadcast(Event.Input(KeyStroke.EOF_STROKE))
        blinkTimer.cancel()
    }

    override fun doResize(width: Int, height: Int) {
        val terminalSize = Size.create(
                xLength = width / getSupportedFontSize().xLength,
                yLength = height / getSupportedFontSize().yLength)
        terminal.setSize(terminalSize)
        resizeHappened = true
    }

    private fun drawCharacter(
            character: TextCharacter,
            xIdx: Int,
            yIdx: Int,
            font: Font,
            drawCursor: Boolean) {

        val x = xIdx * getSupportedFontSize().xLength
        val y = yIdx * getSupportedFontSize().yLength

        listOf(Pair(font, character))
                .plus(fetchOverlayZIntersection(Position.create(xIdx, yIdx)))
                .forEach { (fontOverride, tc) ->
                    // TODO: test font
                    val fontToUse = if (fontOverride === FontSettings.NO_FONT) {
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
            && (!deviceConfiguration.isCursorBlinking ||         // Always render if the cursor isn't blinking
            deviceConfiguration.isCursorBlinking && blinkOn)     // If the cursor is blinking, only render when blinkOn is true
}
