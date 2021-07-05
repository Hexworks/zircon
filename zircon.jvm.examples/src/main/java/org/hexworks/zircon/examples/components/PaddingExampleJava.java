package org.hexworks.zircon.examples.components;

import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.examples.base.TwoColumnComponentExampleJava;
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer;

import static org.hexworks.zircon.api.ComponentDecorations.*;
import static org.hexworks.zircon.api.Components.header;
import static org.hexworks.zircon.api.Components.panel;

public class PaddingExampleJava extends TwoColumnComponentExampleJava {

    public static void main(String[] args) {
        new PaddingExampleJava().show("Padding Example");
    }

    @Override
    public void build(VBox box) {

        box.addComponent(header().withText("Panels with padding"));

        int panelWidth = 26;
        int panelHeight = 8;

        Panel p0 = panel().withSize(panelWidth, panelHeight)
                .withDecorations(border())
                .withComponentRenderer(new NoOpComponentRenderer<>())
                .build();

        box.addComponent(p0);
        p0.addComponent(panel()
                .withSize(panelWidth, panelHeight)
                .withDecorations(box(BoxType.SINGLE, "Padding 1"), margin(1)));

        Panel p1 = panel().withSize(panelWidth, panelHeight)
                .withDecorations(border())
                .withComponentRenderer(new NoOpComponentRenderer<>())
                .build();

        box.addComponent(p1);
        p1.addComponent(panel()
                .withSize(panelWidth, panelHeight)
                .withDecorations(box(BoxType.SINGLE, "Padding 1, 2"), margin(1, 2)));

        Panel p2 = panel().withSize(panelWidth, panelHeight)
                .withDecorations(border())
                .withComponentRenderer(new NoOpComponentRenderer<>())
                .build();

        box.addComponent(p2);
        p2.addComponent(panel()
                .withSize(panelWidth, panelHeight)
                .withDecorations(box(BoxType.SINGLE, "Padding 0, 1, 2, 3"), margin(0, 1, 2, 3)));
    }

}
