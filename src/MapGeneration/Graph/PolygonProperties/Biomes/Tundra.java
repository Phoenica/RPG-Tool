package MapGeneration.Graph.PolygonProperties.Biomes;

import java.awt.*;


public class Tundra implements Biome {
    private static Tundra instance;
    private Tundra()
    {

    }
    public static Tundra getInstance()
    {
        if(instance == null) instance = new Tundra();
        return instance;
    }

    @Override
    public Color getBiomeColor() {
        return new Color(221,221,221);
    }

    @Override
    public TexturePaint getBiomeTexture() {
        return null;
    }

}