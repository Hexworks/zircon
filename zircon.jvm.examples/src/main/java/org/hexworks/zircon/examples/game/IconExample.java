package org.hexworks.zircon.examples.game;

import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.builder.application.AppConfigBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IconExample {

    public static void main(String[] args) throws IOException {
        SwingApplications.startTileGrid(
                new AppConfigBuilder()
//                .withIcon(loadIcon("/image_dictionary/hexworks_logo.png"))
                        .withIcon("image_dictionary/hexworks_logo.png")
                        .build());
    }

    private static byte[] loadIcon(final String path) throws IOException {
        try (final InputStream inputStream = IconExample.class.getResourceAsStream(path)) {
            final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            return buffer.toByteArray();
        }
    }
}
