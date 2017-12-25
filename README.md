# Zircon [![Tweet](https://img.shields.io/twitter/url/http/shields.io.svg?style=social)](https://twitter.com/intent/tweet?text=Do%20you%20plan%20to%20make%20a%20roguelike%3F%20Look%20no%20further.%20Zircon%20is%20the%20right%20tool%20for%20the%20job.&url=https://github.com/Hexworks/zircon&hashtags=games,roguelikes)

<img src="https://cdn.discordapp.com/attachments/363771631727804416/376372957041393677/Screen_Shot_2017-11-04_at_15.11.08.png" />


*Note that* this library is deeply inspired by [Lanterna](https://github.com/mabe02/lanterna). Check it out if you are looking for a terminal emulator instead. 

---

Need info? Check the [Wiki](https://github.com/Hexworks/zircon/wiki) | or [Create an issue](https://github.com/Hexworks/zircon/issues/new) | Check [our project Board](https://github.com/Hexworks/zircon/projects/2) | [Ask us on Discord][discord]

[![][circleci img]][circleci]
[![][maven img]][maven]
[![][codecov img]][codecov]
[![][license img]][license]
[![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome)

---

## Table of Contents

- [Getting Started](https://github.com/Hexworks/zircon#getting-started)
  - [Some rules of thumb](https://github.com/Hexworks/zircon#some-rules-of-thumb)
  - [Creating a Terminal](https://github.com/Hexworks/zircon#creating-a-terminal)
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
- [A little Crash Course](https://github.com/Hexworks/zircon#a-little-crash-course)
  - [Terminal](https://github.com/Hexworks/zircon#terminal)
  - [Colors and StyleSets](https://github.com/Hexworks/zircon#colors-and-stylesets)
  - [Modifiers](https://github.com/Hexworks/zircon#modifiers)
  - [TextImages](https://github.com/Hexworks/zircon#textimages)
  - [Screens](https://github.com/Hexworks/zircon#screens)
- [Road map](https://github.com/Hexworks/zircon#road-map)
- [License](https://github.com/Hexworks/zircon#license)
- [Credits](https://github.com/Hexworks/zircon#credits)

## Getting Started

If you want to work with Zircon you can add it to your project as a dependency.

from Maven:

```xml
<dependency>
    <groupId>org.codetome.zircon</groupId>
    <artifactId>zircon</artifactId>
    <version>2017.4.0-RELEASE</version>
</dependency>

```

or you can also use Gradle:

```groovy
compile("org.codetome.zircon:zircon:2017.4.0-RELEASE")
```

Want to use a `SNAPSHOT`? Check [this Wiki page](https://github.com/Hexworks/zircon/wiki/The-release-process-and-versioning-scheme)

### Some rules of thumb

Before we start there are some guidelines which can help you if you are stuck:

- If you want to build something (a `TextImage`, a `Component` or anything which is part of the public API) it is almost
  sure that there is a `Builder` or a `*Factory` for it. If you want to build a `TextImage` you can use the `TextImageBuilder` to do so.
  Always look for a `Builder` or a `Factory` (in case of `TextColor`s for example) to create the desired object. Your IDE
  will help you with that
- If you want to work with external files like tilesets or REXPaint files check the [resource package](https://github.com/Hexworks/zircon/tree/master/src/main/kotlin/org/codetome/zircon/api/resource).
  There are a bunch of built-in tilesets for example which you can choose from but you can also load your own. The rule of thumb
  is that if you need something external there is probably a `*Resource` for it (like the [REXPaintResource]).
- Anything in the `api.beta` package is considered a *BETA* feature and is subject to change.
- You can use *anything* you can find in the [API][api] package and they will not change (so your code won't break). The
  [internal][internal] package however is considered private to Zircon so don't depend on anything in it.
- Some topics are explained in depth on the [Wiki](https://github.com/Hexworks/zircon/wiki)
- If you want to see some example code look [here][examples].  
- If all else fails read the javadoc. API classes are rather well documented.
- If you have any problems which are not answered here feel free to ask us at the [Hexworks Discord server][discord]. 
  

### Creating a Terminal

In Zircon almost every object you might want to use has a `Builder` for it.
This is the same for [Terminal]s as well so let's create one using a [TerminalBuilder]:

```java
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.terminal.Terminal;

public class Playground {

    public static void main(String[] args) {

        final Terminal terminal = TerminalBuilder.newBuilder()
                .initialTerminalSize(Size.of(32, 16))
                .build();

        terminal.flush();

    }
}
```

Running this snippet will result in this screen:

![](https://cdn.discordapp.com/attachments/363771631727804416/363772871115538454/image.png)

Adding and formatting content is also very simple:

```java
import org.codetome.zircon.api.Modifiers;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.color.ANSITextColor;
import org.codetome.zircon.api.terminal.Terminal;

public class Playground {

    public static void main(String[] args) {

        final Terminal terminal = TerminalBuilder.newBuilder()
                .initialTerminalSize(Size.of(20, 8))
                .build();

        terminal.enableModifiers(Modifiers.VERTICAL_FLIP);
        terminal.setForegroundColor(ANSITextColor.CYAN);
        terminal.putCharacter('a');
        terminal.resetColorsAndModifiers();

        terminal.setForegroundColor(ANSITextColor.GREEN);
        terminal.enableModifiers(Modifiers.HORIZONTAL_FLIP);
        terminal.putCharacter('b');
        terminal.resetColorsAndModifiers();

        terminal.setForegroundColor(ANSITextColor.RED);
        terminal.enableModifiers(Modifiers.CROSSED_OUT);
        terminal.putCharacter('c');
        terminal.flush();
    }
}
```      

Running the above code will result in something like this:

![](https://cdn.discordapp.com/attachments/363771631727804416/363774881298644995/image.png)

> You can do a lot of fancy stuff with [Modifier]s, like this:
>  
> ![](https://cdn.discordapp.com/attachments/277739394641690625/347280659263389696/modifiers_anim.gif)
> 
> If interested check out the code examples [here][examples].

You might have noticed that the default font is not very nice looking, so let's see what else the [TerminalBuilder] can do for us:

```java
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.builder.DeviceConfigurationBuilder;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.color.ANSITextColor;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.terminal.Terminal;
import org.codetome.zircon.api.terminal.config.CursorStyle;

public class Playground {

    private static final String TEXT = "Hello Zircon!";

    public static void main(String[] args) {

        final Terminal terminal = TerminalBuilder.newBuilder()
                .initialTerminalSize(Size.of(20, 8))
                .deviceConfiguration(DeviceConfigurationBuilder.newBuilder()
                        .cursorColor(ANSITextColor.RED)
                        .cursorStyle(CursorStyle.VERTICAL_BAR)
                        .cursorBlinking(true)
                        .blinkLengthInMilliSeconds(500)
                        .clipboardAvailable(true)
                        .build())
                .font(CP437TilesetResource.WANDERLUST_16X16.toFont())
                .title(TEXT)
                .build();

        terminal.setForegroundColor(ANSITextColor.GREEN);
        terminal.setCursorVisibility(true);
        TEXT.chars().forEach((c) -> terminal.putCharacter((char)c));

        terminal.flush();
    }
}
```

![](https://cdn.discordapp.com/attachments/363771631727804416/363778863488303107/image.png)
 
> Font (and by extension resource) handling in Zircon is very simple and if you are interested in how to load your custom fonts and other resources
take a look at the [Resource handling wiki page][resource-handling].

### Working with Screens

[Terminal]s alone won't suffice if you want to get any serious work done since they are rather rudimentary.

Let's create a [Screen] and fill it up with some stuff:


```java
import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.builder.TextCharacterBuilder;
import org.codetome.zircon.api.builder.TextImageBuilder;
import org.codetome.zircon.api.component.ColorTheme;
import org.codetome.zircon.api.graphics.TextImage;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.resource.ColorThemeResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.api.terminal.Terminal;

public class Playground {


    public static void main(String[] args) {

        final Terminal terminal = TerminalBuilder.newBuilder()
                .initialTerminalSize(Size.of(20, 8))
                .font(CP437TilesetResource.WANDERLUST_16X16.toFont())
                .build();
        final Screen screen = TerminalBuilder.createScreenFor(terminal);

        final ColorTheme theme = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme();

        final TextImage image = TextImageBuilder.newBuilder()
                .size(terminal.getBoundableSize())
                .filler(TextCharacterBuilder.newBuilder()
                        .foregroundColor(theme.getBrightForegroundColor())
                        .backgroundColor(theme.getBrightBackgroundColor())
                        .character('~')
                        .build())
                .build();

        screen.draw(image, Position.DEFAULT_POSITION);

        screen.display();
    }
}
```

and we've got a nice ocean:

![](https://cdn.discordapp.com/attachments/363771631727804416/363797686639394817/image.png)

What happens here is that we:

- Create a [Screen]
- Fetch a nice [ColorTheme] which has colors we need
- Create a [TextImage] with the colors added and fill it with `~`s
- Draw the image onto the [Screen]

> You can do so much more with [Screen]s. If interested then check out [A primer on Screens][screen-primer] on the Wiki! 

### Components

Zircon supports a bunch of [Component]s out of the box:

- Button: A simple clickable component with corresponding event listeners
- CheckBox: Like a BUTTON but with checked / unchecked state
- Label: Simple component with text
- Header: Like a label but this one has emphasis (useful when using [ColorTheme]s)
- Panel: A [Container] which can hold multiple [Components]
- RadioButtonGroup and RadioButton: Like a CheckBox but only one can be selected at a time
- TextBox

These components are rather simple and you can expect them to work in a way you might be familiar with:

- You can click on them (press and release are different events)
- You can attach event listeners on them
- Zircon implements focus handling so you can navigate between the components using the `[Tab]` key (forwards) or the `[Shift]+[Tab]` key stroke (backwards).
- Components can be hovered and they can change their styling when you do so

Let's look at an example (notes about how it works are in the comments):

```java
import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.component.Button;
import org.codetome.zircon.api.component.CheckBox;
import org.codetome.zircon.api.component.Header;
import org.codetome.zircon.api.component.Panel;
import org.codetome.zircon.api.component.builder.ButtonBuilder;
import org.codetome.zircon.api.component.builder.CheckBoxBuilder;
import org.codetome.zircon.api.component.builder.HeaderBuilder;
import org.codetome.zircon.api.component.builder.PanelBuilder;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.resource.ColorThemeResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.api.terminal.Terminal;

public class Playground {


    public static void main(String[] args) {

        final Terminal terminal = TerminalBuilder.newBuilder()
                .initialTerminalSize(Size.of(34, 18))
                .font(CP437TilesetResource.WANDERLUST_16X16.toFont())
                .build();
        final Screen screen = TerminalBuilder.createScreenFor(terminal);

        // We create a Panel which will hold our components
        // Note that you can add components to the screen without a panel as well
        Panel panel = PanelBuilder.newBuilder()
                .wrapWithBox() // panels can be wrapped in a box
                .title("Panel") // if a panel is wrapped in a box a title can be displayed
                .wrapWithShadow() // shadow can be added
                .size(Size.of(32, 16)) // the size must be smaller than the parent's size
                .position(Position.OFFSET_1x1) // position is always relative to the parent
                .build();

        final Header header = HeaderBuilder.newBuilder()
                // this will be 1x1 left and down from the top left
                // corner of the panel
                .position(Position.OFFSET_1x1)
                .text("Header")
                .build();

        final CheckBox checkBox = CheckBoxBuilder.newBuilder()
                .text("Check me!")
                .position(Position.of(0, 1)
                        // the position class has some convenience methods
                        // for you to specify your component's position as
                        // relative to another one
                        .relativeToBottomOf(header))
                .build();

        final Button left = ButtonBuilder.newBuilder()
                .position(Position.of(0, 1) // this means 1 row below the check box
                        .relativeToBottomOf(checkBox))
                .text("Left")
                .build();

        final Button right = ButtonBuilder.newBuilder()
                .position(Position.of(1, 0) // 1 column right relative to the left BUTTON
                        .relativeToRightOf(left))
                .text("Right")
                .build();

        panel.addComponent(header);
        panel.addComponent(checkBox);
        panel.addComponent(left);
        panel.addComponent(right);

        screen.addComponent(panel);

        // we can apply color themes to a screen
        screen.applyColorTheme(ColorThemeResource.TECH_LIGHT.getTheme());

        // this is how you can define interactions with a component
        left.onMouseReleased((mouseAction -> {
            screen.applyColorTheme(ColorThemeResource.ADRIFT_IN_DREAMS.getTheme());
        }));

        right.onMouseReleased((mouseAction -> {
            screen.applyColorTheme(ColorThemeResource.SOLARIZED_DARK_ORANGE.getTheme());
        }));

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

There are a bunch of other stuff Zircon can do which are not detailed in this README but you can read about them
in either the source code or the [Wiki](https://github.com/Hexworks/zircon/wiki):

### Layering
Both the [Terminal] and the [Screen] interfaces implement [Layerable] which means that you can add [Layer]s on top of
them. Every [Layerable] can have an arbitrary amount of [Layer]s. [Layer]s are like [TextImage]s and you can also have
transparency in them which can be used to create fancy effects. Look at the [LayerBuilder] to see how to use them.
For more details check the [layers][layers] Wiki page.

> Note that when creating `Layer`s you can set its `offset` from the builder but after attaching it to a `Terminal` or `Screen` you can change its position by calling `moveTo` with a new `Position`.

### Input handling
Both the [Terminal] and the [Screen] interfaces implement [InputEmitter] which means that they re-emit all inputs from
your users (key strokes and mouse actions) and you can listen on them. There is a [Wiki page][inputs] with more info.

### Shape and box drawing
You can draw [Shape]s like rectangles and triangles by using one of the [ShapeFactory] implementations.
Check the corresponding [Wiki page][shapes] for more info.

### Fonts and tilesets
Zircon comes with a bunch of built-in fonts and tilesets. These come in 3 flavors:

- Physical fonts *(Want to use physical fonts? Check how to use them [here](https://github.com/Hexworks/zircon/wiki/Resource-Handling#physical-fonts))*
- CP437 tilesets *(More on using them [here](https://github.com/Hexworks/zircon/wiki/Resource-Handling#cp437-tilesets))*
- and Graphic tilesets *(Usage info [here](https://github.com/Hexworks/zircon/wiki/Resource-Handling#graphic-tilesets))*

Read more about them in the [resource handling Wiki page][resource-handling] if you want to know more
or if you want to use your own tilesets and fonts.

Zircon also comes with **its own tileset format (`ztf`: Zircon Tileset Format)** which is **very easy to use**. Its usage is detailed [here](https://github.com/Hexworks/zircon/wiki/Resource-Handling#graphic-tilesets).

### REXPaint file loading
REXPaint files (`.xp`) can be loaded into Zircon `Layer`s. Read about this feature [here](https://github.com/Hexworks/zircon/wiki/Resource-Handling#rexpaint-files).

### Color themes
Zircon comes with a bunch of built-in color themes which you can apply to your components.
If interested you can read more about how this works [here][color-themes].

### Animations (BETA)
Animations are a beta feature. More info [here][animations].

### The API

If you just want to peruse the Zircon API just navigate [here][api].
Everything which is intented to be the public API is there.

## How Zircon works

In order to work with Zircon you should get familiar with the core concepts. 
Zircon provides multiple layers of abstractions and it depends on your needs which one you should pick.

### Terminal
At the lowest level Zircon provides the [Terminal] interface. This provides you with a surface on which 
you can draw [TextCharacter]s. A [TextCharacter] is basically a character (like an `x`) with additional
metadata like `foregroundColor` and `backgroundColor`. This surface sits on top of a GUI layer and
completely abstracts away how that layer works. For example the default implementation of the [Terminal] 
interface uses Swing under the hood. The main advantage of using [Terminal]s is that by implementing all
its methods you can swap Swing with something else (like SWT) and use **all** higher
level abstractions on top of it (like [Screen]s) which depend on [Terminal] (more on [Screen]s later).
Working with [Terminal]s is *very* simple but somewhat limited. A [Terminal] is responsible for:

- drawing characters (by position or by cursor) on the screen
- handling inputs (keyboard and mouse) which are emitted by the GUI layer
- handling the cursor which is visible to the user
- handling [Layer]ing
- storing style information
- drawing [TextImage]s on top of it

This seems like a lot of things to do at once so you might ask "How is this [SOLID](https://en.wikipedia.org/wiki/SOLID_(object-oriented_design))?".
Zircon solves this problem with composition: All of the above mentioned categories are handled by an object 
within a [Terminal] which is responsible for only one thing.
For example [Terminal] implements the [Layerable] interface and internally all operations defined by it are 
delegated to an object which implements [Layerable] only.
You can peruse these [here](https://github.com/Hexworks/zircon/tree/master/src/main/kotlin/org/codetome/zircon/api/behavior).
In this sense you can consider a [Terminal] as a [Facade](https://en.wikipedia.org/wiki/Facade_pattern).

### Colors and StyleSets

Objects like [TextCharacter]s can have foreground and background colors. You can either use the [ANSITextColor]
`enum` to pick a pre-defined [TextColor] or you can create a new one by using [TextColorFactory]. This class
has some useful factory methods for this like: `fromAWTColor`, `fromRGB` and `fromString`. The latter can be
called with simple CSS-like strings (eg: `#334455`).

If you don't want to set all these colors by hand or you want to have a color template and use it to set colors to
multiple things you can use a [StyleSet] which is basically a [Value Object](https://martinfowler.com/bliki/ValueObject.html)
which holds fore/background colors and modifiers.

### Modifiers
When working with [TextCharacter]s apart from giving them color you might want to apply some special
[Modifier] to them like `UNDERLINE` or `VERTICAL_FLIP`.
You can do this by picking the right [Modifier] from the [Modifiers] class.
You can set any number of [Modifier]s to each [TextCharacter] individually and when
you refresh your [Terminal] by calling `flush` on it you will see them applied.

### TextImages
An image built from [TextCharacter]s with color and style information. 
These are completely in memory and not visible, but can be used when drawing on other [DrawSurface]s,
like a [Screen] or a [Terminal]. In other words [TextImage]s are like real images but composed of
[TextCharacter]s to create ASCII art and the like. 

### Screens 

[Screen]s are in-memory representations of your [Terminal]. They are double buffered
which means that you write to a back-buffer and when you `refresh` your [Screen] only the changes will
be written to the backing [Terminal] instance. Multiple [Screen]s can be attached to the same [Terminal]
object which means that you can have more than one screen in your app and you can switch between them
simultaneously by using the `display` method. [Screen]s also let you use [Component]s like [Button]s
and [Panel]s.

> If you want to read more about the design philosophy behind Zircon check [this][design-philosophy] page on Wiki!
> 
> If you are interested in how components work then [this][components] Wiki page can help you.

## Road map

If you want to see a new feature feel free to [create a new Issue](https://github.com/Hexworks/zircon/issues/new)
or discuss it with us on [discord][discord].
Here are some features which are either under way or planned:

- libGDX support
- Layouts for Components with resizing support
- Components for games like MapDisplay
- Multi-Font support
- Next to `ColorTheme`s we'll introduce `ComponentTheme`s as well (custom look and feel for your components)

## License
Zircon is made available under the [MIT License](http://www.opensource.org/licenses/mit-license.php).

## Credits
Zircon is created and maintained by Adam Arold, Milan Boleradszki and Gergely Lukacsy

*We're open to suggestions, feel free to message us on [Discord][discord] or open an issue.*
*Pull requests are also welcome!*

Zircon is powered by:

<a href="https://www.jetbrains.com/idea/">
    <img src="https://github.com/Hexworks/zircon/blob/master/images/idea_logo.png" width="40" height="40" />
</a>
<a href="https://kotlinlang.org/">
    <img src="https://github.com/Hexworks/zircon/blob/master/images/kotlin_logo.png" width="40" height="40" />
</a>


[circleci]:https://circleci.com/gh/Hexworks/zircon
[circleci img]:https://circleci.com/gh/Hexworks/zircon/tree/master.svg?style=shield

[codecov]:https://codecov.io/github/Hexworks/zircon?branch=master
[codecov img]:https://codecov.io/github/Hexworks/zircon/coverage.svg?branch=master

[license]:https://github.com/Hexworks/zircon/blob/master/LICENSE
[license img]:https://img.shields.io/badge/License-MIT-green.svg

[maven]:https://mvnrepository.com/artifact/org.codetome.zircon/zircon/2017.4.0
[maven img]:https://maven-badges.herokuapp.com/maven-central/org.codetome.zircon/zircon/badge.svg

[resource-handling]:https://github.com/Hexworks/zircon/wiki/Resource-Handling
[design-philosophy]:https://github.com/Hexworks/zircon/wiki/The-design-philosophy-behind-Zircon
[color-themes]:https://github.com/Hexworks/zircon/wiki/Working-with-color-themes
[text-images]:https://github.com/Hexworks/zircon/wiki/How-to-work-with-TextImages
[screen-primer]:https://github.com/Hexworks/zircon/wiki/A-primer-on-Screens
[components]:https://github.com/Hexworks/zircon/wiki/The-component-system
[layers]:https://github.com/Hexworks/zircon/wiki/How-Layers-work
[inputs]:https://github.com/Hexworks/zircon/wiki/Input-handling
[shapes]:https://github.com/Hexworks/zircon/wiki/Shapes
[animations]:https://github.com/Hexworks/zircon/wiki/Animation-support

[discord]:https://discord.gg/p2vSMFc
[examples]:https://github.com/Hexworks/zircon/tree/master/src/test/java/org/codetome/zircon/examples
[api]:https://github.com/Hexworks/zircon/tree/master/src/main/kotlin/org/codetome/zircon/api
[internal]:https://github.com/Hexworks/zircon/tree/master/src/main/kotlin/org/codetome/zircon/internal

[REXPaintResource]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/resource/REXPaintResource.kt
[Shape]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/graphics/Shape.kt
[ShapeFactory]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/shape/ShapeFactory.kt
[ColorTheme]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/component/ColorTheme.kt
[TextColor]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/color/TextColor.kt
[StyleSet]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/graphics/StyleSet.kt  
[Component]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/component/Component.kt
[LayerBuilder]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/builder/LayerBuilder.kt
[TerminalBuilder]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/builder/TerminalBuilder.kt
[Button]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/component/Button.kt
[Panel]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/component/Panel.kt
[DrawSurface]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/behavior/DrawSurface.kt
[Layerable]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/behavior/Layerable.kt
[Layer]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/graphics/Layer.kt
[ANSITextColor]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/color/ANSITextColor.kt
[TextColorFactory]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/color/TextColorFactory.kt
[TextCharacter]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/TextCharacter.kt
[Modifier]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/Modifier.kt
[Modifiers]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/Modifiers.kt
[InputProvider]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/input/InputProvider.kt
[Input]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/input/Input.kt
[TextImage]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/graphics/TextImage.kt
[BasicTextImage]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/graphics/impl/DefaultTextImage.kt
[Screen]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/screen/Screen.kt
[InputEmitter]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/behavior/InputEmitter.kt
[Terminal]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/terminal/Terminal.kt
