package org.hexworks.zircon.examples.components;

import org.hexworks.zircon.api.component.CheckBox;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.uievent.ComponentEventType;
import org.hexworks.zircon.examples.base.TwoColumnComponentExampleJava;

import static org.hexworks.zircon.api.ComponentDecorations.*;
import static org.hexworks.zircon.api.Components.checkBox;
import static org.hexworks.zircon.api.Functions.fromConsumer;

public class CheckBoxesExampleJava extends TwoColumnComponentExampleJava {

    public static void main(String[] args) {
        new CheckBoxesExampleJava().show("Check Boxes Example");
    }

    @Override
    public void build(VBox box) {
        CheckBox invisible = checkBox()
                .withText("Make me invisible")
                .withDecorations(side())
                .build();
        invisible.processComponentEvents(ComponentEventType.ACTIVATED, fromConsumer((event) -> invisible.setHidden(true)));

        CheckBox disabled = checkBox()
                .withText("Disabled Button")
                .build();

        box.addComponents(
                checkBox()
                        .withText("Default")
                        .build(),
                checkBox()
                        .withText("Boxed")
                        .withDecorations(box())
                        .build(),
                checkBox()
                        .withText("Too long name")
                        .withDecorations(box(), shadow())
                        .withPreferredSize(16, 4)
                        .build(),
                checkBox()
                        .withText("Half block")
                        .withDecorations(halfBlock(), shadow())
                        .build(),
                invisible, disabled);

        disabled.setDisabled(true);
    }

}
