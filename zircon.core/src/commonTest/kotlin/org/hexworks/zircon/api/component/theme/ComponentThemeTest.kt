package org.hexworks.zircon.api.component.theme

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.hexworks.zircon.api.ColorThemes
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

    private val neobrutalism = ComponentThemes.neobrutalism { mode = ThemeMode.LIGHT }
    private val dark = ComponentThemes.neobrutalism { mode = ThemeMode.DARK }

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
    fun switching_the_screen_theme_re_applies_and_removing_it_restores_the_original_look() {
        val screen = tileGrid { size = Size(40, 20) }.toScreen()
        val panel = panel { preferredSize = Size(10, 5) }
        screen.addComponent(panel)
        val legacyStyle = panel.componentStyleSet

        screen.componentTheme = neobrutalism
        panel.contentSize shouldBe Size(5, 2)
        screen.componentTheme = dark
        panel.componentTheme shouldBeSameInstanceAs dark
        panel.contentSize shouldBe Size(5, 2)

        screen.componentTheme = null
        panel.componentTheme shouldBe null
        panel.contentSize shouldBe Size(10, 5)
        panel.componentStyleSet shouldBe legacyStyle
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
        button.componentStyleSet shouldBe neobrutalism.resolveEntry(ComponentRole.BUTTON)!!.componentStyleSet
    }

    @Test
    fun an_explicitly_set_theme_survives_a_parent_without_one() {
        val screen = tileGrid { size = Size(40, 20) }.toScreen()
        val box = vBox {
            preferredSize = Size(20, 10)
            componentTheme = neobrutalism
        }

        screen.addComponent(box)

        box.componentTheme shouldBeSameInstanceAs neobrutalism
        // but a parent *with* a theme still wins
        screen.componentTheme = dark
        box.componentTheme shouldBeSameInstanceAs dark
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
    fun the_color_theme_is_ignored_while_a_component_theme_is_present() {
        val screen = tileGrid { size = Size(40, 20) }.toScreen()
        screen.componentTheme = neobrutalism
        val button = screen.componentFactory.button { +"OK" }
        screen.addComponent(button)
        val themed = button.componentStyleSet

        screen.theme = ColorThemes.afterglow()

        button.componentStyleSet shouldBeSameInstanceAs themed
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
        button.componentTheme = dark
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
        override val mode: ThemeMode = ThemeMode.LIGHT
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
