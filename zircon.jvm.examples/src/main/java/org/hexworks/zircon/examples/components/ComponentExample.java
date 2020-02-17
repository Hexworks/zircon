package org.hexworks.zircon.examples.components;

import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.ColorThemes;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.ComponentDecorations.shadow;
import static org.hexworks.zircon.api.Components.header;
import static org.hexworks.zircon.api.Components.vbox;
import static org.hexworks.zircon.api.graphics.BoxType.SINGLE;

abstract class ComponentExample {

    private static ColorTheme THEME = ColorThemes.solarizedLightOrange();
    private static TilesetResource TILESET = CP437TilesetResources.rexPaint20x20();
    private static Size SIZE = Size.create(60, 40);

    final void show() {
        TileGrid tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(TILESET)
                .withSize(SIZE)
                .build());

        Screen screen = Screen.create(tileGrid);

        screen.addComponent(header().withText("Buttons Example").withPosition(1, 1));

        VBox leftBox = vbox()
                .withSize(SIZE.getWidth() / 2 - 4, SIZE.getHeight() - 6)
                .withComponentRenderer(new NoOpComponentRenderer<>())
                .withSpacing(1)
                .withPosition(1, 4)
                .build();

        VBox rightBox = vbox()
                .withDecorations(box(SINGLE, "Buttons on panel"), shadow())
                .withSpacing(1)
                .withSize(SIZE.getWidth() / 2 - 2, SIZE.getHeight() - 3)
                .withPosition(SIZE.getWidth() / 2 + 1, 2)
                .build();
        rightBox.addComponent(Components.label().build());

        screen.addComponent(leftBox);
        screen.addComponent(rightBox);

        build(leftBox);
        build(rightBox);

        screen.display();
        screen.setTheme(THEME);
    }

    abstract void build(VBox box);
}
