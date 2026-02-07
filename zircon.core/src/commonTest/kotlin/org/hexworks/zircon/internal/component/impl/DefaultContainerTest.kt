package org.hexworks.zircon.internal.component.impl

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.events.api.simpleSubscribeTo
import org.hexworks.zircon.ApplicationStub
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.application.withSize
import org.hexworks.zircon.api.builder.component.buildHeader
import org.hexworks.zircon.api.builder.component.buildLabel
import org.hexworks.zircon.api.builder.component.buildPanel
import org.hexworks.zircon.api.builder.grid.tileGrid
import org.hexworks.zircon.api.builder.grid.withAppConfig
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.component.builder.base.withPreferredSize
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.RootContainerRenderer
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentRemoved
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import kotlin.test.BeforeTest
import kotlin.test.Test

class DefaultContainerTest : CommonComponentTest<DefaultContainer>() {

    override lateinit var target: DefaultContainer

    override val expectedComponentStyles: ComponentStyleSet
        get() = ComponentStyleSet.empty()

    private val badTileset = BuiltInCP437TilesetResource.WANDERLUST_16X16

    @BeforeTest
    override fun setUp() {
        componentStub = ComponentStub(COMPONENT_STUB_POSITION_1x1, Size.create(2, 2))
        rendererStub = ComponentRendererStub()
        target = DefaultContainer(
            metadata = ComponentMetadata(
                size = SIZE_4x4,
                relativePosition = POSITION_2_3,
                componentStyleSetProperty = COMPONENT_STYLES.toProperty(),
                tilesetProperty = TILESET_REX_PAINT_20X20.toProperty()
            ),
            renderer = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(),
                componentRenderer = rendererStub
            )
        )
    }

    @Test
    fun shouldProperlySetUpComponentsWhenNestedComponentsAreAdded() {
        val grid = tileGrid {
            withAppConfig {
                withSize {
                    width = 40
                    height = 25
                }
                defaultTileset = TILESET_REX_PAINT_20X20
            }
        }.asInternal()

        grid.application = ApplicationStub()

        val screen = grid.toScreen()

        val panel = buildPanel {
            decorations {
                +box(title = "Panel")
            }
            tileset = TILESET_REX_PAINT_20X20
            withPreferredSize {
                width = 32
                height = 16
            }
            position = Position.offset1x1()
        }.asInternalComponent()

        val panelHeader = buildHeader {
            +"Header"
            position = Position.create(1, 0)
            tileset = TILESET_REX_PAINT_20X20
        }.asInternalComponent()

        val innerPanelHeader = buildHeader {
            +"Header2"
            position = Position.create(1, 0)
            tileset = TILESET_REX_PAINT_20X20
        }.asInternalComponent()

        val innerPanel = buildPanel {
            decorations {
                +box(title = "Panel2")
            }
            withPreferredSize {
                width = 16
                height = 10
            }
            tileset = TILESET_REX_PAINT_20X20
            position = Position.create(1, 2)
        }.asInternalComponent()

        panel.isAttached shouldBe false
        panelHeader.isAttached shouldBe false
        innerPanel.isAttached shouldBe false
        innerPanelHeader.isAttached shouldBe false

        innerPanel.addComponent(innerPanelHeader)
        innerPanelHeader.isAttached shouldBe true

        panel.addComponent(panelHeader)
        panelHeader.isAttached shouldBe true

        panel.addComponent(innerPanel)
        innerPanel.isAttached shouldBe true

        panel.isAttached shouldBe false

        screen.addComponent(panel)

        panel.isAttached shouldBe true

        panel.absolutePosition shouldBe Position.create(1, 1)
        panelHeader.absolutePosition shouldBe Position.create(3, 2) // + 1x1 because of the wrapper
        innerPanel.absolutePosition shouldBe Position.create(3, 4)
        innerPanelHeader.absolutePosition shouldBe Position.create(5, 5)
    }

    @Test
    fun shouldProperlySetUpComponentsWhenAContainerIsAddedThenComponentsAreAddedToIt() {
        val grid: InternalTileGrid = tileGrid {
            withAppConfig {
                withSize {
                    width = 40
                    height = 25
                }
                defaultTileset = TILESET_REX_PAINT_20X20
            }
        }.asInternal()

        grid.application = ApplicationStub()

        val screen = grid.toScreen()

        val panel0 = buildPanel {
            decorations {
                +box(title = "Panel")
            }
            withPreferredSize {
                width = 32
                height = 16
            }
            position = Position.offset1x1()
        }.asInternalComponent()

        val panel1 = buildPanel {
            decorations {
                +box(title = "Panel2")
            }
            tileset = TILESET_REX_PAINT_20X20
            withPreferredSize {
                width = 16
                height = 10
            }
            position = Position.offset1x1()
        }.asInternalComponent()

        val header0 = buildHeader {
            +"Header"
            tileset = TILESET_REX_PAINT_20X20
            position = Position.create(1, 0)
        }.asInternalComponent()

        screen.addComponent(panel0)

        panel0.isAttached shouldBe true

        panel0.addComponent(header0)

        header0.isAttached shouldBe true
        header0.absolutePosition shouldBe Position.create(3, 2)

        panel0.addComponent(panel1)

        panel1.isAttached shouldBe true
        panel1.absolutePosition shouldBe Position.create(3, 3)

    }

    @Test
    fun shouldThrowExceptionIfComponentWithUnsupportedFontSizeIsAdded() {
        shouldThrow<IllegalArgumentException> {
            target.addComponent(
                buildLabel {
                    +"foo"
                    tileset = badTileset
                }
            )
        }
    }

    @Test
    fun shouldNotAcceptFocus() {
        target.acceptsFocus() shouldBe false
    }

    @Test
    fun shouldNotLetToAddAComponentWhichIntersectsWithAnother() {
        shouldThrow<IllegalArgumentException> {
            val pos = Position.create(1, 1)
            val comp = buildLabel {
                +"text"
                position = pos
                tileset = TILESET_REX_PAINT_20X20
            }

            val otherComp = buildLabel {
                +"text"
                position = pos.withRelativeX(1)
                tileset = TILESET_REX_PAINT_20X20
            }
            target.addComponent(comp)
            target.addComponent(otherComp)
        }
    }

    @Test
    fun shouldNotAcceptGivenFocus() {
        target.focusGiven() shouldBe Pass
    }

    @Test
    fun shouldNotAllowAddingAComponentToItself() {
        shouldThrow<IllegalArgumentException> {
            target.addComponent(target)
        }
    }

    @Test
    fun shouldNotBeAbleToAddAComponentWhichIntersectsWithOtherComponents() {
        shouldThrow<IllegalArgumentException> {
            target.addComponent(
                buildLabel {
                    +"foo"
                    tileset = TILESET_REX_PAINT_20X20
                }
            )

            target.addComponent(
                buildLabel {
                    +"foo"
                    position = Position.create(1, 0)
                    tileset = TILESET_REX_PAINT_20X20
                }
            )
        }
    }

    @Test
    fun shouldProperlyRemoveComponentFromSelf() {
        val applicationStub = ApplicationStub()
        val root = DefaultRootContainer(
            metadata = ComponentMetadata(
                relativePosition = Position.defaultPosition(),
                size = Size.create(50, 50),
            ),
            application = applicationStub,
            renderingStrategy = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(),
                componentRenderer = RootContainerRenderer()
            )
        )
        val comp = buildLabel {
            +"x"
            tileset = TILESET_REX_PAINT_20X20
        }.asInternalComponent()

        root.addComponent(target)

        val handle = target.addComponent(comp)
        var removalHappened = false
        root.eventBus.simpleSubscribeTo(ComponentRemoved, root.eventScope) {
            removalHappened = true
        }

        handle.detach()
        removalHappened shouldBe true
    }

    @Test
    fun shouldReturnUnknownStylesWhenThemeApplied() {
        target.convertColorTheme(DEFAULT_THEME) shouldBeSameInstanceAs ComponentStyleSet.unknown()
    }

    @Test
    fun shouldProperlyReturnToString() {
        println(target.toString())
        target.toString() shouldBe
            "DefaultContainer(id=${
                target.id.toString().substring(0, 4)
            }, absolutePosition=(x=2,y=3), relativePosition=(x=2,y=3), size=(w=4,h=4), state=DEFAULT, disabled=false)"
    }

    companion object {
        val SIZE_4x4 = Size.create(4, 4)
        val COMPONENT_STUB_POSITION_1x1 = Position.create(1, 1)
    }
}
