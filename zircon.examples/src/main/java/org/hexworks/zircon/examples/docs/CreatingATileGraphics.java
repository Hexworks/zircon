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
                .withSize(Sizes.create(10, 10))
                .withStyle(StyleSets.newBuilder()
                        .withBackgroundColor(ANSITileColor.RED)
                        .withForegroundColor(ANSITileColor.MAGENTA)
                        .withModifiers(Modifiers.glow())
                        .build())
                .withTileset(CP437TilesetResources.rexPaint16x16())
                .build()
                .fill(Tiles.newBuilder()
                        .withCharacter('x')
                        .build());
    }
}
