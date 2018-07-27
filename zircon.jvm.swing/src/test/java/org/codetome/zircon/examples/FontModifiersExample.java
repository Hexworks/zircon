package org.codetome.zircon.examples;

import org.codetome.zircon.TerminalUtils;
import org.codetome.zircon.api.data.Size;
import org.codetome.zircon.api.interop.Modifiers;
import org.codetome.zircon.api.interop.Sizes;
import org.codetome.zircon.api.modifier.RayShade;
import org.codetome.zircon.api.grid.TileGrid;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.codetome.zircon.api.color.ANSITextColor.*;
import static org.codetome.zircon.api.resource.CP437TilesetResource.WANDERLUST_16X16;

public class FontModifiersExample {

    private static final int TERMINAL_WIDTH = 16;
    private static final int TERMINAL_HEIGHT = 3;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);

    public static void main(String[] args) {
        // for this example we only need a default grid (no extra config)
        final TileGrid tileGrid = TerminalUtils.fetchTerminalBuilder(args)
                .font(WANDERLUST_16X16.toFont())
                .initialTerminalSize(SIZE)
                .build();
        tileGrid.setCursorVisibility(false); // we don't want the cursor right now

        tileGrid.enableModifiers(Modifiers.verticalFlip());
        tileGrid.setBackgroundColor(BLUE);
        tileGrid.setForegroundColor(YELLOW);
        tileGrid.putCharacter('A');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(Modifiers.crossedOut());
        tileGrid.setBackgroundColor(RED);
        tileGrid.setForegroundColor(GREEN);
        tileGrid.putCharacter('B');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(Modifiers.blink());
        tileGrid.setBackgroundColor(RED);
        tileGrid.setForegroundColor(WHITE);
        tileGrid.putCharacter('C');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(Modifiers.underline());
        tileGrid.setBackgroundColor(BLUE);
        tileGrid.setForegroundColor(CYAN);
        tileGrid.putCharacter('D');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(Modifiers.horizontalFlip());
        tileGrid.setBackgroundColor(BLACK);
        tileGrid.setForegroundColor(YELLOW);
        tileGrid.putCharacter('E');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(Modifiers.blink());
        tileGrid.setBackgroundColor(CYAN);
        tileGrid.setForegroundColor(YELLOW);
        tileGrid.putCharacter('F');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(Stream.of(Modifiers.horizontalFlip(), Modifiers.verticalFlip(), Modifiers.blink()).collect(Collectors.toSet()));
        tileGrid.setBackgroundColor(BLUE);
        tileGrid.setForegroundColor(WHITE);
        tileGrid.putCharacter('G');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(Modifiers.border());
        tileGrid.setBackgroundColor(WHITE);
        tileGrid.setForegroundColor(BLUE);
        tileGrid.putCharacter('H');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(new RayShade());
        tileGrid.setBackgroundColor(WHITE);
        tileGrid.setForegroundColor(BLUE);
        tileGrid.putCharacter('I');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(Modifiers.glow());
        tileGrid.setBackgroundColor(WHITE);
        tileGrid.setForegroundColor(BLUE);
        tileGrid.putCharacter('J');

        tileGrid.flush();
    }

    private static void putEmptySpace(TileGrid tileGrid) {
        tileGrid.resetColorsAndModifiers();
        tileGrid.setForegroundColor(BLACK);
        tileGrid.putCharacter(' ');
    }

}
