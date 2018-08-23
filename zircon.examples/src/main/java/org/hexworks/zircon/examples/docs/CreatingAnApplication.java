package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.AppConfigs;
import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.Application;

public class CreatingAnApplication {

    public static void main(String[] args) {

        Application application = SwingApplications.startApplication(
                AppConfigs.newConfig()
                        .defaultSize(Sizes.create(30, 20))
                        .defaultTileset(CP437TilesetResources.rexPaint16x16())
                        .clipboardAvailable(true)
                        .build());
    }
}
