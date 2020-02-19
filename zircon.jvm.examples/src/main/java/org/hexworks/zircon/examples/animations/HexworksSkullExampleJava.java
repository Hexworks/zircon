package org.hexworks.zircon.examples.animations;

import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.examples.base.Animations;
import org.hexworks.zircon.examples.base.Defaults;

public class HexworksSkullExampleJava {

    public static void main(String[] args) {

        Screen screen = Defaults.createDefaultScreen();

        screen.display();

        screen.start(Animations.hexworksSkull(
                Position.create(screen.getWidth() / 2 - 6, screen.getHeight() / 2 - 12),
                screen.getTileset()));
    }

}
