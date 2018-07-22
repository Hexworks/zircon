package org.codetome.zircon.examples;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.SwingTerminalBuilder;
import org.codetome.zircon.api.color.ANSITextColor;
import org.codetome.zircon.api.component.Button;
import org.codetome.zircon.api.component.Panel;
import org.codetome.zircon.api.graphics.Layer;
import org.codetome.zircon.api.interop.*;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.resource.REXPaintResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.api.terminal.Terminal;
import org.codetome.zircon.internal.component.DefaultColorTheme;

import java.io.InputStream;

public class Playground {

    public static void main(String[] args) {
        final InputStream TITLE = Playground.class.getResourceAsStream("/tester.xp");
        REXPaintResource background = REXPaintResource.loadREXFile(TITLE);
        Layer backgroundLayer = background.toLayerList().get(0);

        final Terminal terminal = SwingTerminalBuilder.newBuilder()
                .initialTerminalSize(Sizes.create(70, 43))
                .font(CP437TilesetResource.REX_PAINT_16X16.toFont())
                .build();
        final Screen mainScreen = Screens.createScreenFor(terminal);

        mainScreen.draw(backgroundLayer, Positions.topLeftCorner());

        DefaultColorTheme theme = ColorThemes.newBuilder()
                .accentColor(ANSITextColor.YELLOW)
                .brightBackgroundColor(TextColors.fromRGB(80, 80, 80, 225))
                .darkBackgroundColor(TextColors.fromRGB(50, 50, 50, 0))
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
