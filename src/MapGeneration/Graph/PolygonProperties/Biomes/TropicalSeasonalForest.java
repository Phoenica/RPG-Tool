package MapGeneration.Graph.PolygonProperties.Biomes;

import java.awt.*;


public class TropicalSeasonalForest implements Biome {
    private static TropicalSeasonalForest instance;
    private TropicalSeasonalForest()
    {

    }
    public static TropicalSeasonalForest getInstance()
    {
        if(instance == null) instance = new TropicalSeasonalForest();
        return instance;
    }

    @Override
    public Color getBiomeColor() {
        return new Color(169,204,164);
    }

    @Override
    public TexturePaint getBiomeTexture() {
        return null;
    }

}