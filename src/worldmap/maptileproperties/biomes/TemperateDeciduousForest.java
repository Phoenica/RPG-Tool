package worldmap.maptileproperties.biomes;

import java.awt.*;

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
        return new Color(48,201, 62);
    }

    @Override
    public TexturePaint getBiomeTexture() {
        return null;
    }

}