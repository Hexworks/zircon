package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.component.RadioButtonGroup.Selection;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.CP437Tilesets;
import org.hexworks.zircon.api.resource.ColorThemeResource;
import org.hexworks.zircon.api.screen.Screen;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
public class ColorThemeSwitcher {

    private static final Size SCREEN_SIZE = Sizes.create(80, 40);
<<<<<<< HEAD
    private static final CP437TilesetResource TILESET = CP437TilesetResource.WANDERLUST_16X16;
=======
    private static final CP437Tilesets TILESET = CP437Tilesets.ROGUE_YUN_16X16;
>>>>>>> Add examples and CP437TilesetResource
    private static final ColorThemeResource THEME_PICKER_THEME = ColorThemeResource.GAMEBOOKERS;

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(TILESET)
                .defaultSize(SCREEN_SIZE)
                .build());

        Screen screen = Screens.createScreenFor(tileGrid);

        addScreenTitle(screen, "Color themes");

        AtomicReference<ColorThemeResource> currentTheme = new AtomicReference<>(THEME_PICKER_THEME);
        AtomicReference<Header> currentThemeLabel = new AtomicReference<>(createHeaderForTheme(currentTheme.get()));

        final Size infoPanelSize = SCREEN_SIZE.withYLength(10).withRelativeXLength(-4);

        final Panel infoPanel = Components.panel()
                .wrapWithBox()
                .title("Components example:")
                .size(infoPanelSize)
                .position(Positions.create(2, 2).relativeToBottomOf(currentThemeLabel.get()))
                .build();

        final Button testButton = Components.button()
                .text("Button")
                .position(Positions.create(0, 2))
                .build();

        final CheckBox checkBox = Components.checkBox()
                .text("Checkbox")
                .position(Positions.create(0, 1).relativeToBottomOf(testButton))
                .build();

        final Header header = Components.header()
                .text("Header")
                .position(Positions.create(0, 1).relativeToBottomOf(checkBox))
                .build();

        final Label label = Components.label()
                .text("Label")
                .position(Positions.create(8, 0)
                        .relativeToRightOf(testButton))
                .build();

        RadioButtonGroup rbg = Components.radioButtonGroup()
                .size(Sizes.create(15, 3))
                .position(Positions.create(0, 1).relativeToBottomOf(label))
                .build();
        rbg.addOption("0", "Option 0");
        rbg.addOption("1", "Option 1");
        rbg.addOption("2", "Option 2");


        final Panel panel = Components.panel()
                .addBorder(Borders.newBuilder().build())
                .size(Sizes.create(20, 6))
                .title("Panel")
                .wrapWithBox()
                .wrapWithShadow()
                .position(Positions.create(10, 0)
                        .relativeToRightOf(label))
                .build();

        TextArea textArea = Components.textArea()
                .position(Positions.create(1, 0).relativeToRightOf(panel))
                .size(Sizes.create(20, 6))
                .text("Text box")
                .build();

        infoPanel.addComponent(currentThemeLabel.get());
        infoPanel.addComponent(testButton);
        infoPanel.addComponent(checkBox);
        infoPanel.addComponent(header);
        infoPanel.addComponent(label);
        infoPanel.addComponent(rbg);
        infoPanel.addComponent(panel);
        infoPanel.addComponent(textArea);

        screen.addComponent(infoPanel);


        final Size themePickerSize = SCREEN_SIZE
                .withRelativeYLength(-infoPanelSize.getYLength() - 4)
                .withXLength(SCREEN_SIZE.getXLength() / 3 - 1);

        final Size smallPanelSize = themePickerSize
                .withRelativeXLength(-2)
                .withYLength(themePickerSize.getYLength() / 2 - 1);

        final Panel solarizedLightPanel = Components.panel()
                .title("Solarized Light")
                .position(Positions.create(0, 1).relativeToBottomOf(infoPanel))
                .wrapWithBox()
                .size(smallPanelSize)
                .build();
        final Panel solarizedDarkPanel = Components.panel()
                .title("Solarized Dark")
                .position(Positions.create(0, 1).relativeToBottomOf(solarizedLightPanel))
                .wrapWithBox()
                .size(smallPanelSize)
                .build();
        final Panel zenburnPanel = Components.panel()
                .title("Zenburn")
                .position(Positions.create(1, 0).relativeToRightOf(solarizedLightPanel))
                .wrapWithBox()
                .size(smallPanelSize)
                .build();
        final Panel monokaiPanel = Components.panel()
                .title("Monokai")
                .position(Positions.create(1, 0).relativeToRightOf(solarizedDarkPanel))
                .wrapWithBox()
                .size(smallPanelSize)
                .build();
        final Panel otherPanel = Components.panel()
                .title("Other")
                .position(Positions.create(1, 0).relativeToRightOf(zenburnPanel))
                .wrapWithBox()
                .size(themePickerSize.withRelativeXLength(3).withRelativeYLength(-1))
                .build();


        screen.addComponent(solarizedLightPanel);
        screen.addComponent(solarizedDarkPanel);
        screen.addComponent(zenburnPanel);
        screen.addComponent(monokaiPanel);
        screen.addComponent(otherPanel);

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
                        !option.name().startsWith("ZENBURN"))
                .collect(Collectors.toList());

        final RadioButtonGroup slOptions = Components.radioButtonGroup()
                .size(themePickerSize
                        .withYLength(solarizedLightOptions.size())
                        .withRelativeXLength(-2))
                .build();
        solarizedLightOptions.forEach((option) -> slOptions.addOption(
                option.name(),
                option.name().replace("SOLARIZED_LIGHT_", "")));
        solarizedLightPanel.addComponent(slOptions);

        final RadioButtonGroup sdOptions = Components.radioButtonGroup()
                .size(themePickerSize
                        .withYLength(solarizedDarkOptions.size())
                        .withRelativeXLength(-2))
                .build();
        solarizedDarkOptions.forEach((option) -> sdOptions.addOption(
                option.name(),
                option.name().replace("SOLARIZED_DARK_", "")));
        solarizedDarkPanel.addComponent(sdOptions);

        final RadioButtonGroup zbOptions = Components.radioButtonGroup()
                .size(themePickerSize
                        .withYLength(zenburnOptions.size())
                        .withRelativeXLength(-2))
                .build();
        zenburnOptions.forEach((option) -> zbOptions.addOption(
                option.name(),
                option.name().replace("ZENBURN_", "")));
        zenburnPanel.addComponent(zbOptions);

        final RadioButtonGroup mOptions = Components.radioButtonGroup()
                .size(themePickerSize
                        .withYLength(monokaiOptions.size())
                        .withRelativeXLength(-2))
                .build();
        monokaiOptions.forEach((option) -> mOptions.addOption(
                option.name(),
                option.name().replace("MONOKAI_", "")));
        monokaiPanel.addComponent(mOptions);

        final RadioButtonGroup othOptions = Components.radioButtonGroup()
                .size(otherPanel.size()
                        .withYLength(otherOptions.size())
                        .withRelativeXLength(-2))
                .build();
        otherOptions.forEach((option) -> othOptions.addOption(
                option.name(),
                option.name()));
        otherPanel.addComponent(othOptions);

        slOptions.onSelection((selection -> {
            refreshTheme(screen, currentTheme, currentThemeLabel, infoPanel, selection);
        }));
        sdOptions.onSelection((selection -> {
            refreshTheme(screen, currentTheme, currentThemeLabel, infoPanel, selection);
        }));
        zbOptions.onSelection((selection -> {
            refreshTheme(screen, currentTheme, currentThemeLabel, infoPanel, selection);
        }));
        mOptions.onSelection((selection -> {
            refreshTheme(screen, currentTheme, currentThemeLabel, infoPanel, selection);
        }));
        othOptions.onSelection((selection -> {
            refreshTheme(screen, currentTheme, currentThemeLabel, infoPanel, selection);
        }));

        screen.applyColorTheme(currentTheme.get().getTheme());
        screen.display();

    }

    private static void refreshTheme(Screen screen,
                                     AtomicReference<ColorThemeResource> themeRef,
                                     AtomicReference<Header> labelRef,
                                     Panel infoPanel,
                                     Selection selection) {
        themeRef.set(ColorThemeResource.valueOf(selection.getKey()));
        infoPanel.removeComponent(labelRef.get());
        labelRef.set(createHeaderForTheme(themeRef.get()));
        infoPanel.addComponent(labelRef.get());
        screen.applyColorTheme(themeRef.get().getTheme());
    }

    private static Header createHeaderForTheme(ColorThemeResource currentTheme) {
        return Components.header()
                .text(currentTheme.name())
                .build();
    }

    private static void addScreenTitle(Screen screen, String title) {
        final Header header = Components.header()
                .text(title)
                .position(Positions.create(2, 1))
                .build();
        screen.addComponent(header);
    }
}
