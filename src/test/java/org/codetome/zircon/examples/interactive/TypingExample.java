package org.codetome.zircon.examples.interactive;

import org.codetome.zircon.Position;
import org.codetome.zircon.Size;
import org.codetome.zircon.TextCharacter;
import org.codetome.zircon.api.DeviceConfigurationBuilder;
import org.codetome.zircon.api.TerminalBuilder;
import org.codetome.zircon.api.TextColorFactory;
import org.codetome.zircon.input.InputType;
import org.codetome.zircon.input.KeyStroke;
import org.codetome.zircon.screen.Screen;
import org.codetome.zircon.terminal.Terminal;

import java.util.ArrayList;
import java.util.List;

import static org.codetome.zircon.color.impl.ANSITextColor.BLACK;
import static org.codetome.zircon.color.impl.ANSITextColor.RED;
import static org.codetome.zircon.input.InputType.Enter;

public class TypingExample {

    private static final int TERMINAL_WIDTH = 40;

    private static final List<InputType> EXIT_CONDITIONS = new ArrayList<>();
    private static final TextCharacter TEXT_CHAR_TEMPLATE = TextCharacter.builder()
            .foregroundColor(TextColorFactory.fromString("#F7923A"))
            .backgroundColor(BLACK)
            .build();

    static {
        EXIT_CONDITIONS.add(InputType.Escape);
        EXIT_CONDITIONS.add(InputType.EOF);
    }

    public static void main(String[] args) {
        TerminalBuilder builder = TerminalBuilder.newBuilder()
                .initialTerminalSize(Size.of(TERMINAL_WIDTH, 10))
                .deviceConfiguration(DeviceConfigurationBuilder.newBuilder()
                        .cursorBlinking(true)
                        .build());
        final Terminal terminal = builder.buildTerminal();
        final Screen screen = builder.createScreenFor(terminal);

//        startTypingSupportForScreen(screen);
        startTypingSupportForTerminal(terminal);
    }

    private static void startTypingSupportForScreen(Screen screen) {
        screen.addInputListener((input) -> {
            final Position pos = screen.getCursorPosition();
            if (EXIT_CONDITIONS.contains(input.getInputType())) {
                System.exit(0);
            } else if (input.inputTypeIs(Enter)) {
                screen.putCursorAt(pos.withRelativeRow(1).withColumn(0));
                screen.refresh();
            } else {
                if (input.isKeyStroke()) {
                    final KeyStroke ks = input.asKeyStroke();
                    screen.setCharacterAt(pos, TEXT_CHAR_TEMPLATE.withCharacter(ks.getCharacter()));
                    if (pos.getColumn() == TERMINAL_WIDTH) {
                        screen.putCursorAt(pos.withRelativeRow(1).withColumn(0));
                    } else {
                        screen.putCursorAt(pos.withRelativeColumn(1));
                    }
                    screen.refresh();
                }
            }
        });
    }

    private static void startTypingSupportForTerminal(Terminal terminal) {
        terminal.addInputListener((input) -> {
            final Position pos = terminal.getCursorPosition();
            if (EXIT_CONDITIONS.contains(input.getInputType())) {
                System.exit(0);
            } else if (input.inputTypeIs(Enter)) {
                terminal.putCursorAt(pos.withRelativeRow(1).withColumn(0));
                terminal.flush();
            } else {
                if (input.isKeyStroke()) {
                    final KeyStroke ks = input.asKeyStroke();
                    terminal.setBackgroundColor(BLACK);
                    terminal.setForegroundColor(RED);
                    terminal.putCharacter(ks.getCharacter());
                    terminal.resetColorsAndModifiers();
                    terminal.flush();
                }
            }
        });
    }
}
