package WorldMap.MapTileProperties.Biomes;

import java.awt.*;

public class Shrubland implements Biome {
    private static Shrubland instance;
    private Shrubland()
    {

    }
    public static Shrubland getInstance()
    {
        if(instance == null) instance = new Shrubland();
        return instance;
    }

    @Override
    public Color getBiomeColor() {
        return new Color(140, 205, 97);
    }

    @Override
    public TexturePaint getBiomeTexture() {
        return null;
    }
}