package org.codetome.zircon.test;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.builder.BoxBuilder;
import org.codetome.zircon.api.builder.StyleSetBuilder;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.builder.TextImageBuilder;
import org.codetome.zircon.api.color.ANSITextColor;
import org.codetome.zircon.api.color.TextColorFactory;
import org.codetome.zircon.api.graphics.TextImage;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.resource.ColorThemeResource;
import org.codetome.zircon.api.terminal.Terminal;
import org.codetome.zircon.internal.graphics.BoxType;

import java.util.ArrayList;
import java.util.List;

public class Playground {

    public static void main(String[] args) {
        final Terminal terminal = TerminalBuilder.newBuilder()
                .font(CP437TilesetResource.REX_PAINT_20X20.toFont())
                .initialTerminalSize(Size.of(10, 5))
                .build();

        final TextImage img = TextImageBuilder.newBuilder()
                .size(Size.of(10, 5))
                .build(); // we create a new image to draw onto the terminal

        img.setForegroundColor(ANSITextColor.WHITE);
        img.setBackgroundColor(TextColorFactory.TRANSPARENT); // `putText` will use these

        BoxBuilder.newBuilder()
                .boxType(BoxType.DOUBLE)
                .size(Size.of(10, 5))
                .style(StyleSetBuilder.newBuilder()
                        .foregroundColor(ANSITextColor.CYAN)
                        .backgroundColor(ANSITextColor.BLUE)
                        .build())
                .build()
                .drawOnto(img, Position.DEFAULT_POSITION); // we create a box and draw it onto the image

        final List<String> logElements = new ArrayList<>();
        logElements.add("foo");
        logElements.add("bar"); // our log entries

        for(int i = 0; i < logElements.size(); i++) {
            img.putText(logElements.get(i), Position.OFFSET_1x1.withRelativeRow(i)); // we have to offset because of the box
        }

        terminal.draw(img, Position.DEFAULT_POSITION); // you have to draw each time the image changes

        terminal.flush();
    }
}
