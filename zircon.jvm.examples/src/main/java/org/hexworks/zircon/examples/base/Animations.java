package org.hexworks.zircon.examples.base;

import org.hexworks.zircon.api.animation.Animation;
import org.hexworks.zircon.api.animation.AnimationResource;
import org.hexworks.zircon.api.builder.animation.AnimationBuilder;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.examples.animations.HexworksSkullExampleJava;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Animations {

    public static Animation hexworksSkull(Position position, TilesetResource tileset) {
        AnimationBuilder skull = AnimationResource.loadAnimationFromStream(
                HexworksSkullExampleJava.class.getResourceAsStream("/animations/skull.zap"),
                tileset);
        // 0 means infinite
        skull.withLoopCount(0);
        for (int i = 0; i < skull.getTotalFrameCount(); i++) {
            skull.addPosition(position);
        }
        return skull.build();
    }
}
