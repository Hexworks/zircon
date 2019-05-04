package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.AppConfigs;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource;
import org.hexworks.zircon.api.resource.TilesetResource;

public class WindowTitleExample {

    private static final Size GRID_SIZE = Sizes.create(20, 10);
    private static final TilesetResource TILESET = BuiltInCP437TilesetResource.TAFFER_20X20;

    public static void main(String[] args) {

        SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(TILESET)
                .withSize(GRID_SIZE)
                .withTitle("Some cool title")
                .build());

    }

}
