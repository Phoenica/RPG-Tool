package MapGeneration.Graph.PolygonProperties.Biomes;

import java.awt.*;


public class Water implements Biome {
    private static Water instance;
    private Water()
    {

    }
    public static Water getInstance()
    {
        if(instance == null) instance = new Water();
        return instance;
    }

    @Override
    public Color getBiomeColor() {
        return Color.BLUE;
    }

    @Override
    public TexturePaint getBiomeTexture() {
        return null;
    }

}
