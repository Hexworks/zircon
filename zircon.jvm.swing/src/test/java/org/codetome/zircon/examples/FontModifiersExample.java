package org.codetome.zircon.examples;

import org.codetome.zircon.TerminalUtils;
import org.codetome.zircon.api.data.Size;
import org.codetome.zircon.api.interop.Modifiers;
import org.codetome.zircon.api.interop.Sizes;
import org.codetome.zircon.api.modifier.RayShade;
import org.codetome.zircon.api.terminal.Terminal;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.codetome.zircon.api.color.ANSITextColor.*;
import static org.codetome.zircon.api.resource.CP437TilesetResource.WANDERLUST_16X16;

public class FontModifiersExample {

    private static final int TERMINAL_WIDTH = 16;
    private static final int TERMINAL_HEIGHT = 3;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Terminal terminal = TerminalUtils.fetchTerminalBuilder(args)
                .font(WANDERLUST_16X16.toFont())
                .initialTerminalSize(SIZE)
                .build();
        terminal.setCursorVisibility(false); // we don't want the cursor right now

        terminal.enableModifiers(Modifiers.verticalFlip());
        terminal.setBackgroundColor(BLUE);
        terminal.setForegroundColor(YELLOW);
        terminal.putCharacter('A');

        putEmptySpace(terminal);

        terminal.enableModifiers(Modifiers.crossedOut());
        terminal.setBackgroundColor(RED);
        terminal.setForegroundColor(GREEN);
        terminal.putCharacter('B');

        putEmptySpace(terminal);

        terminal.enableModifiers(Modifiers.blink());
        terminal.setBackgroundColor(RED);
        terminal.setForegroundColor(WHITE);
        terminal.putCharacter('C');

        putEmptySpace(terminal);

        terminal.enableModifiers(Modifiers.underline());
        terminal.setBackgroundColor(BLUE);
        terminal.setForegroundColor(CYAN);
        terminal.putCharacter('D');

        putEmptySpace(terminal);

        terminal.enableModifiers(Modifiers.horizontalFlip());
        terminal.setBackgroundColor(BLACK);
        terminal.setForegroundColor(YELLOW);
        terminal.putCharacter('E');

        putEmptySpace(terminal);

        terminal.enableModifiers(Modifiers.blink());
        terminal.setBackgroundColor(CYAN);
        terminal.setForegroundColor(YELLOW);
        terminal.putCharacter('F');

        putEmptySpace(terminal);

        terminal.enableModifiers(Stream.of(Modifiers.horizontalFlip(), Modifiers.verticalFlip(), Modifiers.blink()).collect(Collectors.toSet()));
        terminal.setBackgroundColor(BLUE);
        terminal.setForegroundColor(WHITE);
        terminal.putCharacter('G');

        putEmptySpace(terminal);

        terminal.enableModifiers(Modifiers.border());
        terminal.setBackgroundColor(WHITE);
        terminal.setForegroundColor(BLUE);
        terminal.putCharacter('H');

        putEmptySpace(terminal);

        terminal.enableModifiers(new RayShade());
        terminal.setBackgroundColor(WHITE);
        terminal.setForegroundColor(BLUE);
        terminal.putCharacter('I');

        putEmptySpace(terminal);

        terminal.enableModifiers(Modifiers.glow());
        terminal.setBackgroundColor(WHITE);
        terminal.setForegroundColor(BLUE);
        terminal.putCharacter('J');

        terminal.flush();
    }

    private static void putEmptySpace(Terminal terminal) {
        terminal.resetColorsAndModifiers();
        terminal.setForegroundColor(BLACK);
        terminal.putCharacter(' ');
    }

}
