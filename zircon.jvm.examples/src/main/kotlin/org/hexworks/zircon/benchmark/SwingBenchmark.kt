package org.hexworks.zircon.benchmark

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.dsl.application.appConfig
import org.hexworks.zircon.benchmark.Benchmark.Companion.BENCHMARK_SIZE
import org.hexworks.zircon.benchmark.Benchmark.Companion.BENCHMARK_TILESET

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
