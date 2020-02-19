package org.hexworks.zircon.examples.components;

import org.hexworks.zircon.api.component.ToggleButton;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.uievent.ComponentEventType;
import org.hexworks.zircon.examples.base.TwoColumnComponentExampleJava;

import static org.hexworks.zircon.api.ComponentDecorations.*;
import static org.hexworks.zircon.api.Components.toggleButton;
import static org.hexworks.zircon.api.Functions.fromConsumer;

public class ToggleButtonsExampleJava extends TwoColumnComponentExampleJava {

    public static void main(String[] args) {
        new ToggleButtonsExampleJava().show("Toggle Buttons Example");
    }

    @Override
    public void build(VBox box) {
        ToggleButton invisible = toggleButton()
                .withText("Click Me")
                .withDecorations(side())
                .build();
        invisible.processComponentEvents(ComponentEventType.ACTIVATED, fromConsumer((event) -> {
            invisible.setHidden(true);
        }));

        ToggleButton disabled = toggleButton()
                .withText("Disabled")
                .build();

        box.addComponents(
                toggleButton()
                        .withText("Default")
                        .build(),
                toggleButton()
                        .withText("Boxed")
                        .withDecorations(box())
                        .build(),
                toggleButton()
                        .withText("Too long name for button")
                        .withDecorations(box(), shadow())
                        .withSize(10, 4)
                        .build(),
                toggleButton()
                        .withText("Half Block")
                        .withDecorations(halfBlock(), shadow())
                        .build(),
                invisible, disabled);

        disabled.setDisabled(true);
    }

}
