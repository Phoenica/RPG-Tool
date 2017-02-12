package MapGeneration.Graph.PolygonProperties.Biomes;

import java.awt.*;

/**
 * Created by Phoenicia on 02.02.2017.
 */
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