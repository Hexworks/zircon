package org.codetome.zircon.test;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.TextCharacter;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.builder.TextCharacterBuilder;
import org.codetome.zircon.api.color.ANSITextColor;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.terminal.Terminal;

public class Playground {

    public static void main(String[] args) {
        final Terminal terminal = TerminalBuilder.newBuilder()
                .font(CP437TilesetResource.REX_PAINT_20X20.toFont())
                .initialTerminalSize(Size.of(10, 10))
                .build();

        TextCharacter tc = TextCharacterBuilder.newBuilder()
                .character('~')
                .foregroundColor(ANSITextColor.WHITE)
                .backgroundColor(ANSITextColor.GREEN)
                .build();

        terminal.setCharacterAt(Position.OFFSET_1x1, tc);
        terminal.flush();
    }
}
