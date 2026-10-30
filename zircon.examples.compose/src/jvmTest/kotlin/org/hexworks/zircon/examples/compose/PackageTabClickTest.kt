package org.hexworks.zircon.examples.compose

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.ImageComposeScene
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.use
import kotlinx.coroutines.runBlocking
import org.hexworks.zircon.compose.TileGridView
import org.hexworks.zircon.examples.ExamplesSelection
import org.hexworks.zircon.examples.examplePackages
import org.hexworks.zircon.examples.examplesScreen
import org.hexworks.zircon.examples.gridSizeFor
import io.kotest.matchers.shouldBe
import kotlin.test.Test
import kotlin.test.fail

@OptIn(ExperimentalComposeUiApi::class)
/** Clicks every package tab with real pointer events in a headless Compose scene. */
class PackageTabClickTest {
    @Test
    fun clickingEveryPackageTabSelectsItWithoutErrors() {
        installResources()
        val selection = ExamplesSelection.DEFAULT
        val shown = runBlocking { examplesScreen(selection, gridSizeFor(selection.tileset.resource, 1920, 1080)) }
        val cell = 16f
        ImageComposeScene(width = 1920, height = 1080) {
            TileGridView(shown.grid, beforeFrame = shown.onFrame)
        }.use { scene ->
            var t = 0L
            fun frames(n: Int) = repeat(n) { scene.render(nanoTime = t); t += 16_000_000L }
            frames(5)
            var x = 1
            examplePackages.forEach { pkg ->
                val pos = Offset((x + 1.5f) * cell, 4.5f * cell)
                try {
                    scene.sendPointerEvent(PointerEventType.Move, pos); frames(1)
                    scene.sendPointerEvent(PointerEventType.Press, pos); frames(1)
                    scene.sendPointerEvent(PointerEventType.Release, pos); frames(10)
                    println("click ${pkg.name} -> selected=${shown.packageBar.selectedKey} example=${shown.selection.exampleName}")
                } catch (e: Throwable) {
                    fail("clicking ${pkg.name} threw $e")
                }
                shown.packageBar.selectedKey shouldBe pkg.name
                x += pkg.name.length + 2
            }
            shown.buildFailures.map { (name, e) -> "$name: ${e.message}" } shouldBe emptyList()
            shown.runtimeErrors.map { (source, e) -> "$source: $e" } shouldBe emptyList()
        }
    }
}
