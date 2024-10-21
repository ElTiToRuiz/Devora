package src.utils;

import java.awt.*;

public enum Pallette {
    FONDO("#FFFFFF"),
    ENCABEZADOS("#003366"),
    BOTONES("#A4D65E"),
    TEXTO("#333333"),
    ALERTAS("#FF6600"),
    SECCIONES("#F2F2F2");

    private final String colorHex;

    Pallette(String colorHex) {
        this.colorHex = colorHex;
    }

    public Color getColor() {
        return HexToColor.getColor(colorHex);
    }
}