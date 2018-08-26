package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.BuiltInCP437Tileset;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;

public class JavaPlayground {

    private static final Size SIZE = Sizes.create(50, 30);
    private static final TilesetResource TILESET = BuiltInCP437Tileset.TAFFER_20X20;

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(TILESET)
                .defaultSize(SIZE)
                .debugMode(true)
                .build());

        Screen screen = Screens.createScreenFor(tileGrid);

        Button btn = Components.button().text("foo").build();

        screen.addComponent(btn);

        btn.onMouseMoved((mouseAction) -> System.out.println("Foo."));

        screen.display();

    }

}
