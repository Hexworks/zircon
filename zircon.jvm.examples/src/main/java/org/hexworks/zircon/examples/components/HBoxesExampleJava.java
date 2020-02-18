package org.hexworks.zircon.examples.components;

import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.modifier.Border;
import org.hexworks.zircon.examples.components.impl.OneColumnComponentExample;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.hexworks.zircon.api.ComponentDecorations.*;
import static org.hexworks.zircon.api.Components.*;
import static org.hexworks.zircon.api.Functions.fromConsumer;
import static org.hexworks.zircon.api.graphics.BoxType.SINGLE;

public class HBoxesExampleJava extends OneColumnComponentExample {

    private int count = 0;
    private Random random = new Random();


    public static void main(String[] args) {
        new HBoxesExampleJava(createGrid(), createTheme()).show("HBoxes Example");
    }

    public HBoxesExampleJava(@NotNull TileGrid tileGrid, @NotNull ColorTheme theme) {
        super(tileGrid, theme);
    }

    @Override
    public void build(VBox box) {

        Button addNew = button()
                .withText("Add New Button")
                .build();

        HBox defaultBox = hbox().withSize(box.getSize().getWidth(), 3).build();
        HBox boxedBox = hbox().withSize(box.getWidth(), 5).withDecorations(box(SINGLE, "Boxed HBox")).build();
        HBox borderedBox = hbox().withSize(box.getWidth(), 5).withDecorations(border(Border.newBuilder().build())).build();
        HBox shadowedBox = hbox().withSize(box.getWidth(), 5).withDecorations(shadow()).build();

        List<HBox> buttonContainers = Arrays.asList(defaultBox, borderedBox, boxedBox, shadowedBox);

        box.addComponents(addNew, defaultBox, boxedBox, borderedBox, shadowedBox);

        addNew.onActivated(fromConsumer((componentEvent -> {
            addButton(buttonContainers.get(random.nextInt(4)));
        })));

        buttonContainers.forEach(this::addButton);
    }

    private void addButton(HBox box) {
        AttachedComponent attachment = box.addComponent(Components.button()
                .withText(String.format("Remove: %d", count))
                .withSize(12, 1)
                .build());

        attachment.onActivated(fromConsumer((componentEvent -> attachment.detach())));

        count++;
    }
}
