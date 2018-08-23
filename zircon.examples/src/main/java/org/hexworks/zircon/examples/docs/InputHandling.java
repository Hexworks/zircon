package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.input.KeyStroke;
import org.hexworks.zircon.api.input.MouseAction;

public class InputHandling {

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid();

        tileGrid.onInput((input -> {
            if(input.isKeyStroke()) {
                KeyStroke keyStroke = input.asKeyStroke().get();
                System.out.println(keyStroke);
            } else if(input.isMouseAction()) {
                MouseAction mouseAction = input.asMouseAction().get();
                System.out.println(mouseAction);
            }
        }));

    }
}
