package org.hexworks.zircon.examples.playground;

import org.hexworks.zircon.api.AppConfigs;
import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.Screens;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.component.ToggleButton;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;

public class JavaPlayground {

    private static final Size SIZE = Sizes.create(50, 30);
    private static final TilesetResource TILESET = CP437TilesetResources.taffer20x20();

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
