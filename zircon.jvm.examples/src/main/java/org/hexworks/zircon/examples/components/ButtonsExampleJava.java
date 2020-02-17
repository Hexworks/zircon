package org.hexworks.zircon.examples.components;

import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.data.Size;

import static org.hexworks.zircon.api.ComponentDecorations.*;
import static org.hexworks.zircon.api.Components.button;
import static org.hexworks.zircon.api.Functions.fromConsumer;
import static org.hexworks.zircon.api.uievent.ComponentEventType.ACTIVATED;

public class ButtonsExampleJava extends ComponentExample {

    public static void main(String[] args) {
        new ButtonsExampleJava().show();
    }

    @Override
    void build(VBox box) {
        Button invisibleButton = button()
                .withText("Make me invisible")
                .withDecorations(side())
                .build();
        invisibleButton.processComponentEvents(ACTIVATED, fromConsumer((event) -> {
            invisibleButton.setHidden(true);
        }));

        Button disabledButton = button()
                .withText("Disabled Button")
                .build();

        box.addComponents(
                button()
                        .withText("Button")
                        .withDecorations(side())
                        .build(),
                button()
                        .withText("Boxed Button")
                        .withDecorations(box())
                        .build(),
                button()
                        .withText("Too long name for button")
                        .withDecorations(box(), shadow())
                        .withSize(Size.create(10, 4))
                        .build(),
                button()
                        .withText("Half block button")
                        .withDecorations(halfBlock(), shadow())
                        .build(),
                invisibleButton, disabledButton);

        disabledButton.setDisabled(true);
    }

}
