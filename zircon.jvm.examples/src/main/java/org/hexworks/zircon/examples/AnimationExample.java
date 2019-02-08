package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.animation.Animation;
import org.hexworks.zircon.api.animation.AnimationResource;
import org.hexworks.zircon.api.builder.animation.AnimationBuilder;
import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;

public class AnimationExample {

    private static final Size TERMINAL_SIZE = Sizes.create(50, 30);
    private static final Position LEFT_POS = Positions.create(8, 5);
    private static final Position RIGHT_POS = Positions.create(29, 5);
    private static final TilesetResource TILESET = BuiltInCP437TilesetResource.TAFFER_20X20;

    public static void main(String[] args) {

        // TODO: Libgdx doesn't have animations yet
        Screen screen = Screens.createScreenFor(SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(TILESET)
                .withSize(TERMINAL_SIZE)
                .withDebugMode(true)
                .enableBetaFeatures()
                .build()));

        final Panel panel = Components.panel()
                .wrapWithBox(true)
                .withTitle("Animation example")
                .withSize(TERMINAL_SIZE)
                .build();

        panel.addComponent(Components.label()
                .withText("Looped:")
                .withPosition(LEFT_POS.withRelativeY(-3).withRelativeX(-1))
                .build());
        panel.addComponent(Components.label()
                .withText("Non-looped:")
                .withPosition(RIGHT_POS.withRelativeY(-3).withRelativeX(-1))
                .build());
        screen.addComponent(panel);

        screen.display();

        AnimationBuilder first = AnimationResource.loadAnimationFromStream(
                AnimationExample.class.getResourceAsStream("/animations/skull.zap"),
                TILESET);
        AnimationBuilder second = first.createCopy();
        first.withLoopCount(0);
        second.withLoopCount(1);
        for (int i = 0; i < first.getTotalFrameCount(); i++) {
            first.addPosition(LEFT_POS);
            second.addPosition(RIGHT_POS);
        }
        Animation leftAnim = first.build();
        Animation rightAnim = second.build();

        screen.startAnimation(leftAnim);
        screen.startAnimation(rightAnim);

    }

}
