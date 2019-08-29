package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.Layer;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.uievent.KeyCode;
import org.hexworks.zircon.api.uievent.KeyboardEventType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FontSwitcherExample {

    private static final int TERMINAL_WIDTH = 30;
    private static final int TERMINAL_HEIGHT = 8;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final List<TilesetResource> TILESETS = new ArrayList<>();

    public static void main(String[] args) {

        final TileGrid tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(CP437TilesetResources.aduDhabi16x16())
                .withSize(SIZE)
                .withDebugMode(true)
                .build());

        TILESETS.add(CP437TilesetResources.aduDhabi16x16());
        TILESETS.add(CP437TilesetResources.rogueYun16x16());
        TILESETS.add(CP437TilesetResources.rexPaint16x16());
        TILESETS.add(CP437TilesetResources.wanderlust16x16());
        TILESETS.add(CP437TilesetResources.bisasam16x16());

        final String switchFont = "Press '->' to switch Tileset!";
        final String switchLayer = "Press '<-' to switch Layer!";

        final Random random = new Random();

        refreshText(tileGrid, Positions.zero());
        refreshLayer(tileGrid, random);

        tileGrid.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED, (event, phase) -> {
            if (event.getCode().equals(KeyCode.RIGHT)) {
                tileGrid.setTileset(TILESETS.get(random.nextInt(TILESETS.size())));
                // this is needed because grid can't be forced to redraw
                refreshText(tileGrid, Positions.zero());
            }
            if (event.getCode().equals(KeyCode.LEFT)) {
                refreshLayer(tileGrid, random);
            }
            return UIEventResponses.processed();
        });
    }

    private static void refreshText(TileGrid tileGrid, Position position) {
        tileGrid.setCursorPosition(position);
        String text = "Press '->' to switch Tileset!";
        tileGrid.draw(CharacterTileStrings.newBuilder().withText(text).build());
    }

    private static void refreshLayer(TileGrid tileGrid, Random random) {
        String text = "Press '<-' to switch Layer!";
        tileGrid.removeAllLayers();
        Layer layer = Layers.newBuilder()
                .withTileset(TILESETS.get(random.nextInt(TILESETS.size())))
                .withOffset(Positions.create(0, 1))
                .withSize(Sizes.create(text.length(), 1))
                .build();
        layer.draw(CharacterTileStrings.newBuilder().withText(text).build());
        tileGrid.addLayer(layer);
    }

}
