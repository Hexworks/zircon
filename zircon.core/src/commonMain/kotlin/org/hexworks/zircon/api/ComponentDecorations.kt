package org.hexworks.zircon.api

import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode.NON_INTERACTIVE
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.internal.component.renderer.decoration.*
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * This object contains functions for creating component decorations.
 */
object ComponentDecorations {

    /**
     * Can be used to draw a border around a [Component].
     */
    @JvmOverloads
    @JvmStatic
    fun border(
            border: Border = Border.newBuilder().build(),
            renderingMode: RenderingMode = NON_INTERACTIVE
    ): ComponentDecorationRenderer = BorderDecorationRenderer(
            border = border,
            renderingMode = renderingMode
    )

    /**
     * Can be used to draw a box (using box drawing characters) around a [Component].
     * **Note that** the [title] will only be displayed for a [Component] if it is wrapped
     * with a box. This decoration uses [RenderingMode.NON_INTERACTIVE] by default.
     */
    @JvmOverloads
    @JvmStatic
    fun box(
            boxType: BoxType = BoxType.SINGLE,
            title: String = "",
            renderingMode: RenderingMode = NON_INTERACTIVE
    ): ComponentDecorationRenderer = BoxDecorationRenderer(
            boxType = boxType,
            titleProperty = createPropertyFrom(title),
            renderingMode = renderingMode
    )

    /**
     * Wraps a [Component] on the left and the right sides with the given
     * [leftSideCharacter] and [rightSideCharacter]. This decoration will use
     * [RenderingMode.INTERACTIVE] by default.
     */
    @JvmOverloads
    @JvmStatic
    fun side(
            leftSideCharacter: Char = '[',
            rightSideCharacter: Char = ']',
            renderingMode: RenderingMode = RenderingMode.INTERACTIVE
    ): ComponentDecorationRenderer = SideDecorationRenderer(
            leftSideCharacter = leftSideCharacter,
            rightSideCharacter = rightSideCharacter,
            renderingMode = renderingMode
    )

    @JvmOverloads
    @JvmStatic
    fun halfBlock(
            renderingMode: RenderingMode = NON_INTERACTIVE
    ): ComponentDecorationRenderer = HalfBlockDecorationRenderer(renderingMode)

    /**
     * Can be used to draw a shadow around a [Component]. The shadow is drawn
     * around the bottom and the right sides.
     */
    @JvmStatic
    fun shadow(): ComponentDecorationRenderer = ShadowDecorationRenderer()


}
