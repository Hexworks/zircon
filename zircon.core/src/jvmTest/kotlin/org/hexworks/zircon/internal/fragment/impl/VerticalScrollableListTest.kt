package org.hexworks.zircon.internal.fragment.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.builder.graphics.withSize
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.convertCharacterTilesToString
import org.hexworks.zircon.internal.component.renderer.DefaultVerticalScrollBarRenderer
import org.hexworks.zircon.internal.renderer.TestRenderer
import org.junit.Ignore
import org.junit.Test

@Ignore
class VerticalScrollableListTest {
    private val graphics: TileGraphics = tileGraphics {
        withSize {
            width = 8
            height = 10
        }
    }

    private val scrollableListFragment = VerticalScrollableList(
        graphics.size,
        Position.zero(),
        (1..20).map { "Item $it" },
        scrollbarRenderer = TestScrollbarRendererDefault
    )
    private val testRenderer = TestRenderer(AppConfig.defaultAppConfig(), graphics).apply {
        withComponentContainer {
            addFragment(scrollableListFragment)
        }
    }

    @Test
    fun rendersCorrectly() {
        testRenderer.render("")
        assertThat(graphics.convertCharacterTilesToString()).isEqualTo(
            """
            Item 1 ▲
            Item 2 +
            Item 3 +
            Item 4 +
            Item 5 +
            Item 6 |
            Item 7 |
            Item 8 |
            Item 9 |
            Item 10▼
            """.trimIndent()
        )
    }

    @Test
    fun scrollsCorrectly() {
        testRenderer.render("")
        assertThat(graphics.convertCharacterTilesToString()).isEqualTo(
            """
            Item 1 ▲
            Item 2 +
            Item 3 +
            Item 4 +
            Item 5 +
            Item 6 |
            Item 7 |
            Item 8 |
            Item 9 |
            Item 10▼
            """.trimIndent()
        )
        testRenderer.dispatch(TAB) // top arrow
        testRenderer.dispatch(TAB) // scrollbar
        testRenderer.dispatch(TAB) // bottom arrow
        assertThat(testRenderer.dispatch(SPACE)).isEqualTo(Processed)
        testRenderer.render("")
        assertThat(graphics.convertCharacterTilesToString()).isEqualTo(
            """
            Item 2 ▲
            Item 3 +
            Item 4 +
            Item 5 +
            Item 6 +
            Item 7 |
            Item 8 |
            Item 9 |
            Item 10|
            Item 11▼
            """.trimIndent()
        )
    }

    @Test
    fun scrollsToBottomCorrectly() {
        testRenderer.dispatch(TAB) // top arrow
        testRenderer.dispatch(TAB) // scrollbar
        testRenderer.dispatch(TAB) // bottom arrow
        repeat(20) {
            testRenderer.dispatch(SPACE)
        }
        testRenderer.render("")
        assertThat(graphics.convertCharacterTilesToString()).isEqualTo(
            """
            Item 11▲
            Item 12|
            Item 13|
            Item 14|
            Item 15|
            Item 16+
            Item 17+
            Item 18+
            Item 19+
            Item 20▼
            """.trimIndent()
        )
    }

    @Test
    fun canScrollByMousePressingEmptyBar() {
        val pos = Position.create(7, 6)
        testRenderer.dispatch(
            MouseEvent(
                MouseEventType.MOUSE_PRESSED,
                1,
                pos
            )
        )
        testRenderer.render("")
        assertThat(graphics.convertCharacterTilesToString()).isEqualTo(
            """
            Item 4 ▲
            Item 5 |
            Item 6 +
            Item 7 +
            Item 8 +
            Item 9 +
            Item 10|
            Item 11|
            Item 12|
            Item 13▼
            """.trimIndent()
        )
    }

    @Test
    fun canScrollUsingMouseWheel() {
        testRenderer.dispatch(
            MouseEvent(
                MouseEventType.MOUSE_WHEEL_ROTATED_DOWN,
                1,
                Position.create(7, 1)
            )
        )
        testRenderer.render("")
        assertThat(graphics.convertCharacterTilesToString()).isEqualTo(
            """
            Item 4 ▲
            Item 5 |
            Item 6 +
            Item 7 +
            Item 8 +
            Item 9 +
            Item 10|
            Item 11|
            Item 12|
            Item 13▼
            """.trimIndent()
        )
    }
}

private val SPACE = KeyboardEvent(
    type = KeyboardEventType.KEY_PRESSED,
    code = KeyCode.SPACE,
    key = " "
)

private val TAB = KeyboardEvent(
    type = KeyboardEventType.KEY_PRESSED,
    key = "\t",
    code = KeyCode.TAB
)

private object TestScrollbarRendererDefault : DefaultVerticalScrollBarRenderer() {
    override val aboveBarCharacter = '|'
    override val belowBarCharacter = '|'
    override val barCharacter = '+'
}
