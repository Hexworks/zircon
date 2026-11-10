---
title: Graphics Box interface removed; boxes are drawn with a drawBox function instead of a TileGraphics subtype
author: Bruce
date: 2026-11-10
branch: master
cost: { minutes: 7, tokens: 63789 }
id: l-7e805def
scope: repo
status: active
src: agent
touches:
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/graphics/extensions/DrawSurfaceExtensions.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/renderer/decoration/BoxDecorationRenderer.kt
---

## Decided
2026-11-10. `Box : TileGraphics` with a `boxType` field was a type nobody read: its one production caller built a throwaway `TileGraphics` just to copy it into a `DrawWindow`. Replaced by plain functions: `boxTiles(boxType, style, size)` builds the border tiles, `DrawSurface.drawBox` and `DrawWindow.drawBox` draw them at a position with a size (whole surface by default). Interior is left untouched, so `fill` after `drawBox` fills the inside.

## Tried and ruled out
A single `DrawSurface.drawBox`: `DrawWindow` is not a `DrawSurface` (it is a `TileComposite` that duplicates the draw methods), so the decoration renderer could not call it. Hence two thin receivers over one tile-map builder.

## Watch out
`ComponentDecorations.box(...)` (used as `decorations { +box(...) }`) is a different function and still exists; only the graphics-level `box {}` builder, `Box`, `DefaultBox` and `BoxBuilder` are gone.

In the code:
- `zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/graphics/extensions/DrawSurfaceExtensions.kt`: `drawBox`, `boxTiles`
- `zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/renderer/decoration/BoxDecorationRenderer.kt`: `drawWindow.drawBox(boxType = boxType, style = style)`
- test: `zircon.core/src/commonTest/kotlin/org/hexworks/zircon/api/graphics/extensions/DrawBoxTest.kt`
