package src.utils;

import java.awt.*;

public enum Pallette {
    FONDO("#FFFFFF"),
    ENCABEZADOS("#1E1E2F"),
    BOTONES("#FF6B6B"),
    TEXTO("#333333"),
    ALERTAS("#FF6600"),
    SECCIONES("#F4F4F4"),
    TITULOS("#1C1A27"),
    BARRA_BUSQUEDA("#F4F4F4"),
    SIDEBAR_FONDO("#FFFFFF"),
    TARJETAS_CURSOS("#E7E7E7"),
    TEXTO_SECUNDARIO("#666666"),
    BORDES_TARJETAS("#CCCCCC"),
    BOTONES_HOVER("#FF4C4C");


    private final String colorHex;

    Pallette(String colorHex) {
        this.colorHex = colorHex;
    }

    public Color getColor() {
        return HexToColor.getColor(colorHex);
    }
}