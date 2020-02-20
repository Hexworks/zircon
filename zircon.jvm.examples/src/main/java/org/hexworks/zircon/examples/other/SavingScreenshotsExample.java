package org.hexworks.zircon.examples.other;

import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.api.tileset.Tileset;
import org.hexworks.zircon.examples.base.Defaults;
import org.hexworks.zircon.internal.tileset.SwingTilesetLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SavingScreenshotsExample {

    public static void main(String[] args) throws IOException {

        // you create an image and the corresponding graphics
        final BufferedImage image = new BufferedImage(800, 600, BufferedImage.TRANSLUCENT);
        final Graphics2D graphics = image.createGraphics();

        // you load the tileset you want
        SwingTilesetLoader loader = new SwingTilesetLoader();
        final Tileset<Graphics2D> tileset = loader.loadTilesetFrom(CP437TilesetResources.rexPaint20x20());

        // you draw tiles on the image using the graphics
        tileset.drawTile(Tile.newBuilder()
                .withCharacter('a')
                .withBackgroundColor(ANSITileColor.RED)
                .withForegroundColor(ANSITileColor.WHITE)
                .build(), graphics, Position.create(0, 0));

        tileset.drawTile(Tile.newBuilder()
                .withCharacter('b')
                .withBackgroundColor(ANSITileColor.GREEN)
                .withForegroundColor(ANSITileColor.YELLOW)
                .build(), graphics, Position.create(1, 0));

        // and you write the result into a file
        ImageIO.write(image, "png", new File("saved.png"));

    }
}
