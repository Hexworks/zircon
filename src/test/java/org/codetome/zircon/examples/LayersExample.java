package org.codetome.zircon.examples;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.TextCharacter;
import org.codetome.zircon.api.builder.DeviceConfigurationBuilder;
import org.codetome.zircon.api.builder.LayerBuilder;
import org.codetome.zircon.api.builder.TextCharacterBuilder;
import org.codetome.zircon.api.factory.TextColorFactory;
import org.codetome.zircon.api.color.TextColor;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.resource.PhysicalFontResource;
import org.codetome.zircon.api.screen.Screen;
import org.jetbrains.annotations.NotNull;

public class LayersExample {

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Screen screen = org.codetome.zircon.api.builder.TerminalBuilder.newBuilder()
                .initialTerminalSize(Size.of(45, 10))
//                .font(CP437TilesetResource.TAFFER_20X20.toFont())
                .font(PhysicalFontResource.UBUNTU_MONO.toFont())
                .deviceConfiguration(DeviceConfigurationBuilder.newBuilder()
                        .build())
                .buildScreen();
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
