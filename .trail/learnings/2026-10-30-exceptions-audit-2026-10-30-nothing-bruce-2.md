---
title: "Exceptions audit 2026-10-30: nothing between a listener/animation/renderer throw and the frame loop caught, so any require() killed the app; where the throw si…"
author: Bruce
date: 2026-10-30
branch: master
cost: { minutes: 17, tokens: 187278 }
id: l-493cac1f
scope: repo
status: active
replaces: l-5e2e7fed
src: agent
touches:
  - zircon.examples.compose/src/jvmTest/kotlin/org/hexworks/zircon/examples/compose/ExamplesAppTest.kt
  - zircon.examples.compose/src/jvmTest/kotlin/org/hexworks/zircon/examples/compose/PackageTabClickTest.kt
  - zircon.examples.terminal/src/commonMain/kotlin/main.kt
  - zircon.examples.terminal/src/jvmTest/kotlin/TerminalExamplesTest.kt
  - zircon.examples/src/commonMain/kotlin/org/hexworks/zircon/examples/ExamplesScreen.kt
  - zircon.examples/src/commonMain/kotlin/org/hexworks/zircon/examples/components/HBoxesExample.kt
  - zircon.examples/src/commonMain/kotlin/org/hexworks/zircon/examples/components/VBoxesExample.kt
  - zircon.examples/src/commonMain/kotlin/org/hexworks/zircon/examples/game/ScrollingExample.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/graphics/TileGraphicsBuilder.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/behavior/impl/DefaultComponentFocusOrderList.kt
---

## Decided
Audit of every throw site in core, compose, terminal and the examples (2026-10-30), before the exception-handling policy was decided and implemented (see the policy learning). Propagation paths found: Compose `TileGridView.kt` ran `renderer.render(frame)` inside `withFrameNanos` in a `LaunchedEffect` with no catch, so a throw in a UI listener (`DefaultUIEventProcessor`), an animation tick (`DefaultAnimationRunner.updateAnimations`), a component renderer or a tileset `drawTile` failed the coroutine and the app died. Terminal `RunTerminal.run()` catches Throwable only to restore the tty, then rethrows. Core had one catch in total (`Color.fromString`). The examples app wrapped only `example.build(context)` in `ExamplesScreen.show`.

Runtime-reachable throws (the ones that crashed a running app): `DefaultContainer.checkIfCanAdd` (out of bounds / overlap, gated on `ZirconDefaults.shouldCheckBounds()`), `DefaultComponent.moveTo` out of parent, `DefaultHBox/DefaultVBox.checkAvailableSpace`, `DefaultLogArea.addLogElement` (element taller than area), `DefaultSize` init (any Size arithmetic going negative), `ComponentMetadata` init (negative position, e.g. CENTER alignment of a child bigger than its target), `Scrollable3DExtensions.scrollTo`, `TilesetResource.checkCompatibilityWith` (called from `DefaultContainer` on addComponent), `TextTileset.drawTile` (non-character tile in the terminal), `TilesetRegistry`/`DefaultTilesetLoader` (no factory for a tile type), tab bar `lookup.getValue(key)`, `DefaultComponentFocusOrderList.findNext/findPrevious` (`getValue` on a component focused since the last refresh). Examples that hit these from input or frame ticks: `MoveExample`, `HBoxesExample`/`VBoxesExample` (add buttons until the box is full).

Unknown-symbol path: `Char.CP437Index()` in `internal/util/CP437.kt` throws by design (now internal); the compose `CP437Tileset` substitutes `?` via `CP437IndexOrNull`. The terminal `cellCodeOf` maps wide/control chars to space or U+FFFD, no throw.

## Tried and ruled out
Drawing out of bounds does not throw: `FastTileGraphics.draw`, `HashMapTileGraphics.draw`, `DefaultLayer.drawAbsoluteTileAt` all clip, and layer `moveTo` goes through `DefaultMovable` unchecked. The out-of-bounds crashes came from the component tree, not from tile drawing. `SetiUiGlyphs.TODO(0xE69C)` looked like a `kotlin.TODO()` call in a grep but is an enum constant named TODO: not a throw site.

## Watch out
A bounds check inside a Kotlin property setter must read `value`, not the backing property: reading the property inside its own setter validates the previous value (`TileGraphicsBuilder.tiles` was written this way; the check now runs in `build()` against the final size). Colour math could push a channel past 255 and `CellBuffer.pack` shifts without masking, so `DefaultColor` clamps every channel on construction; keep it that way for any new Color implementation.
