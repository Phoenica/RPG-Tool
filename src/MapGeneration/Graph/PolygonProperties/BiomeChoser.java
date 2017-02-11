package MapGeneration.Graph.PolygonProperties;

import MapGeneration.Graph.Polygon;
import MapGeneration.Graph.PolygonProperties.Biomes.*;


public class BiomeChoser {
    private static final Biome biomes[][];

    static {
        biomes = new Biome[][]{
                {Scorched.getInstance(),Bare.getInstance(),Tundra.getInstance(),Snow.getInstance(),Snow.getInstance(), Glacier.getInstance()},
                {TemperateDesert.getInstance(),TemperateDesert.getInstance(),Shrubland.getInstance(),Shrubland.getInstance(),Taiga.getInstance(),Water.getInstance()},
                {TemperateDesert.getInstance(),Grassland.getInstance(),Grassland.getInstance(),TemperateDeciduousForest.getInstance(),TemperateRainForest.getInstance(),Water.getInstance()},
                {SubtropicalDesert.getInstance(),Grassland.getInstance(),TropicalSeasonalForest.getInstance(),TropicalSeasonalForest.getInstance(),TropicalRainForest.getInstance(),Water.getInstance()},
                {WasteLandDesert.getInstance(),SubtropicalDesert.getInstance(),Grassland.getInstance(),TropicalSeasonalForest.getInstance(),TropicalSeasonalForest.getInstance(),Water.getInstance()}
        };
    }
    public static Biome getBiome(Polygon polygon)
    {
        return biomes[polygon.temperature.ordinal()][polygon.moisture.ordinal()];
    }
}
