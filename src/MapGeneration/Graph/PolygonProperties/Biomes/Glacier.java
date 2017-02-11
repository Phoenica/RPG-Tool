package MapGeneration.Graph.PolygonProperties.Biomes;

import java.awt.*;

/**
 * Created by Phoenicia on 02.02.2017.
 */
public class Glacier implements Biome {
    private static Glacier instance;
    private Glacier()
    {

    }
    public static Glacier getInstance()
    {
        if(instance == null) instance = new Glacier();
        return instance;
    }

    @Override
    public Color getBiomeColor() {
        return Color.CYAN;
    }

    @Override
    public TexturePaint getBiomeTexture() {
        return null;
    }

}