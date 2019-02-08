package org.hexworks.zircon.examples.playground;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.component.ToggleButton;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;

public class JavaPlayground {

    private static final Size SIZE = Sizes.create(50, 30);
    private static final TilesetResource TILESET = BuiltInCP437TilesetResource.TAFFER_20X20;

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(TILESET)
                .withSize(SIZE)
                .withDebugMode(true)
                .build());

        Screen screen = Screens.createScreenFor(tileGrid);

        ToggleButton tb = Components.toggleButton().withText("foo").build();

    }

}
