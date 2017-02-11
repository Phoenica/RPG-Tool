package MapGeneration.Graph.PolygonProperties.Biomes;

import java.awt.*;

/**
 * Created by Phoenicia on 02.02.2017.
 */
public class TemperateDeciduousForest implements Biome {
    private static TemperateDeciduousForest instance;
    private TemperateDeciduousForest()
    {

    }
    public static TemperateDeciduousForest getInstance()
    {
        if(instance == null) instance = new TemperateDeciduousForest();
        return instance;
    }

    @Override
    public Color getBiomeColor() {
        return new Color(180,201,169);
    }

    @Override
    public TexturePaint getBiomeTexture() {
        return null;
    }

}