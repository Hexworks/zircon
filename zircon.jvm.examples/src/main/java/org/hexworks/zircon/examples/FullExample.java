package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.Animations;
import org.hexworks.zircon.api.AppConfigs;
import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.DrawSurfaces;
import org.hexworks.zircon.api.Layers;
import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.REXPaintResources;
import org.hexworks.zircon.api.Screens;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.TileColors;
import org.hexworks.zircon.api.Tiles;
import org.hexworks.zircon.api.UIEventResponses;
import org.hexworks.zircon.api.animation.Animation;
import org.hexworks.zircon.api.animation.AnimationResource;
import org.hexworks.zircon.api.application.Application;
import org.hexworks.zircon.api.builder.animation.AnimationBuilder;
import org.hexworks.zircon.api.builder.component.PanelBuilder;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.component.Header;
import org.hexworks.zircon.api.component.Label;
import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.component.RadioButtonGroup;
import org.hexworks.zircon.api.component.RadioButtonGroup.Selection;
import org.hexworks.zircon.api.component.TextBox;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.graphics.Layer;
import org.hexworks.zircon.api.graphics.Symbols;
import org.hexworks.zircon.api.graphics.TileGraphics;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.internal.resource.ColorThemeResource;
import org.hexworks.zircon.api.resource.REXPaintResource;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.api.uievent.MouseEventType;
import org.hexworks.zircon.internal.animation.DefaultAnimationFrame;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.hexworks.zircon.internal.resource.ColorThemeResource.AFTERGLOW;
import static org.hexworks.zircon.internal.resource.ColorThemeResource.AFTER_THE_HEIST;
import static org.hexworks.zircon.internal.resource.ColorThemeResource.AMIGA_OS;
import static org.hexworks.zircon.internal.resource.ColorThemeResource.ENTRAPPED_IN_A_PALETTE;
import static org.hexworks.zircon.internal.resource.ColorThemeResource.FOREST;
import static org.hexworks.zircon.internal.resource.ColorThemeResource.GAMEBOOKERS;
import static org.hexworks.zircon.internal.resource.ColorThemeResource.GHOST_OF_A_CHANCE;
import static org.hexworks.zircon.internal.resource.ColorThemeResource.LET_THEM_EAT_CAKE;
import static org.hexworks.zircon.internal.resource.ColorThemeResource.MONOKAI_BLUE;
import static org.hexworks.zircon.internal.resource.ColorThemeResource.MONOKAI_YELLOW;
import static org.hexworks.zircon.internal.resource.ColorThemeResource.OLIVE_LEAF_TEA;
import static org.hexworks.zircon.internal.resource.ColorThemeResource.PABLO_NERUDA;
import static org.hexworks.zircon.internal.resource.ColorThemeResource.SOLARIZED_LIGHT_CYAN;
import static org.hexworks.zircon.internal.resource.ColorThemeResource.TRON;
import static org.hexworks.zircon.internal.resource.ColorThemeResource.ZENBURN_VANILLA;
import static org.hexworks.zircon.internal.resource.ColorThemeResource.valueOf;
import static org.hexworks.zircon.internal.resource.ColorThemeResource.values;

public class FullExample {

    private static final Size PANEL_SIZE = Sizes.create(29, 8);
    private static final Size SCREEN_SIZE = Sizes.create(65, 33);
    private static final TilesetResource TILESET = CP437TilesetResources.rogueYun16x16();
    private static final ColorTheme INTRO_THEME = TRON.getTheme();
    private static final ColorTheme PANELS_THEME = AMIGA_OS.getTheme();
    private static final ColorTheme INPUTS_THEME = GAMEBOOKERS.getTheme();
    private static final ColorTheme ADD_REMOVE_THEME = SOLARIZED_LIGHT_CYAN.getTheme();
    private static final ColorThemeResource THEME_PICKER_THEME = MONOKAI_YELLOW;
    private static final ColorTheme GAME_THEME = OLIVE_LEAF_TEA.getTheme();

    private static final PanelBuilder PANEL_TEMPLATE = PanelBuilder.Companion.newBuilder().withSize(PANEL_SIZE);

