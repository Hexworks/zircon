# Zircon

Zircon is a Text GUI library which targets multiple platforms and designed specifically for game developers.
It is usable out of the box for all JVM languages including Java.

Currently only a Swing implementation is present but we are working on a libGDX one.

*Note that* this library is deeply inspired by [Lanterna](https://github.com/mabe02/lanterna) and parts of its codebase were ported to serve as a basis
 for this library.

[![][circleci img]][circleci]
[![codecov](https://codecov.io/gh/Hexworks/zircon/branch/master/graph/badge.svg)](https://codecov.io/gh/Hexworks/zircon)
[![][license img]][license]

## A little Crash Course

In order to work with Zircon you should get familiar with the core concepts. Zircon provides multiple layers of abstractions and it depends on
your needs which one you should pick.

### Terminal
At the lowest level Zircon provides the [Terminal] interface. This provides you with a surface on which you can draw [TextCharacter]s.
A [TextCharacter] is basically a character (like an `x`) with additional metadata like `foregroundColor` and `backgroundColor`.
This surface sits on top of a GUI layer and completely abstracts away how that layer works.
For example the default implementation of the [Terminal] interface uses Swing under the hood.
The main advantage of using [Terminal]s is that by implementing all its methods you can swap Swing with something else (like SWT) and use **all** higher
level abstractions on top of it (like [Screen]s) which depend on [Terminal] (more on [Screen]s later).
Working with [Terminal]s is *very* simple but somewhat limited. A [Terminal] is responsible for:

- drawing characters (by position or by cursor) on the screen
- handling inputs (keyboard and mouse) which are emitted by the GUI layer
- handling the cursor which is visible to the user
- handling [Layer]ing
- storing style information
- drawing [TextImage]s on top of it

This seems like a lot of things to do at once so you might ask "How is this [SOLID](https://en.wikipedia.org/wiki/SOLID_(object-oriented_design))?". Zircon
solves this problem with composition: All of the above mentioned categories are handled by an object within a [Terminal] which is responsible for only
one thing. For example [Terminal] implements the [Layerable] interface and internally all operations defined by it are delegated to an object
which implements [Layerable] only. You can peruse these [here](https://github.com/Hexworks/zircon/tree/master/src/main/kotlin/org/codetome/zircon/api/behavior).

## Modifiers
When working with [TextCharacter]s apart from giving them color you might want to apply some special [Modifier] to them like `UNDERLINE` or `VERTICAL_FLIP`.
You can do this by picking the right [Modifier] from the [Modifiers] class. You can set any number of [Modifier]s to each [TextCharacter] individually and when
you refresh your [Terminal] by calling `flush` on it you will see them applied.

## TextImages
A [TextImage] is an in-memory object on which you can draw [TextCharacter]s and later you can draw the [TextImage] itself on your [Terminal]. This is useful
to create ASCII art for example and paste it on your [Terminal] multiple times or save it for later use.

## Screens

[Screen]s are a bitmap-like in-memory representations of your [Terminal]. They are double buffered
which means that you write to a back-textBuffer and when you `refresh` your [Screen] only the changes will
be written to the backing [Terminal] instance. Multiple [Screen]s can be attached to the same [Terminal]
object which means that you can have multiple screens in your app and you can switch between them
simultaneously by using the `display` method.

## Getting Started

If you want to work with Zircon you can add it to your project as a dependency.

from Maven:

```xml
<dependency>
    <groupId>org.codetome.zircon</groupId>
    <artifactId>zircon</artifactId>
    <version>2017.1.0</version>
</dependency>
```

or you can also use Gradle:

```groovy
compile("org.codetome.zircon:zircon:2017.1.0")

```

### Creating a Terminal

```java
shape
final DefaultTerminalFactory factory = new DefaultTerminalFactory();

// Then set a size for your terminal (in characters)
factory.initialSize(new TerminalSize(84, 32));

// And finally create a new Terminal
final Terminal terminal = factory.createTerminal();
```

Adding content to your [Terminal] is also very simple:

```java
// Enable a modifier (means that any following characters will have this modifier)
terminal.enableModifier(Modifier.CROSSED_OUT);

// set a background for the following characters
terminal.setBackgroundColor(TextColor.ANSI.BLUE);

// set a foreground
terminal.setForegroundColor(TextColor.ANSI.YELLOW);

// and finally put a character on the screen
terminal.putCharacter('A');
```      
  
Let's add some more content!

```java
terminal.resetColorsAndModifiers();
terminal.putCharacter(' ');

terminal.enableModifiers(Modifier.BOLD, Modifier.ITALIC);
terminal.setBackgroundColor(TextColor.ANSI.GREEN);
terminal.setForegroundColor(TextColor.ANSI.YELLOW);
terminal.putCharacter('B');

terminal.resetColorsAndModifiers();
terminal.putCharacter(' ');

terminal.enableModifiers(Modifier.BLINK);
terminal.setBackgroundColor(TextColor.ANSI.RED);
terminal.setForegroundColor(TextColor.ANSI.WHITE);
terminal.putCharacter('C');
```
And the result is:

![][tilesetFont modifiers img]

### Working with Screens

[Terminal]s alone won't suffice since they are rather rudimentary and hard to use. Let's use a [Screen]!

```java
final DefaultTerminalFactory factory = new DefaultTerminalFactory();
factory.setTerminalFontConfiguration(TerminalFontConfiguration.Companion.buildDefault());
factory.initialSize(new TerminalSize(84, 32));
final Terminal terminal = factory.createTerminal();

final TerminalScreen screen = new TerminalScreen(terminal);
```

now draw some background on it:

```java
final TextGraphics graphics = screen.newTextGraphics();
graphics.setBackgroundColor(TextColor.Companion.fromString("#223344"));
graphics.fill(' ');
```

and add a simple button:

```java
final TextGraphics textGraphics = screen.newTextGraphics();
textGraphics.setBackgroundColor(TextColor.ANSI.BLUE);
textGraphics.setForegroundColor(TextColor.ANSI.CYAN);
textGraphics.putString(new TerminalPosition(10, 10), String.format("<%s>", "OK"), Collections.emptySet());
```

display it:

```java
screen.display();
```

and you'll have this:

![][button img]

You can check out more examples [here](https://github.com/Hexworks/zircon/blob/master/src/testRendering/java/org/codetome/zircon/examples)

## Road map

If you want to see a new feature feel free to [create a new Issue](https://github.com/Hexworks/zircon/issues/new).
Here are some features which are either under way or planned:

- More sophisticated [TextGraphics] and [TextImage] interfacing
- A simple component system which is able to compose contols like
  - Buttons
  - Text fields
  - Radio buttons
  - Tables
  - etc...
- Components for games like map display
- Support for custom fonts (like Dwarf Fortress tilesets)

## License
Zircon is made available under the [MIT License](http://www.opensource.org/licenses/mit-license.php).

## Credits
Zircon is created and maintained by Adam Arold, Milan Boleradszki and Gergely Lukacsy

*We're open to suggestions, feel free to comment or to send us a message.
Pull requests are also welcome!*

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

[tilesetFont modifiers img]:https://github.com/Hexworks/zircon/blob/master/src/main/resources/modifiers_example.png
[button img]:https://github.com/Hexworks/zircon/blob/master/src/main/resources/button.png

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
