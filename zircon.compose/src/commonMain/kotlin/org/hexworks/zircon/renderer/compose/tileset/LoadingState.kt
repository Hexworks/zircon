package org.hexworks.zircon.renderer.compose.tileset

/**
 * Represents the loading state of a tileset resource.
 * Used for lazy loading of tileset assets.
 */
enum class LoadingState {
    NOT_LOADED,
    LOADING,
    LOADED
}
