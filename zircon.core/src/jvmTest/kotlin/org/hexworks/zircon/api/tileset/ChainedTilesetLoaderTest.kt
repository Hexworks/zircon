package org.hexworks.zircon.api.tileset

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.hexworks.zircon.api.resource.TilesetResource
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.*
import org.mockito.quality.Strictness

/*
 This is a very rigid set of test cases. Normally this is an anti-pattern, but we want to verify very specific behavior
 here. Exactly these methods are called in exactly this particular order.
 */
class ChainedTilesetLoaderTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS)

    @Mock(name = "loaderA")
    lateinit var loaderA: TilesetLoader<Any>

    @Mock(name = "loaderB")
    lateinit var loaderB: TilesetLoader<Any>

    @Mock(name = "loaderC")
    lateinit var loaderC: TilesetLoader<Any>

    @Test
    fun firstLoaderCanLoad() {
        val mockTilesetResource = mock<TilesetResource>()
        val mockTileset = mock<Tileset<Any>>()
        whenever(loaderA.canLoadResource(any())).thenReturn(true)
        whenever(loaderA.loadTilesetFrom(any())).thenReturn(mockTileset)

        ChainedTilesetLoader.inOrder(loaderA, loaderB).also { chained ->
            assertThat(chained.loadTilesetFrom(mockTilesetResource)).isSameAs(mockTileset)
        }

        verify(loaderA).canLoadResource(mockTilesetResource)
        verify(loaderA).loadTilesetFrom(mockTilesetResource)
    }

    @Test
    fun secondLoaderCanLoad() {
        val mockTilesetResource = mock<TilesetResource>()
        val mockTileset = mock<Tileset<Any>>()
        whenever(loaderA.canLoadResource(any())).thenReturn(false)
        whenever(loaderB.canLoadResource(any())).thenReturn(true)
        whenever(loaderB.loadTilesetFrom(any())).thenReturn(mockTileset)

        ChainedTilesetLoader.inOrder(loaderA, loaderB).also { chained ->
            assertThat(chained.loadTilesetFrom(mockTilesetResource)).isSameAs(mockTileset)
        }

        inOrder(loaderA, loaderB) {
            verify(loaderA).canLoadResource(mockTilesetResource)
            verify(loaderB).canLoadResource(mockTilesetResource)
            verify(loaderB).loadTilesetFrom(mockTilesetResource)
        }
    }

    @Test
    fun neitherCanLoad() {
        val mockTilesetResource = mock<TilesetResource>()
        whenever(loaderA.canLoadResource(any())).thenReturn(false)
        whenever(loaderB.canLoadResource(any())).thenReturn(false)

        ChainedTilesetLoader.inOrder(loaderA, loaderB).also { chained ->
            assertThatThrownBy {
                chained.loadTilesetFrom(mockTilesetResource)
            }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessageContaining("Unknown tile type")
        }

        verify(loaderA).canLoadResource(mockTilesetResource)
        verify(loaderB).canLoadResource(mockTilesetResource)
    }

    @Test
    fun canLoadResourceA() {
        val mockTilesetResource = mock<TilesetResource>()
        whenever(loaderA.canLoadResource(any())).thenReturn(true)

        ChainedTilesetLoader.inOrder(loaderA, loaderB).also { chained ->
            assertThat(chained.canLoadResource(mockTilesetResource)).isTrue()
        }

        verify(loaderA).canLoadResource(mockTilesetResource)
    }

    @Test
    fun canLoadResourceB() {
        val mockTilesetResource = mock<TilesetResource>()
        whenever(loaderA.canLoadResource(any())).thenReturn(false)
        whenever(loaderB.canLoadResource(any())).thenReturn(true)

        ChainedTilesetLoader.inOrder(loaderA, loaderB).also { chained ->
            assertThat(chained.canLoadResource(mockTilesetResource)).isTrue()
        }

        inOrder(loaderA, loaderB) {
            verify(loaderA).canLoadResource(mockTilesetResource)
            verify(loaderB).canLoadResource(mockTilesetResource)
        }
    }

    @Test
    fun canLoadResourceC() {
        val mockTilesetResource = mock<TilesetResource>()
        whenever(loaderA.canLoadResource(any())).thenReturn(false)
        whenever(loaderB.canLoadResource(any())).thenReturn(false)

        ChainedTilesetLoader.inOrder(loaderA, loaderB).also { chained ->
            assertThat(chained.canLoadResource(mockTilesetResource)).isFalse()
        }

        verify(loaderA).canLoadResource(mockTilesetResource)
        verify(loaderB).canLoadResource(mockTilesetResource)
    }

    @Test
    fun inOrder_empty() {
        assertThatThrownBy {
            ChainedTilesetLoader.inOrder(emptyList<TilesetLoader<Any>>())
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("cannot be empty")
    }

    @Test
    fun inOrder_single() {
        // This is a little odd, but really why have chaining at all if you only have a single loader anyway?
        assertThat(ChainedTilesetLoader.inOrder(listOf(loaderA)))
            .isSameAs(loaderA)
    }

    @Test
    fun inOrder_double() {
        val chained = ChainedTilesetLoader.inOrder(listOf(loaderA, loaderB))

        val mockTilesetResource = mock<TilesetResource>()
        val mockTileset = mock<Tileset<Any>>()
        whenever(loaderA.canLoadResource(any())).thenReturn(false)
        whenever(loaderB.canLoadResource(any())).thenReturn(true)
        whenever(loaderB.loadTilesetFrom(any())).thenReturn(mockTileset)

        assertThat(chained.loadTilesetFrom(mockTilesetResource)).isSameAs(mockTileset)

        inOrder(loaderA, loaderB) {
            verify(loaderA).canLoadResource(mockTilesetResource)
            verify(loaderB).canLoadResource(mockTilesetResource)
            verify(loaderB).loadTilesetFrom(mockTilesetResource)
        }
    }

    @Test
    fun inOrder_triple() {
        val chained = ChainedTilesetLoader.inOrder(listOf(loaderA, loaderB, loaderC))

        val mockTilesetResource = mock<TilesetResource>()
        val mockTileset = mock<Tileset<Any>>()
        whenever(loaderA.canLoadResource(any())).thenReturn(false)
        whenever(loaderB.canLoadResource(any())).thenReturn(false)
        whenever(loaderC.canLoadResource(any())).thenReturn(true)
        whenever(loaderC.loadTilesetFrom(any())).thenReturn(mockTileset)

        assertThat(chained.loadTilesetFrom(mockTilesetResource)).isSameAs(mockTileset)

        inOrder(loaderA, loaderB, loaderC) {
            verify(loaderA).canLoadResource(mockTilesetResource)
            verify(loaderB).canLoadResource(mockTilesetResource)
            verify(loaderC).canLoadResource(mockTilesetResource)
            verify(loaderC).loadTilesetFrom(mockTilesetResource)
        }
    }
}
