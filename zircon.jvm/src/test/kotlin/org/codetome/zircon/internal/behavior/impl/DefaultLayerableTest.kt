package org.codetome.zircon.internal.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.builder.data.TileBuilder
import org.codetome.zircon.api.builder.graphics.LayerBuilder
import org.codetome.zircon.api.builder.graphics.TileImageBuilder
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.internal.tileset.impl.TilesetLoaderRegistry
import org.codetome.zircon.internal.tileset.impl.TestTilesetLoader
import org.junit.Before
import org.junit.Test

class DefaultLayerableTest {

    lateinit var target: DefaultLayerable
    lateinit var tileset: Tileset

    @Before
    fun setUp() {
        TilesetLoaderRegistry.setFontLoader(TestTilesetLoader())
        tileset = FONT.toFont()
        target = DefaultLayerable(
                size = SIZE,
                supportedFontSize = tileset.getSize())
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenLayerUsesUnsupportedFontSize() {
        val layer = LayerBuilder.newBuilder()
                .size(Size.one())
                .font(CP437TilesetResource.BISASAM_20X20.toFont())
                .offset(Position.topLeftCorner())
                .build()


        target.pushLayer(layer)
    }

    @Test
    fun shouldContainLayerWhenLayerIsAdded() {
        val layer = LayerBuilder.newBuilder()
                .size(Size.one())
                .offset(Position.topLeftCorner())
                .build()

        target.pushLayer(layer)

        assertThat(target.fetchOverlayZIntersection(Position.topLeftCorner()))
                .isNotEmpty

    }

    @Test
    fun shouldNotContainLayerWhenLayerIsAddedThenRemoved() {
        val layer = LayerBuilder.newBuilder()
                .size(Size.one())
                .offset(Position.topLeftCorner())
                .build()

        target.pushLayer(layer)
        target.removeLayer(layer)

        assertThat(target.fetchOverlayZIntersection(Position.topLeftCorner()))
                .isEmpty()

    }

    @Test
    fun shouldNotContainLayerWhenLayerIsAddedThenPopped() {
        val layer = LayerBuilder.newBuilder()
                .size(Size.one())
                .offset(Position.topLeftCorner())
                .build()

        target.pushLayer(layer)
        val result = target.popLayer()

        assertThat(target.fetchOverlayZIntersection(Position.topLeftCorner()))
                .isEmpty()
        assertThat(result.get()).isSameAs(layer)

    }

    @Test
    fun shouldContainBottomLayerOnlyWhenTwoLayersAreAddedAndTopDoesNotIntersectCoordinate() {
        val expectedChar = TileBuilder.newBuilder()
                .character('1')
                .build()

        val offset1x1layer = LayerBuilder.newBuilder()
                .size(Size.one())
                .offset(Position.offset1x1())
                .build()
                .fill(expectedChar)
        val offset2x2layer = LayerBuilder.newBuilder()
                .size(Size.one())
                .offset(Position.create(2, 2))
                .build()
                .fill(TileBuilder.newBuilder()
                        .character('2')
                        .build())


        target.pushLayer(offset1x1layer)
        target.pushLayer(offset2x2layer)

        val result = target.fetchOverlayZIntersection(Position.offset1x1())


        assertThat(result.map { it.second }).containsExactly(expectedChar)
    }

    @Test
    fun shouldProperlyMarkDrainedLayerPositionsDirtyWhenLayersAreDrained() {
        val dirty0 = Position.create(1, 2)
        val dirty1 = Position.create(3, 4)

        target.pushLayer(LayerBuilder.newBuilder()
                .offset(dirty0)
                .textImage(TileImageBuilder.newBuilder()
                        .size(Size.one())
                        .tile(Position.defaultPosition(), Tile.defaultTile().withCharacter('x'))
                        .build())
                .build())

        target.pushLayer(LayerBuilder.newBuilder()
                .offset(dirty1)
                .textImage(TileImageBuilder.newBuilder()
                        .size(Size.one())
                        .tile(Position.defaultPosition(), Tile.defaultTile().withCharacter('x'))
                        .build())
                .build())

        target.drainLayers()
        assertThat(target.drainDirtyPositions())
                .containsExactlyInAnyOrder(dirty0, dirty1)
    }

    @Test
    fun shouldProperlyNotMarkDrainedLayerPositionsDirtyWhenTheyAreEmpty() {
        val dirty0 = Position.create(1, 2)
        val dirty1 = Position.create(3, 4)

        target.pushLayer(LayerBuilder.newBuilder()
                .offset(dirty0)
                .build())

        target.pushLayer(LayerBuilder.newBuilder()
                .offset(dirty1)
                .build())

        target.drainLayers()
        assertThat(target.drainDirtyPositions()).isEmpty()
    }

    @Test
    fun shouldContainAllLayersWhenTwoLayersAreAddedAndTheyIntersect() {
        val expectedChar = TileBuilder.newBuilder()
                .character('1')
                .build()

        val offset1x1layer = LayerBuilder.newBuilder()
                .size(Size.one())
                .offset(Position.offset1x1())
                .build()
                .fill(expectedChar)

        val offset2x2layer = LayerBuilder.newBuilder()
                .size(Size.one())
                .offset(Position.offset1x1())
                .build()
                .fill(expectedChar)

        target.pushLayer(offset1x1layer)
        target.pushLayer(offset2x2layer)

        val result = target.fetchOverlayZIntersection(Position.offset1x1())


        assertThat(result.map { it.second }).containsExactly(expectedChar, expectedChar)
    }

    companion object {
        val SIZE = Size.create(10, 10)
        val FONT = CP437TilesetResource.WANDERLUST_16X16
    }
}
