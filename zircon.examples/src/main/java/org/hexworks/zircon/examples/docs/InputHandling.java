package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.input.KeyStroke;
import org.hexworks.zircon.api.input.MouseAction;
import org.hexworks.zircon.api.listener.MouseAdapter;
import org.jetbrains.annotations.NotNull;

public class InputHandling {

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid();

        // listens to all inputs
        tileGrid.onInput((input -> {
            if (input.isKeyStroke()) {
                KeyStroke keyStroke = input.asKeyStroke().get();
                System.out.println(keyStroke);
            } else if (input.isMouseAction()) {
                MouseAction mouseAction = input.asMouseAction().get();
                System.out.println(mouseAction);
            }
        }));

        // listens to specific mouse events (override the methods which you need)
        tileGrid.onMouseAction(new MouseAdapter() {

            @Override
            public void mousePressed(@NotNull MouseAction action) {
                // ...
            }

            @Override
            public void mouseDragged(@NotNull MouseAction action) {
                // ...
            }
        });

        // you can use lambdas as well
        tileGrid.onMouseDragged(action -> {
            // ...
        });

    }
}
