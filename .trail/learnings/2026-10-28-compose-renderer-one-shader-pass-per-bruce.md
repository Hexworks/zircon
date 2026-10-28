---
title: "Compose renderer: one shader pass per atlas, sprite vs glyph mode in the foreground alpha, transparent means keep what is below"
author: Bruce
date: 2026-10-28
branch: master
cost: { minutes: 95, tokens: 849329 }
id: l-0011a670
scope: repo
status: active
src: agent
touches:
  - zircon.compose/src/commonMain/kotlin/org/hexworks/zircon/compose/FrameData.kt
  - zircon.compose/src/commonMain/kotlin/org/hexworks/zircon/compose/TileGridView.kt
  - zircon.compose/src/commonMain/kotlin/org/hexworks/zircon/compose/shader/TileShader.kt
  - zircon.compose/src/commonMain/kotlin/org/hexworks/zircon/compose/tileset/CP437Tileset.kt
  - zircon.compose/src/commonMain/kotlin/org/hexworks/zircon/compose/tileset/ComposeTileset.kt
  - zircon.compose/src/commonMain/kotlin/org/hexworks/zircon/compose/tileset/GraphicalTileset.kt
  - zircon.compose/src/commonMain/kotlin/org/hexworks/zircon/compose/tileset/TilesetRegistry.kt
  - zircon.compose/src/commonMain/kotlin/org/hexworks/zircon/compose/tileset/TrueTypeTileset.kt
  - zircon.compose/src/jvmTest/kotlin/org/hexworks/zircon/compose/GoldenRenderTest.kt
  - zircon.compose/src/jvmTest/kotlin/org/hexworks/zircon/compose/OffscreenRendering.kt
---

## Decided
- `zircon.compose` keeps core's `BaseRenderer` as the tick; a `ComposeTileset.drawTile` writes a cell record into `FrameData` (`zircon.compose/src/commonMain/kotlin/org/hexworks/zircon/compose/FrameData.kt`), and `TileShader` (`skikoMain/.../shader/TileShader.skiko.kt`) draws one background pass plus one pass per atlas the frame used. Several tilesets per frame cost one extra `drawRect` each, not a texture merge.
- Sprite sheets with their own colours (graphical tilesets) and tinted masks (CP437, TrueType) share the shader: the foreground alpha is the mode (255 = tint, 128 = raw texel, 0 = nothing on top). Data images are uploaded UNPREMUL so the alpha survives sampling.
- `FrameData.writeGlyph` treats a transparent foreground or background as "keep what is below"; tiles arrive bottom to top from `BaseRenderer`, so this is how layers and component backgrounds show through. Writing alpha 0 instead (the first version) made headers render on a transparent hole.
- TrueType glyphs are rasterized on demand into a 32x32-cell `ImageBitmap` with `TextMeasurer`/`CanvasDrawScope` (common code); the atlas `version` bumps per new glyph and `TileShader` re-wraps the Skia image only on version change.

## Tried and ruled out
- Re-wrapping every atlas with `Image.makeFromBitmap` on every pass: copies the sheet each frame; cached per atlas id + version instead.

## Watch out
- Golden tests (`zircon.compose/src/jvmTest/.../OffscreenRendering.kt`) render through the real pipeline into a raster `Surface`; PNG viewers show transparent pixels as white, so a "white bar" in a golden usually means nothing was drawn there.
- Compose resources of a library are bundled into dependants automatically, but a wasm app module still has to declare `compose.runtime/foundation/ui` itself; `api(project(":zircon.compose"))` does not carry them.
