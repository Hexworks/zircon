package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.graphics.Layer;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.uievent.KeyboardEventType;

public class PlayerMoveExampleJava {

    private static Tile PLAYER_TILE = Tiles.newBuilder()
            .withBackgroundColor(ANSITileColor.BLACK)
            .withForegroundColor(ANSITileColor.WHITE)
            .withCharacter('@')
            .buildCharacterTile();

    public static void main(String[] args) {
        TileGrid tileGrid = SwingApplications.startTileGrid();

        Layer player = Layers.newBuilder()
                .withSize(Sizes.one())
                .withOffset(Positions.create(tileGrid.getWidth() / 2, tileGrid.getHeight() / 2))
                .build()
                .fill(PLAYER_TILE);

        tileGrid.processKeyboardEvents(KeyboardEventType.KEY_PRESSED, ((event, phase) -> {
            switch (event.getCode()) {
                case UP:
                    player.moveUpBy(1);
                    break;
                case DOWN:
                    player.moveDownBy(1);
                    break;
                case LEFT:
                    player.moveLeftBy(1);
                    break;
                case RIGHT:
                    player.moveRightBy(1);
                    break;
            }
        }));

        tileGrid.pushLayer(player);
    }

}
