package org.hexworks.zircon.api

import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.tileset.loadGraphicalTileset
import kotlin.test.Test

/**
 * Loads the bundled archives (the nethack graphical tileset and the skull animation)
 * through the in-house zip and JSON code paths.
 */
class ArchiveResourcesTest {

    private fun resource(path: String): ByteArray =
        checkNotNull(javaClass.getResourceAsStream(path)) { "$path missing" }.use { it.readBytes() }

    @Test
    fun loadsTheBundledNethackTileset() {
        val tileset = loadGraphicalTileset(resource("/graphical_tilesets/nethack_16x16.zip"))

        tileset.metadata.name shouldBe "Nethack"
        tileset.metadata.tileSize shouldBe 16
        tileset.tileNames shouldContain "Giant ant"
        val bee = checkNotNull(tileset.textureFor("Killer bee"))
        bee.x shouldBe 1
        bee.y shouldBe 0
        bee.file shouldBe "tiles.png"
        tileset.imageBytes("tiles.png").size shouldBeGreaterThan 1000
    }

    @Test
    fun loadsTheSkullAnimation() {
        val animation = loadAnimation(resource("/animations/skull.zap"), CP437TilesetResources.aduDhabi16x16()).build()

        animation.tick.inWholeMilliseconds shouldBe 1000L / 15
        animation.loopKind shouldBe Animation.FiniteLoop(1)
        animation.totalFrameCount shouldBeGreaterThan 20
    }
}
