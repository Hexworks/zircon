package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.screen.Screen;

import java.util.Random;

import static org.hexworks.zircon.api.ComponentDecorations.*;

public class WorkingWithColorThemes {

    public static void main(String[] args) {

        final TileGrid tileGrid = LibgdxApplications.startTileGrid(
                AppConfigs.newConfig()
                        .withSize(12, 10)
                        .withDefaultTileset(CP437TilesetResources.rogueYun16x16())
                        .build());

        final Screen screen = Screens.createScreenFor(tileGrid);

        screen.addComponent(Components.label()
                .withText("Hello")
                .withPosition(1, 1)
                .build());

        screen.addComponent(Components.panel()
                .withDecorations(box(BoxType.SINGLE, "Panel"))
                .withPosition(1, 3)
                .withSize(10, 5)
                .build());

        ColorTheme custom = ColorThemes.newBuilder()
                .withAccentColor(TileColors.fromString("#ff0000"))
                .withPrimaryForegroundColor(TileColors.fromString("#ffaaff"))
                .withSecondaryForegroundColor(TileColors.fromString("#dd88dd"))
                .withPrimaryBackgroundColor(TileColors.fromString("#555555"))
                .withSecondaryBackgroundColor(TileColors.fromString("#222222"))
                .build();

        ColorTheme builtIn = ColorThemes.adriftInDreams();

        screen.applyColorTheme(new Random().nextInt(2) > 0 ? custom : builtIn);

        screen.display();
    }
}
