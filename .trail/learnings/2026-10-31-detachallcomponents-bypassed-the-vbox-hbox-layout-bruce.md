---
title: detachAllComponents bypassed the VBox/HBox layout reset (table emptied on mutation); selection highlight needs explicit row invalidation
author: Bruce
date: 2026-10-31
branch: master
cost: { minutes: 3, tokens: 63317 }
id: l-f7ace999
scope: repo
status: active
src: agent
---

## Decided
Fixed 2026-10-31 after the user tried fragments > Table. `DefaultContainer.detachAllComponents` detaches the plain `DefaultAttachedComponent`s stored in `attachments`, never the `VBoxAttachmentDecorator`/`HBoxAttachmentDecorator` that `addComponent` returns, so `filledUntil`/`availableSpace` were never reset and the next `addComponent` threw "There is not enough space". `DefaultVBox` and `DefaultHBox` now override `detachAllComponents` and reset both (`BoxDetachAllTest`). Any container that caches layout state in its attachment decorator needs the same override.

Table rows decide at render time whether they are selected (`DefaultTable.newRowFor`), but the render cache only invalidates on the common inputs, so the previously selected row kept its highlight until hovered. `DefaultTable` now invalidates every row on `selectedElements.onChange`.

## Watch out
A listener throwing inside the examples app does not crash it any more (runtimeErrors), so a half-done reload (detach ok, re-add throws) shows as "everything vanished" rather than an exception. Check `ExamplesScreen.runtimeErrors` first when a list empties on mutation.
