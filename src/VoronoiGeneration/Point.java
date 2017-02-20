package VoronoiGeneration;

public class Point<T extends Polygon> implements Comparable<Point>{
    private int x, y;
    public T parentPolygon;
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public Point(int x, int y, T polygon)
    {
        this.x = x;
        this.y = y;
        this.parentPolygon = polygon;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }

    @Override
    public int compareTo(Point o) {
        if(this.getX() > o.getX()) return 1;
        else if(this.getX() == o.getX())
        {
            if(this.getY() > o.getY()) return 1;
            else if(this.getY() == o.getY()) return 0;
            else return -1;
        }
        else return -1;
    }

    public double distanceTo(Point point)
    {
        return Math.sqrt(Math.pow((this.x - point.x),2)+Math.pow((this.y - point.y),2));
    }
}
