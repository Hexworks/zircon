package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.ColorThemes;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.Screens;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.UIEventResponses;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.api.uievent.KeyCode;
import org.hexworks.zircon.api.uievent.KeyboardEventType;
import org.hexworks.zircon.api.uievent.MouseEventType;
import org.hexworks.zircon.api.uievent.UIEventPhase;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.ComponentDecorations.shadow;
import static org.hexworks.zircon.api.component.ComponentAlignment.CENTER;
import static org.hexworks.zircon.api.uievent.ComponentEventType.ACTIVATED;

public class InputHandling {

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid();
        Screen screen = Screens.createScreenFor(tileGrid);

        Panel panel = Components.panel()
                .withDecorations(box(BoxType.SINGLE, "New Game"), shadow())
                .withSize(20, 10)
                .withAlignmentWithin(tileGrid, CENTER)
                .build();

        Button button = Components.button()
                .withText("Play")
                .withPosition(5, 5)
                .withAlignmentWithin(panel, CENTER)
                .build();

        screen.addComponent(panel);
        panel.addComponent(button);


        // components support event bubbling so we can filter for BUBBLE events here
        // note that if you try this you will only see the "A pressed..." message when
        // the panel is focused (or something else in the panel)

        panel.processKeyboardEvents(KeyboardEventType.KEY_PRESSED, (event, phase) -> {
            if (phase == UIEventPhase.BUBBLE && event.getCode() == KeyCode.KEY_A) {
                System.out.println("A pressed in bubble phase.");
            }
        });


        // it doesn't matter where you add the listener, you can do it before or after
        // adding the component to the screen

        // when you handle events you need to return a response
        button.handleComponentEvents(ACTIVATED, (event) -> {
            System.out.println("Skipped component event");
            return UIEventResponses.pass(); // pass means that you didn't handle the event
        });

        // when you process events you don't have to return a response, Zircon will treat
        // processors as if they were returning the `processed` response.
        button.processComponentEvents(ACTIVATED, (event) -> System.out.println("Button pressed!"));


        // listens to mouse events
        tileGrid.handleMouseEvents(MouseEventType.MOUSE_RELEASED, ((event, phase) -> {
            // we log the event we received
            System.out.println(String.format("Mouse event was: %s.", event));

            // we return a response indicating that we processed the event
            return UIEventResponses.processed();
        }));

        // listens to keyboard events
        tileGrid.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED, ((event, phase) -> {
            // we filter for KeyCode.UP only
            if (event.getCode().equals(KeyCode.UP)) {
                // only prints it when we press Arrow Up
                System.out.println(String.format("Keyboard event was: %s.", event));
                return UIEventResponses.processed();
            } else {
                // otherwise we just pass on it
                System.out.println("Keyboard event was not UP, we pass.");
                return UIEventResponses.pass(); // we didn't handle it so we pass on the event
            }
        }));

        // we make the contents of the screen visible.
        screen.display();
        screen.applyColorTheme(ColorThemes.entrappedInAPalette());
    }
}
