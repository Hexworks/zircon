package org.codetome.zircon.examples;

import org.codetome.zircon.TerminalUtils;
import org.codetome.zircon.api.data.Position;
import org.codetome.zircon.api.data.Size;
import org.codetome.zircon.api.animation.Animation;
import org.codetome.zircon.api.builder.animation.AnimationBuilder;
import org.codetome.zircon.api.animation.AnimationResource;
import org.codetome.zircon.api.animation.DefaultAnimationHandler;
import org.codetome.zircon.api.component.Panel;
import org.codetome.zircon.api.interop.Components;
import org.codetome.zircon.api.interop.Positions;
import org.codetome.zircon.api.interop.Screens;
import org.codetome.zircon.api.interop.Sizes;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.api.grid.TileGrid;

import static org.codetome.zircon.api.resource.CP437TilesetResource.WANDERLUST_16X16;

public class AnimationExample {

    private static final Size TERMINAL_SIZE = Sizes.create(50, 30);
    private static final Position LEFT_POS = Positions.create(8, 5);
    private static final Position RIGHT_POS = Positions.create(29, 5);

    public static void main(String[] args) {
        final TileGrid tileGrid = TerminalUtils.fetchTerminalBuilder(args)
                .font(WANDERLUST_16X16.toFont())
                .initialTerminalSize(TERMINAL_SIZE)
                .build();
        final Screen screen = Screens.createScreenFor(tileGrid);
        screen.setCursorVisibility(false);

        final Panel panel = Components.newPanelBuilder()
                .wrapWithBox()
                .title("Animation example")
                .size(TERMINAL_SIZE)
                .build();

        panel.addComponent(Components.newLabelBuilder()
                .text("Looped:")
                .position(LEFT_POS.withRelativeY(-3).withRelativeX(-1))
                .build());
        panel.addComponent(Components.newLabelBuilder()
                .text("Non-looped:")
                .position(RIGHT_POS.withRelativeY(-3).withRelativeX(-1))
                .build());
        screen.addComponent(panel);

        screen.display();


        AnimationBuilder first = AnimationResource.loadAnimationFromStream(AnimationExample.class.getResourceAsStream("/animations/skull.zap"));
        AnimationBuilder second = first.createCopy();
        first.loopCount(0);
        second.loopCount(1);
        for (int i = 0; i < first.getLength(); i++) {
            first.addPosition(LEFT_POS);
            second.addPosition(RIGHT_POS);
        }
        Animation leftAnim = first.build();
        Animation rightAnim = second.build();

        final DefaultAnimationHandler animationHandler = new DefaultAnimationHandler(screen);
        animationHandler.addAnimation(leftAnim);
        animationHandler.addAnimation(rightAnim);

    }

}
