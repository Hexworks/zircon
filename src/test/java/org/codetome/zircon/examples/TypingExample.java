package org.codetome.zircon.examples;

import org.codetome.zircon.TerminalPosition;
import org.codetome.zircon.TextCharacter;
import org.codetome.zircon.TextColor;
import org.codetome.zircon.font.MonospaceFontRenderer;
import org.codetome.zircon.input.Input;
import org.codetome.zircon.input.InputType;
import org.codetome.zircon.input.KeyStroke;
import org.codetome.zircon.screen.Screen;
import org.codetome.zircon.terminal.DefaultTerminalFactory;
import org.codetome.zircon.terminal.Terminal;
import org.codetome.zircon.terminal.TerminalSize;
import org.codetome.zircon.terminal.config.DeviceConfiguration;
import org.codetome.zircon.terminal.config.FontConfiguration;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.codetome.zircon.TextColor.ANSI.*;
import static org.codetome.zircon.tileset.DFTilesetResource.*;

public class TypingExample {

    private static final int TERMINAL_WIDTH = 30;
    private static final int TERMINAL_HEIGHT = 30;

    private static final List<InputType> EXIT_CONDITIONS = new ArrayList<>();

    static {
        EXIT_CONDITIONS.add(InputType.Escape);
        EXIT_CONDITIONS.add(InputType.EOF);
    }

    public static void main(String[] args) {
        final DefaultTerminalFactory factory = new DefaultTerminalFactory();
        factory.setInitialTerminalSize(new TerminalSize(TERMINAL_WIDTH, TERMINAL_HEIGHT));
        final MonospaceFontRenderer<Graphics> fontConfig =
                FontConfiguration.createSwingFontRendererForTileset(WANDERLUST_16X16);
        final DeviceConfiguration deviceConfig = DeviceConfiguration.getDefault();

        deviceConfig.setCursorBlinking(true);

        factory.setFontRenderer(fontConfig);
        factory.setTerminalDeviceConfiguration(deviceConfig);
        final Terminal terminal = factory.createTerminal();
        final Screen screen = factory.createScreenFor(terminal);

//        startTypingSupportForScreen(screen);
        startTypingSupportForTerminal(terminal);
    }

    private static void startTypingSupportForScreen(Screen screen) {
        while (true) {
            final Optional<Input> opKey = screen.pollInput();
            if (opKey.isPresent()) {
                final Input key = opKey.get();
                final TerminalPosition pos = screen.getCursorPosition();
                if (EXIT_CONDITIONS.contains(key.getInputType())) {
                    System.exit(0);
                } else if (key.getInputType().equals(InputType.Enter)) {
                    screen.setCursorPosition(pos.withRelativeRow(1).withColumn(0));
                    screen.refresh();
                } else {
                    if (key instanceof KeyStroke) {
                        final KeyStroke ks = (KeyStroke) key;
                        screen.setCharacter(pos, new TextCharacter(
                                ks.getCharacter(),
                                TextColor.ANSI.RED,
                                TextColor.ANSI.YELLOW,
                                new HashSet<>()));
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
                } else if (key.getInputType().equals(InputType.Enter)) {
                    terminal.setCursorPosition(pos.withRelativeRow(1).withColumn(0));
                    terminal.flush();
                } else {
                    if (key instanceof KeyStroke) {
                        final KeyStroke ks = (KeyStroke) key;
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
