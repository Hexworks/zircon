package org.codetome.zircon.benchmark

import org.codetome.zircon.Position
import org.codetome.zircon.builder.FontRendererBuilder
import org.codetome.zircon.builder.TerminalBuilder
import org.codetome.zircon.terminal.Size
import org.codetome.zircon.tileset.DFTilesetResource
import org.codetome.zircon.util.Stats

fun main(args:Array<String>) {
    val terminal = TerminalBuilder.newBuilder()
            .initialTerminalSize(SIZE)
            .fontRenderer(FontRendererBuilder.newBuilder()
                    .useSwing()
//                    .usePhysicalFonts()
                    .useDFTileset(DFTilesetResource.WANDERLUST_16X16)
                    .build())
            .buildTerminal()

    val charCount = 60 * 30
    val chars = listOf('a', 'b')
    var currIdx = 0
    var loopCount = 0
    while(true) {
        Stats.addTimedStatFor("terminalBenchmark") {
            (0..charCount).forEach {
                terminal.putCharacter(chars[currIdx])
            }
            terminal.flush()
            terminal.setCursorPosition(Position.DEFAULT_POSITION)
            currIdx = if(currIdx == 0) 1 else 0
            loopCount++
        }
        if(loopCount.rem(100) == 0) {
            Stats.printStats()
        }
    }
}

val SIZE = Size(60, 30)