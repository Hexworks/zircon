package org.hexworks.zircon.examples.game;

import org.hexworks.zircon.api.Functions;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.graphics.Layer;
import org.hexworks.zircon.api.graphics.LayerHandle;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.uievent.KeyboardEventType;

public class PlayerMoveExampleJava {

    private static Tile PLAYER_TILE = Tile.newBuilder()
            .withBackgroundColor(ANSITileColor.BLACK)
            .withForegroundColor(ANSITileColor.WHITE)
            .withCharacter('@')
            .buildCharacterTile();

    public static void main(String[] args) {
        TileGrid tileGrid = SwingApplications.startTileGrid();

        Layer layer = Layer.newBuilder()
                .withSize(Size.one())
                .withOffset(Position.create(tileGrid.getWidth() / 2, tileGrid.getHeight() / 2))
                .withFiller(PLAYER_TILE)
                .build();
        LayerHandle player = tileGrid.addLayer(layer);

        tileGrid.processKeyboardEvents(KeyboardEventType.KEY_PRESSED, Functions.fromBiConsumer((event, phase) -> {
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
    }

}
