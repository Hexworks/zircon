---
title: "Android is out of scope for Zircon 2.0; zircon.compose ships jvm + wasmJs, Android comes in 2.1"
author: Bruce
date: 2026-10-28
branch: master
cost: { minutes: 17, tokens: 93590 }
id: l-5df2add5
scope: repo
status: active
src: agent
touches:
  - TODO.md
  - ".github/workflows/PUBLISH.yml"
  - ".github/workflows/TEST.yml"
  - CONTRIBUTING.md
  - README.md
  - build.gradle.kts
---

## Decided
- `zircon.compose` publishes for jvm and wasmJs only in 2.0 (`zircon.compose/build.gradle.kts`). Android waits for 2.1: the build machine has no Android SDK and the owner chose not to install one for this release (2026-10-28).
- `zircon.core` and cobalt need no `androidTarget` at all: they are pure Kotlin, and Android consumers resolve the `jvm` variant (Java 17 bytecode). Only the Compose renderer needs a real Android target.

## Tried and ruled out
- Installing the Android SDK into `~/Android/Sdk` now (~1 GB, machine-wide): declined by the owner for 2.0.
- Letting the owner install it later during this release: declined; Android is simply 2.1 scope.

## Watch out
- When 2.1 adds `androidTarget` to `zircon.compose`: `RuntimeShader` needs API 33, so below that fall back to `android.graphics.Canvas.drawVertices` with flat arrays (prototype path C2, history at a8495266b) or `Bitmap.setPixels`; font loading needs an `actual` for `fontFamilyFromBytes`/`createTextMeasurer` (`zircon.compose/src/commonMain/.../tileset/TrueTypeTileset.kt`), and the AGP 9 KMP library plugin wants `ANDROID_HOME` at configuration time, which would break every build on machines without an SDK unless the target is made conditional.
