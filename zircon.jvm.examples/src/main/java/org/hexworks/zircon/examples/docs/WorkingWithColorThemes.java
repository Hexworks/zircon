package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.AppConfigs;
import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.ColorThemes;
import org.hexworks.zircon.api.ComponentDecorations;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.LibgdxApplications;
import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.Screens;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.TileColors;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.screen.Screen;

import java.util.Random;

public class WorkingWithColorThemes {

    public static void main(String[] args) {

        final TileGrid tileGrid = LibgdxApplications.startTileGrid(
                AppConfigs.newConfig()
                        .withSize(Sizes.create(12, 10))
                        .withDefaultTileset(CP437TilesetResources.rogueYun16x16())
                        .build());

        final Screen screen = Screens.createScreenFor(tileGrid);

        screen.addComponent(Components.label()
                .withText("Hello")
                .withPosition(Positions.offset1x1())
                .build());

        screen.addComponent(Components.panel()
                .withDecorations(ComponentDecorations.box(BoxType.SINGLE, "Panel"))
                .withPosition(Positions.create(1, 3))
                .withSize(Sizes.create(10, 5))
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
