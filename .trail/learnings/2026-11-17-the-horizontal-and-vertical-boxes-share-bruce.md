---
title: "The horizontal and vertical boxes share one layout keyed by axis, and an attachment decorator must not touch the container's members by their bare names"
author: Bruce
date: 2026-11-17
branch: master
cost: { minutes: 8, tokens: 38465 }
id: l-8fce0ba7
scope: repo
status: active
src: agent
---

## Decided
2026-11-17, refactor 2 of the component handover. `DefaultHBox` and `DefaultVBox` were copies differing only in which coordinate they add up. One abstract `DefaultLinearBox` takes the axis as an `Orientation` and keeps a single `filled` extent; `remainingSpace` is derived from it, so there is no second bookkeeping value to drift. Detaching a child closes its gap with the unchecked `shiftBy` (a child may overflow under a heavy theme). No public API change: the two classes remain as thin subclasses with their old constructors.

## Watch out
- The attachment decorator (`InternalAttachedComponent by attachment`) delegates the attached component's whole `InternalComponent` surface, so inside it `children`, `size`, `position` name the *attached component's* members, not the box's. The first cut shifted the detached child's own children; the box now does the work in `closeGapOf(component)` and the decorator only calls it.
- Detaching the only child frees its extent alone; any other child frees extent plus one spacing (the gap next to it). `remainingSpace` counts the spacing a new child will need, so a box with 6 cells left and spacing 1 takes a 5-wide child, not a 6-wide one.

In the code:
- `zircon.core/.../internal/component/impl/DefaultLinearBox.kt`, `DefaultHBox.kt`, `DefaultVBox.kt`; `DefaultVBoxRenderer` typed on `VBox`.
- `zircon.core/src/commonTest/.../impl/DefaultLinearBoxTest.kt` (both axes per case), `BoxDetachAllTest`.
