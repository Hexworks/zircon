# Animations (`.zap` files)

A Zircon animation is a zip archive with the extension `.zap`. It holds one REXPaint `.xp`
file per frame plus an `animation.json` document that describes how to play them:

```json
{
  "frameRate": 15,
  "loopCount": 1,
  "baseName": "skull-",
  "extension": "xp",
  "frames": [
    { "frame": 0, "repeatCount": 9 },
    { "frame": 1, "repeatCount": 4 },
    { "frame": 0 },
    { "frame": 2 }
  ]
}
```

- `frameRate`: ticks per second.
- `loopCount`: `0` loops forever, a positive number plays the animation that many times.
- `baseName` and `extension` name the frame files: `<baseName><index>.<extension>`, for
  example `skull-0.xp`. `baseName` defaults to `""`, `extension` to `"xp"`.
- `frames` is the sequence to play; `repeatCount` (default `1`) is how many ticks a frame
  stays on screen. When `frames` is omitted every frame file plays once in numeric order.

Load one with `loadAnimation(bytes, tileset)` (or the `Resource` overload), set the position
on the returned builder, then `build()` and start it on a `TileGrid`.

The same layout applies to graphical tilesets: a zip with `tileset.json` next to the sprite
sheets, see `GraphicalTilesetMetadata`.
