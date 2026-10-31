---
title: "Mouse capture (grill 2026-10-31): the pressed component gets every MOUSE_DRAGGED and the MOUSE_RELEASED until release, mirroring Compose; no hover to others me…"
author: Bruce
date: 2026-10-31
branch: master
cost: { minutes: 17, tokens: 145214 }
id: l-799ea6c5
scope: repo
status: active
src: agent
---

## Decided
Grill 2026-10-31, triggered by the slider stopping as soon as the cursor left its row. `UIEventToComponentDispatcher.dispatch` (zircon.core `internal/uievent/impl/`) used to pick the target by cursor position for every mouse event, so a drag that left the pressed component went to whatever was under the cursor, and the release deactivated that other component (the slider stayed `ACTIVE`).

The dispatcher now does implicit mouse capture, generic for every component (no opt-in): `MOUSE_PRESSED` stores the target in `capturedComponent`; every `MOUSE_DRAGGED` and the `MOUSE_RELEASED` go there via `dispatchCaptured`, whatever the position. Verified against Compose 1.12.2 sources (`PointerInputEventProcessor.kt` hit-tests only when no button is pressed; `HitPathTracker.kt` L587-607 recomputes `isIn` for the pressed node and synthesises Enter/Exit for it) and the model is the same as JavaFX and Qt:
- No `MOUSE_ENTERED`/`MOUSE_EXITED` to any other component while captured.
- The captured component does get `MOUSE_EXITED`/`MOUSE_ENTERED` when the cursor crosses its own bounds (`syncCapturedBounds`), so a button un-highlights when dragged off it.
- On release the capture ends and hover is re-synced to the component under the cursor (`resyncHoverAfterRelease`).
- `MOUSE_CLICKED` stays position-routed (press and release are on the same cell by construction).
- A captured component that is detached mid-drag drops the capture.
Out-of-grid positions are passed through raw (Compose does the same); components clamp. `BaseSlider.setValueToClosestOfStep` already clamps the step, `BaseSlider` overrides `mouseExited`/`mouseEntered` to `Pass` while `isDragging` so it stays `ACTIVE`. `DefaultVerticalScrollBar` drag was using the absolute y (only right when the bar sat at y=0); it is now relative to `position + contentOffset` and clamped 0..1, and it also reacts to the press.
Tests: `UIEventToComponentDispatcherTest` (commonTest), the first tests for the dispatcher.

## Tried and ruled out
- Slider-only fix (slider subscribing to root-level drag events on press): leaves `DefaultVerticalScrollBar` and any future draggable with the same bug, every component would reinvent it.
- Opt-in capture flag: nobody would opt out; more API for nothing.
- Swing's model (enter/exit still fired everywhere during a drag, each widget checks "button held" and ignores): pushes the check into every component.
- Pointer-capture model (no boundary events to the captured component either): simpler, but a button would stay highlighted while dragged off it, unlike Compose.

## Watch out
- `DefaultComponent.COMPONENT_STATE_TRANSITIONS` has no `(ACTIVE, unfocused, MOUSE_EXITED)` row; the press focuses the component first, so the `(ACTIVE, focused, MOUSE_EXITED) -> FOCUSED` row is the one that fires when a captured component is dragged off itself.
- The Compose `MouseEventListener` clamps negative window coordinates to 0 before they reach the dispatcher; cells past the right/bottom edge are passed through as-is. The terminal renderer never sees motion outside the window, nothing to do there.
- Whether the browser (wasmJs) keeps delivering pointer moves outside the canvas while the button is held was not checked.
