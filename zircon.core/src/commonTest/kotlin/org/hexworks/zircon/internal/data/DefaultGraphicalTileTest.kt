package org.hexworks.zircon.internal.data

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.hexworks.zircon.api.builder.data.graphicalTile
import org.hexworks.zircon.api.data.TileType
import org.hexworks.zircon.api.data.tile.GraphicalTile
import org.hexworks.zircon.api.modifier.SimpleModifiers.Blink
import kotlin.test.Test

class DefaultGraphicalTileTest {

    @Test
    fun shouldGenerateProperCacheKey() {
        val target: GraphicalTile = graphicalTile {
            name = NAME
            tags = TAGS
        }

        target.cacheKey shouldBe "GraphicTile(n=NAME,t=[BAR, FOO])"
    }

    @Test
    fun shouldProperlyCreateCopy() {
        val target: GraphicalTile = graphicalTile {
            name = NAME
            tags = TAGS
        }

        val copy = target.createCopy()
        copy shouldBe target
        // Note: For data classes, copy() returns a structurally equal instance
    }

    @Test
    fun shouldProperlyReportTileType() {
        val target: GraphicalTile = graphicalTile {
            name = NAME
            tags = TAGS
        }

        target.tileType shouldBe TileType.GRAPHICAL_TILE
    }

    @Test
    fun shouldHaveNoModifiers() {
        val target: GraphicalTile = graphicalTile {
            name = NAME
            tags = TAGS
        }

        target.modifiers shouldBe emptySet()
    }

    @Test
    fun shouldProperlyCreateCopyWithName() {
        val otherName = "baz"
        val target = graphicalTile {
            name = NAME
            tags = TAGS
        }

        target.withName(otherName) shouldBe graphicalTile {
            name = otherName
            tags = TAGS
        }
    }

    @Test
    fun shouldProperlyCreateCopyWithTags() {
        val otherTags = setOf("qux", "fux")
        val target = graphicalTile {
            name = NAME
            tags = TAGS
        }

        target.withTags(otherTags) shouldBe graphicalTile {
            name = NAME
            tags = otherTags
        }
    }

    @Test
    fun shouldReturnNewInstanceWithModifiers() {
        val target = graphicalTile {
            name = NAME
            tags = TAGS
        }

        val withMods = target.withModifiers(setOf(Blink))
        withMods.modifiers shouldBe setOf(Blink)
    }

    companion object {

        const val NAME = "NAME"
        val TAGS = setOf("FOO", "BAR")
    }
}
