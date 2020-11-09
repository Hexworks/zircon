# Package org.hexworks.zircon.api

This package contains the public *API* of Zircon. You can use anything you find here, but note that
classes and methods annotated with `@Beta` are considered a preliminary implementation, and their
API might change in the future.

# Package org.hexworks.zircon.api.animation

Contains classes that can be used to create animations. RexPaint file loading is also supported.

# Package org.hexworks.zircon.api.application

This package contains the [Application] class that can be used to start a *Zircon* application.
You can also find all the related configuration classes.

# Package org.hexworks.zircon.api.behavior

*Behavior*s are used by *Zircon* as traits or mixins. They implement some common functionality like
[CursorHandler], [Hideable] or [Scrollable].

# Package org.hexworks.zircon.api.builder

This package and its subpackages are containing *builder*s for almost all Zircon objects,
like [Component]s, [TileGraphics] or [Tile] objects.

# Package org.hexworks.zircon.api.color

Contains color-related types that can be used for coloring [Tile]s.

# Package org.hexworks.zircon.api.component

This package contains the public API of the [Component] abstraction. [Component]s are
text GUI elements that you can use on [Screen]s and [View]s.


# Package org.hexworks.zircon.internal

This package (and all its subpackages) contain types internal to *Zircon*.

You *can* use classes from this package, but they are subject to change in future releases, and you shouldn't rely on them.


