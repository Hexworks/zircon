package org.hexworks.zircon.examples.components.impl;

import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.component.HBox;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer;
import org.jetbrains.annotations.NotNull;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.ComponentDecorations.shadow;
import static org.hexworks.zircon.api.Components.vbox;

public abstract class TwoColumnComponentExample extends ComponentExample {

    public TwoColumnComponentExample(@NotNull TileGrid tileGrid, @NotNull ColorTheme theme) {
        super(tileGrid, theme);
    }

    @Override
    public void addDemos(HBox demos) {
        VBox leftBox = vbox()
                .withSize(demos.getWidth() / 2, demos.getHeight())
                .withComponentRenderer(new NoOpComponentRenderer<>())
                .withSpacing(1)
                .build();

        VBox rightBox = vbox()
                .withDecorations(box(BoxType.SINGLE, "Buttons on panel"), shadow())
                .withSpacing(1)
                .withSize(demos.getWidth() / 2, demos.getHeight())
                .build();
        rightBox.addComponent(Components.label().build());

        demos.addComponent(leftBox);
        demos.addComponent(rightBox);

        build(leftBox);
        build(rightBox);
    }
}
