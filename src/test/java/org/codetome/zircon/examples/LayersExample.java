package org.codetome.zircon.examples;

import org.codetome.zircon.Position;
import org.codetome.zircon.TextCharacter;
import org.codetome.zircon.builder.TextCharacterBuilder;
import org.codetome.zircon.builder.TextColorFactory;
import org.codetome.zircon.graphics.layer.DefaultLayer;
import org.codetome.zircon.screen.Screen;
import org.codetome.zircon.screen.TerminalScreen;
import org.codetome.zircon.builder.TerminalBuilder;
import org.codetome.zircon.terminal.Size;
import org.jetbrains.annotations.NotNull;

public class LayersExample {

    private static final String SAMPLE_TEXT_BW = "This is white text on black";

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Screen screen = TerminalBuilder.newBuilder().buildScreen();
        screen.setCursorVisible(false); // we don't want the cursor right now


        for (int y = 1; y < 3; y++) {
            for (int x = 0; x < SAMPLE_TEXT_BW.length(); x++) {
                screen.setCharacter(
                        new Position(x + 1, y),
                        buildWhiteOnBlack(SAMPLE_TEXT_BW.charAt(x)));
            }
        }

        screen.addOverlay(new DefaultLayer(
                new Size(SAMPLE_TEXT_BW.length() - 5, 1),
                TextCharacterBuilder.newBuilder()
                        .backgroundColor(TextColorFactory.fromRGB(100, 100, 100, 255))
                        .foregroundColor(TextColorFactory.fromRGB(100, 100, 100, 255))
                        .character(' ')
                        .build(),
                new Position(1, 2)
        ));

        screen.display();
    }

    @NotNull
    private static TextCharacter buildWhiteOnBlack(char c) {
        return TextCharacterBuilder.newBuilder()
                .character(c)
                .backgroundColor(TextColorFactory.fromRGB(0, 0, 0, 127))
                .foregroundColor(TextColorFactory.fromRGB(255, 255, 255, 127))
                .build();
    }

}
