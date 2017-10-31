package org.codetome.zircon.examples.interactive;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.beta.component.GameComponent;
import org.codetome.zircon.api.beta.component.TextImageGameArea;
import org.codetome.zircon.api.builder.*;
import org.codetome.zircon.api.color.ANSITextColor;
import org.codetome.zircon.api.color.TextColorFactory;
import org.codetome.zircon.api.component.Button;
import org.codetome.zircon.api.component.Label;
import org.codetome.zircon.api.component.Panel;
import org.codetome.zircon.api.component.builder.ButtonBuilder;
import org.codetome.zircon.api.component.builder.PanelBuilder;
import org.codetome.zircon.api.font.Font;
import org.codetome.zircon.api.graphics.Layer;
import org.codetome.zircon.api.graphics.TextImage;
import org.codetome.zircon.api.input.InputType;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.resource.ColorThemeResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.api.shape.FilledRectangleFactory;
import org.codetome.zircon.api.terminal.Terminal;
import org.codetome.zircon.internal.graphics.BoxType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HideNSeek {

    private static final List<InputType> EXIT_CONDITIONS = new ArrayList<>();
    private static final int TERMINAL_WIDTH = 60;
    private static final int TERMINAL_HEIGHT = 30;
    private static final Size SIZE = Size.of(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final Font FONT = CP437TilesetResource.ROGUE_YUN_16X16.toFont();
    private static boolean headless = false;

    static {
        EXIT_CONDITIONS.add(InputType.Escape);
        EXIT_CONDITIONS.add(InputType.EOF);
    }

    @Test
    public void checkSetup() {
        main(new String[]{"test"});
    }

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Terminal terminal = TerminalBuilder.newBuilder()
                .font(FONT)
                .initialTerminalSize(SIZE)
                .buildTerminal(args.length > 0);
        if (args.length > 0) {
            headless = true;
        }
        final Screen screen = TerminalBuilder.createScreenFor(terminal);
        Size size = screen.getBoundableSize();
        screen.setCursorVisibility(false); // we don't want the cursor right now

        Panel actions = PanelBuilder.newBuilder()
                .size(screen.getBoundableSize().withColumns(20))
                .wrapInBox()
                .title("Actions")
                .boxType(BoxType.TOP_BOTTOM_DOUBLE)
                .build();
        Button wait = ButtonBuilder.newBuilder()
                .text("Wait")
                .build();
        Button sleep = ButtonBuilder.newBuilder()
                .text("Sleep")
                .position(Position.DEFAULT_POSITION.withRelativeRow(1))
                .build();
        actions.addComponent(wait);
        actions.addComponent(sleep);
        screen.addComponent(actions);


        final Panel gamePanel = PanelBuilder.newBuilder()
                .size(screen.getBoundableSize().withColumns(40))
                .position(Position.DEFAULT_POSITION.relativeToRightOf(actions))
                .title("Game area")
                .wrapInBox()
                .boxType(BoxType.TOP_BOTTOM_DOUBLE)
                .build();

        final Size visibleGameAreaSize = gamePanel.getBoundableSize().minus(Size.of(2, 2));

        final TextImage gameField = TextImageBuilder.newBuilder()
                .size(visibleGameAreaSize)
                .filler(TextCharacterBuilder.newBuilder()
                        .backgroundColor(ANSITextColor.BLACK)
                        .build())
                .build();

        final GameComponent gameComponent = new GameComponent(
                new TextImageGameArea(gameField),
                gameField.getBoundableSize(),
                CP437TilesetResource.PHOEBUS_16X16.toFont(),
                Position.DEFAULT_POSITION,
                ComponentStylesBuilder.DEFAULT);
        screen.addComponent(gamePanel);
        gamePanel.addComponent(gameComponent);

        final Layer player = new LayerBuilder()
                .filler(TextCharacterBuilder.newBuilder()
                        .character('@')
                        .backgroundColor(TextColorFactory.fromRGB(0, 0, 0, 0))
                        .foregroundColor(ANSITextColor.WHITE)
                        .build())
                .offset(Position.of(visibleGameAreaSize.getColumns() / 2, visibleGameAreaSize.getRows() / 2))
                .size(Size.ONE)
                .build();

        gameComponent.pushLayer(player);

        enableMovement(screen, player);
        screen.applyColorTheme(ColorThemeResource.SOLARIZED_DARK_CYAN.getTheme());
        screen.display();
    }

    private static void enableMovement(final Screen screen, final Layer player) {
        screen.onInput((input) -> {
            if (EXIT_CONDITIONS.contains(input.getInputType()) && !headless) {
                System.exit(0);
            } else {
                Position newPos = player.getPosition();
                if (InputType.ArrowUp == input.getInputType()) {
                    if (player.getPosition().getRow() > 0) {
                        newPos = player.getPosition().withRelativeRow(-1);
                    }
                }
                if (InputType.ArrowDown == input.getInputType()) {
                    newPos = player.getPosition().withRelativeRow(1);
                }
                if (InputType.ArrowLeft == input.getInputType()) {
                    if (player.getPosition().getColumn() > 0) {
                        newPos = player.getPosition().withRelativeColumn(-1);
                    }
                }
                if (InputType.ArrowRight == input.getInputType()) {
                    newPos = player.getPosition().withRelativeColumn(1);
                }
                if (screen.containsPosition(newPos)) {
                    player.moveTo(newPos);
                }
                screen.refresh();
            }
        });
    }
}
