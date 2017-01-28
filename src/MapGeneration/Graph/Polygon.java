package MapGeneration.Graph;

import java.util.ArrayList;

public class Polygon implements Comparable<Polygon>,GraphElement {
    public final Point centerPoint;
    public WaterType water;
    public int distanceToOcean;
    public Polygon(int x, int y)
    {
        centerPoint = new Point(x,y);
        neighborPolygons = new ArrayList<>();
        borders = new ArrayList<>();
        corners = new ArrayList<>();
        polygonPixels = new ArrayList<>();
        distanceToOcean = -1;
    }
    public ArrayList<Point> polygonPixels;
    public ArrayList<Polygon> neighborPolygons;
    public ArrayList<Edge> borders;
    public ArrayList<Vertex> corners;

    public double getWaterToLandNeighbourRatio()
    {
        double counter =  0;
        for(Polygon neighbour: neighborPolygons)
        {
            if(neighbour.water != null && neighbour.water != WaterType.Land) counter++;
        }
        return counter / neighborPolygons.size();
    }

    public boolean hasOceanNeighbour()
    {
        for(Polygon neighbour: neighborPolygons)
        {
            if(neighbour.water == WaterType.Ocean) return true;
        }
        return false;
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
