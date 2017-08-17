package org.codetome.zircon.examples;

import org.codetome.zircon.Size;
import org.codetome.zircon.api.PhysicalFontResource;
import org.codetome.zircon.api.TerminalBuilder;
import org.codetome.zircon.api.TextColorFactory;
import org.codetome.zircon.color.TextColor;
import org.codetome.zircon.screen.Screen;

public class PanelDrawingExample {

    private static final int TERMINAL_WIDTH = 50;
    private static final int TERMINAL_HEIGHT = 24;
    private static final TextColor BACKGROUND_COLOR = TextColorFactory.fromString("#223344");

    public static void main(String[] args) {
        final Screen screen = TerminalBuilder.newBuilder()
                .font(PhysicalFontResource.SOURCE_CODE_PRO.asPhysicalFont())
                .initialTerminalSize(Size.of(TERMINAL_WIDTH, TERMINAL_HEIGHT))
                .buildScreen();

//        drawBackground(screen);
//
//        screen.newTextGraphics().drawImage(
//                Position.TOP_LEFT_CORNER,
//                createPanel(20, 10, "Foo and Bar"));

//        screen.newTextGraphics().drawImage(
//                Position.of(24, 2),
//                createBackgroundForPanel(createPanel(20, 5, "Wom and Bat")));

        screen.display();
    }


}
