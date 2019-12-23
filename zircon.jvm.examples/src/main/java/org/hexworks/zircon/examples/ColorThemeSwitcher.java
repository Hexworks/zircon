package org.hexworks.zircon.examples;

import org.hexworks.cobalt.databinding.api.property.Property;
import org.hexworks.cobalt.datatypes.Maybe;
import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.internal.resource.ColorThemeResource;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.ComponentDecorations.shadow;
import static org.hexworks.zircon.api.Functions.fromConsumer;

@SuppressWarnings("ALL")
public class ColorThemeSwitcher {

    private static final Size SCREEN_SIZE = Size.create(80, 41);
    private static final TilesetResource TILESET = CP437TilesetResources.rogueYun16x16();
    private static final ColorThemeResource THEME = ColorThemeResource.GAMEBOOKERS;

    private static final Size INFO_PANEL_SIZE = SCREEN_SIZE.withHeight(10).withRelativeWidth(-4);
    private static final Size THEME_PICKER_SIZE = SCREEN_SIZE
            .withRelativeHeight(-INFO_PANEL_SIZE.getHeight() - 4)
            .withWidth(SCREEN_SIZE.getWidth() / 3 - 1);

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(TILESET)
                .withSize(SCREEN_SIZE)
                .withDebugMode(true)
                .build());

        Screen screen = Screen.create(tileGrid);

        addScreenTitle(screen, "Color themes");

        AtomicReference<ColorThemeResource> currentTheme = new AtomicReference<>(THEME);
        AtomicReference<Header> currentThemeLabel = new AtomicReference<>(createHeaderForTheme(currentTheme.get()));

        final Panel infoPanel = Components.panel()
                .withDecorations(box(BoxType.SINGLE, "Components example:"))
                .withSize(INFO_PANEL_SIZE)
                .withPosition(Position.create(2, 2).relativeToBottomOf(currentThemeLabel.get()))
                .build();

        final Button testButton = Components.button()
                .withText("Button")
                .withPosition(Position.create(0, 2))
                .build();

        final CheckBox checkBox = Components.checkBox()
                .withText("Checkbox")
                .withPosition(Position.create(0, 1).relativeToBottomOf(testButton))
                .build();

        final Header header = Components.header()
                .withText("Header")
                .withPosition(Position.create(0, 1).relativeToBottomOf(checkBox))
                .build();

        final Label label = Components.label()
                .withText("Label")
                .withPosition(Position.create(8, 0)
                        .relativeToRightOf(testButton))
                .build();

        VBox radioBox = Components.vbox()
                .withSize(Size.create(15, 3))
                .withPosition(Position.create(0, 1).relativeToBottomOf(label))
                .build();
        RadioButton radio0 = Components.radioButton()
                .withKey("0")
                .withText("Option 0")
                .build();
        RadioButton radio1 = Components.radioButton()
                .withKey("1")
                .withText("Option 1")
                .build();
        RadioButton radio2 = Components.radioButton()
                .withKey("2")
                .withText("Option 2")
                .build();

        RadioButtonGroup group = Components.radioButtonGroup().build();
        group.addAll(radio0, radio1, radio2);


        radioBox.addComponents(radio0, radio1, radio2);

        final Panel panel = Components.panel()
                .withSize(Size.create(20, 6))
                .withDecorations(box(BoxType.SINGLE, "Panel"), shadow())
                .withPosition(Position.create(10, 0)
                        .relativeToRightOf(label))
                .build();

        TextArea textArea = Components.textArea()
                .withPosition(Position.create(1, 0).relativeToRightOf(panel))
                .withSize(Size.create(20, 6))
                .withText("Text box")
                .build();

        infoPanel.addComponent(currentThemeLabel.get());
        infoPanel.addComponent(testButton);
        infoPanel.addComponent(checkBox);
        infoPanel.addComponent(header);
        infoPanel.addComponent(label);
        infoPanel.addComponent(panel);
        infoPanel.addComponent(panel);
        infoPanel.addComponent(textArea);

        screen.addComponent(infoPanel);

        final Size smallPanelSize = THEME_PICKER_SIZE
                .withRelativeWidth(-2)
                .withHeight(THEME_PICKER_SIZE.getHeight() / 2 - 1);

        final Panel solarizedLightPanel = Components.panel()
                .withDecorations(box(BoxType.SINGLE, "Solarized Light"))
                .withPosition(Position.create(0, 1).relativeToBottomOf(infoPanel))
                .withSize(smallPanelSize)
                .build();
        final Panel solarizedDarkPanel = Components.panel()
                .withDecorations(box(BoxType.SINGLE, "Solarized Dark"))
                .withPosition(Position.create(0, 1).relativeToBottomOf(solarizedLightPanel))
                .withSize(smallPanelSize)
                .build();
        final Panel zenburnPanel = Components.panel()
                .withDecorations(box(BoxType.SINGLE, "Zenburn"))
                .withPosition(Position.create(1, 0).relativeToRightOf(solarizedLightPanel))
                .withSize(smallPanelSize)
                .build();
        final Panel monokaiPanel = Components.panel()
                .withDecorations(box(BoxType.SINGLE, "MonokaiLight"))
                .withPosition(Position.create(1, 0).relativeToRightOf(solarizedDarkPanel))
                .withSize(smallPanelSize)
                .build();
        final Panel otherPanel = Components.panel()
                .withDecorations(box(BoxType.SINGLE, "Other"))
                .withPosition(Position.create(1, 0).relativeToRightOf(zenburnPanel))
                .withSize(THEME_PICKER_SIZE.withRelativeWidth(3).withRelativeHeight(-1))
                .build();


        screen.addComponents(solarizedLightPanel, solarizedDarkPanel, zenburnPanel, monokaiPanel, otherPanel);

        final List<ColorThemeResource> solarizedLightOptions = Arrays.stream(ColorThemeResource.values())
                .filter(option -> option.name().startsWith("SOLARIZED_LIGHT"))
                .collect(Collectors.toList());
        final List<ColorThemeResource> solarizedDarkOptions = Arrays.stream(ColorThemeResource.values())
                .filter((option) -> option.name().startsWith("SOLARIZED_DARK"))
                .collect(Collectors.toList());
        final List<ColorThemeResource> zenburnOptions = Arrays.stream(ColorThemeResource.values())
                .filter((option) -> option.name().startsWith("ZENBURN"))
                .collect(Collectors.toList());
        final List<ColorThemeResource> monokaiOptions = Arrays.stream(ColorThemeResource.values())
                .filter((option) -> option.name().startsWith("MONOKAI"))
                .collect(Collectors.toList());
        final List<ColorThemeResource> otherOptions = Arrays.stream(ColorThemeResource.values())
                .filter((option) -> !option.name().startsWith("SOLARIZED") &&
                        !option.name().startsWith("MONOKAI") &&
                        !option.name().startsWith("ZENBURN") &&
                        !option.name().startsWith("EMPTY"))
                .collect(Collectors.toList());

        final RadioButtonGroup slOptions = addThemeOptionsTo(solarizedLightPanel, solarizedLightOptions);
        final RadioButtonGroup sdOptions = addThemeOptionsTo(solarizedDarkPanel, solarizedDarkOptions);
        final RadioButtonGroup zbOptions = addThemeOptionsTo(zenburnPanel, zenburnOptions);
        final RadioButtonGroup mOptions = addThemeOptionsTo(monokaiPanel, monokaiOptions);
        final RadioButtonGroup othOptions = addThemeOptionsTo(otherPanel, otherOptions);

