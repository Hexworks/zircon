package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.application.Application;
import org.hexworks.zircon.api.builder.component.PanelBuilder;
import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.component.RadioButtonGroup.Selection;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.graphics.Layer;
import org.hexworks.zircon.api.graphics.Symbols;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.modifier.BorderType;
import org.hexworks.zircon.api.resource.CP437TilesetResource;
import org.hexworks.zircon.api.resource.ColorThemeResource;
import org.hexworks.zircon.api.screen.Screen;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class ComponentsExample {

    private static final Size PANEL_SIZE = Sizes.create(22, 6);
    private static final Size TERMINAL_SIZE = Sizes.create(52, 28);
    private static final CP437TilesetResource TILESET = CP437TilesetResource.ROGUE_YUN_16X16;
    private static final ColorTheme PANELS_THEME = ColorThemeResource.TECH_LIGHT.getTheme();
    private static final ColorTheme INPUTS_THEME = ColorThemeResource.SOLARIZED_DARK_GREEN.getTheme();
    private static final ColorTheme ADD_REMOVE_THEME = ColorThemeResource.GHOST_OF_A_CHANCE.getTheme();
    private static final ColorThemeResource THEME_PICKER_THEME = ColorThemeResource.GAMEBOOKERS;

    private static final PanelBuilder PANEL_TEMPLATE = PanelBuilder.Companion.newBuilder().size(PANEL_SIZE);

    public static void main(String[] args) {

        Application app = SwingApplications.startApplication(AppConfigs.newBuilder()
                .defaultTileset(TILESET)
                .defaultSize(TERMINAL_SIZE)
                .debugMode(true)
                .build());

        final TileGrid tileGrid = app.getTileGrid();

        Screen panelsScreen = Screens.createScreenFor(tileGrid);
        Screen inputsScreen = Screens.createScreenFor(tileGrid);
        Screen addAndRemoveScreen = Screens.createScreenFor(tileGrid);
        Screen colorThemesScreen = Screens.createScreenFor(tileGrid);
        Screen multiFontScreen = Screens.createScreenFor(tileGrid);
        final List<Screen> screens = Arrays.asList(
                panelsScreen,
                inputsScreen,
                addAndRemoveScreen,
                colorThemesScreen,
                multiFontScreen);

        addScreenTitle(panelsScreen, "Panels");
        addScreenTitle(inputsScreen, "Input controls");
        addScreenTitle(addAndRemoveScreen, "Add and remove panels");
        addScreenTitle(colorThemesScreen, "Color themes");
        addScreenTitle(multiFontScreen, "Multi-tileset");


        for (int i = 0; i < screens.size(); i++) {
            addNavigation(screens.get(i), screens, i);
        }

        // ==============
        // panels screen
        // ==============

        final Panel simplePanel = PANEL_TEMPLATE.createCopy()
                .position(Positions.create(2, 4))
                .build();
        simplePanel.addComponent(Components.newLabelBuilder()
                .position(Positions.offset1x1())
                .text("Simple panel")
                .build());
        panelsScreen.addComponent(simplePanel);

        final Panel boxedPanel = PANEL_TEMPLATE.createCopy()
                .title("Boxed panel")
                .position(Positions.create(0, 2).relativeToBottomOf(simplePanel))
                .wrapWithBox()
                .boxType(BoxType.DOUBLE)
                .build();
        panelsScreen.addComponent(boxedPanel);


        final Panel panelWithShadow = PANEL_TEMPLATE.createCopy()
                .position(Positions.create(4, 0).relativeToRightOf(simplePanel))
                .wrapWithShadow()
                .build();
        panelWithShadow.addComponent(Components.newLabelBuilder()
                .text("Panel with shadow")
                .position(Positions.create(1, 1))
                .build());
        panelsScreen.addComponent(panelWithShadow);


        final Panel panelWithShadowAndBox = PANEL_TEMPLATE.createCopy()
                .position(Positions.create(0, 2).relativeToBottomOf(panelWithShadow))
                .wrapWithShadow()
                .title("Panel with shadow")
                .wrapWithBox()
                .build();
        panelWithShadowAndBox.addComponent(Components.newLabelBuilder()
                .text("and box")
                .position(Positions.create(0, 0))
                .build());
        panelsScreen.addComponent(panelWithShadowAndBox);


        final Panel borderedPanel = PANEL_TEMPLATE.createCopy()
                .title("Bordered panel")
                .position(Positions.create(0, 2).relativeToBottomOf(boxedPanel))
                .addBorder(Modifiers.border())
                .build();
        borderedPanel.addComponent(Components.newLabelBuilder()
                .text("Bordered panel")
                .position(Positions.offset1x1())
                .build());
        panelsScreen.addComponent(borderedPanel);

        final Panel borderedPanelWithShadow = PANEL_TEMPLATE.createCopy()
                .title("Bordered panel")
                .position(Positions.create(0, 2).relativeToBottomOf(panelWithShadowAndBox))
                .addBorder(Borders.newBuilder().borderType(BorderType.DOTTED).build())
                .wrapWithShadow()
                .build();
        borderedPanelWithShadow.addComponent(Components.newLabelBuilder()
                .text("Border+shadow panel")
                .position(Positions.offset1x1())
                .build());
        panelsScreen.addComponent(borderedPanelWithShadow);

        panelsScreen.applyColorTheme(PANELS_THEME);

        // ==============
        // inputs screen
        // ==============

        final Panel checkBoxesPanel = PANEL_TEMPLATE.createCopy()
                .position(Positions.create(2, 4))
                .wrapWithBox()
                .title("Check boxes")
                .wrapWithShadow()
                .build();
        for (int i = 0; i < 2; i++) {
            checkBoxesPanel.addComponent(Components.newCheckBoxBuilder()
                    .position(Positions.create(0, i))
                    .text("Check " + (i + 1))
                    .build());
        }
        checkBoxesPanel.addComponent(Components.newCheckBoxBuilder()
                .position(Positions.create(0, 2))
                .text("Too long text for this checkbox")
                .width(19)
                .build());
        inputsScreen.addComponent(checkBoxesPanel);

        final Panel textBoxesPanel = PANEL_TEMPLATE.createCopy()
                .position(Positions.create(0, 2).relativeToBottomOf(checkBoxesPanel))
                .wrapWithBox()
                .title("Text boxes")
                .wrapWithShadow()
                .build();
        textBoxesPanel.addComponent(Components.newTextBoxBuilder()
                .text("Panel" + System.lineSeparator() + "with editable text box" + System.lineSeparator() + "...")
                .size(Sizes.create(13, 3))
                .build());
        inputsScreen.addComponent(textBoxesPanel);

        final Panel buttonsPanel = PANEL_TEMPLATE.createCopy()
                .position(Positions.create(0, 2).relativeToBottomOf(textBoxesPanel))
                .wrapWithBox()
                .title("Buttons")
                .wrapWithShadow()
                .build();
        for (int i = 0; i < 3; i++) {
            buttonsPanel.addComponent(Components.newButtonBuilder()
                    .position(Positions.create(0, i))
                    .text("Button " + i)
                    .build());
        }
        inputsScreen.addComponent(buttonsPanel);

        final Panel radioPanel = PANEL_TEMPLATE.createCopy()
                .position(Positions.create(2, 0).relativeToRightOf(checkBoxesPanel))
                .wrapWithBox()
                .title("Radio buttons")
                .wrapWithShadow()
                .build();
        final RadioButtonGroup radios = Components.newRadioButtonGroupBuilder()
                .size(Sizes.create(15, 3))
                .build();
        radioPanel.addComponent(radios);
        radios.addOption("bar", "Bar");
        radios.addOption("baz", "Baz");


        inputsScreen.addComponent(radioPanel);
        inputsScreen.applyColorTheme(INPUTS_THEME);

        // ==============
        // add/remove panel
        // ==============

        final Panel addAndRemovePanel = PANEL_TEMPLATE.createCopy()
                .position(Positions.create(2, 4))
                .wrapWithShadow()
                .build();
        final Button addButton = Components.newButtonBuilder()
                .text("Add new panel")
                .position(Positions.offset1x1())
                .build();
        final Button clearButton = Components.newButtonBuilder()
                .text("Remove all panels")
                .position(Positions.create(1, 3))
                .build();
        addAndRemovePanel.addComponent(addButton);
        addAndRemovePanel.addComponent(clearButton);
        addAndRemoveScreen.addComponent(addAndRemovePanel);
        addAndRemoveScreen.applyColorTheme(ADD_REMOVE_THEME);

        final Deque<Position> remainingPositions = new LinkedList<>();
        final Deque<Position> usedPositions = new LinkedList<>();
        final Set<Panel> panels = new HashSet<>();

        for (int row = 11; row < 20; row += 7) {
            for (int col = 2; col < 40; col += 9) {
                remainingPositions.add(Positions.create(col, row));
            }
        }

        addButton.onMouseReleased((mouseAction) -> {
            if (!remainingPositions.isEmpty()) {
                Position pos = remainingPositions.pop();
                usedPositions.push(pos);
                final Panel panel = createSmallPanel(pos);
                final Button closeButton = Components.newButtonBuilder()
                        .position(Positions.create(5, 0))
                        .text("X")
                        .build();
                closeButton.onMouseReleased((closeAction -> {
                    addAndRemoveScreen.removeComponent(panel);
                    usedPositions.remove(pos);
                    remainingPositions.push(pos);
                }));
                panel.addComponent(closeButton);
                panel.applyColorTheme(ADD_REMOVE_THEME);
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

        AtomicReference<ColorThemeResource> currentTheme = new AtomicReference<>(THEME_PICKER_THEME);
        AtomicReference<Label> currentThemeLabel = new AtomicReference<>(createLabelForTheme(currentTheme.get()));

        final Panel infoPanel = Components.newPanelBuilder()
                .wrapWithBox()
                .title("Current selection:")
                .size(Sizes.create(48, 3))
                .position(Positions.create(2, 4))
                .build();

        infoPanel.addComponent(currentThemeLabel.get());


        colorThemesScreen.addComponent(infoPanel);


        final Size themePickerSize = Sizes.create(13, 19);
        final Panel solarizedLightPanel = Components.newPanelBuilder()
                .title("Sol. Light")
                .position(Positions.create(0, 1).relativeToBottomOf(infoPanel))
                .wrapWithBox()
                .size(themePickerSize)
                .build();
        final Panel solarizedDarkPanel = Components.newPanelBuilder()
                .title("Sol. Dark")
                .position(Positions.create(1, 0).relativeToRightOf(solarizedLightPanel))
                .wrapWithBox()
                .size(themePickerSize)
                .build();
        final Panel otherPanel = Components.newPanelBuilder()
                .title("Other")
                .position(Positions.create(1, 0).relativeToRightOf(solarizedDarkPanel))
                .wrapWithBox()
                .size(themePickerSize.plus(Sizes.create(7, 0)))
                .build();


        colorThemesScreen.addComponent(solarizedLightPanel);
        colorThemesScreen.addComponent(solarizedDarkPanel);
        colorThemesScreen.addComponent(otherPanel);

        final List<ColorThemeResource> solarizedLightOptions = Arrays.stream(ColorThemeResource.values())
                .filter(option -> option.name().startsWith("SOLARIZED_LIGHT"))
                .collect(Collectors.toList());
        final List<ColorThemeResource> solarizedDarkOptions = Arrays.stream(ColorThemeResource.values())
                .filter((option) -> option.name().startsWith("SOLARIZED_DARK"))
                .collect(Collectors.toList());
        final List<ColorThemeResource> otherOptions = Arrays.stream(ColorThemeResource.values())
                .filter((option) -> !option.name().startsWith("SOLARIZED"))
                .collect(Collectors.toList());

        final RadioButtonGroup slOptions = Components.newRadioButtonGroupBuilder()
                .size(themePickerSize
                        .withYLength(solarizedLightOptions.size())
                        .withRelativeXLength(-2))
                .build();
        solarizedLightOptions.forEach((option) -> slOptions.addOption(
                option.name(),
                option.name().replace("SOLARIZED_LIGHT_", "")));
        solarizedLightPanel.addComponent(slOptions);

        final RadioButtonGroup sdOptions = Components.newRadioButtonGroupBuilder()
                .size(themePickerSize
                        .withYLength(solarizedDarkOptions.size())
                        .withRelativeXLength(-2))
                .build();
        solarizedDarkOptions.forEach((option) -> sdOptions.addOption(
                option.name(),
                option.name().replace("SOLARIZED_DARK_", "")));
        solarizedDarkPanel.addComponent(sdOptions);

        final RadioButtonGroup othOptions = Components.newRadioButtonGroupBuilder()
                .size(otherPanel.size()
                        .withYLength(otherOptions.size())
                        .withRelativeXLength(-2))
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

        colorThemesScreen.applyColorTheme(currentTheme.get().getTheme());

        // ==============
        // multi tileset screen
        // ==============


        final Panel exampleComponentsPanel = Components.newPanelBuilder()
                .wrapWithBox()
                .title("Example components")
                .size(Sizes.create(48, 22))
                .position(Positions.create(2, 4))
                .build();

        Label aLabel = Components.newLabelBuilder()
                .position(Positions.create(2, 1))
                .text("Something with 'a'!")
                .build();
//        final Layer aIcon = Layers.newBuilder()
//                .size(Sizes.create(1, 1))
//                .offset(exampleComponentsPanel.position().plus(Positions.create(2, 2)))
//                .tileset(GraphicTilesetResource.NETHACK_16X16.toFont(new PickRandomMetaStrategy()))
//                .build();
//        exampleComponentsPanel.addComponent(aLabel);
//        multiFontScreen.pushLayer(aIcon);
//        refreshIcon(aIcon, 'a');
//
//        Label bLabel = Components.newLabelBuilder()
//                .position(Positions.create(2, 2))
//                .text("Something with 'b'!")
//                .build();
//        final Layer bIcon = Layers.newBuilder()
//                .size(Sizes.create(1, 1))
//                .offset(exampleComponentsPanel.position().plus(Positions.create(2, 3)))
//                .tileset(GraphicTilesetResource.NETHACK_16X16.toFont(new PickRandomMetaStrategy()))
//                .build();
//        exampleComponentsPanel.addComponent(bLabel);
//        multiFontScreen.pushLayer(bIcon);
//        refreshIcon(bIcon, 'b');


        multiFontScreen.addComponent(exampleComponentsPanel);
        multiFontScreen.applyColorTheme(currentTheme.get().getTheme());

        multiFontScreen.display();
    }

    private static void refreshIcon(Layer icon, char c) {
        icon.setRelativeTileAt(Positions.defaultPosition(), Tiles.newBuilder()
                .character(c)
                .backgroundColor(TileColors.transparent())
                .build());
    }

    private static void refreshTheme(Screen screen,
                                     AtomicReference<ColorThemeResource> themeRef,
                                     AtomicReference<Label> labelRef,
                                     Panel infoPanel,
                                     Selection selection) {
        themeRef.set(ColorThemeResource.valueOf(selection.getKey()));
        infoPanel.removeComponent(labelRef.get());
        labelRef.set(createLabelForTheme(themeRef.get()));
        infoPanel.addComponent(labelRef.get());
        screen.applyColorTheme(themeRef.get().getTheme());
    }

    private static Label createLabelForTheme(ColorThemeResource currentTheme) {
        return Components.newLabelBuilder()
                .text(currentTheme.name())
                .build();
    }

    private static Panel createSmallPanel(Position position) {
        Panel result = Components.newPanelBuilder()
                .size(Sizes.create(8, 6))
                .position(position)
                .addBorder(Modifiers.border())
                .build();
        result.applyColorTheme(ADD_REMOVE_THEME);
        return result;
    }

    private static void addNavigation(Screen screen, List<Screen> screens, int currIdx) {
        if (currIdx > 0) {
            final Button prev = Components.newButtonBuilder()
                    .text(Symbols.TRIANGLE_LEFT_POINTING_BLACK + " Prev")
                    .position(Positions.create(33, 1))
                    .build();
            prev.onMouseReleased((a) -> screens.get(currIdx - 1).display());
            screen.addComponent(prev);
        }
        if (currIdx < screens.size() - 1) {
            final Button next = Components.newButtonBuilder()
                    .text("Next " + Symbols.TRIANGLE_RIGHT_POINTING_BLACK)
                    .position(Positions.create(42, 1))
                    .build();
            next.onMouseReleased((a) -> screens.get(currIdx + 1).display());
            screen.addComponent(next);
        }
    }

    private static void addScreenTitle(Screen screen, String title) {
        final Header header = Components.newHeaderBuilder()
                .text(title)
                .position(Positions.create(2, 1))
                .build();
        screen.addComponent(header);
    }
}
