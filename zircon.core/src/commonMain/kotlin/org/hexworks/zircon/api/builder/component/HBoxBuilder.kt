package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultHBox
import org.hexworks.zircon.internal.component.renderer.DefaultHBoxRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.math.max

@ZirconDsl
class HBoxBuilder : BaseContainerBuilder<HBox>(DefaultHBoxRenderer()) {

    var spacing: Int = 0
        set(value) {
            require(value >= 0) {
                "Can't use a negative spacing"
            }
            field = value
        }


    override fun build(): HBox {
        return DefaultHBox(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            initialTitle = title,
            spacing = spacing,
        ).apply {
            addComponents(*childrenToAdd.toTypedArray())
        }.attachListeners()
    }

    @Suppress("DuplicatedCode")
    override fun calculateContentSize(): Size {
        if (childrenToAdd.isEmpty()) {
            return Size.one()
        }
        var width = 0
        var maxHeight = 0
        childrenToAdd
            .map { it.size }
            .forEach {
                width += it.width
                maxHeight = max(maxHeight, it.height)
            }
        if (spacing > 0) {
            width += childrenToAdd.size * spacing - 1
        }
        return Size.create(max(1, width), maxHeight)
    }
}

/**
 * Creates a new [HBox] using the component builder DSL and returns it.
 */
fun buildHbox(init: HBoxBuilder.() -> Unit): HBox =
    HBoxBuilder().apply(init).build()

/**
 * Creates a new [HBox] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [HBox].
 */
fun <T : BaseContainerBuilder<*>> T.hbox(
    init: HBoxBuilder.() -> Unit
): HBox = buildChildFor(this, HBoxBuilder(), init)