    public static void main(String[] args) {

        Application app = SwingApplications.startApplication(AppConfigs.newConfig()
                .withDefaultTileset(TILESET)
                .withSize(SCREEN_SIZE)
                .withDebugMode(true)
                .enableBetaFeatures()
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
        TileGraphics img = DrawSurfaces.tileGraphicsBuilder().withSize(SCREEN_SIZE).build();
        rex.toLayerList(CP437TilesetResources.rogueYun16x16()).forEach(layer -> img.draw(layer, Positions.zero()));
        AnimationBuilder splashAnimBuilder = Animations.newBuilder();

        for (int i = 20; i >= 0; i--) {
            final int idx = i;
            final int loopCount = i == 1 ? 40 : 1;
            splashAnimBuilder.addFrame(
                    new DefaultAnimationFrame(
                            SCREEN_SIZE,
                            Collections.singletonList(Layers.newBuilder()
                                    .withTileGraphics(img.toTileImage().transform(tile -> tile.withBackgroundColor(tile.getBackgroundColor()
                                            .darkenByPercent(idx / 20d))
                                            .withForegroundColor(tile.getForegroundColor()
                                                    .darkenByPercent(idx / 20d))).toTileGraphic())
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
                                    .withTileGraphics(img.toTileImage().transform(tile -> tile.withBackgroundColor(tile.getBackgroundColor()
                                            .darkenByPercent(idx / 20d))
                                            .withForegroundColor(tile.getForegroundColor()
                                                    .darkenByPercent(idx / 20d))).toTileGraphic())
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
                TILESET).withLoopCount(0);
        for (int i = 0; i < skullAnimBuilder.getTotalFrameCount(); i++) {
            skullAnimBuilder.addPosition(Positions.create(2, 6));
        }
        Animation skullAnim = skullAnimBuilder.build();

        Panel introPanel = Components.panel()
                .withPosition(Positions.create(17, 3))
                .withSize(SCREEN_SIZE.withRelativeWidth(-18).withRelativeHeight(-4))
                .withBoxType(BoxType.SINGLE)
                .wrapWithBox(true)
                .build();

        introPanel.addComponent(Components.header()
                .withPosition(Positions.offset1x1())
                .withText("Do you plan to make a roguelike?")
                .build());

        TextBox introBox = Components.textBox()
                .withPosition(Positions.create(1, 3))
                .withContentWidth(44)
                .addParagraph("Look no further. Zircon is the right tool for the job.")
                .addParagraph("Zircon is a Text GUI library and a Tile Engine which is designed for simplicity and ease of use.")
                .addParagraph("It is usable out of the box for all JVM languages including Java, Kotlin, Clojure and Scala.")
                .addParagraph("Things Zircon knows:")
                .addListItem("Animations")
                .addListItem("A Component System with built-in components for games")
                .addListItem("Layering")
                .addListItem("Mouse and keyboard support")
                .addListItem("Shape and Box drawing")
                .addListItem("Tilesets, and Graphical tiles")
                .addListItem("REXPaint file loading")
                .addListItem("Color Themes and more!")
                .addNewLine()
                .addParagraph("Interested in more details? Read on...", false)
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
                .withPosition(Positions.create(2, 4))
                .build();
        simplePanel.addComponent(Components.label()
                .withPosition(Positions.offset1x1())
                .withText("Simple panel")
                .build());
        panelsScreen.addComponent(simplePanel);

        final Panel boxedPanel = PANEL_TEMPLATE.createCopy()
                .withTitle("Boxed panel")
                .withPosition(Positions.create(0, 2).relativeToBottomOf(simplePanel))
                .wrapWithBox(true)
                .withBoxType(BoxType.DOUBLE)
                .build();
        panelsScreen.addComponent(boxedPanel);


        final Panel panelWithShadow = PANEL_TEMPLATE.createCopy()
                .withPosition(Positions.create(4, 0).relativeToRightOf(simplePanel))
                .wrapWithShadow(true)
                .build();
        panelWithShadow.addComponent(Components.label()
                .withText("Panel with shadow")
                .withPosition(Positions.create(1, 1))
                .build());
        panelsScreen.addComponent(panelWithShadow);


        final Panel panelWithShadowAndBox = PANEL_TEMPLATE.createCopy()
                .withPosition(Positions.create(0, 2).relativeToBottomOf(panelWithShadow))
                .wrapWithShadow(true)
                .withTitle("Panel with shadow")
                .wrapWithBox(true)
                .build();
        panelWithShadowAndBox.addComponent(Components.label()
                .withText("and box")
                .withPosition(Positions.create(0, 0))
                .build());
        panelsScreen.addComponent(panelWithShadowAndBox);


        final Panel borderedPanel = PANEL_TEMPLATE.createCopy()
                .withTitle("Bordered panel")
                .withPosition(Positions.create(0, 2).relativeToBottomOf(boxedPanel))
                .build();
        borderedPanel.addComponent(Components.label()
                .withText("Bordered panel")
                .withPosition(Positions.offset1x1())
                .build());
        panelsScreen.addComponent(borderedPanel);

        final Panel borderedPanelWithShadow = PANEL_TEMPLATE.createCopy()
                .withTitle("Bordered panel")
                .withPosition(Positions.create(0, 2).relativeToBottomOf(panelWithShadowAndBox))
                .wrapWithShadow(true)
                .build();
        borderedPanelWithShadow.addComponent(Components.label()
                .withText("Border+shadow panel")
                .withPosition(Positions.offset1x1())
                .build());
        panelsScreen.addComponent(borderedPanelWithShadow);

        panelsScreen.applyColorTheme(PANELS_THEME);

        // ==============
        // inputs screen
        // ==============

        final Panel checkBoxesPanel = PANEL_TEMPLATE.createCopy()
                .withPosition(Positions.create(2, 4))
                .wrapWithBox(true)
                .withTitle("Check boxes")
                .wrapWithShadow(true)
                .build();
        for (int i = 0; i < 2; i++) {
            checkBoxesPanel.addComponent(Components.checkBox()
                    .withPosition(Positions.create(0, i))
                    .withText("Check " + (i + 1))
                    .build());
        }
        checkBoxesPanel.addComponent(Components.checkBox()
                .withPosition(Positions.create(0, 2))
                .withText("Too long text for this checkbox")
                .withWidth(19)
                .build());
        inputsScreen.addComponent(checkBoxesPanel);

        final Panel textBoxesPanel = PANEL_TEMPLATE.createCopy()
                .withPosition(Positions.create(0, 2).relativeToBottomOf(checkBoxesPanel))
                .wrapWithBox(true)
                .withTitle("Text boxes")
                .wrapWithShadow(true)
                .build();
        textBoxesPanel.addComponent(Components.textArea()
                .withText("Panel" + System.lineSeparator() + "with editable text box" + System.lineSeparator() + "...")
                .withSize(Sizes.create(13, 3))
                .build());
        inputsScreen.addComponent(textBoxesPanel);

        final Panel buttonsPanel = PANEL_TEMPLATE.createCopy()
                .withPosition(Positions.create(0, 2).relativeToBottomOf(textBoxesPanel))
                .wrapWithBox(true)
                .withTitle("Buttons")
                .wrapWithShadow(true)
                .build();
        for (int i = 0; i < 3; i++) {
            buttonsPanel.addComponent(Components.button()
                    .withPosition(Positions.create(0, i))
                    .withText("Button " + i)
                    .build());
        }
        inputsScreen.addComponent(buttonsPanel);

        final Panel radioPanel = PANEL_TEMPLATE.createCopy()
                .withPosition(Positions.create(2, 0).relativeToRightOf(checkBoxesPanel))
                .wrapWithBox(true)
                .withTitle("Radio buttons")
                .wrapWithShadow(true)
                .build();
        final RadioButtonGroup radios = Components.radioButtonGroup()
                .withSize(Sizes.create(15, 3))
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
                .withPosition(Positions.create(2, 4))
                .wrapWithShadow(true)
                .build();
        final Button addButton = Components.button()
                .withText("Add new panel")
                .withPosition(Positions.offset1x1())
                .build();
        final Button clearButton = Components.button()
                .withText("Remove all panels")
                .withPosition(Positions.create(1, 3))
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

        addButton.handleMouseEvents(MouseEventType.MOUSE_RELEASED, (event, phase) -> {
            if (!remainingPositions.isEmpty()) {
                Position pos = remainingPositions.pop();
                usedPositions.push(pos);
                final Panel panel = createSmallPanel(pos);
                final Button closeButton = Components.button()
                        .withPosition(Positions.create(5, 0))
                        .withText("X")
                        .build();
                closeButton.handleMouseEvents(MouseEventType.MOUSE_RELEASED, (e, p) -> {
                    addAndRemoveScreen.removeComponent(panel);
                    usedPositions.remove(pos);
                    remainingPositions.push(pos);
                    return UIEventResponses.processed();
                });
                panel.addComponent(closeButton);
                panel.applyColorTheme(ADD_REMOVE_THEME);
                panels.add(panel);
                addAndRemoveScreen.addComponent(panel);
            }
            return UIEventResponses.processed();
        });

        clearButton.handleMouseEvents(MouseEventType.MOUSE_RELEASED, (event, phase) -> {
            panels.forEach((addAndRemoveScreen::removeComponent));
            panels.clear();
            Iterator<Position> posIter = usedPositions.iterator();
            while (posIter.hasNext()) {
                Position next = posIter.next();
                posIter.remove();
                remainingPositions.push(next);
            }
            return UIEventResponses.processed();
        });

        // ==============
        // add/remove screen
        // ==============

        AtomicReference<ColorThemeResource> currentTheme = new AtomicReference<>(THEME_PICKER_THEME);
        AtomicReference<Label> currentThemeLabel = new AtomicReference<>(createLabelForTheme(currentTheme.get()));

        final Panel infoPanel = Components.panel()
                .wrapWithBox(true)
                .withTitle("Current selection:")
                .withSize(Sizes.create(62, 3))
                .withPosition(Positions.create(2, 4))
                .build();

        infoPanel.addComponent(currentThemeLabel.get());


        colorThemesScreen.addComponent(infoPanel);


        final Size themePickerSize = Sizes.create(17, 24);
        final Panel solarizedLightPanel = Components.panel()
                .withTitle("Sol. Light")
                .withPosition(Positions.create(0, 1).relativeToBottomOf(infoPanel))
                .wrapWithBox(true)
                .withSize(themePickerSize)
                .build();
        final Panel solarizedDarkPanel = Components.panel()
                .withTitle("Sol. Dark")
                .withPosition(Positions.create(1, 0).relativeToRightOf(solarizedLightPanel))
                .wrapWithBox(true)
                .withSize(themePickerSize)
                .build();
        final Panel otherPanel = Components.panel()
                .withTitle("Other")
                .withPosition(Positions.create(1, 0).relativeToRightOf(solarizedDarkPanel))
                .wrapWithBox(true)
                .withSize(themePickerSize.plus(Sizes.create(9, 0)))
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
                .withSize(themePickerSize
                        .withHeight(solarizedLightOptions.size())
                        .withRelativeWidth(-2))
                .build();
        solarizedLightOptions.forEach((option) -> slOptions.addOption(
                option.name(),
                option.name().replace("SOLARIZED_LIGHT_", "")));
        solarizedLightPanel.addComponent(slOptions);

        final RadioButtonGroup sdOptions = Components.radioButtonGroup()
                .withSize(themePickerSize
                        .withHeight(solarizedDarkOptions.size())
                        .withRelativeWidth(-2))
                .build();
        solarizedDarkOptions.forEach((option) -> sdOptions.addOption(
                option.name(),
                option.name().replace("SOLARIZED_DARK_", "")));
        solarizedDarkPanel.addComponent(sdOptions);

        final RadioButtonGroup othOptions = Components.radioButtonGroup()
                .withSize(otherPanel.getSize()
                        .withHeight(otherOptions.size())
                        .withRelativeWidth(-2))
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

        gameScreen.applyColorTheme(GAME_THEME);

    }

    private static void refreshIcon(Layer icon, char c) {
        icon.setAbsoluteTileAt(Positions.zero(), Tiles.newBuilder()
                .withCharacter(c)
                .withBackgroundColor(TileColors.transparent())
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
                .withText(currentTheme.name())
                .build();
    }

    private static Panel createSmallPanel(Position position) {
        Panel result = Components.panel()
                .withSize(Sizes.create(8, 6))
                .withPosition(position)
                .build();
        result.applyColorTheme(ADD_REMOVE_THEME);
        return result;
    }

    private static void addNavigation(Screen screen, List<Screen> screens, int currIdx) {
        if (currIdx > 0) {
            final Button prev = Components.button()
                    .withText(Symbols.TRIANGLE_LEFT_POINTING_BLACK + " Prev")
                    .withPosition(Positions.create(46, 1))
                    .build();
            prev.handleMouseEvents(MouseEventType.MOUSE_RELEASED, (event, phase) -> {
                screens.get(currIdx - 1).display();
                return UIEventResponses.processed();
            });
            screen.addComponent(prev);
        }
        if (currIdx < screens.size() - 1) {
            final Button next = Components.button()
                    .withText("Next " + Symbols.TRIANGLE_RIGHT_POINTING_BLACK)
                    .withPosition(Positions.create(56, 1))
                    .build();
            next.handleMouseEvents(MouseEventType.MOUSE_RELEASED, (event, phase) -> {
                screens.get(currIdx + 1).display();
                return UIEventResponses.processed();
            });
            screen.addComponent(next);
        }
    }

    private static void addScreenTitle(Screen screen, String title) {
        final Header header = Components.header()
                .withText(title)
                .withPosition(Positions.create(2, 1))
                .build();
        screen.addComponent(header);
    }
}
