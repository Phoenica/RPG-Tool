package MapGeneration;


import MapGeneration.Graph.Polygon;
import MapGeneration.Graph.WaterType;

import java.util.*;


public class Map {
    VoronoiDiagram diagram;
    Screen generatedMap;
    Options settings;

    public Screen getMap(Options settings)
    {
        this.settings = settings;
        generateMap();
        return generatedMap;
    }

    private void generateMap()
    {
        diagram = new VoronoiDiagram(settings.getXSize(),settings.getYSize());
        diagram.generate(settings.getPolygons());
        createBodiesOfWater();
        generateElevations();
        generatedMap = new Screen(diagram);
    }

    private void createBodiesOfWater() {
        setMapBordersToWater();
        createOcean();
        createLakes();
    }



    private void generateElevations() {
        Queue<Polygon> polygonQueue = new ArrayDeque<>();
        Set<Polygon> polygonSet = new HashSet<>();
        getCoastPolygons(polygonQueue, polygonSet);
        calculatePolygonsDistanceToOcean(polygonQueue, polygonSet);
        setElevations(polygonSet);
    }

    private void setElevations(Set<Polygon> polygonSet) {
        int maxDistance = 0;
        int avargeDistanceToOcean = 0;
        for(Polygon polygon: polygonSet)
        {
            if(polygon.distanceToOcean > maxDistance) maxDistance = polygon.distanceToOcean;
            avargeDistanceToOcean += polygon.distanceToOcean;
        }
        avargeDistanceToOcean = avargeDistanceToOcean / polygonSet.size();
        for(Polygon polygon: polygonSet)
        {
            if(polygon.distanceToOcean <= 2) polygon.elevation = 1;
            else if(polygon.distanceToOcean < avargeDistanceToOcean+4) polygon.elevation = 2;
            else if(polygon.distanceToOcean < (maxDistance+5 + avargeDistanceToOcean)/2) polygon.elevation = 3;
            else polygon.elevation = 4;

        }
    }

    private void calculatePolygonsDistanceToOcean(Queue<Polygon> polygonQueue, Set<Polygon> polygonSet) {
        while(!polygonQueue.isEmpty())
        {
            Polygon polygon = polygonQueue.poll();
            for(Polygon polygonNeigbor:polygon.neighborPolygons)
            {
                if(!polygonSet.contains(polygonNeigbor))
                {
                    polygonNeigbor.distanceToOcean = polygon.distanceToOcean + 1;
                    polygonQueue.add(polygonNeigbor);
                    polygonSet.add(polygonNeigbor);
                }
            }
        }
    }

    private void getCoastPolygons(Queue<Polygon> polygonQueue, Set<Polygon> polygonSet) {
        for(Polygon polygon: diagram.polygons)
        {
            if(polygon.water == WaterType.Land && polygon.hasOceanNeighbour())
            {
                polygon.distanceToOcean = 0;
                polygonQueue.add(polygon);
                polygonSet.add(polygon);
            }
        }
    }

    private void createLakes() {
        Queue<Polygon> polygonQueue = new ArrayDeque<>();
        Set<Polygon> polygonSet = new HashSet<>();
        Random random = new Random();
        ArrayList<Polygon> landPolygons = new ArrayList<>();
        for(Polygon polygon: diagram.polygons)
        {
            if(polygon.water == WaterType.Land) landPolygons.add(polygon);
        }

        int lakeLimit = landPolygons.size()/300 + settings.getLakeCountModificator();
        int lakeCounter = 0;
        for(Polygon polygon: landPolygons)
        {
            if(lakeCounter < lakeLimit && !polygon.hasOceanNeighbour() && (double)random.nextInt(landPolygons.size())/landPolygons.size() > 0.995)
            {
                polygon.water = WaterType.Lake;
                polygonQueue.add(polygon);
                polygonSet.add(polygon);
            }
        }
        lakeCounter = 0;
        int lakeSizeCounter = 0;
        while(polygonQueue.size()>0)
        {
            Polygon polygon = polygonQueue.poll();
            for(Polygon neighborPolygon: polygon.neighborPolygons)
            {
                if(lakeLimit * settings.getTotalLakeAreaLimitMultipler() < lakeCounter) break;
                if(!polygonSet.contains(neighborPolygon) && neighborPolygon.water == WaterType.Land && !neighborPolygon.hasOceanNeighbour())
                    if(neighborPolygon.getWaterToLandNeighbourRatio() < 1.0-lakeSizeCounter/(double)settings.getLakeSizeLimitModificator())
                    {
                        neighborPolygon.water = WaterType.Lake;
                        lakeCounter++;
                        lakeSizeCounter++;
                        polygonQueue.add(neighborPolygon);
                        polygonSet.add(neighborPolygon);
                    }
            }
        }
        System.out.print("");

    }

