package MapGeneration.Graph;

import java.util.ArrayList;

public class Vertex implements GraphElement {
    public final Point centerPoint;
    public Vertex(int x, int y)
    {
        centerPoint = new Point(x,y);
        parentPolygons = new ArrayList<>();
        touchingEdges = new ArrayList<>();
        neighborVertexes = new ArrayList<>();
    }

    public ArrayList<Polygon> parentPolygons;
    public ArrayList<Edge> touchingEdges;
    public ArrayList<Vertex> neighborVertexes;
}
