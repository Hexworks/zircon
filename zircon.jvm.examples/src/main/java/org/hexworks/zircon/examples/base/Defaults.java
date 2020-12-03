package org.hexworks.zircon.examples.base;

import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource;
import org.hexworks.zircon.internal.resource.BuiltInTrueTypeFontResource;
import org.hexworks.zircon.internal.resource.ColorThemeResource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Defaults {

    public static TilesetResource TILESET = CP437TilesetResources.rogueYun16x16();
    public static ColorThemeResource THEME = ColorThemeResource.GAMEBOOKERS;

    private static Random RANDOM = new Random();

    private static List<Integer> TILESET_SIZES = Collections.singletonList(16);
    private static int TILESET_SIZE = TILESET_SIZES.get(RANDOM.nextInt(TILESET_SIZES.size()));

    public static List<TilesetResource> TILESETS = Arrays.stream(BuiltInCP437TilesetResource.values())
            .filter(tileset -> tileset.getWidth() == TILESET_SIZE && tileset.getHeight() == TILESET_SIZE)
            .collect(Collectors.toList());

    static {
        TILESETS.addAll(BuiltInTrueTypeFontResource.squareFonts(TILESET_SIZE));
    }

    public static Size GRID_SIZE = Size.create(60, 40);

    public static TileGrid startTileGrid() {
        return SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(TILESET)
                .enableBetaFeatures()
                .withSize(GRID_SIZE)
                .build());
    }

    public static TileGrid startTileGrid(TilesetResource tileset) {
        return SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .enableBetaFeatures()
                .withSize(GRID_SIZE)
                .build());
    }

    public static Screen displayScreen() {
        return displayScreen(THEME.getTheme(), TILESET);
    }

    public static Screen displayScreen(ColorTheme theme) {
        return displayScreen(theme, TILESET);
    }

    public static Screen displayScreen(ColorTheme theme, TilesetResource tileset) {
        Screen screen = Screen.create(startTileGrid(tileset));
        screen.setTheme(theme);
        screen.display();
        return screen;
    }
}
