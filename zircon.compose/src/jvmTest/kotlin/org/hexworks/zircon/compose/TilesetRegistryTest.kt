package org.hexworks.zircon.compose

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.test.runTest
import org.hexworks.zircon.api.builder.config.zirconDefaults
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.config.ErrorHandler
import org.hexworks.zircon.api.config.ZirconDefaults
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.TileType
import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.TilesetType
import org.hexworks.zircon.api.resource.base.BaseTilesetResource
import org.hexworks.zircon.compose.tileset.PlaceholderTileset
import org.hexworks.zircon.compose.tileset.TilesetRegistry
import kotlin.test.AfterTest
import kotlin.test.Test

class TilesetRegistryTest {

    private val previousDefaults = ZirconDefaults.current
    private val errors = mutableListOf<String>()
    private val target = TilesetRegistry()

    init {
        zirconDefaults { onError = ErrorHandler { source, _ -> errors += source } }
    }

    @AfterTest
    fun tearDown() {
        ZirconDefaults.current = previousDefaults
    }

    @Test
    fun a_resource_without_a_factory_gets_a_placeholder_tileset_and_one_report() = runTest {
        val first = target.loadTilesetFrom(UnsupportedResource)
        val second = target.loadTilesetFrom(UnsupportedResource)

        first.shouldBeInstanceOf<PlaceholderTileset>()
        second shouldBe first
        target.loadPending() shouldBe true
        errors shouldHaveSize 1
        errors.single() shouldBe "Tileset 'unsupported'"
    }

    @Test
    fun the_placeholder_tileset_draws_a_marked_cell() {
        val frame = FrameData(1, 1)

        target.loadTilesetFrom(UnsupportedResource).drawTile(characterTile { character = 'a' }, frame, Position(0, 0))

        val expected = PlaceholderTileset.MISSING_TILE_COLOR
        frame.backgrounds.toList() shouldBe listOf(expected.red.toByte(), expected.green.toByte(), expected.blue.toByte(), (-1).toByte())
    }

    private object UnsupportedResource : BaseTilesetResource() {
        override val width: Int = 8
        override val height: Int = 8
        override val path: String = "unsupported"
        override val resourceType: ResourceType = ResourceType.FILESYSTEM
        override val tileType: TileType = TileType.CHARACTER_TILE
        override val tilesetType: TilesetType = TilesetType.CustomTileset("unsupported")
    }
}
