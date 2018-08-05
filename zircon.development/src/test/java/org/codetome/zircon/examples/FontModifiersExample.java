package org.codetome.zircon.examples;

import kotlin.collections.SetsKt;
import org.codetome.zircon.api.builder.grid.ApplicationConfigurationBuilder;
import org.codetome.zircon.api.data.Size;
import org.codetome.zircon.api.grid.ApplicationConfiguration;
import org.codetome.zircon.api.grid.TileGrid;
import org.codetome.zircon.api.modifier.Border;
import org.codetome.zircon.api.modifier.RayShade;
import org.codetome.zircon.api.modifier.SimpleModifiers;
import org.codetome.zircon.gui.swing.impl.SwingApplication;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.codetome.zircon.api.color.ANSITextColor.BLACK;
import static org.codetome.zircon.api.color.ANSITextColor.BLUE;
import static org.codetome.zircon.api.color.ANSITextColor.CYAN;
import static org.codetome.zircon.api.color.ANSITextColor.GREEN;
import static org.codetome.zircon.api.color.ANSITextColor.RED;
import static org.codetome.zircon.api.color.ANSITextColor.WHITE;
import static org.codetome.zircon.api.color.ANSITextColor.YELLOW;
import static org.codetome.zircon.api.modifier.BorderPosition.RIGHT;
import static org.codetome.zircon.api.modifier.BorderPosition.TOP;
import static org.codetome.zircon.api.modifier.BorderType.SOLID;
import static org.codetome.zircon.api.resource.CP437TilesetResource.WANDERLUST_16X16;

public class FontModifiersExample {

    private static final int TERMINAL_WIDTH = 16;
    private static final int TERMINAL_HEIGHT = 3;
    private static final Size SIZE = Size.Companion.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);

    public static void main(String[] args) {

        ApplicationConfiguration config = ApplicationConfigurationBuilder.Companion.newBuilder()
                .defaultTileset(WANDERLUST_16X16)
                .defaultSize(SIZE)
                .build();

        SwingApplication app = SwingApplication.Companion.create(config);
        app.start();
        TileGrid tileGrid = app.getTileGrid();

        tileGrid.setCursorVisibility(false); // we don't want the cursor right now

        tileGrid.enableModifiers(SimpleModifiers.VerticalFlip.INSTANCE);
        tileGrid.setBackgroundColor(BLUE);
        tileGrid.setForegroundColor(YELLOW);
        tileGrid.putCharacter('A');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(SimpleModifiers.CrossedOut.INSTANCE);
        tileGrid.setBackgroundColor(RED);
        tileGrid.setForegroundColor(GREEN);
        tileGrid.putCharacter('B');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(SimpleModifiers.Blink.INSTANCE);
        tileGrid.setBackgroundColor(RED);
        tileGrid.setForegroundColor(WHITE);
        tileGrid.putCharacter('C');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(SimpleModifiers.Underline.INSTANCE);
        tileGrid.setBackgroundColor(BLUE);
        tileGrid.setForegroundColor(CYAN);
        tileGrid.putCharacter('D');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(SimpleModifiers.HorizontalFlip.INSTANCE);
        tileGrid.setBackgroundColor(BLACK);
        tileGrid.setForegroundColor(YELLOW);
        tileGrid.putCharacter('E');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(SimpleModifiers.Blink.INSTANCE);
        tileGrid.setBackgroundColor(CYAN);
        tileGrid.setForegroundColor(YELLOW);
        tileGrid.putCharacter('F');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(Stream.of(
                SimpleModifiers.HorizontalFlip.INSTANCE,
                SimpleModifiers.VerticalFlip.INSTANCE,
                SimpleModifiers.Blink.INSTANCE).collect(Collectors.toSet()));
        tileGrid.setBackgroundColor(BLUE);
        tileGrid.setForegroundColor(WHITE);
        tileGrid.putCharacter('G');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(new Border(SOLID, SetsKt.setOf(TOP, RIGHT)));
        tileGrid.setBackgroundColor(WHITE);
        tileGrid.setForegroundColor(BLUE);
        tileGrid.putCharacter('H');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(new RayShade());
        tileGrid.setBackgroundColor(WHITE);
        tileGrid.setForegroundColor(BLUE);
        tileGrid.putCharacter('I');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(SimpleModifiers.Glow.INSTANCE);
        tileGrid.setBackgroundColor(WHITE);
        tileGrid.setForegroundColor(BLUE);
        tileGrid.putCharacter('J');

    }

    private static void putEmptySpace(TileGrid tileGrid) {
        tileGrid.resetColorsAndModifiers();
        tileGrid.setForegroundColor(BLACK);
        tileGrid.putCharacter(' ');
    }

}
