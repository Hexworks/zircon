package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.DrawSurfaces;
import org.hexworks.zircon.api.Tiles;
import org.hexworks.zircon.api.graphics.TileGraphics;

public class CreatingATileGraphics {

    public static void main(String[] args) {

        TileGraphics graphics = DrawSurfaces.tileGraphicsBuilder()
                .withSize(10, 10)
                .withTileset(CP437TilesetResources.rexPaint16x16())
                .withFiller(Tiles.newBuilder()
                        .withCharacter('x')
                        .build())
                .build();
    }
}
