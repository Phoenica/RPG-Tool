package worldmap;

import worldmap.dataexport.MapPrinter;
import worldmap.generationsettings.Options;
import worldmap.maptileproperties.*;
import voronoigeneration.VoronoiDiagram;
import java.util.*;
import java.util.stream.Collectors;


public class Map {
    private VoronoiDiagram<MapTile> diagram;
    private MapPrinter generatedMap;
    private Options settings;

    public MapPrinter getMap(VoronoiDiagram<MapTile> diagram)
    {
        this.diagram = diagram;
        generateMap();
        return generatedMap;
    }
    public MapPrinter getMap()
    {
        generateMap();
        return generatedMap;
    }
    public Map(Options settings)
    {
        this.settings = settings;
    }

    private void generateDiagram()
    {
        diagram = new VoronoiDiagram<>(settings.getXSize(),settings.getYSize(),MapTile.class);
        diagram.generate(settings.getPolygons());
    }
    private void generateMap()
    {
        if(diagram == null)
            generateDiagram();
        generateBodiesOfWater();
        generateElevations();
        generateClimate();
        generateRivers();
        generateBiomes();
        generateCities();
        generatedMap = new MapPrinter(diagram);
    }

    private void generateCities() {
        int cityCounter = 0;
        int maxCityNumber;
        List<MapTile> mapTileList = diagram.polygons.stream().filter(mapTile -> mapTile.water == WaterType.Land).collect(Collectors.toList());
        maxCityNumber = (int)(settings.getCityModifier()*(mapTileList.size()/100));
        Collections.shuffle(mapTileList);
        Random random = new Random();
        for(MapTile mapTile : mapTileList)
        {
            if(mapTile.hasCityNeighbour()) continue;
            int chanceEstimation = estimateCityChance(mapTile);
            if(chanceEstimation > random.nextInt(100))
            {
                mapTile.city = new City(settings.getCityNames().get(cityCounter));
                cityCounter++;
            }
            if(cityCounter >= maxCityNumber || cityCounter >= settings.getCityNames().size()) break;
        }
    }

    private int estimateCityChance(MapTile mapTile)
    {
        int chanceEstimation = 0;

        if(mapTile.temperature == Temperature.Frigid) chanceEstimation -=30;
        if(mapTile.temperature == Temperature.Average) chanceEstimation +=10;
        if(mapTile.temperature == Temperature.Hot) chanceEstimation +=15;

        if(mapTile.moisture == Moisture.SuperWet) chanceEstimation +=10;
        if(mapTile.moisture == Moisture.Wet) chanceEstimation +=10;
        if(mapTile.moisture == Moisture.Normal) chanceEstimation +=5;
        if(mapTile.moisture == Moisture.Dry) chanceEstimation -=5;
        if(mapTile.moisture == Moisture.SuperDry) chanceEstimation -=20;

        if(mapTile.elevation == Elevation.MountainPeaks) chanceEstimation -= 30;

        if(mapTile.hasOceanNeighbour()) chanceEstimation += 40;
            else if(mapTile.hasLakeNeighbour()) chanceEstimation += 20;

        if(mapTile.river) chanceEstimation += 30;
            else if(mapTile.hasRiverNeighbour()) chanceEstimation +=10;

        return chanceEstimation;
    }

    private void generateBiomes() {
        diagram.polygons.forEach(MapTile::setBiome);
    }

