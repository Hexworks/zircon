package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.animation.Animation;
import org.hexworks.zircon.api.animation.AnimationResource;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.builder.animation.AnimationBuilder;
import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;

import static org.hexworks.zircon.api.ComponentDecorations.box;

public class AnimationExample {

    private static final Size TERMINAL_SIZE = Size.create(50, 30);
    private static final Position LEFT_POS = Position.create(8, 5);
    private static final Position RIGHT_POS = Position.create(29, 5);
    private static final TilesetResource TILESET = CP437TilesetResources.taffer20x20();

    public static void main(String[] args) {
        Screen screen = Screen.create(SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(TILESET)
                .withSize(TERMINAL_SIZE)
                .withDebugMode(true)
                .enableBetaFeatures()
                .build()));

        final Panel panel = Components.panel()
                .withDecorations(box(BoxType.SINGLE, "Animation example"))
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
