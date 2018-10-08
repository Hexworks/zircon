package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.graphics.TileGraphics;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.screen.Screen;

public class CreatingAScreen {

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid(
                AppConfigs.newConfig()
                        .defaultSize(Sizes.create(20, 8))
                        .defaultTileset(CP437TilesetResources.wanderlust16x16())
                        .build());

        final Screen screen = Screens.createScreenFor(tileGrid);

        final ColorTheme theme = ColorThemes.adriftInDreams();

        final TileGraphics image = DrawSurfaces.tileGraphicsBuilder()
                .size(tileGrid.getSize())
                .build()
                .fill(Tiles.newBuilder()
                        .foregroundColor(theme.getPrimaryForegroundColor())
                        .backgroundColor(theme.getPrimaryBackgroundColor())
                        .character('~')
                        .build());

        screen.draw(image, Positions.zero());

        screen.display();
    }
}
