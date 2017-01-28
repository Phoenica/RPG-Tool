package MapGeneration;

import MapGeneration.Graph.*;

import java.util.*;

public class VoronoiDiagram {
    public final int xSize;
    public final int ySize;
    public Point[][] pixelPoints;
    public ArrayList<Polygon> polygons;
    public ArrayList<Edge> edges;
    public ArrayList<Vertex> vertexes;
    private Point [] directions = {new Point(-1,0), new Point(0,-1)};
    public VoronoiDiagram(int x, int y)
    {
        xSize = x;
        ySize = y;
    }
    public void generate(int polyCount)
    {
        pixelPoints = new Point[xSize][ySize];
        polygons = generateCentralPoints(polyCount);
        edges = new ArrayList<>();
        vertexes = new ArrayList<>();
        generateVoronoiPixelDiagram();


    }


    private void generateVoronoiPixelDiagram() {
        for(int x = 0; x < xSize; x++)
        {
            for(int y = 0; y < ySize;y++)
            {
                Polygon ownerPolygon = polygons.get(0);
                Point currentPixel = new Point(x,y);
                for(Polygon polygon: polygons)
                {
                    if(ownerPolygon.centerPoint.distanceTo(currentPixel) > polygon.centerPoint.distanceTo(currentPixel))
                        ownerPolygon = polygon;
                }
                pixelPoints[x][y] = new Point(x,y,ownerPolygon);
                ownerPolygon.polygonPixels.add(pixelPoints[x][y]);
                setNeighbourPolygons(pixelPoints[x][y]);
            }
            if(x % (xSize/100) == 0) System.out.println("Progress: " + x/(xSize/100) + "%");
        }
    }
    private void setNeighbourPolygons(Point point)
    {
       for(Point direction: directions)
       {
           int tempX = point.getX() + direction.getX(), tempY = point.getY()+direction.getY();
           if(tempX < xSize && tempX >= 0 && tempY < ySize && tempY >= 0)
           {
               Point tempPoint = pixelPoints[tempX][tempY];
               if(tempPoint.parentPolygon != point.parentPolygon){
                   if(!point.parentPolygon.neighborPolygons.contains(tempPoint.parentPolygon))point.parentPolygon.neighborPolygons.add(tempPoint.parentPolygon);
                   if(!tempPoint.parentPolygon.neighborPolygons.contains(point.parentPolygon))tempPoint.parentPolygon.neighborPolygons.add(point.parentPolygon);
               }
           }
       }
    }

    private ArrayList<Polygon> generateCentralPoints(int polyCount) {
        ArrayList newPolygons = new ArrayList();
        Random random = new Random();
        SortedSet<Point> polygonCenters = new TreeSet();
        for(int i = 0; i < polyCount; i++)
        {
            polygonCenters.add(new Point(random.nextInt(xSize),random.nextInt(ySize)));
        }
        for(Point point: polygonCenters)
        {
            newPolygons.add(new Polygon(point.getX(),point.getY()));
        }
        return newPolygons;
    }
}
