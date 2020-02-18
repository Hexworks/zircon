package org.hexworks.zircon.examples.components;

import org.hexworks.zircon.api.component.Label;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.examples.components.impl.TwoColumnComponentExample;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.ComponentDecorations.shadow;
import static org.hexworks.zircon.api.Components.label;

public class LabelsExampleJava extends TwoColumnComponentExample {

    public static void main(String[] args) {
        new LabelsExampleJava().show("Headers Example");
    }

    @Override
    public void build(VBox box) {

        box.addComponent(label().withText("Default"));

        box.addComponent(label().withText("Boxed").withDecorations(box()));

        box.addComponent(label().withText("Shadowed").withDecorations(shadow()));

        Label disabled = label().withText("Disabled").build();

        box.addComponent(disabled);

        disabled.setDisabled(true);

    }

}
