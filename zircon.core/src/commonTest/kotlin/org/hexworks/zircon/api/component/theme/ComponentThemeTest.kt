package org.hexworks.zircon.api.component.theme

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.hexworks.zircon.api.ComponentThemes
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.builder.grid.tileGrid
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.api.extensions.isEmpty
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import kotlin.test.Test

class ComponentThemeTest {

    private val neobrutalism = ComponentThemes.neobrutalism()
    /** A second theme with a shape of its own, to switch to and from. */
    private val boxed = themeOf(ThemeKey(ComponentRole.PANEL) to themeEntry { decorations { +box() } })

    @Test
    fun factory_seeds_the_builder_so_the_size_includes_the_theme_decorations() {
        val button = neobrutalism.componentFactory.button { +"OK" }

        // 2 for the text, 2 for the padding, 2 for the border, 1 for the shadow.
        button.size shouldBe Size(7, 4)
        button.contentSize shouldBe Size(2, 1)
        button.componentTheme shouldBeSameInstanceAs neobrutalism
        button.role shouldBe ComponentRole.BUTTON
    }

    @Test
    fun a_screen_theme_reaches_an_attached_component() {
        val screen = tileGrid { size = Size(40, 20) }.toScreen()
        screen.componentTheme = neobrutalism
        val panel = panel { preferredSize = Size(10, 5) }
        panel.contentSize shouldBe Size(10, 5)

        screen.addComponent(panel)

        panel.componentTheme shouldBeSameInstanceAs neobrutalism
        // Frame (3x3) and padding (2x0): the outer size stays, the content shrinks.
        panel.size shouldBe Size(10, 5)
        panel.contentSize shouldBe Size(5, 2)
    }

    @Test
    fun switching_back_to_the_default_theme_restores_the_stock_look() {
        val screen = tileGrid { size = Size(40, 20) }.toScreen()
        val panel = panel { preferredSize = Size(10, 5) }
        screen.addComponent(panel)
        val stockStyle = panel.componentStyleSet

        screen.componentTheme = neobrutalism
        panel.contentSize shouldBe Size(5, 2)
        screen.componentTheme = boxed
        panel.componentTheme shouldBeSameInstanceAs boxed
        panel.contentSize shouldBe Size(8, 3)

        screen.componentTheme = ComponentThemes.default()
        panel.componentTheme shouldBeSameInstanceAs ComponentThemes.default()
        panel.contentSize shouldBe Size(10, 5)
        panel.componentStyleSet shouldBe stockStyle
    }

    @Test
    fun the_default_theme_is_the_stock_look_and_is_always_on() {
        val button = button { +"OK" }

        button.componentTheme shouldBeSameInstanceAs ComponentThemes.default()
        button.size shouldBe Size(4, 1)
        button.renderToRows() shouldBe listOf("[OK]")
    }

    @Test
    fun a_missing_aspect_falls_through_the_chain_to_the_default_theme() {
        // A theme that only frames buttons: the renderer comes from the default theme.
        val framed = themeOf(ThemeKey(ComponentRole.BUTTON) to themeEntry { decorations { +box() } })

        val button = framed.componentFactory.button {
            +"OK"
            variant = Variant.PRIMARY
        }

        button.renderToRows() shouldBe listOf(
            "┌──┐",
            "│OK│",
            "└──┘",
        )
        framed.resolveEntry(ComponentRole.BUTTON)!!.componentRenderer shouldBeSameInstanceAs
                ComponentThemes.default().resolveEntry(ComponentRole.BUTTON)!!.componentRenderer
    }

    @Test
    fun a_never_themed_role_resolves_to_nothing_even_in_the_default_theme() {
        ComponentThemes.default().resolveEntry(ComponentRole.NONE, kind = ComponentRole.PANEL) shouldBe null
        neobrutalism.resolveEntry(ComponentRole.NONE, kind = ComponentRole.PANEL) shouldBe null
    }

    @Test
    fun a_switch_between_themes_does_not_leak_the_old_renderer() {
        val custom: ComponentRenderer<Button> = ComponentRenderer { graphics, _ ->
            graphics.fill(Tile.defaultTile().withCharacter('X'))
        }
        val crossed = themeOf(ThemeKey(ComponentRole.BUTTON) to themeEntry { componentRenderer = custom })
        val button = crossed.componentFactory.button { +"OK" }
        button.renderToRows() shouldBe listOf("[XX]")

        // Neobrutalism says nothing about the button renderer: the default one, not `custom`.
        button.componentTheme = neobrutalism
        button.componentTheme = ComponentThemes.default()

        button.renderToRows() shouldBe listOf("[OK]")
    }

    @Test
    fun decorations_that_do_not_fit_are_skipped_but_the_colors_still_apply() {
        val button = button { +"OK" }
        val plainSize = button.size
        val plainContent = button.contentSize

        button.componentTheme = neobrutalism

        button.size shouldBe plainSize
        // A 1 row button has no room for a 3 row frame: the side decoration stays.
        button.contentSize shouldBe plainContent
        button.componentStyleSet shouldBe neobrutalism.resolveEntry(ComponentRole.BUTTON)!!.stylesFor(button.componentPalette, ComponentRole.BUTTON)
    }

