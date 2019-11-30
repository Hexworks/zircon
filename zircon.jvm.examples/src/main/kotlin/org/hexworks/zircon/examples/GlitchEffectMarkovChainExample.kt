package org.hexworks.zircon.examples


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols.ALPHA
import org.hexworks.zircon.api.graphics.Symbols.APPROXIMATION
import org.hexworks.zircon.api.graphics.Symbols.ARROW_UP
import org.hexworks.zircon.api.graphics.Symbols.BETA
import org.hexworks.zircon.api.modifier.Glow
import org.hexworks.zircon.api.modifier.Markov
import org.hexworks.zircon.api.util.markovchain.MarkovChain
import org.hexworks.zircon.api.util.markovchain.MarkovChainNode
import java.util.*

// TODO: not working
object GlitchEffectMarkovChainExample {

    private val tileset = CP437TilesetResources.taffer20x20()
    private val random = Random(68743513)

    private val characters = arrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K')
    private val deterioratingCharacters = arrayOf('%', '-', '0', ALPHA, APPROXIMATION, ARROW_UP, BETA)

    @JvmStatic
    fun main(args: Array<String>) {

        val normal = TileColor.fromString("#71918C")
        val glow0 = TileColor.fromString("#68A19C")
        val glow1 = TileColor.fromString("#65B1AC")
        val glow2 = TileColor.fromString("#62C1BC")

        val dark = TileColor.fromString("#3D615F")
        val background = TileColor.fromString("#212429")

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(80, 40))
                .withDebugMode(true)
                .build())

        val defaultGlow = Glow()

        tileGrid.size.fetchPositions().forEach { _ ->

            val tile = Tile.empty()
                    .withForegroundColor(normal)
                    .withBackgroundColor(background)
                    .withCharacter(characters[random.nextInt(characters.size)])
                    .withModifiers(defaultGlow)

            val initialNode = MarkovChainNode.create(tile)
            val glowingNode0 = MarkovChainNode.create(tile
                    .withForegroundColor(glow0)
                    .withModifiers(Glow(7f)))
            val glowingNode1 = MarkovChainNode.create(tile
                    .withForegroundColor(glow1)
                    .withModifiers(Glow(9f)))
            val glowingNode2 = MarkovChainNode.create(tile
                    .withForegroundColor(glow2)
                    .withModifiers(Glow(11f)))

            val fadedNode = MarkovChainNode.create(tile
                    .withForegroundColor(dark)
                    .withModifiers(Glow(9f)))

            val deterioratingNode0 = MarkovChainNode.create(tile
                    .withForegroundColor(glow0)
                    .withModifiers(Glow(10f))
                    .withCharacter(deterioratingCharacters[random.nextInt(deterioratingCharacters.size)]))
            val deterioratingNode1 = MarkovChainNode.create(tile
                    .withForegroundColor(glow1)
                    .withModifiers(Glow(15f))
                    .withCharacter(deterioratingCharacters[random.nextInt(deterioratingCharacters.size)]))
            val deterioratingNode2 = MarkovChainNode.create(tile
                    .withForegroundColor(glow2)
                    .withModifiers(Glow(20f))
                    .withCharacter(deterioratingCharacters[random.nextInt(deterioratingCharacters.size)]))

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


            tileGrid.putTile(tile.withAddedModifiers(Markov(MarkovChain.create(initialNode))))
        }


    }

}












