---
title: Modal backdrop is a static capture of the screen (TileGrid.captureFrame) darkened in core; grid layers render above components
author: Bruce
date: 2026-10-31
branch: master
cost: { minutes: 44, tokens: 191905 }
id: l-e4156dab
scope: repo
status: active
src: agent
touches:
  - TODO.md
---

## Decided
Grill 2026-10-31 on the modal backdrop (2.0 has no alpha, so the old black filler hid everything below a dialog):
- `TileGridScreen.openModal` captures the screen with `TileGrid.captureFrame()` (new public API, implemented in `DefaultTileGrid`), crops it to the modal's bounds, darkens it by `darkenPercent` (`internal/component/modal/ModalBackdrop.kt`) and stores it in `DefaultModal.backdrop`; `DefaultModalRenderer` draws that instead of a filler.
- The capture is static: things under a modal stop animating while it is open. Accepted, matches "screenshot".
- Nested modals compound: each level captures the already darkened view, so darkness accumulates and closing one restores the previous view with no bookkeeping.
- `captureFrame()` flattens all `renderables` (components and plain layers) with the renderer's top-down stop-at-opaque rule. The walk lives in `internal/graphics/RenderLayers.kt` (`toRenderLayers`, inline `forEachVisibleTile`) and is shared with `BaseRenderer`, so the capture and the frame agree by construction.
- `CharacterTile` got an optional `tileset` (default `TilesetResource.Companion.UNKNOWN`) and implements `TilesetHolder`, so a captured tile from a layer with another tileset keeps its look. `BaseRenderer` honours it via `Tile.tilesetOrElse(layerTileset)`. Only `CharacterTile` is darkened; graphical/image tiles have no colours in core and stay as they are.
- Default `darkenPercent` stays 0.0 (same as 1.x; `DefaultMenuBar` relies on it).

## Tried and ruled out
- Live dimming per frame in the renderer/shader (the TODO.md "dim lower layers in the M4 shader" note): touches the per-cell hot path and both renderers for a feature that only needs a snapshot.
- A new sealed `Tile` subtype carrying a tileset: every tileset does `tile as? CharacterTile`, so it would draw nothing until all of them unwrap it.
- Snapshot as one `TileGraphics` per tileset group: no single image, modal renderer gets complex.

## Watch out
- `ComponentsLayerable.renderables` is `components + layers`, and the renderer draws the list bottom first, so the grid's own base layer and any `addLayer` layer render ABOVE components (and above modals). Tiles drawn straight onto a `Screen` are not covered by a modal backdrop; this was already so in 1.x. Tests that probe what a modal covers must put the content in a component (`ModalBackdropTest`).
- `TilesetResource.UNKNOWN` must be written `TilesetResource.Companion.UNKNOWN` outside its own file (nested object in the companion); `UNKNOWN.id` throws, so `CharacterTile.cacheKey` only appends the tileset id when it is known.
- Only `:zircon.core:jvmTest`, `apiCheck`, `checkApiLeaks` and the jvm compile of compose/terminal/examples were run; js/wasmJs/native tests were not.
