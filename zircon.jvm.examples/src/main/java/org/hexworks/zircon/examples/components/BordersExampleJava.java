package org.hexworks.zircon.examples.components;

import kotlin.Unit;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.data.CharacterTile;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.modifier.Border;
import org.hexworks.zircon.api.modifier.BorderPosition;
import org.hexworks.zircon.api.modifier.BorderType;
import org.hexworks.zircon.examples.base.OneColumnComponentExampleJava;
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer;

import java.util.Arrays;
import java.util.List;

public class BordersExampleJava extends OneColumnComponentExampleJava {

    public static void main(String[] args) {
        new BordersExampleJava().show("Borders Example");
    }

    @Override
    public void build(VBox box) {
        final List<CharacterTile> tiles = Arrays.asList(
                Tile.defaultTile()
                        .withBackgroundColor(ANSITileColor.GREEN)
                        .withCharacter('a')
                        .withModifiers(Border.newBuilder()
                                .withBorderPositions(BorderPosition.TOP)
                                .withBorderType(BorderType.DOTTED)
                                .withBorderColor(TileColor.fromString("#ffaadd"))
                                .withBorderWidth(5)
                                .build()),
                Tile.defaultTile()
                        .withBackgroundColor(ANSITileColor.BLUE)
                        .withCharacter('b')
                        .withModifiers(Border.newBuilder()
                                .withBorderPositions(BorderPosition.RIGHT)
                                .withBorderType(BorderType.SOLID)
                                .withBorderColor(TileColor.fromString("#caacaa"))
                                .withBorderWidth(10)
                                .build()),
                Tile.defaultTile()
                        .withBackgroundColor(ANSITileColor.GRAY)
                        .withCharacter('c')
                        .withModifiers(Border.newBuilder()
                                .withBorderPositions(BorderPosition.BOTTOM)
                                .withBorderType(BorderType.DASHED)
                                .withBorderColor(TileColor.fromString("#caacaa"))
                                .withBorderWidth(15)
                                .build()),
                Tile.defaultTile()
                        .withBackgroundColor(ANSITileColor.RED)
                        .withCharacter('d')
                        .withModifiers(Border.newBuilder()
                                .withBorderPositions(BorderPosition.LEFT)
                                .withBorderType(BorderType.SOLID)
                                .withBorderColor(TileColor.create(80, 80, 80, 80))
                                .withBorderWidth(20)
                                .build()),
                Tile.defaultTile()
                        .withBackgroundColor(ANSITileColor.RED)
                        .withCharacter('e')
                        .withModifiers(
                                Border.newBuilder()
                                        .withBorderPositions(BorderPosition.TOP)
                                        .withBorderColor(TileColor.create(0, 0, 0, 20))
                                        .withBorderWidth(30)
                                        .build(),
                                Border.newBuilder()
                                        .withBorderPositions(BorderPosition.TOP)
                                        .withBorderColor(TileColor.create(0, 0, 0, 20))
                                        .withBorderWidth(20)
                                        .build(),
                                Border.newBuilder()
                                        .withBorderPositions(BorderPosition.TOP)
                                        .withBorderColor(TileColor.create(0, 0, 0, 20))
                                        .withBorderWidth(10)
                                        .build())
        );

        Panel panel = Components.panel()
                .withRendererFunction((graphics, context) -> {
                    for (int i = 0; i < tiles.size(); i++) {
                        graphics.draw(tiles.get(i), Position.create(i, 0));
                    }
                    return Unit.INSTANCE;
                })
                .build();

        box.addComponent(panel);
    }

}
