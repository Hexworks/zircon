# Zircon - A user-friendly Text GUI & Tile Engine [![Tweet](https://img.shields.io/twitter/url/http/shields.io.svg?style=social)](https://twitter.com/intent/tweet?text=Do%20you%20plan%20to%20make%20a%20roguelike%3F%20Look%20no%20further.%20Zircon%20is%20the%20right%20tool%20for%20the%20job.&url=https://github.com/Hexworks/zircon&hashtags=games,roguelikes)

<img src="https://cdn.discordapp.com/attachments/205245036084985857/481213000540225550/full_example.gif" />

---

*Note that* this library was inspired by [Lanterna](https://github.com/mabe02/lanterna). 
Check it out if you are looking for a *terminal emulator* instead. 

---

Need info? Check the [Wiki](https://github.com/Hexworks/zircon/wiki)
 | or [Create an issue](https://github.com/Hexworks/zircon/issues/new)
 | Check [our project Board](https://github.com/Hexworks/zircon/projects/2)
 | [Ask us on Discord][discord] | Support us on [Patreon](https://www.patreon.com/hexworks)

[![][circleci img]][circleci]
[![][maven img]][maven]
[![](https://jitpack.io/v/Hexworks/Zircon.svg)](https://jitpack.io/#Hexworks/Zircon)
[![][license img]][license]
[![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome)

---

## Table of Contents

- [Getting Started](https://github.com/Hexworks/zircon#getting-started)
  - [Some rules of thumb](https://github.com/Hexworks/zircon#some-rules-of-thumb)
  - [Creating an Application](https://github.com/Hexworks/zircon#creating-an-application)
  - [Working with Screens](https://github.com/Hexworks/zircon#working-with-screens)
  - [Components](https://github.com/Hexworks/zircon#components)
  - [Additional features](https://github.com/Hexworks/zircon#additional-features)
    - [Layering](https://github.com/Hexworks/zircon#layering)
    - [Input handling](https://github.com/Hexworks/zircon#input-handling)
    - [Shape and box drawing](https://github.com/Hexworks/zircon#shape-and-box-drawing)
    - [Fonts and tilesets](https://github.com/Hexworks/zircon#fonts-and-tilesets)
    - [REXPaint file loading](https://github.com/Hexworks/zircon#rexpaint-file-loading)
    - [Color themes](https://github.com/Hexworks/zircon#color-themes)
    - [Animations (BETA)](https://github.com/Hexworks/zircon#animations-beta)
    - [The API](https://github.com/Hexworks/zircon#the-api)
- [Road map](https://github.com/Hexworks/zircon#road-map)
- [License](https://github.com/Hexworks/zircon#license)
- [Credits](https://github.com/Hexworks/zircon#credits)

## Getting Started

If you want to work with Zircon you can add it to your project as a dependency.
For this you need to add the Jitpack repository and the dependency itself

from Maven:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>org.hexworks.zircon</groupId>
        <artifactId>zircon.core-jvm</artifactId>
        <version>2018.12.25-XMAS</version>
    </dependency>
    <dependency>
        <groupId>org.hexworks.zircon</groupId>
        <artifactId>zircon.jvm.swing</artifactId>
        <version>2018.12.25-XMAS</version>
    </dependency>
</dependencies>

```

or you can also use Gradle:

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'org.hexworks.zircon:zircon.core-jvm:2018.12.25-XMAS'
    implementation 'org.hexworks.zircon:zircon.jvm.swing:2018.12.25-XMAS'
}
```

Want to use a `PREVIEW` version instead?
 Check [this Wiki page](https://github.com/Hexworks/zircon/wiki/Release-process-and-versioning-scheme#snapshot-releases)

### Some rules of thumb

Before we start there are some guidelines which can help you if you are stuck:

- If you want to build something (a `TileGraphics`, a `Component` or anything which is part of the public API) it
  is almost sure that there is a `Builder` or a `Factory` for it. The convention is that if you want to create a
  `Tile` for example, then you can use the `Tiles` class to do so. (so it is the plural form of the
  thing which you want to build). Your IDE will help you with this. These classes reside in the 
  `org.hexworks.zircon.api` package. There are some classes which are grouped together into a single utility class
  however. With `Components` you can obtain `Builder`s for all `Component`s like `Components.panel()` or
  `Components.checkBox()`. Likewise you can use `DrawSurfaces` to obtain builders for `TileGraphcs` and
  `TileImage`.
- If you want to work with external files like tilesets or REXPaint files check the same package 
  (`org.hexworks.zircon.api`), and look for classes which end with `*Resources`. There are a bunch of
  built-in tilesets for example which you can choose from but you can also load your own.
  The rule of thumb is that if you need something external there is probably a `*Resources` class
  for it (like the [CP437TilesetResources]).
- You can use *anything* you can find in the [API][api] package, they are part of the public API, and safe to use. The
  [internal][internal] package however is considered private to *Zircon* so don't depend on anything in it because
  it can change any time.
- Some topics are explained in depth on the [Wiki](https://github.com/Hexworks/zircon/wiki)
- If you want to see some example code look [here][examples].  
- If all else fails read the javadoc. API classes are well documented.
- If you have any problems which are not answered here feel free to ask us at the [Hexworks Discord server][discord]. 
  

### Creating an Application

In Zircon almost every object you might want to use has a helper class for building it.
This is the same for [Application]s as well so let's create one using the [SwingApplications] class:

> Note that these examples reside in the `org.hexworks.zircon.examples.docs` package in the `zircon.examples` project,
> you can try them all out.

```java
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.Application;

public class CreatingAnApplication {

    public static void main(String[] args) {

        Application application = SwingApplications.startApplication();
    }
}
```

Running this snippet will result in this screen:

![](https://cdn.discordapp.com/attachments/363771631727804416/477466202982055939/CreatingAnApplication.png)

Not very useful, is it? So what happens here? An [Application] is just an object which has a [Renderer]
for rendering [Tile]s on your screen), and a [TileGrid], which is the main interface which you will use to
interact with Zircon. An [Application] is responsible for *continuously rendering* the contents of the grid
on the screen. Here we use the *Swing* variant, but there are other types in the making (one for LibGDX,
and one which works in the browser).

Since most of the time you don't care about the [Application] itself, there is a function for creating a
[TileGrid] directly:

```java
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.grid.TileGrid;

public class CreatingATileGrid {

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid();
    }
}
```

Now let's see how we can specify how a [TileGrid] is created. We'll use the [AppConfigs] helper for this:

```java
;
import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.Application;

public class CreatingAnApplication {

    public static void main(String[] args) {

        Application application = SwingApplications.startApplication(
                AppConfig.newConfig()
                        .withSize(Size.create(30, 20))
                        .withDefaultTileset(CP437TilesetResources.rexPaint16x16())
                        .withClipboardAvailable(true)
                        .build());
    }
}
```

Adding and formatting [Tile]s is very simple:

```java
;
import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.Tiles;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.grid.TileGrid;

public class CreatingAnApplication {

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid(
                AppConfig.newConfig()
                        .withSize(Size.create(10, 10))
                        .withDefaultTileset(CP437TilesetResources.rexPaint16x16())
                        .build());

        tileGrid.setTileAt(
                Position.create(2, 3),
                Tile.newBuilder()
                        .withBackgroundColor(ANSITileColor.CYAN)
                        .withForegroundColor(ANSITileColor.WHITE)
                        .withCharacter('x')
                        .build());

        tileGrid.setTileAt(
                Position.create(3, 4),
                Tile.newBuilder()
                        .withBackgroundColor(ANSITileColor.RED)
                        .withForegroundColor(ANSITileColor.GREEN)
                        .withCharacter('y')
                        .build());

        tileGrid.setTileAt(
                Position.create(4, 5),
                Tile.newBuilder()
                        .withBackgroundColor(ANSITileColor.BLUE)
                        .withForegroundColor(ANSITileColor.MAGENTA)
                        .withCharacter('z')
                        .build());
    }
}

```      

Running the above code will result in something like this:

![](https://cdn.discordapp.com/attachments/363771631727804416/477469640205926401/CreatingATileGrid.png)

As you can see there is a helper for every class which you might want to use. Here we used `Position.create`
to create a [Position], `Size.create` for creating [Size]s and the [TileBuilder] to create tiles.

A `Position` denotes a coordinate on a `TileGrid`, so for example a `Position` of (`2`, `3`) points to the 3rd
column and the 4th row (x, y) on the grid.

> Note that `Position` indexing starts from zero.

Conversely a `Size` denotes an area with a width and a height. These two classes are used throughout Zircon.

A [Tile] is the most basic graphical unit Zircon supports. In most cases it is a simple character with a foreground
and a background color (like in the example above).

In addition to colors and characters you can also use [Modifier]s in your [Tile]s.

> A lot of fancy stuff can be done with [Modifier]s, like this:
>  
> ![](https://cdn.discordapp.com/attachments/363771631727804416/477470683513880576/modifiers.gif)
> 
> If interested check out the code examples [here][examples].

 
> Tileset (and by extension resource) handling in Zircon is very simple and if you are interested in how to
load your custom fonts and other resources take a look at the [Resource handling wiki page][resource-handling].

### Working with Screens

[TileGrid]s alone won't suffice if you want to get any serious work done since they are rather rudimentary.

A [Screen] has its own buffer and it can be `display`ed on
a [TileGrid] any time. This means that you can have multiple [Screen]s at the same time representing your actual
game screens. *Note that* only *one* [Screen] can be displayed at a given moment. `display`ing one deactivates
the previous [Screen].

Let's create a [Screen] and fill it up with some stuff:

```java
import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.graphics.TileGraphics;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.screen.Screen;

public class CreatingAScreen {

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid(
                AppConfig.newConfig()
                        .withSize(Size.create(20, 8))
                        .withDefaultTileset(CP437TilesetResources.wanderlust16x16())
                        .build());

        final Screen screen = Screen.create(tileGrid);

        final ColorTheme theme = ColorThemes.adriftInDreams();

        final TileGraphics image = DrawSurfaces.tileGraphicsBuilder()
                .withSize(tileGrid.getSize())
                .build()
                .fill(Tile.newBuilder()
                        .withForegroundColor(theme.getPrimaryForegroundColor())
                        .withBackgroundColor(theme.getPrimaryBackgroundColor())
                        .withCharacter('~')
                        .build());

        screen.draw(image, Positions.zero());

        screen.display();
    }
}
```

and we've got a nice ocean:

![](https://cdn.discordapp.com/attachments/363771631727804416/477475680594952223/CreatingAScreen.png)

What happens here is that we:

- Create a [Screen]
- Fetch a nice [ColorTheme] which has colors we need
- Create a [TileGraphics] with the colors added and fill it with `~`s
- Draw the graphic onto the [Screen]

For more explanation about these jump to the [Zircon Crash Course](https://github.com/Hexworks/zircon/wiki/A-Zircon-Crash-Course).

> You can do so much more with [Screen]s. If interested then check out [A primer on Screens][screen-primer]
on the Wiki! 

### Components

Zircon supports a bunch of [Component]s out of the box:

- `Button`: A simple clickable component with corresponding event listeners
- `CheckBox`: Like a `Button` but with checked / unchecked state
- `Header`: Like a label but this one has emphasis (useful when using [ColorTheme]s)
- `Label`: Simple component with text
- `LogArea`: Component with a list of items. New items can be added, and they will be srolled.
- `Panel`: A [Container] which can hold multiple [Components]
- `RadioButtonGroup` and `RadioButton`: Like a `CheckBox` but only one can be selected at a time
- `TextArea`: Similar to a text area in HTML this [Component] can be written into
- `TextBox`: A `TextBox` is more like a hypertext document where you can add elements with semantic
  meaning. It supports adding `Header`s, `Paragraph`s, `ListItem`s and even `Button`s

These components are rather simple and you can expect them to work in a way you might be familiar with:

- You can click on them (press and release are different events)
- You can attach event listeners on them
- Zircon implements focus handling so you can between the components using the `[Tab]` key
 (forwards) or the `[Shift]+[Tab]` key stroke (backwards).
- Components can be hovered and they can change their styling when you do so

Let's look at an example (notes about how it works are in the comments):

```java
import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.component.CheckBox;
import org.hexworks.zircon.api.component.Header;
import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.screen.Screen;

public class UsingComponents {

    public static void main(String[] args) {

        final TileGrid tileGrid = SwingApplications.startTileGrid(
                AppConfig.newConfig()
                        .withSize(Size.create(34, 18))
                        .withDefaultTileset(CP437TilesetResources.aduDhabi16x16())
                        .build());
        final Screen screen = Screen.create(tileGrid);

        Panel panel = Components.panel()
                .wrapWithBox(true) // panels can be wrapped in a box
                .withTitle("Panel") // if a panel is wrapped in a box a title can be displayed
                .wrapWithShadow(true) // shadow can be added
                .withSize(Size.create(32, 16)) // the size must be smaller than the parent's size
                .withPosition(Positions.offset1x1())
                .build(); // position is always relative to the parent

        final Header header = Components.header()
                // this will be 1x1 left and down from the top left
                // corner of the panel
                .withPosition(Positions.offset1x1())
                .withText("Header")
                .build();

        final CheckBox checkBox = Components.checkBox()
                .withText("Check me!")
                .withPosition(Position.create(0, 1)
                        // the position class has some convenience methods
                        // for you to specify your component's position as
                        // relative to another one
                        .relativeToBottomOf(header))
                .build();

        final Button left = Components.button()
                .withPosition(Position.create(0, 1) // this means 1 row below the check box
                        .relativeToBottomOf(checkBox))
                .withText("Left")
                .build();

        final Button right = Components.button()
                .withPosition(Position.create(1, 0) // 1 column right relative to the left BUTTON
                        .relativeToRightOf(left))
                .withText("Right")
                .build();

        panel.addComponent(header);
        panel.addComponent(checkBox);
        panel.addComponent(left);
        panel.addComponent(right);

        screen.addComponent(panel);

        // we can apply color themes to a screen
        screen.applyColorTheme(ColorThemes.monokaiBlue());

        // this is how you can define interactions with a component
        left.onMouseReleased((mouseAction -> screen.applyColorTheme(ColorThemes.monokaiGreen())));

        right.onMouseReleased((mouseAction -> screen.applyColorTheme(ColorThemes.monokaiViolet())));

        // in order to see the changes you need to display your screen.
        screen.display();
    }
}
```

And the result will look like this:

![](https://cdn.discordapp.com/attachments/363771631727804416/363813193488924673/image.png)

You can check out more examples [here][examples]. Here are some
screenshots of them:

#### Tileset example:
![](https://cdn.discordapp.com/attachments/277739394641690625/348400285879894018/image.png)

#### Animations:
![](https://cdn.discordapp.com/attachments/277739394641690625/360086607380086807/GIF.gif)

#### Components:
![](https://cdn.discordapp.com/attachments/335444788167966720/361297190863241218/GIF.gif)

## Additional features

There are a bunch of other things Zircon can do which are not detailed in this README but you can read about them
in either the source code or the [Wiki](https://github.com/Hexworks/zircon/wiki):

### Layering
Both the [TileGrid] and the [Screen] interfaces implement [Layerable] which means that you can add [Layer]s
on top of them. Every [Layerable] can have an arbitrary amount of [Layer]s. [Layer]s are like [TileGraphics]
and you can also have transparency in them which can be used to create fancy effects. [Component]s are also
[Layer]s themselves. For more details check the [layers][layers] Wiki page.

> Note that when creating `Layer`s you can set their `offset` from the builder but after attaching it to a
 `TileGrid` or `Screen` you can change its position by calling `moveTo` with a new `Position`.

### Input handling
Both the [TileGrid] and the [Screen] interfaces implement [InputEmitter] which means that they re-emit all inputs
from your users (key strokes and mouse actions) and you can listen on them. There is a [Wiki page][inputs]
with more info.

### Shape and box drawing
You can draw [Shape]s like rectangles and triangles by using one of the [ShapeFactory] implementations.
Check the corresponding [Wiki page][shapes] for more info.

### Fonts and tilesets
Zircon comes with a bunch of built-in fonts tilesets. These come in 2 flavors:

- CP437 tilesets *(More on using them [here](https://github.com/Hexworks/zircon/wiki/Resource-Handling#cp437-tilesets))*
- True Type Fonts
- and Graphic tilesets *(Usage info [here](https://github.com/Hexworks/zircon/wiki/Resource-Handling#graphic-tilesets))*

Read more about them in the [resource handling Wiki page][resource-handling] if you want to know more
or if you want to use your own tilesets and fonts.

Zircon also comes with **its own tileset format (`ztf`: Zircon Tileset Format)** which is **very easy to use**. 
It is detailed [here](https://github.com/Hexworks/zircon/wiki/Resource-Handling#graphic-tilesets).

### REXPaint file loading
REXPaint files (`.xp`) can be loaded into Zircon `Layer`s. Read about this feature
 [here](https://github.com/Hexworks/zircon/wiki/Resource-Handling#rexpaint-files).

### Color themes
Zircon comes with a bunch of built-in color themes which you can apply to your components.
If interested you can read more about how this works [here][color-themes].

### Animations (BETA)
Animations are a beta feature. More info [here][animations].

### The API
If you just want to peruse the Zircon API just navigate [here][api].
Everything which is intended to be the public API is there.

## Road map
If you want to see a new feature feel free to [create a new Issue](https://github.com/Hexworks/zircon/issues/new)
or discuss it with us on [discord][discord].
Here are some features which are either under way or planned:

- libGDX support
- Layouts for `Component`s with resizing support
- Next to `ColorTheme`s we'll introduce `ComponentTheme`s as well (custom look and feel for your components)
- Floating and modal `Component`s

## License
Zircon is made available under the [MIT License](http://www.opensource.org/licenses/mit-license.php).

## Credits
Zircon is created and maintained by Adam Arold, Milan Boleradszki, Gergely Lukacsy and Coldwarrl

*We're open to suggestions, feel free to message us on [Discord][discord] or open an issue.*
*Pull requests are also welcome!*

Zircon is powered by:

<a href="https://www.jetbrains.com/idea/">
    <img src="https://github.com/Hexworks/zircon/blob/master/images/idea_logo.png" width="40" height="40" />
</a>
<a href="https://kotlinlang.org/">
    <img src="https://github.com/Hexworks/zircon/blob/master/images/kotlin_logo.png" width="40" height="40" />
</a>
<a href="https://www.yourkit.com/java/profiler/">
    <img src="https://github.com/Hexworks/zircon/blob/master/images/yklogo.png" width="168" height="40" />
</a>

### Thanks

Thanks to the folks over at [Dwarf Fortress Tileset Repository](http://dwarffortresswiki.org/Tileset_repository)
for letting us bundle their tilesets.

Thanks to Kyzrati who let us bundle the [REXPaint Tilesets](https://www.gridsagegames.com/rexpaint/)
into Zircon!

Zircon comes bundled with the [Nethack Tileset](https://nethackwiki.com/wiki/Tileset).

Some True Type fonts are used from [Google Fonts](https://fonts.google.com/).

Thanks to *VileR* for the [Oldschool Font Pack](https://int10h.org/oldschool-pc-fonts/) which
we bundled into Zircon.

[circleci]:https://circleci.com/gh/Hexworks/zircon
[circleci img]:https://circleci.com/gh/Hexworks/zircon/tree/master.svg?style=shield

[codecov]:https://codecov.io/github/Hexworks/zircon?branch=master
[codecov img]:https://codecov.io/github/Hexworks/zircon/coverage.svg?branch=master

[license]:https://github.com/Hexworks/zircon/blob/master/LICENSE
[license img]:https://img.shields.io/badge/License-MIT-green.svg

[maven]:https://search.maven.org/search?q=g:org.hexworks.zircon
[maven img]:https://maven-badges.herokuapp.com/maven-central/org.hexworks.zircon/zircon.jvm.swing/badge.svg

[screen-primer]:https://github.com/Hexworks/zircon/wiki/A-primer-on-Screens
[text-images]:https://github.com/Hexworks/zircon/wiki/How-to-work-with-TileGraphics

[discord]:https://discord.gg/vSNgvBh
[examples]:https://github.com/Hexworks/zircon/tree/master/zircon.jvm.examples
[api]:https://github.com/Hexworks/zircon/tree/master/zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api
[internal]:https://github.com/Hexworks/zircon/tree/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/internal

[resource-handling]:https://github.com/Hexworks/zircon/wiki/Resource-Handling
[design-philosophy]:https://github.com/Hexworks/zircon/wiki/The-design-philosophy-behind-Zircon
[color-themes]:https://github.com/Hexworks/zircon/wiki/Working-with-ColorThemes
[layers]:https://github.com/Hexworks/zircon/wiki/How-Layers-work
[inputs]:https://github.com/Hexworks/zircon/wiki/Input-handling
[components]:https://github.com/Hexworks/zircon/wiki/The-component-system
[shapes]:https://github.com/Hexworks/zircon/wiki/Shapes
[animations]:https://github.com/Hexworks/zircon/wiki/Animation-support
[behaviors]:https://github.com/Hexworks/zircon/wiki/Behaviors

[Animation]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/animation/Animation.kt
[AnimationHandler]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/animation/AnimationHandler.kt
[AppConfigs]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/AppConfig.kt
[Application]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/application/Application.kt
[ANSITileColor]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/color/ANSITileColor.kt
[Boundable]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/behavior/Boundable.kt
[Button]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/component/Button.kt
[ColorTheme]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/component/ColorTheme.kt
[Clearable]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/behavior/Clearable.kt
[Component]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/component/Component.kt
[Container]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/component/Container.kt
[BuiltInCP437TilesetResource]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/resource/BuiltInCP437TilesetResource.kt
[Drawable]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/behavior/Drawable.kt
[DrawSurface]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/graphics/TileGraphics.kt
[Input]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/input/Input.kt
[InputEmitter]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/behavior/InputEmitter.kt
[Layerable]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/behavior/Layerable.kt
[Layer]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/graphics/Layer.kt
[Layers]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/Layers.kt
[Modifier]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/modifier/Modifier.kt
[Modifiers]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/Modifiers.kt
[Panel]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/component/Panel.kt
[Position]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/data/Position.kt
[Renderer]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/internal/renderer/Renderer.kt
[Screen]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/screen/Screen.kt
[Shape]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/shape/Shape.kt
[ShapeFactory]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/shape/ShapeFactory.kt
[ShutdownHook]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/behavior/ShutdownHook.kt
[Size]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/data/Size.kt
[Styleable]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/behavior/Styleable.kt
[StyleSet]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/graphics/StyleSet.kt  
[SwingApplications]:https://github.com/Hexworks/zircon/blob/master/zircon.jvm.swing/src/main/kotlin/org/hexworks/zircon/api/SwingApplications.kt
[TileColor]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/color/TileColor.kt
[TileColors]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/TileColors.kt
[Tile]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/data/Tile.kt
[TileGraphics]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/graphics/TileGraphics.kt
[TileGrid]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/grid/TileGrid.kt
[TileBuilder]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/builder/data/TileBuilder.kt
[TilesetOverride]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/behavior/TilesetOverride.kt
[TypingSupport]:https://github.com/Hexworks/zircon/blob/master/zircon.core/common/src/main/kotlin/org/hexworks/zircon/api/behavior/TypingSupport.kt
