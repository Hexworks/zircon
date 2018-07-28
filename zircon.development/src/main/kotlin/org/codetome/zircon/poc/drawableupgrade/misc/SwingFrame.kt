package org.codetome.zircon.poc.drawableupgrade.misc

import org.codetome.zircon.poc.drawableupgrade.renderer.SwingCanvasRenderer
import java.awt.Frame
import java.awt.event.WindowEvent
import java.awt.event.WindowStateListener
import javax.swing.JFrame

class SwingFrame(private val renderer: SwingCanvasRenderer) : JFrame(), WindowStateListener {

    override fun windowStateChanged(e: WindowEvent) {
        if (e.newState == Frame.NORMAL) {
            println("============ Window state changed")
            renderer.render()
        }
    }

    init {
        isResizable = false // TODO: implement proper resizing
        add(renderer.surface)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        pack()
        setLocationRelativeTo(null)
        renderer.surface.createBufferStrategy(2)
        renderer.initializeBufferStrategy()
        addWindowStateListener(this)
    }
}
