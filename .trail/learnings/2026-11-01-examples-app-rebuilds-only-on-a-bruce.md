---
title: "Examples app rebuilds only on a tile size change; a Screen's tileset does not reach its components, so the app sets the root container's tileset through TileGr…"
author: Bruce
date: 2026-11-01
branch: master
cost: { minutes: 13, tokens: 213772 }
id: l-9146cdd2
scope: repo
status: active
src: agent
---

## Decided
2026-11-01, examples app review. The tileset selector is split in two: a tile *size* selector (`TILE_SIZES` in `zircon.examples/.../Catalog.kt`, CP437 sheet sizes up to 24 px) and a tileset selector listing `tilesetsFor(size)`. Only a size change forces a new grid (cell count changes); the launcher (`ExamplesApp.kt`) watches `ExamplesScreen.tileSizeProperty`, rebuilds, and passes the old screen as `previous` so tileset, theme and the selected example carry over. Tileset and theme changes within a size are applied in place.

Launcher-facing API of `ExamplesScreen` is `grid`, `onFrame`, `tileSizeProperty`. The test hooks (`tabBar`, `packages`, `buildFailures`, `runtimeErrors`) are behind `@ExamplesTestApi` (a `@RequiresOptIn` marker in `ExamplesTestApi.kt`): tests in the compose/terminal modules opt in, launchers cannot use them by accident. `internal` was not enough because those tests live in other modules.

`SelectorBuilder.build()` falls back to the first value when neither `defaultSelected` nor `selectedProperty` is set (core), as it did before `selectedProperty` became a shared `lateinit` property.

## Tried and ruled out
Keying the rebuild on tile *height* alone: the TTF narrow/wide fonts and the 8x16 sheets share a height with 16x16 ones but change the column count and the shader's cell size (`TileGridView` remembers `TileShader(tileset.width, tileset.height)` per grid). Size is the key.

Applying a tileset with `screen.tileset = x` only: `Screen` gets `tileset` from its buffer grid (base layer + cursor); the component tree hangs off a separate `tilesetProperty` created in `TileGridScreen.buildComponentContainer`, and `ComponentContainer` has no tileset counterpart to its synchronized `theme`. Components did not change.

## Watch out
`examplesScreen` reaches the component tree with `(screen as TileGridScreen).root.tileset = x` (`TileGridScreen.root` is public on an internal class). The clean fix is a core one: give `ComponentContainer` a tileset synchronized with its children the way `theme` is, or bind the root container's tileset to the grid's. Until then this cast is the only way to re-tileset a displayed screen without rebuilding it.

Layers built with `layer {}` default to `ZirconDefaults.current.tileset`, not the screen's; the app's fps and error layers are built with the screen's tileset and follow `tilesetProperty`. Example layers (REX files, the skull animation) keep the tileset they were loaded with, scaled into the cell.

`produceState` keeps its state object across key changes, so inside the producer `value` is the previous screen; `ExamplesApp` relies on that for the carry-over.
