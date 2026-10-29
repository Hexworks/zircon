package org.hexworks.zircon.examples

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.hexworks.zircon.api.data.Size
import kotlin.test.Test

class ExamplesScreenTest {

    @Test
    fun benchmarkFillsAFullHdWindowWith8x8Tiles() {
        gridSizeFor(benchmarkDemo.tileset, FULL_HD_WIDTH, FULL_HD_HEIGHT) shouldBe Size(240, 135)
    }

    @Test
    fun selectingAnotherTabReportsIt() {
        var selected: Demo? = null
        val shown = runBlocking { examplesScreen(componentsDemo, Size(120, 40)) { selected = it } }
        shown.tabBar.selectedKey = benchmarkDemo.name
        selected shouldBe benchmarkDemo
    }

    @Test
    fun theTerminalLauncherSkipsGraphicalDemos() {
        demos.filter { it.textOnly }.map { it.name } shouldContainExactly listOf("Components", "REXPaint", "Animation", "Benchmark")
    }

    companion object {
        const val FULL_HD_WIDTH = 1920
        const val FULL_HD_HEIGHT = 1080
    }
}
