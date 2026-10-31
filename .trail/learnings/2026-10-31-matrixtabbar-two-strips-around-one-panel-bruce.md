---
title: "MatrixTabBar: two strips around one panel, primary filters secondary by existing cells, TabStrip.replaceTabs; strips shorter than one tab plus two scroll butto…"
author: Bruce
date: 2026-10-31
branch: master
cost: { minutes: 7, tokens: 92517 }
id: l-62e408bd
scope: repo
status: active
src: agent
touches:
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/impl/DefaultHBox.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/impl/DefaultVBox.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/fragment/impl/DefaultTable.kt
  - zircon.core/src/commonTest/kotlin/org/hexworks/zircon/internal/component/impl/BoxDetachAllTest.kt
  - zircon.examples/src/commonMain/kotlin/org/hexworks/zircon/examples/ExamplesScreen.kt
---

## Decided
Added 2026-10-31 on the user's request after the examples app screenshot: `MatrixTabBar` (`api/fragment/MatrixTabBar.kt`, `MatrixTabBarBuilder`, `DefaultMatrixTabBar`) is two strips around one content panel, horizontal along the top (3 high, starts at x = tabWidth) and vertical down the left (tabWidth wide, starts at y = 3), so no strip is nested in another's content and every tab stays visible. Views are sparse cells keyed by (horizontal key, vertical key); tabs keep declaration order on each axis.

One strip is `primary` (default HORIZONTAL): it shows all its tabs, the secondary strip shows only the tabs that have a view with the selected primary tab (`TabStrip.replaceTabs`). Selecting a primary tab keeps the secondary key when that cell exists, else falls back to the first available. Setting a secondary key whose cell is missing is reported via `reportError("Matrix tab bar", ...)` and the old value restored, same pattern as the other tab bars. Builder requires every tab to appear in at least one view.

## Tried and ruled out
Reusing `TabBuilder` for matrix tabs: it requires content per tab, but here content belongs to the cell, so `MatrixTabBuilder` (key + label) and `MatrixViewBuilder` (horizontal, vertical, content) were added instead.

## Watch out
`TabStrip` cannot lay out when the strip is too short for one tab plus both scroll buttons (vertical: fewer than 9 cells of strip): `ensureVisible` then scrolls to a window of two scroll buttons and the VBox throws "not enough space". Tests for vertical strips need height >= 3 + 2 + 9. The examples app still uses the nested horizontal/vertical bars; switching it to the matrix bar is a separate change (`ExamplesScreen.exampleBars` and its tests depend on the per-package bars).
