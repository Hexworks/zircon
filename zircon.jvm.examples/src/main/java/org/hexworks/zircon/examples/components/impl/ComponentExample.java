package org.hexworks.zircon.examples.components.impl;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.component.HBox;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.fragment.MultiSelect;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.view.base.BaseView;
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer;
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource;
import org.hexworks.zircon.internal.resource.ColorThemeResource;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ComponentExample extends BaseView {

    private static ColorThemeResource THEME = ColorThemeResource.SOLARIZED_LIGHT_ORANGE;
    private static TilesetResource TILESET = CP437TilesetResources.rexPaint20x20();
    private static Size SIZE = Size.create(60, 40);
    private static Size CONTENT_SIZE = SIZE.minus(Size.create(2, 2));

    public ComponentExample(@NotNull TileGrid tileGrid, @NotNull ColorTheme theme) {
        super(tileGrid, theme);
    }

    public final void show(String title) {
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
                    getScreen().setTheme(newTheme.getTheme());
                }))
                .build();
        controls.addFragment(themeSelector);

        controls.addComponent(Components.label());
        controls.addComponent(Components.label().withText("Pick a tileset"));

        List<TilesetResource> tilesets = new ArrayList<>();
        for (TilesetResource tileset : BuiltInCP437TilesetResource.values()) {
            if (tileset.getWidth() == 20 && tileset.getHeight() == 20) {
                tilesets.add(tileset);
            }
        }
        MultiSelect<TilesetResource> tilesetSelector = Fragments.multiSelect(controls.getWidth() - 4, tilesets)
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

        container.addComponents(heading, demos);

        getScreen().addComponent(container);

        addDemos(demos);

        dock();
    }

    public abstract void build(VBox box);

    public abstract void addDemos(HBox demos);

    public static TileGrid createGrid() {
        return SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(TILESET)
                .withSize(SIZE)
                .build());
    }

    public static ColorTheme createTheme() {
        return THEME.getTheme();
    }
}
