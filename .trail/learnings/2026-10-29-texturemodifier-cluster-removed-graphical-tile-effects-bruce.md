---
title: "TextureModifier cluster removed: graphical tile effects belong in the shader, not a per-tile texture hook"
author: Bruce
date: 2026-10-29
branch: master
cost: { minutes: 5, tokens: 60530 }
id: l-45c4390d
scope: repo
status: active
src: agent
---

## Decided
Deleted `TextureModifier`, `TextureTransformer`, `TextureContext`-based `TextureModifierStrategy`, `TextureModifierBuilder`, `DefaultTextureTransformer`, `AppConfig.textureModifierStrategies` and the `textureModifier {}` DSL on `AppConfigBuilder` (2026-10-29). Nothing implemented or consumed them: `BaseRenderer` only applies `TileModifier`, and `zircon.compose` never looked at tile modifiers. It was a leftover from the Swing/KorGE per-tile draw era.

## Tried and ruled out
Wiring it into the Compose renderer: `TextureTransformer.apply` would run per tile per frame on a texture object outside the atlas, which forces per-tile draws or per-tile `Image` creation. That is exactly what the one-shader-pass-per-atlas design rules out.

## Watch out
Future graphical effects (glow, flip, outline) go into the RuntimeEffect shader as either extra per-cell data in the data texture (a flags byte the shader branches on) or as pre-baked atlas variants. Do not reintroduce a per-tile CPU transform hook. `TileModifier` (CPU, tile-to-tile) stays and is the right place for content changes like Hidden/Blink/Fade.
