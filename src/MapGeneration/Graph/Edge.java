package MapGeneration.Graph;

import java.util.ArrayList;

public class Edge implements GraphElement {
    public Edge()
    {
        parentPolygons = new ArrayList<>();
        touchingEdges = new ArrayList<>();
        parentVertexes = new ArrayList<>();
    }

    public ArrayList<Polygon> parentPolygons;
    public ArrayList<Edge> touchingEdges;
    public ArrayList<Vertex> parentVertexes;
}
