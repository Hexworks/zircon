package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.AppConfigs;
import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.ColorThemes;
import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.Screens;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.TileGraphics;
import org.hexworks.zircon.api.Tiles;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.graphics.TileGraphic;
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

        final TileGraphic image = TileGraphics.newBuilder()
                .size(tileGrid.size())
                .build()
                .withFiller(Tiles.newBuilder()
                        .foregroundColor(theme.primaryForegroundColor())
                        .backgroundColor(theme.primaryBackgroundColor())
                        .character('~')
                        .build());

        screen.draw(image, Positions.defaultPosition());

        screen.display();
    }
}
