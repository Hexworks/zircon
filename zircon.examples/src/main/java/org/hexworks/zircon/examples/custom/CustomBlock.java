package org.hexworks.zircon.examples.custom;

import org.hexworks.zircon.api.data.base.BlockBase;
import org.hexworks.zircon.api.data.impl.Position3D;
import org.hexworks.zircon.api.data.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CustomBlock extends BlockBase {

    private Position3D position3D;
    private List<Tile> layers;

    public CustomBlock(Position3D position3D, List<Tile> layers) {
        this.position3D = position3D;
        this.layers = layers;
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
}
