package org.codetome.zircon.behavior.impl

import org.codetome.zircon.Position
import org.codetome.zircon.api.TextImageBuilder
import org.codetome.zircon.behavior.ContainerHolder
import org.codetome.zircon.component.Container
import org.codetome.zircon.graphics.image.TextImage
import org.codetome.zircon.input.InputProvider
import org.codetome.zircon.input.MouseAction
import org.codetome.zircon.input.MouseActionType
import java.util.concurrent.Executors

class DefaultContainerHolder(private var container: Container) : ContainerHolder {

    private lateinit var inputProvider: InputProvider
    private val executor = Executors.newSingleThreadExecutor()
    private var lastMousePosition = Position.DEFAULT_POSITION

    override fun getContainer() = container

    override fun setContainer(container: Container) {
        this.container = container
    }

    override fun drawComponentsToImage(): TextImage {
        val result = TextImageBuilder.newBuilder()
                .size(container.getBoundableSize())
                .build()
        container.drawOnto(result)
        return result
    }

    override fun setInputProvider(inputProvider: InputProvider) {
        this.inputProvider = inputProvider
        executor.submit {
            while (true) {
                val maybeInput = inputProvider.pollInput()
                if (maybeInput.isPresent) {
                    maybeInput.get().let { input ->
                        if (input is MouseAction && input.actionType == MouseActionType.MOUSE_MOVED) {
                            if(input.position != lastMousePosition) {
                                lastMousePosition = input.position
                                println(lastMousePosition)
                            }
                        }
                    }
                }
            }
        }
    }
}