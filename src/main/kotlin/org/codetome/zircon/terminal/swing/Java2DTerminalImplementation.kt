package org.codetome.zircon.terminal.swing

import org.codetome.zircon.Modifier
import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.builder.TextColorFactory
import org.codetome.zircon.font.Font
import org.codetome.zircon.input.InputType
import org.codetome.zircon.input.KeyStroke
import org.codetome.zircon.input.MouseAction
import org.codetome.zircon.input.MouseActionType
import org.codetome.zircon.input.MouseActionType.*
import org.codetome.zircon.terminal.Size
import org.codetome.zircon.terminal.config.CursorStyle.*
import org.codetome.zircon.terminal.config.DeviceConfiguration
import org.codetome.zircon.terminal.virtual.VirtualTerminal
import org.codetome.zircon.util.TextUtils
import java.awt.*
import java.awt.datatransfer.DataFlavor
import java.awt.event.*
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
        private val virtualTerminal: VirtualTerminal)
    : VirtualTerminal by virtualTerminal {

    private var enableInput = false
    private var hasBlinkingText = deviceConfiguration.isCursorBlinking
    private var blinkOn = true
    private var lastBufferUpdateScrollPosition: Int = 0
    private var lastComponentWidth: Int = 0
    private var lastComponentHeight: Int = 0

    private var blinkTimer = Timer("BlinkTimer", true)
    private val charRenderer = SwingCharacterImageRenderer(font.getWidth(), font.getHeight())

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
     * Returns `true` if anti-aliasing is enabled, `false` otherwise.
     */
    abstract fun isTextAntiAliased(): Boolean

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
            getFontWidth() * virtualTerminal.getBoundableSize().columns,
            getFontHeight() * virtualTerminal.getBoundableSize().rows)

    /**
     * Updates the back buffer (if necessary) and draws it to the component's surface.
     */
    @Synchronized
    fun draw(graphics: Graphics2D) {
        var needToRedraw = hasBlinkingText

        // Detect resize
        if (resizeHappened()) {
            val terminalSize = Size(
                    columns = getWidth() / getFontWidth(),
                    rows = getHeight() / getFontHeight())
            virtualTerminal.setSize(terminalSize)
            needToRedraw = true
        }

        if (isDirty()) {
            needToRedraw = true
        }

        if (needToRedraw) {
            val cursorPosition = virtualTerminal.getCursorPosition()
            var foundBlinkingCharacters = deviceConfiguration.isCursorBlinking

            graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED)
            graphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE)
            graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY)
            graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF)
            graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED)
            if (isTextAntiAliased()) {
                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            } else {
                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF)
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF)
            }

            virtualTerminal.forEachDirtyCell { (position, textCharacter) ->
                val atCursorLocation = cursorPosition == position
                val characterWidth = getFontWidth()
                val foregroundColor = deriveTrueForegroundColor(textCharacter)
                val backgroundColor = deriveTrueBackgroundColor(textCharacter, atCursorLocation)
                val drawCursor = atCursorLocation && (!deviceConfiguration.isCursorBlinking || //Always draw if the cursor isn't blinking
                        deviceConfiguration.isCursorBlinking && blinkOn)    //If the cursor is blinking, only draw when blinkOn is true
                if (textCharacter.getModifiers().contains(Modifier.BLINK)) {
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

        // TODO:    add smart refresh for layers (`charDiffersInBuffers` should
        // TODO:    check z level intersections of layers)

        if (fixedChar.isNotEmpty()) {
            graphics.drawImage(font.fetchRegionForChar(fixedChar), x, y, null)
        }

        fetchOverlayZIntersection(Position(columnIndex, rowIndex)).forEach {
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
    override fun clear() {
        virtualTerminal.clear()
    }

    @Synchronized
    override fun setCursorPosition(cursorPosition: Position) {
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
        draw()
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
                    position = Position(
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