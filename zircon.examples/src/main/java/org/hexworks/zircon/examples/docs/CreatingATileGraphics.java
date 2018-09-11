package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.Modifiers;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.StyleSets;
import org.hexworks.zircon.api.DrawSurfaces;
import org.hexworks.zircon.api.Tiles;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.graphics.TileGraphics;

public class CreatingATileGraphics {

    public static void main(String[] args) {

        TileGraphics graphics = DrawSurfaces.tileGraphicsBuilder()
                .size(Sizes.create(10, 10))
                .style(StyleSets.newBuilder()
                        .backgroundColor(ANSITileColor.RED)
                        .foregroundColor(ANSITileColor.MAGENTA)
                        .modifiers(Modifiers.glow())
                        .build())
                .tileset(CP437TilesetResources.rexPaint16x16())
                .build()
                .fill(Tiles.newBuilder()
                        .character('x')
                        .build());
    }
}
