package org.codetome.zircon.examples.interactive;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.Symbols;
import org.codetome.zircon.api.TextCharacter;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.builder.TextCharacterBuilder;
import org.codetome.zircon.api.builder.TextImageBuilder;
import org.codetome.zircon.api.color.ANSITextColor;
import org.codetome.zircon.api.color.TextColor;
import org.codetome.zircon.api.color.TextColorFactory;
import org.codetome.zircon.api.component.ColorTheme;
import org.codetome.zircon.api.component.Panel;
import org.codetome.zircon.api.component.RadioButtonGroup;
import org.codetome.zircon.api.component.builder.PanelBuilder;
import org.codetome.zircon.api.component.builder.RadioButtonGroupBuilder;
import org.codetome.zircon.api.font.Font;
import org.codetome.zircon.api.graphics.TextImage;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.resource.ColorThemeResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.api.terminal.Terminal;
import org.codetome.zircon.internal.graphics.BoxType;
import org.junit.Test;

public class ShootingExample {

    private static final int TERMINAL_WIDTH = 61;
    private static final int TERMINAL_HEIGHT = 24;
    private static final Size SIZE = Size.of(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final int WEAPONS_PANEL_WIDTH = 21;
    private static final Font FONT = CP437TilesetResource.ROGUE_YUN_16X16.toFont();
    private static final ColorTheme THEME = ColorThemeResource.SOLARIZED_DARK_GREEN.getTheme();
    private static final PanelBuilder PANEL_TEMPLATE = PanelBuilder.newBuilder()
            .boxType(BoxType.TOP_BOTTOM_DOUBLE)
            .wrapWithBox();
    private static final TextColor LINE_COLOR = TextColorFactory.fromRGB(20, 120, 80, 125);
    private static final TextCharacter LINE_CHAR = TextCharacterBuilder.newBuilder()
            .foregroundColor(LINE_COLOR)
            .character(Symbols.BLOCK_DENSE)
            .build();

    enum Weapons {
        ARROW,
        RIFLE,
        MACHINE_GUN,
        ROCKET_LAUNCHER
    }

    @Test
    public void checkSetup() {
        main(new String[]{"test"});
    }

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Terminal terminal = TerminalBuilder.newBuilder()
                .font(FONT)
                .initialTerminalSize(SIZE)
                .buildTerminal(args.length > 0);
        final Screen screen = TerminalBuilder.createScreenFor(terminal);

        final Panel controlsPanel = PANEL_TEMPLATE.createCopy()
                .size(Size.of(WEAPONS_PANEL_WIDTH, TERMINAL_HEIGHT))
                .title("Weapons")
                .build();
        final RadioButtonGroup radioButtonGroup = RadioButtonGroupBuilder.newBuilder()
                .size(Size.of(controlsPanel.getBoundableSize().getColumns() - 2, 4))
                .build();
        for (Weapons w : Weapons.values()) {
            radioButtonGroup.addOption(w.name(), w.name());
        }
        controlsPanel.addComponent(radioButtonGroup);
        screen.addComponent(controlsPanel);

        final Panel gamePanel = PANEL_TEMPLATE.createCopy()
                .size(Size.of(TERMINAL_WIDTH - WEAPONS_PANEL_WIDTH, TERMINAL_HEIGHT))
                .position(Position.DEFAULT_POSITION.relativeToRightOf(controlsPanel))
                .title("Shooting example")
                .build();

        final TextImage gameField = TextImageBuilder.newBuilder()
                .size(gamePanel.getBoundableSize().minus(Size.of(2, 2)))
                .filler(TextCharacterBuilder.newBuilder()
                        .backgroundColor(ANSITextColor.BLACK)
                        .build())
                .build();

//        final GameComponent gameComponent = new GameComponent(
//                new TextImageGameArea(gameField),
//                gameField.getBoundableSize(),
//                CP437TilesetResource.PHOEBUS_16X16.toFont(),
//                Position.DEFAULT_POSITION,
//                ComponentStylesBuilder.DEFAULT);
//        screen.addComponent(gamePanel);
//        gamePanel.addComponent(gameComponent);

//        final TextCharacter player = TextCharacterBuilder.newBuilder()
//                .character('@')
//                .build();
//        final Position playerPos = Position.of(gameField.getBoundableSize().getColumns() / 2, 0);
//        final Position absoluteShootingPos = playerPos
//                .plus(gameComponent.getPosition())
//                .withRelativeRow(1);
//        gameField.setCharacterAt(playerPos, player);
//
//        final AtomicReference<Layer> lastLayer = new AtomicReference<>();
//
//        gameComponent.onMouseMoved(mouseAction -> {
//            final TextImage line = LineFactory
//                    .buildLine(absoluteShootingPos, mouseAction.getPosition())
//                    .toTextImage(LINE_CHAR);
//            if(lastLayer.get() != null) {
//                screen.removeLayer(lastLayer.get());
//            }
//            Position lineOffset = absoluteShootingPos;
//            int diff = absoluteShootingPos.getColumn() - mouseAction.getPosition().getColumn();
//            if(mouseAction.getPosition().getColumn() < absoluteShootingPos.getColumn()) {
//                lineOffset = absoluteShootingPos.withRelativeColumn(-diff);
//            }
//            lastLayer.set(LayerBuilder.newBuilder()
//                    .textImage(line)
//                    .offset(lineOffset)
//                    .build());
//            screen.pushLayer(lastLayer.get());
//            screen.refresh();
//        });
//        gameComponent.onMouseReleased(mouseAction -> {
//            System.out.println("release at pos: " + mouseAction.getPosition());
//        });

        screen.applyColorTheme(THEME);
        screen.display();
    }

}
