package org.hexworks.zircon.api.resource;

import org.hexworks.zircon.api.AppConfigs;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.builder.component.*;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ComponentsTest {

    @Test
    public void shouldProperlyCreateTextAreaBuilder() {
        assertThat(Components.textArea()).isInstanceOf(TextAreaBuilder.class);
    }

    @Test
    public void shouldProperlyCreateLogAreaBuilder() {
        assertThat(Components.logArea()).isInstanceOf(LogAreaBuilder.class);
    }

    @Test
    public void shouldProperlyCreateLogBoxBuilder() {
        assertThat(Components.textBox()).isInstanceOf(TextBoxBuilder.class);
    }

    @Test
    public void shouldProperlyCreateRadioButtonGroupBuilder() {
        assertThat(Components.radioButtonGroup()).isInstanceOf(RadioButtonGroupBuilder.class);
    }

    @Test
    public void shouldProperlyCreatePanelBuilder() {
        assertThat(Components.panel()).isInstanceOf(PanelBuilder.class);
    }

    @Test
    public void shouldProperlyCreateLabelBuilder() {
        assertThat(Components.label()).isInstanceOf(LabelBuilder.class);
    }

    @Test
    public void shouldProperlyCreateButtonBuilder() {
        assertThat(Components.button()).isInstanceOf(ButtonBuilder.class);
    }

    @Test
    public void shouldProperlyCreateCheckBoxBuilder() {
        assertThat(Components.checkBox()).isInstanceOf(CheckBoxBuilder.class);
    }

    @Test
    public void shouldProperlyCreateGameComponentBuilder() {
        AppConfigs.newConfig().enableBetaFeatures().build();
        assertThat(Components.gameComponent()).isInstanceOf(GameComponentBuilder.class);
    }

    @Test
    public void shouldProperlyCreateHeaderBuilder() {
        assertThat(Components.header()).isInstanceOf(HeaderBuilder.class);
    }
}
