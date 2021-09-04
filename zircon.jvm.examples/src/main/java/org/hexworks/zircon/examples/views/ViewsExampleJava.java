package org.hexworks.zircon.examples.views;

import org.hexworks.zircon.api.ColorThemes;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.Functions;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.view.base.BaseView;
import org.jetbrains.annotations.NotNull;

import static org.hexworks.zircon.examples.base.Defaults.startTileGrid;

public class ViewsExampleJava {

    public static void main(String[] args) {

        ColorTheme theme = ColorThemes.arc();

        TileGrid tileGrid = startTileGrid();

        class InitialView extends BaseView {

            public final Button dockOther = Components.button()
                    .withText("Dock other")
                    .withPosition(0, 2)
                    .build();

            public InitialView(@NotNull TileGrid tileGrid, @NotNull ColorTheme theme) {
                super(tileGrid, theme);
                getScreen().addComponent(Components.header().withText("Initial view."));
                getScreen().addComponent(dockOther);
            }

            @Override
            public void onDock() {
                System.out.println("Docking Initial View.");
            }

            @Override
            public void onUndock() {
                System.out.println("Undocking Initial View.");
            }
        }

        class OtherView extends BaseView {

            public final Button dockInitial = Components.button()
                    .withText("Dock initial")
                    .withPosition(12, 2)
                    .build();

            public OtherView(@NotNull TileGrid tileGrid, @NotNull ColorTheme theme) {
                super(tileGrid, theme);
                getScreen().addComponent(Components.header().withText("Other view."));
                getScreen().addComponent(dockInitial);
            }

            @Override
            public void onDock() {
                System.out.println("Docking Other View.");
            }

            @Override
            public void onUndock() {
                System.out.println("Undocking Other View.");
            }
        }

        InitialView initial = new InitialView(tileGrid, theme);
        OtherView other = new OtherView(tileGrid, theme);

        initial.dockOther.onActivated(Functions.fromConsumer(event -> other.dock()));

        other.dockInitial.onActivated(Functions.fromConsumer(event -> other.replaceWith(initial)));

        initial.dock();
    }
}
