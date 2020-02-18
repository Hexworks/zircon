package org.hexworks.zircon.examples.components;

import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.AttachedComponent;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.component.HBox;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.modifier.Border;
import org.hexworks.zircon.examples.components.impl.OneColumnComponentExample;

import static org.hexworks.zircon.api.ComponentDecorations.*;
import static org.hexworks.zircon.api.Components.*;
import static org.hexworks.zircon.api.Functions.fromConsumer;
import static org.hexworks.zircon.api.graphics.BoxType.SINGLE;

public class HBoxesExampleJava extends OneColumnComponentExample {

    private int count = 0;

    public static void main(String[] args) {
        new HBoxesExampleJava().show("HBoxes Example");
    }

    @Override
    public void build(VBox box) {

        Button addNew = button()
                .withText("Add New Button")
                .build();

        VBox buttonBox = vbox()
                .withSize(box.getSize().getWidth(), 5)
                .withSpacing(1)
                .build();

        HBox buttons = hbox()
                .withSize(box.getSize().getWidth(), 1)
                .build();

        buttonBox.addComponents(addNew, buttons);

        addNew.onActivated(fromConsumer((componentEvent -> addButton(buttons))));

        HBox boxedBox = hbox().withSize(box.getWidth(), 5).withDecorations(box(SINGLE, "Boxed HBox")).build();
        HBox borderedBox = hbox().withSize(box.getWidth(), 5).withDecorations(border(Border.newBuilder().build())).build();
        HBox shadowedBox = hbox().withSize(box.getWidth(), 5).withDecorations(shadow()).build();


        box.addComponents(buttonBox, boxedBox, borderedBox, shadowedBox);

        addButton(buttons);
    }

    private void addButton(HBox box) {
        AttachedComponent attachment = box.addComponent(Components.button()
                .withText(String.format("Remove: %d", count))
                .withSize(12, 1)
                .build());

        attachment.onActivated(fromConsumer((componentEvent -> attachment.detach())));

        count++;
    }
}
