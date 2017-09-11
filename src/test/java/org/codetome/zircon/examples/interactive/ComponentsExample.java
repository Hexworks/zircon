package org.codetome.zircon.examples.interactive;

import org.codetome.zircon.api.Modifiers;
import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.Symbols;
import org.codetome.zircon.api.builder.ComponentStylesBuilder;
import org.codetome.zircon.api.builder.DeviceConfigurationBuilder;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.component.*;
import org.codetome.zircon.api.component.builder.*;
import org.codetome.zircon.api.factory.TextColorFactory;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.resource.PhysicalFontResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.api.terminal.Terminal;
import org.codetome.zircon.api.terminal.config.CursorStyle;
import org.codetome.zircon.internal.component.impl.DefaultRadioButton;
import org.codetome.zircon.internal.component.impl.DefaultRadioButtonGroup;
import org.codetome.zircon.internal.graphics.BoxType;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.codetome.zircon.api.Modifiers.BorderType.DOTTED;
import static org.codetome.zircon.api.Modifiers.BorderType.SOLID;

public class ComponentsExample {

    private static final Size PANEL_SIZE = Size.of(22, 6);
    private static final Size TERMINAL_SIZE = Size.of(52, 28);
    private static final Theme PANELS_THEME = ThemeRepository.GHOST_OF_A_CHANCE.getTheme();
    private static final Theme BUTTONS_THEME = ThemeRepository.SOLARIZED_LIGHT_VIOLET.getTheme();
    private static final PanelBuilder PANEL_TEMPLATE = PanelBuilder.newBuilder().size(PANEL_SIZE);

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Terminal terminal = TerminalBuilder.newBuilder()
                .initialTerminalSize(TERMINAL_SIZE)
//                .font(PhysicalFontResource.UBUNTU_MONO.toFont())
                .font(CP437TilesetResource.TAFFER_20X20.toFont())
                .deviceConfiguration(DeviceConfigurationBuilder.newBuilder()
                        .cursorBlinking(true)
                        .cursorStyle(CursorStyle.USE_CHARACTER_FOREGROUND)
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
        addScreenTitle(buttonsAndTextBoxesScreen, "Input controls");

        for (int i = 0; i < screens.size(); i++) {
            addNavigation(screens.get(i), screens, i);
        }

        // panels screen

        final Panel simplePanel = PANEL_TEMPLATE.createCopy()
                .position(Position.of(2, 4))
                .build();
        simplePanel.addComponent(LabelBuilder.newBuilder()
                .position(Position.OFFSET_1x1)
                .text("Simple panel")
                .build());
        panelsScreen.addComponent(simplePanel);

        final Panel boxedPanel = PANEL_TEMPLATE.createCopy()
                .title("Boxed panel")
                .position(Position.of(0, 2).relativeToBottomOf(simplePanel))
                .wrapInBox()
                .boxType(BoxType.DOUBLE)
                .build();
        panelsScreen.addComponent(boxedPanel);


        final Panel panelWithShadow = PANEL_TEMPLATE.createCopy()
                .position(Position.of(4, 0).relativeToRightOf(simplePanel))
                .addShadow()
                .build();
        panelWithShadow.addComponent(LabelBuilder.newBuilder()
                .text("Panel with shadow")
                .position(Position.of(1, 1))
                .build());
        panelsScreen.addComponent(panelWithShadow);


        final Panel panelWithShadowAndBox = PANEL_TEMPLATE.createCopy()
                .position(Position.of(0, 2).relativeToBottomOf(panelWithShadow))
                .addShadow()
                .title("Panel with shadow")
                .wrapInBox()
                .build();
        panelWithShadowAndBox.addComponent(LabelBuilder.newBuilder()
                .text("and box")
                .position(Position.of(0, 0))
                .build());
        panelsScreen.addComponent(panelWithShadowAndBox);


        final Panel borderedPanel = PANEL_TEMPLATE.createCopy()
                .title("Bordered panel")
                .position(Position.of(0, 2).relativeToBottomOf(boxedPanel))
                .addBorder(Modifiers.BORDER.of(SOLID))
                .build();
        borderedPanel.addComponent(LabelBuilder.newBuilder()
                .text("Bordered panel")
                .position(Position.OFFSET_1x1)
                .build());
        panelsScreen.addComponent(borderedPanel);

        final Panel borderedPanelWithShadow = PANEL_TEMPLATE.createCopy()
                .title("Bordered panel")
                .position(Position.of(0, 2).relativeToBottomOf(panelWithShadowAndBox))
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

        final Panel checkBoxesPanel = PANEL_TEMPLATE.createCopy()
                .position(Position.of(2, 4))
                .wrapInBox()
                .title("Check boxes")
                .addShadow()
                .build();
        for (int i = 0; i < 2; i++) {
            checkBoxesPanel.addComponent(CheckBoxBuilder.newBuilder()
                    .position(Position.of(0, i))
                    .text("Check " + (i + 1))
                    .build());
        }
        checkBoxesPanel.addComponent(CheckBoxBuilder.newBuilder()
                .position(Position.of(0, 2))
                .text("Too long text for this checkbox")
                .width(19)
                .build());
        buttonsAndTextBoxesScreen.addComponent(checkBoxesPanel);

        final Panel textBoxesPanel = PANEL_TEMPLATE.createCopy()
                .position(Position.of(0, 2).relativeToBottomOf(checkBoxesPanel))
                .wrapInBox()
                .title("Text boxes")
                .addShadow()
                .build();
        textBoxesPanel.addComponent(TextBoxBuilder.newBuilder()
                .text("Panel" + System.lineSeparator() + "with editable text box" + System.lineSeparator() + "...")
                .size(Size.of(13, 3))
                .build());
        buttonsAndTextBoxesScreen.addComponent(textBoxesPanel);

        final Panel buttonsPanel = PANEL_TEMPLATE.createCopy()
                .position(Position.of(0, 2).relativeToBottomOf(textBoxesPanel))
                .wrapInBox()
                .title("Buttons")
                .addShadow()
                .build();
        for(int i = 0; i < 3; i++) {
            buttonsPanel.addComponent(ButtonBuilder.newBuilder()
                    .position(Position.of(0, i))
                    .text("Button " + i)
                    .build());
        }
        buttonsAndTextBoxesScreen.addComponent(buttonsPanel);

        final Panel radioPanel = PANEL_TEMPLATE.createCopy()
                .position(Position.of(2, 0).relativeToRightOf(checkBoxesPanel))
                .wrapInBox()
                .title("Radio buttons")
                .addShadow()
                .build();
        radioPanel.addComponent(new DefaultRadioButton(
                "text",
                new LinkedList<>(),
                15,
                Position.DEFAULT_POSITION,
                ComponentStylesBuilder.DEFAULT));

        buttonsAndTextBoxesScreen.addComponent(radioPanel);

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
