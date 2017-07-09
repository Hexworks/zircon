package org.codetome.zircon.terminal.swing

import org.codetome.zircon.TextCharacter
import org.codetome.zircon.input.Input
import org.codetome.zircon.terminal.TerminalSize
import org.codetome.zircon.terminal.config.TerminalFontConfiguration
import org.codetome.zircon.terminal.config.TerminalColorConfiguration
import org.codetome.zircon.terminal.config.TerminalDeviceConfiguration
import org.codetome.zircon.terminal.virtual.DefaultVirtualTerminal
import java.awt.AWTKeyStroke
import java.awt.Dimension
import java.awt.Font
import java.awt.KeyboardFocusManager
import java.awt.event.HierarchyEvent
import java.awt.event.MouseEvent
import javax.swing.JComponent
import javax.swing.SwingUtilities

/**
 * Concrete implementation of [GraphicalTerminalImplementation] that adapts it to Swing.
 */
class SwingTerminalImplementation(
        private val component: JComponent,
        val fontConfiguration: TerminalFontConfiguration,
        initialTerminalSize: TerminalSize,
        deviceConfiguration: TerminalDeviceConfiguration,
        colorConfiguration: TerminalColorConfiguration)

    : GraphicalTerminalImplementation(deviceConfiguration, colorConfiguration, DefaultVirtualTerminal(initialTerminalSize)) {

    init {

        //Prevent us from shrinking beyond one character
        component.minimumSize = Dimension(fontConfiguration.getFontWidth(), fontConfiguration.getFontHeight())
        component.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, emptySet<AWTKeyStroke>())
        component.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, emptySet<AWTKeyStroke>())
        //Make sure the component is double-buffered to prevent flickering
        component.isDoubleBuffered = true
        component.addKeyListener(TerminalInputListener())
        component.addMouseListener(object : TerminalMouseListener() {
            override fun mouseClicked(e: MouseEvent) {
                super.mouseClicked(e)
                this@SwingTerminalImplementation.component.requestFocusInWindow()
            }
        })
        component.addHierarchyListener { e ->
            if (e.changeFlags == HierarchyEvent.DISPLAYABILITY_CHANGED.toLong()) {
                if (e.changed.isDisplayable) {
                    onCreated()
                } else {
                    onDestroyed()
                }
            }
        }
    }

    override fun getFontHeight() = fontConfiguration.getFontHeight()

    override fun getFontWidth() = fontConfiguration.getFontWidth()

    override fun getHeight() = component.height

    override fun getWidth() = component.width

    override fun getFontForCharacter(character: TextCharacter): Font {
        return fontConfiguration.getFontForCharacter(character)
    }

    override fun isTextAntiAliased() = fontConfiguration.isAntiAliased()

    override fun repaint() {
        if (SwingUtilities.isEventDispatchThread()) {
            component.repaint()
        } else {
            SwingUtilities.invokeLater { component.repaint() }
        }
    }

    override fun readInput(): Input {
        if (SwingUtilities.isEventDispatchThread()) {
            throw UnsupportedOperationException("Cannot call SwingTerminalComponent.readInput() on the AWT thread")
        }
        return super.readInput()
    }
}
