import korlibs.datastructure.FastIdentityMap
import korlibs.datastructure.getOrPut
import korlibs.event.Key
import korlibs.event.KeyEvent
import korlibs.event.MouseEvent
import korlibs.image.atlas.MutableAtlasUnit
import korlibs.image.atlas.add
import korlibs.image.bitmap.Bitmap32
import korlibs.image.bitmap.BmpSlice
import korlibs.image.bitmap.context2d
import korlibs.image.bitmap.slice
import korlibs.image.color.Colors
import korlibs.image.color.RGBA
import korlibs.image.format.readBitmap
import korlibs.io.async.launchImmediately
import korlibs.io.dynamic.dyn
import korlibs.io.file.std.localCurrentDirVfs
import korlibs.io.file.std.openAsZip
import korlibs.io.file.std.resourcesVfs
import korlibs.io.serialization.yaml.Yaml
import korlibs.korge.annotations.KorgeExperimental
import korlibs.korge.input.MouseEvents
import korlibs.korge.input.keys
import korlibs.korge.input.mouse
import korlibs.korge.scene.PixelatedScene
import korlibs.korge.time.interval
import korlibs.korge.view.SContainer
import korlibs.korge.view.renderableView
import korlibs.math.geom.*
import korlibs.math.geom.Size
import korlibs.math.geom.slice.splitInRows
import korlibs.time.DateTime
import korlibs.time.milliseconds
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.Applications
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.DrawSurfaces
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.RenderData
import org.hexworks.zircon.api.behavior.TilesetHolder
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.internal.application.InternalApplication
import org.hexworks.zircon.internal.behavior.RenderableContainer
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.graphics.FastTileGraphics
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.renderer.impl.KORGE_CONTAINER
import org.hexworks.zircon.internal.resource.TilesetSourceType
import org.hexworks.zircon.internal.resource.TilesetType
import org.hexworks.zircon.internal.tileset.impl.korge.toRGBA
import org.hexworks.zircon.internal.util.CP437Utils
import kotlin.random.Random


/// === BENCHMARK ===

val BENCHMARK_RANDOM = Random(13513516895)

var BENCHMARK_CURR_IDX = 0

const val BENCHMARK_LAYER_WIDTH = GRID_WIDTH.div(2)
const val BENCHMARK_LAYER_HEIGHT = GRID_HEIGHT.div(2)
const val BENCHMARK_LAYER_COUNT = 20
val BENCHMARK_LAYER_SIZE = org.hexworks.zircon.api.data.Size.create(BENCHMARK_LAYER_WIDTH, BENCHMARK_LAYER_HEIGHT)
val BENCHMARK_FILLER = Tile.defaultTile().withCharacter('x')

val BENCHMARK_LAYERS = (0..BENCHMARK_LAYER_COUNT).map {
    val imageLayer = DrawSurfaces.tileGraphicsBuilder()
        .withSize(BENCHMARK_LAYER_SIZE)
        .withTileset(CP437TilesetResources.rexPaint16x16())
        .build()
    BENCHMARK_LAYER_SIZE.fetchPositions().forEach {
        imageLayer.draw(BENCHMARK_FILLER, it)
    }
    LayerBuilder.newBuilder()
        .withOffset(
            Position.create(
                x = BENCHMARK_RANDOM.nextInt(GRID_WIDTH - BENCHMARK_LAYER_WIDTH),
                y = BENCHMARK_RANDOM.nextInt(GRID_HEIGHT - BENCHMARK_LAYER_HEIGHT)
            )
        )
        .withTileGraphics(imageLayer)
        .build()
}

/// === END BENCHMARK ===

const val BENCHMARK = true
const val DO_RENDER = true

