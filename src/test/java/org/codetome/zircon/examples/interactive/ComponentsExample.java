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
import org.codetome.zircon.terminal.Terminal;

import java.util.concurrent.atomic.AtomicInteger;

public class ComponentsExample {

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Terminal terminal = TerminalBuilder.newBuilder()
                .initialTerminalSize(Size.of(40, 20))
                .font(CP437TilesetResource.TAFFER_20X20.asJava2DFont())
                .buildTerminal();

        Screen screen0 = TerminalBuilder.newBuilder().createScreenFor(terminal);
        Screen screen1 = TerminalBuilder.newBuilder().createScreenFor(terminal);

        final Container container0 = screen0.getContainer();
        final Container container1 = screen1.getContainer();


        container0.addComponent(new DefaultComponent(
                Size.of(3, 4),
                Position.of(2, 2),
                new ComponentStyles(
                        StyleSetBuilder.newBuilder()
                                .backgroundColor(TextColorFactory.fromString("#445566"))
                                .build(),
                        StyleSetBuilder.newBuilder()
                                .backgroundColor(TextColorFactory.fromString("#aabbcc"))
                                .build(),
                        StyleSetBuilder.EMPTY)));

        container0.addComponent(new DefaultComponent(
                Size.of(3, 4),
                Position.of(7, 2),
                new ComponentStyles(
                        StyleSetBuilder.newBuilder()
                                .backgroundColor(TextColorFactory.fromString("#665544"))
                                .build(),
                        StyleSetBuilder.newBuilder()
                                .backgroundColor(TextColorFactory.fromString("#ccbbaa"))
                                .build(),
                        StyleSetBuilder.EMPTY)));

        Container nestedContainer = new DefaultContainer(
                Size.of(5, 5),
                Position.of(2, 7),
                new ComponentStyles(
                        StyleSetBuilder.newBuilder()
                                .backgroundColor(TextColorFactory.fromString("#555555"))
                                .build(),
                        StyleSetBuilder.newBuilder()
                                .backgroundColor(TextColorFactory.fromString("#bbbbbb"))
                                .build(),
                        StyleSetBuilder.EMPTY));

        nestedContainer.addComponent(new DefaultComponent(
                Size.of(2, 2),
                Position.of(3, 8),
                new ComponentStyles(
                        StyleSetBuilder.newBuilder()
                                .backgroundColor(TextColorFactory.fromString("#446644"))
                                .build(),
                        StyleSetBuilder.newBuilder()
                                .backgroundColor(TextColorFactory.fromString("#aaffaa"))
                                .build(),
                        StyleSetBuilder.EMPTY)));

        container1.addComponent(nestedContainer);

        final Screen[] screens = new Screen[]{screen0, screen1};
        final AtomicInteger currentScreen = new AtomicInteger(0);

        terminal.addInputListener((input) -> {
            if(input.isKeyStroke() && input.asKeyStroke().getCharacter() == 'x') {
                currentScreen.set(currentScreen.get() == 0 ? 1 : 0);
                screens[currentScreen.get()].display();
            }
        });

        screen0.display();
    }

}
