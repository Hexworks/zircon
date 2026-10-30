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
import kotlin.random.Random

/**
 * TV static over letters: each cell's Markov chain blanks out, shows scan lines or noise
 * blocks for a moment and returns.
 */
public val staticExample: Example = Example("Static") { context ->
    val random = Random(68743513)
    val characters = arrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K')

    val normal = Color.fromString("#71918C")
    val glow0 = Color.fromString("#68A19C")
    val glow1 = Color.fromString("#65B1AC")
    val dark = Color.fromString("#3D615F")
    val background = Color.fromString("#212429")

    val layer = context.contentLayer()
    layer.size.fetchPositions().forEach { position ->
        val tile = Tile.empty()
            .withForegroundColor(normal)
            .withBackgroundColor(background)
            .withCharacter(characters[random.nextInt(characters.size)])

        val initialNode = MarkovChainNode.create(tile, random.nextLong())
        val emptyNode = MarkovChainNode.create(tile.withCharacter(' '), random.nextLong())
        val staticNode0 = MarkovChainNode.create(tile.withCharacter(Symbols.SINGLE_LINE_HORIZONTAL).withForegroundColor(glow0), random.nextLong())
        val staticNode1 = MarkovChainNode.create(tile.withCharacter(Symbols.DOUBLE_LINE_HORIZONTAL).withForegroundColor(glow1), random.nextLong())
        val noiseNode0 = MarkovChainNode.create(tile.withCharacter(Symbols.BLOCK_SPARSE).withForegroundColor(dark), random.nextLong())
        val noiseNode1 = MarkovChainNode.create(tile.withCharacter(Symbols.BLOCK_MIDDLE).withForegroundColor(dark), random.nextLong())
        val noiseNode2 = MarkovChainNode.create(tile.withCharacter(Symbols.BLOCK_DENSE).withForegroundColor(dark), random.nextLong())

        initialNode.addNext(.0025, emptyNode)
        emptyNode.addNext(.999, initialNode)
        emptyNode.addNext(.001, emptyNode)

        initialNode.addNext(.00025, staticNode0)
        staticNode0.addNext(.008, initialNode)
        staticNode0.addNext(.15, staticNode1)
        staticNode1.addNext(.05, staticNode0)
        staticNode1.addNext(.05, initialNode)

        initialNode.addNext(.00025, noiseNode0)
        noiseNode0.addNext(.008, initialNode)
        noiseNode0.addNext(.15, noiseNode1)
        noiseNode1.addNext(.05, noiseNode0)
        noiseNode1.addNext(.05, initialNode)
        noiseNode1.addNext(.025, noiseNode2)
        noiseNode2.addNext(.15, noiseNode1)
        noiseNode2.addNext(.15, initialNode)

        layer.draw(tile.withAddedModifiers(Markov(MarkovChain.create(initialNode))), position)
    }
    ExampleView(context.emptyContent(), layers = listOf(layer))
}
