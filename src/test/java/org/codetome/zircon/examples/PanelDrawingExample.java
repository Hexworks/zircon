package org.codetome.zircon.examples;

import org.codetome.zircon.Position;
import org.codetome.zircon.Size;
import org.codetome.zircon.api.*;
import org.codetome.zircon.api.shape.FilledRectangleFactory;
import org.codetome.zircon.color.TextColor;
import org.codetome.zircon.graphics.box.Box;
import org.codetome.zircon.graphics.box.BoxType;
import org.codetome.zircon.graphics.box.DefaultBox;
import org.codetome.zircon.screen.Screen;

public class PanelDrawingExample {

    private static final int TERMINAL_WIDTH = 30;
    private static final int TERMINAL_HEIGHT = 20;
    private static final TextColor BACKGROUND_COLOR = TextColorFactory.fromString("#223344");
    private static final TextColor PANEL_BG_COLOR = TextColorFactory.fromString("#666666");
    private static final TextColor PANEL_FG_COLOR = TextColorFactory.fromString("#ffffff");

    public static void main(String[] args) {
        final Screen screen = TerminalBuilder.newBuilder()
                .font(CP437TilesetResource.WANDERLUST_16X16.asJava2DFont())
                .initialTerminalSize(Size.of(TERMINAL_WIDTH, TERMINAL_HEIGHT))
                .buildScreen();
        screen.setCursorVisible(false);

        FilledRectangleFactory
                .buildFilledRectangle(Position.DEFAULT_POSITION, screen.getBoundableSize())
                .toTextImage(TextCharacterBuilder.DEFAULT_CHARACTER
                        .withBackgroundColor(BACKGROUND_COLOR))
                .drawOnto(screen, Position.DEFAULT_POSITION);

        final Box box = BoxBuilder.newBuilder()
                .boxType(BoxType.DOUBLE)
                .size(Size.of(15, 8))
                .style(StyleSetBuilder.newBuilder()
                        .backgroundColor(PANEL_BG_COLOR)
                        .foregroundColor(PANEL_FG_COLOR)
                        .build())
                .build();
        box.putText("Title", Position.DEFAULT_POSITION
                .withRelativeColumn(5));
        box.setCharacterAt(Position.DEFAULT_POSITION.withRelativeColumn(4),
                BoxType.TOP_BOTTOM_DOUBLE.getConnectorLeft());
        box.setCharacterAt(Position.DEFAULT_POSITION.withRelativeColumn(10),
                BoxType.TOP_BOTTOM_DOUBLE.getConnectorRight());
        screen.draw(box, Position.of(6, 2));
        screen.display();
    }
}
