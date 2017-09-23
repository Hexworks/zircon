package org.codetome.zircon.examples.interactive;

import org.codetome.zircon.api.Modifiers;
import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.Symbols;
import org.codetome.zircon.api.builder.ComponentStylesBuilder;
import org.codetome.zircon.api.builder.DeviceConfigurationBuilder;
import org.codetome.zircon.api.builder.LayerBuilder;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.component.*;
import org.codetome.zircon.api.component.RadioButtonGroup.Selection;
import org.codetome.zircon.api.component.builder.*;
import org.codetome.zircon.api.factory.TextColorFactory;
import org.codetome.zircon.api.font.Font;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.api.terminal.Terminal;
import org.codetome.zircon.api.terminal.config.CursorStyle;
import org.codetome.zircon.internal.component.impl.DefaultRadioButtonGroup;
import org.codetome.zircon.internal.graphics.BoxType;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.codetome.zircon.api.Modifiers.BorderType.DOTTED;
import static org.codetome.zircon.api.Modifiers.BorderType.SOLID;

public class ComponentsExample {

    private static final Size PANEL_SIZE = Size.of(22, 6);
    private static final Size TERMINAL_SIZE = Size.of(52, 28);
    private static final ColorTheme PANELS_THEME = ColorThemeRepository.SOLARIZED_LIGHT_ORANGE.getTheme();
    private static final ColorTheme INPUTS_THEME = ColorThemeRepository.SOLARIZED_DARK_GREEN.getTheme();
    private static final ColorTheme ADD_REMOVE_THEME = ColorThemeRepository.GHOST_OF_A_CHANCE.getTheme();
    private static final Font<BufferedImage> FONT = CP437TilesetResource.YOBBO_20X20.toFont();

    private static final PanelBuilder PANEL_TEMPLATE = PanelBuilder.newBuilder().size(PANEL_SIZE);

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Terminal terminal = TerminalBuilder.newBuilder()
                .initialTerminalSize(TERMINAL_SIZE)
//                .font(PhysicalFontResource.UBUNTU_MONO.toFont())
                .font(FONT)
                .deviceConfiguration(DeviceConfigurationBuilder.newBuilder()
                        .cursorBlinking(true)
                        .cursorStyle(CursorStyle.USE_CHARACTER_FOREGROUND)
                        .cursorColor(TextColorFactory.fromString("#ff00ff"))
                        .build())
                .buildTerminal();

        Screen panelsScreen = TerminalBuilder.newBuilder().createScreenFor(terminal);
        Screen inputsScreen = TerminalBuilder.newBuilder().createScreenFor(terminal);
        Screen addAndRemoveScreen = TerminalBuilder.newBuilder().createScreenFor(terminal);
        Screen colorThemesScreen = TerminalBuilder.newBuilder().createScreenFor(terminal);
        final List<Screen> screens = Arrays.asList(
                panelsScreen,
                inputsScreen,
                addAndRemoveScreen,
                colorThemesScreen);

        addScreenTitle(panelsScreen, "Panels");
        addScreenTitle(inputsScreen, "Input controls");
        addScreenTitle(addAndRemoveScreen, "Add and remove panels");
        addScreenTitle(colorThemesScreen, "Color themes");


        for (int i = 0; i < screens.size(); i++) {
            addNavigation(screens.get(i), screens, i);
        }

        // ==============
        // panels screen
        // ==============

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

        // ==============
        // inputs screen
        // ==============

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
        inputsScreen.addComponent(checkBoxesPanel);

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
        inputsScreen.addComponent(textBoxesPanel);

        final Panel buttonsPanel = PANEL_TEMPLATE.createCopy()
                .position(Position.of(0, 2).relativeToBottomOf(textBoxesPanel))
                .wrapInBox()
                .title("Buttons")
                .addShadow()
                .build();
        for (int i = 0; i < 3; i++) {
            buttonsPanel.addComponent(ButtonBuilder.newBuilder()
                    .position(Position.of(0, i))
                    .text("Button " + i)
                    .build());
        }
        inputsScreen.addComponent(buttonsPanel);

