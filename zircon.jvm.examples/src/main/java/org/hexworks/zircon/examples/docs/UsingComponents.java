package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.component.CheckBox;
import org.hexworks.zircon.api.component.Header;
import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.screen.Screen;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.ComponentDecorations.shadow;
import static org.hexworks.zircon.api.uievent.ComponentEventType.ACTIVATED;

public class UsingComponents {

    public static void main(String[] args) {

        final TileGrid tileGrid = SwingApplications.startTileGrid(
                AppConfigs.newConfig()
                        .withSize(34, 18)
                        .withDefaultTileset(CP437TilesetResources.aduDhabi16x16())
                        .build());
        final Screen screen = Screens.createScreenFor(tileGrid);

        Panel panel = Components.panel()
                .withDecorations(
                        // panels can be wrapped in a box
                        box(BoxType.SINGLE, "Panel"),
                        shadow()) // shadow can be added
                .withSize(32, 16) // the size must be smaller than the parent's size
                .withPosition(1, 1)
                .build(); // position is always relative to the parent

        final Header header = Components.header()
                // this will be 1x1 left and down from the top left
                // corner of the panel
                .withPosition(1, 1)
                .withText("Header")
                .build();

        final CheckBox checkBox = Components.checkBox()
                .withText("Check me!")
                .withPosition(Positions.create(0, 1)
                        // the position class has some convenience methods
                        // for you to specify your component's position as
                        // relative to another one
                        .relativeToBottomOf(header))
                .build();

        final Button left = Components.button()
                .withPosition(Positions.create(0, 1) // this means 1 row below the check box
                        .relativeToBottomOf(checkBox))
                .withText("Left")
                .build();

        final Button right = Components.button()
                .withPosition(Positions.create(1, 0) // 1 column right relative to the left BUTTON
                        .relativeToRightOf(left))
                .withText("Right")
                .build();

        panel.addComponent(header);
        panel.addComponent(checkBox);
        panel.addComponent(left);
        panel.addComponent(right);

        screen.addComponent(panel);

        // we can apply color themes to a screen
        screen.applyColorTheme(ColorThemes.monokaiBlue());

        // this is how you can define interactions with a component
        left.handleComponentEvents(ACTIVATED, (event) -> {
            screen.applyColorTheme(ColorThemes.monokaiGreen());
            return UIEventResponses.processed();
        });

        right.handleComponentEvents(ACTIVATED, (event) -> {
            screen.applyColorTheme(ColorThemes.monokaiViolet());
            return UIEventResponses.processed();
        });

        // in order to see the changes you need to display your screen.
        screen.display();
    }
}
