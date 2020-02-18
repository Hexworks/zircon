package org.hexworks.zircon.examples.components.impl;

import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.Fragments;
import org.hexworks.zircon.api.Functions;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.component.HBox;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.fragment.MultiSelect;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer;
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource;
import org.hexworks.zircon.internal.resource.ColorThemeResource;

import java.util.*;
import java.util.stream.Collectors;

public abstract class ComponentExample {

    public static Size SIZE = Size.create(60, 40);

    private static ColorThemeResource THEME = ColorThemeResource.values()[new Random().nextInt(ColorThemeResource.values().length)];
    private static List<Integer> TILESET_SIZES = Arrays.asList(16, 20);
    private static int TILESET_SIZE = TILESET_SIZES.get(new Random().nextInt(TILESET_SIZES.size()));
    private static List<TilesetResource> TILESETS = Arrays.stream(BuiltInCP437TilesetResource.values())
            .filter(tileset -> tileset.getWidth() == TILESET_SIZE && tileset.getHeight() == TILESET_SIZE)
            .collect(Collectors.toList());
    private static TilesetResource TILESET = TILESETS.get(new Random().nextInt(TILESETS.size()));
    private static Size CONTENT_SIZE = SIZE.minus(Size.create(2, 2));

    public final VBox createContent(Screen screen, String title) {

        VBox container = Components.vbox()
                .withSize(CONTENT_SIZE)
                .withComponentRenderer(new NoOpComponentRenderer<>())
                .withSpacing(1)
                .withPosition(1, 1)
                .build();

        HBox heading = Components.hbox()
                .withSize(CONTENT_SIZE.getWidth(), 5)
                .build();

        VBox controls = Components.vbox()
                .withSize(CONTENT_SIZE.getWidth() / 2, 5)
                .build();

        controls.addComponent(Components.label().withText("Pick a theme"));

        List<ColorThemeResource> themes = new ArrayList<>();
        Collections.addAll(themes, ColorThemeResource.values());
        MultiSelect<ColorThemeResource> themeSelector = Fragments.multiSelect(controls.getWidth() - 4, themes)
                .withDefaultSelected(THEME)
                .withCallback(Functions.fromBiConsumer((oldTheme, newTheme) -> {
                    screen.setTheme(newTheme.getTheme());
                }))
                .build();
        controls.addFragment(themeSelector);

        controls.addComponent(Components.label());
        controls.addComponent(Components.label().withText("Pick a tileset"));

        MultiSelect<TilesetResource> tilesetSelector = Fragments.multiSelect(controls.getWidth() - 4, TILESETS)
                .withDefaultSelected(TILESET)
                .withCallback(Functions.fromBiConsumer((oldTileset, newTileset) -> {
                    container.setTileset(newTileset);
                }))
                .build();
        controls.addFragment(tilesetSelector);

        heading.addComponents(
                Components.header().withText(title).withSize(CONTENT_SIZE.getWidth() / 2, 1).build(),
                controls);

        HBox demos = Components.hbox()
                .withComponentRenderer(new NoOpComponentRenderer<>())
                .withSize(CONTENT_SIZE.getWidth(), CONTENT_SIZE.getHeight() - 6)
                .build();

        addDemos(demos);

        container.addComponents(heading, demos);
        return container;
    }

    public final void show(String title) {
        TileGrid tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(TILESET)
                .withSize(SIZE)
                .build());

        Screen screen = Screen.create(tileGrid);

        screen.addComponent(createContent(screen, title));
        screen.display();
        screen.setTheme(THEME.getTheme());
    }

    public abstract void build(VBox box);

    public abstract void addDemos(HBox demos);
}
