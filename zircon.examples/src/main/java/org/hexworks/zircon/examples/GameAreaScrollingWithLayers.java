package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.application.Application;
import org.hexworks.zircon.api.builder.component.GameComponentBuilder;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.component.ComponentBuilder;
import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.data.impl.Position3D;
import org.hexworks.zircon.api.data.impl.Size3D;
import org.hexworks.zircon.api.game.GameArea;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.graphics.Layer;
import org.hexworks.zircon.api.graphics.Symbols;
import org.hexworks.zircon.api.graphics.TileGraphics;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.input.InputType;
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.internal.game.DefaultGameComponent;
import org.hexworks.zircon.internal.game.InMemoryGameArea;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("ALL")
public class GameAreaScrollingWithLayers {

    private static final List<InputType> EXIT_CONDITIONS = new ArrayList<>();
    private static final ColorTheme THEME = ColorThemes.amigaOs();
    private static final int TERMINAL_WIDTH = 60;
    private static final int TERMINAL_HEIGHT = 30;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static boolean headless = false;
    private static final BuiltInCP437TilesetResource TILESET = BuiltInCP437TilesetResource.ROGUE_YUN_16X16;

    static {
        EXIT_CONDITIONS.add(InputType.Escape);
        EXIT_CONDITIONS.add(InputType.EOF);
    }

    public static void main(String[] args) {

        Application app = SwingApplications.startApplication(AppConfigs.newConfig()
                .withDefaultTileset(TILESET)
                .withSize(SIZE)
                .withDebugMode(true)
                .enableBetaFeatures()
                .build());

        TileGrid tileGrid = app.getTileGrid();


        if (args.length > 0) {
            headless = true;
        }
        final Screen screen = Screens.createScreenFor(tileGrid);

        Panel actions = Components.panel()
                .withSize(screen.getSize().withWidth(20))
                .wrapWithBox(true)
                .withTitle("Actions")
                .withBoxType(BoxType.TOP_BOTTOM_DOUBLE)
                .build();
        Button wait = Components.button()
                .withText("Wait")
                .build();
        Button sleep = Components.button()
                .withText("Sleep")
                .withPosition(Positions.zero().withRelativeY(1))
                .build();
        actions.addComponent(wait);
        actions.addComponent(sleep);
        screen.addComponent(actions);


        final Panel gamePanel = Components.panel()
                .withSize(screen.getSize().withWidth(40))
                .withPosition(Positions.topRightOf(actions))
                .withTitle("Game area")
                .wrapWithBox(true)
                .withBoxType(BoxType.TOP_BOTTOM_DOUBLE)
                .build();

        final Size3D visibleGameAreaSize = Sizes.from2DTo3D(gamePanel.getSize()
                .minus(Sizes.create(2, 2)), 5);
        final Size virtualGameAreaSize = Sizes.create(Integer.MAX_VALUE, Integer.MAX_VALUE);


        final Map<Integer, List<TileGraphics>> levels = new HashMap<>();
        final int totalLevels = 10;
        for (int i = 0; i < totalLevels; i++) {
            levels.put(i, Collections.singletonList(DrawSurfaces.tileGraphicsBuilder()
                    .withSize(virtualGameAreaSize)
                    .build()));
        }

        final GameArea gameArea =
                new InMemoryGameArea<Tile>(
                        Blocks.newBuilder()
                                .withPosition(Positions.default3DPosition())
                                .withEmptyTile(Tiles.empty())
                                .addLayer(Tiles.empty())
                                .build(),
                        Sizes.from2DTo3D(virtualGameAreaSize, totalLevels),
                        1);

        ComponentBuilder builder = Components.gameComponent()
                .withGameArea(gameArea)
                .withTileset(BuiltInCP437TilesetResource.PHOEBUS_16X16);

        final DefaultGameComponent gameComponent = ((GameComponentBuilder<Tile>) Components.gameComponent()
                .withGameArea(gameArea)
                .withVisibleSize(visibleGameAreaSize)
                .withTileset(BuiltInCP437TilesetResource.PHOEBUS_16X16))
                .build();

        screen.addComponent(gamePanel);
        gamePanel.addComponent(gameComponent);

        enableMovement(screen, gameComponent);
        generatePyramid(3, Positions.create3DPosition(5, 5, 2), gameArea);
        generatePyramid(6, Positions.create3DPosition(15, 9, 5), gameArea);
        generatePyramid(5, Positions.create3DPosition(9, 21, 4), gameArea);

        screen.applyColorTheme(THEME);
        screen.display();
    }

