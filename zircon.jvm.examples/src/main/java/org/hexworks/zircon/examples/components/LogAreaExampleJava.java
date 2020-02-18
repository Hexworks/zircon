package org.hexworks.zircon.examples.components;

import org.hexworks.zircon.api.builder.component.ParagraphBuilder;
import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.component.ComponentStyleSet;
import org.hexworks.zircon.api.component.LogArea;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.graphics.StyleSet;
import org.hexworks.zircon.examples.components.impl.OneColumnComponentExample;

import java.util.HashSet;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.Components.button;
import static org.hexworks.zircon.api.Components.logArea;
import static org.hexworks.zircon.api.color.ANSITileColor.YELLOW;

public class LogAreaExampleJava extends OneColumnComponentExample {

    public static void main(String[] args) {
        new LogAreaExampleJava().show("Log Area Example");
    }

    @Override
    public void build(VBox box) {

        LogArea logArea = logArea()
                .withDecorations(box(BoxType.DOUBLE, "Log"))
                .withSize(box.getContentSize().withRelativeHeight(-1))
                .build();

        logArea.addParagraph("This is a simple log row", false, 0);

        logArea.addParagraph("This is a log row with a typing effect", false, 200);
        logArea.addNewRows(2);

        logArea.addInlineText("This is a log row with a ");
        Button btn = button()
                .withText("Button")
                .build();
        logArea.addInlineComponent(btn);
        logArea.commitInlineElements();

        logArea.addNewRows(2);
        logArea.addParagraph("This is a long log row, which gets wrapped, since it is long", false, 0);


        logArea.addNewRows(1);
        ComponentStyleSet paragraphStyleSet = ComponentStyleSet.newBuilder()
                .withDefaultStyle(StyleSet.create(YELLOW, TileColor.defaultBackgroundColor(), new HashSet<>()))
                .build();
        logArea.addParagraph(ParagraphBuilder.newBuilder()
                .withText("This is a long log row, which gets wrapped, since it is long with a different style")
                .withComponentStyleSet(paragraphStyleSet), false);

        box.addComponent(logArea);
    }

}
