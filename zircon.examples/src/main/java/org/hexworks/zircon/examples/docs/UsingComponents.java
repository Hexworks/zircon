package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.component.CheckBox;
import org.hexworks.zircon.api.component.Header;
import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.screen.Screen;

public class UsingComponents {

    public static void main(String[] args) {

        final TileGrid tileGrid = SwingApplications.startTileGrid(
                AppConfigs.newConfig()
                        .defaultSize(Sizes.create(34, 18))
                        .defaultTileset(CP437TilesetResources.aduDhabi16x16())
                        .build());
        final Screen screen = Screens.createScreenFor(tileGrid);

        Panel panel = Components.panel()
                .wrapWithBox() // panels can be wrapped in a box
                .title("Panel") // if a panel is wrapped in a box a title can be displayed
                .wrapWithShadow() // shadow can be added
                .size(Sizes.create(32, 16)) // the size must be smaller than the parent's size
                .position(Positions.offset1x1())
                .build(); // position is always relative to the parent

        final Header header = Components.header()
                // this will be 1x1 left and down from the top left
                // corner of the panel
                .position(Positions.offset1x1())
                .text("Header")
                .build();

        final CheckBox checkBox = Components.checkBox()
                .text("Check me!")
                .position(Positions.create(0, 1)
                        // the position class has some convenience methods
                        // for you to specify your component's position as
                        // relative to another one
                        .relativeToBottomOf(header))
                .build();

        final Button left = Components.button()
                .position(Positions.create(0, 1) // this means 1 row below the check box
                        .relativeToBottomOf(checkBox))
                .text("Left")
                .build();

        final Button right = Components.button()
                .position(Positions.create(1, 0) // 1 column right relative to the left BUTTON
                        .relativeToRightOf(left))
                .text("Right")
                .build();

        panel.addComponent(header);
        panel.addComponent(checkBox);
        panel.addComponent(left);
        panel.addComponent(right);

        screen.addComponent(panel);

        // we can apply color themes to a screen
        screen.applyColorTheme(ColorThemes.monokaiBlue());

        // this is how you can define interactions with a component
        left.onMouseReleased((mouseAction -> screen.applyColorTheme(ColorThemes.monokaiGreen())));

        right.onMouseReleased((mouseAction -> screen.applyColorTheme(ColorThemes.monokaiViolet())));

        // in order to see the changes you need to display your screen.
        screen.display();
    }
}
