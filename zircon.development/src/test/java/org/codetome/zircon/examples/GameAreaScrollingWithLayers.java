package org.codetome.zircon.examples;

import org.codetome.zircon.api.builder.grid.AppConfigBuilder;
import org.codetome.zircon.api.component.Button;
import org.codetome.zircon.api.component.Panel;
import org.codetome.zircon.api.data.Position;
import org.codetome.zircon.api.data.Position3D;
import org.codetome.zircon.api.data.Size;
import org.codetome.zircon.api.data.Size3D;
import org.codetome.zircon.api.data.Tile;
import org.codetome.zircon.api.game.GameArea;
import org.codetome.zircon.api.graphics.BoxType;
import org.codetome.zircon.api.graphics.Layer;
import org.codetome.zircon.api.graphics.Symbols;
import org.codetome.zircon.api.graphics.TileImage;
import org.codetome.zircon.api.grid.AppConfig;
import org.codetome.zircon.api.grid.CursorStyle;
import org.codetome.zircon.api.grid.TileGrid;
import org.codetome.zircon.api.input.InputType;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.resource.ColorThemeResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.gui.swing.internal.application.SwingApplication;
import org.codetome.zircon.internal.game.DefaultGameComponent;
import org.codetome.zircon.internal.game.InMemoryGameArea;
import org.codetome.zircon.jvm.api.interop.Components;
import org.codetome.zircon.jvm.api.interop.Layers;
import org.codetome.zircon.jvm.api.interop.Position3Ds;
import org.codetome.zircon.jvm.api.interop.Positions;
import org.codetome.zircon.jvm.api.interop.Screens;
import org.codetome.zircon.jvm.api.interop.Size3Ds;
import org.codetome.zircon.jvm.api.interop.Sizes;
import org.codetome.zircon.jvm.api.interop.TextCharacterStrings;
import org.codetome.zircon.jvm.api.interop.TextColors;
import org.codetome.zircon.jvm.api.interop.TextImages;
import org.codetome.zircon.jvm.api.interop.Tiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class GameAreaScrollingWithLayers {

    private static final List<InputType> EXIT_CONDITIONS = new ArrayList<>();
    private static final int TERMINAL_WIDTH = 60;
    private static final int TERMINAL_HEIGHT = 30;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static boolean headless = false;
    private static final CP437TilesetResource TILESET = CP437TilesetResource.ROGUE_YUN_16X16;

    static {
        EXIT_CONDITIONS.add(InputType.Escape);
        EXIT_CONDITIONS.add(InputType.EOF);
    }

    public static void main(String[] args) {

        AppConfig config = AppConfigBuilder.Companion.newBuilder()
                .cursorColor(TextColors.fromString("#ff8844"))
                .cursorStyle(CursorStyle.FIXED_BACKGROUND)
                .cursorBlinking(true)
                .defaultSize(SIZE)
                .defaultTileset(TILESET)
                .build();

        SwingApplication app = SwingApplication.Companion.create(config);

        app.start();

        TileGrid tileGrid = app.getTileGrid();


        if (args.length > 0) {
            headless = true;
        }
        final Screen screen = Screens.createScreenFor(tileGrid);
        screen.setCursorVisibility(false); // we don't want the cursor right now

        Panel actions = Components.newPanelBuilder()
                .size(screen.getBoundableSize().withXLength(20))
                .wrapWithBox()
                .title("Actions")
                .boxType(BoxType.TOP_BOTTOM_DOUBLE)
                .build();
        Button wait = Components.newButtonBuilder()
                .text("Wait")
                .build();
        Button sleep = Components.newButtonBuilder()
                .text("Sleep")
                .position(Positions.defaultPosition().withRelativeY(1))
                .build();
        actions.addComponent(wait);
        actions.addComponent(sleep);
        screen.addComponent(actions);


        final Panel gamePanel = Components.newPanelBuilder()
                .size(screen.getBoundableSize().withXLength(40))
                .position((Positions.defaultPosition()).relativeToRightOf(actions))
                .title("Game area")
                .wrapWithBox()
                .boxType(BoxType.TOP_BOTTOM_DOUBLE)
                .build();

        final Size3D visibleGameAreaSize = Size3Ds.from2DSize(gamePanel.getBoundableSize()
                .minus(Sizes.create(2, 2)), 5);
        final Size virtualGameAreaSize = Sizes.create(Integer.MAX_VALUE, Integer.MAX_VALUE);


        final Map<Integer, List<TileImage>> levels = new HashMap<>();
        final int totalLevels = 10;
        for (int i = 0; i < totalLevels; i++) {
            levels.put(i, Collections.singletonList(TextImages.newBuilder()
                    .size(virtualGameAreaSize)
                    .build()));
        }

        final GameArea gameArea =
                new InMemoryGameArea(
                        Size3Ds.from2DSize(virtualGameAreaSize, totalLevels),
                        5,
                        Tiles.empty());

        final DefaultGameComponent gameComponent = Components.newGameComponentBuilder()
                .gameArea(gameArea)
                .visibleSize(visibleGameAreaSize)
                .font(CP437TilesetResource.PHOEBUS_16X16)
                .build();

        screen.addComponent(gamePanel);
        gamePanel.addComponent(gameComponent);

        enableMovement(screen, gameComponent);
        generatePyramid(3, Position3Ds.create(5, 5, 2), gameArea);
        generatePyramid(6, Position3Ds.create(15, 9, 5), gameArea);
        generatePyramid(5, Position3Ds.create(9, 21, 4), gameArea);

        screen.applyColorTheme(ColorThemeResource.SOLARIZED_DARK_CYAN.getTheme());
        screen.display();
    }

    private static void generatePyramid(int height, Position3D startPos, GameArea gameArea) {
        double percent = 1.0 / (height + 1);
        Tile wall = Tiles.newBuilder()
                .character(Symbols.BLOCK_SOLID)
                .build();
        AtomicInteger currLevel = new AtomicInteger(startPos.getZ());
        for (int currSize = 0; currSize < height; currSize++) {
            final double currPercent = (currSize + 1) * percent;
            Position levelOffset = startPos.to2DPosition()
                    .withRelativeX(-currSize)
                    .withRelativeY(-currSize);
            Size levelSize = Sizes.create(1 + currSize * 2, 1 + currSize * 2);
            levelSize.fetchPositions().forEach(position -> gameArea.setBlockAt(
                    Position3Ds.from2DPosition((position.plus(levelOffset)), currLevel.get()),
                    Collections.singletonList(wall
                            .withBackgroundColor(wall.getBackgroundColor().darkenByPercent(currPercent))
                            .withForegroundColor(wall.getForegroundColor().darkenByPercent(currPercent)))));
            currLevel.decrementAndGet();
        }
    }

    private static void enableMovement(final Screen screen, final DefaultGameComponent gameComponent) {
        final AtomicReference<Layer> coordinates = new AtomicReference<>(Layers.newBuilder()
                .textImage(TextCharacterStrings.newBuilder()
                        .backgroundColor(TextColors.transparent())
                        .foregroundColor(TextColors.fromString("#aaaadd"))
                        .text(String.format("Position: (x=%s, y=%s, z=%s)", 0, 0, 0))
                        .build()
                        .toTextImage(TILESET))
                .offset(Positions.create(21, 1))
                .build());
        screen.onInput((input) -> {
            if (EXIT_CONDITIONS.contains(input.getInputType()) && !headless) {
                System.exit(0);
            } else {
                if (InputType.ArrowUp == input.getInputType()) {
                    gameComponent.scrollOneBackward();
                }
                if (InputType.ArrowDown == input.getInputType()) {
                    gameComponent.scrollOneForward();
                }
                if (InputType.ArrowLeft == input.getInputType()) {
                    gameComponent.scrollOneLeft();
                }
                if (InputType.ArrowRight == input.getInputType()) {
                    gameComponent.scrollOneRight();
                }
                if (InputType.PageUp == input.getInputType()) {
                    gameComponent.scrollOneUp();
                }
                if (InputType.PageDown == input.getInputType()) {
                    gameComponent.scrollOneDown();
                }
                screen.removeLayer(coordinates.get());
                Position3D visibleOffset = gameComponent.getVisibleOffset();
                coordinates.set(Layers.newBuilder()
                        .textImage(TextCharacterStrings.newBuilder()
                                .backgroundColor(TextColors.transparent())
                                .foregroundColor(TextColors.fromString("#aaaadd"))
                                .text(String.format("Position: (x=%s, y=%s, z=%s)",
                                        visibleOffset.getX(), visibleOffset.getY(), visibleOffset.getZ()))
                                .build()
                                .toTextImage(TILESET))
                        .offset(Positions.create(21, 1))
                        .build());
                screen.pushLayer(coordinates.get());
            }
        });
    }

}