    private void generateClimate() {
        for(MapTile mapTile : diagram.polygons)
        {
            calculateTemperature(mapTile);
            if(mapTile.water != WaterType.Land) mapTile.moisture = Moisture.LiterallyWater;
            else if(mapTile.getDistanceToWater()* mapTile.temperature.ordinal() < 2.0*Math.sqrt((double)diagram.polygons.size())/(double)80*(double)settings.getMoistureClimateModificator()) mapTile.moisture = Moisture.SuperWet;
            else if(mapTile.getDistanceToWater()* mapTile.temperature.ordinal() < 4.0*Math.sqrt((double)diagram.polygons.size())/(double)80*(double)settings.getMoistureClimateModificator()) mapTile.moisture = Moisture.Wet;
            else if(mapTile.getDistanceToWater()* mapTile.temperature.ordinal() < 10.0*Math.sqrt((double)diagram.polygons.size())/(double)80*(double)settings.getMoistureClimateModificator()) mapTile.moisture = Moisture.Normal;
            else if(mapTile.getDistanceToWater()* mapTile.temperature.ordinal() < 13.0*Math.sqrt((double)diagram.polygons.size())/(double)80*(double)settings.getMoistureClimateModificator()) mapTile.moisture = Moisture.Dry;
            else  mapTile.moisture = Moisture.SuperDry;
        }
    }

    private void calculateTemperature(MapTile mapTile) {
        int tempTemperature = calculateClimateTemperature(mapTile).ordinal();
        if(tempTemperature != 0)
            if(mapTile.elevation == Elevation.MountainPeaks)  tempTemperature = 0;
            else
            {
                if(mapTile.elevation == Elevation.High) tempTemperature = tempTemperature - 2;
                    else if(mapTile.elevation == Elevation.Medium) tempTemperature--;
                if(tempTemperature <= 0) tempTemperature = 1;
            }
            mapTile.temperature = Temperature.values()[tempTemperature];
    }

    private Temperature calculateClimateTemperature(MapTile mapTile) {
        int mapMiddle = settings.getYSize()/2;
        int map1Percent = settings.getYSize()/100;
        Random random = new Random();
        double mod = 1.0-0.1*((double)(random.nextInt(10)-5));
        switch(settings.getClimate())
        {
            case EquatorOnMiddle:
                if(((mapMiddle - map1Percent*5*mod <= mapTile.centerPoint.getY() && mapMiddle >= mapTile.centerPoint.getY()) || (mapMiddle + map1Percent*5*mod >= mapTile.centerPoint.getY() && mapMiddle <= mapTile.centerPoint.getY()))) return Temperature.Scorching;
                if(((mapMiddle - map1Percent*18*mod < mapTile.centerPoint.getY() && mapMiddle > mapTile.centerPoint.getY()) || (mapMiddle + map1Percent*18*mod > mapTile.centerPoint.getY() && mapMiddle < mapTile.centerPoint.getY()))) return Temperature.Hot;
                if(((mapMiddle - map1Percent*35 < mapTile.centerPoint.getY() && mapMiddle > mapTile.centerPoint.getY()) || (mapMiddle + map1Percent*35 > mapTile.centerPoint.getY() && mapMiddle < mapTile.centerPoint.getY()))) return Temperature.Average;
                if(((mapMiddle - map1Percent*45 < mapTile.centerPoint.getY() && mapMiddle > mapTile.centerPoint.getY()) || (mapMiddle + map1Percent*45 > mapTile.centerPoint.getY() && mapMiddle < mapTile.centerPoint.getY()))) return Temperature.Cold;
                return Temperature.Frigid;
            case ColdNorth:
                if(settings.getYSize() - map1Percent*12*mod<= mapTile.centerPoint.getY()) return Temperature.Scorching;
                if(settings.getYSize() - map1Percent*36*mod <= mapTile.centerPoint.getY()) return Temperature.Hot;
                if(settings.getYSize() - map1Percent*70 <= mapTile.centerPoint.getY()) return Temperature.Average;
                if(settings.getYSize() - map1Percent*88 <= mapTile.centerPoint.getY()) return Temperature.Cold;
                return Temperature.Frigid;
            case ColdSouth:
                if(settings.getYSize() - map1Percent*12<= mapTile.centerPoint.getY()) return Temperature.Frigid;
                if(settings.getYSize() - map1Percent*28*mod <= mapTile.centerPoint.getY()) return Temperature.Cold;
                if(settings.getYSize() - map1Percent*64*mod <= mapTile.centerPoint.getY()) return Temperature.Average;
                if(settings.getYSize() - map1Percent*88*mod <= mapTile.centerPoint.getY()) return Temperature.Hot;
                return Temperature.Scorching;
            case UniformTemperature:
                return Temperature.Average;
            default:

        }
        return null;
    }

