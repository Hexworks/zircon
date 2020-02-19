package org.hexworks.zircon.examples.components;

import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.HBox;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.examples.base.OneColumnComponentExampleKotlin;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.component.ComponentAlignment.CENTER;

public class HBoxVBoxExampleJava extends OneColumnComponentExampleKotlin {

    public static void main(String[] args) {
        new HBoxVBoxExampleJava().show("HBox + VBox Example");
    }

    @Override
    public void build(VBox box) {

        int contentWidth = 46;

        VBox table = Components.vbox()
                .withSize(contentWidth + 2, 20)
                .withAlignmentWithin(box, CENTER)
                .withDecorations(box(BoxType.SINGLE, "Crew"))
                .build();

        HBox headerRow = Components.hbox()
                .withSize(contentWidth, 1)
                .build();
        headerRow.addComponent(Components.header().withText("#").withSize(4, 1));
        headerRow.addComponent(Components.header().withText("First Name").withSize(12, 1));
        headerRow.addComponent(Components.header().withText("Last Name").withSize(12, 1));
        headerRow.addComponent(Components.header().withText("Job").withSize(12, 1));

        HBox samLawrey = Components.hbox()
                .withSize(contentWidth, 1)
                .build();
        samLawrey.addComponent(Components.label().withText("0").withSize(4, 1));
        samLawrey.addComponent(Components.label().withText("Sam").withSize(12, 1));
        samLawrey.addComponent(Components.label().withText("Lawrey").withSize(12, 1));
        samLawrey.addComponent(Components.label().withText("Stoneworker").withSize(12, 1));

        HBox janeFisher = Components.hbox()
                .withSize(contentWidth, 1)
                .build();
        janeFisher.addComponent(Components.label().withText("1").withSize(4, 1));
        janeFisher.addComponent(Components.label().withText("Jane").withSize(12, 1));
        janeFisher.addComponent(Components.label().withText("Fisher").withSize(12, 1));
        janeFisher.addComponent(Components.label().withText("Woodcutter").withSize(12, 1));

        HBox johnSmith = Components.hbox()
                .withSize(contentWidth, 1)
                .build();
        johnSmith.addComponent(Components.label().withText("2").withSize(4, 1));
        johnSmith.addComponent(Components.label().withText("John").withSize(12, 1));
        johnSmith.addComponent(Components.label().withText("Smith").withSize(12, 1));
        johnSmith.addComponent(Components.label().withText("Mason").withSize(12, 1));

        HBox steveThrush = Components.hbox()
                .withSize(contentWidth, 1)
                .build();
        steveThrush.addComponent(Components.label().withText("3").withSize(4, 1));
        steveThrush.addComponent(Components.label().withText("Steve").withSize(12, 1));
        steveThrush.addComponent(Components.label().withText("Thrush").withSize(12, 1));
        steveThrush.addComponent(Components.label().withText("Farmer").withSize(12, 1));

        table.addComponent(headerRow);
        table.addComponent(samLawrey);
        table.addComponent(janeFisher);
        table.addComponent(johnSmith);
        table.addComponent(steveThrush);

        box.addComponent(table);
    }

}
