package org.hexworks.zircon.examples.game;

import kotlin.Unit;
import org.hexworks.cobalt.core.behavior.DisposedByHand;
import org.hexworks.cobalt.databinding.api.extension.Properties;
import org.hexworks.cobalt.databinding.api.property.Property;
import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.color.ColorInterpolator;
import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.data.*;
import org.hexworks.zircon.api.game.GameArea;
import org.hexworks.zircon.api.game.ProjectionMode;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.graphics.Symbols;
import org.hexworks.zircon.api.graphics.TileGraphics;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.api.uievent.KeyCode;
import org.hexworks.zircon.api.uievent.KeyboardEventType;
import org.hexworks.zircon.api.uievent.UIEventResponse;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.Functions.fromConsumer;

@SuppressWarnings("ALL")
public class GameAreaWithScrollingJava {

    private static final ColorTheme THEME = ColorThemes.stormyGreen();
    private static final TilesetResource TILESET = CP437TilesetResources.rexPaint20x20();
    private static final Dimension DIMENSIONS = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int GRID_WIDTH = DIMENSIONS.width / TILESET.getWidth() - 1;
    private static final int GRID_HEIGHT = DIMENSIONS.height / TILESET.getHeight() - 1;
    private static final Size GRID_SIZE = Size.create(GRID_WIDTH, GRID_HEIGHT);

    private static final int LEVEL_COUNT = 10;
    private static final ProjectionMode PROJECTION_MODE = ProjectionMode.TOP_DOWN_OBLIQUE_FRONT;
    private static final Tile FILLER = Tile.empty().withBackgroundColor(TileColor.fromString("#e7b751"));
    private static final TileColor PYRAMID_TOP_COLOR = TileColor.fromString("#ecc987");
    private static final TileColor PYRAMID_BOTTOM_COLOR = TileColor.fromString("#a36431");

    private static boolean HEADLESS = false;

    public static void main(String[] args) {

        if (args.length > 0) {
            HEADLESS = true;
        }

        final Screen screen = Screen.create(SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(TILESET)
                .withSize(GRID_SIZE)
                .withDebugMode(true)
                .enableBetaFeatures()
                .build()));

        final Property<ProjectionMode> projectionProperty = Properties.createPropertyFrom(PROJECTION_MODE, (value) -> true);

        Panel actions = Components.panel()
                .withSize(20, 5)
                .withPosition(1, 1)
                .withDecorations(box(BoxType.LEFT_RIGHT_DOUBLE, "Actions"))
                .build();
        Button quit = Components.button()
                .withText("Quit")
                .build();
        quit.onActivated(fromConsumer((event) -> {
            System.exit(0);
        }));

        VBox projections = Components.vbox()
                .withSize(20, 4)
                .withDecorations(box(BoxType.LEFT_RIGHT_DOUBLE, "Projection Mode"))
                .build();
        RadioButton topDown = Components.radioButton()
                .withText("Top Down")
                .withKey(ProjectionMode.TOP_DOWN.name())
                .build();
        RadioButton topDownOblique = Components.radioButton()
                .withText("Top Down Oblique")
                .withKey(ProjectionMode.TOP_DOWN_OBLIQUE_FRONT.name())
                .build();
        RadioButtonGroup projectionsGroup = Components.radioButtonGroup()
                .build();
        projectionsGroup.addComponent(topDown);
        projectionsGroup.addComponent(topDownOblique);
        actions.addComponents(quit);

        final Size3D visibleGameAreaSize = GRID_SIZE.minus(Size.create(2, 2)).to3DSize(5);
        final Size actualGameAreaSize = Size.create(Integer.MAX_VALUE, Integer.MAX_VALUE);

        final GameArea gameArea = GameComponents.newGameAreaBuilder()
                .withActualSize(actualGameAreaSize.to3DSize(LEVEL_COUNT))
                .withVisibleSize(visibleGameAreaSize)
                .build();
        screen.onShutdown(() -> {
            gameArea.dispose(DisposedByHand.INSTANCE);
            return Unit.INSTANCE;
        });

        final Panel gamePanel = Components.panel()
                .withSize(screen.getSize())
                .withComponentRenderer(GameComponents.newGameAreaComponentRenderer(
                        gameArea,
                        projectionProperty,
                        FILLER
                ))
                .withDecorations(box(BoxType.TOP_BOTTOM_DOUBLE, "Game Area with Scrolling"))
                .build();

        gamePanel.addComponent(actions);

        final Map<Integer, List<TileGraphics>> levels = new HashMap<>();
        for (int i = 0; i < LEVEL_COUNT; i++) {
            levels.put(i, Collections.singletonList(DrawSurfaces.tileGraphicsBuilder()
                    .withSize(actualGameAreaSize)
                    .build()));
        }

        screen.addComponent(gamePanel);

        enableMovement(screen, gameArea);
        generatePyramid(3, Position3D.create(25, 5, 2), gameArea);
        generatePyramid(6, Position3D.create(35, 9, 5), gameArea);
        generatePyramid(5, Position3D.create(30, 21, 4), gameArea);

        screen.setTheme(THEME);
        screen.display();
    }

    private static void generatePyramid(int height, Position3D startPos, GameArea gameArea) {
        double percent = 1.0 / (height + 1);
        Tile wall = Tile.newBuilder()
                .withCharacter(Symbols.BLOCK_SOLID)
                .build();
        AtomicInteger currLevel = new AtomicInteger(startPos.getZ());
        final ColorInterpolator interpolator = PYRAMID_TOP_COLOR.interpolateTo(PYRAMID_BOTTOM_COLOR);
        for (int currSize = 0; currSize < height; currSize++) {
            final double currPercent = (currSize + 1) * percent;
            Position levelOffset = startPos.to2DPosition()
                    .withRelativeX(-currSize)
                    .withRelativeY(-currSize);
            Size levelSize = Size.create(1 + currSize * 2, 1 + currSize * 2);
            levelSize.fetchPositions().forEach(position -> {
                Position3D pos = position.plus(levelOffset).to3DPosition(currLevel.get());
                Tile top = wall.withForegroundColor(interpolator.getColorAtRatio(currPercent));
                Tile front = top.withForegroundColor(top.getForegroundColor().darkenByPercent(.1));
                gameArea.setBlockAt(
                        pos,
                        Block.newBuilder()
                                .withTop(top)
                                .withFront(front)
                                .withEmptyTile(Tile.empty())
                                .build());
            });
            currLevel.decrementAndGet();
        }
    }

    private static void enableMovement(final Screen screen, final GameArea<Tile, Block<Tile>> gameArea) {
        screen.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED, (event, phase) -> {
            if (event.getCode().equals(KeyCode.ESCAPE) && !HEADLESS) {
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
            }
            return UIEventResponse.processed();
        });
    }

}
