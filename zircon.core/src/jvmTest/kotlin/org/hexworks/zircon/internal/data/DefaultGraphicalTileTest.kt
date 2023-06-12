package org.hexworks.zircon.internal.data

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.data.graphicalTile
import org.hexworks.zircon.api.color.ANSITileColor.GREEN
import org.hexworks.zircon.api.color.ANSITileColor.YELLOW
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.GraphicalTile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.SimpleModifiers.Blink
import org.hexworks.zircon.internal.resource.TileType
import org.junit.Test

class DefaultGraphicalTileTest {

    @Test
    fun shouldGenerateProperCacheKey() {
        val target: GraphicalTile = graphicalTile {
            name = NAME
            tags = TAGS
        }

        assertThat(target.cacheKey).isEqualTo("GraphicTile(n=NAME,t=[BAR, FOO])")
    }

    @Test
    fun shouldProperlyCreateCopy() {
        val target: GraphicalTile = graphicalTile {
            name = NAME
            tags = TAGS
        }

        assertThat(target.createCopy())
            .isEqualTo(target)
            .isNotSameAs(target)
    }

    @Test
    fun shouldProperlyReportTileType() {
        val target: GraphicalTile = graphicalTile {
            name = NAME
            tags = TAGS
        }

        assertThat(target.tileType).isEqualTo(TileType.GRAPHICAL_TILE)
    }

    @Test
    fun shouldHaveTransparentFGColor() {
        val target: GraphicalTile = graphicalTile {
            name = NAME
            tags = TAGS
        }

        assertThat(target.foregroundColor).isEqualTo(TileColor.transparent())
    }

    @Test
    fun shouldHaveTransparentBGColor() {
        val target: GraphicalTile = graphicalTile {
            name = NAME
            tags = TAGS
        }

        assertThat(target.backgroundColor).isEqualTo(TileColor.transparent())
    }

    @Test
    fun shouldHaveNoModifiers() {
        val target: GraphicalTile = graphicalTile {
            name = NAME
            tags = TAGS
        }

        assertThat(target.modifiers).isEmpty()
    }

    @Test
    fun shouldHaveTheEmptyStyleSet() {
        val target: GraphicalTile = graphicalTile {
            name = NAME
            tags = TAGS
        }

        assertThat(target.styleSet).isSameAs(StyleSet.empty())
    }

    @Test
    fun shouldProperlyCreateCopyWithName() {
        val otherName = "baz"
        val target = graphicalTile {
            name = NAME
            tags = TAGS
        }

        assertThat(target.withName(otherName))
            .isEqualTo(graphicalTile {
                name = otherName
                tags = TAGS
            })
    }

    @Test
    fun shouldProperlyCreateCopyWithTags() {
        val otherTags = setOf("qux", "fux")
        val target = graphicalTile {
            name = NAME
            tags = TAGS
        }

        assertThat(target.withTags(otherTags))
            .isEqualTo(graphicalTile {
                name = NAME
                tags = otherTags
            })
    }

    @Test
    fun shouldReturnItselfForAllStyleCopiers() {
        val target = graphicalTile {
            name = NAME
            tags = TAGS
        }

        assertThat(target.withForegroundColor(YELLOW)).isSameAs(target)
        assertThat(target.withBackgroundColor(GREEN)).isSameAs(target)
        assertThat(target.withStyle(StyleSet.defaultStyle())).isSameAs(target)
        assertThat(target.withModifiers(setOf(Blink))).isSameAs(target)
        assertThat(target.withAddedModifiers(Blink)).isSameAs(target)
        assertThat(target.withRemovedModifiers(Blink)).isSameAs(target)
        assertThat(target.withRemovedModifiers(setOf(Blink))).isSameAs(target)
        assertThat(target.withNoModifiers()).isSameAs(target)
    }

    companion object {

        const val NAME = "NAME"
        val TAGS = setOf("FOO", "BAR")
    }
}
