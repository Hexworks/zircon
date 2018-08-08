package org.codetome.zircon.examples;

import org.codetome.zircon.api.builder.grid.TileGridBuilder;
import org.codetome.zircon.api.color.ANSITextColor;
import org.codetome.zircon.api.component.Button;
import org.codetome.zircon.api.component.Panel;
import org.codetome.zircon.api.data.CharacterTile;
import org.codetome.zircon.api.graphics.Layer;
import org.codetome.zircon.api.grid.TileGrid;
import org.codetome.zircon.api.interop.ColorThemes;
import org.codetome.zircon.api.interop.Components;
import org.codetome.zircon.api.interop.Positions;
import org.codetome.zircon.api.interop.Screens;
import org.codetome.zircon.api.interop.Sizes;
import org.codetome.zircon.api.interop.TextColors;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.resource.REXPaintResource;
import org.codetome.zircon.api.resource.TilesetResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.internal.component.impl.DefaultColorTheme;

import java.io.InputStream;

public class Playground {

    public static void main(String[] args) {
        final TilesetResource<CharacterTile> tileset = CP437TilesetResource.CLA_18X18;
        final InputStream TITLE = Playground.class.getResourceAsStream("/rex_files/keyfinder.xp");
        REXPaintResource background = REXPaintResource.loadREXFile(TITLE);
        Layer backgroundLayer = background.toLayerList(tileset).get(0);

        final TileGrid tileGrid = TileGridBuilder.Companion.newBuilder()
                .size(Sizes.create(70, 43))
                .tileset(tileset)
                .build();
        final Screen mainScreen = Screens.createScreenFor(tileGrid);

        mainScreen.draw(backgroundLayer, Positions.topLeftCorner());

        DefaultColorTheme theme = ColorThemes.newBuilder()
                .accentColor(ANSITextColor.YELLOW)
                .brightBackgroundColor(TextColors.create(80, 80, 80, 225))
                .darkBackgroundColor(TextColors.create(50, 50, 50, 0))
                .brightForegroundColor(ANSITextColor.GREEN)
                .darkForegroundColor(ANSITextColor.BLUE)
                .build();

        Panel panel = Components.newPanelBuilder()
                .wrapWithShadow()
                .wrapWithBox()
                .title("Main menu")
                .size(Sizes.create(30, 10))
                .position(Positions.create(3, 2))
                .build();

        Button newGame = Components.newButtonBuilder()
                .text("New Game")
                .position(Positions.offset1x1())
                .build();

        Button options = Components.newButtonBuilder()
                .text("Options")
                .position(Positions.create(1, 2))
                .build();

        Button exit = Components.newButtonBuilder()
                .text("Exit")
                .position(Positions.create(1, 4))
                .build();

        panel.addComponent(newGame);
        panel.addComponent(options);
        panel.addComponent(exit);
        mainScreen.addComponent(panel);

        mainScreen.applyColorTheme(theme);
        mainScreen.display();
    }

}
