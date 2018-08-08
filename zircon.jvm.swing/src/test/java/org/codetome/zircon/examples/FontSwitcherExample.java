package org.codetome.zircon.examples;

import org.codetome.zircon.api.builder.grid.AppConfigBuilder;
import org.codetome.zircon.api.color.TextColor;
import org.codetome.zircon.api.data.Position;
import org.codetome.zircon.api.data.Size;
import org.codetome.zircon.api.graphics.Layer;
import org.codetome.zircon.api.grid.AppConfig;
import org.codetome.zircon.api.grid.CursorStyle;
import org.codetome.zircon.api.grid.TileGrid;
import org.codetome.zircon.api.input.InputType;
import org.codetome.zircon.api.interop.Layers;
import org.codetome.zircon.api.interop.Positions;
import org.codetome.zircon.api.interop.Sizes;
import org.codetome.zircon.api.resource.TilesetResource;
import org.codetome.zircon.internal.application.SwingApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.codetome.zircon.api.resource.CP437TilesetResource.*;

public class FontSwitcherExample {

    private static final int TERMINAL_WIDTH = 30;
    private static final int TERMINAL_HEIGHT = 8;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final List<TilesetResource> TILESETS = new ArrayList<>();

    public static void main(String[] args) {

        final AppConfig config = AppConfigBuilder.Companion.newBuilder()
                .defaultSize(SIZE)
                .defaultTileset(ADU_DHABI_16X16)
                .cursorBlinking(true)
                .cursorStyle(CursorStyle.FIXED_BACKGROUND)
                .cursorColor(TextColor.Companion.fromString("#ff00ff"))
                .build();

        final SwingApplication app = SwingApplication.Companion.create(config);

        final TileGrid tileGrid = app.getTileGrid();

        app.start();

        TILESETS.add(ADU_DHABI_16X16);
        TILESETS.add(ROGUE_YUN_16X16);
        TILESETS.add(REX_PAINT_16X16);
        TILESETS.add(WANDERLUST_16X16);
        TILESETS.add(BISASAM_16X16);

        final String switchFont = "Press '->' to switch Tileset!";
        final String switchLayer = "Press '<-' to switch Layer!";

        final Random random = new Random();

        refreshText(tileGrid, switchFont, Positions.defaultPosition());
        refreshLayer(tileGrid, switchLayer, random);

        tileGrid.onInput(input -> {
            if (input.isKeyStroke()) {
                if (input.asKeyStroke().inputTypeIs(InputType.ArrowRight)) {
                    tileGrid.useTileset(TILESETS.get(random.nextInt(TILESETS.size())));
                    // this is needed because grid can't be forced to redraw
                    refreshText(tileGrid, switchFont, Positions.defaultPosition());
                }
                if (input.asKeyStroke().inputTypeIs(InputType.ArrowLeft)) {
                    refreshLayer(tileGrid, switchLayer, random);
                }
            }
        });
    }

    private static void refreshText(TileGrid tileGrid, String text, Position position) {
        tileGrid.putCursorAt(position);
        for (int i = 0; i < text.length(); i++) {
            tileGrid.putCharacter(text.charAt(i));
        }
    }

    private static void refreshLayer(TileGrid tileGrid, String text, Random random) {
        tileGrid.getLayers().forEach(t -> tileGrid.removeLayer(t));
        Layer layer = Layers.newBuilder()
                .tileset(TILESETS.get(random.nextInt(TILESETS.size())))
                .offset(Positions.create(0, 1))
                .size(Sizes.create(text.length(), 1))
                .build();
//        layer.putText(text, Positions.defaultPosition());
        tileGrid.pushLayer(layer);
    }

}
