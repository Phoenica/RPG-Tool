package MapGeneration.Graph.PolygonProperties.Biomes;

import java.awt.*;

/**
 * Created by Phoenicia on 02.02.2017.
 */
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