package MapGeneration.Graph.PolygonProperties.Biomes;

import java.awt.*;

/**
 * Created by Phoenicia on 02.02.2017.
 */
public class TemperateRainForest implements Biome {
    private static TemperateRainForest instance;
    private TemperateRainForest()
    {

    }
    public static TemperateRainForest getInstance()
    {
        if(instance == null) instance = new TemperateRainForest();
        return instance;
    }

    @Override
    public Color getBiomeColor() {
        return new Color(124,196, 0);
    }

    @Override
    public TexturePaint getBiomeTexture() {
        return null;
    }

}