    private void generateRivers() {
        Queue<MapTile> mapTileQueue = new ArrayDeque<>();
        Set<MapTile> mapTileSet = new HashSet<>();

        int landPolygonCounter = 0;
        for(MapTile mapTile : diagram.polygons)
        {
            if(mapTile.water == WaterType.Land) landPolygonCounter++;
            mapTile.setPotentialRiverDirection();
        }

        createRiverStartingPositions(mapTileQueue, mapTileSet, landPolygonCounter);
        while(!mapTileQueue.isEmpty())
        {
            MapTile tempMapTile = mapTileQueue.poll();
            tempMapTile.river = true;
            if(tempMapTile.moisture.ordinal() < Moisture.SuperWet.ordinal()) tempMapTile.moisture = Moisture.values()[(tempMapTile.moisture.ordinal()+1)];
            if(!mapTileSet.contains(tempMapTile.riverDirection))
            {
                mapTileSet.add(tempMapTile.riverDirection);
                mapTileQueue.add(tempMapTile.riverDirection);
            }
        }
    }

    private void createRiverStartingPositions(Queue<MapTile> mapTileQueue, Set<MapTile> mapTileSet, int landPolygonCounter) {
        int riverLimit = landPolygonCounter/100 + settings.getRiverCountModificator();
        Random random = new Random();
        int riverCounter = 0;
        ArrayList<MapTile> mapTiles = new ArrayList(diagram.polygons);
        Collections.shuffle(mapTiles);
        for(MapTile mapTile : mapTiles)
        {
            if(mapTile.water == WaterType.Land
                    && riverCounter < riverLimit
                    && (mapTile.temperature != Temperature.Frigid
                    || mapTile.elevation == Elevation.MountainPeaks)
                    && mapTile.elevation.ordinal() >=2
                    && mapTile.moisture.ordinal() >= 2
                    && (double)random.nextInt(mapTile.elevation.ordinal() * landPolygonCounter)/(landPolygonCounter) > 0.995)
            {
                riverCounter++;
                mapTile.river = true;
                mapTileQueue.add(mapTile);
                mapTileSet.add(mapTile);
            }
        }
    }

    private void generateBodiesOfWater() {
        setMapBordersToWater();
        createOcean();
        createLakes();
        setToLakeDistance();
    }

    private void setToLakeDistance() {
        Queue<MapTile> mapTileQueue = new ArrayDeque<>();
        Set<MapTile> mapTileSet = new HashSet<>();

        findLakesAndSetTheirDistance(mapTileQueue, mapTileSet);
        while(!mapTileQueue.isEmpty())
        {
            MapTile mapTile = mapTileQueue.poll();
            mapTile.neighborPolygons.stream().filter(neighbour -> !mapTileSet.contains(neighbour) && neighbour.water == WaterType.Land).forEach(neighbour -> {
                neighbour.distanceToLake = mapTile.distanceToLake + 1;
                mapTileSet.add(neighbour);
                mapTileQueue.add(neighbour);
            });
        }
    }

    private void findLakesAndSetTheirDistance(Queue<MapTile> mapTileQueue, Set<MapTile> mapTileSet) {
        diagram.polygons.stream().filter(mapTile -> mapTile.water == WaterType.Lake).forEach(mapTile -> {
            mapTileQueue.add(mapTile);
            mapTileSet.add(mapTile);
            mapTile.distanceToLake = 0;
        });
    }

    private void generateElevations() {
        Queue<MapTile> mapTileQueue = new ArrayDeque<>();
        Set<MapTile> mapTileSet = new HashSet<>();
        getCoastPolygons(mapTileQueue, mapTileSet);
        calculatePolygonsDistanceToOcean(mapTileQueue, mapTileSet);
        setElevations(mapTileSet);
    }

