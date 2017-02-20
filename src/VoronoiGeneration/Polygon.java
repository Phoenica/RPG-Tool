package VoronoiGeneration;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Polygon<T extends Polygon> {
    public final Point<T> centerPoint;
    public ArrayList<Point> polygonPixels;
    public ArrayList<T> neighborPolygons;

    public Polygon(Integer x, Integer y)
    {
        centerPoint = new Point<>(x,y);
        this.neighborPolygons = new ArrayList<>();
        polygonPixels = new ArrayList<>();
    }

    public static <E extends Polygon>E getGenericInstance(Class<E> diagramClassType, Point centerPoint){
        E polygon = null;
        try {
            Class[] cArg = new Class[2];
            cArg[0] = Integer.class;
            cArg[1] = Integer.class;
             polygon = diagramClassType.getDeclaredConstructor(cArg).newInstance(centerPoint.getX(),centerPoint.getY());
        } catch (InstantiationException | IllegalAccessException |InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return polygon;
    }
}
