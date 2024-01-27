# Things to do before next release

## Must have

- [x] Implement Rexpaint loading
- [ ] Implement animation loading
- [ ] Add remaining tilesets to app config and test them
- [ ] Retrofit code examples  
- [ ] Implement texture transform modifiers (crossed-out, underline, border)
- [ ] Fix the buggy components
  - [ ] radio box won't do the `o` when selected without focus (first click)
  - [x] `TextBox` will do an interaction recoloring when `<Space>` is pressed
  - [x] `TextBox` cursor is drawn outside the box
  - [ ] if `CheckBox` is clicked quickly it won't check
- [ ] Use all code examples to check for bugs

## Nice to have

- [x] Refactor java-sytle builders to kotlin builder dsls
- [ ] Refactor the Markov modifier
- [ ] Check the rest of the code for Javaesque patterns and refactor them
- [ ] Make life easier for Java users

## Korge features to ask about

- clipboard
- borderless window
- display grid
- display coordinates


# Release Notes

- Korge renderer added
- Builders were retrofitted
- Java support dropped
- All MPP targets supported
- Obsolete configurations dropped
- add to the docs that the GameComponent was removed and now a ComponentRenderer can be created instead
- `TextBox` was removed as it was made redundant by `HBox` / `VBox`
- Code example with improved builders:
```kotlin
ComponentStyleSetBuilder.newBuilder()
        .withDefaultStyle(
            StyleSetBuilder.newBuilder()
                .withForegroundColor(primaryForegroundColor)
                .withBackgroundColor(TileColor.transparent())
                .build()
        )
        .withDisabledStyle(
            StyleSetBuilder.newBuilder()
                .withForegroundColor(primaryForegroundColor.desaturate(.85).darkenByPercent(.1))
                .withBackgroundColor(TileColor.transparent())
                .build()
        )
        .build()
        
// vs
componentStyleSet {
    defaultStyle = styleSet {
        foregroundColor = primaryForegroundColor
        backgroundColor = TileColor.transparent()
    }
    disabledStyle = styleSet {
        foregroundColor = primaryForegroundColor.desaturate(.85).darkenByPercent(.1)
        backgroundColor = TileColor.transparent()
    }
}
```
