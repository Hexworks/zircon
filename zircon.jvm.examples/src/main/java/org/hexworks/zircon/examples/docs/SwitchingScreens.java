package org.hexworks.zircon.examples.docs;

import org.hexworks.cobalt.logging.api.Logger;
import org.hexworks.cobalt.logging.api.LoggerFactory;
import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.api.uievent.ComponentEventType;

public class SwitchingScreens {

    private static Logger LOGGER = LoggerFactory.INSTANCE.getLogger(SwitchingScreens.class);

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid();

        final Screen screen0 = Screens.createScreenFor(tileGrid);
        final Button next = Components.button()
                .withText("Next")
                .withPosition(8, 1)
                .build();
        screen0.addComponent(next);

        final Screen screen1 = Screens.createScreenFor(tileGrid);
        final Button prev = Components.button()
                .withText("Prev")
                .withPosition(1, 1)
                .build();
        screen1.addComponent(prev);

        next.handleComponentEvents(ComponentEventType.ACTIVATED, (event) -> {
            LOGGER.info("Switching to Screen 1");
            screen1.display();
            return UIEventResponses.preventDefault();
        });
        prev.handleComponentEvents(ComponentEventType.ACTIVATED, (event) -> {
            LOGGER.info("Switching to Screen 0");
            screen0.display();
            return UIEventResponses.processed();
        });

        screen0.applyColorTheme(ColorThemes.adriftInDreams());
        screen1.applyColorTheme(ColorThemes.afterTheHeist());

        screen1.display();
    }
}
