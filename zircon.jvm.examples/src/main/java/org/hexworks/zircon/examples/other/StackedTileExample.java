package org.hexworks.zircon.examples.other;

import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.GraphicalTilesetResources;
import org.hexworks.zircon.api.Modifiers;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.data.*;
import org.hexworks.zircon.api.graphics.Symbols;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.jetbrains.annotations.NotNull;

import static org.hexworks.zircon.api.color.ANSITileColor.*;

public class StackedTileExample {

    private static final int GRID_WIDTH = 60;
    private static final int GRID_HEIGHT = 30;
    private static final Size SIZE = Size.create(GRID_WIDTH, GRID_HEIGHT);

    public static void main(String[] args) {
        TileGrid app = SwingApplications.startApplication(AppConfig.newBuilder()
                .withDefaultTileset(CP437TilesetResources.bisasam16x16())
                .withSize(SIZE)
                .build())
                .getTileGrid();


        Position p = Position.create(4, 5);
        simpleStack(app, p);
        createVsPush(app, movePos(p, 2));
        withBaseTile(app, movePos(p, 4));
        graphicalStack(app, movePos(p, 6));
        letsGoApeshit(app, movePos(p, 8));
    }

    private static void simpleStack(TileGrid app, Position gridPosition) {
        app.draw(stackXAndPlus(), gridPosition);
    }

    private static void createVsPush(TileGrid app, Position gridPosition) {
        CharacterTile circumflex = charTile(Symbols.CIRCUMFLEX, RED, TileColor.transparent());
        StackedTile stackedTile1 = stackXAndPlus();
        StackedTile stackedTile2Created = StackedTile.create(
                stackedTile1,
                circumflex
        );

        StackedTile stackedTile2Pushed = stackedTile1
                .withPushedTile(circumflex);
        app.draw(stackedTile2Created, gridPosition);
        app.draw(stackedTile2Pushed, gridPosition.withRelativeY(1));
    }

    private static void withBaseTile(TileGrid app, Position gridPosition) {
        StackedTile switchedBaseTile = stackXAndPlus().withBaseTile(
                charTile('O', BRIGHT_WHITE, GREEN)
        );
        app.draw(switchedBaseTile, gridPosition);
    }

    private static void graphicalStack(TileGrid app, Position gridPosition) {
        GraphicalTile werewolf = graphicalTile("Werewolf");
        GraphicalTile giantAnt = graphicalTile("Fire ant");
        StackedTile graphicalStack = StackedTile.create(
                werewolf,
                giantAnt
        );
        drawAdditionAt(gridPosition, app, graphicalStack, werewolf, giantAnt);
    }

    private static void letsGoApeshit(TileGrid app, Position gridPosition) {
        StackedTile stackGraphical = StackedTile.create(
                graphicalTile("Gremlin"),
                graphicalTile("Fire ant")
        );

        StackedTile stackCharacter = stackXAndPlus();

        Tile border = charTile(' ', CYAN, TileColor.transparent())
                .withAddedModifiers(Modifiers.border());

        StackedTile fullStack = StackedTile.create(
                stackCharacter,
                stackGraphical,
                border
        );

        drawAdditionAt(gridPosition, app, fullStack, stackGraphical, stackCharacter, border);
    }

    private static void drawAdditionAt(Position gridPosition, TileGrid app, Tile result, Tile... tiles) {
        int y = 0;
        for (Tile tile : tiles) {
            if (y > 0) {
                app.draw(charTile('+', WHITE, BLACK), gridPosition.withRelativeY(y - 1));
            }
            app.draw(tile, gridPosition.withRelativeY(y));
            y += 2;
        }
        app.draw(charTile('=', WHITE, BLACK), gridPosition.withRelativeY(y - 1));
        app.draw(result, gridPosition.withRelativeY(y));
    }

    @NotNull
    private static GraphicalTile graphicalTile(String tileName) {
        TilesetResource tileset = GraphicalTilesetResources.nethack16x16();
        return Tile.newBuilder()
                .withName(tileName)
                .withTileset(tileset)
                .buildGraphicalTile();
    }

    @NotNull
    private static StackedTile stackXAndPlus() {
        return StackedTile.create(
                charTile('x', BRIGHT_YELLOW, BLUE),
                charTile('+', BRIGHT_MAGENTA, TileColor.transparent())
        );
    }

    @NotNull
    private static Position movePos(Position position, int offset) {
        return position.withRelativeX(offset);
    }

    @NotNull
    private static CharacterTile charTile(char character, TileColor foreground, TileColor background) {
        return Tile.newBuilder()
                .withCharacter(character)
                .withForegroundColor(foreground)
                .withBackgroundColor(background)
                .buildCharacterTile();
    }
}