        final Panel radioPanel = PANEL_TEMPLATE.createCopy()
                .position(Position.of(2, 0).relativeToRightOf(checkBoxesPanel))
                .wrapInBox()
                .title("Radio buttons")
                .addShadow()
                .build();
        final RadioButtonGroup radios = new DefaultRadioButtonGroup(
                new LinkedList<>(),
                Size.of(15, 3),
                Position.DEFAULT_POSITION,
                ComponentStylesBuilder.DEFAULT);
        radioPanel.addComponent(radios);
        radios.addOption("bar", "Bar");
        radios.addOption("baz", "Baz");


        inputsScreen.addComponent(radioPanel);
        inputsScreen.applyTheme(INPUTS_THEME);

        // ==============
        // add/remove screen
        // ==============

        final Panel addAndRemovePanel = PANEL_TEMPLATE.createCopy()
                .position(Position.of(2, 4))
                .addShadow()
                .build();
        final Button addButton = ButtonBuilder.newBuilder()
                .text("Add new panel")
                .position(Position.OFFSET_1x1)
                .build();
        final Button clearButton = ButtonBuilder.newBuilder()
                .text("Remove all panels")
                .position(Position.of(1, 3))
                .build();
        addAndRemovePanel.addComponent(addButton);
        addAndRemovePanel.addComponent(clearButton);
        addAndRemoveScreen.addComponent(addAndRemovePanel);
        addAndRemoveScreen.applyTheme(ADD_REMOVE_THEME);

        final Deque<Position> remainingPositions = new LinkedList<>();
        final Deque<Position> usedPositions = new LinkedList<>();
        final Set<Panel> panels = new HashSet<>();

        for (int row = 11; row < 20; row += 7) {
            for (int col = 2; col < 40; col += 9) {
                remainingPositions.add(Position.of(col, row));
            }
        }

        addButton.onMouseReleased((mouseAction) -> {
            if (!remainingPositions.isEmpty()) {
                Position pos = remainingPositions.pop();
                usedPositions.push(pos);
                final Panel panel = createSmallPanel(pos);
                final Button closeButton = ButtonBuilder.newBuilder()
                        .position(Position.of(5, 0))
                        .text("X")
                        .build();
                closeButton.onMouseReleased((closeAction -> {
                    addAndRemoveScreen.removeComponent(panel);
                    usedPositions.remove(pos);
                    remainingPositions.push(pos);
                }));
                panel.addComponent(closeButton);
                panel.applyTheme(ADD_REMOVE_THEME);
                panels.add(panel);
                addAndRemoveScreen.addComponent(panel);
            }
        });

        clearButton.onMouseReleased((mouseAction -> {
            panels.forEach((addAndRemoveScreen::removeComponent));
            panels.clear();
            Iterator<Position> posIter = usedPositions.iterator();
            while (posIter.hasNext()) {
                Position next = posIter.next();
                posIter.remove();
                remainingPositions.push(next);
            }
        }));

        // ==============
        // add/remove screen
        // ==============

        AtomicReference<ColorThemeRepository> currentTheme = new AtomicReference<>(ColorThemeRepository.ADRIFT_IN_DREAMS);
        AtomicReference<Label> currentThemeLabel = new AtomicReference<>(createLabelForTheme(currentTheme.get()));

        final Panel infoPanel = PanelBuilder.newBuilder()
                .wrapInBox()
                .title("Current selection:")
                .size(Size.of(48, 3))
                .position(Position.of(2, 4))
                .build();

        infoPanel.addComponent(currentThemeLabel.get());



        colorThemesScreen.addComponent(infoPanel);


        final Size themePickerSize = Size.of(13, 19);
        final Panel solarizedLightPanel = PanelBuilder.newBuilder()
                .title("Sol. Light")
                .position(Position.of(0, 1).relativeToBottomOf(infoPanel))
                .wrapInBox()
                .size(themePickerSize)
                .build();
        final Panel solarizedDarkPanel = PanelBuilder.newBuilder()
                .title("Sol. Dark")
                .position(Position.of(1, 0).relativeToRightOf(solarizedLightPanel))
                .wrapInBox()
                .size(themePickerSize)
                .build();
        final Panel otherPanel = PanelBuilder.newBuilder()
                .title("Other")
                .position(Position.of(1, 0).relativeToRightOf(solarizedDarkPanel))
                .wrapInBox()
                .size(themePickerSize.plus(Size.of(7, 0)))
                .build();


