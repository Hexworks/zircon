package org.hexworks.zircon.examples.game;

import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.builder.application.AppConfigBuilder;

import java.awt.*;

public class BorderlessExample {

    public static void main(String[] args) {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        SwingApplications.startTileGrid(
                new AppConfigBuilder()
                        .withDefaultTileset(CP437TilesetResources.rogueYun16x16())
                        .withSize(screenSize.width / 16, screenSize.height / 16)
                        .withBorderless()
                        .withFullScreen(true)
                        .build());
    }
}
