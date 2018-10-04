package org.hexworks.zircon.examples

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.modifier.Glow
import org.hexworks.zircon.api.modifier.Markov
import org.hexworks.zircon.api.util.markovchain.MarkovChain
import org.hexworks.zircon.api.util.markovchain.MarkovChainNode
import java.util.*

object StaticEffectMarkovChainExample {

    private val tileset = CP437TilesetResources.rexPaint20x20()
    private val random = Random(68743513)

    private val characters = arrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K')

    val normal = TileColor.fromString("#71918C")
    val glow0 = TileColor.fromString("#68A19C")
    val glow1 = TileColor.fromString("#65B1AC")
    val glow2 = TileColor.fromString("#62C1BC")

    val dark = TileColor.fromString("#3D615F")
    val background = TileColor.fromString("#212429")

    @JvmStatic
    fun main(args: Array<String>) {


        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(tileset)
                .defaultSize(Sizes.create(80, 40))
                .debugMode(true)
                .build())

        val defaultGlow = Glow()

        tileGrid.size.fetchPositions().forEach { _ ->

            val tile = Tile.empty()
                    .withForegroundColor(normal)
                    .withBackgroundColor(background)
                    .withCharacter(characters[random.nextInt(characters.size)])
                    .withModifiers(defaultGlow)

            val initialNode = MarkovChainNode.create(tile)

            val emptyNode = MarkovChainNode.create(tile
                    .withCharacter(' '))

            val staticNode0 = MarkovChainNode.create(tile
                    .withCharacter(Symbols.SINGLE_LINE_HORIZONTAL)
                    .withForegroundColor(glow0))
            val staticNode1 = MarkovChainNode.create(tile
                    .withCharacter(Symbols.DOUBLE_LINE_HORIZONTAL)
                    .withForegroundColor(glow1))

            val noiseNode0 = MarkovChainNode.create(tile
                    .withCharacter(Symbols.BLOCK_SPARSE)
                    .withForegroundColor(dark))
            val noiseNode1 = MarkovChainNode.create(tile
                    .withCharacter(Symbols.BLOCK_MIDDLE)
                    .withForegroundColor(dark))
            val noiseNode2 = MarkovChainNode.create(tile
                    .withCharacter(Symbols.BLOCK_DENSE)
                    .withForegroundColor(dark))

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

            val markov = Markov(MarkovChain.create(initialNode))


            tileGrid.putTile(tile.withAddedModifiers(markov))
        }


    }

}












