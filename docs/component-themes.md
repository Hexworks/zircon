# Component Themes

Currently we have `ColorTheme`s in Zircon which is a very limited theming feature: it has fixed 5 colors, and nothing else. What we'd like to achieve is to be able to

- set both the `ComponentRenderingStrategy` and the `ComponentStyleSet`s for each `Component` when a theme is switched. This allows for customizing the look and feel of the components, not just the colors
- allow the theme to set colors on its own for each `Component` based on its `ComponentState` using a `Palette`.

## How it works

Component themes should build on top of the existing functionality. This means that `ColorTheme` and component themes will co-exist until we sunset `ColorTheme`.

Component Theme support should be implemented using a creational pattern (abstract factory?). We'll have a pre-selected set of `Component`s and `Fragment`s that we group into an interface, and each concrete component theme will have one of those builder objects, so the user should be able to do something like:

```kt
val grid = tileGrid {
    componentTheme = ComponentThemes.tinyBrutalism {
        theme = ThemeSettings.SYSTEM // LIGHT or DARK too
    }
    // ...
}

val components = tileGrid.componentBuilderFactory

val button = components.button {
  // configure the button as before
}
```

> [!IMPORTANT]
> I'm not sure about the naming, we should figure this out when implementing

The _builder_ object created this way is an implementation of a `ComponentBuilderFactory` (we should figure out a good name for this) interface that contains a builder function for each supported component:

```kt
interface ComponentBuilderFactory {

    fun accordion(): AccordionBuilder

    fun alert(): AlertBuilder

    fun badge(): BadgeBuilder

    fun button(): ButtonBuilder

    fun card(): CardBuilder

    fun checkbox(): CheckboxBuilder

    fun input(): InputBuilder

    fun progressBar(): ProgressBarBuilder

    fun select(): SelectBuilder

    // ... etc
}
```

When a specific factory function is called (eg: `builder.alert()`) then the way the theme works is that it pre-configures the corresponding component `Builder` with theme specific renderers and decorations. We already have this functionality so we'll just wrap it with the new setup. This is some pseudo-code that shows how it could work internally:

```kt
button {
    this.componentRenderer = MyCustomRenderer()
    this.componentStyleSet = ThisThemesComponmentStyleSet()
    this.decorationRenderers = listOf(thisRenderer(), thatRenderer())
}
```

> [!IMPORTANT]
> Currently when a component is created its `ComponentStyleSet` and `ComponentRenderingStrategy` is set. The style set can be changed, but the rendering strategy is not. We need to fix this.

### Theme Change

The `ComponentBuilderFactory` should retain a reference to all `Component`s that was built by it. This is necessary because the user can change a component theme by doing:

```kt
grid.componentTheme = ComponentThemes.solarized {
    theme = ThemeSettings.SYSTEM // LIGHT or DARK too
    variant = Solarized.CYAN
}
```

When a theme changes for a `grid` its `componentTheme` property is overwritten, and all the `ComponentStyleSet`s and `ComponentRenderingStrategy` objects are replaced for each component by the ones that come from the new theme. This shouldn't be part of the public api (eg: users shouldn't be able to set the style sets and renderers manually), it should be only known for the system components that take part in this abstraction.

## Components vs Fragments

`ComponentBuilderFactory` is not limited to producing `Component`s only. For example `VerticalTabBarBuilder` produces `TabBar`s which are `Fragment`s.

## Variants

We should add the option to choose from `LIGHT` and `DARK` variants (see above in `ThemeSettings`). `SYSTEM` should take a look at the environment and try to figure out what the system theme is.

Some theme can also have `Variant`s. A good example is _Solarized_. We already have `ColorTheme`s that have many Solarized variants that only differ int he accent color.

> [!NOTE]
> This is something we have to research a bit as I'm not sure how we should implement it.

## Component Refactoring

Some of the already-existing `Component`s are very similar to each other and they might need some rafactoring to reduce duplication (of code and logic). We need to figure out if it is worth the effort.

- `HBox` and `VBox` are the same interafce and the implementations are mostly duplicated code, the only difference is the implementation of `addComponent` and `reorganizeComponents`
- `CheckBox`, `RadioButton` and `ToggleButton` are almost the same. The only difference (apart from the visual appearance) between them is the states (`CHECKING`, `CHECKED`, `UNCHECKING`, `UNCHECKED` vs `PRESSED`, `SELECTED`, `NOT_SELECTED` vs plain `Selectable`). In the case of `CheckBox` and `RadioButton` the states are redundant as they are only used for rendering, and they can be calculated from the `isSelected` property and the `ComponentState`. I think these 3 can be collapsed to a single `BinarySelector` component by introducing an `allowUnselect` property (`false` for `RadioButton`). The difference then will be just between the renderers. We can still keep the `checkBox()`/`radioButton()`/`toggleButton()` functions, but they should be parameterizing the same component differently. As for the `RadioButtonGroup`, it should be generalized to an `ExclusiveSelectionGroup`. `Selectable` components can be accepted by the group and it ensures that only `1` component is in the `isSelected` state from the group. We also need to refactor `Selectable` to also include a `key` which has to be unique between elements in a group. To make this backward compatible the `key` should be calculated by default based on the normalized text and disallow creation of the component if no `key` is set or can be derived.
- `Header`, `Label`, `ListItem` and `Paragraph` are a similar story, but they are only semantically different, and can be implemented from the same component with tifferent renderers. Not sure on the name for the final component though.
- We have `NumberInput`, but no simple `Input`. I think we should have a simple 1 line input component, and specialized `Fragment`s that add the extra features.
- `TextArea` is broken currently, the cursor is displayed 1 row above if there are decorations around the `TextArea` and there are other issues with scrolling and typing, we need to fix and test the component
- `ScrollBar` suffers from similar problems as the `TextArea`

## Test Implementation

For the first implementation I'd like to try and implement [Neobrutalism](https://hyperui.dev/components/neobrutalism/) as our first component theme. Mockups and the glyph mapping are in [neo-brutalism.md](neo-brutalism.md).

More info on the components:

- Accordion: out of scope
- Select: out of scope
- Alert: Probably a `Fragment` that has a `Panel`, an `Icon` and a `Label`
- Badge: `HBox` with a `Label` and optional `Icon` (left) and `Button` (right). When pressing the `Button` the Badge is `detach`ed.
- Button: `Button`
- Card: `VBox` with a `HBox` that has an `Icon` + `Label` plus a `Header` and a `Paragraph`. We should skip the dashed variant. Retro window should be split into its own `Window` that is a `VBox` with a `HBox` a `Header` and a `Paragraph`
- Checkbox: `VBox` with a `CheckBox` in it with an optional `Paragraph` below it. The Grouped variant is out of scope
- Input: `VBox` with a `Label` and a `HBox`. The `HBox` has an `Input` (see info about implementation above) by default, and it can have an optional `Icon` or a `Button`
- Progressbar: `VBox` that has a `ProgressBar` in it with an optional `HBox` above it with 2 `Label`s.
- Tabs: `HorizontalTabBar` / `VerticalTabBar` with custom renderers
- Textarea: `Textarea` (by default) or `VBox` with a `Label` (optional), a `Textarea` and a `HBox` (optional) with the `Button`s
