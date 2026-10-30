---
title: "Compose renderer scales a tile's own tileset slot into the grid cell; the grid cursor is drawn by BaseRenderer except in the terminal; the yayo sheet is 13x13"
author: Bruce
date: 2026-10-30
branch: master
cost: { minutes: 15, tokens: 137417 }
id: l-4bc0ef9d
scope: repo
status: active
src: agent
touches:
  - ROADMAP.md
  - zircon.examples/src/commonMain/kotlin/org/hexworks/zircon/examples/Catalog.kt
  - zircon.examples/src/commonMain/kotlin/org/hexworks/zircon/examples/modifiers/ModifiersExample.kt
  - zircon.examples/src/commonMain/kotlin/org/hexworks/zircon/examples/other/ColorInterpolationExample.kt
  - zircon.examples/src/commonMain/kotlin/org/hexworks/zircon/examples/other/MultiTileExample.kt
  - zircon.examples/src/commonMain/kotlin/org/hexworks/zircon/examples/other/OtherExamples.kt
  - zircon.examples/src/commonMain/kotlin/org/hexworks/zircon/examples/other/TrueTypeFontExample.kt
  - zircon.compose/src/commonMain/kotlin/org/hexworks/zircon/compose/tileset/TrueTypeTileset.kt
  - zircon.compose/src/commonMain/composeResources/files/cp_437_tilesets/yayo_13x13.png
---

## Decided
Fixes after the 2026-10-30 examples review:
- `TileShader.skiko.kt` samples `uv = (slot + inTile) * cellSize`, so a tile drawn with a tileset whose cell size differs from the grid's is scaled into the grid cell (a 20x20 glyph on a 16x16 grid shows at 16x16). Only containers reject a child with a different cell size (`DefaultContainer` → `TilesetResource.checkCompatibilityWith`); layers were never checked, in 1.x either. The "Multi tile" example therefore picks CP437 sheets of the selected tileset's size from `TILESETS`; the "TrueType" example uses the six square fonts, whose glyphs still fill the cell differently because the fonts have different native aspect ratios.
- `TrueTypeTileset` shrinks the font until a probe glyph ("W") and its line height fit the atlas slot, and clips every glyph to its slot: pixel fonts have a line height above the em size, and without the clip glyphs bled into neighbouring slots (the mangled "vtech narrow").
- `BaseRenderer` draws the grid cursor: the topmost tile of the cursor cell with colours swapped, or a block in the default foreground on an empty cell. `drawsCursor` is open; `TerminalRenderer` sets it false because `AnsiEncoder` positions the terminal's own cursor.
- `Scrollable` scroll helpers (`ScrollableExtensions.kt`) clamp to `0..actualSize - visibleSize`; the offset property's validator rejects anything negative with a `ValueValidationFailedException`, which is what the arrow keys in the game examples hit.
- Colour math in `ColorExtensions.kt` returns a transparent colour unchanged: `darkenByPercent` on `Color.TRANSPARENT` used to produce opaque black, which is why fades showed a black background.
- `ComponentRenderer.rendersOnEveryFrame` (default false) feeds `DefaultComponentRenderingStrategy.rendersOnEveryFrame`; `GameAreaComponentRenderer` sets it true, since the game area scrolls without the component's own inputs changing (the U/D keys in "Oblique world" only showed after a hover re-render).
- `yayo_12x12.png` is a 208x208 sheet, 13x13 cells, in 1.x too; renamed to `yayo_13x13.png`, `YAYO_13X13`, `CP437TilesetResources.yayo13x13()`. `nagidal_16x16.png` was a stale 24x24 sheet no resource referenced and is gone. The examples' `TILESETS` leaves out anything above 24x24: the 100x40 minimum grid does not fit a window at 32x32.

## Tried and ruled out
Drawing mismatched tilesets at native pixel size (what the 1.x Swing renderer did): the shader would have to clip or overflow the cell; scaling keeps every tileset usable on any grid. Rejecting layer tilesets that differ from the grid's: not done, no 1.x precedent and it would break the TrueType example's one-layer-per-font layout.

## Watch out
The "Stacked tiles" example is gone: the 2.0 renderer has no per-tile alpha, so only the opaque top of a stack shows. `ROADMAP.md` has the pre-rendered stacked tiles entry.
