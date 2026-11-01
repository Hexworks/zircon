package org.hexworks.zircon.examples

/**
 * Marks the parts of [ExamplesScreen] that exist for the tests: they drive the tab bar and
 * read what failed. A launcher only needs the grid, the frame hook and the tile size, so
 * using one of these elsewhere is a compile error unless the file opts in with
 * `@OptIn(ExamplesTestApi::class)`.
 */
@RequiresOptIn(
    level = RequiresOptIn.Level.ERROR,
    message = "A test hook of the examples screen; launchers need only grid, onFrame and tileSizeProperty.",
)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class ExamplesTestApi
