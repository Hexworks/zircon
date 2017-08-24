package org.codetome.zircon.examples.interactive;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.Symbols;
import org.codetome.zircon.api.builder.ComponentStylesBuilder;
import org.codetome.zircon.api.builder.StyleSetBuilder;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.component.*;
import org.codetome.zircon.api.component.builder.ButtonBuilder;
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

    private static final StyleSet PANEL_STYLE = StyleSetBuilder.newBuilder()
            .backgroundColor(TextColorFactory.fromString("#268bd2"))
            .foregroundColor(TextColorFactory.fromString("#b58900"))
            .build();

    private static final StyleSet BUTTON_DEFAULT_STYLE = StyleSetBuilder.newBuilder()
            .backgroundColor(TextColorFactory.fromString("#859900"))
            .build();

    private static final StyleSet BUTTON_MOUSE_STYLE = StyleSetBuilder.newBuilder()
            .backgroundColor(TextColorFactory.fromString("#a5b920"))
            .build();

    private static final StyleSet BUTTON_ACTIVE_STYLE = StyleSetBuilder.newBuilder()
            .backgroundColor(TextColorFactory.fromString("#c5d940"))
            .build();

    private static final ComponentStyles BUTTON_STYLES = ComponentStylesBuilder.newBuilder()
            .defaultStyle(BUTTON_DEFAULT_STYLE)
            .mouseOverStyle(BUTTON_MOUSE_STYLE)
            .activeStyle(BUTTON_ACTIVE_STYLE)
            .build();

    private static final ComponentStyles SCREEN_BG_STYLES = ComponentStylesBuilder.newBuilder()
            .defaultStyle(StyleSetBuilder.newBuilder()
                    .backgroundColor(TextColorFactory.fromString("#002b36"))
                    .build())
            .build();

    private static final ComponentStyles HEADER_STYLES = ComponentStylesBuilder.newBuilder()
            .defaultStyle(StyleSetBuilder.newBuilder()
                    .foregroundColor(TextColorFactory.fromString("#cb4b16"))
                    .backgroundColor(TextColorFactory.TRANSPARENT)
                    .build())
            .build();

    private static final ComponentStyles PANEL_STYLES = ComponentStylesBuilder.newBuilder()
            .defaultStyle(PANEL_STYLE)
            .build();

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Terminal terminal = TerminalBuilder.newBuilder()
                .initialTerminalSize(TERMINAL_SIZE)
//                .font(PhysicalFontResource.UBUNTU_MONO.toFont())
                .font(CP437TilesetResource.WANDERLUST_16X16.toFont())
                .buildTerminal();

        Screen panelsScreen = TerminalBuilder.newBuilder().createScreenFor(terminal);
        Screen buttonsScreen = TerminalBuilder.newBuilder().createScreenFor(terminal);
        Screen radioAndCheckScreen = TerminalBuilder.newBuilder().createScreenFor(terminal);
        final List<Screen> screens = Arrays.asList(panelsScreen, buttonsScreen, radioAndCheckScreen);

        final Container panelsScreenContainer = panelsScreen.getContainer();
        panelsScreenContainer.setComponentStyles(SCREEN_BG_STYLES);

        addScreenTitle(panelsScreenContainer, "Panels");

        for(int i = 0; i < screens.size(); i++) {
            addNavigation(screens.get(i).getContainer(), screens, i);
        }

        final Panel simplePanel = PanelBuilder.newBuilder()
                .position(Position.of(2, 4))
                .size(PANEL_SIZE)
                .componentStyles(PANEL_STYLES)
                .build();
        simplePanel.addComponent(LabelBuilder.newBuilder()
                .text("Simple panel")
                .position(Position.of(1, 1))
                .componentStyles(PANEL_STYLES)
                .build());
        panelsScreenContainer.addComponent(simplePanel);

        final Panel borderedPanel = PanelBuilder.newBuilder()
                .title("Panel")
                .position(Position.of(0, 2).relativeToBottomOf(simplePanel))
                .size(PANEL_SIZE)
                .wrapInBox()
                .boxType(BoxType.DOUBLE)
                .componentStyles(PANEL_STYLES)
                .build();
        borderedPanel.addComponent(LabelBuilder.newBuilder()
                .text("Bordered panel")
                .componentStyles(PANEL_STYLES)
                .build());
        panelsScreenContainer.addComponent(borderedPanel);


        final Panel panelWithShadow = PanelBuilder.newBuilder()
                .position(Position.of(4, 0).relativeToRightOf(simplePanel))
                .size(PANEL_SIZE)
                .addShadow()
                .componentStyles(PANEL_STYLES)
                .build();
        panelWithShadow.addComponent(LabelBuilder.newBuilder()
                .text("Panel with shadow")
                .position(Position.of(1, 1))
                .componentStyles(PANEL_STYLES)
                .build());
        panelsScreenContainer.addComponent(panelWithShadow);


        final Panel panelWithShadowAndBorder = PanelBuilder.newBuilder()
                .position(Position.of(0, 2).relativeToBottomOf(panelWithShadow))
                .size(PANEL_SIZE)
                .addShadow()
                .wrapInBox()
                .componentStyles(PANEL_STYLES)
                .build();
        panelWithShadowAndBorder.addComponent(LabelBuilder.newBuilder()
                .text("Panel with shadow")
                .componentStyles(PANEL_STYLES)
                .build());
        panelWithShadowAndBorder.addComponent(LabelBuilder.newBuilder()
                .text("and border")
                .position(Position.of(0, 1))
                .componentStyles(PANEL_STYLES)
                .build());
        panelsScreenContainer.addComponent(panelWithShadowAndBorder);

        panelsScreen.display();
    }

    private static void addNavigation(Container panelsScreenContainer, List<Screen> screens, int currIdx) {
        if (currIdx > 0) {
            final Button prev = ButtonBuilder.newBuilder()
                    .text(Symbols.TRIANGLE_LEFT_POINTING_BLACK + " Prev")
                    .position(Position.of(31, 1))
                    .componentStyles(BUTTON_STYLES)
                    .build();
            prev.onMouseReleased((a) -> screens.get(currIdx - 1).display());
            panelsScreenContainer.addComponent(prev);
        }
        if (currIdx < screens.size() - 1) {
            final Button next = ButtonBuilder.newBuilder()
                    .text("Next " + Symbols.TRIANGLE_RIGHT_POINTING_BLACK)
                    .position(Position.of(40, 1))
                    .componentStyles(BUTTON_STYLES)
                    .build();
            next.onMouseReleased((a) -> screens.get(currIdx + 1).display());
            panelsScreenContainer.addComponent(next);
        }
    }

    private static void addScreenTitle(Container container, String title) {
        container.addComponent(LabelBuilder.newBuilder()
                .text(title)
                .componentStyles(HEADER_STYLES)
                .position(Position.of(2, 1))
                .build());
    }

}
