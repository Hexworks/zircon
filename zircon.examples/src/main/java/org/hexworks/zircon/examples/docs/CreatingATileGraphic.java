package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.Modifiers;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.StyleSets;
import org.hexworks.zircon.api.TileGraphics;
import org.hexworks.zircon.api.Tiles;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.graphics.TileGraphic;

public class CreatingATileGraphic {

    public static void main(String[] args) {

        TileGraphic graphic = TileGraphics.newBuilder()
                .size(Sizes.create(10, 10))
                .style(StyleSets.newBuilder()
                        .backgroundColor(ANSITileColor.RED)
                        .foregroundColor(ANSITileColor.MAGENTA)
                        .modifiers(Modifiers.glow())
                        .build())
                .filler(Tiles.newBuilder()
                        .character('x')
                        .build())
                .tileset(CP437TilesetResources.rexPaint16x16())
                .build();
    }
}
