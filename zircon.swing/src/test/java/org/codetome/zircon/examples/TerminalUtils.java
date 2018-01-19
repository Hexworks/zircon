package org.codetome.zircon.examples;

import org.codetome.zircon.api.SwingTerminalBuilder;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.builder.VirtualTerminalBuilder;

public class TerminalUtils {

    public static TerminalBuilder fetchTerminalBuilder(String[] args) {
        final TerminalBuilder terminalBuilder;
        if(args.length > 0) {
            terminalBuilder = VirtualTerminalBuilder.newBuilder();
        } else {
            terminalBuilder = SwingTerminalBuilder.newBuilder();
        }
        return terminalBuilder;
    }
}
