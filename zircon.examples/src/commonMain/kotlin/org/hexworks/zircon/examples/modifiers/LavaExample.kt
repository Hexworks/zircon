package org.hexworks.zircon.examples.modifiers

import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.extensions.fetchPositions
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.modifier.Markov
import org.hexworks.zircon.api.util.markovchain.MarkovChain
import org.hexworks.zircon.api.util.markovchain.MarkovChainNode
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** A lava field: a Markov chain per cell heats up, bubbles, bursts and subsides. */
public val lavaExample: Example = Example("Lava") { context ->
    val lava0 = Color.fromString("#570000")
    val lava1 = Color.fromString("#750000")
    val lava2 = Color.fromString("#930000")
    val lava3 = Color.fromString("#b10000")
    val lava4 = Color.fromString("#c90000")
    val bubble0 = Color.fromString("#f0c98a")
    val bubble1 = Color.fromString("#f2d65a")
    val bubble2 = Color.fromString("#f4e32a")
    val bubble3 = Color.fromString("#f6f00a")

    val defaultLava = Tile.empty().withBackgroundColor(lava0)
    val heatedLava0 = Tile.empty().withForegroundColor(lava1).withBackgroundColor(lava0).withCharacter(Symbols.BLOCK_SPARSE)
    val heatedLava1 = Tile.empty().withForegroundColor(lava2).withBackgroundColor(lava0).withCharacter(Symbols.BLOCK_SPARSE)
    val smallBubble = Tile.empty().withCharacter(Symbols.INTERPUNCT).withForegroundColor(bubble0).withBackgroundColor(lava1)
    val mediumBubble = Tile.empty().withCharacter(Symbols.BULLET_SMALL).withForegroundColor(bubble1).withBackgroundColor(lava2)
    val largeBubble = Tile.empty().withCharacter(Symbols.BULLET).withForegroundColor(bubble2).withBackgroundColor(lava3)
    val explodingBubble = Tile.empty().withCharacter(Symbols.SOLAR_SYMBOL).withForegroundColor(bubble3).withBackgroundColor(lava4)
    val subsidingBubble0 = Tile.empty().withCharacter(Symbols.WHITE_CIRCLE).withForegroundColor(bubble2).withBackgroundColor(lava3)
    val subsidingBubble1 = Tile.empty().withCharacter(Symbols.DEGREE).withForegroundColor(bubble1).withBackgroundColor(lava2)

    val layer = context.contentLayer()
    layer.size.fetchPositions().forEach { position ->
        // One chain per cell: every cell has its own state (and random seed).
        val initialNode = MarkovChainNode.create(defaultLava, position.hashCode().toLong())
        val heatedNode0 = MarkovChainNode.create(heatedLava0, position.hashCode() + 1L)
        val heatedNode1 = MarkovChainNode.create(heatedLava1, position.hashCode() + 2L)
        val smallBubbleNode = MarkovChainNode.create(smallBubble, position.hashCode() + 3L)
        val mediumBubbleNode = MarkovChainNode.create(mediumBubble, position.hashCode() + 4L)
        val largeBubbleNode = MarkovChainNode.create(largeBubble, position.hashCode() + 5L)
        val explodingBubbleNode = MarkovChainNode.create(explodingBubble, position.hashCode() + 6L)
        val subsidingBubbleNode0 = MarkovChainNode.create(subsidingBubble0, position.hashCode() + 7L)
        val subsidingBubbleNode1 = MarkovChainNode.create(subsidingBubble1, position.hashCode() + 8L)

        initialNode.addNext(.0005, heatedNode0)
        heatedNode0.addNext(.0005, heatedNode1)
        heatedNode1.addNext(.0005, heatedNode0)
        heatedNode0.addNext(.0005, initialNode)

        initialNode.addNext(.00025, smallBubbleNode)
        smallBubbleNode.addNext(.025, mediumBubbleNode)
        mediumBubbleNode.addNext(.05, largeBubbleNode)
        largeBubbleNode.addNext(.025, explodingBubbleNode)
        explodingBubbleNode.addNext(.1, subsidingBubbleNode0)
        subsidingBubbleNode0.addNext(.075, subsidingBubbleNode1)
        subsidingBubbleNode1.addNext(.05, initialNode)

        layer.draw(defaultLava.withModifiers(Markov(MarkovChain.create(initialNode))), position)
    }
    ExampleView(context.emptyContent(), layers = listOf(layer))
}
