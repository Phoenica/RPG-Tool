package WorldMap.MapTileProperties.Biomes;

import java.awt.*;

public class TropicalRainForest implements Biome {
    private static TropicalRainForest instance;
    private TropicalRainForest()
    {

    }
    public static TropicalRainForest getInstance()
    {
        if(instance == null) instance = new TropicalRainForest();
        return instance;
    }

    @Override
    public Color getBiomeColor() {
        return new Color(0, 190, 100);
    }

    @Override
    public TexturePaint getBiomeTexture() {
        return null;
    }

}