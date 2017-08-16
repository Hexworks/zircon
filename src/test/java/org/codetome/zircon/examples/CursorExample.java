package org.codetome.zircon.examples;

import org.codetome.zircon.api.TextColorFactory;
import org.codetome.zircon.api.DeviceConfigurationBuilder;
import org.codetome.zircon.api.TerminalBuilder;
import org.codetome.zircon.terminal.Terminal;
import org.codetome.zircon.terminal.config.CursorStyle;

public class CursorExample {

    public static void main(String[] args) {
        // we create a new terminal using TerminalBuilder
        final Terminal terminal = TerminalBuilder.newBuilder()
                // we only override the parts which we need, in this case the device config
                .deviceConfiguration(DeviceConfigurationBuilder.newBuilder()
                        .cursorColor(TextColorFactory.fromString("#ff8844"))
                        .cursorStyle(CursorStyle.UNDER_BAR)
                        .cursorBlinking(true)
                        .build())
                .buildTerminal(); // then we build the terminal

        // for this example we need the cursor to be visible
        terminal.setCursorVisible(true);

        String text = "Cursor example...";
        for (int i = 0; i < text.length(); i++) {
            terminal.putCharacter(text.charAt(i));
        }
    }
}
