package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.animation.Animation;
import org.hexworks.zircon.api.animation.AnimationResource;
import org.hexworks.zircon.api.application.Application;
import org.hexworks.zircon.api.builder.animation.AnimationBuilder;
import org.hexworks.zircon.api.builder.component.PanelBuilder;
import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.component.RadioButtonGroup.Selection;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.graphics.Layer;
import org.hexworks.zircon.api.graphics.Symbols;
import org.hexworks.zircon.api.graphics.TileGraphic;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.modifier.BorderType;
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource;
import org.hexworks.zircon.api.resource.ColorThemeResource;
import org.hexworks.zircon.api.resource.REXPaintResource;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.internal.animation.DefaultAnimationFrame;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.hexworks.zircon.api.resource.ColorThemeResource.*;

public class FullExample {

    private static final Size PANEL_SIZE = Sizes.create(29, 8);
    private static final Size SCREEN_SIZE = Sizes.create(65, 33);
    private static final TilesetResource TILESET = BuiltInCP437TilesetResource.ROGUE_YUN_16X16;
    private static final ColorTheme INTRO_THEME = TRON.getTheme();
    private static final ColorTheme PANELS_THEME = AMIGA_OS.getTheme();
    private static final ColorTheme INPUTS_THEME = GAMEBOOKERS.getTheme();
    private static final ColorTheme ADD_REMOVE_THEME = SOLARIZED_LIGHT_CYAN.getTheme();
    private static final ColorThemeResource THEME_PICKER_THEME = MONOKAI_YELLOW;
    private static final ColorTheme GAME_THEME = OLIVE_LEAF_TEA.getTheme();

    private static final PanelBuilder PANEL_TEMPLATE = PanelBuilder.Companion.newBuilder().size(PANEL_SIZE);