    @Test
    fun an_explicitly_set_theme_is_pinned_against_the_parent() {
        val screen = tileGrid { size = Size(40, 20) }.toScreen()
        val box = vBox {
            preferredSize = Size(20, 10)
            componentTheme = neobrutalism
        }
        val inherited = vBox {
            preferredSize = Size(20, 10)
            position = Position(20, 0)
        }

        screen.addComponent(box)
        screen.addComponent(inherited)

        box.componentTheme shouldBeSameInstanceAs neobrutalism
        inherited.componentTheme shouldBeSameInstanceAs ComponentThemes.default()
        // The screen's theme reaches what was not pinned.
        screen.componentTheme = boxed
        box.componentTheme shouldBeSameInstanceAs neobrutalism
        inherited.componentTheme shouldBeSameInstanceAs boxed
    }

    @Test
    fun a_pinned_renderer_is_kept_under_a_theme() {
        val custom: ComponentRenderer<Button> = ComponentRenderer { graphics, _ ->
            graphics.fill(Tile.defaultTile().withCharacter('X'))
        }
        val button = neobrutalism.componentFactory.button {
            +"OK"
            componentRenderer = custom
        }

        val rows = button.renderToRows()

        rows[1] shouldBe "┃ XX ┃▌"
    }

    @Test
    fun a_pinned_style_set_is_kept_under_a_theme() {
        val mine = componentStyleSet {
            defaultStyle = styleSet { foregroundColor = Color.create(255, 0, 0) }
        }
        val button = neobrutalism.componentFactory.button {
            +"OK"
            componentStyleSet = mine
        }

        button.componentStyleSet shouldBeSameInstanceAs mine
        button.componentTheme = ComponentThemes.default()
        button.componentStyleSet shouldBeSameInstanceAs mine
        button.componentTheme shouldNotBe neobrutalism
    }

    @Test
    fun a_role_without_an_entry_leaves_the_component_alone() {
        val box = vBox {
            preferredSize = Size(10, 5)
            role = ComponentRole.NONE
        }
        val style = box.componentStyleSet

        box.componentTheme = neobrutalism

        box.contentSize shouldBe Size(10, 5)
        box.componentStyleSet shouldBeSameInstanceAs style
    }

    @Test
    fun a_variant_without_an_entry_falls_back_to_the_default_variant() {
        val theme = themeOf(ThemeKey(ComponentRole.BUTTON) to themeEntry { decorations { +box() } })

        val button = theme.componentFactory.button {
            +"OK"
            variant = Variant.PRIMARY
        }

        button.size shouldBe Size(4, 3)
        button.contentSize shouldBe Size(2, 1)
    }

    @Test
    fun a_part_without_an_entry_falls_back_to_the_component_kind_not_to_its_composite() {
        val framedCard = themeOf(ThemeKey(ComponentRole.CARD) to themeEntry { decorations { +box() } })
        val boldLabels = themeOf(ThemeKey(ComponentRole.LABEL) to themeEntry { decorations { +box() } })

        val cardTitle = framedCard.componentFactory.label {
            +"Title"
            role = ComponentRole.CARD
            part = Part.TITLE
        }
        val plainTitle = boldLabels.componentFactory.label {
            +"Title"
            role = ComponentRole.CARD
            part = Part.TITLE
        }

        // The card's frame is the card's, not its title's.
        cardTitle.size shouldBe Size(5, 1)
        // A card title is a label when the theme says nothing about card titles.
        plainTitle.size shouldBe Size(7, 3)
    }

    @Test
    fun the_fallback_order_is_default_variant_then_kind() {
        ThemeKey(ComponentRole.CARD, Variant.WARNING, Part.TITLE).fallbacks(kind = ComponentRole.LABEL) shouldBe listOf(
            ThemeKey(ComponentRole.CARD, Variant.WARNING, Part.TITLE),
            ThemeKey(ComponentRole.CARD, Variant.DEFAULT, Part.TITLE),
            ThemeKey(ComponentRole.LABEL, Variant.WARNING),
            ThemeKey(ComponentRole.LABEL),
        )
        ThemeKey(ComponentRole.BUTTON).fallbacks() shouldBe listOf(ThemeKey(ComponentRole.BUTTON))
        ThemeKey(ComponentRole.NONE).fallbacks(kind = ComponentRole.PANEL) shouldBe emptyList()
    }

    private fun themeOf(vararg entries: Pair<ThemeKey, ThemeEntry>): ComponentTheme = object : ComponentTheme {
        private val map = entries.toMap()
        override val name: String = "test"
        override fun entryFor(key: ThemeKey): ThemeEntry? = map[key]
    }
}

/**
 * What the component and its descendants draw, one string per row; `_` is a cell nothing drew on.
 */
internal fun org.hexworks.zircon.api.component.Component.renderToRows(): List<String> {
    val root = this as InternalComponent
    val origin = root.absolutePosition
    val rows = Array(size.height) { CharArray(size.width) { '_' } }
    fun paint(component: InternalComponent) {
        val graphics = component.render()
        val offset = component.absolutePosition - origin
        for (y in 0 until component.size.height) for (x in 0 until component.size.width) {
            val tile = graphics.getTileAtOrNull(Position(x, y)) as? CharacterTile ?: continue
            if (tile.isEmpty) continue
            val ty = offset.y + y
            val tx = offset.x + x
            if (ty in rows.indices && tx in rows[ty].indices) rows[ty][tx] = tile.character
        }
        component.children.forEach { paint(it) }
    }
    paint(root)
    return rows.map { it.concatToString() }
}
