package org.codetome.zircon.examples;

import org.codetome.zircon.api.animation.Animation;
import org.codetome.zircon.api.builder.animation.AnimationBuilder;
import org.codetome.zircon.api.builder.grid.AppConfigBuilder;
import org.codetome.zircon.api.builder.screen.ScreenBuilder;
import org.codetome.zircon.api.component.Panel;
import org.codetome.zircon.api.data.CharacterTile;
import org.codetome.zircon.api.data.Position;
import org.codetome.zircon.api.data.Size;
import org.codetome.zircon.api.grid.AppConfig;
import org.codetome.zircon.api.resource.TilesetResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.gui.swing.internal.application.SwingApplication;
import org.codetome.zircon.jvm.api.animation.AnimationResource;
import org.codetome.zircon.jvm.api.interop.Components;
import org.codetome.zircon.jvm.api.interop.Positions;
import org.codetome.zircon.jvm.api.interop.Sizes;

import static org.codetome.zircon.api.resource.CP437TilesetResource.WANDERLUST_16X16;

public class AnimationExample {

    private static final Size TERMINAL_SIZE = Sizes.create(50, 30);
    private static final Position LEFT_POS = Positions.create(8, 5);
    private static final Position RIGHT_POS = Positions.create(29, 5);
    private static final TilesetResource<CharacterTile> TILESET = WANDERLUST_16X16;

    public static void main(String[] args) {

        AppConfig config = AppConfigBuilder.Companion.newBuilder()
                .defaultTileset(TILESET)
                .defaultSize(TERMINAL_SIZE)
                .debugMode(true)
                .build();

        SwingApplication app = SwingApplication.Companion.create(config);
        app.start();

        Screen screen = ScreenBuilder.Companion.createScreenFor(app.getTileGrid());

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

        screen.addAnimation(leftAnim);
        screen.addAnimation(rightAnim);

    }

}
