package org.hexworks.zircon.examples.components;

import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.examples.base.Defaults;
import org.hexworks.zircon.examples.base.OneColumnComponentExampleJava;
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer;

import static java.lang.Thread.sleep;
import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.ComponentDecorations.shadow;
import static org.hexworks.zircon.api.Components.*;
import static org.hexworks.zircon.api.GraphicalTilesetResources.nethack16x16;
import static org.hexworks.zircon.api.graphics.BoxType.TOP_BOTTOM_DOUBLE;

public class AllComponentsExampleJava extends OneColumnComponentExampleJava {

    public static void main(String[] args) {
        new AllComponentsExampleJava().show("All Components Example");
    }

    @Override
    public void build(VBox box) {

        HBox columns = hbox()
                .withComponentRenderer(new NoOpComponentRenderer<>())
                .withPreferredSize(box.getContentSize().withRelativeHeight(-1))
                .build();
        box.addComponent(columns);

        int half = columns.getContentSize().getWidth() / 2;

        VBox leftColumn = vbox()
                .withPreferredSize(columns.getContentSize().withWidth(half))
                .withSpacing(1)
                .withDecorations(box(TOP_BOTTOM_DOUBLE, "Content"))
                .build();
        VBox rightColumn = vbox()
                .withPreferredSize(columns.getContentSize().withWidth(half))
                .withSpacing(1)
                .withDecorations(box(TOP_BOTTOM_DOUBLE, "Interactions"))
                .build();

        int columnWidth = rightColumn.getContentSize().getWidth();

        leftColumn.addComponent(header().withText("This is a header"));
        leftColumn.addComponent(label().withText("This is a label"));
        leftColumn.addComponent(listItem().withText("A list item to read"));
        leftColumn.addComponent(paragraph()
                .withPreferredSize(leftColumn.getContentSize().getWidth(), 3)
                .withText("And a multi-line paragraph which is very long."));
        if (Defaults.TILESET.getSize().getWidth() == 16) {
            leftColumn.addComponent(icon()
                    .withIcon(Tile.newBuilder()
                            .withTileset(nethack16x16())
                            .withName("Plate mail")
                            .buildGraphicalTile())
                    .withTileset(nethack16x16()));
        }
        leftColumn.addComponent(textBox(leftColumn.getContentSize().getWidth() - 3)
                .addHeader("Text Box!")
                .withDecorations(box(), shadow())
                .addParagraph("This is a paragraph which won't fit on one line."));

        VBox radioBox = vbox()
                .withPreferredSize(columnWidth, 6)
                .withDecorations(box(), shadow())
                .build();

        RadioButton a = radioButton()
                .withText("Option A")
                .withKey("a")
                .build();
        RadioButton b = radioButton()
                .withText("Option B")
                .withKey("b")
                .build();
        RadioButton c = radioButton()
                .withText("Option C")
                .withKey("c")
                .build();
        radioBox.addComponent(a);
        radioBox.addComponent(b);
        radioBox.addComponent(c);

        RadioButtonGroup group = radioButtonGroup().build();
        group.addComponents(a, b, c);

        rightColumn.addComponent(radioBox);

        rightColumn.addComponent(horizontalNumberInput()
                .withPreferredSize(columnWidth, 1)
                .withInitialValue(5)
                .withMinValue(1)
                .withMaxValue(100)
                .withPreferredSize(columnWidth, 3)
                .withDecorations(box()));

        ProgressBar progressBar = progressBar()
                .withNumberOfSteps(100)
                .withRange(100)
                .withDisplayPercentValueOfProgress(true)
                .withPreferredSize(columnWidth, 3)
                .withDecorations(box())
                .build();

        progressBar.setProgress(1);

        new Thread(() -> {
            try {
                while (progressBar.getProgress() < 100) {
                    sleep(1500);
                    progressBar.setProgress(progressBar.getProgress() + 1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        rightColumn.addComponent(progressBar);

        rightColumn.addComponent(horizontalSlider()
                .withMinValue(1)
                .withMaxValue(100)
                .withNumberOfSteps(100)
                .withPreferredSize(columnWidth, 3));

        columns.addComponents(leftColumn, rightColumn);

    }

}
