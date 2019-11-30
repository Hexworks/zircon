package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.component.Label;
import org.hexworks.zircon.api.screen.Screen;

public class TextChangeExample {

    public static void main(String[] args) {

        Screen screen = Screen.create(SwingApplications.startTileGrid());

        final Label label = Components.label().withText("label").build();

        label.setText("foo");
        screen.addComponent(label);
        screen.display();
    }

}
