package MapGeneration.Graph.PolygonProperties.Biomes;

import java.awt.*;

/**
 * Created by Phoenicia on 02.02.2017.
 */
public class SubtropicalDesert implements Biome {
    private static SubtropicalDesert instance;
    private SubtropicalDesert()
    {

    }
    public static SubtropicalDesert getInstance()
    {
        if(instance == null) instance = new SubtropicalDesert();
        return instance;
    }

    @Override
    public Color getBiomeColor() {
        return new Color(233,221,199);
    }

    @Override
    public TexturePaint getBiomeTexture() {
        return null;
    }

}