package org.hexworks.zircon.examples.custom;

import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.data.BaseTile;
import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.graphics.StyleSet;
import org.hexworks.zircon.api.modifier.Modifier;
import org.hexworks.zircon.api.resource.TileType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class CustomTile extends BaseTile {

    private StyleSet styleSet;
    private char character;

    private CustomTile(StyleSet styleSet, char character) {
        this.styleSet = styleSet;
        this.character = character;
    }

    @NotNull
    @Override
    public TileType getTileType() {
        return TileType.CHARACTER_TILE;
    }


    @NotNull
    @Override
    public StyleSet getStyleSet() {
        return styleSet;
    }

    @NotNull
    @Override
    public Tile withForegroundColor(@NotNull TileColor foregroundColor) {
        return new CustomTile(
                styleSet.withForegroundColor(foregroundColor),
                character);
    }

    @NotNull
    @Override
    public Tile withBackgroundColor(@NotNull TileColor backgroundColor) {
        return new CustomTile(
                styleSet.withBackgroundColor(backgroundColor),
                character);
    }

    @NotNull
    @Override
    public Tile withStyle(@NotNull StyleSet style) {
        return new CustomTile(
                style,
                character);
    }

    @NotNull
    @Override
    public Tile withModifiers(@NotNull Set<? extends Modifier> modifiers) {
        return new CustomTile(
                styleSet.withModifiers(modifiers),
                character);
    }

    @NotNull
    @Override
    public Tile withRemovedModifiers(@NotNull Set<? extends Modifier> modifiers) {
        return new CustomTile(
                styleSet.withRemovedModifiers(modifiers),
                character);
    }

    @NotNull
    @Override
    public String generateCacheKey() {
        return String.format("CustomTile(style=%s,char=%s)",
                styleSet.generateCacheKey(), character);
    }

    @NotNull
    @Override
    public Tile createCopy() {
        return new CustomTile(
                styleSet,
                character);
    }

    @NotNull
    @Override
    public Tile withAddedModifiers(@NotNull Set<? extends Modifier> modifiers) {
        return new CustomTile(
                styleSet.withAddedModifiers(modifiers),
                character);
    }

    @NotNull
    @Override
    public Tile withNoModifiers() {
        return new CustomTile(
                styleSet.withNoModifiers(),
                character);
    }

    @NotNull
    @Override
    public TileColor getForegroundColor() {
        return styleSet.getForegroundColor();
    }

    @NotNull
    @Override
    public TileColor getBackgroundColor() {
        return styleSet.getBackgroundColor();
    }

    @NotNull
    @Override
    public Set<Modifier> getModifiers() {
        return styleSet.getModifiers();
    }
}