    public static void main(String[] args) {

        Application app = SwingApplications.startApplication(AppConfigs.newConfig()
                .defaultTileset(TILESET)
                .defaultSize(SCREEN_SIZE)
                .debugMode(true)
                .build());

        final TileGrid tileGrid = app.getTileGrid();

        Screen splashScreen = Screens.createScreenFor(tileGrid);
        Screen introScreen = Screens.createScreenFor(tileGrid);
        Screen panelsScreen = Screens.createScreenFor(tileGrid);
        Screen inputsScreen = Screens.createScreenFor(tileGrid);
        Screen addAndRemoveScreen = Screens.createScreenFor(tileGrid);
        Screen colorThemesScreen = Screens.createScreenFor(tileGrid);
        Screen gameScreen = Screens.createScreenFor(tileGrid);
        final List<Screen> screens = Arrays.asList(
                introScreen,
                panelsScreen,
                inputsScreen,
                addAndRemoveScreen,
                colorThemesScreen,
                gameScreen);

        addScreenTitle(introScreen, "Zircon: a user-friendly Text GUI & Tile Engine");
        addScreenTitle(panelsScreen, "Panels");
        addScreenTitle(inputsScreen, "Input controls");
        addScreenTitle(addAndRemoveScreen, "Add and remove panels");
        addScreenTitle(colorThemesScreen, "Color themes");
        addScreenTitle(gameScreen, "Game");


        for (int i = 0; i < screens.size(); i++) {
            addNavigation(screens.get(i), screens, i);
        }

        // ==============
        // splash screen
        // ==============

        REXPaintResource rex = REXPaintResources.loadREXFile(RexLoaderExample.class.getResourceAsStream("/rex_files/zircon_logo.xp"));


        TileGraphic img = TileGraphics.newBuilder().size(SCREEN_SIZE).build();

        rex.toLayerList(CP437TilesetResources.rogueYun16x16()).forEach(layer -> img.draw(layer, Positions.defaultPosition()));

        AnimationBuilder splashAnimBuilder = Animations.newBuilder();

        for (int i = 20; i >= 0; i--) {
            final int idx = i;
            final int loopCount = i == 1 ? 40 : 1;
            splashAnimBuilder.addFrame(
                    new DefaultAnimationFrame(
                            SCREEN_SIZE,
                            Collections.singletonList(Layers.newBuilder()
                                    .tileGraphic(img.transform(tile -> tile.withBackgroundColor(tile.getBackgroundColor()
                                            .darkenByPercent(idx / 20d))
                                            .withForegroundColor(tile.getForegroundColor()
                                                    .darkenByPercent(idx / 20d))))
                                    .build()),
                            loopCount));
        }

        for (int i = 0; i <= 20; i++) {
            final int idx = i;
            final int loopCount = i == 20 ? 10 : 1;
            splashAnimBuilder.addFrame(
                    new DefaultAnimationFrame(
                            SCREEN_SIZE,
                            Collections.singletonList(Layers.newBuilder()
                                    .tileGraphic(img.transform(tile -> tile.withBackgroundColor(tile.getBackgroundColor()
                                            .darkenByPercent(idx / 20d))
                                            .withForegroundColor(tile.getForegroundColor()
                                                    .darkenByPercent(idx / 20d))))
                                    .build()),
                            loopCount));
        }

        Animation splashAnim = splashAnimBuilder
                .setPositionForAll(Positions.create(2, 2))
                .build();

        splashScreen.display();


        // ==============
        // intro screen
        // ==============

        AnimationBuilder skullAnimBuilder = AnimationResource.loadAnimationFromStream(
                AnimationExample.class.getResourceAsStream("/animations/skull.zap"),
                TILESET).loopCount(0);
        for (int i = 0; i < skullAnimBuilder.getTotalFrameCount(); i++) {
            skullAnimBuilder.addPosition(Positions.create(2, 6));
        }
        Animation skullAnim = skullAnimBuilder.build();

        Panel introPanel = Components.panel()
                .position(Positions.create(17, 3))
                .size(SCREEN_SIZE.withRelativeXLength(-18).withRelativeYLength(-4))
                .boxType(BoxType.SINGLE)
                .wrapWithBox()
                .build();

        introPanel.addComponent(Components.header()
                .position(Positions.offset1x1())
                .text("Do you plan to make a roguelike?")
                .build());

        TextBox introBox = Components.textBox()
                .position(Positions.create(1, 3))
                .size(Sizes.create(44, 25))
                .paragraph("Look no further. Zircon is the right tool for the job.")
                .newLine()
                .paragraph("Zircon is a Text GUI library and a Tile Engine which is designed for simplicity and ease of use.")
                .newLine()
                .paragraph("It is usable out of the box for all JVM languages including Java, Kotlin, Clojure and Scala.")
                .newLine()
                .paragraph("Things Zircon knows:")
                .newLine()
                .listItem("Animations")
                .listItem("A Component System with built-in components for games")
                .listItem("Layering")
                .listItem("Mouse and keyboard support")
                .listItem("Shape and Box drawing")
                .listItem("Tilesets, and Graphical tiles")
                .listItem("REXPaint file loading")
                .listItem("Color Themes and more!")
                .newLine()
                .paragraph("Interested in more details? Read on...", false)
                .build();

        introPanel.addComponent(introBox);

        introScreen.addComponent(introPanel);

        introScreen.applyColorTheme(INTRO_THEME);

        splashScreen.startAnimation(splashAnim).onFinished(info -> {
            introScreen.display();
            introScreen.startAnimation(skullAnim);
        });


        // ==============
        // panels screen
        // ==============

        final Panel simplePanel = PANEL_TEMPLATE.createCopy()
                .position(Positions.create(2, 4))
                .build();
        simplePanel.addComponent(Components.label()
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
        panelWithShadow.addComponent(Components.label()
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
        panelWithShadowAndBox.addComponent(Components.label()
                .text("and box")
                .position(Positions.create(0, 0))
                .build());
        panelsScreen.addComponent(panelWithShadowAndBox);


        final Panel borderedPanel = PANEL_TEMPLATE.createCopy()
                .title("Bordered panel")
                .position(Positions.create(0, 2).relativeToBottomOf(boxedPanel))
                .addBorder(Modifiers.border())
                .build();
        borderedPanel.addComponent(Components.label()
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
        borderedPanelWithShadow.addComponent(Components.label()
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
            checkBoxesPanel.addComponent(Components.checkBox()
                    .position(Positions.create(0, i))
                    .text("Check " + (i + 1))
                    .build());
        }
        checkBoxesPanel.addComponent(Components.checkBox()
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
        textBoxesPanel.addComponent(Components.textArea()
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
            buttonsPanel.addComponent(Components.button()
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
        final RadioButtonGroup radios = Components.radioButtonGroup()
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
        final Button addButton = Components.button()
                .text("Add new panel")
                .position(Positions.offset1x1())
                .build();
        final Button clearButton = Components.button()
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

        for (int row = 13; row < 22; row += 7) {
            for (int col = 2; col < 40; col += 9) {
                remainingPositions.add(Positions.create(col, row));
            }
        }

        addButton.onMouseReleased((mouseAction) -> {
            if (!remainingPositions.isEmpty()) {
                Position pos = remainingPositions.pop();
                usedPositions.push(pos);
                final Panel panel = createSmallPanel(pos);
                final Button closeButton = Components.button()
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

        final Panel infoPanel = Components.panel()
                .wrapWithBox()
                .title("Current selection:")
                .size(Sizes.create(62, 3))
                .position(Positions.create(2, 4))
                .build();

        infoPanel.addComponent(currentThemeLabel.get());


        colorThemesScreen.addComponent(infoPanel);


        final Size themePickerSize = Sizes.create(17, 24);
        final Panel solarizedLightPanel = Components.panel()
                .title("Sol. Light")
                .position(Positions.create(0, 1).relativeToBottomOf(infoPanel))
                .wrapWithBox()
                .size(themePickerSize)
                .build();
        final Panel solarizedDarkPanel = Components.panel()
                .title("Sol. Dark")
                .position(Positions.create(1, 0).relativeToRightOf(solarizedLightPanel))
                .wrapWithBox()
                .size(themePickerSize)
                .build();
        final Panel otherPanel = Components.panel()
                .title("Other")
                .position(Positions.create(1, 0).relativeToRightOf(solarizedDarkPanel))
                .wrapWithBox()
                .size(themePickerSize.plus(Sizes.create(9, 0)))
                .build();


        colorThemesScreen.addComponent(solarizedLightPanel);
        colorThemesScreen.addComponent(solarizedDarkPanel);
        colorThemesScreen.addComponent(otherPanel);

        final List<ColorThemeResource> solarizedLightOptions = Arrays.stream(values())
                .filter(option -> option.name().startsWith("SOLARIZED_LIGHT"))
                .collect(Collectors.toList());
        final List<ColorThemeResource> solarizedDarkOptions = Arrays.stream(values())
                .filter((option) -> option.name().startsWith("SOLARIZED_DARK"))
                .collect(Collectors.toList());
        final List<ColorThemeResource> otherOptions = Arrays.asList(
                ZENBURN_VANILLA,
                MONOKAI_BLUE,
                PABLO_NERUDA,
                AFTER_THE_HEIST,
                AFTERGLOW,
                LET_THEM_EAT_CAKE,
                OLIVE_LEAF_TEA,
                GAMEBOOKERS,
                TRON,
                AMIGA_OS,
                ENTRAPPED_IN_A_PALETTE,
                FOREST,
                GHOST_OF_A_CHANCE);

        final RadioButtonGroup slOptions = Components.radioButtonGroup()
                .size(themePickerSize
                        .withYLength(solarizedLightOptions.size())
                        .withRelativeXLength(-2))
                .build();
        solarizedLightOptions.forEach((option) -> slOptions.addOption(
                option.name(),
                option.name().replace("SOLARIZED_LIGHT_", "")));
        solarizedLightPanel.addComponent(slOptions);

        final RadioButtonGroup sdOptions = Components.radioButtonGroup()
                .size(themePickerSize
                        .withYLength(solarizedDarkOptions.size())
                        .withRelativeXLength(-2))
                .build();
        solarizedDarkOptions.forEach((option) -> sdOptions.addOption(
                option.name(),
                option.name().replace("SOLARIZED_DARK_", "")));
        solarizedDarkPanel.addComponent(sdOptions);

        final RadioButtonGroup othOptions = Components.radioButtonGroup()
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


        IsometricGameArea.INSTANCE.addGamePanel(gameScreen, Positions.create(2, 4), Sizes.create(62, 28), Positions.create(5, 5));

        gameScreen.applyColorTheme(GAME_THEME);

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
        themeRef.set(valueOf(selection.getKey()));
        infoPanel.removeComponent(labelRef.get());
        labelRef.set(createLabelForTheme(themeRef.get()));
        infoPanel.addComponent(labelRef.get());
        screen.applyColorTheme(themeRef.get().getTheme());
    }

    private static Label createLabelForTheme(ColorThemeResource currentTheme) {
        return Components.label()
                .text(currentTheme.name())
                .build();
    }

    private static Panel createSmallPanel(Position position) {
        Panel result = Components.panel()
                .size(Sizes.create(8, 6))
                .position(position)
                .addBorder(Modifiers.border())
                .build();
        result.applyColorTheme(ADD_REMOVE_THEME);
        return result;
    }

    private static void addNavigation(Screen screen, List<Screen> screens, int currIdx) {
        if (currIdx > 0) {
            final Button prev = Components.button()
                    .text(Symbols.TRIANGLE_LEFT_POINTING_BLACK + " Prev")
                    .position(Positions.create(46, 1))
                    .build();
            prev.onMouseReleased((a) -> screens.get(currIdx - 1).display());
            screen.addComponent(prev);
        }
        if (currIdx < screens.size() - 1) {
            final Button next = Components.button()
                    .text("Next " + Symbols.TRIANGLE_RIGHT_POINTING_BLACK)
                    .position(Positions.create(56, 1))
                    .build();
            next.onMouseReleased((a) -> screens.get(currIdx + 1).display());
            screen.addComponent(next);
        }
    }

    private static void addScreenTitle(Screen screen, String title) {
        final Header header = Components.header()
                .text(title)
                .position(Positions.create(2, 1))
                .build();
        screen.addComponent(header);
    }
}
