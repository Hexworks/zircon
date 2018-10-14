package org.hexworks.zircon.api.resource;

import org.hexworks.zircon.api.AppConfigs;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.builder.component.*;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ComponentsTest {

    @Test
    public void shouldProperlyCreateTextAreaBuilder() {
        assertThat(Components.textArea()).isEqualTo(TextAreaBuilder.newBuilder());
    }

    @Test
    public void shouldProperlyCreateLogAreaBuilder() {
        assertThat(Components.logArea()).isEqualTo(LogAreaBuilder.newBuilder());
    }

    @Test
    public void shouldProperlyCreateLogBoxBuilder() {
        assertThat(Components.textBox()).isEqualTo(TextBoxBuilder.newBuilder());
    }

    @Test
    public void shouldProperlyCreateRadioButtonGroupBuilder() {
        assertThat(Components.radioButtonGroup()).isEqualTo(RadioButtonGroupBuilder.newBuilder());
    }

    @Test
    public void shouldProperlyCreatePanelBuilder() {
        assertThat(Components.panel()).isEqualTo(PanelBuilder.newBuilder());
    }

    @Test
    public void shouldProperlyCreateLabelBuilder() {
        assertThat(Components.label()).isEqualTo(LabelBuilder.newBuilder());
    }

    @Test
    public void shouldProperlyCreateButtonBuilder() {
        assertThat(Components.button()).isEqualTo(ButtonBuilder.newBuilder());
    }

    @Test
    public void shouldProperlyCreateCheckBoxBuilder() {
        assertThat(Components.checkBox()).isEqualTo(CheckBoxBuilder.newBuilder());
    }

    @Test
    public void shouldProperlyCreateGameComponentBuilder() {
        AppConfigs.newConfig().enableBetaFeatures().build();
        assertThat(Components.gameComponent()).isEqualTo(GameComponentBuilder.newBuilder());
    }

    @Test
    public void shouldProperlyCreateHeaderBuilder() {
        assertThat(Components.header()).isEqualTo(HeaderBuilder.newBuilder());
    }
}
