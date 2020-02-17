package org.hexworks.zircon.examples.components;

import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.ColorThemes;
import org.hexworks.zircon.api.Functions;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.api.uievent.ComponentEventType;
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer;

import static org.hexworks.zircon.api.ComponentDecorations.*;
import static org.hexworks.zircon.api.Components.*;
import static org.hexworks.zircon.api.Functions.*;
import static org.hexworks.zircon.api.graphics.BoxType.DOUBLE;
import static org.hexworks.zircon.api.graphics.BoxType.SINGLE;
import static org.hexworks.zircon.api.uievent.ComponentEventType.*;

public class ButtonsExampleJava {

    private static ColorTheme THEME = ColorThemes.solarizedLightOrange();
    private static TilesetResource TILESET = CP437TilesetResources.rexPaint20x20();
    private static Size SIZE = Size.create(60, 40);

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(TILESET)
                .withSize(SIZE)
                .build());

        Screen screen = Screen.create(tileGrid);

        screen.addComponent(header().withText("Buttons Example").withPosition(2, 0));

        VBox leftBox = vbox()
                .withSize(SIZE.getWidth() / 2 - 4, SIZE.getHeight() - 6)
                .withComponentRenderer(new NoOpComponentRenderer<>())
                .withSpacing(1)
                .withPosition(2, 3)
                .build();

        VBox rightBox = vbox()
                .withDecorations(box(SINGLE, "Buttons on panel"), shadow())
                .withSpacing(1)
                .withSize(SIZE.getWidth() / 2 - 2, SIZE.getHeight() - 3)
                .withPosition(SIZE.getWidth() / 2 + 1, 2)
                .build();

        screen.addComponent(leftBox);
        screen.addComponent(rightBox);

        addButtonsTo(leftBox);
        addButtonsTo(rightBox);

        screen.display();
        screen.setTheme(THEME);
    }

    private static void addButtonsTo(VBox box) {

        Button invisibleButton = button()
                .withText("Make me invisible")
                .withDecorations(side())
                .build();
        invisibleButton.processComponentEvents(ACTIVATED, fromConsumer((event) -> {
            invisibleButton.setHidden(true);
        }));

        Button disabledButton = button()
                .withText("Disabled Button")
                .build();

        box.addComponents(
                button()
                        .withText("Button")
                        .withDecorations(side())
                        .build(),
                button()
                        .withText("Boxed Button")
                        .withDecorations(box())
                        .build(),
                button()
                        .withText("Too long name for button")
                        .withDecorations(box(), shadow())
                        .withSize(Size.create(10, 4))
                        .build(),
                button()
                        .withText("Half block button")
                        .withDecorations(halfBlock(), shadow())
                        .build(),
                invisibleButton, disabledButton);

        disabledButton.setDisabled(true);

        System.out.println();
    }

}
