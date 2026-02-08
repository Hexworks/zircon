import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.position
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.color.ANSIColor.*
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette


fun Application.test1() = also {
    tileGrid.draw(characterTile {
        +'W'
        withStyleSet {
            foregroundColor = DefaultAnsiPalette[YELLOW]
            backgroundColor = DefaultAnsiPalette[BLUE]
        }
    }, position {
        x = 5
        y = 5
    })

    tileGrid.draw(characterTile {
        +'O'
        withStyleSet {
            foregroundColor = DefaultAnsiPalette[BRIGHT_GREEN]
            backgroundColor = DefaultAnsiPalette[GRAY]
        }
    }, position {
        x = 6
        y = 5
    })

    tileGrid.draw(characterTile {
        +'M'
        withStyleSet {
            foregroundColor = DefaultAnsiPalette[BRIGHT_RED]
            backgroundColor = DefaultAnsiPalette[GRAY]
        }
    }, position {
        x = 7
        y = 5
    })

//    tileGrid.draw(characterTile {
//        +'B'
//        withStyleSet {
//            modifiers = setOf(border {
//                this.borderColor = CYAN
//                this.borderWidth = 1
//                this.borderType = SOLID
//                this.borderPositions = setOf(TOP, RIGHT, BOTTOM, LEFT)
//            })
//        }
//    }, position {
//        x = 9
//        y = 5
//    })
//
//    tileGrid.draw(characterTile {
//        +'A'
//        withStyleSet {
//            modifiers = setOf(border {
//                this.borderColor = YELLOW
//                this.borderWidth = 2
//                this.borderType = SOLID
//                this.borderPositions = setOf(TOP, RIGHT, BOTTOM, LEFT)
//            })
//        }
//    }, position {
//        x = 10
//        y = 5
//    })
//
//    tileGrid.draw(characterTile {
//        +'T'
//        withStyleSet {
//            modifiers = setOf(border {
//                this.borderColor = MAGENTA
//                this.borderWidth = 3
//                this.borderType = SOLID
//                this.borderPositions = setOf(TOP, RIGHT, BOTTOM, LEFT)
//            })
//        }
//    }, position {
//        x = 11
//        y = 5
//    })
}