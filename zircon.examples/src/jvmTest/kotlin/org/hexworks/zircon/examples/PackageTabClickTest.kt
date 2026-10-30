package org.hexworks.zircon.examples

import kotlinx.coroutines.runBlocking
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.ResourceLoader
import org.hexworks.zircon.api.resource.Resources
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.internal.grid.InternalTileGrid
import java.io.File
import io.kotest.matchers.shouldBe
import kotlin.test.Test
import kotlin.test.fail

/** Clicks every package tab with synthetic mouse events, at two grid sizes. */
class PackageTabClickTest {
    @Test
    fun clickingEveryPackageTabSelectsItWithoutErrors() {
        Resources.loader = ResourceLoader { resource: Resource ->
            File("resources/files/" + resource.path.trimStart('/')).readBytes()
        }
        for (size in listOf(Size(100, 40), Size(120, 67))) {
            val shown = runBlocking { examplesScreen(ExamplesSelection.DEFAULT, size) }
            val grid = shown.grid as InternalTileGrid
            // package tabs: x = 1 + sum(previous label lengths + 2), y = 4
            var x = 1
            examplePackages.forEach { pkg ->
                val pos = Position(x + 1, 4)
                try {
                    listOf(MouseEventType.MOUSE_MOVED, MouseEventType.MOUSE_PRESSED, MouseEventType.MOUSE_RELEASED, MouseEventType.MOUSE_CLICKED).forEach { type ->
                        grid.process(MouseEvent(type, 1, pos), UIEventPhase.TARGET)
                    }
                    shown.onFrame()
                    println("click $size ${pkg.name} at $pos -> selected=${shown.packageBar.selectedKey} example=${shown.selection.exampleName}")
                } catch (e: Throwable) {
                    fail("clicking ${pkg.name} at $pos in a $size grid threw $e")
                }
                shown.packageBar.selectedKey shouldBe pkg.name
                x += pkg.name.length + 2
            }
            shown.buildFailures.map { (name, e) -> "$name: ${e.message}" } shouldBe emptyList()
        }
    }
}
