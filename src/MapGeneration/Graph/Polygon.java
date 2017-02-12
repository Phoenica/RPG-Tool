package MapGeneration.Graph;

import MapGeneration.Graph.PolygonProperties.*;
import MapGeneration.Graph.PolygonProperties.Biomes.Biome;

import java.util.ArrayList;

public class Polygon implements Comparable<Polygon>,GraphElement {
    public final Point centerPoint;
    public WaterType water;
    public Polygon riverDirection;
    public Elevation elevation;
    public int distanceToOcean;
    public int distanceToLake;
    public Moisture moisture;
    public Temperature temperature;
    public Biome biome;
    public City city;
    public boolean river;

    public Polygon(int x, int y)
    {
        centerPoint = new Point(x,y);
        neighborPolygons = new ArrayList<>();
        polygonPixels = new ArrayList<>();
        distanceToOcean = -1;
        elevation = Elevation.Water;
        distanceToLake = 999;
        river = false;
    }
    public ArrayList<Point> polygonPixels;
    public ArrayList<Polygon> neighborPolygons;

    public double getWaterToLandNeighbourRatio()
    {
        double counter =  0;
        for(Polygon neighbour: neighborPolygons)
        {
            if(neighbour.water != null && neighbour.water != WaterType.Land) counter++;
        }
        return counter / neighborPolygons.size();
    }

    public void setPotentialRiverDirection()
    {
        riverDirection = this;
        if(hasOceanNeighbour())
        {
            for(Polygon polygon: neighborPolygons)
            {
                if(polygon.water == WaterType.Ocean){
                    riverDirection = polygon;
                break;}
            }

        }
        for(Polygon polygon: neighborPolygons)
        {
            if(polygon.elevation.ordinal() < riverDirection.elevation.ordinal() && polygon.riverDirection != this) {
                riverDirection = polygon;
            }
            if(riverDirection.elevation.ordinal() == 0) break;
        }
        if(riverDirection == this)
        {
            for(Polygon polygon: neighborPolygons)
            {
                if(polygon.distanceToOcean < riverDirection.distanceToOcean && polygon.riverDirection != this) {
                    riverDirection = polygon;
                }
                if(riverDirection.elevation.ordinal() == 0) break;
            }
        }
        if(riverDirection == this)
        {
            for(Polygon polygon: neighborPolygons)
            {
                if(polygon.river == true && polygon.riverDirection != this)
                {
                    riverDirection = polygon;
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
        for(Polygon neighbour: neighborPolygons)
        {
            if(neighbour.water == WaterType.Lake) return true;
        }
        return false;
    }
    public boolean hasRiverNeighbour()
    {
        for(Polygon neighbour: neighborPolygons)
        {
            if(neighbour.river == true) return true;
        }
        return false;
    }
    public boolean hasCityNeighbour()
    {
        for(Polygon neighbour: neighborPolygons)
        {
            if(neighbour.city != null) return true;
        }
        return false;
    }
    public boolean hasOceanNeighbour()
    {
        for(Polygon neighbour: neighborPolygons)
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
    public int compareTo(Polygon o) {
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
