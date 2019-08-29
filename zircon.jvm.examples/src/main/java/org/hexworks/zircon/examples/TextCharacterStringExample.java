package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.application.Application;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.CharacterTileString;
import org.hexworks.zircon.api.graphics.TextWrap;
import org.hexworks.zircon.api.grid.TileGrid;

public class TextCharacterStringExample {

    private static final int TERMINAL_WIDTH = 42;
    private static final int TERMINAL_HEIGHT = 16;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);

    public static void main(String[] args) {
        Application app = SwingApplications.startApplication(AppConfigs.newConfig()
                .withDefaultTileset(CP437TilesetResources.taffer20x20())
                .withSize(SIZE)
                .withDebugMode(true)
                .build());

        TileGrid tileGrid = app.getTileGrid();

        String text = "This is some text which is too long to fit on one line...";
        CharacterTileString tcs = CharacterTileStrings.newBuilder()
                .withForegroundColor(TileColors.fromString("#eeffee"))
                .withBackgroundColor(TileColors.fromString("#223344"))
                .withTextWrap(TextWrap.WRAP)
                .withSize(tileGrid.getSize())
                .withModifiers(Modifiers.underline())
                .withText(text)
                .build();

        tileGrid.draw(tcs, Positions.zero(), tileGrid.getSize());

    }
}
