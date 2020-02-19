package org.hexworks.zircon.examples.base;

import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource;
import org.hexworks.zircon.internal.resource.ColorThemeResource;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Defaults {

    private static Random RANDOM = new Random();

    private static List<Integer> TILESET_SIZES = Arrays.asList(16, 20);
    private static int TILESET_SIZE = TILESET_SIZES.get(RANDOM.nextInt(TILESET_SIZES.size()));

    public static ColorThemeResource THEME = ColorThemeResource.values()[RANDOM.nextInt(ColorThemeResource.values().length)];
    public static List<TilesetResource> TILESETS = Arrays.stream(BuiltInCP437TilesetResource.values())
            .filter(tileset -> tileset.getWidth() == TILESET_SIZE && tileset.getHeight() == TILESET_SIZE)
            .collect(Collectors.toList());
    public static TilesetResource TILESET = TILESETS.get(RANDOM.nextInt(TILESETS.size()));

    public static Size GRID_SIZE = Size.create(60, 40);

    public static Screen createDefaultScreen() {
        return Screen.create(SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(TILESET)
                .withSize(GRID_SIZE)
                .build()));
    }
}
