package org.codetome.zircon.examples.interactive;

import org.codetome.zircon.*;
import org.codetome.zircon.builder.TerminalBuilder;
import org.codetome.zircon.builder.TextCharacterBuilder;
import org.codetome.zircon.builder.TextColorFactory;
import org.codetome.zircon.graphics.TextGraphics;
import org.codetome.zircon.graphics.layer.DefaultLayer;
import org.codetome.zircon.graphics.layer.Layer;
import org.codetome.zircon.input.Input;
import org.codetome.zircon.input.InputType;
import org.codetome.zircon.screen.Screen;
import org.codetome.zircon.terminal.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HideNSeek {

    private static final List<InputType> EXIT_CONDITIONS = new ArrayList<>();

    static {
        EXIT_CONDITIONS.add(InputType.Escape);
        EXIT_CONDITIONS.add(InputType.EOF);
    }

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Screen screen = TerminalBuilder.newBuilder()
                .initialTerminalSize(new Size(80, 40))
//                .fontRenderer(FontRendererBuilder.newBuilder()
//                        .useSwing()
//                        .useDFTileset(DFTilesetResource.TAFFER_20X20)
//                        .build())
                .buildScreen();
        Size size = screen.getBoundableSize();
        screen.setCursorVisible(false); // we don't want the cursor right now
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColorFactory.fromString("#665233"));
        graphics.fill(' ');

        final Layer player = new DefaultLayer(Size.ONE,
                TextCharacterBuilder.newBuilder()
                        .character('@')
                        .backgroundColor(TextColorFactory.fromRGB(0, 0, 0, 0))
                        .foregroundColor(ANSITextColor.WHITE)
                        .build(),
                new Position(size.getColumns() / 2, size.getRows() / 2));

        screen.addOverlay(player);
        screen.display();
        drawBuilding(screen, new Position(5, 10));
        enableMovement(screen, player);
    }

    private static void drawBuilding(Screen screen, Position position) {
        Layer building = new DefaultLayer(new Size(4, 4), TextCharacter.DEFAULT_CHARACTER, position);
        TextColor windowColor = TextColorFactory.fromString("#808080");
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 5; x++) {
                building.setCharacterAt(new Position(x, y).plus(position), TextCharacterBuilder.newBuilder()
                        .backgroundColor(windowColor)
                        .character(' ')
                        .build());
            }
        }

        drawCharAt(building, new Position(0, 2).plus(position), Symbols.SINGLE_LINE_TOP_LEFT_CORNER);
        drawCharAt(building, new Position(1, 2).plus(position), Symbols.SINGLE_LINE_T_DOWN);
        drawCharAt(building, new Position(2, 2).plus(position), Symbols.SINGLE_LINE_T_DOWN);
        drawCharAt(building, new Position(3, 2).plus(position), Symbols.SINGLE_LINE_TOP_RIGHT_CORNER);
        drawCharAt(building, new Position(0, 3).plus(position), Symbols.SINGLE_LINE_BOTTOM_LEFT_CORNER);
        drawCharAt(building, new Position(1, 3).plus(position), Symbols.SINGLE_LINE_T_UP);
        drawCharAt(building, new Position(2, 3).plus(position), Symbols.SINGLE_LINE_T_UP);
        drawCharAt(building, new Position(3, 3).plus(position), Symbols.SINGLE_LINE_BOTTOM_RIGHT_CORNER);
        screen.display();
        screen.addOverlay(building);
    }

    private static void drawCharAt(Layer building, Position position, char c) {
        TextColor wallColor = TextColorFactory.fromString("#333333");
        TextColor windowColor = TextColorFactory.fromString("#808080");
        building.setCharacterAt(position, TextCharacterBuilder.newBuilder()
                .backgroundColor(wallColor)
                .foregroundColor(windowColor)
                .character(c)
                .build());
    }

    private static void enableMovement(Screen screen, Layer player) {
        while (true) {
            final Optional<Input> opKey = screen.pollInput();
            if (opKey.isPresent()) {
                final Input key = opKey.get();
                if (EXIT_CONDITIONS.contains(key.getInputType())) {
                    System.exit(0);
                } else {
                    if (InputType.ArrowUp == key.getInputType()) {
                        player.setOffset(player.getOffset().withRelativeRow(-1));
                    }
                    if (InputType.ArrowDown == key.getInputType()) {
                        player.setOffset(player.getOffset().withRelativeRow(1));
                    }
                    if (InputType.ArrowLeft == key.getInputType()) {
                        player.setOffset(player.getOffset().withRelativeColumn(-1));
                    }
                    if (InputType.ArrowRight == key.getInputType()) {
                        player.setOffset(player.getOffset().withRelativeColumn(1));
                    }
                    screen.display();
                }
            }
        }
    }
}
