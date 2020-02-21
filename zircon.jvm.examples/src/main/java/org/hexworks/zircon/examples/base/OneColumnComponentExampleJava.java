package org.hexworks.zircon.examples.base;

import org.hexworks.zircon.api.component.HBox;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer;

import static org.hexworks.zircon.api.Components.vbox;

public abstract class OneColumnComponentExampleJava extends ComponentExampleJava {

    public OneColumnComponentExampleJava() {
        super();
    }

    public OneColumnComponentExampleJava(Size size) {
        super(size);
    }

    @Override
    public final void addExamples(HBox exampleArea) {
        VBox box = vbox()
                .withSize(exampleArea.getWidth(), exampleArea.getHeight())
                .withComponentRenderer(new NoOpComponentRenderer<>())
                .withSpacing(1)
                .build();
        build(box);
        exampleArea.addComponent(box);
    }
}
