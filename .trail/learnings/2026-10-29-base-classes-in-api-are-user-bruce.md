---
title: "Base* classes in api/ are user extension points; BasePosition subclasses supply create(), GameArea/TileGraphics/Renderer bases stay internal"
author: Bruce
date: 2026-10-29
branch: master
cost: { minutes: 33, tokens: 74565 }
id: l-d8649038
scope: repo
status: active
src: agent
touches:
  - README.md
  - ROADMAP.md
  - zircon.core/api/zircon.core.api
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/application/AppConfig.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/application/RenderData.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/application/AppConfigBuilder.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/modifier/Modifier.kt
---

## Decided
The `Base*` abstract classes exist so users can write their own implementations of Zircon interfaces. The ones whose supertypes are all public live in `api/.../base/`: `BaseView`, `BaseComponentBuilder`, `BaseContainerBuilder`, `BasePalette`, and (moved back 2026-10-29) `BasePosition`, `BaseSize` (`zircon.core/.../api/data/base/`) and `BaseTilesetResource` (`api/resource/base/`). The Oct 28 "Enforce the public API" commit had swept those three into `internal/`, which made `PixelPosition` (public) extend an internal-package class.

`BasePosition` now has `protected abstract fun create(x, y)`; `plus`/`minus` call it. Before, they switched on `is PixelPosition` / `is GridPosition` and threw for any other subclass, so it was not actually extensible.

## Tried and ruled out
Promoting `BaseGameArea`, `BaseTileGraphics`, `BaseRenderer` to `api/` as well: they implement `InternalGameArea`, `InternalTileGraphics`, `Renderer`, which are internal, and the grill rule is "no internal types in public signatures". Making them extension points means first deciding to publish those interfaces. Left in `internal/` for now.

## Watch out
`BaseView` must stay public: `DefaultViewContainer.dock` does `require(view is BaseView)`. Still one api-extends-internal leak in the BCV dump: `HorizontalTabBarBuilder` / `VerticalTabBarBuilder` extend `internal.fragment.impl.TabBarBuilder` (dump lines ~1413, ~1552). Not touched.
