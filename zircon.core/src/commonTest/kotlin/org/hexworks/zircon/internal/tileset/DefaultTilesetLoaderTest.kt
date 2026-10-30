package org.hexworks.zircon.internal.tileset

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.hexworks.zircon.ErrorRecorder
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.data.TileType
import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.TilesetType
import org.hexworks.zircon.api.resource.base.BaseTilesetResource
import kotlin.test.AfterTest
import kotlin.test.Test

class DefaultTilesetLoaderTest {

    private val errors = ErrorRecorder()
    private val target = DefaultTilesetLoader<StringBuilder>(emptyList(), StringBuilder::class)

    @AfterTest
    fun tearDown() {
        errors.uninstall()
    }

    @Test
    fun a_resource_without_a_factory_gets_a_tileset_that_draws_nothing_and_one_report() {
        val resource = CP437TilesetResources.rogueYun16x16()

        val first = target.loadTilesetFrom(resource)
        val second = target.loadTilesetFrom(resource)

        first.shouldBeInstanceOf<NoOpTileset<StringBuilder>>()
        second shouldBe first
        first.width shouldBe 16
        errors.errors shouldHaveSize 1
        errors.sources.single() shouldBe "Tileset '${resource.path}'"
    }

    @Test
    fun can_load_is_false_for_a_resource_without_a_factory() {
        target.canLoadTileset(UnsupportedResource) shouldBe false
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
