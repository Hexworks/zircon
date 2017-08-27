package org.codetome.zircon.examples.interactive;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.Symbols;
import org.codetome.zircon.api.builder.ComponentStylesBuilder;
import org.codetome.zircon.api.builder.StyleSetBuilder;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.component.*;
import org.codetome.zircon.api.component.builder.ButtonBuilder;
import org.codetome.zircon.api.component.builder.HeaderBuilder;
import org.codetome.zircon.api.component.builder.LabelBuilder;
import org.codetome.zircon.api.component.builder.PanelBuilder;
import org.codetome.zircon.api.factory.TextColorFactory;
import org.codetome.zircon.api.graphics.StyleSet;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.resource.PhysicalFontResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.api.terminal.Terminal;
import org.codetome.zircon.internal.graphics.BoxType;

import java.util.Arrays;
import java.util.List;

public class ComponentsExample {

    private static final Size PANEL_SIZE = Size.of(21, 10);
    private static final Size TERMINAL_SIZE = Size.of(50, 28);


    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Terminal terminal = TerminalBuilder.newBuilder()
                .initialTerminalSize(TERMINAL_SIZE)
//                .font(PhysicalFontResource.UBUNTU_MONO.toFont())
                .font(CP437TilesetResource.TAFFER_20X20.toFont())
                .buildTerminal();

        Screen panelsScreen = TerminalBuilder.newBuilder().createScreenFor(terminal);
        Screen buttonsScreen = TerminalBuilder.newBuilder().createScreenFor(terminal);
        Screen radioAndCheckScreen = TerminalBuilder.newBuilder().createScreenFor(terminal);
        final List<Screen> screens = Arrays.asList(panelsScreen, buttonsScreen, radioAndCheckScreen);

        final Container panelsScreenContainer = panelsScreen.getContainer();

        addScreenTitle(panelsScreenContainer, "Panels");

        for(int i = 0; i < screens.size(); i++) {
            addNavigation(screens.get(i).getContainer(), screens, i);
        }

        final Panel simplePanel = PanelBuilder.newBuilder()
                .position(Position.of(2, 4))
                .size(PANEL_SIZE)
                .build();
        simplePanel.addComponent(LabelBuilder.newBuilder()
                .text("Simple panel")
                .position(Position.of(1, 1))
                .build());
        panelsScreenContainer.addComponent(simplePanel);

        final Panel borderedPanel = PanelBuilder.newBuilder()
                .title("Panel")
                .position(Position.of(0, 2).relativeToBottomOf(simplePanel))
                .size(PANEL_SIZE)
                .wrapInBox()
                .boxType(BoxType.DOUBLE)
                .build();
        borderedPanel.addComponent(LabelBuilder.newBuilder()
                .text("Bordered panel")
                .build());
        panelsScreenContainer.addComponent(borderedPanel);


        final Panel panelWithShadow = PanelBuilder.newBuilder()
                .position(Position.of(4, 0).relativeToRightOf(simplePanel))
                .size(PANEL_SIZE)
                .addShadow()
                .build();
        panelWithShadow.addComponent(LabelBuilder.newBuilder()
                .text("Panel with shadow")
                .position(Position.of(1, 1))
                .build());
        panelsScreenContainer.addComponent(panelWithShadow);


        final Panel panelWithShadowAndBorder = PanelBuilder.newBuilder()
                .position(Position.of(0, 2).relativeToBottomOf(panelWithShadow))
                .size(PANEL_SIZE)
                .addShadow()
                .wrapInBox()
                .build();
        panelWithShadowAndBorder.addComponent(LabelBuilder.newBuilder()
                .text("Panel with shadow")
                .build());
        panelWithShadowAndBorder.addComponent(LabelBuilder.newBuilder()
                .text("and border")
                .position(Position.of(0, 1))
                .build());
        panelsScreenContainer.addComponent(panelWithShadowAndBorder);

        panelsScreenContainer.applyTheme(ThemeRepository.GAMEBOOKERS.getTheme());
        panelsScreen.display();
    }

    private static void addNavigation(Container panelsScreenContainer, List<Screen> screens, int currIdx) {
        if (currIdx > 0) {
            final Button prev = ButtonBuilder.newBuilder()
                    .text(Symbols.TRIANGLE_LEFT_POINTING_BLACK + " Prev")
                    .position(Position.of(31, 1))
                    .build();
            prev.onMouseReleased((a) -> screens.get(currIdx - 1).display());
            panelsScreenContainer.addComponent(prev);
        }
        if (currIdx < screens.size() - 1) {
            final Button next = ButtonBuilder.newBuilder()
                    .text("Next " + Symbols.TRIANGLE_RIGHT_POINTING_BLACK)
                    .position(Position.of(40, 1))
                    .build();
            next.onMouseReleased((a) -> screens.get(currIdx + 1).display());
            panelsScreenContainer.addComponent(next);
        }
    }

    private static void addScreenTitle(Container container, String title) {
        container.addComponent(HeaderBuilder.newBuilder()
                .text(title)
                .position(Position.of(2, 1))
                .build());
    }
}
