---
title: "Check box and radio button commit on deactivated() (release), keep the pending glyph for the whole press, and cancel when released outside; a click in tests is…"
author: Bruce
date: 2026-10-31
branch: master
cost: { minutes: 4, tokens: 37872 }
id: l-52ceaba7
scope: repo
status: active
src: agent
touches:
  - zircon.core/api/zircon.core.api
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/impl/BaseSlider.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/impl/DefaultHorizontalSlider.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/impl/DefaultVerticalSlider.kt
  - zircon.core/src/commonTest/kotlin/org/hexworks/zircon/internal/uievent/impl/UIEventToComponentDispatcherTest.kt
---

## Decided
2026-10-31, after the mouse capture change made the press reach these components for its whole duration. `DefaultCheckBox` and `DefaultRadioButton` (zircon.core `internal/component/impl/`):
- `activated()` (dispatcher fires it on press and on the space key) only shows the pending glyph: `[+]`/`[-]` via `CHECKING`/`UNCHECKING`, `<o>` via `PRESSED`. It no longer selects the radio button; before, `activated()` set `isSelected = true`, so the selection listener overwrote `PRESSED` with `SELECTED` and `<o>` was only visible on an already-selected button.
- `deactivated()` (fired on release and on the space key release) commits: toggles the check box, selects the radio button. Both guard with `pressing && !cursorOutside`, so a release outside the component cancels the press (browser/Compose behaviour).
- `mouseExited`/`mouseEntered` only track `cursorOutside` while pressing; they no longer reset the glyph. Before, the check box reset `checkBoxState` on every exit, which is why `[+]` vanished the moment the cursor left and never came back.
- `focusTaken()` resets both. The radio button's `mousePressed`/`mouseReleased` overrides are gone, the component events cover them.
- `DefaultRadioButtonGroupTest.shouldSelectButtonWhenClicked` now calls `activated()` then `deactivated()`: a click is both.

## Watch out
- `DefaultToggleButton.activated()` still toggles on press, not on release, and does not cancel when released outside. Left as is; change it the same way if the user wants it consistent.
- Zircon buttons fire `onActivated` on press (the `ACTIVATED` component event), not on release; the cancel-on-release-outside rule above applies only to the two selectables.
