package org.codetome.zircon.examples;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.TextCharacter;
import org.codetome.zircon.api.builder.DeviceConfigurationBuilder;
import org.codetome.zircon.api.builder.LayerBuilder;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.builder.TextCharacterBuilder;
import org.codetome.zircon.api.factory.TextColorFactory;
import org.codetome.zircon.api.color.TextColor;
import org.codetome.zircon.api.font.Font;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.resource.PhysicalFontResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.api.terminal.Terminal;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

import static org.codetome.zircon.api.builder.TerminalBuilder.*;
import static org.codetome.zircon.api.resource.CP437TilesetResource.WANDERLUST_16X16;

public class LayersExample {

    private static final int TERMINAL_WIDTH = 50;
    private static final int TERMINAL_HEIGHT = 6;
    private static final Size SIZE = Size.of(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final Font<BufferedImage> FONT = WANDERLUST_16X16.toFont();

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Terminal terminal = TerminalBuilder.newBuilder()
                .font(FONT)
                .initialTerminalSize(SIZE)
                .buildTerminal();
        final Screen screen = TerminalBuilder.createScreenFor(terminal);
        screen.setCursorVisible(false); // we don't want the cursor right now

        final String firstRow = "This is white title on black";
        for (int x = 0; x < firstRow.length(); x++) {
            screen.setCharacterAt(
                    Position.of(x + 1, 1),
                    buildWhiteOnBlack(firstRow.charAt(x)));
        }

        final String secondRow = "Like the row above but with blue overlay.";
        for (int x = 0; x < secondRow.length(); x++) {
            screen.setCharacterAt(
                    Position.of(x + 1, 2),
                    buildWhiteOnBlack(secondRow.charAt(x)));
        }

        addOverlayAt(screen,
                Position.of(1, 2),
                Size.of(secondRow.length(), 1),
                org.codetome.zircon.api.factory.TextColorFactory.fromRGB(50, 50, 200, 127));

        screen.display();
    }

    private static void addOverlayAt(Screen screen, Position offset, Size size, TextColor color) {
        screen.addLayer(new LayerBuilder()
                .offset(offset)
                .size(size)
                .filler(TextCharacterBuilder.newBuilder()
                        .backgroundColor(color)
                        .character(' ')
                        .build())
                .build());
    }

    @NotNull
    private static TextCharacter buildWhiteOnBlack(char c) {
        return TextCharacterBuilder.newBuilder()
                .character(c)
                .backgroundColor(TextColorFactory.fromRGB(0, 0, 0, 255))
                .foregroundColor(TextColorFactory.fromRGB(255, 255, 255, 255))
                .build();
    }

}
