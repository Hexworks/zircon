package org.codetome.zircon.examples.interactive;

import org.codetome.zircon.api.Modifiers;
import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.Symbols;
import org.codetome.zircon.api.builder.ComponentStylesBuilder;
import org.codetome.zircon.api.builder.DeviceConfigurationBuilder;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.component.*;
import org.codetome.zircon.api.component.builder.ButtonBuilder;
import org.codetome.zircon.api.component.builder.HeaderBuilder;
import org.codetome.zircon.api.component.builder.LabelBuilder;
import org.codetome.zircon.api.component.builder.PanelBuilder;
import org.codetome.zircon.api.factory.TextColorFactory;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.api.terminal.Terminal;
import org.codetome.zircon.api.terminal.config.CursorStyle;
import org.codetome.zircon.internal.behavior.impl.DefaultScrollable;
import org.codetome.zircon.internal.component.impl.DefaultTextBox;
import org.codetome.zircon.internal.graphics.BoxType;

import java.util.Arrays;
import java.util.List;

import static org.codetome.zircon.api.Modifiers.BorderType.DOTTED;
import static org.codetome.zircon.api.Modifiers.BorderType.SOLID;

public class ComponentsExample {

    private static final Size PANEL_SIZE = Size.of(22, 6);
    private static final Size TERMINAL_SIZE = Size.of(52, 28);
    private static final Theme PANELS_THEME = ThemeRepository.SOLARIZED_DARK_ORANGE.getTheme();
    private static final Theme BUTTONS_THEME = ThemeRepository.SOLARIZED_LIGHT_VIOLET.getTheme();

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Terminal terminal = TerminalBuilder.newBuilder()
                .initialTerminalSize(TERMINAL_SIZE)
//                .font(PhysicalFontResource.UBUNTU_MONO.toFont())
                .font(CP437TilesetResource.TAFFER_20X20.toFont())
                .deviceConfiguration(DeviceConfigurationBuilder.newBuilder()
                        .cursorBlinking(true)
                        .cursorStyle(CursorStyle.REVERSED)
                        .cursorColor(TextColorFactory.fromString("#ff00ff"))
                        .build())
                .buildTerminal();

        Screen panelsScreen = TerminalBuilder.newBuilder().createScreenFor(terminal);
        Screen buttonsAndTextBoxesScreen = TerminalBuilder.newBuilder().createScreenFor(terminal);
        Screen radioAndCheckScreen = TerminalBuilder.newBuilder().createScreenFor(terminal);
        final List<Screen> screens = Arrays.asList(
                panelsScreen,
                buttonsAndTextBoxesScreen,
                radioAndCheckScreen);

        addScreenTitle(panelsScreen, "Panels");

        for(int i = 0; i < screens.size(); i++) {
            addNavigation(screens.get(i), screens, i);
        }

        // panels screen

        final Panel simplePanel = PanelBuilder.newBuilder()
                .position(Position.of(2, 4))
                .size(PANEL_SIZE)
                .build();
        simplePanel.addComponent(new DefaultTextBox(
                "Simple panel with editable text box",
                Size.of(13, 3),
                Size.of(13, 3),
                Position.of(1, 1),
                ComponentStylesBuilder.DEFAULT
        ));
        panelsScreen.addComponent(simplePanel);

        final Panel boxedPanel = PanelBuilder.newBuilder()
                .title("Panel")
                .position(Position.of(0, 2).relativeToBottomOf(simplePanel))
                .size(PANEL_SIZE)
                .wrapInBox()
                .boxType(BoxType.DOUBLE)
                .build();
        boxedPanel.addComponent(LabelBuilder.newBuilder()
                .text("Boxed panel")
                .build());
        panelsScreen.addComponent(boxedPanel);


        final Panel panelWithShadow = PanelBuilder.newBuilder()
                .position(Position.of(4, 0).relativeToRightOf(simplePanel))
                .size(PANEL_SIZE)
                .addShadow()
                .build();
        panelWithShadow.addComponent(LabelBuilder.newBuilder()
                .text("Panel with shadow")
                .position(Position.of(1, 1))
                .build());
        panelsScreen.addComponent(panelWithShadow);


        final Panel panelWithShadowAndBox = PanelBuilder.newBuilder()
                .position(Position.of(0, 2).relativeToBottomOf(panelWithShadow))
                .size(PANEL_SIZE)
                .addShadow()
                .wrapInBox()
                .build();
        panelWithShadowAndBox.addComponent(LabelBuilder.newBuilder()
                .text("Panel with shadow")
                .build());
        panelWithShadowAndBox.addComponent(LabelBuilder.newBuilder()
                .text("and box")
                .position(Position.of(0, 1))
                .build());
        panelsScreen.addComponent(panelWithShadowAndBox);


        final Panel borderedPanel = PanelBuilder.newBuilder()
                .title("Bordered panel")
                .position(Position.of(0, 2).relativeToBottomOf(boxedPanel))
                .size(PANEL_SIZE)
                .addBorder(Modifiers.BORDER.of(SOLID))
                .build();
        borderedPanel.addComponent(LabelBuilder.newBuilder()
                .text("Bordered panel")
                .position(Position.OFFSET_1x1)
                .build());
        panelsScreen.addComponent(borderedPanel);

        final Panel borderedPanelWithShadow = PanelBuilder.newBuilder()
                .title("Bordered panel")
                .position(Position.of(0, 2).relativeToBottomOf(panelWithShadowAndBox))
                .size(PANEL_SIZE)
                .addBorder(Modifiers.BORDER.of(DOTTED))
                .addShadow()
                .build();
        borderedPanelWithShadow.addComponent(LabelBuilder.newBuilder()
                .text("Border+shadow panel")
                .position(Position.OFFSET_1x1)
                .build());
        panelsScreen.addComponent(borderedPanelWithShadow);

        panelsScreen.applyTheme(PANELS_THEME);

        // buttons screen

        buttonsAndTextBoxesScreen.addComponent(new DefaultTextBox(
                "fooooooooooo",
                Size.of(5, 5),
                Size.of(3, 3),
                Position.of(1, 1),
                ComponentStylesBuilder.DEFAULT
        ));
        buttonsAndTextBoxesScreen.applyTheme(BUTTONS_THEME);
        panelsScreen.display();
    }

    private static void addNavigation(Screen screen, List<Screen> screens, int currIdx) {
        if (currIdx > 0) {
            final Button prev = ButtonBuilder.newBuilder()
                    .text(Symbols.TRIANGLE_LEFT_POINTING_BLACK + " Prev")
                    .position(Position.of(33, 1))
                    .build();
            prev.onMouseReleased((a) -> screens.get(currIdx - 1).display());
            screen.addComponent(prev);
        }
        if (currIdx < screens.size() - 1) {
            final Button next = ButtonBuilder.newBuilder()
                    .text("Next " + Symbols.TRIANGLE_RIGHT_POINTING_BLACK)
                    .position(Position.of(42, 1))
                    .build();
            next.onMouseReleased((a) -> screens.get(currIdx + 1).display());
            screen.addComponent(next);
        }
    }

    private static void addScreenTitle(Screen screen, String title) {
        final Header header = HeaderBuilder.newBuilder()
                .text(title)
                .position(Position.of(2, 1))
                .build();
        screen.addComponent(header);
    }
}
