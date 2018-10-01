package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.application.Application;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.Layer;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.input.InputType;
import org.hexworks.zircon.api.input.KeyStroke;
import org.hexworks.zircon.api.listener.KeyStrokeListener;
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource.*;

public class FontSwitcherExample {

    private static final int TERMINAL_WIDTH = 30;
    private static final int TERMINAL_HEIGHT = 8;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final List<TilesetResource> TILESETS = new ArrayList<>();

    public static void main(String[] args) {

        Application app = SwingApplications.startApplication(AppConfigs.newConfig()
                .defaultTileset(BuiltInCP437TilesetResource.ADU_DHABI_16X16)
                .defaultSize(SIZE)
                .debugMode(true)
                .build());

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

        tileGrid.onKeyStroke(keyStroke -> {
            if (keyStroke.inputTypeIs(InputType.ArrowRight)) {
                tileGrid.useTileset(TILESETS.get(random.nextInt(TILESETS.size())));
                // this is needed because grid can't be forced to redraw
                refreshText(tileGrid, switchFont, Positions.defaultPosition());
            }
            if (keyStroke.inputTypeIs(InputType.ArrowLeft)) {
                refreshLayer(tileGrid, switchLayer, random);
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
        tileGrid.getLayers().forEach(tileGrid::removeLayer);
        Layer layer = Layers.newBuilder()
                .tileset(TILESETS.get(random.nextInt(TILESETS.size())))
                .offset(Positions.create(0, 1))
                .size(Sizes.create(text.length(), 1))
                .build();
//        layer.putText(text, Positions.defaultPosition());
        tileGrid.pushLayer(layer);
    }

}
