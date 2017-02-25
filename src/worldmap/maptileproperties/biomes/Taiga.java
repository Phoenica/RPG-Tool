package worldmap.maptileproperties.biomes;

import java.awt.*;

public class Taiga implements Biome {
    private static Taiga instance;
    private Taiga()
    {

    }
    public static Taiga getInstance()
    {
        if(instance == null) instance = new Taiga();
        return instance;
    }

    @Override
    public Color getBiomeColor() {
        return new Color(155,212, 157);
    }

    @Override
    public TexturePaint getBiomeTexture() {
        return null;
    }

}