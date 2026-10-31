---
title: "No tab bar examples in the fragments package: the examples app's own MatrixTabBar is the demo of all three tab bar fragments"
author: Bruce
date: 2026-10-31
branch: master
cost: { minutes: 8, tokens: 8860 }
id: l-87981fa4
scope: repo
status: active
src: agent
touches:
  - zircon.examples/src/commonMain/kotlin/org/hexworks/zircon/examples/fragments/FragmentExamples.kt
---

## Decided
On 2026-10-31 the user removed the "H tab bar", "V tab bar" and "Matrix tab bar" entries from the fragments package (with their shared `TabContent.kt` helper): the examples app itself is the demo of all three tab bar fragments, since `ExamplesScreen.tabBar` is a `MatrixTabBar` whose two strips are the horizontal and vertical tab bars. Do not add a tab bar example back; the fragments package is Menu bar, Selector, Table.

## Watch out
Anything that needs a tab bar to exercise in tests should drive `ExamplesScreen.tabBar` (`selectedHorizontalKey` / `selectedVerticalKey`) or build one directly, as `DefaultMatrixTabBarTest`, `DefaultHorizontalTabBarTest` and `TabStripTest` in core do.
