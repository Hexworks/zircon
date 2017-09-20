package org.codetome.zircon.examples;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.builder.AnimationBuilder;
import org.codetome.zircon.api.builder.DeviceConfigurationBuilder;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.component.Panel;
import org.codetome.zircon.api.component.builder.LabelBuilder;
import org.codetome.zircon.api.component.builder.PanelBuilder;
import org.codetome.zircon.api.graphics.Animation;
import org.codetome.zircon.api.graphics.AnimationFrame;
import org.codetome.zircon.api.graphics.AnimationHandler;
import org.codetome.zircon.api.resource.AnimationResource;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.screen.Screen;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AnimationExample {

    private static final Size TERMINAL_SIZE = Size.of(50, 30);
    private static final Position LEFT_POS = Position.of(8, 5);
    private static final Position RIGHT_POS = Position.of(29, 5);

    public static void main(String[] args) {
        final Screen screen = TerminalBuilder.newBuilder()
                .initialTerminalSize(TERMINAL_SIZE)
                .font(CP437TilesetResource.YOBBO_20X20.toFont())
                .deviceConfiguration(DeviceConfigurationBuilder.newBuilder()
                        .build())
                .buildScreen();
        screen.setCursorVisible(false);

        final Panel panel = PanelBuilder.newBuilder()
                .wrapInBox()
                .title("Animation example")
                .size(TERMINAL_SIZE)
                .build();

        panel.addComponent(LabelBuilder.newBuilder()
                .text("Looped:")
                .position(LEFT_POS.withRelativeRow(-3).withRelativeColumn(-1))
                .build());
        panel.addComponent(LabelBuilder.newBuilder()
                .text("Non-looped:")
                .position(RIGHT_POS.withRelativeRow(-3).withRelativeColumn(-1))
                .build());
        screen.addComponent(panel);

        screen.display();



        AnimationBuilder first = AnimationResource.loadAnimationFromFile("src/test/resources/animations/skull.zap");
        AnimationBuilder second = first.createCopy();
        first.loopCount(0);
        second.loopCount(1);
        for (int i = 0; i < first.getLength(); i++) {
            first.addPosition(LEFT_POS);
            second.addPosition(RIGHT_POS);
        }
        Animation leftAnim = first.build();
        Animation rightAnim = second.build();

        final AnimationHandler animationHandler = new AnimationHandler(screen);
        animationHandler.addAnimation(leftAnim);
        animationHandler.addAnimation(rightAnim);

    }

}
