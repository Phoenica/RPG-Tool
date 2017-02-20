package WorldMap.DataExport;

import VoronoiGeneration.Point;
import WorldMap.MapTileProperties.Biomes.Glacier;
import WorldMap.MapTile;
import VoronoiGeneration.VoronoiDiagram;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;


public class MapPrinter extends JPanel {
    private VoronoiDiagram<MapTile> diagram;
    public MapPrinter(VoronoiDiagram<MapTile> diagram)
    {
        this.diagram = diagram;
        repaint();
    }
    public void paint(Graphics g)
    {
        if(diagram != null)
        {
            for(MapTile mapTile : diagram.polygons)
            {
                g.setColor(mapTile.biome.getBiomeColor());

                for(Point pixel: mapTile.polygonPixels)
                {
                    g.drawRect(pixel.getX(),pixel.getY(),1,1);
                }
            }
            for(MapTile mapTile : diagram.polygons)
            {
                g.setColor(Color.BLUE);
                if(mapTile.river && !(mapTile.biome instanceof Glacier))
                {
                    g.drawLine(mapTile.centerPoint.getX(), mapTile.centerPoint.getY(), mapTile.riverDirection.centerPoint.getX(), mapTile.riverDirection.centerPoint.getY());
                }
                g.setColor(Color.BLACK);
                if(mapTile.city != null)
                {
                    Ellipse2D.Double circle = new Ellipse2D.Double(mapTile.centerPoint.getX(), mapTile.centerPoint.getY(), 6, 6);
                    Graphics2D g2d = (Graphics2D)g;
                    g2d.fill(circle);
                    g2d.drawString(mapTile.city.toString(), mapTile.centerPoint.getX(), mapTile.centerPoint.getY());
                }
            }

        }
    }
}
