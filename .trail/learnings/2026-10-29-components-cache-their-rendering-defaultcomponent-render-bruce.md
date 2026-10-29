---
title: Components cache their rendering; DefaultComponent.render() redraws only after invalidateRender()
author: Bruce
date: 2026-10-29
branch: master
cost: { minutes: 15, tokens: 124662 }
id: l-2063a361
scope: repo
status: active
src: agent
---

## Decided
- `DefaultComponent.render()` (zircon.core `internal/component/impl/DefaultComponent.kt`) keeps one `FastTileGraphics` and returns it until something invalidates it. Before, `BaseRenderer.fetchLayers()` made every component allocate and redraw its graphics on every frame: on a 240x135 Screen with a tab bar that was 6.9 ms of an 11.3 ms frame on the JVM; the bare grid renders in 2.3 ms. After: 3.2 ms. A static component screen went from 4.3 ms to 1.1 ms per frame.
- Invalidation comes from three places: hooks on the observables renderers read (state, theme, style, tileset, hidden, disabled, focus, bounds, plus `TextOverride.textProperty`, `TitleOverride.titleProperty`, `Selectable.selectedProperty`, `Scrollable` offsets and a per-subclass `renderInputs` list: progress, icon, slider/number values, the scroll bar's target `Scrollable`); `UIEventToComponentDispatcher.executePhase`, which calls `invalidateRender()` whenever a component answers anything but `Pass`; and explicit calls (`DefaultTextArea.text` setter).
- Hooks are installed lazily on the first `render()`, not in the base `init`, because the subclasses' delegated properties (`TextOverride by ...`) are not initialised when the base class constructor runs.
- `ComponentRenderingStrategy.rendersOnEveryFrame` opts a component out of the cache; the default strategy sets it when it has post processors (`TypingEffectPostProcessor` is time based).

## Watch out
- A custom component whose renderer reads state that is not one of the hooked observables must call `invalidateRender()` (on `InternalComponent`) when that state changes, or override `renderInputs`. Symptom otherwise: the component never visually updates.
- `ComponentRenderCacheTest` (core commonTest) pins the behaviour with a counting renderer; `shouldNotBe` on `TileGraphics` is useless here since the cached object is reused and equality is by content.
