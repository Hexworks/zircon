package org.hexworks.zircon.examples.components;

import org.hexworks.zircon.api.component.CheckBox;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.uievent.ComponentEventType;
import org.hexworks.zircon.examples.components.impl.TwoColumnComponentExample;
import org.jetbrains.annotations.NotNull;

import static org.hexworks.zircon.api.ComponentDecorations.*;
import static org.hexworks.zircon.api.Components.checkBox;
import static org.hexworks.zircon.api.Functions.fromConsumer;

public class CheckBoxesExampleJava extends TwoColumnComponentExample {

    public static void main(String[] args) {
        new CheckBoxesExampleJava(createGrid(), createTheme()).show("Check Boxes Example");
    }

    public CheckBoxesExampleJava(@NotNull TileGrid tileGrid, @NotNull ColorTheme theme) {
        super(tileGrid, theme);
    }

    @Override
    public void build(VBox box) {
        CheckBox invisible = checkBox()
                .withText("Make me invisible")
                .withDecorations(side())
                .build();
        invisible.processComponentEvents(ComponentEventType.ACTIVATED, fromConsumer((event) -> {
            invisible.setHidden(true);
        }));

        CheckBox disabled = checkBox()
                .withText("Disabled Button")
                .build();

        box.addComponents(
                checkBox()
                        .withText("Default")
                        .build(),
                checkBox()
                        .withText("Boxed")
                        .withDecorations(box())
                        .build(),
                checkBox()
                        .withText("Too long name")
                        .withDecorations(box(), shadow())
                        .withSize(16, 4)
                        .build(),
                checkBox()
                        .withText("Half block")
                        .withDecorations(halfBlock(), shadow())
                        .build(),
                invisible, disabled);

        disabled.setDisabled(true);
    }

}
