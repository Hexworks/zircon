package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.api.uievent.KeyboardEventType;
import org.hexworks.zircon.api.uievent.MouseEventType;

import static org.hexworks.zircon.api.uievent.ComponentEventType.ACTIVATED;

public class InputHandling {

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid();
        Screen screen = Screens.createScreenFor(tileGrid);

        Button button = Components.button()
                .withText("Test me")
                .withPosition(5, 5)
                .wrapWithShadow(true)
                .build();

        screen.addComponent(button);

        // it doesn't matter where you add the listener, you can do it before or after
        // adding the component to the screen
        button.onComponentEvent(ACTIVATED, (event) -> {
            System.out.println(event.toString());
            return UIEventResponses.processed();
        });

        // listens to mouse events
        tileGrid.onMouseEvent(MouseEventType.MOUSE_RELEASED, ((event, phase) -> {
            // we log the event we received
            System.out.println(event.toString());

            // we return a response indicating that we processed the event
            return UIEventResponses.processed();
        }));

        // listens to keyboard events
        tileGrid.onKeyboardEvent(KeyboardEventType.KEY_PRESSED, ((event, phase) -> {
            System.out.println(event.toString());
            return UIEventResponses.processed();
        }));

        // we make the contents of the screen visible.
        screen.display();
        screen.applyColorTheme(ColorThemes.entrappedInAPalette());
    }
}
