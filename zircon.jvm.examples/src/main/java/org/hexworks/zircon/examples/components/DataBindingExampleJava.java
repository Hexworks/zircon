package org.hexworks.zircon.examples.components;

import org.hexworks.cobalt.databinding.api.property.Property;
import org.hexworks.zircon.api.component.CheckBox;
import org.hexworks.zircon.api.component.HBox;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.examples.base.OneColumnComponentExampleJava;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.Components.*;
import static org.hexworks.zircon.api.graphics.BoxType.SINGLE;

public class DataBindingExampleJava extends OneColumnComponentExampleJava {

    public static void main(String[] args) {
        new DataBindingExampleJava().show("Data Binding Example");
    }

    @Override
    public void build(VBox box) {

        HBox columns = hbox()
                .withSize(box.getContentSize())
                .build();
        box.addComponents(columns);

        Size columnSize = columns.getContentSize().withWidth(columns.getWidth() / 2);

        VBox leftColumn = vbox()
                .withSize(columnSize)
                .build();

        VBox rightColumn = vbox()
                .withSize(columnSize)
                .build();

        VBox selectionBinding = vbox()
                .withSize(columnSize.withHeight(4))
                .withDecorations(box(SINGLE, "One-way Binding"))
                .build();
        leftColumn.addComponent(selectionBinding);

        CheckBox check0 = checkBox().withText("Check Me!").build();
        CheckBox check1 = checkBox().withText("I am bound!").build();
//        check1.getSelectedProperty().updateFrom(check0.getSelectedProperty());

        selectionBinding.addComponents(check0, check1);

        columns.addComponents(leftColumn, rightColumn);
    }

}
