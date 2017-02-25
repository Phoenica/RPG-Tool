package worldmap.maptileproperties.biomes;

import java.awt.*;

public class TropicalSeasonalForest implements Biome {
    private static TropicalSeasonalForest instance;
    private TropicalSeasonalForest()
    {

    }
    public static TropicalSeasonalForest getInstance()
    {
        if(instance == null) instance = new TropicalSeasonalForest();
        return instance;
    }

    @Override
    public Color getBiomeColor() {
        return new Color(162, 231, 190);
    }

    @Override
    public TexturePaint getBiomeTexture() {
        return null;
    }

}