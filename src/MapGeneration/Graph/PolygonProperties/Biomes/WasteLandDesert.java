package MapGeneration.Graph.PolygonProperties.Biomes;

import java.awt.*;

/**
 * Created by Phoenicia on 02.02.2017.
 */
public class WasteLandDesert implements Biome {
    private static WasteLandDesert instance;
    private WasteLandDesert()
    {

    }
    public static WasteLandDesert getInstance()
    {
        if(instance == null) instance = new WasteLandDesert();
        return instance;
    }

    @Override
    public Color getBiomeColor() {
        return new Color(209,205,151);
    }

    @Override
    public TexturePaint getBiomeTexture() {
        return null;
    }
}