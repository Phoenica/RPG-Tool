package MapGeneration;

import MapGeneration.Graph.Point;
import MapGeneration.Graph.PolygonProperties.Moisture;
import MapGeneration.Graph.PolygonProperties.Temperature;
import MapGeneration.Graph.PolygonProperties.WaterType;

import javax.swing.*;
import java.awt.*;


public class Screen extends JPanel {
    private VoronoiDiagram diagram;
    public Screen(VoronoiDiagram diagram)
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
                if(polygon.moisture == Moisture.LiterallyWater) g.setColor(Color.BLUE);
                if(polygon.moisture == Moisture.SuperWet) g.setColor(Color.GREEN);
                if(polygon.moisture == Moisture.Wet) g.setColor(Color.CYAN);
                if(polygon.moisture == Moisture.Normal) g.setColor(Color.YELLOW);
                if(polygon.moisture == Moisture.Dry) g.setColor(Color.ORANGE);
                if(polygon.moisture == Moisture.SuperDry) g.setColor(Color.RED);

                /*if(polygon.temperature == Temperature.Frigid) g.setColor(Color.WHITE);
                else
                {
                    if(polygon.water != WaterType.Land) g.setColor(Color.BLUE);
                    else
                    {
                        if(polygon.temperature == Temperature.Cold) g.setColor(Color.CYAN);
                        if(polygon.temperature == Temperature.Average) g.setColor(Color.GREEN);
                        if(polygon.temperature == Temperature.Hot) g.setColor(Color.ORANGE);
                        if(polygon.temperature == Temperature.Scorching) g.setColor(Color.RED);
                    }
                }*/

                /*if(polygon.water == WaterType.Water) g.setColor(new Color(0,0,153));
                else if(polygon.water == WaterType.Lake) g.setColor(Color.blue);
                else if(polygon.elevation == 1)
                {
                    g.setColor(Color.GREEN);
                }
                else if(polygon.elevation == 2)
                {
                    g.setColor(Color.yellow);
                }
                else if(polygon.elevation == 3)
                {
                    g.setColor(Color.GRAY);
                }
                else if(polygon.elevation == 4)
                {
                    g.setColor(Color.red);
                }
                for(Point pixel:polygon.polygonPixels)
                {
                    g.drawRect(pixel.getX(),pixel.getY(),1,1);
                }
                g.setColor(Color.red);*/

                for(Point pixel:polygon.polygonPixels)
                {
                    g.drawRect(pixel.getX(),pixel.getY(),1,1);
                }
                g.setColor(Color.red);
            }
            for(MapGeneration.Graph.Polygon polygon: diagram.polygons)
            {
                g.setColor(Color.BLUE);
                if(polygon.river == true)
                {
                    g.drawLine(polygon.centerPoint.getX(),polygon.centerPoint.getY(),polygon.riverDirection.centerPoint.getX(),polygon.riverDirection.centerPoint.getY());
                }
            }
        }
    }
}
