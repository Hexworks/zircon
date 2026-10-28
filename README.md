# Zircon - A user-friendly Tile Engine & Text GUI

Zircon is a Kotlin Multiplatform library for games and tools drawn on a grid of tiles:
roguelikes, text-mode UIs, ASCII art. It gives you a tile grid, layers, animations, a
component system (buttons, text areas, tables, menus, modals...) and a renderer built on
Compose Multiplatform that runs on the desktop and in the browser.

Need info? Check the [Docs][zircon-docs]
| [Create an issue](https://github.com/Hexworks/zircon/issues/new)
| [Ask us on Discord][discord]
| Support us on [Patreon](https://www.patreon.com/hexworks)

[![Maven Central](https://img.shields.io/maven-central/v/org.hexworks.zircon/zircon.core)](https://central.sonatype.com/artifact/org.hexworks.zircon/zircon.core)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

---

## Table of Contents

- [What's new in 2.0](#whats-new-in-20)
- [Getting Started](#getting-started)
    - [Adding Zircon as a dependency](#adding-zircon-as-a-dependency)
    - [A first window](#a-first-window)
    - [Embedding the grid in your own Compose UI](#embedding-the-grid-in-your-own-compose-ui)
- [Features at a Glance](#features-at-a-glance)
    - [Drawing](#drawing)
    - [Components](#components)
    - [Input handling](#input-handling)
    - [Layers and animations](#layers-and-animations)
    - [Tilesets, fonts and REXPaint files](#tilesets-fonts-and-rexpaint-files)
- [Modules and targets](#modules-and-targets)
- [Building and running the examples](#building-and-running-the-examples)
- [Road Map](#road-map)
- [License](#license)
- [Credits](#credits)
- [Thanks](#thanks)

## What's new in 2.0

2.0 is a clean break from the 2021 line:

- **One renderer, Compose Multiplatform.** Swing, LibGDX and KorGE are gone. The grid is a
  composable (`TileGridView`) you drop into any Compose tree, or `runDesktop(grid)` for a
  single window. It draws the whole grid with one shader pass per tileset, so even a
  1080p screen of 8x8 tiles redraws at display rate on desktop and in the browser.
- **Kotlin DSL everywhere.** `button { +"OK" }`, `vBox { ... }`, `appConfig { ... }`,
  `Position(x, y)`, `Size(w, h)`. The Java-style builders, `newBuilder()`, `withX()`
  setters and `Shapes`/`Modifiers` factory objects are gone, and so is Java support.
- **Explicit, checked API.** `zircon.core` compiles in Kotlin explicit API mode, its binary
  API is tracked (`zircon.core/api/zircon.core.api`), and nothing from the `internal`
  package appears in public signatures.
- **Fewer dependencies.** Core depends on cobalt, kotlinx-collections-immutable,
  kotlinx-coroutines and kotlinx-serialization only. Zip, gzip and REXPaint parsing are
  built in and work on every target.
- **JSON metadata.** Graphical tilesets carry `tileset.json`, animations `animation.json`
  (see [ANIMATIONS.md](ANIMATIONS.md)). YAML is gone.
- **No alpha channel.** A `Color` is opaque or `Color.TRANSPARENT`; layering still works,
  blending does not exist, and the renderer stays fast.

## Getting Started

### Adding Zircon as a dependency

```kotlin
// build.gradle.kts of a Compose Multiplatform project
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("org.hexworks.zircon:zircon.core:2.0.0")
            implementation("org.hexworks.zircon:zircon.compose:2.0.0")
        }
    }
}
```

`zircon.core` publishes for the JVM, JS, Wasm, iOS, macOS, Linux and Windows.
`zircon.compose` publishes for the JVM (desktop) and `wasmJs` (browser); it needs the
Compose Multiplatform Gradle plugin in your project, exactly like any other Compose library.

### A first window

```kotlin
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.builder.application.appConfig
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.header
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.createTileGrid
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.compose.runDesktop

fun main() {
    val grid = createTileGrid(
        appConfig {
            size = Size(60, 30)
            defaultTileset = CP437TilesetResources.wanderlust16x16()
            title = "Hello, Zircon"
        }
    )
    val screen = grid.toScreen()
    screen.theme = ColorThemes.arc()

    val panel = vBox { preferredSize = Size(60, 30) }
    screen.addComponent(panel)
    panel.addComponent(header { +"Hello, Zircon!" })
    panel.addComponent(button { +"Click me" }.apply {
        handleComponentEvents(ComponentEventType.ACTIVATED) {
            println("Clicked!")
            Processed
        }
    })
    screen.display()

    runDesktop(grid)
}
```

Inside a container lambda the same names add children, so the panel above could also be
written as

```kotlin
val panel = vBox {
    preferredSize = Size(60, 30)
    header { +"Hello, Zircon!" }
    button { +"Click me" }
}
```

### Embedding the grid in your own Compose UI

`TileGridView` is a regular composable: it measures itself to the grid's pixel size, runs
the frame loop and feeds keyboard and pointer input to the grid.

```kotlin
@Composable
fun GameScreen(grid: TileGrid) {
    Column {
        TopBar()
        TileGridView(grid, beforeFrame = { game.update() })
    }
}
```

In the browser, put it inside `ComposeViewport`; on the desktop, inside a `Window`. The
examples do both.

## Features at a Glance

### Drawing

A `TileGrid` is a `DrawSurface`: draw tiles, tile graphics, shapes and REXPaint files
onto it, or onto `Layer`s stacked above it.

```kotlin
grid.draw(
    characterTile {
        character = '@'
        withStyleSet {
            foregroundColor = Color.fromString("#ffcc66")
            backgroundColor = Color.TRANSPARENT
        }
    },
    Position(10, 5)
)
grid.draw(line(Position(0, 0), Position(20, 10)).toTileGraphics(wallTile, tileset))
```

### Components

Buttons, toggle buttons, check boxes, radio buttons, labels, headers, paragraphs, text
areas, number inputs, sliders, scroll bars, progress bars, panels, `hBox`/`vBox` layouts,
log areas, modals, plus fragments such as tab bars, menu bars, tables and selectors. All
components are themeable (`ColorThemes` ships dozens) and decorated with boxes, shadows
and borders.

```kotlin
val form = vBox {
    preferredSize = Size(30, 10)
    decorations { +box(title = "Settings") }
    checkBox { +"Music" }
    radioButton { +"Easy"; key = "easy" }
    radioButton { +"Hard"; key = "hard" }
    button {
        +"Save"
        activationShortcut = KeyboardEventMatcher(type = KeyboardEventType.KEY_PRESSED, code = KeyCode.F2)
    }
}
```

### Input handling

Keyboard and mouse events arrive as `KeyboardEvent` and `MouseEvent`; subscribe on the
grid, a screen or any component:

```kotlin
grid.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED) { event, _ ->
    if (event.code == KeyCode.ESCAPE) quit()
    Processed
}
```

### Layers and animations

Layers are independent tile graphics drawn above the grid; animations are sequences of
layer frames, loaded from `.zap` archives or built in code:

```kotlin
val skull = loadAnimation(Resource.create("/animations/skull.zap", ResourceType.PROJECT), tileset)
    .apply { loopKind = Animation.InfiniteLoop; position = Position(20, 5) }
    .build()
grid.start(skull)
```

### Tilesets, fonts and REXPaint files

Zircon bundles 55 CP437 tilesets (`CP437TilesetResources`), 14 monospace TrueType fonts
(`TrueTypeFontResources`) and the nethack graphical tileset
(`GraphicalTilesetResources`). Load your own with `loadCP437TilesetResource`,
`loadFontTilesetResource` and `loadGraphicalTilesetResource`, and REXPaint drawings with
`loadREXFile`. A graphical tileset is a zip of sprite sheets plus `tileset.json`; see
`GraphicalTilesetMetadata`.

Resources with `ResourceType.PROJECT` are read from Compose resources. Register your
application's own with `ComposeResourceLoader.addProjectResources { Res.readBytes(it) }`.

## Modules and targets

| Module | Targets | What |
|---|---|---|
| `zircon.core` | jvm, js, wasmJs, iOS, macOS, linuxX64/Arm64, mingwX64 | Grid, layers, components, animations, resources. No rendering. |
| `zircon.compose` | jvm, wasmJs | The Compose Multiplatform renderer: `TileGridView`, `runDesktop`, tilesets, bundled assets. |
| `zircon.examples` | jvm, wasmJs | The demos as common code (`ExamplesApp`). |
| `zircon.examples.desktop`, `zircon.examples.web` | jvm / wasmJs | Launchers for the demos. |

Android is planned for 2.1.

## Building and running the examples

```sh
./gradlew :zircon.examples.desktop:jvmRun                 # desktop demos
./gradlew :zircon.examples.web:wasmJsBrowserDevelopmentRun # demos in your browser
./gradlew check                                           # tests on every host target, API checks
```

The Compose renderer is verified by golden-image tests
(`zircon.compose/src/jvmTest/resources/golden`); run with `UPDATE_GOLDENS=1` after an
intentional rendering change.

## Road Map

Planned after 2.0:

- Android target for `zircon.compose` (2.1)
- Image dictionary tilesets (a folder of PNGs as tiles)
- A more flexible component theming model
- [Floating components](https://github.com/Hexworks/zircon/issues/23),
  [drag'n drop](https://github.com/Hexworks/zircon/issues/22),
  [custom layouts](https://github.com/Hexworks/zircon/issues/28),
  [tree](https://github.com/Hexworks/zircon/issues/184),
  [accordion](https://github.com/Hexworks/zircon/issues/27) and
  [combo box](https://github.com/Hexworks/zircon/issues/262) components

If you'd like to give any of these a shot feel free to *contribute*.

## License

Zircon is made available under the [Apache 2.0 License](https://www.apache.org/licenses/LICENSE-2.0).

## Credits

Zircon is created and maintained by Addamsson, Coldwarrl, G3ldrin, Milonoir, Seveen and many others.

*We're open to suggestions, feel free to message us on [Discord][discord] or open an issue.*
*Pull requests are also welcome!*

Zircon is powered by:

<a href="https://www.jetbrains.com/idea/">
    <img src="https://github.com/Hexworks/zircon/blob/master/images/idea_logo.png" width="40" height="40"  alt="IDEA"/>
</a>
<a href="https://kotlinlang.org/">
    <img src="https://github.com/Hexworks/zircon/blob/master/images/kotlin_logo.png" width="40" height="40"  alt="Kotlin"/>
</a>
<a href="https://www.yourkit.com/java/profiler/">
    <img src="https://github.com/Hexworks/zircon/blob/master/images/yklogo.png" width="168" height="40"  alt="Yourkit"/>
</a>

## Thanks

Thanks to the folks over at [Dwarf Fortress Tileset Repository](http://dwarffortresswiki.org/Tileset_repository) for
letting us bundle their tilesets.

Thanks to *Kyzrati* who let us bundle the [REXPaint Tilesets](https://www.gridsagegames.com/rexpaint/) into Zircon!

Zircon comes bundled with the [Nethack Tileset](https://nethackwiki.com/wiki/Tileset).

Some True Type fonts are used from [Google Fonts](https://fonts.google.com/).

Thanks to *VileR* for the [Oldschool Font Pack](https://int10h.org/oldschool-pc-fonts/) which we bundled into Zircon.


[discord]:https://discord.gg/vSNgvBh
[zircon-docs]:https://hexworks.org/zircon/docs/