    private void createOcean() {
        Queue<Polygon> polygonQueue = new ArrayDeque<>();
        Set<Polygon> polygonSet = new HashSet<>();
        generateStartingPointsForOceanGenerator(polygonQueue, polygonSet);
        double oceanCounter = 0;
        while(polygonQueue.size()>0)
        {
            if(1.0 - (oceanCounter / (double)diagram.polygons.size())< settings.getLandmassMinPercentage())
                break;
            Polygon polygon = polygonQueue.poll();
            if(polygon.water == null || polygon.water == WaterType.UnspecifiedWater)
            {
                for(Polygon neighbour: polygon.neighborPolygons)
                {
                    if(!polygonSet.contains(neighbour))
                    {
                        polygonQueue.add(neighbour);
                        polygonSet.add(neighbour);
                    }
                }
                if(polygon.water == null)
                {
                    if(polygon.getWaterToLandNeighbourRatio() > getOceanPolygonRation(diagram.polygons.size())) {polygon.water = WaterType.Ocean;oceanCounter++;}
                    else polygon.water = WaterType.Land;
                }else {polygon.water = WaterType.Ocean; oceanCounter++;}
            }
        }
    }
    private double logOfBase(double num, int base) {
        return Math.log(num) / Math.log(base);
    }
    private double getOceanPolygonRation(int polygonCount) {
        return 0.3 - logOfBase(((double)polygonCount)/50,3) * settings.getWaterLevelConstant();
    }

    private void generateStartingPointsForOceanGenerator(Queue<Polygon> polygonQueue, Set<Polygon> polygonSet) {
        if(settings.isBottomWater() || settings.isLeftWater())
        {
            polygonQueue.add(diagram.pixelPoints[0][diagram.ySize-1].parentPolygon);
            polygonSet.add(diagram.pixelPoints[0][diagram.ySize-1].parentPolygon);
        }
        if(settings.isBottomWater() || settings.isRightWater())
        {
            polygonQueue.add(diagram.pixelPoints[diagram.xSize-1][diagram.ySize-1].parentPolygon);
            polygonSet.add(diagram.pixelPoints[diagram.xSize-1][diagram.ySize-1].parentPolygon);
            polygonQueue.add(diagram.pixelPoints[diagram.xSize-1][diagram.ySize-1].parentPolygon.neighborPolygons.get(0));
            polygonSet.add(diagram.pixelPoints[diagram.xSize-1][diagram.ySize-1].parentPolygon.neighborPolygons.get(0));
        }
        if(settings.isTopWater() || settings.isLeftWater())
        {
            polygonSet.add(diagram.pixelPoints[0][0].parentPolygon);
            polygonQueue.add(diagram.pixelPoints[0][0].parentPolygon);
        }
        if(settings.isTopWater() || settings.isRightWater())
        {
            polygonQueue.add(diagram.pixelPoints[diagram.xSize-1][0].parentPolygon);
            polygonSet.add(diagram.pixelPoints[diagram.xSize-1][0].parentPolygon);
        }

        Random random = new Random();
        if(settings.isBottomWater() && (random.nextInt(3) != 0 || diagram.polygons.size() > 10000))
        {
            polygonQueue.add(diagram.pixelPoints[(int)(diagram.xSize/2)][diagram.ySize-1].parentPolygon);
            polygonSet.add(diagram.pixelPoints[(int)(diagram.xSize/2)][diagram.ySize-1].parentPolygon);
        }
        if(settings.isTopWater() && random.nextInt(4) == 0)
        {
            polygonQueue.add(diagram.pixelPoints[(int)(diagram.xSize/2)][0].parentPolygon);
            polygonSet.add(diagram.pixelPoints[(int)(diagram.xSize/2)][0].parentPolygon);
        }
    }

    private void setMapBordersToWater() {
        for(int x = 0; x < diagram.xSize; x++)
        {
            if(settings.isTopWater())diagram.pixelPoints[x][0].parentPolygon.water = WaterType.UnspecifiedWater;
            if(settings.isBottomWater())diagram.pixelPoints[x][diagram.ySize-1].parentPolygon.water = WaterType.UnspecifiedWater;
        }
        for(int y = 0; y < diagram.ySize; y++)
        {
            if(settings.isLeftWater())diagram.pixelPoints[0][y].parentPolygon.water = WaterType.UnspecifiedWater;
            if(settings.isRightWater())diagram.pixelPoints[diagram.xSize-1][y].parentPolygon.water = WaterType.UnspecifiedWater;
        }
    }
}
