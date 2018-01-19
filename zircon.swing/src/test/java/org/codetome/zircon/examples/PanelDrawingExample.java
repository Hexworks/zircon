package org.codetome.zircon.examples;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.builder.*;
import org.codetome.zircon.api.color.TextColorFactory;
import org.codetome.zircon.api.font.Font;
import org.codetome.zircon.api.shape.FilledRectangleFactory;
import org.codetome.zircon.api.color.TextColor;
import org.codetome.zircon.api.graphics.Box;
import org.codetome.zircon.api.terminal.Terminal;
import org.codetome.zircon.internal.graphics.BoxType;
import org.codetome.zircon.api.screen.Screen;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.codetome.zircon.api.resource.CP437TilesetResource.WANDERLUST_16X16;

public class PanelDrawingExample {

    private static final int TERMINAL_WIDTH = 19;
    private static final int TERMINAL_HEIGHT = 12;
    private static final Size SIZE = Size.of(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final Font FONT = WANDERLUST_16X16.toFont();
    private static final TextColor BACKGROUND_COLOR = TextColorFactory.fromString("#223344");
    private static final TextColor PANEL_BG_COLOR = TextColorFactory.fromString("#666666");
    private static final TextColor PANEL_FG_COLOR = TextColorFactory.fromString("#ffffff");

    @Ignore
    @Test
    public void checkSetup() {
        main(new String[]{"test"});
    }

    public static void main(String[] args) {
        final Terminal terminal = TerminalUtils.fetchTerminalBuilder(args)
                .font(FONT)
                .initialTerminalSize(SIZE)
                .build();
        final Screen screen = ScreenBuilder.createScreenFor(terminal);
        screen.setCursorVisibility(false);

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
        screen.draw(box, Position.of(2, 2));
        screen.display();
    }
}
