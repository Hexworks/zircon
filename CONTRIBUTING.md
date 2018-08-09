# Contributing to Zircon

If you would like to contribute code you can do so through GitHub by forking the repository and sending a pull request.
When submitting code, please make every effort to follow existing conventions and style in order to keep the code as readable as possible.

## Code
- Zircon is written in Kotlin but you can add your contribution using *Java*
- If you contribute *Java* code put it into `src/main/java`. If you kontribute Kotlin code it should go into `src/main/kotlin`
- If you write implementation code for something put it into the `org.hexworks.zircon.internal` package
  Conversely interfaces and code which expresses an external api should go to the `org.hexworks.zircon.api` package
- If you add implementation code try to follow [the design philosophy behind Zircon](https://github.com/Hexworks/zircon/wiki/The-design-philosophy-behind-Zircon)
- Make sure that you add unit tests for your code and
- Check whether the build passes when creating a pull request

## Submitting a PR
- For every PR there should be an accompanying issue which the PR solves
- The PR itself should only contain code which is the solution for the given issue
- If you are a first time contributor check if there is a [suitable issue](https://github.com/Hexworks/zircon/issues?q=is%3Aissue+is%3Aopen+label%3A%22good+first+issue%22) for you

## License

By contributing your code, you agree to license your contribution under the terms of the [MIT](https://github.com/Hexworks/zircon/blob/master/LICENSE) license.
All files are released with the MIT license.