//        Property<Maybe<RadioButton>> selectedButtonProperty = slOptions.getSelectedButtonProperty();

//        slOptions.onSelection(fromConsumer((selection -> {
//            refreshTheme(screen, currentTheme, currentThemeLabel, infoPanel, selection);
//        })));
//        sdOptions.onSelection(fromConsumer((selection -> {
//            refreshTheme(screen, currentTheme, currentThemeLabel, infoPanel, selection);
//        })));
//        zbOptions.onSelection(fromConsumer((selection -> {
//            refreshTheme(screen, currentTheme, currentThemeLabel, infoPanel, selection);
//        })));
//        mOptions.onSelection(fromConsumer((selection -> {
//            refreshTheme(screen, currentTheme, currentThemeLabel, infoPanel, selection);
//        })));
//        othOptions.onSelection(fromConsumer((selection -> {
//            refreshTheme(screen, currentTheme, currentThemeLabel, infoPanel, selection);
//        })));

        screen.setTheme(currentTheme.get().getTheme());
        screen.display();

    }

    @NotNull
    private static RadioButtonGroup addThemeOptionsTo(Panel panel, List<ColorThemeResource> options) {
        final RadioButtonGroup group = Components.radioButtonGroup().build();
        final VBox box = Components.vbox()
                .withSize(THEME_PICKER_SIZE
                        .withHeight(options.size())
                        .withRelativeWidth(-4))
                .build();
        options.forEach((option) -> {
            RadioButton btn = Components.radioButton()
                    .withText(option.name())
                    .withKey(option.name().replace("SOLARIZED_LIGHT_", ""))
                    .build();
            box.addComponent(btn);
            group.add(btn);
        });
        panel.addComponent(box);
        return group;
    }

    private static void refreshTheme(Screen screen,
                                     AtomicReference<ColorThemeResource> themeRef,
                                     AtomicReference<Header> labelRef,
                                     Panel infoPanel,
                                     RadioButton selection) {
        themeRef.set(ColorThemeResource.valueOf(selection.getKey()));
        infoPanel.removeComponent(labelRef.get());
        labelRef.set(createHeaderForTheme(themeRef.get()));
        infoPanel.addComponent(labelRef.get());
        screen.setTheme(themeRef.get().getTheme());
    }

    private static Header createHeaderForTheme(ColorThemeResource currentTheme) {
        return Components.header()
                .withText(currentTheme.name())
                .build();
    }

    private static void addScreenTitle(Screen screen, String title) {
        final Header header = Components.header()
                .withText(title)
                .withPosition(Position.create(2, 1))
                .build();
        screen.addComponent(header);
    }
}
