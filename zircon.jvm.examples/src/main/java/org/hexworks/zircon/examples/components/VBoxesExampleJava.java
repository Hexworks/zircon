package org.hexworks.zircon.examples.components;

import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.AttachedComponent;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.component.HBox;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.modifier.Border;
import org.hexworks.zircon.examples.base.OneColumnComponentExampleJava;
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.hexworks.zircon.api.ComponentDecorations.*;
import static org.hexworks.zircon.api.Components.*;
import static org.hexworks.zircon.api.Functions.fromConsumer;
import static org.hexworks.zircon.api.graphics.BoxType.SINGLE;

public class VBoxesExampleJava extends OneColumnComponentExampleJava {

    private int count = 0;
    private Random random = new Random();

    public static void main(String[] args) {
        new VBoxesExampleJava().show("VBoxes Example");
    }

    @Override
    public void build(VBox box) {

        Button addNew = button()
                .withText("Add New Button")
                .build();

        HBox container = hbox()
                .withPreferredSize(box.getContentSize().minus(Size.create(1, 2)))
                .withComponentRenderer(new NoOpComponentRenderer<>())
                .withSpacing(1)
                .build();

        VBox defaultBox = vbox().withPreferredSize(container.getContentSize().withWidth(14)).build();
        VBox boxedBox = vbox().withPreferredSize(container.getContentSize().withWidth(14)).withDecorations(box(SINGLE, "Boxed VBox")).build();
        VBox borderedBox = vbox().withPreferredSize(container.getContentSize().withWidth(14)).withDecorations(border(Border.newBuilder().build())).build();
        VBox shadowedBox = vbox().withPreferredSize(container.getContentSize().withWidth(14)).withDecorations(shadow()).build();

        List<VBox> buttonContainers = Arrays.asList(defaultBox, borderedBox, boxedBox, shadowedBox);

        box.addComponent(addNew);
        container.addComponents(defaultBox, boxedBox, borderedBox, shadowedBox);
        box.addComponent(container);

        addNew.onActivated(fromConsumer((componentEvent -> {
            addButton(buttonContainers.get(random.nextInt(4)));
        })));

        buttonContainers.forEach(this::addButton);
    }

    private void addButton(VBox box) {
        AttachedComponent attachment = box.addComponent(Components.button()
                .withText(String.format("Remove: %d", count))
                .withPreferredSize(12, 1)
                .build());

        attachment.onActivated(fromConsumer((componentEvent -> attachment.detach())));

        count++;
    }
}
