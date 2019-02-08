package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.AppConfigs;
import org.hexworks.zircon.api.Layers;
import org.hexworks.zircon.api.LibgdxApplications;
import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.UIEventResponses;
import org.hexworks.zircon.api.application.Application;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.Layer;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.uievent.KeyCode;
import org.hexworks.zircon.api.uievent.KeyboardEventType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource.ADU_DHABI_16X16;
import static org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource.BISASAM_16X16;
import static org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource.REX_PAINT_16X16;
import static org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource.ROGUE_YUN_16X16;
import static org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource.WANDERLUST_16X16;

public class FontSwitcherExample {

    private static final int TERMINAL_WIDTH = 30;
    private static final int TERMINAL_HEIGHT = 8;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final List<TilesetResource> TILESETS = new ArrayList<>();

    public static void main(String[] args) {

        final TileGrid tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(BuiltInCP437TilesetResource.ADU_DHABI_16X16)
                .withSize(SIZE)
                .withDebugMode(true)
                .build());

        TILESETS.add(ADU_DHABI_16X16);
        TILESETS.add(ROGUE_YUN_16X16);
        TILESETS.add(REX_PAINT_16X16);
        TILESETS.add(WANDERLUST_16X16);
        TILESETS.add(BISASAM_16X16);

        final String switchFont = "Press '->' to switch Tileset!";
        final String switchLayer = "Press '<-' to switch Layer!";

        final Random random = new Random();

        refreshText(tileGrid, Positions.zero());
        refreshLayer(tileGrid, random);

        tileGrid.onKeyboardEvent(KeyboardEventType.KEY_PRESSED, (event, phase) -> {
            if (event.getCode().equals(KeyCode.RIGHT)) {
                tileGrid.useTileset(TILESETS.get(random.nextInt(TILESETS.size())));
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
        tileGrid.putCursorAt(position);
        for (int i = 0; i < "Press '->' to switch Tileset!".length(); i++) {
            tileGrid.putCharacter("Press '->' to switch Tileset!".charAt(i));
        }
    }

    private static void refreshLayer(TileGrid tileGrid, Random random) {
        tileGrid.getLayers().forEach(tileGrid::removeLayer);
        Layer layer = Layers.newBuilder()
                .withTileset(TILESETS.get(random.nextInt(TILESETS.size())))
                .withOffset(Positions.create(0, 1))
                .withSize(Sizes.create("Press '<-' to switch Layer!".length(), 1))
                .build();
//        layer.putText(text, Positions.defaultPosition());
        tileGrid.pushLayer(layer);
    }

}
