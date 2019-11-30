package org.hexworks.zircon.api

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.internal.component.renderer.decoration.BorderDecorationRenderer
import org.hexworks.zircon.internal.component.renderer.decoration.BoxDecorationRenderer
import org.hexworks.zircon.internal.component.renderer.decoration.HalfBlockDecorationRenderer
import org.hexworks.zircon.internal.component.renderer.decoration.ShadowDecorationRenderer
import org.hexworks.zircon.internal.component.renderer.decoration.SideDecorationRenderer
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.modifier.Border
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

object ComponentDecorations {

    /**
     * Can be used to draw a border around a [Component].
     */
    @JvmStatic
    fun border(border: Border): ComponentDecorationRenderer = BorderDecorationRenderer(border)

    /**
     * Can be used to draw a box (using box drawing characters) around a [Component].
     * **Note that** a `title` will only be displayed for a [Component] if it is wrapped
     * with a box.
     */
    @JvmOverloads
    @JvmStatic
    fun box(boxType: BoxType = BoxType.SINGLE,
            title: String = ""): ComponentDecorationRenderer = BoxDecorationRenderer(
            boxType = boxType,
            titleProperty = createPropertyFrom(title))

    /**
     * Wraps a [Component] on the left and the right sides with the given
     * [leftSideCharacter] and [rightSideCharacter].
     */
    @JvmStatic
    @JvmOverloads
    fun side(leftSideCharacter: Char = '[',
             rightSideCharacter: Char = ']'): ComponentDecorationRenderer =
            SideDecorationRenderer(leftSideCharacter, rightSideCharacter)

    @JvmStatic
    fun halfBlock(): ComponentDecorationRenderer = HalfBlockDecorationRenderer()

    /**
     * Can be used to draw a shadow around a [Component]. The shadow is drawn
     * around the bottom and the right sides.
     */
    @JvmStatic
    fun shadow(): ComponentDecorationRenderer = ShadowDecorationRenderer()


}