    private void setElevations(Set<MapTile> mapTileSet) {
        int maxDistance = 0;
        int averageDistanceToOcean = 0;
        for(MapTile mapTile : mapTileSet)
        {
            if(mapTile.distanceToOcean > maxDistance) maxDistance = mapTile.distanceToOcean;
            averageDistanceToOcean += mapTile.distanceToOcean;
        }
        averageDistanceToOcean = averageDistanceToOcean / mapTileSet.size();
        for(MapTile mapTile : mapTileSet)
        {
            if(mapTile.distanceToOcean <= 2) mapTile.elevation = Elevation.Low;
            else if(mapTile.distanceToOcean < averageDistanceToOcean+5) mapTile.elevation = Elevation.Medium;
            else if(mapTile.elevation!=Elevation.MountainPeaks && mapTile.distanceToOcean < (maxDistance+8 + averageDistanceToOcean)/2) mapTile.elevation = Elevation.High;
            else mapTile.elevation = Elevation.MountainPeaks;

        }
    }

    private void calculatePolygonsDistanceToOcean(Queue<MapTile> mapTileQueue, Set<MapTile> mapTileSet) {
        while(!mapTileQueue.isEmpty())
        {
            MapTile mapTile = mapTileQueue.poll();
            mapTile.neighborPolygons.stream().filter(mapTileNeighbour -> !mapTileSet.contains(mapTileNeighbour))
                    .forEach(mapTileNeighbour -> {

                mapTileNeighbour.distanceToOcean = mapTile.distanceToOcean + 1;
                mapTileQueue.add(mapTileNeighbour);
                mapTileSet.add(mapTileNeighbour);
            });
        }
    }

    private void getCoastPolygons(Queue<MapTile> mapTileQueue, Set<MapTile> mapTileSet) {
        diagram.polygons.stream().filter(mapTile -> mapTile.water == WaterType.Land && mapTile.hasOceanNeighbour())
                .forEach(mapTile -> {
            mapTile.distanceToOcean = 0;
            mapTileQueue.add(mapTile);
            mapTileSet.add(mapTile);
        });
    }

    private void createLakes() {
        Queue<MapTile> mapTileQueue = new ArrayDeque<>();
        Set<MapTile> mapTileSet = new HashSet<>();
        ArrayList<MapTile> landMapTiles = diagram.polygons.stream().filter(mapTile -> mapTile.water == WaterType.Land)
                .collect(Collectors.toCollection(ArrayList::new));

        int lakeLimit = landMapTiles.size()/300 + settings.getLakeCountModificator();

        createLakeStartingPoints(mapTileQueue, mapTileSet, landMapTiles, lakeLimit);
        expandLakeSizes(mapTileQueue, mapTileSet, lakeLimit);
    }

    private void expandLakeSizes(Queue<MapTile> mapTileQueue, Set<MapTile> mapTileSet, int lakeLimit) {
        int lakeCounter = 0;
        int lakeSizeCounter = 0;
        while(mapTileQueue.size()>0)
        {
            MapTile mapTile = mapTileQueue.poll();
            for(MapTile neighborMapTile : mapTile.neighborPolygons)
            {
                if(lakeLimit * settings.getTotalLakeAreaLimitMultiplier() < lakeCounter) break;
                if(!mapTileSet.contains(neighborMapTile) && neighborMapTile.water == WaterType.Land && !neighborMapTile.hasOceanNeighbour())
                    if(neighborMapTile.getWaterToLandNeighbourRatio() < 1.0-lakeSizeCounter/(double)settings.getLakeSizeLimitModificator())
                    {
                        neighborMapTile.water = WaterType.Lake;
                        lakeCounter++;
                        lakeSizeCounter++;
                        mapTileQueue.add(neighborMapTile);
                        mapTileSet.add(neighborMapTile);
                    }
            }
        }
    }

    private void createLakeStartingPoints(Queue<MapTile> mapTileQueue, Set<MapTile> mapTileSet, ArrayList<MapTile> landMapTiles, int lakeLimit) {
        int lakeCounter = 0;
        Random random = new Random();

        landMapTiles.stream().filter(mapTile -> lakeCounter < lakeLimit && !mapTile.hasOceanNeighbour()
                && (double) random.nextInt(landMapTiles.size()) / landMapTiles.size() > 0.995).forEach(mapTile -> {
            mapTile.water = WaterType.Lake;
            mapTileQueue.add(mapTile);
            mapTileSet.add(mapTile);
        });
    }

