package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.graphics.TileGraphics;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.screen.Screen;

public class CreatingAScreen {

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid(
                AppConfig.newBuilder()
                        .withSize(20, 8)
                        .withDefaultTileset(CP437TilesetResources.wanderlust16x16())
                        .build());

        final Screen screen = Screen.create(tileGrid);

        final ColorTheme theme = ColorThemes.adriftInDreams();

        final TileGraphics image = DrawSurfaces.tileGraphicsBuilder()
                .withSize(tileGrid.getSize())
                .withFiller(Tile.newBuilder()
                        .withForegroundColor(theme.getPrimaryForegroundColor())
                        .withBackgroundColor(theme.getPrimaryBackgroundColor())
                        .withCharacter('~')
                        .build())
                .build();

        screen.draw(image, Position.zero(), image.getSize());

        screen.display();
    }
}
