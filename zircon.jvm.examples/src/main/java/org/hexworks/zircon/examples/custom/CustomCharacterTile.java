package org.hexworks.zircon.examples.custom;

import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.data.base.BaseCharacterTile;
import org.hexworks.zircon.api.graphics.StyleSet;
import org.jetbrains.annotations.NotNull;

public class CustomCharacterTile extends BaseCharacterTile {

    private StyleSet styleSet;
    private char character;

    CustomCharacterTile(StyleSet styleSet, char character) {
        this.styleSet = styleSet;
        this.character = character;
    }


    @Override
    public char getCharacter() {
        return character;
    }

    @NotNull
    @Override
    public StyleSet getStyleSet() {
        return styleSet;
    }

    @NotNull
    @Override
    public Tile createCopy() {
        return new CustomCharacterTile(styleSet, character);
    }

    @NotNull
    @Override
    public String generateCacheKey() {
        return String.format("CustomCharacterTile(c=%s,s=%s)", character, styleSet);
    }
}
