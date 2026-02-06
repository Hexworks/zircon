# Progress

## Week 1

- Created `Palette`, refactored `TileColor` to just `Color` and separated the current
  color `enum`s into `Color`s and `Palette`s respecitvely.
- Created ANSI `Palette`s for the most well-known terminals such as xterm.
- Refactored `Modifier`s, converted those `TextureModifiers` into `TileModifiers` that can be converted,
  deleted the rest
- Devised a way to allow for component theming that isn't limited to the 5 colors in `ColorTheme` (not in code yet)
- Figured out a way to allow for combining `TileModifier`s


## Week 2

- 