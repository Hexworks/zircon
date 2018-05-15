package org.codetome.zircon.examples.interactive;

import org.codetome.zircon.api.*;
import org.codetome.zircon.api.builder.*;
import org.codetome.zircon.api.color.ANSITextColor;
import org.codetome.zircon.api.color.TextColor;
import org.codetome.zircon.api.color.TextColorFactory;
import org.codetome.zircon.api.component.Button;
import org.codetome.zircon.api.component.Panel;
import org.codetome.zircon.api.component.builder.ButtonBuilder;
import org.codetome.zircon.api.component.builder.GameComponentBuilder;
import org.codetome.zircon.api.component.builder.PanelBuilder;
import org.codetome.zircon.api.game.GameArea;
import org.codetome.zircon.api.game.Position3D;
import org.codetome.zircon.api.game.Size3D;
import org.codetome.zircon.api.interop.Positions;
import org.codetome.zircon.api.interop.Sizes;
import org.codetome.zircon.api.interop.TextCharacters;
import org.codetome.zircon.api.interop.TextColors;
import org.codetome.zircon.examples.TerminalUtils;
import org.codetome.zircon.api.graphics.TextImage;
import org.codetome.zircon.api.input.InputType;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.resource.ColorThemeResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.api.terminal.Terminal;
import org.codetome.zircon.api.util.TextColorUtils;
import org.codetome.zircon.internal.component.impl.DefaultGameComponent;
import org.codetome.zircon.internal.game.InMemoryGameArea;
import org.codetome.zircon.internal.graphics.BoxType;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GameAreaScrollingWithLayers {

    private static final List<InputType> EXIT_CONDITIONS = new ArrayList<>();
    private static final int TERMINAL_WIDTH = 60;
    private static final int TERMINAL_HEIGHT = 30;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static boolean headless = false;

    static {
        EXIT_CONDITIONS.add(InputType.Escape);
        EXIT_CONDITIONS.add(InputType.EOF);
    }

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Terminal terminal = TerminalUtils.fetchTerminalBuilder(args)
                .font(CP437TilesetResource.ROGUE_YUN_16X16.toFont())
                .initialTerminalSize(SIZE)
                .build();
        if (args.length > 0) {
            headless = true;
        }
        final Screen screen = ScreenBuilder.createScreenFor(terminal);
        screen.setCursorVisibility(false); // we don't want the cursor right now

        Panel actions = PanelBuilder.newBuilder()
                .size(screen.getBoundableSize().withXLength(20))
                .wrapWithBox()
                .title("Actions")
                .boxType(BoxType.TOP_BOTTOM_DOUBLE)
                .build();
        Button wait = ButtonBuilder.newBuilder()
                .text("Wait")
                .build();
        Button sleep = ButtonBuilder.newBuilder()
                .text("Sleep")
                .position(Positions.DEFAULT_POSITION.withRelativeY(1))
                .build();
        actions.addComponent(wait);
        actions.addComponent(sleep);
        screen.addComponent(actions);


        final Panel gamePanel = PanelBuilder.newBuilder()
                .size(screen.getBoundableSize().withXLength(40))
                // TODO: FIX CAST
                .position(((JvmPosition) Positions.DEFAULT_POSITION).relativeToRightOf(actions))
                .title("Game area")
                .wrapWithBox()
                .boxType(BoxType.TOP_BOTTOM_DOUBLE)
                .build();

        final Size3D visibleGameAreaSize = Size3D.from2DSize(gamePanel.getBoundableSize()
                .minus(Sizes.create(2, 2)), 5);
        final Size virtualGameAreaSize = Sizes.create(Integer.MAX_VALUE, Integer.MAX_VALUE);


        final Map<Integer, List<TextImage>> levels = new HashMap<>();
        final int totalLevels = 10;
        for (int i = 0; i < totalLevels; i++) {
            levels.put(i, Collections.singletonList(TextImageBuilder.newBuilder()
                    .size(virtualGameAreaSize)
                    .build()));
        }

        final GameArea gameArea =
                new InMemoryGameArea(
                        Size3D.from2DSize(virtualGameAreaSize, totalLevels),
                        5,
                        TextCharacters.EMPTY);

        final DefaultGameComponent gameComponent = GameComponentBuilder.newBuilder()
                .gameArea(gameArea)
                .visibleSize(visibleGameAreaSize)
                .font(CP437TilesetResource.PHOEBUS_16X16.toFont())
                .build();

        screen.addComponent(gamePanel);
        gamePanel.addComponent(gameComponent);

        enableMovement(screen, gameComponent);
        generatePyramid(3, Position3D.of(5, 5, 2), gameArea);
        generatePyramid(6, Position3D.of(15, 9, 5), gameArea);
        generatePyramid(5, Position3D.of(9, 21, 4), gameArea);

        screen.applyColorTheme(ColorThemeResource.SOLARIZED_DARK_CYAN.getTheme());
        screen.display();
    }

    private static void generatePyramid(int height, Position3D startPos, GameArea gameArea) {
        double percent = 1.0 / (height + 1);
        TextCharacter wall = TextCharacters.newBuilder()
                .character(Symbols.BLOCK_SOLID)
                .build();
        AtomicInteger currLevel = new AtomicInteger(startPos.getZ());
        for (int currSize = 0; currSize < height; currSize++) {
            final double currPercent = (currSize + 1) * percent;
            Position levelOffset = startPos.to2DPosition()
                    .withRelativeX(-currSize)
                    .withRelativeY(-currSize);
            Size levelSize = Sizes.create(1 + currSize * 2, 1 + currSize * 2);
            levelSize.fetchPositions().forEach(position -> {
                gameArea.setBlockAt(
                        Position3D.from2DPosition((position.plus(levelOffset)), currLevel.get()),
                        Collections.singletonList(wall
                                .withBackgroundColor(TextColorUtils.darkenColorByPercent(wall.getBackgroundColor(), currPercent))
                                .withForegroundColor(TextColorUtils.darkenColorByPercent(wall.getForegroundColor(), currPercent))));
            });
            currLevel.decrementAndGet();
        }
    }

    private static void enableMovement(final Screen screen, final DefaultGameComponent gameComponent) {
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
                screen.drainLayers();
                Position3D visibleOffset = gameComponent.getVisibleOffset();
                screen.pushLayer(LayerBuilder.newBuilder()
                        .textImage(TextCharacterStringBuilder.newBuilder()
                                .backgroundColor(TextColors.TRANSPARENT)
                                .foregroundColor(TextColors.fromString("#aaaadd"))
                                .text(String.format("Position: (x=%s, y=%s, z=%s)", visibleOffset.getX(), visibleOffset.getY(), visibleOffset.getZ()))
                                .build()
                                .toTextImage())
                        .offset(Positions.create(21, 1))
                        .build());
                screen.refresh();
            }
        });
    }

}
