---
title: TOP_DOWN_OBLIQUE_FRONT pointed at the top-down renderer since the port; attach copies the parent tileset so overrides need NoActionOnBind and a root with the g…
author: Bruce
date: 2026-10-31
branch: master
cost: { minutes: 7, tokens: 84177 }
id: l-fa0e6fc9
scope: repo
status: active
src: agent
---

## Decided
Found 2026-10-31 from the examples: `ProjectionMode.TOP_DOWN_OBLIQUE_FRONT.createRenderer()` returned `TopDownGameAreaRenderer` since the port (commit 71540ba3f), so the oblique projection had never been used in 2.0; "Oblique world" and the Scrolling example's projection switch both drew top-down. It now returns `TopDownObliqueFrontGameAreaRenderer`, which renders roofs plus front walls as expected (checked by text-rendering both game examples).

Attaching a component copies the parent's tileset and theme into it (`DefaultContainer.DefaultAttachedComponent` → `updateFrom(parent.tilesetProperty, bindingAction)`) and keeps following the parent's later changes. A tileset set on a component before it is attached is therefore lost, and so is one set after, once an ancestor's tileset changes (the examples app attaches an example's root to a placeholder panel, which moves the root from the UNKNOWN builder default to the grid tileset and cascades down). The "Multi tile" example builds each overriding component with `tileset = ...`, `colorTheme = screen.theme` and `bindingAction = NoActionOnBind` (the builder requires both when not updating on bind) and gives the root the screen tileset, so no change cascades. Equal values do not fire a property change, which is what makes the root trick work.

## Watch out
A component's `tileset` is UNKNOWN until attached unless the builder sets it; `TilesetResource.isCompatibleWith` treats UNKNOWN as compatible with anything. To confirm which tileset a component really renders with, walk `(grid as InternalTileGrid).renderables` (each is a `TilesetOverride`), not the screen, which has no public children.
