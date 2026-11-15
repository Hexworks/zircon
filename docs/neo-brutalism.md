# Neobrutalism Component Theme

Reference for the first component theme (see [component-themes.md](component-themes.md)). The source is HyperUI's
[neobrutalism collection](https://hyperui.dev/components/neobrutalism/): 11 categories, each with light and dark variants.
This document maps every visual device in that collection to something a tile grid can draw, then shows a mockup of each
component in the resulting style.

The mockups below are monochrome. The interactive version with colors and the light/dark toggle is at
https://claude.ai/artifact/UPQy9qjPfhBqnuUmXJD6zb. The renderer that produced both lives in that page's script; the glyphs
here are exactly what it emits.

## The style in one paragraph

Neobrutalism is: a 2px solid border, a hard offset shadow with no blur (2px or 4px, sometimes stacked), flat fills with no
gradients or radius, bold text, and one accent (yellow-200 in light mode, yellow-700 in dark mode) used for hover, focus and
the active state. The dark variant is not a different design: ink and paper swap, the accent darkens, and the status colors
move from the 100 shade on 900 text to the 800 shade on 50 text.

## Translation table

Every device HyperUI uses has a cell-level equivalent. Shadows need a tileset with the Unicode Block Elements
(U+2580 to U+259F) and the borders need the heavy box-drawing glyphs (U+250F and friends). A CP437 sheet has only
`▌ ▀ █` of these, so the theme needs a TrueType or Nerd Font tileset.

| HyperUI                                                              | Tile grid                                                                                           | Fidelity                                                                                                   |
| -------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------- |
| `border-2 border-black`                                              | heavy box drawing `┏ ━ ┓ ┃ ┗ ┛`, dividers `┣ ━ ┫`                                                   | exact                                                                                                      |
| `shadow-[4px_4px_0_0]`                                               | `▌` right column, `▀` bottom row, `▘` corner; both start one cell in from the top-left                               | good. On 1:2 terminal cells the bottom edge is twice as thick as the right one; on square tiles they match |
| `shadow-[2px_2px_0_0]` (badges, checkboxes, progress, small buttons) | `▎` right column, `▔` bottom row, corner empty                                                                    | good                                                                                                       |
| `shadow-[4px_4px,8px_8px]` (retro window)                            | one full `█` cell right and below                                                                   | exact                                                                                                      |
| `shadow-[4px,8px,12px]` (stacked card)                               | `█` then `▌` / `▀`: 1.5 cells                                                                       | exact                                                                                                      |
| `border-dashed`                                                      | `┅ ┇` heavy dashed                                                                                  | exact                                                                                                      |
| `ring-2 ring-offset-2 ring-offset-yellow-300` (double border)        | `╔ ═ ╗` double line drawn on yellow background cells: ink, yellow, ink                              | exact                                                                                                      |
| `after:h-1` underline, `hover:after:h-full`                          | `▂` row under the text; hover inverts every cell of the button                                      | exact                                                                                                      |
| `hover:translate-1 hover:shadow-none`                                | shift one column right, drop the shadow. No vertical shift, a row is 16px                           | partial                                                                                                    |
| `hover:shadow-[-1px_-1px]` (inset)                                   | `▁` in the row above, `▕` in the column to the left                                                 | good                                                                                                       |
| striped gradient (progress)                                          | `╱` in yellow-500 on yellow-400 cells                                                               | good                                                                                                       |
| `width: 25%` fill                                                    | whole cells plus a `▌` end cap: half-cell precision                                                 | good                                                                                                       |
| checkbox `size-6`, `checked:bg-black`                                | `☐`; checked is an inverted cell with `✓`. No shadow on a one-cell box                                                           | good                                                                                                       |
| SVG icons                                                            | `ⓘ ✔ ⚠ ▦ ✕ @ ▾ ▴`, or Nerd Font glyphs when the tileset has them                                    | good                                                                                                       |
| `focus:ring-2 ring-yellow-300`                                       | yellow `▁ ▔ ▏ ▕` ring one cell outside the border. Costs a gutter cell, so not drawn in the mockups | partial                                                                                                    |
| `text-xl`, `text-xs`, `font-semibold`                                | one size. Bold and UPPERCASE carry the hierarchy                                                    | partial                                                                                                    |
| `dark:` variants                                                     | palette swap, see below                                                                             | exact                                                                                                      |

### Shadow anatomy

The 4px shadow around a box at `(x, y, w, h)`:

| Cell                  | Glyph | Why                               |
| --------------------- | ----- | --------------------------------- |
| `(x+w, y+1 .. y+h-1)` | `▌`   | right edge, starts one row down    |
| `(x+w, y+h)`          | `▘`   | corner                            |
| `(x+1 .. x+w-1, y+h)` | `▀`   | bottom edge, starts one column in |

Nothing is drawn beside the top row or under the left column: the shadow is offset by a whole cell from the top-left, so the box reads as lifted off the surface. The 2px shadow follows the same rule with `▎` on the right column from `y+1` and `▔` on the bottom row from `x+1` to `x+w-1`. Its corner cell `(x+w, y+h)` stays empty: the notch would be 2×2px and no glyph has that shape (`▔` overshoots, `▘` is too fat, `🬀` needs the Legacy Computing block). A custom tileset can add the notch tile. The 8px shadow is `█` on
`(x+w, y+1 .. y+h)` and `(x+1 .. x+w, y+h)`. The 12px stacked shadow is the 8px shadow plus a 4px shadow of the same box
shifted by (1, 1).

All shadow glyphs use the ink color as foreground and whatever is behind as background, so the shadow works over the page,
over another component, and over the yellow hover fill.

## Palette

Tailwind names from the HyperUI source. Ink and paper are the border/shadow/text color and the fill color respectively.

| Role                                                         | Light                                        | Dark                                     |
| ------------------------------------------------------------ | -------------------------------------------- | ---------------------------------------- |
| ink (border, shadow, text)                                   | black `#000000`                              | white `#ffffff`                          |
| paper (fill)                                                 | white `#ffffff`                              | gray-900 `#111827`                       |
| accent (hover, active tab, Save)                             | yellow-200 `#fef08a`                         | yellow-700 `#a16207`                     |
| accent strong (focus ring, Search button, double-border gap) | yellow-300 `#fde047`                         | yellow-600 `#ca8a04`                     |
| stripes                                                      | yellow-400 `#facc15` on yellow-500 `#eab308` | reversed                                 |
| info                                                         | blue-100 `#dbeafe` / blue-900 `#1e3a8a`      | blue-800 `#1e40af` / blue-50 `#eff6ff`   |
| success                                                      | green-100 `#dcfce7` / green-900 `#14532d`    | green-800 `#166534` / green-50 `#f0fdf4` |
| error                                                        | red-100 `#fee2e2` / red-900 `#7f1d1d`        | red-800 `#991b1b` / red-50 `#fef2f2`     |
| status dot, progress fill                                    | green-600 `#16a34a`, red-600 `#dc2626`       | green-300 `#86efac`, red-300 `#fca5a5`   |

Alerts are the one place the border is not ink: Tailwind's default border color is `currentColor`, so the alert border
takes the text color (blue-900 and so on) while the shadow stays ink.

## Component states

The theme needs these states per component. They map onto the existing `ComponentState` set.

| HyperUI                       | ComponentState | Rendering                                                                                                         |
| ----------------------------- | -------------- | ----------------------------------------------------------------------------------------------------------------- |
| rest                          | `DEFAULT`      | paper fill, ink border, shadow                                                                                    |
| `hover:`                      | `HIGHLIGHTED`  | accent fill (most), or shift + no shadow (pressed buttons, pills), or inverted (underlined button, inactive tabs) |
| `focus:`                      | `FOCUSED`      | same as hover in HyperUI for accordions and search; otherwise a ring, see table                                   |
| `active` / `checked` / `open` | `ACTIVE`       | accent fill (tabs), inverted cell (checkbox), flipped chevron (accordion)                                         |
| `disabled`                    | `DISABLED`     | not in HyperUI. Suggest: no shadow, muted ink                                                                     |

## Mockups

Rest state unless noted. Where a hover state changes geometry (pressed buttons, dashed card, pills) the hover frame is shown
next to or instead of the rest frame.

> [!IMPORTANT]
> All shadows are offset with regards to the top/left of the component to create a 3D effect (see the mockups below)

### Accordions

Three layouts over the same three items. The open item shows the hover fill and the flipped chevron. The body uses the
same 2 cells of padding as the summary.

#### Base

Each summary is its own shadowed box; the open body sits below with no border.

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ What are the basic features?           ▴ ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘
  Lorem ipsum dolor sit amet consectetur
  adipisicing elit. Quas, eos!

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ How do I get started?                  ▾ ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ What support options are available?    ▾ ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘
```

#### Contained

Each item is one box; the open body is inside it under a `┣━┫` divider.

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ What are the basic features?           ▴ ┃▌
┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫▌
┃ Lorem ipsum dolor sit amet consectetur   ┃▌
┃ adipisicing elit. Quas, eos!             ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ How do I get started?                  ▾ ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ What support options are available?    ▾ ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘
```

#### Grouped

One box, items separated by dividers, one shadow for the group.

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ What are the basic features?           ▴ ┃▌
┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫▌
┃ Lorem ipsum dolor sit amet consectetur   ┃▌
┃ adipisicing elit. Quas, eos!             ┃▌
┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫▌
┃ How do I get started?                  ▾ ┃▌
┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫▌
┃ What support options are available?    ▾ ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘
```

### Alerts

Same frame for all three, only color and icon change. Border takes the text color, shadow stays ink. The icon is a single
glyph in column 2; the text starts at column 4 and wraps under itself.

#### Info / Success / Error

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ ⓘ Lorem ipsum dolor sit amet consectetur adipisicing   ┃▌
┃   elit. Quas, eos!                                     ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ ✔ Lorem ipsum dolor sit amet consectetur adipisicing   ┃▌
┃   elit. Quas, eos!                                     ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ ⚠ Lorem ipsum dolor sit amet consectetur adipisicing   ┃▌
┃   elit. Quas, eos!                                     ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘
```

### Badges

The 2px shadow. The status dot is `■` in green-600 or red-600. The dismiss button is one inverted cell; its own 2px
shadow is dropped because the cell below it is the border.

#### Base, with status, with dismiss

```
┏━━━━━━┓  ┏━━━━━━━━━┓  ┏━━━━━━━┓
┃ Info ┃▎ ┃ Success ┃▎ ┃ Error ┃▎
┗━━━━━━┛▎ ┗━━━━━━━━━┛▎ ┗━━━━━━━┛▎
 ▔▔▔▔▔▔▔   ▔▔▔▔▔▔▔▔▔▔   ▔▔▔▔▔▔▔▔

┏━━━━━━━━━━━┓  ┏━━━━━━━━━┓
┃ ■ Success ┃▎ ┃ ■ Error ┃▎
┗━━━━━━━━━━━┛▎ ┗━━━━━━━━━┛▎
 ▔▔▔▔▔▔▔▔▔▔▔▔   ▔▔▔▔▔▔▔▔▔▔

┏━━━━━━━━━━━━━━━━┓
┃ New Message  ✕ ┃▎
┗━━━━━━━━━━━━━━━━┛▎
 ▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔
```

### Buttons

Left frame rest, right frame hover. All buttons are text plus 2 cells of padding, 3 rows tall.

#### Base

Hover: accent fill. Shadow stays.

```
┏━━━━━━━━━━━━┓    ┏━━━━━━━━━━━━┓
┃ Click Here ┃▌   ┃ Click Here ┃▌
┗━━━━━━━━━━━━┛▌   ┗━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▘    ▀▀▀▀▀▀▀▀▀▀▀▀▀▘
```

#### Pressed

Hover: shift one column right, shadow gone. HyperUI also shifts down 4px; a row is too coarse for that.

```
┏━━━━━━━━━━━━┓     ┏━━━━━━━━━━━━┓
┃ Click Here ┃▌    ┃ Click Here ┃
┗━━━━━━━━━━━━┛▌    ┗━━━━━━━━━━━━┛
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▘
```

#### Pressed with inset

Hover: shift, shadow gone, a 1px line appears top-left: `▁` above and `▕` to the left. Mirror of the main rule: offset one cell from the bottom-right, corner empty.

```
                   ▁▁▁▁▁▁▁▁▁▁▁▁
┏━━━━━━━━━━━━┓    ▕┏━━━━━━━━━━━━┓
┃ Click Here ┃▌   ▕┃ Click Here ┃
┗━━━━━━━━━━━━┛▌    ┗━━━━━━━━━━━━┛
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▘
```

#### Underlined

No border. A `▂` row is the 4px bar. Hover: the bar grows to the full button, so every cell inverts.

```

  Click Here        Click Here
▂▂▂▂▂▂▂▂▂▂▂▂▂▂
```

#### Double border

`╔═╗` on yellow-300 background cells gives ink, yellow, ink. No shadow. Hover: interior turns yellow-200.

```
╔════════════╗    ╔════════════╗
║ Click Here ║    ║ Click Here ║
╚════════════╝    ╚════════════╝
```

### Cards

Date row in bold caps stands in for `text-xs uppercase`. Title bold, body regular with an ellipsis for `line-clamp-2`.
The whole card is one hover target, so hover fills everything inside the border with the accent.

#### Base

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                                            ┃▌
┃ ▦ APRIL 1, 2025                            ┃▌
┃                                            ┃▌
┃ How I built my first website with          ┃▌
┃ Nuxt, Tailwind CSS and Vercel.             ┃▌
┃                                            ┃▌
┃ Lorem ipsum dolor sit amet consectetur     ┃▌
┃ adipisicing elit. Quas, eos! Lorem ipsum…  ┃▌
┃                                            ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘
```

#### Base stacked

12px shadow: a full cell then a half cell. Hover drops all of it and shifts the card, same as the pressed button.

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                                            ┃█
┃ ▦ APRIL 1, 2025                            ┃█▌
┃                                            ┃█▌
┃ How I built my first website with          ┃█▌
┃ Nuxt, Tailwind CSS and Vercel.             ┃█▌
┃                                            ┃█▌
┃ Lorem ipsum dolor sit amet consectetur     ┃█▌
┃ adipisicing elit. Quas, eos! Lorem ipsum…  ┃█▌
┃                                            ┃█▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛█▌
 ██████████████████████████████████████████████▌
  ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘
```

#### Base dashed

Shown hovered. At rest the dashed layer sits exactly under the card and is invisible; on hover the card moves up-left one cell and the `┅ ┇` outline shows along the right and bottom. The card is tall (`h-72`) with content at the bottom, and the body text only appears on hover.

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                                            ┃┓
┃                                            ┃┇
┃                                            ┃┇
┃                                            ┃┇
┃                                            ┃┇
┃ ▦ APRIL 1, 2025                            ┃┇
┃                                            ┃┇
┃ How I built my first website with          ┃┇
┃ Nuxt, Tailwind CSS and Vercel.             ┃┇
┃                                            ┃┇
┃ Lorem ipsum dolor sit amet consectetur     ┃┇
┃ adipisicing elit. Quas, eos! Lorem ipsum…  ┃┇
┃                                            ┃┇
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛┇
 ┗┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┛
```

#### Retro window

Title bar is one accent row with bold caps and two `□` controls. 8px shadow is one full `█` cell. No hover.

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ SYSTEM MESSAGE.                       □ □  ┃█
┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫█
┃                                            ┃█
┃ Retro Window                               ┃█
┃                                            ┃█
┃ Lorem ipsum dolor sit amet consectetur     ┃█
┃ adipisicing elit. Quas, eos!               ┃█
┃                                            ┃█
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛█
 ██████████████████████████████████████████████
```

### Checkboxes

One cell per box. Unchecked `☐`, checked inverted with `✓`. No shadow: on a one-cell box the 2px shadow is a sliver
next to a single glyph and reads as noise. The blank row between options is HyperUI's `gap-3`.

#### Base

```
☐  Option 1

✓  Option 2

☐  Option 3
```

#### With description

Label bold, description regular, both indented past the box.

```
☐  Option 1
   Lorem, ipsum dolor sit amet consectetur adipisicing elit.

✓  Option 2
   Lorem, ipsum dolor sit amet consectetur adipisicing elit.

☐  Option 3
   Lorem, ipsum dolor sit amet consectetur adipisicing elit.
```

#### Grouped

One shadowed box, items separated by dividers. The hovered row takes the accent fill across the full inner width.

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ ☐  Option 1                                                    ┃▌
┃    Lorem, ipsum dolor sit amet consectetur adipisicing elit.   ┃▌
┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫▌
┃ ✓  Option 2                                                    ┃▌
┃    Lorem, ipsum dolor sit amet consectetur adipisicing elit.   ┃▌
┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫▌
┃ ☐  Option 3                                                    ┃▌
┃    Lorem, ipsum dolor sit amet consectetur adipisicing elit.   ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘
```

### Inputs

Label above in bold, box below, 3 rows tall, 4px shadow. Focus is a yellow ring in HyperUI; here the caret and ring are
left to the implementation.

#### Base

```
Email
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                                        ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘
```

#### With icon

A 32px ink square with the glyph inside becomes three inverted cells at the right edge, one cell in from the border.

```
Email
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                                     @  ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘
```

#### Search

Input and button share one border. The button is accent-strong (yellow-300) across the full inner height with bold caps.

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                                SEARCH  ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘
```

### Progress bars

Track is a 3 row box with the 2px shadow and one cell of padding each side (`p-1`). Fill ends on a `▌` so 25% and 75%
of a 38 cell track land exactly.

#### Base

25%, ink fill.

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃          ▌                             ┃▎
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▎
 ▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔
```

#### Striped

`╱` in yellow-500 on yellow-400 cells; the end cap is a yellow-400 `▌`.

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ ╱╱╱╱╱╱╱╱╱▌                             ┃▎
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▎
 ▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔
```

#### Status

Bold label left, bold percentage right, green-600 fill.

```
Updating                               75%
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                             ▌          ┃▎
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▎
 ▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔
```

### Selects

The browser draws the native dropdown; a tile grid has to draw its own. The open list reuses the same box and shadow and
the accent for the hovered row. Group labels are bold and muted, options indented two cells.

#### Base

```
Headliner
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ Please select                        ▾ ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘
```

#### Option groups

Shown open. Chevron flips to `▴`.

```
Headliner
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ Please select                        ▴ ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ A                                      ┃▌
┃   Albert King                          ┃▌
┃ B                                      ┃▌
┃   B.B King                             ┃▌
┃   Buddy Guy                            ┃▌
┃ E                                      ┃▌
┃   Eric Clapton                         ┃▌
┃ J                                      ┃▌
┃   John Mayer                           ┃▌
┃   Jimi Hendrix                         ┃▌
┃ S                                      ┃▌
┃   Stevie Ray Vaughn                    ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘
```

#### Datalist

Shown while typing. The matching option appears in a one row list under the input.

```
Headliner
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ Ji▏                                  ▾ ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ Jimi Hendrix                           ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘
```

### Tabs

Tab buttons are text plus 3 cells of padding inside a border, 3 rows tall. Inactive tabs keep the border cells but leave
them blank (`border-transparent`), so hover can fill the same footprint in ink. The active tab is accent-filled and merges
into the border line: the line meets the tab with `┛` and `┗` and is absent under it.

#### Base

Third tab hovered: inverted over its full footprint, including the line row.

```
 ┏━━━━━━━━━━━━━┓
 ┃   Profile   ┃    Account        Notifications
━┛             ┗━━━━━━━━━━━━━━━                     ━

Lorem ipsum dolor sit amet consectetur
adipisicing elit. Quas, eos!
```

#### Contained

The line is the top border of a shadowed panel. At the left the active tab’s border runs straight into the panel’s, so the corner is `┃`.

```
┏━━━━━━━━━━━━━┓
┃   Profile   ┃    Account        Notifications
┃             ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                                                 ┃▌
┃ Lorem ipsum dolor sit amet consectetur          ┃▌
┃ adipisicing elit. Quas, eos!                    ┃▌
┃                                                 ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘
```

#### Pills

Each tab is a shadowed box; the active one accent-filled. Third tab hovered: shifted one column, shadow gone.

```
┏━━━━━━━━━━━━━┓   ┏━━━━━━━━━━━━━┓    ┏━━━━━━━━━━━━━━━━━━━┓
┃   Profile   ┃▌  ┃   Account   ┃▌   ┃   Notifications   ┃
┗━━━━━━━━━━━━━┛▌  ┗━━━━━━━━━━━━━┛▌   ┗━━━━━━━━━━━━━━━━━━━┛
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘   ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘

Lorem ipsum dolor sit amet consectetur
adipisicing elit. Quas, eos!
```

#### Vertical

The sidebar line meets the active tab with `┛` above and `┓` below, and is absent beside it.

```
               ┃
┏━━━━━━━━━━━━━━┛    Lorem ipsum dolor sit amet consectetur
┃   General         adipisicing elit. Quas, eos!
┗━━━━━━━━━━━━━━┓
               ┃
    Privacy    ┃
               ┃
               ┃
    Security   ┃
               ┃
               ┃
```

### Textareas

Four rows of text plus the border. Buttons are the small variant: text plus 2 cells padding, 2px shadow, Save
accent-filled.

#### Base

```
Notes
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                                                  ┃▌
┃                                                  ┃▌
┃                                                  ┃▌
┃                                                  ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘
```

#### Actions inside

The footer is inside the box under a divider; the buttons’ thin shadows need their own row before the bottom border.

```
Notes
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                                                  ┃▌
┃                                                  ┃▌
┃                                                  ┃▌
┃                                                  ┃▌
┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫▌
┃                            ┏━━━━━━━┓   ┏━━━━━━┓  ┃▌
┃                            ┃ Clear ┃▎  ┃ Save ┃▎ ┃▌
┃                            ┗━━━━━━━┛▎  ┗━━━━━━┛▎ ┃▌
┃                             ▔▔▔▔▔▔▔▔    ▔▔▔▔▔▔▔  ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘
```

#### Actions outside

Buttons sit under the box, right-aligned, one row of gap.

```
Notes
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                                                  ┃▌
┃                                                  ┃▌
┃                                                  ┃▌
┃                                                  ┃▌
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛▌
 ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▘

                              ┏━━━━━━━┓   ┏━━━━━━┓
                              ┃ Clear ┃▎  ┃ Save ┃▎
                              ┗━━━━━━━┛▎  ┗━━━━━━┛▎
                               ▔▔▔▔▔▔▔▔    ▔▔▔▔▔▔▔
```

## Notes for the implementation

- **Shadow is a decoration, not part of the component bounds.** Every shadowed component needs one extra column and one
  extra row of the parent's space (two for the retro window, two for stacked). The decoration renderer should draw into
  that margin, the same way the current `shadow()` decoration does, but with the quadrant glyphs at the ends.
- **Hover geometry changes.** Pressed buttons, pills and the dashed card move on hover. The component bounds should
  include the shifted position so nothing is redrawn outside them. The simplest model: the component owns the
  shadow cells and on hover draws the box one cell to the right into the space the shadow vacated.
- **Tabs need joint glyphs.** The active tab's bottom corners depend on what the line under it is: a bare line (`┛ ┗`),
  a panel's top edge (`┃ ┗`) or a vertical sidebar (`┛ ┓`). The tab bar renderer owns the line and draws the joints;
  the tab renderer only fills.
- **Half-cell precision for fills.** Progress bars should keep a `▌` end cap rather than rounding to whole cells. The
  same trick applies to anything sized by percentage.
- **One size of text.** The hierarchy is bold vs regular, caps vs mixed, ink vs muted. Nothing else is available.
- **Alerts break the ink rule.** Their border color comes from the status color, not ink. The style set for alerts
  needs a per-severity border color while the shadow stays ink.
- **Dark mode is a palette swap**, nothing structural. One `Palette` with the roles in the table above, two instances,
  and `ThemeSettings.SYSTEM` picks one.
- **Open list for selects.** There is no native dropdown to lean on; the select needs a popup layer that renders the
  list with the same box, shadow and accent. That popup should be a `Fragment` so it can also serve the datalist.
