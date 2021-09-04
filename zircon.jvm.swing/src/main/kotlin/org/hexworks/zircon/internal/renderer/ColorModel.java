package org.hexworks.zircon.internal.renderer;

import java.awt.*;
import java.awt.image.IndexColorModel;

public class ColorModel {
    public static final Color BLACK = new Color(0x00, 0x00, 0x00);
    public static final Color MAROON = new Color(0x80, 0x00, 0x00);
    public static final Color GREEN = new Color(0x00, 0x80, 0x00);
    public static final Color OLIVE = new Color(0x80, 0x80, 0x00);
    public static final Color NAVY = new Color(0x00, 0x00, 0x80);
    public static final Color PURPLE = new Color(0x80, 0x00, 0x80);
    public static final Color TEAL = new Color(0x00, 0x80, 0x80);
    public static final Color SILVER = new Color(0xC0, 0xC0, 0xC0);
    public static final Color GRAY = new Color(0x80, 0x80, 0x80);
    public static final Color RED = new Color(0xFF, 0x00, 0x00);
    public static final Color LIME = new Color(0x00, 0xFF, 0x00);
    public static final Color YELLOW = new Color(0xFF, 0xFF, 0x00);
    public static final Color BLUE = new Color(0x00, 0x00, 0xFF);
    public static final Color FUCHSIA = new Color(0xFF, 0x00, 0xFF);
    public static final Color AQUA = new Color(0x00, 0xFF, 0xFF);
    public static final Color WHITE = new Color(0xFF, 0xFF, 0xFF);

    private ColorModel() {}

    protected static final Color[] COLOR_ARRAY = {
            BLACK, MAROON, GREEN, OLIVE, NAVY, PURPLE, TEAL, SILVER,
            GRAY, RED, LIME, YELLOW, BLUE, FUCHSIA, AQUA, WHITE
    };

    protected static final byte[] COLOR_ARRAY_RED_COMPONENTS = {
            (byte) COLOR_ARRAY[0].getRed(),
            (byte) COLOR_ARRAY[1].getRed(),
            (byte) COLOR_ARRAY[2].getRed(),
            (byte) COLOR_ARRAY[3].getRed(),
            (byte) COLOR_ARRAY[4].getRed(),
            (byte) COLOR_ARRAY[5].getRed(),
            (byte) COLOR_ARRAY[6].getRed(),
            (byte) COLOR_ARRAY[7].getRed(),
            (byte) COLOR_ARRAY[8].getRed(),
            (byte) COLOR_ARRAY[9].getRed(),
            (byte) COLOR_ARRAY[10].getRed(),
            (byte) COLOR_ARRAY[11].getRed(),
            (byte) COLOR_ARRAY[12].getRed(),
            (byte) COLOR_ARRAY[13].getRed(),
            (byte) COLOR_ARRAY[14].getRed(),
            (byte) COLOR_ARRAY[15].getRed()
    };
    protected static final byte[] COLOR_ARRAY_GREEN_COMPONENTS = {
            (byte) COLOR_ARRAY[0].getGreen(),
            (byte) COLOR_ARRAY[1].getGreen(),
            (byte) COLOR_ARRAY[2].getGreen(),
            (byte) COLOR_ARRAY[3].getGreen(),
            (byte) COLOR_ARRAY[4].getGreen(),
            (byte) COLOR_ARRAY[5].getGreen(),
            (byte) COLOR_ARRAY[6].getGreen(),
            (byte) COLOR_ARRAY[7].getGreen(),
            (byte) COLOR_ARRAY[8].getGreen(),
            (byte) COLOR_ARRAY[9].getGreen(),
            (byte) COLOR_ARRAY[10].getGreen(),
            (byte) COLOR_ARRAY[11].getGreen(),
            (byte) COLOR_ARRAY[12].getGreen(),
            (byte) COLOR_ARRAY[13].getGreen(),
            (byte) COLOR_ARRAY[14].getGreen(),
            (byte) COLOR_ARRAY[15].getGreen()
    };
    protected static final byte[] COLOR_ARRAY_BLUE_COMPONENTS = {
            (byte) COLOR_ARRAY[0].getBlue(),
            (byte) COLOR_ARRAY[1].getBlue(),
            (byte) COLOR_ARRAY[2].getBlue(),
            (byte) COLOR_ARRAY[3].getBlue(),
            (byte) COLOR_ARRAY[4].getBlue(),
            (byte) COLOR_ARRAY[5].getBlue(),
            (byte) COLOR_ARRAY[6].getBlue(),
            (byte) COLOR_ARRAY[7].getBlue(),
            (byte) COLOR_ARRAY[8].getBlue(),
            (byte) COLOR_ARRAY[9].getBlue(),
            (byte) COLOR_ARRAY[10].getBlue(),
            (byte) COLOR_ARRAY[11].getBlue(),
            (byte) COLOR_ARRAY[12].getBlue(),
            (byte) COLOR_ARRAY[13].getBlue(),
            (byte) COLOR_ARRAY[14].getBlue(),
            (byte) COLOR_ARRAY[15].getBlue()
    };
}
