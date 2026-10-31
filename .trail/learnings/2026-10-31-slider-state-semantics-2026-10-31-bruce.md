---
title: "Slider state semantics (2026-10-31): space is a no-op, arrow keys and dragging make it ACTIVE, focused and active share one style, hover differs only in handle…"
author: Bruce
date: 2026-10-31
branch: master
cost: { minutes: 7, tokens: 38028 }
id: l-f70408a3
scope: repo
status: active
src: agent
---

## Decided
Specified by the user on 2026-10-31 after trying the slider example; implemented in `BaseSlider`, `DefaultHorizontalSlider`, `DefaultVerticalSlider` (zircon.core `internal/component/impl/`):
- Tab onto a slider: `FOCUSED`. Tab away or click anything else focusable: back to `DEFAULT` (the `FOCUS_TAKEN` rows in `DefaultComponent.COMPONENT_STATE_TRANSITIONS` already do this from `FOCUSED`, `ACTIVE` and `HIGHLIGHTED`).
- The activation shortcut (space) does nothing: `BaseSlider.activated()` and `deactivated()` return `Pass` without touching the state. Before, `activated()` set `HIGHLIGHTED`, which is why space looked like it "activated" the slider and why a press ended up `HIGHLIGHTED` instead of `ACTIVE`.
- An arrow key that moves the value makes the slider `ACTIVE` (`navigatedByKey()`), the same state as a mouse drag. Dragging stays `ACTIVE` outside the bounds; release on the slider gives `HIGHLIGHTED`, release elsewhere gives `FOCUSED` (see the capture learnings).
- Styles in `convertColorTheme`: focused == active (accent background, primaryBackground handle), hover is accent background with secondaryForeground handle, the same convention `ColorTheme.toInteractiveStyle()` uses for buttons. Before, `activeStyle` was unset, so `ACTIVE` fell back to the default look (the builder maps untouched styles to `defaultStyle`), and the focused background equalled the filled bar's colour so the handle vanished into the bar on focus.

## Tried and ruled out
- Keeping the old focused style (primaryForeground background): the slider renderers draw the filled part of the bar with that same colour, so a focused or active handle became invisible, read as a "shape change".

## Watch out
- `ComponentStyleSetBuilder.build()` replaces any style left at `StyleSet.defaultStyle()` with the default style by identity. A component that wants a distinct `ACTIVE` look must set `activeStyle` explicitly.
