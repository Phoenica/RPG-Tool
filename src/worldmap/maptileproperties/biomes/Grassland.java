package worldmap.maptileproperties.biomes;

import java.awt.*;

public class Grassland implements Biome {
    private static Grassland instance;
    private Grassland()
    {

    }
    public static Grassland getInstance()
    {
        if(instance == null) instance = new Grassland();
        return instance;
    }

    @Override
    public Color getBiomeColor() {
        return new Color(175,255, 0);
    }

    @Override
    public TexturePaint getBiomeTexture() {
        return null;
    }

}