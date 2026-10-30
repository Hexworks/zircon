---
title: "Exceptions audit 2026-10-30: nothing between a listener/animation/renderer throw and the frame loop catches, so any require() kills the app; full site list gro…"
author: Bruce
date: 2026-10-30
branch: master
cost: { minutes: 1, tokens: 3278 }
id: l-5e2e7fed
scope: repo
status: active
src: agent
---

## Decided
Audit of every throw site in core, compose, terminal and the examples (2026-10-30), before deciding how to handle them. Propagation paths: Compose `TileGridView.kt` runs `renderer.render(frame)` inside `withFrameNanos` in a `LaunchedEffect` with no catch, so a throw in a UI listener (`DefaultUIEventProcessor`), an animation tick (`DefaultAnimationRunner.updateAnimations`), a component renderer or a tileset `drawTile` fails the coroutine and the app dies. Terminal `RunTerminal.run()` catches Throwable only to restore the tty, then rethrows. Core has one catch in total (`Color.fromString`). The examples app wraps only `example.build(context)` in `ExamplesScreen.show`; `onShown`, `beforeFrame`, event handlers and the `placeholders.getValue(key).addComponent(...)` right after the try run bare.

Runtime-reachable throws (the ones that crash a running app): `DefaultContainer.checkIfCanAdd` (out of bounds / overlap, gated on `ZirconDefaults.shouldCheckBounds()`), `DefaultComponent.moveTo` out of parent, `DefaultHBox/DefaultVBox.checkAvailableSpace`, `DefaultLogArea.addLogElement` (element taller than area), `DefaultSize` init (any Size arithmetic going negative), `ComponentMetadata` init (negative position, e.g. CENTER alignment of a child bigger than its target), `Scrollable3DExtensions.scrollTo`, `TilesetResource.checkCompatibilityWith` (called from `DefaultContainer` on addComponent), `TextTileset.drawTile` (non-character tile in the terminal), `TilesetRegistry.create`/`DefaultTilesetLoader` (no factory for a tile type), tab bar `lookup.getValue(key)`, `DefaultComponentFocusOrderList.findNext/findPrevious` (`getValue` on the focused id). Examples that hit these from input or frame ticks: `MoveExample` (moveTo every 120 frames), `HBoxesExample`/`VBoxesExample` (add buttons until the box is full), `ScrollingExample` (`ProjectionMode.valueOf(radio.key)`).

Unknown-symbol path: `Char.CP437Index()` in `internal/util/CP437.kt` throws by design; the compose `CP437Tileset` substitutes `?` via `CP437IndexOrNull`, so only direct API callers hit it. `GraphicalTileset` skips unknown sprite names silently. The terminal `cellCodeOf` maps wide/control chars to space or U+FFFD, no throw.

## Tried and ruled out
Drawing out of bounds does not throw: `FastTileGraphics.draw`, `HashMapTileGraphics.draw`, `DefaultLayer.drawAbsoluteTileAt` all clip, and layer `moveTo` goes through `DefaultMovable` unchecked. The out-of-bounds crashes come from the component tree (containers, moveTo, boxes), not from tile drawing.

## Watch out
A bounds check inside a Kotlin property setter must read `value`, not the backing property: reading the property inside its own setter validates the previous value (`TileGraphicsBuilder.tiles` was written this way). `ColorExtensions.lightenByPercent` can push a channel past 255 and `CellBuffer.pack` shifts without masking, so the overflow bleeds into the next channel and `ByteSink.small` indexes its colour table out of range; any colour math feeding the terminal must clamp to 0..255.
