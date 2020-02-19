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
        new ComponentStyleSetExampleJava().show("Moving a Component");
    }

    @Override
    public void build(VBox box) {

        Panel panel = panel()
                .withDecorations(box(BoxType.SINGLE, "Buttons on panel"), shadow())
                .withSize(30, 20)
                .withPosition(5, 5)
                .build();

        box.addComponent(panel);

        ComponentStyleSet compStyleSet = ComponentStyleSet.newBuilder()
                .withDefaultStyle(StyleSet.create(CYAN, BLACK, new HashSet<>()))
                .withActiveStyle(StyleSet.create(CYAN, BLACK, new HashSet<>()))
                .withFocusedStyle(StyleSet.create(RED, BLACK, new HashSet<>()))
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
