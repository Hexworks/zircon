package org.hexworks.zircon.examples.components;

import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode;
import org.hexworks.zircon.examples.base.OneColumnComponentExampleJava;

import java.util.Random;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.ComponentDecorations.halfBlock;
import static org.hexworks.zircon.api.Components.*;
import static org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode.INTERACTIVE;
import static org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode.NON_INTERACTIVE;
import static org.hexworks.zircon.api.graphics.BoxType.*;

public class ComponentDecorationsExampleJava extends OneColumnComponentExampleJava {

    public static void main(String[] args) {
        new ComponentDecorationsExampleJava().show("Component Decorations Example");
    }

    @Override
    public void build(VBox box) {
        HBox columns = hbox()
                .withPreferredSize(box.getContentSize())
                .build();
        box.addComponent(columns);
        int half = columns.getContentSize().getWidth() / 2;

        VBox interactive = vbox()
                .withPreferredSize(half, columns.getHeight())
                .withSpacing(1)
                .withDecorations(box(SINGLE, "Interactive"))
                .build();
        VBox nonInteractive = vbox()
                .withPreferredSize(half, columns.getHeight())
                .withSpacing(1)
                .withDecorations(box(SINGLE, "Non-Interactive"))
                .build();
        columns.addComponents(interactive, nonInteractive);

        addComponents(interactive, INTERACTIVE);
        addComponents(nonInteractive, NON_INTERACTIVE);

    }

    public void addComponents(VBox box, RenderingMode renderingMode) {

        box.addComponent(button()
                .withText("Click Me!")
                .withDecorations(halfBlock(renderingMode))
                .build());

        box.addComponent(checkBox()
                .withText("Check Me!")
                .withDecorations(box(SINGLE, "", renderingMode))
                .build());

        box.addComponent(toggleButton()
                .withText("Toggle Me!")
                .withDecorations(box(BASIC, "", renderingMode))
                .build());

        RadioButton a = radioButton()
                .withText("Press A!")
                .withDecorations(box(DOUBLE, "", renderingMode))
                .withKey("a")
                .build();
        box.addComponent(a);

        RadioButton b = radioButton()
                .withText("Press B!")
                .withDecorations(box(DOUBLE, "", renderingMode))
                .withKey("b")
                .build();
        box.addComponent(b);

        RadioButtonGroup rbg = radioButtonGroup().build();
        rbg.addComponents(a, b);

        box.addComponent(horizontalSlider()
                .withMinValue(1)
                .withMaxValue(100)
                .withNumberOfSteps(box.getContentSize().getWidth() - 5)
                .withDecorations(box(TOP_BOTTOM_DOUBLE, "Slide Me", renderingMode))
                .build());

        ProgressBar leftBar = progressBar()
                .withDisplayPercentValueOfProgress(true)
                .withNumberOfSteps(box.getContentSize().getWidth() - 4)
                .withRange(100)
                .withDecorations(box(TOP_BOTTOM_DOUBLE, "I'm progressing", renderingMode))
                .build();
        leftBar.setProgress(42);
        box.addComponent(leftBar);
    }

}
