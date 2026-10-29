package org.hexworks.zircon.internal.component.impl

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.progressBar
import org.hexworks.zircon.api.builder.component.textArea
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.DrawWindow
import org.hexworks.zircon.internal.component.InternalComponent
import kotlin.test.Test

class ComponentRenderCacheTest {

    private class CountingRenderer<T : Component> : ComponentRenderer<T> {
        var renders = 0
        override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<T>) {
            renders++
        }
    }

    @Test
    fun anUnchangedComponentRendersOnce() {
        val counter = CountingRenderer<org.hexworks.zircon.api.component.Label>()
        val target = label { +"hello"; componentRenderer = counter } as InternalComponent
        target.render() shouldBeSameInstanceAs target.render()
        counter.renders shouldBe 1
    }

    @Test
    fun changingTheTextRendersAgain() {
        val counter = CountingRenderer<org.hexworks.zircon.api.component.Label>()
        val target = label { +"hello"; componentRenderer = counter } as DefaultLabel
        target.render()
        target.text = "world"
        target.render()
        counter.renders shouldBe 2
    }

    @Test
    fun changingTheStateRendersAgain() {
        val counter = CountingRenderer<org.hexworks.zircon.api.component.Label>()
        val target = label { +"hello"; componentRenderer = counter } as InternalComponent
        target.render()
        target.componentState = ComponentState.HIGHLIGHTED
        target.render()
        counter.renders shouldBe 2
    }

    @Test
    fun invalidatingRendersAgain() {
        val counter = CountingRenderer<org.hexworks.zircon.api.component.Label>()
        val target = label { +"hello"; componentRenderer = counter } as InternalComponent
        target.render()
        target.invalidateRender()
        target.render()
        counter.renders shouldBe 2
    }

    @Test
    fun subclassRenderInputsAreFollowed() {
        val counter = CountingRenderer<org.hexworks.zircon.api.component.ProgressBar>()
        val target = progressBar {
            preferredSize = Size(10, 1); range = 100; numberOfSteps = 10
            componentRenderer = counter
        } as DefaultProgressBar
        target.render()
        target.progress = 50.0
        target.render()
        counter.renders shouldBe 2
    }

    @Test
    fun settingTextAreaTextRendersAgain() {
        val counter = CountingRenderer<org.hexworks.zircon.api.component.TextArea>()
        val target = textArea { text = "abc"; preferredSize = Size(5, 1); componentRenderer = counter } as DefaultTextArea
        target.render()
        target.text = "xyz"
        target.render()
        counter.renders shouldBe 2
    }
}
