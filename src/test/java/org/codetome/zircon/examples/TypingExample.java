package org.codetome.zircon.examples;

import org.codetome.zircon.TerminalPosition;
import org.codetome.zircon.TextCharacter;
import org.codetome.zircon.builder.DeviceConfigurationBuilder;
import org.codetome.zircon.builder.FontRendererBuilder;
import org.codetome.zircon.font.FontRenderer;
import org.codetome.zircon.input.Input;
import org.codetome.zircon.input.InputType;
import org.codetome.zircon.input.KeyStroke;
import org.codetome.zircon.screen.Screen;
import org.codetome.zircon.terminal.DefaultTerminalBuilder;
import org.codetome.zircon.terminal.Terminal;
import org.codetome.zircon.terminal.TerminalSize;
import org.codetome.zircon.terminal.config.DeviceConfiguration;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.codetome.zircon.ANSITextColor.BLACK;
import static org.codetome.zircon.ANSITextColor.RED;
import static org.codetome.zircon.input.InputType.Enter;
import static org.codetome.zircon.tileset.DFTilesetResource.WANDERLUST_16X16;

public class TypingExample {

    private static final int TERMINAL_WIDTH = 30;
    private static final int TERMINAL_HEIGHT = 30;

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
        final DefaultTerminalBuilder factory = new DefaultTerminalBuilder();
        factory.initialTerminalSize(new TerminalSize(TERMINAL_WIDTH, TERMINAL_HEIGHT));
        final FontRenderer<Graphics> fontConfig = FontRendererBuilder.newBuilder()
                .useSwing()
                .useDFTileset(WANDERLUST_16X16)
                .build();
        final DeviceConfiguration deviceConfig = DeviceConfigurationBuilder.newBuilder()
                .cursorBlinking(true).build();

        factory.fontRenderer(fontConfig);
        factory.deviceConfiguration(deviceConfig);
        final Terminal terminal = factory.buildTerminal();
        final Screen screen = factory.createScreenFor(terminal);

        startTypingSupportForScreen(screen);
//        startTypingSupportForTerminal(terminal);
    }

    private static void startTypingSupportForScreen(Screen screen) {
        while (true) {
            final Optional<Input> opKey = screen.pollInput();
            if (opKey.isPresent()) {
                final Input key = opKey.get();
                final TerminalPosition pos = screen.getCursorPosition();
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
            }
        }
    }

    private static void startTypingSupportForTerminal(Terminal terminal) {
        while (true) {
            final Optional<Input> opKey = terminal.pollInput();
            if (opKey.isPresent()) {
                final Input key = opKey.get();
                final TerminalPosition pos = terminal.getCursorPosition();
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
