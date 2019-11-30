package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.builder.component.GameComponentBuilder;
import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.component.builder.ComponentBuilder;
import org.hexworks.zircon.api.data.*;
import org.hexworks.zircon.api.game.GameArea;
import org.hexworks.zircon.api.graphics.*;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.api.uievent.KeyCode;
import org.hexworks.zircon.api.uievent.KeyboardEventType;
import org.hexworks.zircon.api.uievent.UIEventResponse;
import org.hexworks.zircon.internal.game.impl.DefaultGameComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.hexworks.zircon.api.ComponentDecorations.box;

@SuppressWarnings("ALL")
public class GameAreaScrollingWithLayers {

    private static final ColorTheme THEME = ColorThemes.amigaOs();
    private static final int TERMINAL_WIDTH = 60;
    private static final int TERMINAL_HEIGHT = 30;
    private static final Size SIZE = Size.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static boolean headless = false;
    private static final TilesetResource TILESET = CP437TilesetResources.rogueYun16x16();

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(TILESET)
                .withSize(SIZE)
                .withDebugMode(true)
                .enableBetaFeatures()
                .build());


        if (args.length > 0) {
            headless = true;
        }
        final Screen screen = Screen.create(tileGrid);

        Panel actions = Components.panel()
                .withSize(screen.getSize().withWidth(20))
                .withDecorations(box(BoxType.TOP_BOTTOM_DOUBLE, "Actions"))
                .build();
        Button wait = Components.button()
                .withText("Wait")
                .build();
        Button sleep = Components.button()
                .withText("Sleep")
                .withPosition(Position.zero().withRelativeY(1))
                .build();
        actions.addComponent(wait);
        actions.addComponent(sleep);
        screen.addComponent(actions);


        final Panel gamePanel = Components.panel()
                .withSize(screen.getSize().withWidth(40))
                .withPosition(Position.topRightOf(actions))
                .withDecorations(box(BoxType.TOP_BOTTOM_DOUBLE, "Game area"))
                .build();

        final Size3D visibleGameAreaSize = gamePanel.getSize()
                .minus(Size.create(2, 2)).to3DSize(5);
        final Size actualGameAreaSize = Size.create(Integer.MAX_VALUE, Integer.MAX_VALUE);


        final Map<Integer, List<TileGraphics>> levels = new HashMap<>();
        final int totalLevels = 10;
        for (int i = 0; i < totalLevels; i++) {
            levels.put(i, Collections.singletonList(DrawSurfaces.tileGraphicsBuilder()
                    .withSize(actualGameAreaSize)
                    .build()));
        }

        final GameArea gameArea = GameComponents.newGameAreaBuilder()
                .withActualSize(actualGameAreaSize.to3DSize(totalLevels))
                .withVisibleSize(visibleGameAreaSize)
                .build();

        ComponentBuilder builder = Components.gameComponent()
                .withGameArea(gameArea)
                .withTileset(CP437TilesetResources.phoebus16x16());

        final DefaultGameComponent gameComponent = ((GameComponentBuilder<Tile, Block<Tile>>) Components.gameComponent()
                .withGameArea(gameArea)
                .withSize(visibleGameAreaSize.to2DSize())
                .withTileset(CP437TilesetResources.phoebus16x16()))
                .build();

        screen.addComponent(gamePanel);
        gamePanel.addComponent(gameComponent);

        enableMovement(screen, gameArea);
        generatePyramid(3, Position3D.create(5, 5, 2), gameArea);
        generatePyramid(6, Position3D.create(15, 9, 5), gameArea);
        generatePyramid(5, Position3D.create(9, 21, 4), gameArea);

        screen.setTheme(THEME);
        screen.display();
    }

    private static void generatePyramid(int height, Position3D startPos, GameArea gameArea) {
        double percent = 1.0 / (height + 1);
        Tile wall = Tile.newBuilder()
                .withCharacter(Symbols.BLOCK_SOLID)
                .build();
        AtomicInteger currLevel = new AtomicInteger(startPos.getZ());
        for (int currSize = 0; currSize < height; currSize++) {
            final double currPercent = (currSize + 1) * percent;
            Position levelOffset = startPos.to2DPosition()
                    .withRelativeX(-currSize)
                    .withRelativeY(-currSize);
            Size levelSize = Size.create(1 + currSize * 2, 1 + currSize * 2);
            levelSize.fetchPositions().forEach(position -> {
                Position3D pos = position.plus(levelOffset).to3DPosition(currLevel.get());
                gameArea.setBlockAt(
                        pos,
                        Block.newBuilder()
                                .withContent(wall
                                        .withBackgroundColor(wall.getBackgroundColor().darkenByPercent(currPercent))
                                        .withForegroundColor(wall.getForegroundColor().darkenByPercent(currPercent)))
                                .withEmptyTile(Tile.empty())
                                .build());
            });
            currLevel.decrementAndGet();
        }
    }

    private static void enableMovement(final Screen screen, final GameArea<Tile, Block<Tile>> gameArea) {
        final AtomicReference<Layer> coordinates = new AtomicReference<>();
        coordinates.set(createCoordinates(Position3D.create(0, 0, 0)));
        screen.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED, (event, phase) -> {
            if (event.getCode().equals(KeyCode.ESCAPE) && !headless) {
                System.exit(0);
            } else {
                if (event.getCode().equals(KeyCode.UP)) {
                    gameArea.scrollOneBackward();
                }
                if (event.getCode().equals(KeyCode.DOWN)) {
                    gameArea.scrollOneForward();
                }
                if (event.getCode().equals(KeyCode.LEFT)) {
                    gameArea.scrollOneLeft();
                }
                if (event.getCode().equals(KeyCode.RIGHT)) {
                    gameArea.scrollOneRight();
                }
                if (event.getCode().equals(KeyCode.KEY_U)) {
                    gameArea.scrollOneUp();
                }
                if (event.getCode().equals(KeyCode.KEY_D)) {
                    gameArea.scrollOneDown();
                }
                screen.removeLayer(coordinates.get());
                coordinates.set(createCoordinates(gameArea.getVisibleOffset()));
                screen.addLayer(coordinates.get());
            }
            return UIEventResponse.processed();
        });
    }

    @NotNull
    private static Layer createCoordinates(Position3D pos) {
        String text = String.format("Position: (x=%s, y=%s, z=%s)", pos.getX(), pos.getY(), pos.getZ());
        TileComposite tc = CharacterTileStrings.newBuilder()
                .withBackgroundColor(TileColor.transparent())
                .withForegroundColor(TileColor.fromString("#aaaadd"))
                .withText(text)
                .withSize(Size.create(text.length(), 1))
                .build();
        Layer layer = Layer.newBuilder()
                .withOffset(Position.create(21, 1))
                .build();
        layer.draw(tc, Position.zero(), Size.create(text.length(), 1));
        return layer;
    }
}
