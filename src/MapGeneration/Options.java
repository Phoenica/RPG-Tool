package MapGeneration;

/**
 * Created by Phoenicia on 28.01.2017.
 */
public class Options {
    private int xSize;
    private int ySize;
    private int polygons;
    private int lakeCountModificator;
    private int totalLakeAreaLimitMultipler;
    private int lakeSizeLimitModificator;
    private double waterLevelConstant;
    private double landmassMinPercentage;
    private boolean topWater,bottomWater,leftWater,rightWater;
    public Options(int x, int y, int p)
    {
        xSize = x;
        ySize = y;
        polygons = p;
        setDefaultVaules();
    }

    private void setDefaultVaules()
    {
        waterLevelConstant = 0.021;
        topWater = bottomWater = leftWater = rightWater = true;
        lakeCountModificator = 0;
        totalLakeAreaLimitMultipler = 8;
        lakeSizeLimitModificator = 100;
        landmassMinPercentage = 0.30;
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

    public int getTotalLakeAreaLimitMultipler() {
        return totalLakeAreaLimitMultipler;
    }

    public void setTotalLakeAreaLimitMultipler(int totalLakeAreaLimitMultipler) {
        this.totalLakeAreaLimitMultipler = totalLakeAreaLimitMultipler;
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
}