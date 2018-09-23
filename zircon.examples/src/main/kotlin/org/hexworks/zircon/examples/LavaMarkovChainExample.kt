package org.hexworks.zircon.examples

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.modifier.Markov
import org.hexworks.zircon.api.util.markovchain.MarkovChain
import org.hexworks.zircon.api.util.markovchain.MarkovChainNode

object LavaMarkovChainExample {

    private val tileset = CP437TilesetResources.taffer20x20()

    private val lavaColor0 = TileColor.fromString("#570000")
    private val lavaColor1 = TileColor.fromString("#750000")
    private val lavaColor2 = TileColor.fromString("#930000")
    private val lavaColor3 = TileColor.fromString("#b10000")
    private val lavaColor4 = TileColor.fromString("#c90000")

    private val bubbleColor0 = TileColor.fromString("#f0c98a")
    private val bubbleColor1 = TileColor.fromString("#f2d65a")
    private val bubbleColor2 = TileColor.fromString("#f4e32a")
    private val bubbleColor3 = TileColor.fromString("#f6f00a")


    private val defaultLava = Tile.empty()
            .withBackgroundColor(lavaColor0)

    private val heatedLava0 = Tile.empty()
            .withForegroundColor(lavaColor1)
            .withBackgroundColor(lavaColor0)
            .withCharacter(Symbols.BLOCK_SPARSE)
    private val heatedLava1 = Tile.empty()
            .withForegroundColor(lavaColor2)
            .withBackgroundColor(lavaColor0)
            .withCharacter(Symbols.BLOCK_SPARSE)

    private val smallBubble = Tile.empty()
            .withCharacter(Symbols.INTERPUNCT)
            .withForegroundColor(bubbleColor0)
            .withBackgroundColor(lavaColor1)

    private val mediumBubble = Tile.empty()
            .withCharacter(Symbols.BULLET_SMALL)
            .withForegroundColor(bubbleColor1)
            .withBackgroundColor(lavaColor2)

    private val largeBubble = Tile.empty()
            .withCharacter(Symbols.BULLET)
            .withForegroundColor(bubbleColor2)
            .withBackgroundColor(lavaColor3)

    private val explodingBubble = Tile.empty()
            .withCharacter(Symbols.SOLAR_SYMBOL)
            .withForegroundColor(bubbleColor3)
            .withBackgroundColor(lavaColor4)

    private val subsidingBubble0 = Tile.empty()
            .withCharacter(Symbols.WHITE_CIRCLE)
            .withForegroundColor(bubbleColor2)
            .withBackgroundColor(lavaColor3)

    private val subsidingBubble1 = Tile.empty()
            .withCharacter(Symbols.DEGREE)
            .withForegroundColor(bubbleColor1)
            .withBackgroundColor(lavaColor2)

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(tileset)
                .defaultSize(Sizes.create(80, 40))
                .debugMode(true)
                .build())

        val initialNode = MarkovChainNode.create(defaultLava)
        val heatedNode0 = MarkovChainNode.create(heatedLava0)
        val heatedNode1 = MarkovChainNode.create(heatedLava1)

        val smallBubbleNode = MarkovChainNode.create(smallBubble)
        val mediumBubbleNode = MarkovChainNode.create(mediumBubble)
        val largeBubbleNode = MarkovChainNode.create(largeBubble)
        val explodingBubbleNode = MarkovChainNode.create(explodingBubble)
        val subsidingBubbleNode0 = MarkovChainNode.create(subsidingBubble0)
        val subsidingBubbleNode1 = MarkovChainNode.create(subsidingBubble1)

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


        tileGrid.size().fetchPositions().forEach {
            tileGrid.setTileAt(it, defaultLava.withModifiers(Markov(MarkovChain.create(initialNode))))
        }


    }

}












