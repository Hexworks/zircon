package org.hexworks.zircon.examples.modifiers

import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.extensions.fetchPositions
import org.hexworks.zircon.api.graphics.Symbols.ALPHA
import org.hexworks.zircon.api.graphics.Symbols.APPROXIMATION
import org.hexworks.zircon.api.graphics.Symbols.ARROW_UP
import org.hexworks.zircon.api.graphics.Symbols.BETA
import org.hexworks.zircon.api.modifier.Markov
import org.hexworks.zircon.api.util.markovchain.MarkovChain
import org.hexworks.zircon.api.util.markovchain.MarkovChainNode
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import kotlin.random.Random

/**
 * Every cell runs its own Markov chain: letters mostly stay, now and then glow brighter,
 * fade, or deteriorate into symbols and recover.
 */
public val glitchExample: Example = Example("Glitch") { context ->
    val random = Random(68743513)
    val characters = arrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K')
    val deteriorating = arrayOf('%', '-', '0', ALPHA, APPROXIMATION, ARROW_UP, BETA)

    val normal = Color.fromString("#71918C")
    val glow0 = Color.fromString("#68A19C")
    val glow1 = Color.fromString("#65B1AC")
    val glow2 = Color.fromString("#62C1BC")
    val dark = Color.fromString("#3D615F")
    val background = Color.fromString("#212429")

    val layer = context.contentLayer()
    layer.size.fetchPositions().forEach { position ->
        val tile = Tile.empty()
            .withForegroundColor(normal)
            .withBackgroundColor(background)
            .withCharacter(characters[random.nextInt(characters.size)])

        val initialNode = MarkovChainNode.create(tile, random.nextLong())
        val glowingNode0 = MarkovChainNode.create(tile.withForegroundColor(glow0), random.nextLong())
        val glowingNode1 = MarkovChainNode.create(tile.withForegroundColor(glow1), random.nextLong())
        val glowingNode2 = MarkovChainNode.create(tile.withForegroundColor(glow2), random.nextLong())
        val fadedNode = MarkovChainNode.create(tile.withForegroundColor(dark), random.nextLong())
        val deterioratingNode0 = MarkovChainNode.create(
            tile.withForegroundColor(glow0).withCharacter(deteriorating[random.nextInt(deteriorating.size)]),
            random.nextLong()
        )
        val deterioratingNode1 = MarkovChainNode.create(
            tile.withForegroundColor(glow1).withCharacter(deteriorating[random.nextInt(deteriorating.size)]),
            random.nextLong()
        )
        val deterioratingNode2 = MarkovChainNode.create(
            tile.withForegroundColor(glow2).withCharacter(deteriorating[random.nextInt(deteriorating.size)]),
            random.nextLong()
        )

        initialNode.addNext(.001, glowingNode0)
        glowingNode0.addNext(.002, glowingNode1)
        glowingNode1.addNext(.004, glowingNode2)
        glowingNode2.addNext(.008, glowingNode1)
        glowingNode1.addNext(.016, glowingNode0)
        glowingNode0.addNext(.032, initialNode)

        initialNode.addNext(.001, fadedNode)
        fadedNode.addNext(.005, initialNode)

        initialNode.addNext(.0005, deterioratingNode0)
        deterioratingNode0.addNext(.16, deterioratingNode1)
        deterioratingNode1.addNext(.16, deterioratingNode2)
        deterioratingNode2.addNext(.16, initialNode)

        layer.draw(tile.withAddedModifiers(Markov(MarkovChain.create(initialNode))), position)
    }
    ExampleView(context.emptyContent(), layers = listOf(layer))
}
