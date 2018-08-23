package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.animation.Animation;
import org.hexworks.zircon.api.animation.AnimationResource;
import org.hexworks.zircon.api.builder.animation.AnimationBuilder;
import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.resource.BuiltInCP437Tileset;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;

public class AnimationExample {

    private static final Size TERMINAL_SIZE = Sizes.create(50, 30);
    private static final Position LEFT_POS = Positions.create(8, 5);
    private static final Position RIGHT_POS = Positions.create(29, 5);
    private static final TilesetResource TILESET = BuiltInCP437Tileset.TAFFER_20X20;

    public static void main(String[] args) {

        Screen screen = Screens.createScreenFor(SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(TILESET)
                .defaultSize(TERMINAL_SIZE)
                .debugMode(true)
                .build()));

        final Panel panel = Components.panel()
                .wrapWithBox()
                .title("Animation example")
                .size(TERMINAL_SIZE)
                .build();

        panel.addComponent(Components.label()
                .text("Looped:")
                .position(LEFT_POS.withRelativeY(-3).withRelativeX(-1))
                .build());
        panel.addComponent(Components.label()
                .text("Non-looped:")
                .position(RIGHT_POS.withRelativeY(-3).withRelativeX(-1))
                .build());
        screen.addComponent(panel);

        screen.display();

        AnimationBuilder first = AnimationResource.loadAnimationFromStream(
                AnimationExample.class.getResourceAsStream("/animations/skull.zap"),
                TILESET);
        AnimationBuilder second = first.createCopy();
        first.loopCount(0);
        second.loopCount(1);
        for (int i = 0; i < first.getLength(); i++) {
            first.addPosition(LEFT_POS);
            second.addPosition(RIGHT_POS);
        }
        Animation leftAnim = first.build();
        Animation rightAnim = second.build();

        screen.startAnimation(leftAnim);
        screen.startAnimation(rightAnim);

    }

}