//class ZirconKorgeScene : PixelatedScene(128, 128, sceneScaleMode = ScaleMode.FILL) {
@OptIn(KorgeExperimental::class)
open class ZirconKorgeScene(
    private val sceneSize: Size,
    val function: (screen: Screen) -> Unit
) : PixelatedScene(
    sceneSize.width.toInt(),
    sceneSize.height.toInt(),
    sceneScaleMode = ScaleMode.NO_SCALE,
    sceneAnchor = Anchor.TOP_LEFT,
    sceneSmoothing = false
) {
    override suspend fun SContainer.sceneMain() {
        val config = AppConfig.newBuilder()
            .withDefaultTileset(CP437TilesetResources.fnord16x16())
            //.withSize(16, 16)
            .withProperty(KORGE_CONTAINER, this)
            .withSize(GRID_WIDTH, GRID_HEIGHT)
            .withDefaultTileset(TILESET)
            .build()

        val application = KorgeBasicInternalApplication(config)
        val tileGrid = application.tileGrid as InternalTileGrid

        if (BENCHMARK) {
            BENCHMARK_LAYERS.forEach(tileGrid::addLayer)
        }

        val screen = tileGrid.toScreen()


        val tileWidth = TILE_SIZE
        val tileHeight = TILE_SIZE
        val tileSize = SizeInt(tileWidth, tileHeight)
        val atlas = MutableAtlasUnit()
        val bmp = resourcesVfs["cp_437_tilesets/${TILESET.fileName}"].readBitmap();
        val tilemapBitmap = bmp.toBMP32()

        val bgTile = atlas.add(Bitmap32(tileWidth, tileHeight) { _, _ -> Colors.WHITE }).slice
        val strikeoutTile = atlas.add(Bitmap32(tileWidth, tileHeight).context2d {
            stroke(Colors.WHITE, 2f) { line(Point(0, tileHeight * .5f), Point(tileWidth, tileHeight * .5f)) }
        }).slice
        val underlinedTile = atlas.add(Bitmap32(tileWidth, tileHeight).context2d {
            stroke(Colors.WHITE, 2f) { line(Point(0, tileHeight - 1), Point(tileWidth, tileHeight - 1)) }
        }).slice
        //val tiles = tilemapBitmap.slice().splitInRows(tileSize.width, tileSize.height)

        mouse {
            fun mevent(e: MouseEvents) {
                val tilePos = (e.currentPosLocal / tileSize.toFloat()).toInt()
                val pos = Position.create(tilePos.x, tilePos.y)
                //println("$tilePos: $e")

                val type = when (e.lastEvent.type) {
                    MouseEvent.Type.MOVE -> MouseEventType.MOUSE_MOVED
                    MouseEvent.Type.DRAG -> MouseEventType.MOUSE_DRAGGED
                    MouseEvent.Type.UP -> MouseEventType.MOUSE_RELEASED
                    MouseEvent.Type.DOWN -> MouseEventType.MOUSE_PRESSED
                    MouseEvent.Type.CLICK -> MouseEventType.MOUSE_CLICKED
                    MouseEvent.Type.ENTER -> MouseEventType.MOUSE_ENTERED
                    MouseEvent.Type.EXIT -> MouseEventType.MOUSE_EXITED
                    MouseEvent.Type.SCROLL -> if (e.scrollDeltaYPages < 0f) MouseEventType.MOUSE_WHEEL_ROTATED_DOWN else MouseEventType.MOUSE_WHEEL_ROTATED_UP
                }
                tileGrid.process(
                    org.hexworks.zircon.api.uievent.MouseEvent(type, e.button.id, pos),
                    UIEventPhase.TARGET
                )
            }
            click { mevent(it) }
            over { mevent(it) }
            out { mevent(it) }
            move { mevent(it) }
            upAnywhere { mevent(it) }
            down { mevent(it) }
            downOutside { mevent(it) }
        }

        keys {
            fun kevent(e: KeyEvent) {
                val type = when (e.type) {
                    KeyEvent.Type.TYPE -> KeyboardEventType.KEY_TYPED
                    KeyEvent.Type.UP -> KeyboardEventType.KEY_RELEASED
                    KeyEvent.Type.DOWN -> KeyboardEventType.KEY_PRESSED
                }
                tileGrid.process(
                    KeyboardEvent(type, e.characters(), e.key.toKeyCode(), e.ctrl, e.alt, e.meta, e.shift),
                    UIEventPhase.TARGET
                )
            }
            down { kevent(it) }
            up { kevent(it) }
            typed { kevent(it) }
        }

        var blinkOn = false
        interval(config.blinkLengthInMilliSeconds.milliseconds) {
            blinkOn = !blinkOn
        }

        val cachedTileSets = FastIdentityMap<TilesetResource, KorgeTileset>()

        renderableView {
            val now = DateTime.nowUnixMillisLong()
            tileGrid.updateAnimations(now, tileGrid)

            if (BENCHMARK) {
                benchmark(tileGrid)
            }

            this.ctx.useBatcher { batch ->
                val tileWidthF = tileWidth.toFloat()
                val tileHeightF = tileHeight.toFloat()

                fun drawSlice(x: Float, y: Float, bmp: BmpSlice?, color: RGBA, flipX: Boolean, flipY: Boolean) {
                    if (bmp == null) return
                    val rx = if (flipX) x + tileWidthF else x
                    val ry = if (flipY) y + tileHeightF else y
                    val w = if (flipX) -tileWidthF else tileWidthF
                    val h = if (flipY) -tileHeightF else tileHeightF

                    batch.drawQuad(this.ctx.getTex(bmp), rx, ry, w, h, colorMul = color)
                }

                if (DO_RENDER) {
                    tileGrid.renderAllTiles { pos, tile, tileset ->
                        if (tile.isBlinking && blinkOn) return@renderAllTiles


                        val ktileset = cachedTileSets.getOrPut(tileset) {
                            KorgeTileset(it).also { tileset ->
                                launchImmediately {
                                    tileset.preload()
                                }
                            }
                        }

                        if (!ktileset.ready) return@renderAllTiles

                        val flipX = tile.isHorizontalFlipped
                        val flipY = tile.isVerticalFlipped
                        val px = (pos.x * tileWidthF)
                        val py = (pos.y * tileHeightF)

                        when (tile) {
                            is CharacterTile -> {
                                drawSlice(px, py, bgTile, tile.backgroundColor.toRGBA(), flipX, flipY)
                                drawSlice(
                                    px,
                                    py,
                                    ktileset.tiles[CP437Utils.fetchCP437IndexForChar(tile.character)],
                                    tile.foregroundColor.toRGBA(),
                                    flipX,
                                    flipY
                                )
                                if (tile.isCrossedOut) drawSlice(
                                    px,
                                    py,
                                    strikeoutTile,
                                    tile.foregroundColor.toRGBA(),
                                    false,
                                    false
                                )
                                if (tile.isUnderlined) drawSlice(
                                    px,
                                    py,
                                    underlinedTile,
                                    tile.foregroundColor.toRGBA(),
                                    false,
                                    false
                                )
                            }

                            is GraphicalTile -> {
                                drawSlice(px, py, ktileset.tilesByName[tile.name], Colors.WHITE, flipX, flipY)
                            }
                        }
                    }

                }
            }

            function(screen)
        }
    }


    class KorgeTileset(
        val resource: TilesetResource
    ) {
        suspend fun preload() {
            println("KorgeTileset.preload: ${resource.tilesetSourceType}, ${resource.tilesetType}, '${resource.path}'")
            try {
                val vfs = when (resource.tilesetSourceType) {
                    TilesetSourceType.FILESYSTEM -> localCurrentDirVfs
                    TilesetSourceType.JAR -> resourcesVfs
                }
                when (resource.tilesetType) {
                    TilesetType.CP437Tileset -> {
                        val bitmap = vfs[resource.path].readBitmap().toBMP32IfRequired()
                        tiles = bitmap.slice().splitInRows(resource.width, resource.width)
                    }

                    TilesetType.GraphicalTileset -> {
                        val zip = vfs[resource.path].openAsZip()
                        val info = Yaml.decode(zip["tileinfo.yml"].readString()).dyn
                        val infoName = info["name"].str
                        val infoSize = info["size"].int
                        val infoFiles = info["files"].list
                        for (file in infoFiles) {
                            val fileName = file["name"].str
                            val tilesPerRow = file["tilesPerRow"].int
                            val tiles = file["tiles"].list
                            val tileNames = tiles.map { it["name"].str }
                            val bitmap = zip[fileName].readBitmap().toBMP32IfRequired()
                            val tilesBmps = bitmap.slice().splitInRows(infoSize, infoSize)
                            this.tiles = tilesBmps
                            this.tilesByName = tileNames.zip(tilesBmps).associate { it.first to it.second }

                            //println("tileNames=$tileNames")
                            //println("file=$file")
                        }
                        //println(zip.listNames())
                        //println(info)
                        //TODO()
                    }

                    is TilesetType.CustomTileset -> TODO()
                    TilesetType.TrueTypeFont -> TODO()
                }
                ready = true
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }

        var ready = false
        var tiles: List<BmpSlice> = listOf()
        var tilesByName: Map<String, BmpSlice> = mapOf()
    }

    fun ANSITileColor.Companion.random(): ANSITileColor {
        return ANSITileColor.values()[BENCHMARK_RANDOM.nextInt(0, ANSITileColor.values().size)]
    }

    fun benchmark(grid: TileGrid) {


        for (x in 0 until GRID_WIDTH) {
            for (y in 1 until GRID_HEIGHT) {
                grid.draw(
                    Tile.newBuilder()
                        .withCharacter(CP437Utils.convertCp437toUnicode(BENCHMARK_RANDOM.nextInt(0, 255)))
                        .withBackgroundColor(ANSITileColor.random())
                        .withForegroundColor(ANSITileColor.random())
                        .buildCharacterTile(), Position.create(x, y)
                )
            }
        }

        BENCHMARK_LAYERS.forEach {
            it.asInternalLayer().moveTo(
                Position.create(
                    x = BENCHMARK_RANDOM.nextInt(GRID_WIDTH - BENCHMARK_LAYER_WIDTH),
                    y = BENCHMARK_RANDOM.nextInt(GRID_HEIGHT - BENCHMARK_LAYER_HEIGHT)
                )
            )
        }
        BENCHMARK_CURR_IDX = if (BENCHMARK_CURR_IDX == 0) 1 else 0
    }

    class KorgeBasicInternalApplication(
        override val config: AppConfig
    ) : InternalApplication {
        override val eventBus: EventBus = EventBus.create()
        override val eventScope: ZirconScope = ZirconScope()
        override val tileGrid: TileGrid = Applications.createTileGrid(config, eventBus).asInternal().also {
            it.application = this
        }

        override fun beforeRender(listener: (RenderData) -> Unit): Subscription = TODO("Not yet implemented")
        override fun afterRender(listener: (RenderData) -> Unit): Subscription = TODO("Not yet implemented")
        override fun asInternal(): InternalApplication = this
        override val closedValue: ObservableValue<Boolean> get() = TODO()
        override fun close() = Unit

    }

    private fun InternalTileGrid.renderAllTiles(
        renderTile: (position: Position, tile: Tile, tileset: TilesetResource) -> Unit
    ) {
        val layers = fetchLayers()
        val gridPositions = size.fetchPositions().toList()
        val tiles = mutableListOf<Pair<Tile, TilesetResource>>()
        gridPositions.forEach { pos ->
            tiles@ for (i in layers.size - 1 downTo 0) {
                val (layerPos, layer) = layers[i]
                val toRender = layer.getTileAtOrNull(pos - layerPos)?.tiles() ?: listOf()
                for (j in toRender.size - 1 downTo 0) {
                    val tile = toRender[j]
                    val tileset = tile.finalTileset(layer)
                    tiles.add(0, tile to tileset)
                    if (tile.isOpaque) break@tiles
                }
            }

            var idx = 1
            for ((tile, tileset) in tiles) {
                renderTile(pos, tile, tileset)
                idx++
            }
            tiles.clear()
        }
    }

    private fun RenderableContainer.fetchLayers(): List<Pair<Position, TileGraphics>> {
        return renderables.map { renderable ->
            val tg = FastTileGraphics(initialSize = renderable.size, initialTileset = renderable.tileset)
            if (!renderable.isHidden) renderable.render(tg)
            renderable.position to tg
        }
    }

    private fun Tile.tiles(): List<Tile> = when (this) {
        is StackedTile -> tiles.flatMap { it.tiles() }
        else -> listOf(this)
    }

    private fun Tile.finalTileset(graphics: TileGraphics): TilesetResource = when (this) {
        is TilesetHolder -> tileset
        else -> graphics.tileset
    }

    fun Key.toKeyCode(): KeyCode = when (this) {
        Key.ENTER -> KeyCode.ENTER
        Key.BACKSPACE -> KeyCode.BACKSPACE
        Key.TAB -> KeyCode.TAB
        Key.CANCEL -> KeyCode.CANCEL
        Key.CLEAR -> KeyCode.CLEAR
        Key.SHIFT -> KeyCode.SHIFT
        Key.CONTROL -> KeyCode.CONTROL
        Key.ALT -> KeyCode.ALT
        Key.PAUSE -> KeyCode.PAUSE
        Key.CAPS_LOCK -> KeyCode.CAPS_LOCK
        Key.ESCAPE -> KeyCode.ESCAPE
        Key.SPACE -> KeyCode.SPACE
        Key.PAGE_UP -> KeyCode.PAGE_UP
        Key.PAGE_DOWN -> KeyCode.PAGE_DOWN
        Key.END -> KeyCode.END
        Key.HOME -> KeyCode.HOME
        Key.LEFT -> KeyCode.LEFT
        Key.UP -> KeyCode.UP
        Key.RIGHT -> KeyCode.RIGHT
        Key.DOWN -> KeyCode.DOWN
        Key.COMMA -> KeyCode.COMMA
        Key.MINUS -> KeyCode.MINUS
        Key.PERIOD -> KeyCode.PERIOD
        Key.SLASH -> KeyCode.SLASH
        Key.N0 -> KeyCode.DIGIT_0
        Key.N1 -> KeyCode.DIGIT_1
        Key.N2 -> KeyCode.DIGIT_2
        Key.N3 -> KeyCode.DIGIT_3
        Key.N4 -> KeyCode.DIGIT_4
        Key.N5 -> KeyCode.DIGIT_5
        Key.N6 -> KeyCode.DIGIT_6
        Key.N7 -> KeyCode.DIGIT_7
        Key.N8 -> KeyCode.DIGIT_8
        Key.N9 -> KeyCode.DIGIT_9
        Key.SEMICOLON -> KeyCode.SEMICOLON
        Key.EQUAL -> KeyCode.EQUALS
        Key.A -> KeyCode.KEY_A
        Key.B -> KeyCode.KEY_B
        Key.C -> KeyCode.KEY_C
        Key.D -> KeyCode.KEY_D
        Key.E -> KeyCode.KEY_E
        Key.F -> KeyCode.KEY_F
        Key.G -> KeyCode.KEY_G
        Key.H -> KeyCode.KEY_H
        Key.I -> KeyCode.KEY_I
        Key.J -> KeyCode.KEY_J
        Key.K -> KeyCode.KEY_K
        Key.L -> KeyCode.KEY_L
        Key.M -> KeyCode.KEY_M
        Key.N -> KeyCode.KEY_N
        Key.O -> KeyCode.KEY_O
        Key.P -> KeyCode.KEY_P
        Key.Q -> KeyCode.KEY_Q
        Key.R -> KeyCode.KEY_R
        Key.S -> KeyCode.KEY_S
        Key.T -> KeyCode.KEY_T
        Key.U -> KeyCode.KEY_U
        Key.V -> KeyCode.KEY_V
        Key.W -> KeyCode.KEY_W
        Key.X -> KeyCode.KEY_X
        Key.Y -> KeyCode.KEY_Y
        Key.Z -> KeyCode.KEY_Z
        Key.OPEN_BRACKET -> KeyCode.OPEN_BRACKET
        Key.BACKSLASH -> KeyCode.BACKSLASH
        Key.CLOSE_BRACKET -> KeyCode.CLOSE_BRACKET
        //Key.N0 -> KeyCode.NUMPAD_0
        //Key.N1 -> KeyCode.NUMPAD_1
        //Key.N2 -> KeyCode.NUMPAD_2
        //Key.N3 -> KeyCode.NUMPAD_3
        //Key.N4 -> KeyCode.NUMPAD_4
        //Key.N5 -> KeyCode.NUMPAD_5
        //Key.N6 -> KeyCode.NUMPAD_6
        //Key.N7 -> KeyCode.NUMPAD_7
        //Key.N8 -> KeyCode.NUMPAD_8
        //Key.N9 -> KeyCode.NUMPAD_9
        Key.KP_MULTIPLY -> KeyCode.MULTIPLY
        Key.KP_ADD -> KeyCode.ADD
        Key.KP_SEPARATOR -> KeyCode.SEPARATOR
        Key.KP_SUBTRACT -> KeyCode.SUBTRACT
        Key.KP_DECIMAL -> KeyCode.DECIMAL
        Key.KP_DIVIDE -> KeyCode.DIVIDE
        Key.DELETE -> KeyCode.DELETE
        Key.NUM_LOCK -> KeyCode.NUM_LOCK
        Key.SCROLL_LOCK -> KeyCode.SCROLL_LOCK
        Key.F1 -> KeyCode.F1
        Key.F2 -> KeyCode.F2
        Key.F3 -> KeyCode.F3
        Key.F4 -> KeyCode.F4
        Key.F5 -> KeyCode.F5
        Key.F6 -> KeyCode.F6
        Key.F7 -> KeyCode.F7
        Key.F8 -> KeyCode.F8
        Key.F9 -> KeyCode.F9
        Key.F10 -> KeyCode.F10
        Key.F11 -> KeyCode.F11
        Key.F12 -> KeyCode.F12
        Key.F13 -> KeyCode.F13
        Key.F14 -> KeyCode.F14
        Key.F15 -> KeyCode.F15
        Key.F16 -> KeyCode.F16
        Key.F17 -> KeyCode.F17
        Key.F18 -> KeyCode.F18
        Key.F19 -> KeyCode.F19
        Key.F20 -> KeyCode.F20
        Key.F21 -> KeyCode.F21
        Key.F22 -> KeyCode.F22
        Key.F23 -> KeyCode.F23
        Key.F24 -> KeyCode.F24
        Key.PRINT_SCREEN -> KeyCode.PRINT_SCREEN
        Key.INSERT -> KeyCode.INSERT
        Key.HELP -> KeyCode.HELP
        Key.META -> KeyCode.META
        Key.BACKQUOTE -> KeyCode.BACK_QUOTE
        Key.APOSTROPHE -> KeyCode.APOSTROPHE
        Key.KP_UP -> KeyCode.KP_UP
        Key.KP_DOWN -> KeyCode.KP_DOWN
        Key.KP_LEFT -> KeyCode.KP_LEFT
        Key.KP_RIGHT -> KeyCode.KP_RIGHT
        Key.GRAVE -> KeyCode.DEAD_GRAVE
        //Key.DEAD_ACUTE -> KeyCode.DEAD_ACUTE
        //Key.DEAD_CIRCUMFLEX -> KeyCode.DEAD_CIRCUMFLEX
        //Key.DEAD_TILDE -> KeyCode.DEAD_TILDE
        //Key.DEAD_MACRON -> KeyCode.DEAD_MACRON
        //Key.DEAD_BREVE -> KeyCode.DEAD_BREVE
        //Key.DEAD_ABOVEDOT -> KeyCode.DEAD_ABOVEDOT
        //Key.DEAD_DIAERESIS -> KeyCode.DEAD_DIAERESIS
        //Key.DEAD_ABOVERING -> KeyCode.DEAD_ABOVERING
        //Key.DEAD_DOUBLEACUTE -> KeyCode.DEAD_DOUBLEACUTE
        //Key.DEAD_CARON -> KeyCode.DEAD_CARON
        //Key.DEAD_CEDILLA -> KeyCode.DEAD_CEDILLA
        //Key.DEAD_OGONEK -> KeyCode.DEAD_OGONEK
        //Key.DEAD_IOTA -> KeyCode.DEAD_IOTA
        //Key.DEAD_VOICED_SOUND -> KeyCode.DEAD_VOICED_SOUND
        //Key.DEAD_SEMIVOICED_SOUND -> KeyCode.DEAD_SEMIVOICED_SOUND
        //Key.AMPERSAND -> KeyCode.AMPERSAND
        //Key.ASTERISK -> KeyCode.ASTERISK
        Key.QUOTE -> KeyCode.QUOTE
        //Key.LESS -> KeyCode.LESS
        //Key.GREATER -> KeyCode.GREATER
        //Key.BRACE_LEFT -> KeyCode.BRACE_LEFT
        //Key.BRACE_RIGHT -> KeyCode.BRACE_RIGHT
        Key.AT -> KeyCode.AT
        //Key.COLON -> KeyCode.COLON
        //Key.CIRCUMFLEX -> KeyCode.CIRCUMFLEX
        //Key.DOLLAR -> KeyCode.DOLLAR
        //Key.EURO_SIGN -> KeyCode.EURO_SIGN
        //Key.EXCLAMATION_MARK -> KeyCode.EXCLAMATION_MARK
        //Key.INVERTED_EXCLAMATION_MARK -> KeyCode.INVERTED_EXCLAMATION_MARK
        //Key.LEFT_PARENTHESIS -> KeyCode.LEFT_PARENTHESIS
        //Key.NUMBER_SIGN -> KeyCode.NUMBER_SIGN
        Key.PLUS -> KeyCode.PLUS
        //Key.RIGHT_PARENTHESIS -> KeyCode.RIGHT_PARENTHESIS
        //Key.UNDERSCORE -> KeyCode.UNDERSCORE
        //Key.WINDOWS -> KeyCode.WINDOWS
        //Key.CONTEXT_MENU -> KeyCode.CONTEXT_MENU
        Key.CUT -> KeyCode.CUT
        Key.COPY -> KeyCode.COPY
        Key.PASTE -> KeyCode.PASTE
        //Key.UNDO -> KeyCode.UNDO
        //Key.AGAIN -> KeyCode.AGAIN
        //Key.FIND -> KeyCode.FIND
        //Key.PROPS -> KeyCode.PROPS
        //Key.STOP -> KeyCode.STOP
        //Key.COMPOSE -> KeyCode.COMPOSE
        //Key.ALT_GRAPH -> KeyCode.ALT_GRAPH
        //Key.BEGIN -> KeyCode.BEGIN
        else -> KeyCode.UNKNOWN
    }
}