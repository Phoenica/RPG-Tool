package WorldMap.GenerationSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Options {
    private int xSize;
    private int ySize;
    private int polygons;
    private int lakeCountModificator;
    private int totalLakeAreaLimitMultiplier;
    private int lakeSizeLimitModificator;
    private double waterLevelConstant;
    private double landmassMinPercentage;
    private boolean topWater,bottomWater,leftWater,rightWater;
    private MapPositionOnPlanet climate;
    private double moistureClimateModificator;
    private int riverCountModificator;
    private double cityModifier;
    private ArrayList<String> cityNames;

    public Options(int x, int y, int p)
    {
        xSize = x;
        ySize = y;
        polygons = p;
        setDefaultValues();
    }

    private void setDefaultValues()
    {
        waterLevelConstant = 0.018;
        topWater = bottomWater = leftWater = rightWater = true;
        lakeCountModificator = 0;
        riverCountModificator = 0;
        totalLakeAreaLimitMultiplier = 8;
        lakeSizeLimitModificator = 100;
        landmassMinPercentage = 0.30;
        climate = MapPositionOnPlanet.EquatorOnMiddle;
        moistureClimateModificator = 0.8;
        cityModifier = 0.4;
        setDefaultCityNames();
    }

    private void setDefaultCityNames() {
        cityNames =new ArrayList<>( Arrays.asList(new String[]{"Aerilon", "Albion","Aquarin", "Avalon","Aramoor","Azmar","Begger’s Hole","Black Hollow","Blue Field","Briar Glen","Brickelwhyte",
        "Broken Shield","Boatwright","Bullmar","Carran","City of Fire","Coalfell","Cullfield","Darkwell","Deathfall","Doonatel","Dry Gulch","Easthaven","Ecrin",
        "Erast", "Far Water", "Firebend", "Fool’s March", "Frostford", "Goldcrest","Goldenleaf","Greenflower","Garen’s Well","Haran","Hillfar","Hogsfeet",
                "Hollyhead","Hull","Hwen","Icemeet","Irragin","Jongvale","Leeside","Lullin","Millstone","Moonbright","Mountmend","Nearon","New Cresthill",
         "Northpass", "Nuxvar", "Oakheart", "Old Ashton", "Orrinshire", "Ozryn", "Pavv", "Pran", "Quan Ma", "Queenstown", "Ramshorn", "Rivermouth", "Seameet",
                "Silverkeep", "South Warren", "Snowmelt", "Swordbreak", "Tarrin", "Trudid", "Ula’ree", "Veritas", "Wavemeet", "Whiteridge", "Willowdale",
                "Windrip", "Wellspring", "Westwend", "Wolfden", "Yellowseed","Zumka"}));
        Collections.shuffle(cityNames);
    }


    public int getXSize()
    {
        return xSize;
    }
    public int getYSize()
    {
        return ySize;
    }
    public int getPolygons()
    {
        return polygons;
    }

    public double getWaterLevelConstant() {
        return waterLevelConstant;
    }

    public void setWaterLevelConstant(double waterLevelConstant) {
        this.waterLevelConstant = waterLevelConstant;
    }

    public boolean isTopWater() {
        return topWater;
    }

    public void setTopWater(boolean topWater) {
        this.topWater = topWater;
    }

    public boolean isBottomWater() {
        return bottomWater;
    }

    public void setBottomWater(boolean bottomWater) {
        this.bottomWater = bottomWater;
    }

    public boolean isLeftWater() {
        return leftWater;
    }

    public void setLeftWater(boolean leftWater) {
        this.leftWater = leftWater;
    }

    public void setRightWater(boolean rightWater) {
        this.rightWater = rightWater;
    }
    public boolean isRightWater()
    {
        return rightWater;
    }

    public int getLakeCountModificator() {
        return lakeCountModificator;
    }

    public void setLakeCountModificator(int lakeCountModificator) {
        this.lakeCountModificator = lakeCountModificator;
    }

    public int getTotalLakeAreaLimitMultiplier() {
        return totalLakeAreaLimitMultiplier;
    }

    public void setTotalLakeAreaLimitMultiplier(int totalLakeAreaLimitMultiplier) {
        this.totalLakeAreaLimitMultiplier = totalLakeAreaLimitMultiplier;
    }

    public int getLakeSizeLimitModificator() {
        return lakeSizeLimitModificator;
    }

    public void setLakeSizeLimitModificator(int lakeSizeLimitModificator) {
        this.lakeSizeLimitModificator = lakeSizeLimitModificator;
    }

    public double getLandmassMinPercentage() {
        return landmassMinPercentage;
    }

    public void setLandmassMinPercentage(double landmassMinPercentage) {
        this.landmassMinPercentage = landmassMinPercentage;
    }

    public MapPositionOnPlanet getClimate() {
        return climate;
    }

    public void setClimate(MapPositionOnPlanet climate) {
        this.climate = climate;
    }

    public double getMoistureClimateModificator() {
        return moistureClimateModificator;
    }

    public void setMoistureClimateModificator(double moistureClimateModificator) {
        this.moistureClimateModificator = moistureClimateModificator;
    }

    public int getRiverCountModificator() {
        return riverCountModificator;
    }

    public void setRiverCountModificator(int riverCountModificator) {
        this.riverCountModificator = riverCountModificator;
    }

    public double getCityModifier() {
        return cityModifier;
    }

    public void setCityModifier(double cityModifier) {
        this.cityModifier = cityModifier;
    }

    public ArrayList<String> getCityNames() {
        return cityNames;
    }

    public void setCityNames(ArrayList<String> cityNames) {
        this.cityNames = cityNames;
    }
}
