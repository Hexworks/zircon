package org.hexworks.zircon.examples.components;

import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.component.ComponentStyleSet;
import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.graphics.StyleSet;
import org.hexworks.zircon.examples.base.OneColumnComponentExampleJava;
import org.hexworks.zircon.examples.base.OneColumnComponentExampleKotlin;

import java.util.HashSet;

import static org.hexworks.zircon.api.ComponentDecorations.*;
import static org.hexworks.zircon.api.Components.button;
import static org.hexworks.zircon.api.Components.panel;
import static org.hexworks.zircon.api.color.ANSITileColor.*;

public class ComponentStyleSetExampleJava extends OneColumnComponentExampleJava {

    public static void main(String[] args) {
        new ComponentStyleSetExampleJava().show("Component Style Set");
    }

    @Override
    public void build(VBox box) {

        Panel panel = panel()
                .withDecorations(box(BoxType.SINGLE, "Buttons on panel"), shadow())
                .withPreferredSize(30, 20)
                .withPosition(5, 5)
                .build();

        box.addComponent(panel);

        ComponentStyleSet compStyleSet = ComponentStyleSet.newBuilder()
                .withDefaultStyle(StyleSet.newBuilder().withForegroundColor(CYAN).withBackgroundColor(BLACK).build())
                .withActiveStyle(StyleSet.newBuilder().withForegroundColor(CYAN).withBackgroundColor(BLACK).build())
                .withFocusedStyle(StyleSet.newBuilder().withForegroundColor(RED).withBackgroundColor(BLACK).build())
                .build();


        Button simpleBtn = button()
                .withText("Button")
                .withDecorations(side())
                .withComponentStyleSet(compStyleSet)
                .withPosition(1, 3)
                .build();


        panel.addComponent(simpleBtn);
    }
}
