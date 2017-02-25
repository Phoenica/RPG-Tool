package worldmap;

import worldmap.maptileproperties.*;
import worldmap.maptileproperties.biomes.Biome;
import voronoigeneration.Polygon;

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

    double getWaterToLandNeighbourRatio()
    {
        double counter =  0;
        for(MapTile neighbour: neighborPolygons)
        {
            if((neighbour.water != null && neighbour.water != WaterType.Land)) counter++;
        }
        return counter / neighborPolygons.size();
    }

    void setPotentialRiverDirection()
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
        neighborPolygons.stream().filter(polygon -> polygon.elevation.ordinal() < riverDirection.elevation.ordinal() && ((MapTile) polygon).riverDirection != this).forEach(polygon -> {
            riverDirection = polygon;
        });
        if(riverDirection == this)
        {
            neighborPolygons.stream().filter(mapTile -> mapTile.distanceToOcean < riverDirection.distanceToOcean && mapTile.riverDirection != this).forEach(mapTile -> {
                riverDirection = mapTile;
            });
        }
        if(riverDirection == this)
        {
            neighborPolygons.stream().filter(mapTile -> mapTile.river && mapTile.riverDirection != this).forEach(mapTile -> {
                riverDirection = mapTile;
            });
        }
    }
    int getDistanceToWater()
    {
        if(distanceToOcean > distanceToLake) return distanceToLake;
        else return distanceToOcean;
    }
    boolean hasLakeNeighbour()
    {
        for(MapTile neighbour: neighborPolygons)
        {
            if(neighbour.water == WaterType.Lake) return true;
        }
        return false;
    }
    boolean hasRiverNeighbour()
    {
        for(MapTile neighbour: neighborPolygons)
        {
            if(neighbour.river) return true;
        }
        return false;
    }
    boolean hasCityNeighbour()
    {
        for(MapTile neighbour: neighborPolygons)
        {
            if(neighbour.city != null) return true;
        }
        return false;
    }
    boolean hasOceanNeighbour()
    {
        for(MapTile neighbour: neighborPolygons)
        {
            if(neighbour.water == WaterType.Ocean) return true;
        }
        return false;
    }
    
    void setBiome()
    {
        biome = BiomeChooser.getBiome(this);
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
