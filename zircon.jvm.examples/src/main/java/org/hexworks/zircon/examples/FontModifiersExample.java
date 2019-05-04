package org.hexworks.zircon.examples;

import kotlin.collections.SetsKt;
import org.hexworks.zircon.api.AppConfigs;
import org.hexworks.zircon.api.Borders;
import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.Modifiers;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.Application;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.modifier.Crop;
import org.hexworks.zircon.api.modifier.RayShade;
import org.hexworks.zircon.api.modifier.SimpleModifiers;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hexworks.zircon.api.modifier.BorderPosition.RIGHT;
import static org.hexworks.zircon.api.modifier.BorderPosition.TOP;

public class FontModifiersExample {

    private static final int TERMINAL_WIDTH = 16;
    private static final int TERMINAL_HEIGHT = 3;
    private static final Size SIZE = Size.Companion.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);

    public static void main(String[] args) {

        Application app = SwingApplications.startApplication(AppConfigs.newConfig()
                .withDefaultTileset(CP437TilesetResources.wanderlust16x16())
                .withSize(SIZE)
                .withDebugMode(true)
                .build());

        TileGrid tileGrid = app.getTileGrid();

        tileGrid.setCursorVisibility(false); // we don't want the cursor right now

        tileGrid.enableModifiers(SimpleModifiers.VerticalFlip.INSTANCE);
        tileGrid.setBackgroundColor(ANSITileColor.BLUE);
        tileGrid.setForegroundColor(ANSITileColor.YELLOW);
        tileGrid.putCharacter('A');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(SimpleModifiers.CrossedOut.INSTANCE);
        tileGrid.setBackgroundColor(ANSITileColor.RED);
        tileGrid.setForegroundColor(ANSITileColor.GREEN);
        tileGrid.putCharacter('B');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(SimpleModifiers.Blink.INSTANCE);
        tileGrid.setBackgroundColor(ANSITileColor.RED);
        tileGrid.setForegroundColor(ANSITileColor.WHITE);
        tileGrid.putCharacter('C');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(SimpleModifiers.Underline.INSTANCE);
        tileGrid.setBackgroundColor(ANSITileColor.BLUE);
        tileGrid.setForegroundColor(ANSITileColor.CYAN);
        tileGrid.putCharacter('D');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(SimpleModifiers.HorizontalFlip.INSTANCE);
        tileGrid.setBackgroundColor(ANSITileColor.BLACK);
        tileGrid.setForegroundColor(ANSITileColor.YELLOW);
        tileGrid.putCharacter('E');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(SimpleModifiers.Blink.INSTANCE);
        tileGrid.setBackgroundColor(ANSITileColor.CYAN);
        tileGrid.setForegroundColor(ANSITileColor.YELLOW);
        tileGrid.putCharacter('F');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(Stream.of(
                SimpleModifiers.HorizontalFlip.INSTANCE,
                SimpleModifiers.VerticalFlip.INSTANCE,
                SimpleModifiers.Blink.INSTANCE).collect(Collectors.toSet()));
        tileGrid.setBackgroundColor(ANSITileColor.BLUE);
        tileGrid.setForegroundColor(ANSITileColor.WHITE);
        tileGrid.putCharacter('G');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(Borders.newBuilder().withBorderPositions(SetsKt.setOf(TOP, RIGHT)).build());
        tileGrid.setBackgroundColor(ANSITileColor.WHITE);
        tileGrid.setForegroundColor(ANSITileColor.BLUE);
        tileGrid.putCharacter('H');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(new RayShade());
        tileGrid.setBackgroundColor(ANSITileColor.WHITE);
        tileGrid.setForegroundColor(ANSITileColor.BLUE);
        tileGrid.putCharacter('I');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(Modifiers.glow());
        tileGrid.setBackgroundColor(ANSITileColor.WHITE);
        tileGrid.setForegroundColor(ANSITileColor.BLUE);
        tileGrid.putCharacter('J');

        putEmptySpace(tileGrid);

        tileGrid.enableModifiers(new Crop(8, 8, 8, 8));
        tileGrid.setBackgroundColor(ANSITileColor.RED);
        tileGrid.setForegroundColor(ANSITileColor.WHITE);
        tileGrid.putCharacter('K');

    }

    private static void putEmptySpace(TileGrid tileGrid) {
        tileGrid.resetColorsAndModifiers();
        tileGrid.setForegroundColor(ANSITileColor.BLACK);
        tileGrid.putCharacter(' ');
    }

}
