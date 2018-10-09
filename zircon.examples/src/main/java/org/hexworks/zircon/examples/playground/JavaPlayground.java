package org.hexworks.zircon.examples.playground;

import org.hexworks.zircon.api.AppConfigs;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.input.MouseAction;
import org.hexworks.zircon.api.listener.MouseAdapter;
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.jetbrains.annotations.NotNull;

public class JavaPlayground {

    private static final Size SIZE = Sizes.create(50, 30);
    private static final TilesetResource TILESET = BuiltInCP437TilesetResource.TAFFER_20X20;

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(TILESET)
                .withSize(SIZE)
                .withDebugMode(true)
                .build());

        tileGrid.onInput(System.out::println);

        tileGrid.onMouseAction(new MouseAdapter() {
            @Override
            public void mouseClicked(@NotNull MouseAction action) {

            }
        });


    }

}
