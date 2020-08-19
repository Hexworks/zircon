package org.hexworks.zircon.examples.playground;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.component.ComponentAlignment;
import org.hexworks.zircon.api.component.TextArea;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.api.view.base.BaseView;

public class TestApp {
    private static final TilesetResource defaultTileset = CP437TilesetResources.sb16x16();

    public static void main(String[] args) {
        TileGrid tileGrid = SwingApplications.startTileGrid(
                AppConfig.newBuilder()
                        .withSize(Size.create(60, 34))
                        .withDefaultTileset(defaultTileset)
                        .build());

        TestTerminalScreen testTerminalScreen = new TestTerminalScreen(tileGrid, ColorThemes.linuxMintDark());
        testTerminalScreen.dock();
    }

    public static class TestTerminalScreen extends BaseView {

        private final TextArea commandLine;

        public TestTerminalScreen(TileGrid tileGrid, ColorTheme theme) {
            super(tileGrid, theme);

            Screen screen = getScreen();

            commandLine = Components.textArea()
                    .withSize(tileGrid.getWidth(), 3)
                    .withDecorations(ComponentDecorations.box())
                    .withAlignmentWithin(screen, ComponentAlignment.BOTTOM_CENTER)
                    .build();
            screen.addComponent(commandLine);
        }

        @Override
        public void onDock() {
            super.onDock();
            commandLine.requestFocus();
        }

    }
}
