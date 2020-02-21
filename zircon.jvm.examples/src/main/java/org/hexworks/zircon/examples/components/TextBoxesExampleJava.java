package org.hexworks.zircon.examples.components;

import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.AttachedComponent;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.examples.base.TwoColumnComponentExampleJava;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.ComponentDecorations.shadow;
import static org.hexworks.zircon.api.Functions.fromConsumer;

public class TextBoxesExampleJava extends TwoColumnComponentExampleJava {

    private int count = 0;

    public static void main(String[] args) {
        new TextBoxesExampleJava().show("Text Boxes Example");
    }

    @Override
    public void build(VBox box) {

        box.addComponent(Components.textBox(26)
                .addHeader("Header!")
                .addParagraph("This is a plain text box.")
                .addNewLine()
                .addListItem("This is a list item")
                .addListItem("And another list item")
                .addNewLine()
                .addInlineText("Inline text ")
                .addInlineComponent(Components.button()
                        .withText("Button")
                        .build())
                .addInlineText(" text")
                .commitInlineElements()
                .addNewLine()
                .addParagraph("And a multi-line paragraph with typewriter effect...", false, 200));
        box.addComponent(Components.textBox(22)
                .addHeader("Decorated!")
                .withDecorations(box(), shadow())
                .addParagraph("This is a paragraph which won't fit on one line."));
    }

    private void addButton(VBox box) {
        AttachedComponent attachment = box.addComponent(Components.button()
                .withText(String.format("Remove: %d", count))
                .withSize(12, 1)
                .build());

        attachment.onActivated(fromConsumer((componentEvent -> attachment.detach())));

        count++;
    }
}
