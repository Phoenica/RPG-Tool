package MapGeneration.DataExport;

import MapGeneration.Graph.Point;
import MapGeneration.Graph.PolygonProperties.Biomes.Biome;
import MapGeneration.Graph.PolygonProperties.Biomes.Glacier;
import MapGeneration.Graph.PolygonProperties.Moisture;
import MapGeneration.Graph.PolygonProperties.Temperature;
import MapGeneration.Graph.PolygonProperties.WaterType;
import MapGeneration.VoronoiDiagram;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;


public class MapPrinter extends JPanel {
    private VoronoiDiagram diagram;
    public MapPrinter(VoronoiDiagram diagram)
    {
        this.diagram = diagram;
        repaint();
    }
    public void paint(Graphics g)
    {
        if(diagram != null)
        {
            for(MapGeneration.Graph.Polygon polygon: diagram.polygons)
            {
                g.setColor(polygon.biome.getBiomeColor());

                for(Point pixel:polygon.polygonPixels)
                {
                    g.drawRect(pixel.getX(),pixel.getY(),1,1);
                }
            }
            for(MapGeneration.Graph.Polygon polygon: diagram.polygons)
            {
                g.setColor(Color.BLUE);
                if(polygon.river == true && !(polygon.biome instanceof Glacier))
                {
                    g.drawLine(polygon.centerPoint.getX(),polygon.centerPoint.getY(),polygon.riverDirection.centerPoint.getX(),polygon.riverDirection.centerPoint.getY());
                }
                g.setColor(Color.BLACK);
                if(polygon.city != null)
                {
                    Ellipse2D.Double circle = new Ellipse2D.Double(polygon.centerPoint.getX(), polygon.centerPoint.getY(), 6, 6);
                    Graphics2D g2d = (Graphics2D)g;
                    g2d.fill(circle);
                    g2d.drawString(polygon.city.toString(),polygon.centerPoint.getX(), polygon.centerPoint.getY());
                }
            }

        }
    }
}