    private static void generatePyramid(int height, Position3D startPos, GameArea gameArea) {
        double percent = 1.0 / (height + 1);
        Tile wall = Tiles.newBuilder()
                .withCharacter(Symbols.BLOCK_SOLID)
                .build();
        AtomicInteger currLevel = new AtomicInteger(startPos.getZ());
        for (int currSize = 0; currSize < height; currSize++) {
            final double currPercent = (currSize + 1) * percent;
            Position levelOffset = startPos.to2DPosition()
                    .withRelativeX(-currSize)
                    .withRelativeY(-currSize);
            Size levelSize = Sizes.create(1 + currSize * 2, 1 + currSize * 2);
            levelSize.fetchPositions().forEach(position -> {
                Position3D pos = Positions.from2DTo3D((position.plus(levelOffset)), currLevel.get());
                gameArea.setBlockAt(
                        pos,
                        Blocks.newBuilder()
                                .addLayer(wall
                                        .withBackgroundColor(wall.getBackgroundColor().darkenByPercent(currPercent))
                                        .withForegroundColor(wall.getForegroundColor().darkenByPercent(currPercent)))
                                .withPosition(pos)
                                .withEmptyTile(Tiles.empty())
                                .build());
            });
            currLevel.decrementAndGet();
        }
    }

    private static void enableMovement(final Screen screen, final DefaultGameComponent gameComponent) {
        final AtomicReference<Layer> coordinates = new AtomicReference<>(Layers.newBuilder()
                .withTileGraphics(CharacterTileStrings.newBuilder()
                        .withBackgroundColor(TileColors.transparent())
                        .withForegroundColor(TileColors.fromString("#aaaadd"))
                        .withText(String.format("Position: (x=%s, y=%s, z=%s)", 0, 0, 0))
                        .build()
                        .toTileGraphic(TILESET))
                .withOffset(Positions.create(21, 1))
                .build());
        screen.onInput((input) -> {
            if (EXIT_CONDITIONS.contains(input.inputType()) && !headless) {
                System.exit(0);
            } else {
                if (InputType.ArrowUp == input.inputType()) {
                    gameComponent.scrollOneBackward();
                }
                if (InputType.ArrowDown == input.inputType()) {
                    gameComponent.scrollOneForward();
                }
                if (InputType.ArrowLeft == input.inputType()) {
                    gameComponent.scrollOneLeft();
                }
                if (InputType.ArrowRight == input.inputType()) {
                    gameComponent.scrollOneRight();
                }
                if (InputType.PageUp == input.inputType()) {
                    gameComponent.scrollOneUp();
                }
                if (InputType.PageDown == input.inputType()) {
                    gameComponent.scrollOneDown();
                }
                screen.removeLayer(coordinates.get());
                Position3D visibleOffset = gameComponent.visibleOffset();
                coordinates.set(Layers.newBuilder()
                        .withTileGraphics(CharacterTileStrings.newBuilder()
                                .withBackgroundColor(TileColors.transparent())
                                .withForegroundColor(TileColors.fromString("#aaaadd"))
                                .withText(String.format("Position: (x=%s, y=%s, z=%s)",
                                        visibleOffset.getX(), visibleOffset.getY(), visibleOffset.getZ()))
                                .build()
                                .toTileGraphic(TILESET))
                        .withOffset(Positions.create(21, 1))
                        .build());
                screen.pushLayer(coordinates.get());
            }
        });
    }

}
