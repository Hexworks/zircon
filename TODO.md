# Things to do before next release

## Must have

- [ ] Implement Rexpaint loading (multiplatform)
- [ ] Implement animation loading (multiplatform)
- [ ] Create a new tileset format without yaml files (`JSON Schema`?)
- [ ] Create a loader for the new tileset format
- [ ] Retrofit code examples
- [ ] Fix the buggy components
  - [ ] radio box won't do the `o` when selected without focus (first click)
  - [x] `TextBox` will do an interaction recoloring when `<Space>` is pressed
  - [x] `TextBox` cursor is drawn outside the box
  - [ ] if `CheckBox` is clicked quickly it won't check
- [ ] Use all code examples to check for bugs
- [ ] Remove transparency support
- [ ] Remove default texture transformers
- [ ] Refactor `Tile` to a `sealed class`
- [ ] Figure out a more flexible way of theming components
- [ ] Add the option to bind the activation of interactive
      components to a shortcut


## Nice to have

- [x] Refactor java-sytle builders to kotlin builder dsls
- [ ] Create abstract factories that contain factories for the appropriate things
      based on the configuration (eg: no `StackedTile`s for terminal renderers)
- [ ] Refactor the Markov modifier
- [ ] Check the rest of the code for Javaesque patterns and refactor them
- [ ] Make life easier for Java users