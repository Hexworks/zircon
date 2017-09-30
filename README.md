# Zircon

Zircon is a Text GUI library which targets multiple platforms and designed specifically for game developers.
It is usable out of the box for all JVM languages including Java.

Currently only a Swing implementation is present but we are working on a libGDX one.

*Note that* this library is deeply inspired by [Lanterna](https://github.com/mabe02/lanterna) and parts of its codebase were ported to serve as a basis
 for this library.
 
 *If you have any questions which are unanswered in the README or the Wiki feel free to ask away on the
 [official Discord server](https://discordapp.com/invite/p2vSMFc)*!

[![][circleci img]][circleci]
[![][maven img]][maven]
[![][codecov img]][codecov]
[![][license img]][license]

## A little Crash Course

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

[Screen]s are a bitmap-like in-memory representations of your [Terminal]. They are double buffered
which means that you write to a back-textBuffer and when you `refresh` your [Screen] only the changes will
be written to the backing [Terminal] instance. Multiple [Screen]s can be attached to the same [Terminal]
object which means that you can have multiple screens in your app and you can switch between them
simultaneously by using the `display` method. [Screen]s also let you use [Component]s like [Button]s
and [Panel]s.

> If you want to read more about the design philosophy behind Zircon check [this][design-philosophy] page on Wiki!

Now that we got the basics out of the way, let's see how this all works in practice.

## Getting Started

If you want to work with Zircon you can add it to your project as a dependency.

from Maven:

```xml
<dependency>
    <groupId>org.codetome.zircon</groupId>
    <artifactId>zircon</artifactId>
    <version>2017.2.0</version>
</dependency>
```

or you can also use Gradle:

```groovy
compile("org.codetome.zircon:zircon:2017.2.0")

```

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

        terminal.enableModifier(Modifiers.VERTICAL_FLIP);
        terminal.setForegroundColor(ANSITextColor.CYAN);
        terminal.putCharacter('a');
        terminal.resetColorsAndModifiers();

        terminal.setForegroundColor(ANSITextColor.GREEN);
        terminal.enableModifier(Modifiers.HORIZONTAL_FLIP);
        terminal.putCharacter('b');
        terminal.resetColorsAndModifiers();

        terminal.setForegroundColor(ANSITextColor.RED);
        terminal.enableModifier(Modifiers.CROSSED_OUT);
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
- CheckBox: Like a button but with checked / unchecked state
- Label: Simple component with text
- Header: Like a label but this one has emphasis (useful when using [ColorTheme]s)
- Panel: A [Container] which can hold multiple [Components]
- RadioButtonGroup and RadioButton: Like a CheckBox but only one can be selected at a time
- TextBox

Let's look at an example (notes about how it works in comments):

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
                .title("Panel") // if a panel is wrapped in a box a title can be displayed
                // panels can be wrapped in a box
                .wrapInBox()
                .addShadow() // shadow can be added
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
                .position(Position.of(0, 1)
                        .relativeToBottomOf(checkBox))
                .text("Left")
                .build();

        final Button right = ButtonBuilder.newBuilder()
                .position(Position.of(1, 0)
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

## Road map

If you want to see a new feature feel free to [create a new Issue](https://github.com/Hexworks/zircon/issues/new).
Here are some features which are either under way or planned:

- libGDX support
- Layouts for Components with resizing support
- Components for games like MapDisplay
- Multi-Font support

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

[maven]:https://mvnrepository.com/artifact/org.codetome.zircon/zircon/2017.1.0
[maven img]:https://maven-badges.herokuapp.com/maven-central/org.codetome.zircon/zircon/badge.svg

[tilesetFont modifiers img]:https://github.com/Hexworks/zircon/blob/master/src/main/resources/modifiers_example.png
[button img]:https://github.com/Hexworks/zircon/blob/master/src/main/resources/button.png

[resource-handling]:https://github.com/Hexworks/zircon/wiki/Resource-Handling
[design-philosophy]:https://github.com/Hexworks/zircon/wiki/The-design-philosophy-behind-Zircon
[color-themes]:https://github.com/Hexworks/zircon/wiki/Working-with-color-themes
[text-images]:https://github.com/Hexworks/zircon/wiki/How-to-work-with-TextImages
[screen-primer]:https://github.com/Hexworks/zircon/wiki/A-primer-on-Screens

[discord]:https://discord.gg/p2vSMFc
[examples]:https://github.com/Hexworks/zircon/tree/master/src/test/java/org/codetome/zircon/examples

[Component]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/component/Component.kt
[TerminalBuilder]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/builder/TerminalBuilder.kt
[Button]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/component/Button.kt
[Panel]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/component/Panel.kt
[DrawSurface]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/behavior/DrawSurface.kt
[Layerable]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/behavior/Layerable.kt
[Layer]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/graphics/Layer.kt
[TextColor]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/color/TextColor.kt
[TextCharacter]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/TextCharacter.kt
[Terminal]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/terminal/Terminal.kt
[Modifier]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/Modifier.kt
[Modifiers]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/Modifiers.kt
[InputProvider]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/input/InputProvider.kt
[Input]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/input/Input.kt
[TextGraphics]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/graphics/TextGraphics.kt
[TextImage]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/graphics/TextImage.kt
[BasicTextImage]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/graphics/impl/DefaultTextImage.kt
[Screen]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/api/screen/Screen.kt
