package org.hexworks.zircon.examples.compose

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.ImageComposeScene
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.use
import kotlinx.coroutines.runBlocking
import org.hexworks.zircon.compose.TileGridView
import org.hexworks.zircon.examples.EXAMPLE_TAB_WIDTH
import org.hexworks.zircon.api.builder.grid.DEFAULT_TILESET
import org.hexworks.zircon.examples.EXAMPLE_GROUPS
import org.hexworks.zircon.examples.ExamplesTestApi
import org.hexworks.zircon.examples.examplesScreen
import org.hexworks.zircon.examples.gridSizeFor
import io.kotest.matchers.shouldBe
import kotlin.test.Test
import kotlin.test.fail

/** Clicks every package tab with real pointer events in a headless Compose scene. */
@OptIn(ExperimentalComposeUiApi::class, ExamplesTestApi::class)
class PackageTabClickTest {
    @Test
    fun clickingEveryPackageTabSelectsItWithoutErrors() {
        installResources()
        val shown = runBlocking { examplesScreen(gridSizeFor(DEFAULT_TILESET.size, 1920, 1080)) }
        val cell = 16f
        ImageComposeScene(width = 1920, height = 1080) {
            TileGridView(shown.grid, beforeFrame = shown.onFrame)
        }.use { scene ->
            var t = 0L
            fun frames(n: Int) = repeat(n) { scene.render(nanoTime = t); t += 16_000_000L }
            frames(5)
            var x = EXAMPLE_TAB_WIDTH + 1
            EXAMPLE_GROUPS.forEach { pkg ->
                val pos = Offset((x + 1.5f) * cell, 4.5f * cell)
                try {
                    scene.sendPointerEvent(PointerEventType.Move, pos); frames(1)
                    scene.sendPointerEvent(PointerEventType.Press, pos); frames(1)
                    scene.sendPointerEvent(PointerEventType.Release, pos); frames(10)
                    println("click ${pkg.name} -> selected=${shown.tabBar.selectedHorizontalKey} example=${shown.tabBar.selectedVerticalKey}")
                } catch (e: Throwable) {
                    fail("clicking ${pkg.name} threw $e")
                }
                shown.tabBar.selectedHorizontalKey shouldBe pkg.name
                x += pkg.name.length + 2
            }
            shown.buildFailures.map { (name, e) -> "$name: ${e.message}" } shouldBe emptyList()
            shown.runtimeErrors.map { (source, e) -> "$source: $e" } shouldBe emptyList()
        }
    }
}
