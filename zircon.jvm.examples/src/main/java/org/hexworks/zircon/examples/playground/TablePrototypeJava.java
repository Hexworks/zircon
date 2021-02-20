package org.hexworks.zircon.examples.playground;

import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.ColorThemes;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.component.ComponentAlignment;
import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer;

import java.util.Arrays;
import java.util.List;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.ComponentDecorations.shadow;

public class TablePrototypeJava {

    public static void main(String[] args) {

        List<TableField<Model, Object>> fields = Arrays.asList(
                new TableField<>("#", 5, Model::getId),
                new TableField<>("First Name", 15, Model::getFirstName),
                new TableField<>("Last Name", 15, Model::getLastName),
                new TableField<>("Job", 15, Model::getJob)
        );

        List<Model> models = Arrays.asList(
                new Model(1, "Sam", "Lawrey", "Stoneworker"),
                new Model(2, "Jane", "Fischer", "Woodcutter"),
                new Model(3, "John", "Smith", "Mason"),
                new Model(4, "Steve", "Thrush", "Farmer"),
                new Model(5, "Bob", "Wagner", "Musician"),
                new Model(5, "Amanda", "Flair", "Brewer")
        );

        TableOld<Model> table = new TableOld<>(fields, models);

        Screen screen = Screen.create(SwingApplications.startTileGrid(
                AppConfig.newBuilder()
                        .withDefaultTileset(CP437TilesetResources.rexPaint16x16())
                        .build()
        ));

        Panel panel = Components
                .panel()
                .withComponentRenderer(new NoOpComponentRenderer<>())
                .withAlignmentWithin(screen, ComponentAlignment.CENTER)
                .withSize(table.getRoot().getSize().plus(Size.create(3, 3)))
                .withDecorations(box(BoxType.SINGLE, "Table"), shadow())
                .build();

        panel.addFragment(table);

        screen.addComponent(panel);

        screen.display();
        screen.setTheme(ColorThemes.gamebookers());
    }

    static class Model {
        private int id;
        private String firstName;
        private String lastName;
        private String job;

        public Model(int id, String firstName, String lastName, String job) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.job = job;
        }

        public int getId() {
            return id;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getJob() {
            return job;
        }
    }

}
