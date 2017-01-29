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
                if(polygon.water == WaterType.Ocean) g.setColor(new Color(0,0,153));
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
                g.setColor(Color.red);
            //    g.drawOval(polygon.centerPoint.getX(),polygon.centerPoint.getY(),5,5);
            }
        }
    }
}
