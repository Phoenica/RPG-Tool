package MapGeneration;

import MapGeneration.MapTileProperties.*;
import MapGeneration.MapTileProperties.Biomes.Biome;
import MapGeneration.Graph.Polygon;

public class MapTile extends Polygon<MapTile> implements Comparable<MapTile> {
    public WaterType water;
    public MapTile riverDirection;
    public Elevation elevation;
    public int distanceToOcean;
    public int distanceToLake;
    public Moisture moisture;
    public Temperature temperature;
    public Biome biome;
    public City city;
    public boolean river;

    public MapTile(Integer x, Integer y)
    {
        super(x,y);
        distanceToOcean = -1;
        elevation = Elevation.Water;
        distanceToLake = 999;
        river = false;
    }

    public double getWaterToLandNeighbourRatio()
    {
        double counter =  0;
        for(MapTile neighbour: neighborPolygons)
        {
            if((neighbour.water != null && neighbour.water != WaterType.Land)) counter++;
        }
        return counter / neighborPolygons.size();
    }

    public void setPotentialRiverDirection()
    {
        riverDirection = this;
        if(hasOceanNeighbour())
        {
            for(Polygon neighbourPolygon : neighborPolygons)
            {
                if(((MapTile)neighbourPolygon).water == WaterType.Ocean){
                    riverDirection = (MapTile)neighbourPolygon;
                break;}
            }

        }
        for(Polygon polygon : neighborPolygons)
        {
            if(((MapTile)polygon).elevation.ordinal() < riverDirection.elevation.ordinal() && ((MapTile)polygon).riverDirection != this) {
                riverDirection = (MapTile)polygon;
            }
            if(riverDirection.elevation.ordinal() == 0) break;
        }
        if(riverDirection == this)
        {
            for(MapTile mapTile : neighborPolygons)
            {
                if(mapTile.distanceToOcean < riverDirection.distanceToOcean && mapTile.riverDirection != this) {
                    riverDirection = mapTile;
                }
                if(riverDirection.elevation.ordinal() == 0) break;
            }
        }
        if(riverDirection == this)
        {
            for(MapTile mapTile : neighborPolygons)
            {
                if(mapTile.river == true && mapTile.riverDirection != this)
                {
                    riverDirection = mapTile;
                }
            }
        }

    }
    public int getDistanceToWater()
    {
        if(distanceToOcean > distanceToLake) return distanceToLake;
        else return distanceToOcean;
    }
    public boolean hasLakeNeighbour()
    {
        for(MapTile neighbour: neighborPolygons)
        {
            if(neighbour.water == WaterType.Lake) return true;
        }
        return false;
    }
    public boolean hasRiverNeighbour()
    {
        for(MapTile neighbour: neighborPolygons)
        {
            if(neighbour.river == true) return true;
        }
        return false;
    }
    public boolean hasCityNeighbour()
    {
        for(MapTile neighbour: neighborPolygons)
        {
            if(neighbour.city != null) return true;
        }
        return false;
    }
    public boolean hasOceanNeighbour()
    {
        for(MapTile neighbour: neighborPolygons)
        {
            if(neighbour.water == WaterType.Ocean) return true;
        }
        return false;
    }
    
    public void setBiome()
    {
        biome = BiomeChoser.getBiome(this);
    }
    @Override
    public int compareTo(MapTile o) {
        if(this.centerPoint.getX() > o.centerPoint.getX()) return 1;
        else if(this.centerPoint.getX() == o.centerPoint.getX())
        {
            if(this.centerPoint.getY() > o.centerPoint.getY()) return 1;
            else if(this.centerPoint.getY() == o.centerPoint.getY()) return 0;
            else return -1;
        }
        else return -1;
    }

}
