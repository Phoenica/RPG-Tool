package MapGeneration;

import MapGeneration.Graph.*;
import MapGeneration.Graph.Point;

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
                if(polygon.water == WaterType.Ocean) g.setColor(Color.blue);
                else if(polygon.water == WaterType.Lake) g.setColor(Color.cyan);
                else g.setColor(Color.GREEN);
                for(Point pixel:polygon.polygonPixels)
                {
                    g.drawRect(pixel.getX(),pixel.getY(),1,1);
                }
                g.setColor(Color.red);
            //    g.drawOval(polygon.centerPoint.getX(),polygon.centerPoint.getY(),5,5);
            }
        }
    }
}
