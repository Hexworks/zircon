import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.position
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.modifier.border
import org.hexworks.zircon.api.color.ANSITileColor.*
import org.hexworks.zircon.api.modifier.BorderPosition.*
import org.hexworks.zircon.api.modifier.BorderType.SOLID
import org.hexworks.zircon.api.modifier.SimpleModifiers.CrossedOut
import org.hexworks.zircon.api.modifier.SimpleModifiers.Underline


fun Application.test1() = also {
    tileGrid.draw(characterTile {
        +'W'
        withStyleSet {
            foregroundColor = YELLOW
            backgroundColor = BLUE
        }
    }, position {
        x = 5
        y = 5
    })

    tileGrid.draw(characterTile {
        +'O'
        withStyleSet {
            modifiers = setOf(Underline)
            foregroundColor = BRIGHT_GREEN
            backgroundColor = GRAY
        }
    }, position {
        x = 6
        y = 5
    })

    tileGrid.draw(characterTile {
        +'M'
        withStyleSet {
            modifiers = setOf(CrossedOut)
            foregroundColor = BRIGHT_RED
            backgroundColor = GRAY
        }
    }, position {
        x = 7
        y = 5
    })

    tileGrid.draw(characterTile {
        +'B'
        withStyleSet {
            modifiers = setOf(border {
                this.borderColor = CYAN
                this.borderWidth = 1
                this.borderType = SOLID
                this.borderPositions = setOf(TOP, RIGHT, BOTTOM, LEFT)
            })
        }
    }, position {
        x = 9
        y = 5
    })

    tileGrid.draw(characterTile {
        +'A'
        withStyleSet {
            modifiers = setOf(border {
                this.borderColor = YELLOW
                this.borderWidth = 2
                this.borderType = SOLID
                this.borderPositions = setOf(TOP, RIGHT, BOTTOM, LEFT)
            })
        }
    }, position {
        x = 10
        y = 5
    })

    tileGrid.draw(characterTile {
        +'T'
        withStyleSet {
            modifiers = setOf(border {
                this.borderColor = MAGENTA
                this.borderWidth = 3
                this.borderType = SOLID
                this.borderPositions = setOf(TOP, RIGHT, BOTTOM, LEFT)
            })
        }
    }, position {
        x = 11
        y = 5
    })
}