    private void createOcean() {
        Queue<MapTile> mapTileQueue = new ArrayDeque<>();
        Set<MapTile> mapTileSet = new HashSet<>();
        generateStartingPointsForOceanGenerator(mapTileQueue, mapTileSet);
        double oceanCounter = 0;
        while(mapTileQueue.size()>0)
        {
            if(1.0 - (oceanCounter / (double)diagram.polygons.size())< settings.getLandmassMinPercentage())
                break;
            MapTile mapTile = mapTileQueue.poll();
            if(mapTile.water == null || mapTile.water == WaterType.UnspecifiedWater)
            {
                mapTile.neighborPolygons.stream().filter(neighbour -> !mapTileSet.contains(neighbour)).forEach(neighbour -> {
                    mapTileQueue.add(neighbour);
                    mapTileSet.add(neighbour);
                });
                if(mapTile.water == null)
                {
                    if(mapTile.getWaterToLandNeighbourRatio() > getOceanPolygonRation(diagram.polygons.size())) {
                        mapTile.water = WaterType.Ocean;oceanCounter++;}
                    else mapTile.water = WaterType.Land;
                }else {
                    mapTile.water = WaterType.Ocean; oceanCounter++;}
            }
        }
    }
    private double logOfBase(double num, int base) {
        return Math.log(num) / Math.log(base);
    }
    private double getOceanPolygonRation(int polygonCount) {
        return 0.3 - logOfBase(((double)polygonCount)/50,3) * settings.getWaterLevelConstant();
    }

    private void generateStartingPointsForOceanGenerator(Queue<MapTile> mapTileQueue, Set<MapTile> mapTileSet) {
        if(settings.isBottomWater() || settings.isLeftWater())
        {
            mapTileQueue.add(diagram.pixelPoints[0][diagram.ySize-1].parentPolygon);
            mapTileSet.add(diagram.pixelPoints[0][diagram.ySize-1].parentPolygon);
        }
        if(settings.isBottomWater() || settings.isRightWater())
        {
            mapTileQueue.add(diagram.pixelPoints[diagram.xSize-1][diagram.ySize-1].parentPolygon);
            mapTileSet.add(diagram.pixelPoints[diagram.xSize-1][diagram.ySize-1].parentPolygon);
            mapTileQueue.add(diagram.pixelPoints[diagram.xSize-1][diagram.ySize-1].parentPolygon.neighborPolygons.get(0));
            mapTileSet.add(diagram.pixelPoints[diagram.xSize-1][diagram.ySize-1].parentPolygon.neighborPolygons.get(0));
        }
        if(settings.isTopWater() || settings.isLeftWater())
        {
            mapTileSet.add(diagram.pixelPoints[0][0].parentPolygon);
            mapTileQueue.add(diagram.pixelPoints[0][0].parentPolygon);
        }
        if(settings.isTopWater() || settings.isRightWater())
        {
            mapTileQueue.add(diagram.pixelPoints[diagram.xSize-1][0].parentPolygon);
            mapTileSet.add(diagram.pixelPoints[diagram.xSize-1][0].parentPolygon);
        }

        Random random = new Random();
        if(settings.isBottomWater() && (random.nextInt(3) != 0 || diagram.polygons.size() > 10000))
        {
            mapTileQueue.add(diagram.pixelPoints[diagram.xSize/2][diagram.ySize-1].parentPolygon);
            mapTileSet.add(diagram.pixelPoints[diagram.xSize/2][diagram.ySize-1].parentPolygon);
        }
        if(settings.isTopWater() && random.nextInt(4) == 0)
        {
            mapTileQueue.add(diagram.pixelPoints[diagram.xSize/2][0].parentPolygon);
            mapTileSet.add(diagram.pixelPoints[diagram.xSize/2][0].parentPolygon);
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
