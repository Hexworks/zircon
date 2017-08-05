package org.codetome.zircon.examples;

import org.codetome.zircon.Symbols;
import org.codetome.zircon.Position;
import org.codetome.zircon.TextColor;
import org.codetome.zircon.builder.TextColorFactory;
import org.codetome.zircon.builder.FontRendererBuilder;
import org.codetome.zircon.builder.TextImageBuilder;
import org.codetome.zircon.graphics.TextGraphics;
import org.codetome.zircon.graphics.TextImage;
import org.codetome.zircon.screen.Screen;
import org.codetome.zircon.screen.TerminalScreen;
import org.codetome.zircon.terminal.DefaultTerminalBuilder;
import org.codetome.zircon.terminal.Size;
import org.codetome.zircon.tileset.DFTilesetResource;

import java.util.Collections;

import static org.codetome.zircon.Symbols.*;

public class PanelDrawingExample {

    private static final int TERMINAL_WIDTH = 50;
    private static final int TERMINAL_HEIGHT = 24;
    private static final TextColor BACKGROUND_COLOR = TextColorFactory.fromString("#223344");

    public static void main(String[] args) {
        final TerminalScreen screen = DefaultTerminalBuilder.newBuilder()
                .fontRenderer(FontRendererBuilder.newBuilder()
                        .useSwing()
                        .useDFTileset(DFTilesetResource.WANDERLUST_16X16)
                        .build())
                .initialTerminalSize(new Size(TERMINAL_WIDTH, TERMINAL_HEIGHT))
                .buildScreen();

        drawBackground(screen);

        screen.newTextGraphics().drawImage(
                new Position(2, 2),
                createPanel(20, 10, "Foo and Bar"));

        screen.newTextGraphics().drawImage(
                new Position(24, 2),
                createBackgroundForPanel(createPanel(20, 5, "Wom and Bat")));

        screen.display();
    }

    private static TextImage createBackgroundForPanel(TextImage panel) {
        final TextImage bg = TextImageBuilder.newBuilder()
                .size(new Size(panel.getSize().getColumns() + 1, panel.getSize().getRows() + 1))
                .build();
        final Size bgSize = bg.getSize();
        final TextGraphics panelWithBg = bg.newTextGraphics();
        panelWithBg.setBackgroundColor(TextColorFactory.fromString("#112233"));
        panelWithBg.setForegroundColor(TextColorFactory.fromString("#223344"));
        panelWithBg.fill(Symbols.BLOCK_MIDDLE);
        panelWithBg.setBackgroundColor(BACKGROUND_COLOR);
        panelWithBg.setCharacter(new Position(bgSize.getColumns() - 1, 0), ' ');
        panelWithBg.setCharacter(new Position(0, bgSize.getRows() - 1), ' ');
        panel.copyTo(bg);
        return bg;
    }

    private static void drawBackground(Screen screen) {
        final TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(BACKGROUND_COLOR);
        graphics.fill(' ');
    }

    private static TextImage createPanel(int width, int height, String title) {
        final Position topLeft = Position.getTOP_LEFT_CORNER();
        final Position topRight = topLeft.withRelativeColumn(width - 1);
        final Position bottomLeft = topLeft.withRelativeRow(height - 1);
        final Position bottomRight = topRight.withRelativeRow(height - 1);

        final TextImage image = TextImageBuilder.newBuilder()
                .size(new Size(width, height))
                .build();
        final TextGraphics graphics = image.newTextGraphics();
        graphics.setBackgroundColor(TextColorFactory.fromString("#666666"));
        graphics.setCharacter(topLeft, DOUBLE_LINE_TOP_LEFT_CORNER);
        graphics.setCharacter(topRight, DOUBLE_LINE_TOP_RIGHT_CORNER);
        graphics.setCharacter(bottomLeft, DOUBLE_LINE_BOTTOM_LEFT_CORNER);
        graphics.setCharacter(bottomRight, DOUBLE_LINE_BOTTOM_RIGHT_CORNER);
        graphics.drawLine(topLeft.withRelativeColumn(1), topRight.withRelativeColumn(-1), DOUBLE_LINE_HORIZONTAL);
        graphics.drawLine(bottomLeft.withRelativeColumn(1), bottomRight.withRelativeColumn(-1), DOUBLE_LINE_HORIZONTAL);
        graphics.drawLine(topLeft.withRelativeRow(1), bottomLeft.withRelativeRow(-1), DOUBLE_LINE_VERTICAL);
        graphics.drawLine(topRight.withRelativeRow(1), bottomRight.withRelativeRow(-1), DOUBLE_LINE_VERTICAL);
        graphics.fillRectangle(
                topLeft.withRelative(new Position(1, 1)),
                new Size(width - 2, height - 2),
                ' ');
        graphics.setForegroundColor(TextColorFactory.fromString("#cccc22"));
        graphics.putString(
                topLeft.withRelativeColumn(width / 2 - title.length() / 2),
                title,
                Collections.emptySet());
        return image;
    }
}
