package org.codetome.zircon.benchmark

import org.codetome.zircon.Position
import org.codetome.zircon.builder.TerminalBuilder
import org.codetome.zircon.builder.TextColorFactory
import org.codetome.zircon.font.resource.DFTilesetResource
import org.codetome.zircon.terminal.Size
import org.codetome.zircon.util.Stats

fun main(args:Array<String>) {
    val terminal = TerminalBuilder.newBuilder()
            .initialTerminalSize(SIZE)
            .font(DFTilesetResource.WANDERLUST_16X16.asJava2DFont())
            .buildTerminal()
    terminal.setCursorVisible(false)

    val charCount = 60 * 30
    val chars = listOf('a', 'b')
    val bgColors = listOf(TextColorFactory.fromString("#223344"), TextColorFactory.fromString("#112233"))
    val fgColors = listOf(TextColorFactory.fromString("#ffaaff"), TextColorFactory.fromString("#aaffaa"))

    var currIdx = 0
    var loopCount = 0
    while(true) {
        Stats.addTimedStatFor("terminalBenchmark") {
            terminal.setBackgroundColor(bgColors[currIdx])
            terminal.setForegroundColor(fgColors[currIdx])
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