        colorThemesScreen.addComponent(solarizedLightPanel);
        colorThemesScreen.addComponent(solarizedDarkPanel);
        colorThemesScreen.addComponent(otherPanel);

        final List<ColorThemeRepository> solarizedLightOptions = Arrays.stream(ColorThemeRepository.values())
                .filter((option) -> option.name().startsWith("SOLARIZED_LIGHT"))
                .collect(Collectors.toList());
        final List<ColorThemeRepository> solarizedDarkOptions = Arrays.stream(ColorThemeRepository.values())
                .filter((option) -> option.name().startsWith("SOLARIZED_DARK"))
                .collect(Collectors.toList());
        final List<ColorThemeRepository> otherOptions = Arrays.stream(ColorThemeRepository.values())
                .filter((option) -> !option.name().startsWith("SOLARIZED"))
                .collect(Collectors.toList());

        final RadioButtonGroup slOptions = RadioButtonGroupBuilder.newBuilder()
                .size(themePickerSize
                        .withRows(solarizedLightOptions.size())
                        .withRelativeColumns(-2))
                .build();
        solarizedLightOptions.forEach((option) -> slOptions.addOption(
                option.name(),
                option.name().replace("SOLARIZED_LIGHT_", "")));
        solarizedLightPanel.addComponent(slOptions);

        final RadioButtonGroup sdOptions = RadioButtonGroupBuilder.newBuilder()
                .size(themePickerSize
                        .withRows(solarizedDarkOptions.size())
                        .withRelativeColumns(-2))
                .build();
        solarizedDarkOptions.forEach((option) -> sdOptions.addOption(
                option.name(),
                option.name().replace("SOLARIZED_DARK_", "")));
        solarizedDarkPanel.addComponent(sdOptions);

        final RadioButtonGroup othOptions = RadioButtonGroupBuilder.newBuilder()
                .size(otherPanel.getBoundableSize()
                        .withRows(otherOptions.size())
                        .withRelativeColumns(-2))
                .build();
        otherOptions.forEach((option) -> othOptions.addOption(
                option.name(),
                option.name()));
        otherPanel.addComponent(othOptions);

        slOptions.onSelection((selection -> {
            refreshTheme(colorThemesScreen, currentTheme, currentThemeLabel, infoPanel, selection);
            sdOptions.clearSelection();
            othOptions.clearSelection();
        }));
        sdOptions.onSelection((selection -> {
            refreshTheme(colorThemesScreen, currentTheme, currentThemeLabel, infoPanel, selection);
            slOptions.clearSelection();
            othOptions.clearSelection();
        }));
        othOptions.onSelection((selection -> {
            refreshTheme(colorThemesScreen, currentTheme, currentThemeLabel, infoPanel, selection);
            slOptions.clearSelection();
            sdOptions.clearSelection();
        }));

        colorThemesScreen.applyTheme(currentTheme.get().getTheme());
        // ==============
        // display the first screen
        // ==============
        colorThemesScreen.display();
    }

    private static void refreshTheme(Screen screen,
                                     AtomicReference<ColorThemeRepository> themeRef,
                                     AtomicReference<Label> labelRef,
                                     Panel infoPanel,
                                     Selection selection) {
        themeRef.set(ColorThemeRepository.valueOf(selection.getKey()));
        infoPanel.removeComponent(labelRef.get());
        labelRef.set(createLabelForTheme(themeRef.get()));
        infoPanel.addComponent(labelRef.get());
        screen.applyTheme(themeRef.get().getTheme());
    }

    private static Label createLabelForTheme(ColorThemeRepository currentTheme) {
        return LabelBuilder.newBuilder()
                .text(currentTheme.name())
                .build();
    }

    private static Panel createSmallPanel(Position position) {
        Panel result = PanelBuilder.newBuilder()
                .size(Size.of(8, 6))
                .position(position)
                .addBorder(Modifiers.BORDER.of())
                .build();
        result.applyTheme(ADD_REMOVE_THEME);
        return result;
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
