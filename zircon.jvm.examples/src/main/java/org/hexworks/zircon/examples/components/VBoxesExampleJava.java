package org.hexworks.zircon.examples.components;

import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.modifier.Border;
import org.hexworks.zircon.examples.components.impl.OneColumnComponentExample;
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.hexworks.zircon.api.ComponentDecorations.*;
import static org.hexworks.zircon.api.Components.*;
import static org.hexworks.zircon.api.Functions.fromConsumer;
import static org.hexworks.zircon.api.graphics.BoxType.SINGLE;

public class VBoxesExampleJava extends OneColumnComponentExample {

    private int count = 0;
    private Random random = new Random();

    public static void main(String[] args) {
        new VBoxesExampleJava(createGrid(), createTheme()).show("HBoxes Example");
    }

    public VBoxesExampleJava(@NotNull TileGrid tileGrid, @NotNull ColorTheme theme) {
        super(tileGrid, theme);
    }

    @Override
    public void build(VBox box) {

        Button addNew = button()
                .withText("Add New Button")
                .build();

        HBox container = hbox()
                .withSize(box.getContentSize().minus(Size.create(1, 3)))
                .withComponentRenderer(new NoOpComponentRenderer<>())
                .withSpacing(1)
                .build();

        VBox defaultBox = vbox().withSize(container.getContentSize().withWidth(12)).build();
        VBox boxedBox = vbox().withSize(container.getContentSize().withWidth(14)).withDecorations(box(SINGLE, "Boxed VBox")).build();
        VBox borderedBox = vbox().withSize(container.getContentSize().withWidth(14)).withDecorations(border(Border.newBuilder().build())).build();
        VBox shadowedBox = vbox().withSize(container.getContentSize().withWidth(13)).withDecorations(shadow()).build();

        List<VBox> buttonContainers = Arrays.asList(defaultBox, borderedBox, boxedBox, shadowedBox);

        box.addComponent(addNew);
        container.addComponents(defaultBox, boxedBox, borderedBox, shadowedBox);
        box.addComponent(container);

        addNew.onActivated(fromConsumer((componentEvent -> {
            addButton(buttonContainers.get(random.nextInt(4)));
        })));

        buttonContainers.forEach(this::addButton);
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
