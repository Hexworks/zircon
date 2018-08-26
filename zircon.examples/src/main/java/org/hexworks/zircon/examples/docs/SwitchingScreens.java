package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.screen.Screen;

public class SwitchingScreens {

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid();

        final Screen screen0 = Screens.createScreenFor(tileGrid);
        final Button next = Components.button()
                .text("Next")
                .position(Positions.offset1x1())
                .build();
        screen0.addComponent(next);

        final Screen screen1 = Screens.createScreenFor(tileGrid);
        final Button prev = Components.button()
                .text("Prev")
                .position(Positions.offset1x1())
                .build();
        screen1.addComponent(prev);

        next.onMouseReleased(mouseAction -> screen1.display());
        prev.onMouseReleased(mouseAction -> screen0.display());

        screen0.applyColorTheme(ColorThemes.adriftInDreams());
        screen1.applyColorTheme(ColorThemes.afterTheHeist());

        screen0.display();
    }
}
