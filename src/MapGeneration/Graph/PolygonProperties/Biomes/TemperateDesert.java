package MapGeneration.Graph.PolygonProperties.Biomes;

import java.awt.*;

/**
 * Created by Phoenicia on 02.02.2017.
 */
public class TemperateDesert implements Biome {
    private static TemperateDesert instance;
    private TemperateDesert()
    {

    }
    public static TemperateDesert getInstance()
    {
        if(instance == null) instance = new TemperateDesert();
        return instance;
    }

    @Override
    public Color getBiomeColor() {
        return new Color(228,232,202);
    }

    @Override
    public TexturePaint getBiomeTexture() {
        return null;
    }

}