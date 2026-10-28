# Contributing to Zircon

If you would like to contribute code you can do so through GitHub by forking the repository and sending a pull request.
When submitting code, please make every effort to follow existing conventions and style in order to keep the code as readable as possible.

## Code

- Zircon is written in Kotlin (Multiplatform). Everything lives in `commonMain` unless a platform API is unavoidable;
  then use `expect`/`actual` next to it.
- Public API goes into the `org.hexworks.zircon.api` package, implementations into `org.hexworks.zircon.internal`.
  Nothing from `internal` may appear in a public signature; `./gradlew check` enforces this.
- `zircon.core` compiles in explicit API mode, so public declarations need a visibility modifier and a return type.
  Its binary API is tracked in `zircon.core/api/zircon.core.api`: run `./gradlew apiDump` after an intentional API
  change and commit the result.
- Builders are Kotlin DSLs with plain `var` properties (`button { text = "OK" }`), named after the thing they build.
- Add unit tests in `commonTest`. Rendering changes in `zircon.compose` are covered by golden images; run the JVM tests
  with `UPDATE_GOLDENS=1` after an intentional visual change and commit the new PNGs.
- Check that `./gradlew check` passes before opening a pull request.

## Submitting a PR

- For every PR there should be an accompanying issue which the PR solves
- The PR itself should only contain code which is the solution for the given issue
- If you are a first time contributor check if there is a [suitable issue](https://github.com/Hexworks/zircon/issues?q=is%3Aissue+is%3Aopen+label%3A%22good+first+issue%22) for you

## License

By contributing your code, you agree to license your contribution under the terms of the
[Apache 2.0](https://github.com/Hexworks/zircon/blob/master/LICENSE) license.
