package org.codetome.zircon.examples;

import org.codetome.zircon.api.SwingTerminalBuilder;
import org.codetome.zircon.api.builder.ScreenBuilder;
import org.codetome.zircon.api.color.ANSITextColor;
import org.codetome.zircon.api.component.Button;
import org.codetome.zircon.api.component.ColorTheme;
import org.codetome.zircon.api.component.Panel;
import org.codetome.zircon.api.component.builder.ButtonBuilder;
import org.codetome.zircon.api.component.builder.ColorThemeBuilder;
import org.codetome.zircon.api.component.builder.PanelBuilder;
import org.codetome.zircon.api.graphics.Layer;
import org.codetome.zircon.api.interop.ColorThemes;
import org.codetome.zircon.api.interop.Positions;
import org.codetome.zircon.api.interop.Sizes;
import org.codetome.zircon.api.interop.TextColors;
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
        final Screen mainScreen = ScreenBuilder.createScreenFor(terminal);

        mainScreen.draw(backgroundLayer, Positions.TOP_LEFT_CORNER);

        DefaultColorTheme theme = ColorThemes.newBuilder()
                .accentColor(ANSITextColor.YELLOW)
                .brightBackgroundColor(TextColors.fromRGB(80, 80, 80, 225))
                .darkBackgroundColor(TextColors.fromRGB(50, 50, 50, 0))
                .brightForegroundColor(ANSITextColor.GREEN)
                .darkForegroundColor(ANSITextColor.BLUE)
                .build();

        Panel panel = PanelBuilder.newBuilder()
                .wrapWithShadow()
                .wrapWithBox()
                .title("Main menu")
                .size(Sizes.create(30, 10))
                .position(Positions.create(3, 2))
                .build();

        Button newGame = ButtonBuilder.newBuilder()
                .text("New Game")
                .position(Positions.OFFSET_1x1)
                .build();

        Button options = ButtonBuilder.newBuilder()
                .text("Options")
                .position(Positions.create(1, 2))
                .build();

        Button exit = ButtonBuilder.newBuilder()
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
