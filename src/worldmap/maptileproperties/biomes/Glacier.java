package worldmap.maptileproperties.biomes;

import java.awt.*;

public class Glacier implements Biome {
    private static Glacier instance;
    private Glacier()
    {

    }
    public static Glacier getInstance()
    {
        if(instance == null) instance = new Glacier();
        return instance;
    }

    @Override
    public Color getBiomeColor() {
        return Color.CYAN;
    }

    @Override
    public TexturePaint getBiomeTexture() {
        return null;
    }

}