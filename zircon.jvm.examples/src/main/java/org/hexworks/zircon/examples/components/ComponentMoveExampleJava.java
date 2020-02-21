package org.hexworks.zircon.examples.components;

import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.examples.base.OneColumnComponentExampleJava;

import static java.lang.Thread.sleep;
import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.Components.button;
import static org.hexworks.zircon.api.Components.panel;

public class ComponentMoveExampleJava extends OneColumnComponentExampleJava {

    public static void main(String[] args) {
        new ComponentMoveExampleJava().show("Moving a Component");
    }

    @Override
    public void build(VBox box) {

        Panel panel = panel()
                .withSize(20, 10)
                .withDecorations(box())
                .build();

        Panel innerPanel = panel()
                .withSize(10, 5)
                .withDecorations(box())
                .build();

        innerPanel.addComponent(button()
                .withText("Foo")
                .withPosition(1, 1)
                .build());

        panel.addComponent(innerPanel);

        box.addComponent(panel);

        new Thread(() -> {
            try {
                sleep(2000);
                panel.moveBy(Position.create(5, 5));

                sleep(2000);
                innerPanel.moveBy(Position.create(2, 2));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
