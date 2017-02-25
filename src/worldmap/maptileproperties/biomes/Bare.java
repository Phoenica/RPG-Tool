package worldmap.maptileproperties.biomes;

import java.awt.*;

public class Bare implements Biome {
    private static Bare instance;
    private Bare()
    {

    }
    public static Bare getInstance()
    {
        if(instance == null) instance = new Bare();
        return instance;
    }

    @Override
    public Color getBiomeColor() {
        return new Color(187,187,187);
    }

    @Override
    public TexturePaint getBiomeTexture() {
        return null;
    }

}