import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.builder.component.*
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.builder.base.withPreferredContentSize
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.graphics.Symbols


fun Application.test() = also {
    val screen = tileGrid.toScreen()

    screen.addComponent(buildVbox {
        lateinit var textArea: TextArea
        hbox {
            textArea = textArea {
                withPreferredContentSize {
                    width = 20
                    height = 10
                }
            }
            verticalScrollBar(textArea){
            }
        }
        hbox {
            button {
                +"${Symbols.ARROW_LEFT}"
                onActivated {
                    textArea.scrollOneLeft()
                    textArea.requestFocus()
                }
            }
            button {
                +"${Symbols.ARROW_RIGHT}"
                onActivated {
                    textArea.scrollOneRight()
                    textArea.requestFocus()
                }
            }
            button {
                +"${Symbols.ARROW_UP}"
                onActivated {
                    textArea.scrollOneUp()
                    textArea.requestFocus()
                }
            }
            button {
                +"${Symbols.ARROW_DOWN}"
                onActivated {
                    textArea.scrollOneDown()
                    textArea.requestFocus()
                }
            }
        }
    })

    screen.display()
}