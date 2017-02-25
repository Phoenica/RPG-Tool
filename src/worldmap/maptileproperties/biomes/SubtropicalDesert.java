package worldmap.maptileproperties.biomes;

import java.awt.*;

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
        return new Color(229, 233, 146);
    }

    @Override
    public TexturePaint getBiomeTexture() {
        return null;
    }

}