package org.codetome.zircon

import org.codetome.zircon.api.SwingTerminalBuilder
import org.codetome.zircon.api.builder.grid.TerminalBuilder
import org.codetome.zircon.api.builder.grid.VirtualTerminalBuilder

object TerminalUtils {

    @JvmStatic
    fun fetchTerminalBuilder(args: Array<String>): TerminalBuilder {
        return if (args.isNotEmpty()) {
            VirtualTerminalBuilder.newBuilder()
        } else {
            SwingTerminalBuilder.newBuilder()
        }
    }
}
