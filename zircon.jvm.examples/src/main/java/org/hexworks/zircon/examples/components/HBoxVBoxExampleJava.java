package org.hexworks.zircon.examples.components;

import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.HBox;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.examples.base.OneColumnComponentExampleJava;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.component.ComponentAlignment.CENTER;

public class HBoxVBoxExampleJava extends OneColumnComponentExampleJava {

    public static void main(String[] args) {
        new HBoxVBoxExampleJava().show("HBox + VBox Example");
    }

    @Override
    public void build(VBox box) {

        int contentWidth = 46;

        VBox table = Components.vbox()
                .withPreferredSize(contentWidth + 2, 20)
                .withAlignmentWithin(box, CENTER)
                .withDecorations(box(BoxType.SINGLE, "Crew"))
                .build();

        HBox headerRow = Components.hbox()
                .withPreferredSize(contentWidth, 1)
                .build();
        headerRow.addComponent(Components.header().withText("#").withPreferredSize(4, 1));
        headerRow.addComponent(Components.header().withText("First Name").withPreferredSize(12, 1));
        headerRow.addComponent(Components.header().withText("Last Name").withPreferredSize(12, 1));
        headerRow.addComponent(Components.header().withText("Job").withPreferredSize(12, 1));

        HBox samLawrey = Components.hbox()
                .withPreferredSize(contentWidth, 1)
                .build();
        samLawrey.addComponent(Components.label().withText("0").withPreferredSize(4, 1));
        samLawrey.addComponent(Components.label().withText("Sam").withPreferredSize(12, 1));
        samLawrey.addComponent(Components.label().withText("Lawrey").withPreferredSize(12, 1));
        samLawrey.addComponent(Components.label().withText("Stoneworker").withPreferredSize(12, 1));

        HBox janeFisher = Components.hbox()
                .withPreferredSize(contentWidth, 1)
                .build();
        janeFisher.addComponent(Components.label().withText("1").withPreferredSize(4, 1));
        janeFisher.addComponent(Components.label().withText("Jane").withPreferredSize(12, 1));
        janeFisher.addComponent(Components.label().withText("Fisher").withPreferredSize(12, 1));
        janeFisher.addComponent(Components.label().withText("Woodcutter").withPreferredSize(12, 1));

        HBox johnSmith = Components.hbox()
                .withPreferredSize(contentWidth, 1)
                .build();
        johnSmith.addComponent(Components.label().withText("2").withPreferredSize(4, 1));
        johnSmith.addComponent(Components.label().withText("John").withPreferredSize(12, 1));
        johnSmith.addComponent(Components.label().withText("Smith").withPreferredSize(12, 1));
        johnSmith.addComponent(Components.label().withText("Mason").withPreferredSize(12, 1));

        HBox steveThrush = Components.hbox()
                .withPreferredSize(contentWidth, 1)
                .build();
        steveThrush.addComponent(Components.label().withText("3").withPreferredSize(4, 1));
        steveThrush.addComponent(Components.label().withText("Steve").withPreferredSize(12, 1));
        steveThrush.addComponent(Components.label().withText("Thrush").withPreferredSize(12, 1));
        steveThrush.addComponent(Components.label().withText("Farmer").withPreferredSize(12, 1));

        table.addComponent(headerRow);
        table.addComponent(samLawrey);
        table.addComponent(janeFisher);
        table.addComponent(johnSmith);
        table.addComponent(steveThrush);

        box.addComponent(table);
    }

}
