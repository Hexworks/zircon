package org.codetome.zircon.examples.interactive;

import org.codetome.zircon.Position;
import org.codetome.zircon.Size;
import org.codetome.zircon.api.CP437TilesetResource;
import org.codetome.zircon.api.StyleSetBuilder;
import org.codetome.zircon.api.TerminalBuilder;
import org.codetome.zircon.api.TextColorFactory;
import org.codetome.zircon.component.ComponentStyles;
import org.codetome.zircon.component.Container;
import org.codetome.zircon.component.impl.DefaultComponent;
import org.codetome.zircon.component.impl.DefaultContainer;
import org.codetome.zircon.screen.Screen;

public class ComponentsExample {

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Screen screen = TerminalBuilder.newBuilder()
                .initialTerminalSize(Size.of(40, 20))
                .font(CP437TilesetResource.TAFFER_20X20.asJava2DFont())
                .buildScreen();
        screen.setCursorVisible(false);
        final Container container = screen.getContainer();

        container.addComponent(new DefaultComponent(
                Size.of(3, 4),
                Position.of(2, 2),
                new ComponentStyles(
                        StyleSetBuilder.newBuilder()
                                .backgroundColor(TextColorFactory.fromString("#445566"))
                                .build(),
                        StyleSetBuilder.newBuilder()
                                .backgroundColor(TextColorFactory.fromString("#667788"))
                                .build(),
                        StyleSetBuilder.EMPTY)));

        container.addComponent(new DefaultComponent(
                Size.of(3, 4),
                Position.of(7, 2),
                new ComponentStyles(
                        StyleSetBuilder.newBuilder()
                                .backgroundColor(TextColorFactory.fromString("#665544"))
                                .build(),
                        StyleSetBuilder.newBuilder()
                                .backgroundColor(TextColorFactory.fromString("#887766"))
                                .build(),
                        StyleSetBuilder.EMPTY)));

        container.addComponent(new DefaultContainer(
                Size.of(5, 5),
                Position.of(2, 7),
                new ComponentStyles(
                        StyleSetBuilder.newBuilder()
                                .backgroundColor(TextColorFactory.fromString("#555555"))
                                .build(),
                        StyleSetBuilder.newBuilder()
                                .backgroundColor(TextColorFactory.fromString("#777777"))
                                .build(),
                        StyleSetBuilder.EMPTY)
        ));

        screen.display();
    }

}
