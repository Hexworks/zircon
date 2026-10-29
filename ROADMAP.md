# Zircon Road Map

Features planned after 2.0. Each entry is a short outline of *what* we intend to build and
*why*, not a design document. The 2.0 release plan itself lives in `TODO.md`; the reasoning
behind decisions lives in `.trail/learnings/`.

If you'd like to give any of these a shot feel free to *contribute*.

## Texture effects in the shader

Graphical per-tile effects (flip, glow, outline, and similar) applied by the Compose renderer
without any per-tile CPU work. This replaces the old `TextureTransformer` / `TextureModifier`
hook, which was removed in 2.0 because it needed a texture object per tile and would have
broken the one-shader-pass-per-atlas design.

Planned shape:

- A new modifier kind next to `TileModifier` that carries effect flags (and small parameters)
  instead of transforming the tile.
- The Compose tileset writes those flags into a spare channel of the per-cell data texture
  (`FrameData.cells.a` is currently a constant).
- The tile shader branches on the flags: flips mirror the in-tile UV, glow and outline sample
  the atlas at neighbouring texels and blend a halo under the glyph. Tunables such as glow
  radius become shader uniforms.
- Effects that are too expensive per pixel get pre-baked **atlas variants** (a second sprite
  sheet with the effect applied once at load time) and are selected by atlas id instead.

## Android target for `zircon.compose` (2.1)

`zircon.compose` ships for JVM and wasmJs in 2.0. Android needs `RuntimeShader`, which is API
33 and up, so the target lands in 2.1 with a minimum SDK to match.

## Image dictionary tilesets

A folder of PNGs as a tileset: one image per tile, addressed by name, so graphical tilesets can
be assembled without packing a sprite sheet by hand.

## A more flexible component theming model

Let themes control more than the current fixed set of colour slots, so custom components and
custom looks do not need to subclass renderers.

## More components

- [Floating components](https://github.com/Hexworks/zircon/issues/23)
- [Drag'n drop](https://github.com/Hexworks/zircon/issues/22)
- [Custom layouts](https://github.com/Hexworks/zircon/issues/28)
- [Tree](https://github.com/Hexworks/zircon/issues/184)
- [Accordion](https://github.com/Hexworks/zircon/issues/27)
- [Combo box](https://github.com/Hexworks/zircon/issues/262)
