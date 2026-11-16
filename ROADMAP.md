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

## Live logging from the terminal renderer

`runTerminal` holds log statements back while the alternate screen is active and prints them
to the error stream on exit, since any output during the run would scribble over the frame.
Watching a long session live is not possible that way. Planned shape: a `runTerminal` parameter
(or a `LogSink` the app installs) that writes to the error stream when it is redirected to a
file, and falls back to holding the lines back when it is the terminal itself. Needs an
`isatty` check on both the native and the JVM side.

## Android target for `zircon.compose` (2.1)

`zircon.compose` ships for JVM and wasmJs in 2.0. Android needs `RuntimeShader`, which is API
33 and up, so the target lands in 2.1 with a minimum SDK to match.

## Image dictionary tilesets

A folder of PNGs as a tileset: one image per tile, addressed by name, so graphical tilesets can
be assembled without packing a sprite sheet by hand.

## More component themes

Component themes landed with Neobrutalism (`docs/component-themes.md`); palettes split out of them
(`docs/component-palettes.md`). Next: a Solarized palette, and a Nerd Font tileset bundled with the
examples so the heavy box drawing and block glyphs the themes use render out of the box.

## Component state flags and ARIA vocabulary

Components identify themselves to a theme by `role` + `variant` + `part`. Interaction state is the
single `ComponentState` (hover, active, focus, disabled). Data states (selected, checked, pressed,
expanded, invalid, current) are read by renderers directly today. Planned shape: a `StateFlag` set
derived from component properties that themes can consult when deriving colors (an invalid input
gets `error` colors without the renderer knowing), and ARIA role names (`switch`, `tablist`,
`tabpanel`, `spinbutton`, `dialog`) for `ComponentRole`, so a web or Compose renderer can expose an
accessibility tree from the same data. Deferred until palettes land; see the 2026-11-16 grill.

## Palette import and terminal following

- `Palettes.fromBase16(scheme)`: map the 16 base16 slots to `PaletteKey`s (research doc, section 10,
  open question 5) and get tinted-theming's hundreds of schemes for free.
- A palette made of ANSI indices for the terminal renderer, so the app recolors when the terminal
  theme changes the way mc does (`default` fg/bg, slots 0-15). Needs indexed colors in the SGR
  encoder; the alternative is an `OSC 10/11` query at startup with a timeout, which tmux never answers.

## Clipboard

Copy and paste for text components. 1.x had an `isClipboardAvailable` switch that nothing
read; it was dropped in 2.0 rather than shipped as a no-op. The real feature needs a
platform clipboard behind a small core interface (Compose has one on every target), paste
into `TextArea` and `TextBox`, and copy of the current selection once selections exist.

## More components

- [Floating components](https://github.com/Hexworks/zircon/issues/23)
- [Drag'n drop](https://github.com/Hexworks/zircon/issues/22)
- [Custom layouts](https://github.com/Hexworks/zircon/issues/28)
- [Tree](https://github.com/Hexworks/zircon/issues/184)
- [Accordion](https://github.com/Hexworks/zircon/issues/27)
- [Combo box](https://github.com/Hexworks/zircon/issues/262)

## Pre-rendered stacked tiles

`StackedTile` composites several tiles into one cell. The 1.x renderers drew the stack back
to front with alpha, so a sprite over a glyph over a background showed all three. The 2.0
renderer draws one glyph or sprite per cell and has no per-tile alpha, so only the opaque
top of a stack shows and the "Stacked tiles" example was removed.

Planned shape:

- Pre-render a stack the first time it is drawn: composite its tiles into a dedicated atlas
  slot (CPU side, once per distinct stack), keyed by the stack's cache key.
- Draw the slot like any other sprite afterwards, so stacks cost nothing per frame.
- Evict slots of stacks that have not been drawn for a while, so animated stacks do not
  fill the atlas.

