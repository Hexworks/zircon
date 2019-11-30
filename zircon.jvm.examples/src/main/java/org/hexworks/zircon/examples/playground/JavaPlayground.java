package org.hexworks.zircon.examples.playground;

import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.Tiles;
import org.hexworks.zircon.api.UIEventResponses;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.graphics.StyleSet;
import org.hexworks.zircon.api.tileset.Tileset;
import org.hexworks.zircon.api.uievent.Pass;
import org.hexworks.zircon.api.uievent.UIEventResponse;
import org.hexworks.zircon.internal.tileset.SwingTilesetLoader;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JavaPlayground {

    public static void main(String[] args) throws IOException {

        final UIEventResponse r = UIEventResponse.pass();
        // you create an image and the corresponding graphics
        final BufferedImage image = new BufferedImage(800, 600, BufferedImage.TRANSLUCENT);
        final Graphics2D graphics = image.createGraphics();

        // you load the tileset you want
        SwingTilesetLoader loader = new SwingTilesetLoader();
        final Tileset<Graphics2D> tileset = loader.loadTilesetFrom(CP437TilesetResources.rexPaint20x20());

        // you draw tiles on the image using the graphics
        tileset.drawTile(Tiles.newBuilder()
                .withCharacter('a')
                .withBackgroundColor(ANSITileColor.RED)
                .withForegroundColor(ANSITileColor.WHITE)
                .build(), graphics, Positions.create(0, 0));

        tileset.drawTile(Tiles.newBuilder()
                .withCharacter('b')
                .withBackgroundColor(ANSITileColor.GREEN)
                .withForegroundColor(ANSITileColor.YELLOW)
                .build(), graphics, Positions.create(1, 0));

        // and you write the result into a file
        ImageIO.write(image, "png", new File("saved.png"));

    }

}
