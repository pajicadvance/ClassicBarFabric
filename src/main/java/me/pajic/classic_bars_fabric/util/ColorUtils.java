package me.pajic.classic_bars_fabric.util;

import me.pajic.classic_bars_fabric.config.ModConfig;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.List;

public class ColorUtils {
    public static Color hex2Color(String s) {
        int i1 = Integer.decode(s);
        int r = i1 >> 16 & 0xFF;
        int g = i1 >> 8 & 0xFF;
        int b = i1 & 0xFF;
        return Color.from(r, g, b);
    }

    public static List<Color> awt2ColorList(List<java.awt.Color> hex) {
        List<Color> list = new ArrayList<>();
        hex.forEach(s -> list.add(awt2Color(s)));
        return list;
    }

    public static Color awt2Color(java.awt.Color awtColor) {
        return Color.from(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue());
    }

    public static List<java.awt.Color> string2AwtColorList(List<String> hex) {
        List<java.awt.Color> list = new ArrayList<>();
        hex.forEach(s -> list.add(java.awt.Color.decode(s)));
        return list;
    }

    public static Color calculateScaledColor(double d1, double d2, HealthEffect effect) {
        double d3 = (d1 / d2);

        List<Color> colorCodes;
        List<? extends Double> colorFractions;

        switch (effect) {
            case NONE: colorCodes = awt2ColorList(ModConfig.normalColors);
                colorFractions = ModConfig.normalFractions; break;
            case POISON: colorCodes = awt2ColorList(ModConfig.poisonedColors);
                colorFractions = ModConfig.poisonedFractions; break;
            case WITHER: colorCodes = awt2ColorList(ModConfig.witheredColors);
                colorFractions = ModConfig.witheredFractions; break;
            case FROZEN:return awt2Color(ModConfig.frozenHealthColor);
            default: return Color.BLACK;
        }

        if (colorCodes.size() != colorFractions.size()) return Color.BLACK;
        int i1 = colorFractions.size() - 1;
        int i3 = 0;
        for (int i2 = 0; i2 < i1; i2++) {
            if (d3 < colorFractions.get(i2)) break;
            i3++;
        }

        //return first color in the list if health is too low
        if (d3 <= colorFractions.getFirst())
            return colorCodes.getFirst();
        //return last color in the list if health is too high
        if (d3 >= colorFractions.getLast())
            return colorCodes.getLast();

        Color c1 = colorCodes.get(i3 - 1);
        Color c2 = colorCodes.get(i3);

        double d4 = Mth.inverseLerp(d3,colorFractions.get(i3-1),colorFractions.get(i3));
        return c1.colorBlend(c2, (float) d4);
    }
}