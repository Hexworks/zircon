package org.hexworks.zircon.examples.components.impl;

import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.component.HBox;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer;
import org.jetbrains.annotations.NotNull;

import static org.hexworks.zircon.api.Components.vbox;

public abstract class OneColumnComponentExample extends ComponentExample {

    public OneColumnComponentExample(@NotNull TileGrid tileGrid, @NotNull ColorTheme theme) {
        super(tileGrid, theme);
    }

    @Override
    public void addDemos(HBox demos) {
        VBox box = vbox()
                .withSize(demos.getWidth(), demos.getHeight())
                .withComponentRenderer(new NoOpComponentRenderer<>())
                .withSpacing(1)
                .build();
        build(box);
        demos.addComponent(box);
    }
}
