package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.graphics.TileGraphics;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.api.uievent.ComponentEventType;
import org.hexworks.zircon.api.uievent.UIEventResponse;

public class APrimerOnScreens {

    public static void main(String[] args) {
        drawWaves();
        switchScreens();
    }

    private static void switchScreens() {
        TileGrid tileGrid = SwingApplications.startTileGrid();

        final Screen screen0 = Screen.create(tileGrid);
        final Button next = Components.button()
                .withText("Next")
                .withPosition(8, 1)
                .build();
        screen0.addComponent(next);

        final Screen screen1 = Screen.create(tileGrid);
        final Button prev = Components.button()
                .withText("Prev")
                .withPosition(1, 1)
                .build();
        screen1.addComponent(prev);

        next.handleComponentEvents(ComponentEventType.ACTIVATED, (event) -> {
            System.out.println("Switching to Screen 1");
            screen1.display();
            return UIEventResponse.preventDefault();
        });
        prev.handleComponentEvents(ComponentEventType.ACTIVATED, (event) -> {
            System.out.println("Switching to Screen 0");
            screen0.display();
            return UIEventResponse.processed();
        });

        screen0.setTheme(ColorThemes.adriftInDreams());
        screen1.setTheme(ColorThemes.afterTheHeist());

        screen1.display();
    }

    private static void drawWaves() {
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
