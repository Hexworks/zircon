package org.codetome.zircon.examples.interactive;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.Symbols;
import org.codetome.zircon.api.builder.ComponentStylesBuilder;
import org.codetome.zircon.api.builder.StyleSetBuilder;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.component.Container;
import org.codetome.zircon.api.component.builder.ButtonBuilder;
import org.codetome.zircon.api.component.builder.LabelBuilder;
import org.codetome.zircon.api.factory.TextColorFactory;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.api.terminal.Terminal;
import org.codetome.zircon.internal.component.impl.DefaultComponent;
import org.codetome.zircon.internal.component.impl.DefaultContainer;

import java.util.concurrent.atomic.AtomicInteger;

public class ComponentsExample {

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Terminal terminal = TerminalBuilder.newBuilder()
                .initialTerminalSize(Size.of(40, 20))
                .font(CP437TilesetResource.TAFFER_20X20.toFont())
                .buildTerminal();

        Screen mainScreen = TerminalBuilder.newBuilder().createScreenFor(terminal);
        Screen screen1 = TerminalBuilder.newBuilder().createScreenFor(terminal);

        final Container mainScreenContainer = mainScreen.getContainer();


        mainScreenContainer.addComponent(LabelBuilder.newBuilder()
                .text("Simple label")
                .position(Position.of(2, 1))
                .componentStyles(ComponentStylesBuilder.newBuilder()
                        .defaultStyle(StyleSetBuilder.newBuilder()
                                .backgroundColor(TextColorFactory.fromString("#111166"))
                                .foregroundColor(TextColorFactory.fromString("#8888ff"))
                                .build())
                        .build())
                .build());

        mainScreenContainer.addComponent(LabelBuilder.newBuilder()
                .text("Hover effect label")
                .position(Position.of(2, 3))
                .componentStyles(ComponentStylesBuilder.newBuilder()
                        .defaultStyle(StyleSetBuilder.newBuilder()
                                .backgroundColor(TextColorFactory.fromString("#665544"))
                                .foregroundColor(TextColorFactory.fromString("#ccbbaa"))
                                .build())
                        .mouseOverStyle(StyleSetBuilder.newBuilder()
                                .backgroundColor(TextColorFactory.fromString("#998877"))
                                .foregroundColor(TextColorFactory.fromString("#ffeedd"))
                                .build())
                        .build())
                .build());

        mainScreenContainer.addComponent(ButtonBuilder.newBuilder()
                .text("Button")
                .position(Position.of(2, 5))
                .componentStyles(ComponentStylesBuilder.newBuilder()
                        .defaultStyle(StyleSetBuilder.newBuilder()
                                .backgroundColor(TextColorFactory.fromString("#999999"))
                                .foregroundColor(TextColorFactory.fromString("#ffffff"))
                                .build())
                        .mouseOverStyle(StyleSetBuilder.newBuilder()
                                .backgroundColor(TextColorFactory.fromString("#aa9999"))
                                .foregroundColor(TextColorFactory.fromString("#ffffff"))
                                .build())
                        .activeStyle(StyleSetBuilder.newBuilder()
                                .backgroundColor(TextColorFactory.fromString("#ddaaaa"))
                                .foregroundColor(TextColorFactory.fromString("#ffffff"))
                                .build())
                        .build())
                .build());


        final Screen[] screens = new Screen[]{mainScreen, screen1};
        final AtomicInteger currentScreen = new AtomicInteger(0);

        terminal.addInputListener((input) -> {
            if (input.isKeyStroke() && input.asKeyStroke().getCharacter() == 'x') {
                currentScreen.set(currentScreen.get() == 0 ? 1 : 0);
                screens[currentScreen.get()].display();
            }
        });

        mainScreen.display();
    }

}
