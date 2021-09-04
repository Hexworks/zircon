package org.hexworks.zircon.examples.base;

import org.hexworks.cobalt.databinding.api.property.Property;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.Fragments;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.builder.fragment.SelectorBuilder;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.component.Container;
import org.hexworks.zircon.api.component.HBox;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.fragment.Selector;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.examples.extensions.SelectorExtensionsKt;
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer;

import static org.hexworks.zircon.examples.base.Defaults.*;

public abstract class ComponentExampleJava {

    private Size gridSize;

    protected ComponentExampleJava() {
        this(GRID_SIZE);
    }

    protected ComponentExampleJava(Size gridSize) {
        this.gridSize = gridSize;
    }

    /**
     * Creates the container for the examples.
     */
    @SuppressWarnings("unused")
    public final VBox createExampleContainer(Screen screen, String title) {

        VBox container = Components.vbox()
                .withPreferredSize(gridSize)
                .withSpacing(1)
                .withComponentRenderer(new NoOpComponentRenderer<>())
                .build();

        HBox heading = Components.hbox()
                .withPreferredSize(gridSize.getWidth(), 5)
                .build();

        VBox controls = Components.vbox()
                .withPreferredSize(gridSize.getWidth() / 2, 5)
                .build();

        controls.addComponent(Components.label().withText("Pick a theme"));

        Selector<ColorTheme> themeSelector = SelectorExtensionsKt.colorThemeSelector(Fragments.INSTANCE)
                .withWidth(controls.getWidth())
                .withDefaultSelected(THEME.getTheme())
                .build();

        controls.addFragment(themeSelector);

        controls.addComponent(Components.label());

        controls.addComponent(Components.label().withText("Pick a tileset"));

        final Selector<TilesetResource> tilesetSelector = SelectorExtensionsKt.tilesetSelector(Fragments.INSTANCE)
                .withWidth(controls.getWidth())
                .withDefaultSelected(TILESET)
                .build();

        controls.addFragment(tilesetSelector);

        heading.addComponents(
                Components.header().withText(title).withPreferredSize(gridSize.getWidth() / 2, 1).build(),
                controls);

        HBox exampleArea = Components.hbox()
                .withComponentRenderer(new NoOpComponentRenderer<>())
                .withPreferredSize(gridSize.getWidth(), gridSize.getHeight() - 6)
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
                .withSize(gridSize.plus(Size.create(2, 2)))
                .build());

        Screen screen = Screen.create(tileGrid);

        Container container = Components.panel()
                .withPreferredSize(gridSize)
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
