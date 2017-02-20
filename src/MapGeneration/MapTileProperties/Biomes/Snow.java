package MapGeneration.MapTileProperties.Biomes;

import java.awt.*;


public class Snow implements Biome {
    private static Snow instance;
    private Snow()
    {

    }
    public static Snow getInstance()
    {
        if(instance == null) instance = new Snow();
        return instance;
    }

    @Override
    public Color getBiomeColor() {
        return Color.WHITE;
    }

    @Override
    public TexturePaint getBiomeTexture() {
        return null;
    }


}
