---
title: A component that swallows MOUSE_EXITED during a captured drag must apply it itself on release; the dispatcher never re-sends it
author: Bruce
date: 2026-10-31
branch: master
cost: { minutes: 8, tokens: 32522 }
id: l-30f23f54
scope: repo
status: active
src: agent
touches:
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/impl/BaseSlider.kt
  - zircon.core/src/commonTest/kotlin/org/hexworks/zircon/internal/uievent/impl/UIEventToComponentDispatcherTest.kt
  - zircon.core/api/zircon.core.api
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/impl/DefaultVerticalScrollBar.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/uievent/impl/UIEventToComponentDispatcher.kt
---

## Decided
`UIEventToComponentDispatcher.resyncHoverAfterRelease` only sends `MOUSE_EXITED` to the captured component when the cursor is still geometrically inside it at release (released over an overlapping component). When the cursor already left, the exit was delivered during the drag (`syncCapturedBounds`) and is not repeated. So a component that ignores that mid-drag exit, as `BaseSlider` does to stay `ACTIVE` while tracking, owns the debt: `BaseSlider` records it in `exitedWhileDragging` and applies `super.mouseExited` from `mouseReleased`. Without that, `(ACTIVE, focused, MOUSE_RELEASED) -> HIGHLIGHTED` in `DefaultComponent.COMPONENT_STATE_TRANSITIONS` leaves the slider in the hover colour while the button under the cursor is also highlighted (seen 2026-10-31 in the examples app).

## Tried and ruled out
- Re-sending `MOUSE_EXITED` from the dispatcher on every release outside the captured bounds: the component already got it once, and Compose does not repeat boundary events either; it would double-fire for components that handled it the first time.

## Watch out
- Any future draggable that overrides `mouseExited` to keep its state during a capture (scroll bar thumbs, split handles) needs the same release-time catch-up.
