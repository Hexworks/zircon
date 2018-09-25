package org.hexworks.zircon.examples.events;

import org.hexworks.zircon.api.AppConfigs;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.input.MouseAction;
import org.hexworks.zircon.api.listener.MouseAdapter;
import org.hexworks.zircon.api.listener.MouseListener;
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.jetbrains.annotations.NotNull;

public class InputHandlingExampleJava {

    private static final Size SCREEN_SIZE = Sizes.create(80, 40);
    private static final TilesetResource TILESET = BuiltInCP437TilesetResource.ROGUE_YUN_16X16;

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(TILESET)
                .defaultSize(SCREEN_SIZE)
                .build());

        // called for all inputs
        tileGrid.onInput(System.out::println);

        // called for all key strokes
        tileGrid.onKeyStroke(keyStroke -> System.out.println(String.format("Key stroke is: %s", keyStroke)));

        // MouseAdapter provides empty defaults. In this case only the pressed events are handled.
        tileGrid.onMouseAction(new MouseAdapter() {
            @Override
            public void mousePressed(@NotNull MouseAction action) {
                System.out.println("Mouse is pressed");
            }
        });

        // methods are called for specific mouse action types
        tileGrid.onMouseAction(new MouseListener() {
            @Override
            public void mouseClicked(@NotNull MouseAction action) {

            }

            @Override
            public void mousePressed(@NotNull MouseAction action) {

            }

            @Override
            public void mouseReleased(@NotNull MouseAction action) {

            }

            @Override
            public void mouseEntered(@NotNull MouseAction action) {

            }

            @Override
            public void mouseExited(@NotNull MouseAction action) {

            }

            @Override
            public void mouseWheelRotatedUp(@NotNull MouseAction action) {

            }

            @Override
            public void mouseWheelRotatedDown(@NotNull MouseAction action) {

            }

            @Override
            public void mouseDragged(@NotNull MouseAction action) {

            }

            @Override
            public void mouseMoved(@NotNull MouseAction action) {

            }
        });
    }
}
