package org.hexworks.zircon.examples.game;

import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.builder.application.AppConfigBuilder;

public class IconExample {

    public static void main(String[] args) {
        SwingApplications.startTileGrid(
            new AppConfigBuilder()
                .withIcon("https://avatars0.githubusercontent.com/u/16441716?s=60&v=4")
                .build());
    }
}
