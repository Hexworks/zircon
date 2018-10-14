package org.hexworks.zircon.examples.custom;

import org.hexworks.zircon.api.data.BlockSide;
import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.data.base.BlockBase;
import org.hexworks.zircon.api.data.impl.Position3D;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class CustomBlock extends BlockBase {

    private Position3D position3D;
    private List<Tile> layers;
    private Map<BlockSide, Tile> sides;

    public CustomBlock(Position3D position3D, List<Tile> layers, Map<BlockSide, Tile> sides) {
        this.position3D = position3D;
        this.layers = layers;
        this.sides = sides;
    }

    @NotNull
    @Override
    public Position3D getPosition() {
        return position3D;
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
