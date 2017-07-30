package org.codetome.zircon.terminal.swing

import org.codetome.zircon.*
import org.codetome.zircon.font.MonospaceFontRenderer
import org.codetome.zircon.input.InputType
import org.codetome.zircon.input.KeyStroke
import org.codetome.zircon.input.MouseAction
import org.codetome.zircon.input.MouseActionType
import org.codetome.zircon.input.MouseActionType.*
import org.codetome.zircon.terminal.TerminalSize
import org.codetome.zircon.terminal.config.*
import org.codetome.zircon.terminal.config.CursorStyle.*
import org.codetome.zircon.terminal.virtual.VirtualTerminal
import java.awt.*
import java.awt.datatransfer.DataFlavor
import java.awt.event.*
import java.awt.image.BufferedImage
import java.util.*

/**
 * This is the class that does the heavy lifting for [SwingTerminalComponent]. It maintains
 * most of the external terminal state and also the main back buffer that is copied to the component
 * area on draw operations.
 */
@Suppress("unused")
abstract class GraphicalTerminalImplementation(
        private val deviceConfiguration: DeviceConfiguration,
        private val colorConfiguration: ColorConfiguration,
        private val monospaceFontRenderer: MonospaceFontRenderer<Graphics>,
        private val virtualTerminal: VirtualTerminal)
    : VirtualTerminal by virtualTerminal {

    private var enableInput = false
    private var hasBlinkingText = deviceConfiguration.isCursorBlinking
    private var blinkOn = true
    private var lastBufferUpdateScrollPosition: Int = 0
    private var lastComponentWidth: Int = 0
    private var lastComponentHeight: Int = 0

    private var blinkTimer = Timer("BlinkTimer", true)
    private var buffer: Optional<BufferedImage> = Optional.empty()

    /**
     * Used to find out the font height, in pixels.
     */
    abstract fun getFontHeight(): Int

    /**
     * Used to find out the font width, in pixels.
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
     * Returns `true` if anti-aliasing is enabled, `false` otherwise.
     */
    abstract fun isTextAntiAliased(): Boolean

    /**
     * Called by the [GraphicalTerminalImplementation] when it would like the OS to schedule a repaint of the
     * window.
     */
    abstract fun repaint()

    @Synchronized
    fun onCreated() {
        blinkTimer.schedule(object : TimerTask() {
            override fun run() {
                blinkOn = !blinkOn
                if (hasBlinkingText) {
                    repaint()
                }
            }
        }, deviceConfiguration.blinkLengthInMilliSeconds, deviceConfiguration.blinkLengthInMilliSeconds)
        enableInput = true
    }

    @Synchronized
    fun onDestroyed() {
        virtualTerminal.addInput(KeyStroke.EOF_STROKE)
        blinkTimer.cancel()
        enableInput = false
    }

    /**
     * Calculates the preferred size of this terminal.
     */
    fun getPreferredSize() = Dimension(
            getFontWidth() * virtualTerminal.getTerminalSize().columns,
            getFontHeight() * virtualTerminal.getTerminalSize().rows)

    /**
     * Updates the back buffer (if necessary) and draws it to the component's surface.
     */
    @Synchronized
    fun paintComponent(componentGraphics: Graphics) {
        var needToUpdateBackBuffer = hasBlinkingText

        // Detect resize
        if (resizeHappened()) {
            val terminalSize = TerminalSize(
                    columns = getWidth() / getFontWidth(),
                    rows = getHeight() / getFontHeight())
            virtualTerminal.setTerminalSize(terminalSize)
            needToUpdateBackBuffer = true
        }

        if (needToUpdateBackBuffer) {
            updateBackBuffer()
        }

        ensureGraphicBufferHasRightSize()
        val clipBounds: Rectangle = componentGraphics.clipBounds ?: Rectangle(0, 0, getWidth(), getHeight())
        componentGraphics.drawImage(
                buffer.get(),
                clipBounds.x,
                clipBounds.y,
                clipBounds.getWidth().toInt(),
                clipBounds.getHeight().toInt(),
                clipBounds.x,
                clipBounds.y,
                clipBounds.getWidth().toInt(),
                clipBounds.getHeight().toInt(), null)

        fillLeftoverSpaceWithBlack(componentGraphics)

        this.lastComponentWidth = getWidth()
        this.lastComponentHeight = getHeight()
        componentGraphics.dispose()
    }

    private fun fillLeftoverSpaceWithBlack(componentGraphics: Graphics) {
        // Take care of the left-over area at the bottom and right of the component where no character can fit
        componentGraphics.color = Color.BLACK

        val leftoverWidth = getWidth() % getFontWidth()
        if (leftoverWidth > 0) {
            componentGraphics.fillRect(getWidth() - leftoverWidth, 0, leftoverWidth, getHeight())
        }

        val leftoverHeight = getHeight() % getFontHeight()
        if (leftoverHeight > 0) {
            componentGraphics.fillRect(0, getHeight() - leftoverHeight, getWidth(), leftoverHeight)
        }
    }

    @Synchronized
    private fun updateBackBuffer() {
        val cursorPosition = virtualTerminal.getCursorPosition()
        var foundBlinkingCharacters = deviceConfiguration.isCursorBlinking
        ensureGraphicBufferHasRightSize()
        val backBufferGraphics: Graphics2D = buffer.get().createGraphics()

        if (isTextAntiAliased()) {
            backBufferGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
            backBufferGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        }

        virtualTerminal.forEachDirtyCell { (position, textCharacter) ->
            val atCursorLocation = cursorPosition == position
            val characterWidth = getFontWidth()
            val foregroundColor = deriveTrueForegroundColor(textCharacter, atCursorLocation)
            val backgroundColor = deriveTrueBackgroundColor(textCharacter, atCursorLocation)
            val drawCursor = atCursorLocation && (!deviceConfiguration.isCursorBlinking || //Always draw if the cursor isn't blinking
                    deviceConfiguration.isCursorBlinking && blinkOn)    //If the cursor is blinking, only draw when blinkOn is true
            if (textCharacter.getModifiers().contains(Modifier.BLINK)) {
                foundBlinkingCharacters = true
            }

            drawCharacter(backBufferGraphics,
                    textCharacter,
                    position.column,
                    position.row,
                    foregroundColor,
                    backgroundColor,
                    characterWidth,
                    drawCursor)
        }

        backBufferGraphics.dispose()

        this.hasBlinkingText = foundBlinkingCharacters || deviceConfiguration.isCursorBlinking
    }

    private fun ensureGraphicBufferHasRightSize() {
        if (buffer.isPresent.not()) {
            buffer = Optional.of(BufferedImage(getWidth() * 2, getHeight() * 2, BufferedImage.TYPE_INT_RGB))

            val graphics = buffer.get().createGraphics()
            graphics.color = colorConfiguration.toAWTColor(TextColor.ANSI.DEFAULT, false, false)
            graphics.fillRect(0, 0, getWidth() * 2, getHeight() * 2)
            graphics.dispose()
        }
        val backBufferRef = buffer.get()
        if (backBufferRef.width < getWidth() || backBufferRef.width > getWidth() * 4 ||
                backBufferRef.height < getHeight() || backBufferRef.height > getHeight() * 4) {

            val newBackBuffer = BufferedImage(Math.max(getWidth(), 1) * 2, Math.max(getHeight(), 1) * 2, BufferedImage.TYPE_INT_RGB)
            val graphics = newBackBuffer.createGraphics()
            graphics.fillRect(0, 0, newBackBuffer.width, newBackBuffer.height)
            graphics.drawImage(backBufferRef, 0, 0, null)
            graphics.dispose()
            buffer = Optional.of(newBackBuffer)
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
        graphics.color = backgroundColor
        graphics.setClip(x, y, characterWidth, getFontHeight())
        graphics.fillRect(x, y, characterWidth, getFontHeight())

        graphics.color = foregroundColor

        monospaceFontRenderer.renderCharacter(character, graphics, x, y)

        if (drawCursor) {
            graphics.color = colorConfiguration.toAWTColor(deviceConfiguration.cursorColor, false, false)
            if (deviceConfiguration.cursorStyle === UNDER_BAR) {
                graphics.fillRect(x, y + getFontHeight() - 3, characterWidth, 2)
            } else if (deviceConfiguration.cursorStyle === VERTICAL_BAR) {
                graphics.fillRect(x, y + 1, 2, getFontHeight() - 2)
            }
        }
    }


    private fun deriveTrueForegroundColor(character: TextCharacter, atCursorLocation: Boolean): Color {
        val foregroundColor = character.getForegroundColor()
        val backgroundColor = character.getBackgroundColor()
        var inverse = character.isInverse()
        val blink = character.isBlinking()

        if (isCursorVisible() && atCursorLocation) {
            if (deviceConfiguration.cursorStyle === REVERSED) {
                inverse = true
            }
        }

        if (inverse && (!blink || !blinkOn)) {
            return colorConfiguration.toAWTColor(backgroundColor, backgroundColor !== TextColor.ANSI.DEFAULT, character.isBold())
        } else if (!inverse && blink && blinkOn) {
            return colorConfiguration.toAWTColor(backgroundColor, false, character.isBold())
        } else {
            return colorConfiguration.toAWTColor(foregroundColor, true, character.isBold())
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
            return colorConfiguration.toAWTColor(foregroundColor, backgroundColor === TextColor.ANSI.DEFAULT, character.isBold())
        } else {
            return colorConfiguration.toAWTColor(backgroundColor, false, false)
        }
    }

    @Synchronized
    override fun clearScreen() {
        virtualTerminal.clearScreen()
        clearBuffer()
    }

    private fun clearBuffer() {
        if (buffer.isPresent) {
            val graphics = buffer.get().createGraphics()
            val backgroundColor = colorConfiguration.toAWTColor(TextColor.ANSI.DEFAULT, false, false)
            graphics.color = backgroundColor
            graphics.fillRect(0, 0, getWidth(), getHeight())
            graphics.dispose()
        }
    }

    @Synchronized
    override fun setCursorPosition(cursorPosition: TerminalPosition) {
        var fixedPos = cursorPosition
        if (fixedPos.column < 0) {
            fixedPos = fixedPos.withColumn(0)
        }
        if (fixedPos.row < 0) {
            fixedPos = fixedPos.withRow(0)
        }
        virtualTerminal.setCursorPosition(fixedPos)
    }

    @Synchronized
    override fun flush() {
        updateBackBuffer()
        repaint()
    }

    override fun close() {
        // No action
    }

    protected inner class TerminalInputListener : KeyAdapter() {

        override fun keyTyped(e: KeyEvent) {
            var character = e.keyChar
            val altDown = e.modifiersEx and InputEvent.ALT_DOWN_MASK != 0
            val ctrlDown = e.modifiersEx and InputEvent.CTRL_DOWN_MASK != 0
            val shiftDown = e.modifiersEx and InputEvent.SHIFT_DOWN_MASK != 0

            if (!TYPED_KEYS_TO_IGNORE.contains(character)) {
                //We need to re-adjust alphabet characters if ctrl was pressed, just like for the AnsiTerminal
                if (ctrlDown && character.toInt() > 0 && character.toInt() < 0x1a) {
                    character = ('a' - 1 + character.toInt()).toChar()
                    if (shiftDown) {
                        character = Character.toUpperCase(character)
                    }
                }

                // Check if clipboard is avavilable and this was a paste (ctrl + shift + v) before
                // adding the key to the input queue
                if (!altDown && ctrlDown && shiftDown && character == 'V' && deviceConfiguration.isClipboardAvailable) {
                    pasteClipboardContent()
                } else {
                    virtualTerminal.addInput(KeyStroke(
                            character = character,
                            ctrlDown = ctrlDown,
                            altDown = altDown,
                            shiftDown = shiftDown))
                }
            }
        }

        override fun keyPressed(e: KeyEvent) {
            val altDown = e.modifiersEx and InputEvent.ALT_DOWN_MASK != 0
            val ctrlDown = e.modifiersEx and InputEvent.CTRL_DOWN_MASK != 0
            val shiftDown = e.modifiersEx and InputEvent.SHIFT_DOWN_MASK != 0

            if (e.keyCode == KeyEvent.VK_INSERT) {
                // This could be a paste (shift+insert) if the clipboard is available
                if (!altDown && !ctrlDown && shiftDown && deviceConfiguration.isClipboardAvailable) {
                    pasteClipboardContent()
                } else {
                    virtualTerminal.addInput(KeyStroke(it = InputType.Insert,
                            ctrlDown = ctrlDown,
                            altDown = altDown,
                            shiftDown = shiftDown))
                }
            } else if (e.keyCode == KeyEvent.VK_TAB) {
                if (e.isShiftDown) {
                    virtualTerminal.addInput(KeyStroke(it = InputType.ReverseTab,
                            ctrlDown = ctrlDown,
                            altDown = altDown,
                            shiftDown = shiftDown))
                } else {
                    virtualTerminal.addInput(KeyStroke(it = InputType.Tab,
                            ctrlDown = ctrlDown,
                            altDown = altDown,
                            shiftDown = shiftDown))
                }
            } else if (KEY_EVENT_TO_KEY_TYPE_LOOKUP.containsKey(e.keyCode)) {
                virtualTerminal.addInput(KeyStroke(it = KEY_EVENT_TO_KEY_TYPE_LOOKUP[e.keyCode]!!,
                        ctrlDown = ctrlDown,
                        altDown = altDown,
                        shiftDown = shiftDown))
            } else {
                //keyTyped doesn't catch this scenario (for whatever reason...) so we have to do it here
                if (altDown && ctrlDown && e.keyCode >= 'A'.toByte() && e.keyCode <= 'Z'.toByte()) {
                    var character = e.keyCode.toChar()
                    if (!shiftDown) {
                        character = Character.toLowerCase(character)
                    }
                    virtualTerminal.addInput(KeyStroke(
                            character = character,
                            ctrlDown = ctrlDown,
                            altDown = altDown,
                            shiftDown = shiftDown))
                }
            }
        }
    }

    protected open inner class TerminalMouseListener : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            if (MouseInfo.getNumberOfButtons() > 2 &&
                    e.button == MouseEvent.BUTTON2 &&
                    deviceConfiguration.isClipboardAvailable) {
                pasteSelectionContent()
            }
            addActionToKeyQueue(MOUSE_CLICKED, e)
        }

        override fun mouseReleased(e: MouseEvent) {
            addActionToKeyQueue(MOUSE_RELEASED, e)
        }

        override fun mouseMoved(e: MouseEvent) {
            addActionToKeyQueue(MOUSE_MOVED, e)
        }

        override fun mouseEntered(e: MouseEvent) {
            addActionToKeyQueue(MOUSE_ENTERED, e)
        }

        override fun mouseExited(e: MouseEvent) {
            addActionToKeyQueue(MOUSE_EXITED, e)
        }

        override fun mouseDragged(e: MouseEvent) {
            addActionToKeyQueue(MOUSE_DRAGGED, e)
        }

        override fun mousePressed(e: MouseEvent) {
            addActionToKeyQueue(MOUSE_PRESSED, e)
        }

        override fun mouseWheelMoved(e: MouseWheelEvent) {
            val actionType = if (e.preciseWheelRotation > 0) {
                MOUSE_WHEEL_ROTATED_DOWN
            } else {
                MOUSE_WHEEL_ROTATED_UP
            }
            (0..e.preciseWheelRotation.toInt()).forEach {
                addActionToKeyQueue(actionType, e)
            }
        }

        private fun addActionToKeyQueue(actionType: MouseActionType, e: MouseEvent) {
            virtualTerminal.addInput(MouseAction(
                    actionType = actionType,
                    button = e.button,
                    position = TerminalPosition(
                            column = e.x.div(getFontWidth()),
                            row = e.y.div(getFontHeight()))
            ))
        }
    }

    private fun pasteClipboardContent() {
        Toolkit.getDefaultToolkit().systemClipboard?.let {
            injectStringAsKeyStrokes(it.getData(DataFlavor.stringFlavor) as String)
        }
    }

    private fun pasteSelectionContent() {
        Toolkit.getDefaultToolkit().systemSelection?.let {
            injectStringAsKeyStrokes(it.getData(DataFlavor.stringFlavor) as String)
        }
    }

    private fun injectStringAsKeyStrokes(string: String) {
        string
                .filter {
                    TextUtils.isPrintableCharacter(it)
                }
                .forEach {
                    virtualTerminal.addInput(KeyStroke(character = it))
                }
    }

    private fun resizeHappened() = getWidth() != lastComponentWidth || getHeight() != lastComponentHeight

    companion object {
        private val TYPED_KEYS_TO_IGNORE = HashSet(Arrays.asList('\n', '\t', '\r', '\b', '\u001b', 127.toChar()))

        val KEY_EVENT_TO_KEY_TYPE_LOOKUP = mapOf(
                Pair(KeyEvent.VK_ENTER, InputType.Enter),
                Pair(KeyEvent.VK_ESCAPE, InputType.Escape),
                Pair(KeyEvent.VK_BACK_SPACE, InputType.Backspace),
                Pair(KeyEvent.VK_LEFT, InputType.ArrowLeft),
                Pair(KeyEvent.VK_RIGHT, InputType.ArrowRight),
                Pair(KeyEvent.VK_UP, InputType.ArrowUp),
                Pair(KeyEvent.VK_DOWN, InputType.ArrowDown),
                Pair(KeyEvent.VK_DELETE, InputType.Delete),
                Pair(KeyEvent.VK_HOME, InputType.Home),
                Pair(KeyEvent.VK_END, InputType.End),
                Pair(KeyEvent.VK_PAGE_UP, InputType.PageUp),
                Pair(KeyEvent.VK_PAGE_DOWN, InputType.PageDown),
                Pair(KeyEvent.VK_F1, InputType.F1),
                Pair(KeyEvent.VK_F2, InputType.F2),
                Pair(KeyEvent.VK_F3, InputType.F3),
                Pair(KeyEvent.VK_F4, InputType.F4),
                Pair(KeyEvent.VK_F5, InputType.F5),
                Pair(KeyEvent.VK_F6, InputType.F6),
                Pair(KeyEvent.VK_F7, InputType.F7),
                Pair(KeyEvent.VK_F8, InputType.F8),
                Pair(KeyEvent.VK_F9, InputType.F9),
                Pair(KeyEvent.VK_F10, InputType.F10),
                Pair(KeyEvent.VK_F11, InputType.F11),
                Pair(KeyEvent.VK_F12, InputType.F12))
    }
}