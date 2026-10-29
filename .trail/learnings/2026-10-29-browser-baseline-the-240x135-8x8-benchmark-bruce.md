---
title: "Browser baseline: the 240x135 8x8 benchmark demo runs at 32-37 fps on wasmJs after the render-path fixes"
author: Bruce
date: 2026-10-29
branch: master
cost: { minutes: 1, tokens: 4403 }
id: l-66e25b42
scope: repo
status: active
src: agent
touches:
  - zircon.compose/src/commonMain/kotlin/org/hexworks/zircon/compose/FrameData.kt
  - zircon.compose/src/commonMain/kotlin/org/hexworks/zircon/compose/tileset/TilesetRegistry.kt
  - zircon.core/api/zircon.core.api
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/component/renderer/ComponentRenderingStrategy.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/fragment/TabBar.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/InternalComponent.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/impl/BaseNumberInput.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/impl/BaseSlider.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/impl/DefaultComponent.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/impl/DefaultIcon.kt
---

## Decided
- Measured by the user in the browser on 2026-10-29 at commit 9fda6e682: the Benchmark tab (full HD, 8x8 tiles, 240x135 cells, every cell rewritten each frame, Screen with tab bar) runs at 32-37 fps on wasmJs. Was 7 fps before the component render cache and the per-tile fixes. Accepted as good enough for 2.0; no further renderer work planned.

## Watch out
- Use this scene as the regression check for the render path; the old 120x60 "60 fps" was vsync-capped and tells nothing. Remaining levers if ever needed: per-row renderable culling in `BaseRenderer.renderAllTiles`, and the three per-frame byte copies into Skiko in `TileShader.draw` (380 KB at this size).
