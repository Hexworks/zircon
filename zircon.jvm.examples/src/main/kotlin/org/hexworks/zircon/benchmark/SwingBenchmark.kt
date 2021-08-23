package org.hexworks.zircon.benchmark

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.dsl.application.appConfig
import org.hexworks.zircon.api.dsl.application.tilesetFactory
import org.hexworks.zircon.api.dsl.tileset.buildTilesetFactory
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.benchmark.Benchmark.Companion.BENCHMARK_SIZE
import org.hexworks.zircon.benchmark.Benchmark.Companion.BENCHMARK_TILESET
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.resource.TilesetType
import org.hexworks.zircon.internal.tileset.ImageLoader
import org.hexworks.zircon.internal.tileset.Java2DCP437Tileset
import java.awt.Graphics2D

object SwingBenchmark {

    @JvmStatic
    fun main(args: Array<String>) {
        Benchmark().execute(
            SwingApplications.startTileGrid(
                appConfig {
                    size = BENCHMARK_SIZE
                    defaultTileset = BENCHMARK_TILESET
                    debugMode = true
                }
            )
        )

    }
}
