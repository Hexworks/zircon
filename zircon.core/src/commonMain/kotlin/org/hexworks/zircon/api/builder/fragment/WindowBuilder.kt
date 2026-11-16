package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.dsl.AnyContainerBuilder
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.dsl.buildFragmentFor
import org.hexworks.zircon.api.fragment.Window
import org.hexworks.zircon.api.fragment.builder.ThemedFragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultWindow

/**
 * Builds a [Window]: [width] is the outer width, the height follows from the wrapped texts.
 */
@ZirconDsl
public class WindowBuilder : ThemedFragmentBuilder<Window> {

    override var position: Position = Position.ZERO
    override var theme: ComponentTheme? = null
    public var width: Int = 40
    public var title: String = ""
    public var header: String = ""
    public var body: String = ""

    /**
     * The window controls shown at the right end of the title bar.
     */
    public var controls: String = "□ □"

    override fun build(): Window {
        require(width >= 10) { "A window needs at least 10 columns" }
        return DefaultWindow(
            width = width,
            initialTitle = title,
            initialHeader = header,
            initialBody = body,
            controls = controls,
            theme = theme,
        ).also { it.root.moveTo(position) }
    }
}

public fun window(init: WindowBuilder.() -> Unit): Window = WindowBuilder().apply(init).build()

public fun AnyContainerBuilder.window(init: WindowBuilder.() -> Unit): Window = buildFragmentFor(this, WindowBuilder(), init)
