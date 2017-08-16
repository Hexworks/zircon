package org.codetome.zircon.examples;

import org.codetome.zircon.Position;
import org.codetome.zircon.Symbols;
import org.codetome.zircon.api.*;
import org.codetome.zircon.color.TextColor;
import org.codetome.zircon.graphics.TextGraphics;
import org.codetome.zircon.graphics.TextImage;
import org.codetome.zircon.graphics.box.BoxConnectingMode;
import org.codetome.zircon.graphics.box.BoxType;
import org.codetome.zircon.graphics.style.DefaultStyleSet;
import org.codetome.zircon.screen.Screen;
import org.codetome.zircon.Size;

import java.util.Collections;

import static org.codetome.zircon.Symbols.*;

public class PanelDrawingExample {

    private static final int TERMINAL_WIDTH = 50;
    private static final int TERMINAL_HEIGHT = 24;
    private static final TextColor BACKGROUND_COLOR = TextColorFactory.fromString("#223344");

    public static void main(String[] args) {
        final Screen screen = TerminalBuilder.newBuilder()
                .font(PhysicalFontResource.SOURCE_CODE_PRO.asPhysicalFont())
                .initialTerminalSize(Size.of(TERMINAL_WIDTH, TERMINAL_HEIGHT))
                .buildScreen();

        drawBackground(screen);

        screen.newTextGraphics().drawImage(
                Position.TOP_LEFT_CORNER,
                createPanel(20, 10, "Foo and Bar"));

//        screen.newTextGraphics().drawImage(
//                Position.of(24, 2),
//                createBackgroundForPanel(createPanel(20, 5, "Wom and Bat")));

        screen.display();
    }

    private static TextImage createBackgroundForPanel(TextImage panel) {
        final TextImage bg = TextImageBuilder.newBuilder()
                .size(Size.of(panel.getBoundableSize().getColumns() + 1, panel.getBoundableSize().getRows() + 1))
                .build();
        final Size bgSize = bg.getBoundableSize();
        final TextGraphics panelWithBg = bg.newTextGraphics();
        panelWithBg.setBackgroundColor(TextColorFactory.fromString("#112233"));
        panelWithBg.setForegroundColor(TextColorFactory.fromString("#223344"));
        panelWithBg.fill(Symbols.BLOCK_MIDDLE);
        panelWithBg.setBackgroundColor(BACKGROUND_COLOR);
        panelWithBg.setCharacter(Position.of(bgSize.getColumns() - 1, 0), ' ');
        panelWithBg.setCharacter(Position.of(0, bgSize.getRows() - 1), ' ');
        panel.copyTo(bg);
        return bg;
    }

    private static void drawBackground(Screen screen) {
        final TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(BACKGROUND_COLOR);
        graphics.fill(' ');
    }

    private static TextImage createPanel(int width, int height, String title) {
        final TextImage image = TextImageBuilder.newBuilder()
                .size(Size.of(width, height))
                .build();
        final TextGraphics graphics = image.newTextGraphics();
        graphics.drawBox(Position.OFFSET_1x1, Size.of(width -2, height -2), StyleSetBuilder.newBuilder()
                .backgroundColor(TextColorFactory.fromString("#666666"))
                .build(), BoxType.DOUBLE, BoxConnectingMode.CONNECT);
//        graphics.setBackgroundColor(TextColorFactory.fromString("#666666"));
//        graphics.setCharacter(topLeft, DOUBLE_LINE_TOP_LEFT_CORNER);
//        graphics.setCharacter(topRight, DOUBLE_LINE_TOP_RIGHT_CORNER);
//        graphics.setCharacter(bottomLeft, DOUBLE_LINE_BOTTOM_LEFT_CORNER);
//        graphics.setCharacter(bottomRight, DOUBLE_LINE_BOTTOM_RIGHT_CORNER);
//        graphics.drawLine(topLeft.withRelativeColumn(1), topRight.withRelativeColumn(-1), DOUBLE_LINE_HORIZONTAL);
//        graphics.drawLine(bottomLeft.withRelativeColumn(1), bottomRight.withRelativeColumn(-1), DOUBLE_LINE_HORIZONTAL);
//        graphics.drawLine(topLeft.withRelativeRow(1), bottomLeft.withRelativeRow(-1), DOUBLE_LINE_VERTICAL);
//        graphics.drawLine(topRight.withRelativeRow(1), bottomRight.withRelativeRow(-1), DOUBLE_LINE_VERTICAL);
//        graphics.fillRectangle(
//                topLeft.withRelative(Position.of(1, 1)),
//                Size.of(width - 2, height - 2),
//                ' ');
        graphics.setForegroundColor(TextColorFactory.fromString("#cccc22"));
        graphics.putString(
                Position.TOP_LEFT_CORNER.withRelativeColumn(width / 2 - title.length() / 2),
                title,
                Collections.emptySet());
        return image;
    }
}
