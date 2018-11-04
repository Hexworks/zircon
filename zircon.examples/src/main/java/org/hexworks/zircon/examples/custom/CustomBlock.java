package org.hexworks.zircon.examples.custom;

import org.hexworks.zircon.api.data.BlockSide;
import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.data.base.BlockBase;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class CustomBlock extends BlockBase {

    private List<Tile> layers;
    private Map<BlockSide, Tile> sides;

    public CustomBlock(List<Tile> layers, Map<BlockSide, Tile> sides) {
        this.layers = layers;
        this.sides = sides;
    }

    @NotNull
    @Override
    public List<Tile> getLayers() {
        return layers;
    }

    @NotNull
    @Override
    public Tile fetchSide(@NotNull BlockSide side) {
        return sides.get(side);
    }
}
