package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.AppConfigs;
import org.hexworks.zircon.api.Borders;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.Screens;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.component.CheckBox;
import org.hexworks.zircon.api.component.Header;
import org.hexworks.zircon.api.component.Label;
import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.component.RadioButtonGroup;
import org.hexworks.zircon.api.component.RadioButtonGroup.Selection;
import org.hexworks.zircon.api.component.TextBox;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.CP437TilesetResource;
import org.hexworks.zircon.api.resource.ColorThemeResource;
import org.hexworks.zircon.api.screen.Screen;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class ColorThemeSwitcher {

    private static final Size SCREEN_SIZE = Sizes.create(80, 40);
    private static final CP437TilesetResource TILESET = CP437TilesetResource.ROGUE_YUN_16X16;
    private static final ColorThemeResource THEME_PICKER_THEME = ColorThemeResource.GAMEBOOKERS;

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(TILESET)
                .defaultSize(SCREEN_SIZE)
                .build());

        Screen screen = Screens.createScreenFor(tileGrid);

        addScreenTitle(screen, "Color themes");

        AtomicReference<ColorThemeResource> currentTheme = new AtomicReference<>(THEME_PICKER_THEME);
        AtomicReference<Label> currentThemeLabel = new AtomicReference<>(createLabelForTheme(currentTheme.get()));

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
                        .relativeToRightOf(testButton)
                .withRelativeY(-1))
                .build();


        final Panel panel = Components.panel()
                .addBorder(Borders.newBuilder().build())
                .size(Sizes.create(20, 7))
                .title("Panel")
                .wrapWithBox()
                .wrapWithShadow()
                .position(Positions.create(3, 0)
                        .relativeToRightOf(label))
                .build();

        RadioButtonGroup rbg = Components.radioButtonGroup()
                .size(Sizes.create(20, 3))
                .position(Positions.create(1, 1).relativeToRightOf(panel))
                .build();
        rbg.addOption("0", "Option 0");
        rbg.addOption("1", "Option 1");
        rbg.addOption("2", "Option 2");

        TextBox textBox = Components.textBox()
                .position(Positions.create(1, 0).relativeToRightOf(rbg))
                .text("Text box")
                .build();

        infoPanel.addComponent(currentThemeLabel.get());
        infoPanel.addComponent(testButton);
        infoPanel.addComponent(checkBox);
        infoPanel.addComponent(header);
        infoPanel.addComponent(label);
        infoPanel.addComponent(panel);
        infoPanel.addComponent(rbg);


        screen.addComponent(infoPanel);


        final Size themePickerSize = SCREEN_SIZE
                .withRelativeYLength(-infoPanelSize.getYLength() - 4)
                .withXLength(SCREEN_SIZE.getXLength() / 3 - 1);

        final Panel solarizedLightPanel = Components.panel()
                .title("Solarized Light")
                .position(Positions.create(0, 1).relativeToBottomOf(infoPanel))
                .wrapWithBox()
                .size(themePickerSize.withRelativeXLength(-2))
                .build();
        final Panel solarizedDarkPanel = Components.panel()
                .title("Solarized Dark")
                .position(Positions.create(1, 0).relativeToRightOf(solarizedLightPanel))
                .wrapWithBox()
                .size(themePickerSize.withRelativeXLength(-2))
                .build();
        final Panel otherPanel = Components.panel()
                .title("Other")
                .position(Positions.create(1, 0).relativeToRightOf(solarizedDarkPanel))
                .wrapWithBox()
                .size(themePickerSize.withRelativeXLength(3))
                .build();


        screen.addComponent(solarizedLightPanel);
        screen.addComponent(solarizedDarkPanel);
        screen.addComponent(otherPanel);

        final List<ColorThemeResource> solarizedLightOptions = Arrays.stream(ColorThemeResource.values())
                .filter(option -> option.name().startsWith("SOLARIZED_LIGHT"))
                .collect(Collectors.toList());
        final List<ColorThemeResource> solarizedDarkOptions = Arrays.stream(ColorThemeResource.values())
                .filter((option) -> option.name().startsWith("SOLARIZED_DARK"))
                .collect(Collectors.toList());
        final List<ColorThemeResource> otherOptions = Arrays.stream(ColorThemeResource.values())
                .filter((option) -> !option.name().startsWith("SOLARIZED"))
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
            sdOptions.clearSelection();
            othOptions.clearSelection();
        }));
        sdOptions.onSelection((selection -> {
            refreshTheme(screen, currentTheme, currentThemeLabel, infoPanel, selection);
            slOptions.clearSelection();
            othOptions.clearSelection();
        }));
        othOptions.onSelection((selection -> {
            refreshTheme(screen, currentTheme, currentThemeLabel, infoPanel, selection);
            slOptions.clearSelection();
            sdOptions.clearSelection();
        }));

        screen.applyColorTheme(currentTheme.get().getTheme());
        screen.display();

    }

    private static void refreshTheme(Screen screen,
                                     AtomicReference<ColorThemeResource> themeRef,
                                     AtomicReference<Label> labelRef,
                                     Panel infoPanel,
                                     Selection selection) {
        themeRef.set(ColorThemeResource.valueOf(selection.getKey()));
        infoPanel.removeComponent(labelRef.get());
        labelRef.set(createLabelForTheme(themeRef.get()));
        infoPanel.addComponent(labelRef.get());
        screen.applyColorTheme(themeRef.get().getTheme());
    }

    private static Label createLabelForTheme(ColorThemeResource currentTheme) {
        return Components.label()
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
