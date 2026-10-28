package org.hexworks.zircon.api.tileset

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.animation.AnimationFrameRef
import org.hexworks.zircon.api.parseAnimationMetadata
import kotlin.test.Test

class GraphicalTilesetMetadataTest {

    @Test
    fun parsesTilesetMetadataWithDefaults() {
        val metadata = parseGraphicalTilesetMetadata(
            """
            {
              "name": "Nethack",
              "tileSize": 16,
              "files": [
                { "name": "tiles.png", "tilesPerRow": 40, "tiles": [
                  { "name": "Giant ant", "tags": ["animal"], "char": "a", "description": "It is big." },
                  { "name": "Killer bee" }
                ] }
              ]
            }
            """.trimIndent()
        )

        metadata.name shouldBe "Nethack"
        metadata.tileSize shouldBe 16
        metadata.files.single().tiles[0].tags shouldBe setOf("animal")
        metadata.files.single().tiles[1].tags shouldBe setOf("killer", "bee")
        metadata.files.single().tiles[1].char shouldBe "K"
        metadata.files.single().tiles[1].description shouldBe ""
    }

    @Test
    fun parsesAnimationMetadataWithDefaults() {
        val metadata = parseAnimationMetadata(
            """{ "frameRate": 15, "loopCount": 0, "baseName": "skull-", "frames": [ { "frame": 0, "repeatCount": 9 }, { "frame": 1 } ] }"""
        )

        metadata.frameRate shouldBe 15
        metadata.loopCount shouldBe 0
        metadata.extension shouldBe "xp"
        metadata.frames shouldBe listOf(AnimationFrameRef(0, 9), AnimationFrameRef(1, 1))
    }
}
