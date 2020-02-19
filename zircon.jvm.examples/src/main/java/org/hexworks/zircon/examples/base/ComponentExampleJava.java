package org.hexworks.zircon.examples.base;

import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.Fragments;
import org.hexworks.zircon.api.Functions;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.component.Container;
import org.hexworks.zircon.api.component.HBox;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.fragment.MultiSelect;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer;
import org.hexworks.zircon.internal.resource.ColorThemeResource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hexworks.zircon.examples.base.Defaults.*;

public abstract class ComponentExampleJava {

    private Size size;

    public ComponentExampleJava() {
        this(GRID_SIZE);
    }

    public ComponentExampleJava(Size size) {
        this.size = size;
    }

    /**
     * Creates the container for the examples.
     */
    public final VBox createExampleContainer(Screen screen, String title) {

        VBox container = Components.vbox()
                .withSize(size)
                .withSpacing(1)
                .withComponentRenderer(new NoOpComponentRenderer<>())
                .build();

        HBox heading = Components.hbox()
                .withSize(size.getWidth(), 5)
                .build();

        VBox controls = Components.vbox()
                .withSize(size.getWidth() / 2, 5)
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
                Components.header().withText(title).withSize(size.getWidth() / 2, 1).build(),
                controls);

        HBox exampleArea = Components.hbox()
                .withComponentRenderer(new NoOpComponentRenderer<>())
                .withSize(size.getWidth(), size.getHeight() - 6)
                .build();

        addExamples(exampleArea);

        container.addComponents(heading, exampleArea);
        return container;
    }

    /**
     * Shows this example with the given title on the screen.
     */
    public final void show(String title) {
        TileGrid tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(TILESET)
                .withSize(size.plus(Size.create(2, 2)))
                .build());

        Screen screen = Screen.create(tileGrid);

        Container container = Components.panel()
                .withSize(size)
                .withPosition(Position.offset1x1())
                .withComponentRenderer(new NoOpComponentRenderer<>())
                .build();

        container.addComponent(createExampleContainer(screen, title));

        screen.addComponent(container);
        screen.display();
        screen.setTheme(THEME.getTheme());
    }

    /**
     * Builds the actual example code using the given box.
     */
    public abstract void build(VBox box);

    /**
     * Adds the example(s) to the root container according to the layout
     * being used (one column vs two column).
     */
    public abstract void addExamples(HBox exampleArea);
}
