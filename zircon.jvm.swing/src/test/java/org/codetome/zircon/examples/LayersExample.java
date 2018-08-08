package org.codetome.zircon.examples;

import org.codetome.zircon.TerminalUtils;
import org.codetome.zircon.api.builder.graphics.LayerBuilder;
import org.codetome.zircon.api.color.TextColor;
import org.codetome.zircon.api.data.Position;
import org.codetome.zircon.api.data.Size;
import org.codetome.zircon.api.data.Tile;
import org.codetome.zircon.api.grid.TileGrid;
import org.codetome.zircon.api.interop.Positions;
import org.codetome.zircon.api.interop.Screens;
import org.codetome.zircon.api.interop.Sizes;
import org.codetome.zircon.api.interop.TextColors;
import org.codetome.zircon.api.interop.Tiles;
import org.codetome.zircon.api.screen.Screen;
import org.jetbrains.annotations.NotNull;

import static org.codetome.zircon.api.resource.CP437TilesetResource.WANDERLUST_16X16;

public class LayersExample {

    private static final int TERMINAL_WIDTH = 45;
    private static final int TERMINAL_HEIGHT = 5;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);

    public static void main(String[] args) {
        // for this example we only need a default grid (no extra config)
        final TileGrid tileGrid = TerminalUtils.fetchTerminalBuilder(args)
                .font(WANDERLUST_16X16)
                .initialTerminalSize(SIZE)
                .build();
        final Screen screen = Screens.createScreenFor(tileGrid);
        screen.setCursorVisibility(false); // we don't want the cursor right now

        final String firstRow = "This is white title on black";
        for (int x = 0; x < firstRow.length(); x++) {
            screen.setTileAt(
                    Positions.create(x + 1, 1),
                    buildWhiteOnBlack(firstRow.charAt(x)));
        }

        final String secondRow = "Like the row above but with blue overlay.";
        for (int x = 0; x < secondRow.length(); x++) {
            screen.setTileAt(
                    Positions.create(x + 1, 2),
                    buildWhiteOnBlack(secondRow.charAt(x)));
        }

        addOverlayAt(screen,
                Positions.create(1, 2),
                Sizes.create(secondRow.length(), 1),
                TextColors.create(50, 50, 200, 127));

        screen.display();
    }

    private static void addOverlayAt(Screen screen, Position offset, Size size, TextColor color) {
        screen.pushLayer(new LayerBuilder()
                .offset(offset)
                .size(size)
                .build()
                .fill(Tiles.newBuilder()
                        .backgroundColor(color)
                        .character(' ')
                        .build()));
    }

    @NotNull
    private static Tile buildWhiteOnBlack(char c) {
        return Tiles.newBuilder()
                .character(c)
                .backgroundColor(TextColors.create(0, 0, 0, 255))
                .foregroundColor(TextColors.create(255, 255, 255, 255))
                .build();
    }

}
