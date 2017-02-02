package MapGeneration;

import MapGeneration.GenerationSettings.MapPositionOnPlanet;
import MapGeneration.GenerationSettings.Options;
import MapGeneration.Graph.Polygon;
import MapGeneration.Graph.PolygonProperties.Elevation;
import MapGeneration.Graph.PolygonProperties.Moisture;
import MapGeneration.Graph.PolygonProperties.Temperature;
import MapGeneration.Graph.PolygonProperties.WaterType;
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
        generateBodiesOfWater();
        generateElevations();
        generateClimate();
        generateRivers();
        generatedMap = new Screen(diagram);
    }

    private void generateClimate() {


        for(Polygon polygon: diagram.polygons)
        {
            calculateTemperature(polygon);
            if(polygon.getDistanceToWater() == 0) polygon.moisture = Moisture.LiterallyWater;
         //   else if(polygon.getDistanceToWater() == settings.)
        }
    }

    private void calculateTemperature(Polygon polygon) {
        int tempTemperature = calculateClimateTemperature(polygon).ordinal();
        if(tempTemperature == 0);
            else
        if(polygon.elevation == Elevation.MountainPeaks)  tempTemperature = 0;
        else
        {

             if(polygon.elevation == Elevation.Hight) tempTemperature = tempTemperature - 2;
                else if(polygon.elevation == Elevation.Medium) tempTemperature--;

            if(tempTemperature <= 0) tempTemperature = 1;
        }
     //   if(settings.getClimate() == MapPositionOnPlanet.EquatorOnMiddle)
            polygon.temperature = Temperature.values()[tempTemperature];
    }

    private Temperature calculateClimateTemperature(Polygon polygon) {
        int mapMiddle = settings.getYSize()/2;
        int map1Percent = settings.getYSize()/100;
        switch(settings.getClimate())
        {
            case EquatorOnMiddle:
                if(((mapMiddle - map1Percent*5 <= polygon.centerPoint.getY() && mapMiddle >= polygon.centerPoint.getY()) || (mapMiddle + map1Percent*5 >= polygon.centerPoint.getY() && mapMiddle <= polygon.centerPoint.getY()))) return Temperature.Scorching;
                if(((mapMiddle - map1Percent*18 < polygon.centerPoint.getY() && mapMiddle > polygon.centerPoint.getY()) || (mapMiddle + map1Percent*18 > polygon.centerPoint.getY() && mapMiddle < polygon.centerPoint.getY()))) return Temperature.Hot;
                if(((mapMiddle - map1Percent*35 < polygon.centerPoint.getY() && mapMiddle > polygon.centerPoint.getY()) || (mapMiddle + map1Percent*35 > polygon.centerPoint.getY() && mapMiddle < polygon.centerPoint.getY()))) return Temperature.Average;
                if(((mapMiddle - map1Percent*45 < polygon.centerPoint.getY() && mapMiddle > polygon.centerPoint.getY()) || (mapMiddle + map1Percent*45 > polygon.centerPoint.getY() && mapMiddle < polygon.centerPoint.getY()))) return Temperature.Cold;
                return Temperature.Frigid;
            case ColdNorth:
                if(settings.getYSize() - map1Percent*12<= polygon.centerPoint.getY()) return Temperature.Scorching;
                if(settings.getYSize() - map1Percent*36 <= polygon.centerPoint.getY()) return Temperature.Hot;
                if(settings.getYSize() - map1Percent*70 <= polygon.centerPoint.getY()) return Temperature.Average;
                if(settings.getYSize() - map1Percent*88 <= polygon.centerPoint.getY()) return Temperature.Cold;
                return Temperature.Frigid;
            case ColdSouth:
                if(settings.getYSize() - map1Percent*12<= polygon.centerPoint.getY()) return Temperature.Frigid;
                if(settings.getYSize() - map1Percent*28 <= polygon.centerPoint.getY()) return Temperature.Hot;
                if(settings.getYSize() - map1Percent*64 <= polygon.centerPoint.getY()) return Temperature.Average;
                if(settings.getYSize() - map1Percent*88 <= polygon.centerPoint.getY()) return Temperature.Cold;
                return Temperature.Scorching;
            case UniformTemperature:
                return Temperature.Average;
            default:

        }
        return null;
    }

    private boolean isAWhitinBDistanceOfC(int a, int b,int c)
    {
        return (c - b < a || c + b >  a);
    }

    private void generateRivers() {
        for(Polygon polygon: diagram.polygons)
        {
            if(polygon.water != WaterType.Ocean) polygon.setPotentialRiverDirection();
        }
    }

    private void generateBodiesOfWater() {
        setMapBordersToWater();
        createOcean();
        createLakes();
        setToLakeDistance();
    }

    private void setToLakeDistance() {
        Queue<Polygon> polygonQueue = new ArrayDeque<>();
        Set<Polygon> polygonSet = new HashSet<>();
        for(Polygon polygon: diagram.polygons){
            if(polygon.water == WaterType.Lake)
            {
                polygonQueue.add(polygon);
                polygonSet.add(polygon);
                polygon.distanceToLake = 0;
            }
        }
        while(!polygonQueue.isEmpty())
        {
            Polygon polygon = polygonQueue.poll();
            for(Polygon neigbour: polygon.neighborPolygons)
            {
                if(!polygonSet.contains(neigbour))
                {
                    if(neigbour.water == WaterType.Land)
                    {
                        neigbour.distanceToLake = polygon.distanceToLake;
                        polygonSet.add(neigbour);
                        polygonQueue.add(neigbour);
                    }
                }
            }
        }
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
            if(polygon.distanceToOcean <= 2) polygon.elevation = Elevation.Low;
            else if(polygon.distanceToOcean < avargeDistanceToOcean+5) polygon.elevation = Elevation.Medium;
            else if(polygon.elevation!=Elevation.MountainPeaks && polygon.distanceToOcean < (maxDistance+6 + avargeDistanceToOcean)/2) polygon.elevation = Elevation.Hight;
            else polygon.elevation = Elevation.MountainPeaks;

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
