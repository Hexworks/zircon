package org.codetome.zircon.examples;

import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.builder.DeviceConfigurationBuilder;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.factory.TextColorFactory;
import org.codetome.zircon.api.font.Font;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.resource.PhysicalFontResource;
import org.codetome.zircon.api.terminal.Terminal;
import org.codetome.zircon.api.terminal.config.CursorStyle;

import java.awt.image.BufferedImage;

public class CursorExample {

    private static final int TERMINAL_WIDTH = 16;
    private static final int TERMINAL_HEIGHT = 16;
    private static final Size SIZE = Size.of(TERMINAL_WIDTH, TERMINAL_HEIGHT);

    public static void main(String[] args) {
        // we create a new terminal using TerminalBuilder
        final Terminal terminal = TerminalBuilder.newBuilder()
                .initialTerminalSize(SIZE)
                .font(PhysicalFontResource.SOURCE_CODE_PRO.toFont())
                // we only override the device config
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
