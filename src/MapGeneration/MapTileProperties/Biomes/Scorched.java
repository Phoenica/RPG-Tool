package MapGeneration.MapTileProperties.Biomes;

import java.awt.*;

/**
 * Created by Phoenicia on 02.02.2017.
 */
public class Scorched implements Biome {
    private static Scorched instance;
    private Scorched()
    {

    }
    public static Scorched getInstance()
    {
        if(instance == null) instance = new Scorched();
        return instance;
    }

    @Override
    public Color getBiomeColor() {
        return new Color(153,153,153);
    }

    @Override
    public TexturePaint getBiomeTexture() {
        return null;
    }

}