package org.hexworks.zircon.examples.components;

import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.Header;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.examples.base.TwoColumnComponentExampleJava;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.ComponentDecorations.shadow;

public class HeadersExampleJava extends TwoColumnComponentExampleJava {

    public static void main(String[] args) {
        new HeadersExampleJava().show("Headers Example");
    }

    @Override
    public void build(VBox box) {

        box.addComponent(Components.header().withText("Default"));

        box.addComponent(Components.header().withText("Boxed").withDecorations(box()));

        box.addComponent(Components.header().withText("Shadowed").withDecorations(shadow()));

        Header disabled = Components.header().withText("Disabled").build();

        box.addComponent(disabled);

        disabled.setDisabled(true);

    }

}
