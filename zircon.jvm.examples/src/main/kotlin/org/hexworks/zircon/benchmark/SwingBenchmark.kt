package org.hexworks.zircon.benchmark

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.benchmark.Benchmark.Companion.BENCHMARK_SIZE
import org.hexworks.zircon.benchmark.Benchmark.Companion.BENCHMARK_TILESET

object SwingBenchmark {

    @JvmStatic
    fun main(args: Array<String>) {
        Benchmark().execute(SwingApplications.startTileGrid(AppConfigBuilder.newBuilder()
                .withSize(BENCHMARK_SIZE)
                .withDefaultTileset(BENCHMARK_TILESET)
                .withDebugMode(true)
                .build()))

    }
}
