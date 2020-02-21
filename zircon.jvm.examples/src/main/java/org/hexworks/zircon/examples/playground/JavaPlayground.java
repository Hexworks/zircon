package org.hexworks.zircon.examples.playground;

import org.hexworks.zircon.api.ComponentAlignments;
import org.hexworks.zircon.api.ComponentDecorations;
import org.hexworks.zircon.api.Functions;
import org.hexworks.zircon.api.component.AttachedComponent;
import org.hexworks.zircon.api.component.ComponentAlignment;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.examples.base.Defaults;

import static org.hexworks.zircon.api.Components.button;
import static org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode;

public class JavaPlayground {

    public static void main(String[] args) {

        Screen screen = Defaults.displayScreen();

        AttachedComponent attachment = screen.addComponent(button()
                .withText("Click Me!") // 1
                .withAlignment(ComponentAlignments.alignmentWithin(screen, ComponentAlignment.CENTER)) // 2
                // 3
                .withDecorations(ComponentDecorations // 4
                        .box(BoxType.SINGLE, "", RenderingMode.INTERACTIVE)) // 5
                .build());
        attachment.onActivated(Functions.fromConsumer((event) -> { // 6
            attachment.detach(); // 7
        }));
    }

}
