package worldmap.maptileproperties.biomes;

import java.awt.*;

public class Tundra implements Biome {
    private static Tundra instance;
    private Tundra()
    {

    }
    public static Tundra getInstance()
    {
        if(instance == null) instance = new Tundra();
        return instance;
    }

    @Override
    public Color getBiomeColor() {
        return new Color(189,221, 190);
    }

    @Override
    public TexturePaint getBiomeTexture() {
        return null;
    }

}