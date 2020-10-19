package org.hexworks.zircon.benchmark

import org.hexworks.zircon.api.VirtualApplications
import org.hexworks.zircon.api.builder.application.AppConfigBuilder

object VirtualBenchmark {

    @JvmStatic
    fun main(args: Array<String>) {
        Benchmark().execute(VirtualApplications.startTileGrid(AppConfigBuilder.newBuilder()
                .withSize(Benchmark.BENCHMARK_SIZE)
                .withDefaultTileset(Benchmark.BENCHMARK_TILESET)
                .withDebugMode(true)
                .build()))
    }

}
