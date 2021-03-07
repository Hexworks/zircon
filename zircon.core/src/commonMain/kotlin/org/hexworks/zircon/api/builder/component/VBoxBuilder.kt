package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.component.impl.DefaultVBox
import org.hexworks.zircon.internal.component.renderer.DefaultVBoxRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class VBoxBuilder(
    private var spacing: Int = 0
) : BaseComponentBuilder<VBox, VBoxBuilder>(
    DefaultVBoxRenderer()
) {

    fun withSpacing(spacing: Int) = also {
        require(spacing >= 0) {
            "Can't use a negative spacing"
        }
        this.spacing = spacing
    }

    override fun build(): VBox {
        return DefaultVBox(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            initialTitle = title,
            spacing = spacing
        )
    }

    override fun createCopy() = newBuilder().withProps(props.copy())
        .withSpacing(spacing)

    companion object {

        @JvmStatic
        fun newBuilder() = VBoxBuilder()
    }
}
