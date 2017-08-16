package org.codetome.zircon.examples.interactive;

import org.codetome.zircon.Position;
import org.codetome.zircon.TextCharacter;
import org.codetome.zircon.api.DeviceConfigurationBuilder;
import org.codetome.zircon.api.PhysicalFontResource;
import org.codetome.zircon.api.TerminalBuilder;
import org.codetome.zircon.input.Input;
import org.codetome.zircon.input.InputType;
import org.codetome.zircon.input.KeyStroke;
import org.codetome.zircon.screen.Screen;
import org.codetome.zircon.terminal.Terminal;
import org.codetome.zircon.terminal.virtual.VirtualTerminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.codetome.zircon.color.ANSITextColor.BLACK;
import static org.codetome.zircon.color.ANSITextColor.RED;
import static org.codetome.zircon.input.InputType.Enter;

public class TypingExample {

    private static final int TERMINAL_WIDTH = 80;

    private static final List<InputType> EXIT_CONDITIONS = new ArrayList<>();
    private static final TextCharacter TEXT_CHAR_TEMPLATE = TextCharacter.builder()
            .foregroundColor(RED)
            .backgroundColor(BLACK)
            .build();

    static {
        EXIT_CONDITIONS.add(InputType.Escape);
        EXIT_CONDITIONS.add(InputType.EOF);
    }

    public static void main(String[] args) {
        TerminalBuilder builder = TerminalBuilder.newBuilder()
                .deviceConfiguration(DeviceConfigurationBuilder.newBuilder()
                        .cursorBlinking(true)
                        .build());
        final VirtualTerminal terminal = builder.buildTerminal();
        final Screen screen = builder.createScreenFor(terminal);

//        startTypingSupportForScreen(screen);
        startTypingSupportForTerminal(terminal);
    }

    private static void startTypingSupportForScreen(Screen screen) {
        while (true) {
            final Optional<Input> opKey = screen.pollInput();
            if (opKey.isPresent()) {
                final Input key = opKey.get();
                final Position pos = screen.getCursorPosition();
                if (EXIT_CONDITIONS.contains(key.getInputType())) {
                    System.exit(0);
                } else if (key.inputTypeIs(Enter)) {
                    screen.setCursorPosition(pos.withRelativeRow(1).withColumn(0));
                    screen.refresh();
                } else {
                    if (key.isKeyStroke()) {
                        final KeyStroke ks = key.asKeyStroke();
                        screen.setCharacter(pos, TEXT_CHAR_TEMPLATE.withCharacter(ks.getCharacter()));
                        if (pos.getColumn() == TERMINAL_WIDTH - 1) {
                            screen.setCursorPosition(pos.withRelativeRow(1).withColumn(0));
                        } else {
                            screen.setCursorPosition(pos.withRelativeColumn(1));
                        }
                        screen.refresh();
                    }
                }
                screen.setCursorPosition(pos.withRelativeColumn(1));
            }
        }
    }

    private static void startTypingSupportForTerminal(Terminal terminal) {
        while (true) {
            final Optional<Input> opKey = terminal.pollInput();
            if (opKey.isPresent()) {
                final Input key = opKey.get();
                final Position pos = terminal.getCursorPosition();
                if (EXIT_CONDITIONS.contains(key.getInputType())) {
                    System.exit(0);
                } else if (key.inputTypeIs(Enter)) {
                    terminal.setCursorPosition(pos.withRelativeRow(1).withColumn(0));
                    terminal.flush();
                } else {
                    if (key.isKeyStroke()) {
                        final KeyStroke ks = key.asKeyStroke();
                        terminal.setBackgroundColor(BLACK);
                        terminal.setForegroundColor(RED);
                        terminal.putCharacter(ks.getCharacter());
                        terminal.resetColorsAndModifiers();
                        terminal.flush();
                    }
                }
            }
        }
    }
}
