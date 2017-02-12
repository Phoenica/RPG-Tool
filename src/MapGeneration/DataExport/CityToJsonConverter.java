package MapGeneration.DataExport;

import MapGeneration.Graph.Polygon;
import MapGeneration.Graph.PolygonProperties.City;
import MapGeneration.Graph.PolygonProperties.WaterType;
import MapGeneration.Map;
import com.google.gson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Phoenicia on 12.02.2017.
 */
public class CityToJsonConverter {
    public void convertAndSave(Map map) throws IOException {
        ArrayList<CityPolygon> toJsonList = new ArrayList();
        for(Polygon polygon: map.diagram.polygons)
        {
            if(polygon.city != null)
                toJsonList.add(new CityPolygon(polygon));
        }
        PrintWriter out = new PrintWriter("Cities.json");
        String json = new Gson().toJson(toJsonList);
        System.out.println(json);
        out.println(json);
        out.close();

    }
    private class CityPolygon
    {
        String name;
        //String biome;
        boolean isThereRiver;
        boolean waterOnLeft = false;
        boolean waterOnRight = false;
        boolean waterOnTop = false;
        boolean waterOnBottom = false;
        public CityPolygon(Polygon polygon)
        {
            this.name = polygon.city.toString();
        //    this.biome = getLast(polygon.biome.getClass().toString().split("\\."));
            if(polygon.river == true)
            {
                isThereRiver = true;
            }
            isThereRiver = false;
            for(Polygon neighbour: polygon.neighborPolygons)
            {
                if(neighbour.water != WaterType.Land)
                {
                    if(neighbour.centerPoint.getY() > polygon.centerPoint.getY()) waterOnTop = true;
                    else waterOnBottom = true;
                    if(neighbour.centerPoint.getX() > polygon.centerPoint.getX()) waterOnRight = true;
                    else waterOnLeft = true;
                }
            }

        }

        private String getLast(String[] split) {
            return split[split.length-1];
        }
    }
}
