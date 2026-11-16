package org.hexworks.zircon.api.component.theme

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.ComponentThemes
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.fragment.AlertKind
import org.hexworks.zircon.api.fragment.BadgeStatus
import org.hexworks.zircon.internal.component.InternalComponent
import kotlin.test.Test

class NeobrutalismRenderTest {

    private val theme = ComponentThemes.neobrutalism { mode = ThemeMode.LIGHT }
    private val factory = theme.componentFactory

    @Test
    fun a_button_is_a_heavy_box_with_the_offset_shadow() {
        val button = factory.button { +"OK" }

        button.renderToRows() shouldContainExactly listOf(
            "┏━━━━┓_",
            "┃ OK ┃▌",
            "┗━━━━┛▌",
            "_▀▀▀▀▀▘",
        )
    }

    @Test
    fun a_hovered_button_shifts_right_and_drops_the_shadow() {
        val button = factory.button { +"OK" }
        (button as InternalComponent).componentState = ComponentState.HIGHLIGHTED

        button.renderToRows() shouldContainExactly listOf(
            "_┏━━━━┓",
            "_┃ OK ┃",
            "_┗━━━━┛",
            "_______",
        )
    }

    @Test
    fun a_secondary_button_keeps_its_shadow_when_hovered() {
        val button = factory.button {
            +"OK"
            role = ComponentRole.BUTTON_SECONDARY
        }
        (button as InternalComponent).componentState = ComponentState.HIGHLIGHTED

        button.renderToRows()[1] shouldBe "┃ OK ┃▌"
    }

    @Test
    fun a_primary_button_has_a_double_border_and_no_shadow() {
        val button = factory.button {
            +"Save"
            role = ComponentRole.BUTTON_PRIMARY
        }

        button.renderToRows() shouldContainExactly listOf(
            "╔══════╗",
            "║ Save ║",
            "╚══════╝",
        )
    }

    @Test
    fun a_link_button_is_underlined() {
        val button = factory.button {
            +"More"
            role = ComponentRole.BUTTON_LINK
        }

        button.renderToRows() shouldContainExactly listOf(
            " More ",
            "▂▂▂▂▂▂",
        )
    }

    @Test
    fun a_check_box_is_one_cell_and_inverts_when_checked() {
        val checkBox = factory.checkBox { +"Opt" }

        checkBox.renderToRows()[0] shouldBe "☐  Opt "
        checkBox.isSelected = true
        checkBox.renderToRows()[0] shouldBe "✓  Opt "
    }

    @Test
    fun a_progress_bar_ends_on_a_half_cell() {
        val bar = factory.progressBar {
            range = 100
            numberOfSteps = 10
            preferredContentSize = org.hexworks.zircon.api.data.Size(10, 1)
        }
        bar.progress = 25.0

        // 25% of 10 cells is 2.5: two filled cells and a half block.
        bar.renderToRows()[1] shouldBe "┃   ▌        ┃▎"
    }

    @Test
    fun an_alert_wraps_its_text_next_to_the_icon() {
        val alert = factory.alert {
            width = 20
            kind = AlertKind.INFO
            +"Lorem ipsum dolor sit"
        }

        alert.root.renderToRows() shouldContainExactly listOf(
            "┏━━━━━━━━━━━━━━━━━┓_",
            "┃ ⓘ Lorem ipsum   ┃▌",
            "┃   dolor sit     ┃▌",
            "┗━━━━━━━━━━━━━━━━━┛▌",
            "_▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘",
        )
    }

    @Test
    fun a_badge_has_the_thin_shadow_and_a_status_dot() {
        val badge = factory.badge {
            +"Error"
            status = BadgeStatus.ERROR
        }

        badge.root.renderToRows() shouldContainExactly listOf(
            "┏━━━━━━━━━┓_",
            "┃ ■ Error ┃▎",
            "┗━━━━━━━━━┛▎",
            "_▔▔▔▔▔▔▔▔▔▔_",
        )
    }

    @Test
    fun a_window_has_a_full_shadow_and_a_divider_under_the_title_bar() {
        val window = factory.window {
            width = 20
            title = "SYSTEM"
            header = "Retro"
            body = "Hello"
        }

        val rows = window.root.renderToRows()
        rows[0] shouldBe "┏━━━━━━━━━━━━━━━━━┓_"
        rows[1] shouldBe "┃ SYSTEM      □ □ ┃█"
        rows[2] shouldBe "┣━━━━━━━━━━━━━━━━━┫█"
        rows[3] shouldBe "┃ Retro           ┃█"
        rows[5] shouldBe "┃ Hello           ┃█"
        rows.last() shouldBe "_███████████████████"
    }

    @Test
    fun a_horizontal_tab_bar_draws_the_line_with_joints_under_the_selected_tab() {
        val tabBar = factory.horizontalTabBar {
            size = org.hexworks.zircon.api.data.Size(30, 6)
            tab { key = "a"; label = "One"; content = factory.panel { role = ComponentRole.NONE; preferredSize = org.hexworks.zircon.api.data.Size(10, 3) } }
            tab { key = "b"; label = "Two"; content = factory.panel { role = ComponentRole.NONE; preferredSize = org.hexworks.zircon.api.data.Size(10, 3) } }
        }

        val rows = tabBar.root.renderToRows()
        rows[0].take(10) shouldBe "┏━━━┓     "
        rows[1].take(10) shouldBe "┃One┃ Two "
        rows[2].take(10) shouldBe "┛   ┗━━━━━"
    }
}
