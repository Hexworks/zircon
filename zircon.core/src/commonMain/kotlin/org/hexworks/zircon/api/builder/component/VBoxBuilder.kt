package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultVBox
import org.hexworks.zircon.internal.component.renderer.DefaultVBoxRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.math.max

@ZirconDsl
class VBoxBuilder : BaseContainerBuilder<VBox>(
    initialRenderer = DefaultVBoxRenderer()
) {

    var spacing: Int = 0
        set(value) {
            require(value >= 0) {
                "Can't use a negative spacing"
            }
            field = value
        }

    override fun build(): VBox {
        return DefaultVBox(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            initialTitle = title,
            spacing = spacing
        ).apply {
            addComponents(*childrenToAdd.toTypedArray())
        }.attachListeners()
    }

    @Suppress("DuplicatedCode")
    override fun calculateContentSize(): Size {
        if (childrenToAdd.isEmpty()) {
            return Size.one()
        }
        var height = 0
        var maxWidth = 0
        childrenToAdd
            .map { it.size }
            .forEach {
                height += it.height
                maxWidth = max(maxWidth, it.width)
            }
        if (spacing > 0) {
            height += childrenToAdd.size * spacing - 1
        }
        return Size.create(maxWidth, max(1, height))
    }
}

/**
 * Creates a new [VBox] using the component builder DSL and returns it.
 */
fun buildVbox(init: VBoxBuilder.() -> Unit): VBox =
    VBoxBuilder().apply(init).build()

/**
 * Creates a new [VBox] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [VBox].
 */
fun <T : BaseContainerBuilder<*>> T.vbox(
    init: VBoxBuilder.() -> Unit
): VBox = buildChildFor(this, VBoxBuilder(), init)