package org.codetome.zircon.test;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.component.Button;
import org.codetome.zircon.api.component.CheckBox;
import org.codetome.zircon.api.component.Header;
import org.codetome.zircon.api.component.Panel;
import org.codetome.zircon.api.component.builder.ButtonBuilder;
import org.codetome.zircon.api.component.builder.CheckBoxBuilder;
import org.codetome.zircon.api.component.builder.HeaderBuilder;
import org.codetome.zircon.api.component.builder.PanelBuilder;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.resource.ColorThemeResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.api.terminal.Terminal;

public class Playground {

    private static final String TEXT = "Hello Zircon!";

    public static void main(String[] args) {
        final Terminal terminal = TerminalBuilder.newBuilder()
                .initialTerminalSize(Size.of(34, 18))
                .font(CP437TilesetResource.WANDERLUST_16X16.toFont())
                .build();
        final Screen screen = TerminalBuilder.createScreenFor(terminal);

        // We create a Panel which will hold our components
        // Note that you can add components to the screen without a panel as well
        Panel panel = PanelBuilder.newBuilder()
                .wrapInBox() // panels can be wrapped in a box
                .title("Panel") // if a panel is wrapped in a box a title can be displayed
                .addShadow() // shadow can be added
                .size(Size.of(32, 16)) // the size must be smaller than the parent's size
                .position(Position.OFFSET_1x1) // position is always relative to the parent
                .build();

        final Header header = HeaderBuilder.newBuilder()
                // this will be 1x1 left and down from the top left
                // corner of the panel
                .position(Position.OFFSET_1x1)
                .text("Header")
                .build();

        final CheckBox checkBox = CheckBoxBuilder.newBuilder()
                .text("Check me!")
                .position(Position.of(0, 1)
                        // the position class has some convenience methods
                        // for you to specify your component's position as
                        // relative to another one
                        .relativeToBottomOf(header))
                .build();

        final Button left = ButtonBuilder.newBuilder()
                .position(Position.of(0, 1) // this means 1 row below the check box
                        .relativeToBottomOf(checkBox))
                .text("Left")
                .build();

        final Button right = ButtonBuilder.newBuilder()
                .position(Position.of(1, 0) // 1 column right relative to the left BUTTON
                        .relativeToRightOf(left))
                .text("Right")
                .build();

        panel.addComponent(header);
        panel.addComponent(checkBox);
        panel.addComponent(left);
        panel.addComponent(right);

        screen.addComponent(panel);

        // we can apply color themes to a screen
        screen.applyColorTheme(ColorThemeResource.TECH_LIGHT.getTheme());

        // this is how you can define interactions with a component
        left.onMouseReleased((mouseAction -> {
            screen.applyColorTheme(ColorThemeResource.ADRIFT_IN_DREAMS.getTheme());
        }));

        right.onMouseReleased((mouseAction -> {
            screen.applyColorTheme(ColorThemeResource.SOLARIZED_DARK_ORANGE.getTheme());
        }));

        // in order to see the changes you need to display your screen.
        screen.display();
    }
}
