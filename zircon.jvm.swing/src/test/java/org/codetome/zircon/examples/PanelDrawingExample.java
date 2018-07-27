package org.codetome.zircon.examples;

import org.codetome.zircon.TerminalUtils;
import org.codetome.zircon.api.data.Size;
import org.codetome.zircon.api.color.TextColor;
import org.codetome.zircon.api.graphics.Box;
import org.codetome.zircon.api.graphics.BoxType;
import org.codetome.zircon.api.interop.*;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.api.grid.TileGrid;

import static org.codetome.zircon.api.resource.CP437TilesetResource.WANDERLUST_16X16;

public class PanelDrawingExample {

    private static final int TERMINAL_WIDTH = 19;
    private static final int TERMINAL_HEIGHT = 12;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final TextColor BACKGROUND_COLOR = TextColors.fromString("#223344");
    private static final TextColor PANEL_BG_COLOR = TextColors.fromString("#666666");
    private static final TextColor PANEL_FG_COLOR = TextColors.fromString("#ffffff");

    public static void main(String[] args) {
        final TileGrid tileGrid = TerminalUtils.fetchTerminalBuilder(args)
                .font(WANDERLUST_16X16.toFont())
                .initialTerminalSize(SIZE)
                .build();
        final Screen screen = Screens.createScreenFor(tileGrid);
        screen.setCursorVisibility(false);

        Shapes
                .buildFilledRectangle(Positions.defaultPosition(), screen.getBoundableSize())
                .toTextImage(Tiles.defaultTile()
                        .withBackgroundColor(BACKGROUND_COLOR))
                .drawOnto(screen, Positions.defaultPosition());

        final Box box = Boxes.newBuilder()
                .boxType(BoxType.DOUBLE)
                .size(Sizes.create(15, 8))
                .style(StyleSets.newBuilder()
                        .backgroundColor(PANEL_BG_COLOR)
                        .foregroundColor(PANEL_FG_COLOR)
                        .build())
                .build();
        box.putText("Title", Positions.defaultPosition()
                .withRelativeX(5));
        box.setCharacterAt(Positions.defaultPosition().withRelativeX(4),
                BoxType.TOP_BOTTOM_DOUBLE.getConnectorLeft());
        box.setCharacterAt(Positions.defaultPosition().withRelativeX(10),
                BoxType.TOP_BOTTOM_DOUBLE.getConnectorRight());
        screen.draw(box, Positions.create(2, 2));
        screen.display();
    }
}
