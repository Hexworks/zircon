package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.ColorThemes;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.LibgdxApplications;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.screen.Screen;

import java.util.Random;

import static org.hexworks.zircon.api.ComponentDecorations.box;

public class WorkingWithColorThemes {

    public static void main(String[] args) {

        final TileGrid tileGrid = LibgdxApplications.startTileGrid(
                AppConfig.newBuilder()
                        .withSize(12, 10)
                        .withDefaultTileset(CP437TilesetResources.rogueYun16x16())
                        .build());

        final Screen screen = Screen.create(tileGrid);

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
                .withAccentColor(TileColor.fromString("#ff0000"))
                .withPrimaryForegroundColor(TileColor.fromString("#ffaaff"))
                .withSecondaryForegroundColor(TileColor.fromString("#dd88dd"))
                .withPrimaryBackgroundColor(TileColor.fromString("#555555"))
                .withSecondaryBackgroundColor(TileColor.fromString("#222222"))
                .build();

        ColorTheme builtIn = ColorThemes.adriftInDreams();

        screen.setTheme(new Random().nextInt(2) > 0 ? custom : builtIn);

        screen.display();
    }
}
