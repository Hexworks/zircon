package org.hexworks.zircon.examples.base;

import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.Fragments;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.component.Container;
import org.hexworks.zircon.api.component.HBox;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer;

import static org.hexworks.zircon.examples.base.Defaults.*;

public abstract class ComponentExampleJava {

    private Size gridSize;

    public ComponentExampleJava() {
        this(GRID_SIZE);
    }

    public ComponentExampleJava(Size gridSize) {
        this.gridSize = gridSize;
    }

    /**
     * Creates the container for the examples.
     */
    public final VBox createExampleContainer(Screen screen, String title) {

        VBox container = Components.vbox()
                .withSize(gridSize)
                .withSpacing(1)
                .withComponentRenderer(new NoOpComponentRenderer<>())
                .build();

        HBox heading = Components.hbox()
                .withSize(gridSize.getWidth(), 5)
                .build();

        VBox controls = Components.vbox()
                .withSize(gridSize.getWidth() / 2, 5)
                .build();

        controls.addComponent(Components.label().withText("Pick a theme"));

        controls.addFragment(Fragments.colorThemeSelector(controls.getWidth(), THEME.getTheme())
                .withThemeables(screen)
                .build());

        controls.addComponent(Components.label());

        controls.addComponent(Components.label().withText("Pick a tileset"));
        controls.addFragment(Fragments.tilesetSelector(controls.getWidth(), TILESET)
                .withTilesetOverrides(container)
                .build());

        heading.addComponents(
                Components.header().withText(title).withSize(gridSize.getWidth() / 2, 1).build(),
                controls);

        HBox exampleArea = Components.hbox()
                .withComponentRenderer(new NoOpComponentRenderer<>())
                .withSize(gridSize.getWidth(), gridSize.getHeight() - 6)
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
                .withSize(gridSize)
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
