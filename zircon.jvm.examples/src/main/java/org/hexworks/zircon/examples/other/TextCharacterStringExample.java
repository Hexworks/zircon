package org.hexworks.zircon.examples.other;

import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.CharacterTileStrings;
import org.hexworks.zircon.api.Modifiers;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.application.Application;
import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.CharacterTileString;
import org.hexworks.zircon.api.graphics.TextWrap;
import org.hexworks.zircon.api.grid.TileGrid;

public class TextCharacterStringExample {

    private static final int TERMINAL_WIDTH = 42;
    private static final int TERMINAL_HEIGHT = 16;
    private static final Size SIZE = Size.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);

    public static void main(String[] args) {
        Application app = SwingApplications.startApplication(AppConfig.newBuilder()
                .withDefaultTileset(CP437TilesetResources.taffer20x20())
                .withSize(SIZE)
                .withDebugMode(true)
                .build());

        TileGrid tileGrid = app.getTileGrid();

        String text = "This is some text which is too long to fit on one line...";
        CharacterTileString tcs = CharacterTileStrings.newBuilder()
                .withForegroundColor(TileColor.fromString("#eeffee"))
                .withBackgroundColor(TileColor.fromString("#223344"))
                .withTextWrap(TextWrap.WRAP)
                .withSize(tileGrid.getSize())
                .withModifiers(Modifiers.underline())
                .withText(text)
                .build();

        tileGrid.draw(tcs, Position.zero(), tileGrid.getSize());

    }
}
