# Zircon

Zircon is a terminal emulator which targets multiple GUI platforms and designed specifically for game developers.
It is usable out of the box for all JVM languages including Java.

Currently only a Swing implementation is present but there other GUI platforms on the road map.

*Note that* this library is deeply inspired by [Lanterna](https://github.com/mabe02/lanterna) and parts of its codebase were ported to serve as a basis for this library.

[![][travis img]][travis]
[![][codecov img]][codecov]
[![][license img]][license]

## Getting Started

If you want to work with Zircon you can add it to your project as a dependency

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
// First you have to create a terminal factory
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

![][font modifiers img]

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

## How Zircon works

Terminal emulation means that when you use this library you don't write to a real terminal but to
a virtual one. This makes a lot of extras possible compared to a real terminal like being able to use
mouse events and put characters at arbitrary locations. *Note that* currently Zircon only supports
a Swing-based virtual terminal.

### The Terminal interface

Zircon gives you a rather low-level [Terminal] interface which lets you put characters on the screen
with [Modifier]s applied (like `BOLD` or `BLINK`) by using `putCharacter(Character char)`
and `enableModifier(Modifier modifier)` respectively. You can also set background and foreground colors.

A [Terminal] is also an [InputProvider] which means that you can listen to user input from a [Terminal]
by using `pollInput()` or `readInput`. An [Input] can be either a `KeyStroke` (a key press on the keyboard)
or a `MouseAction` (click, press, move, etc).

### Text graphics

You can create [TextGraphics] objects by calling `newTextGraphics()` on a [Terminal] instance. A
[TextGraphics] is basically a group of text characters which form a quasi-graphical object (like a
panel or a simple rectangle). A [TextGraphics] object is always backed by a [Terminal]. Drawing operations
like `createLine(TerminalPosition fromPoint, TerminalPosition toPoint, Char character)` will result in
them displayed on your screen. A [TextImage] on an other hand is an in-memory group of characters. You
can create one by hand using the [BasicTextImage] class. [TextImage]s are basically blueprints for
[TextGraphics] objects. If you want to use draw operations on a [TextImage] you can call `newTextGraphics()`
on one and you will be presented by a [TextGraphics] object which is backed by the in-memory [TextImage].
When you are done drawing use `drawImage(TerminalPosition topLeft, TextImageimage)` on a [TextGraphics]
which is backed by a [Terminal] instance to draw the image on your screen. You can also copy images
to other [TextImage] objects using the `copyTo` method.

## Screens

[Screen]s are a bitmap-like in-memory representations of your [Terminal]. They are double buffered
which means that you write to a back-buffer and when you `refresh` your [Screen] only the changes will
be written to the backing [Terminal] instance. Multiple [Screen]s can be attached to the same [Terminal]
object which means that you can have multiple screens in your app and you can switch between them
simultaneously by using the `display` method. 

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
Zircon is created and maintained by Adam Arold

*I'm open to suggestions, feel free to comment or to send me a message.
Pull requests are also welcome!*

[travis]:https://travis-ci.org/Hexworks/zircon
[travis img]:https://api.travis-ci.org/Hexworks/zircon.svg?branch=master

[codecov]:https://codecov.io/github/Hexworks/zircon?branch=master
[codecov img]:https://codecov.io/github/Hexworks/zircon/coverage.svg?branch=master

[license]:https://github.com/Hexworks/zircon/blob/master/LICENSE
[license img]:https://img.shields.io/badge/License-MIT-green.svg

[font modifiers img]:https://github.com/Hexworks/zircon/blob/master/src/main/resources/modifiers_example.png
[button img]:https://github.com/Hexworks/zircon/blob/master/src/main/resources/button.png

[Terminal]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/terminal/Terminal.kt
[Modifier]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/Modifier.kt
[InputProvider]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/input/InputProvider.kt
[Input]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/input/Input.kt
[TextGraphics]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/graphics/TextGraphics.kt
[TextImage]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/graphics/TextImage.kt
[BasicTextImage]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/graphics/impl/DefaultTextImage.kt
[Screen]:https://github.com/Hexworks/zircon/blob/master/src/main/kotlin/org/codetome/zircon/screen/Screen.kt
