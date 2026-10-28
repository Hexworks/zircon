# Renderer prototype (M1)

Standalone Gradle build, independent of the Zircon modules, used to pick the drawing strategy
for the 2.0 Compose renderer. Results and the decision are in the root `TODO.md` (Results log).
This directory is deleted once the real renderer lands in `zircon.compose` (M4).

## Run

```sh
cd prototype
./gradlew jvmRun                                  # desktop, interactive, buttons switch paths
PROTO_AUTO=1 ./gradlew jvmRun                     # desktop, cycles every path, prints RESULT lines, exits
PROTO_AUTO=1 PROTO_ONLY=SHADER ./gradlew jvmRun   # one path only, stays open

./gradlew wasmJsBrowserDistribution
python3 -m http.server 8971 --bind 127.0.0.1 --directory build/dist/wasmJs/productionExecutable
# then open http://127.0.0.1:8971/?auto  (or ?auto&only=SHADER)
```

Desktop runs with `-Dskiko.vsync.enabled=false` so the frame rate is not capped at the display rate.

## Paths

| Enum | File | What |
|---|---|---|
| `DRAW_SCOPE` | `DrawScopePath.kt` | `drawRect` + tinted `drawImage` per tile |
| `CANVAS` | `DrawScopePath.kt` | same through `Canvas` with one reused `Paint` |
| `PIXELS` | `PixelPath.kt`, `skikoMain/PixelUploader.skiko.kt` | CPU BGRA buffer, `Bitmap.installPixels` per frame |
| `VERTICES` | `VerticesPath.kt` | Compose `Vertices`, two meshes per frame |
| `NATIVE_VERTICES` | `NativeVerticesPath.kt`, `skikoMain/NativeVertexBatch.skiko.kt` | skia `drawVertices` with flat arrays |
| `SHADER` | `ShaderPath.kt`, `skikoMain/DataTextureRenderer.skiko.kt` | three data textures + `RuntimeEffect`; the winner |

The grid is 240x135 cells of 8x8 px (1920x1080). `Grid.randomize` rewrites every cell each frame;
`ColorMode` switches between random 24-bit colours, a 16-colour palette and monochrome.
