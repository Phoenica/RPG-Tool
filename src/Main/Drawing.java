package Main;

import WorldMap.*;
import WorldMap.DataExport.MapPrinter;
import WorldMap.GenerationSettings.Options;

import javax.swing.*;
import java.awt.*;

public class Drawing extends JFrame {
    public Drawing()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1700,1500);
        setResizable(false);
        setTitle("Graphics");

        init();
    }
    public void init()
    {
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1,1,0,0));
        MapPrinter s;
        Options settings = new Options(600,300,4000);
        Map map = new Map(settings);
        s = map.getMap();
        add(s);
        setVisible(true);
    }
    public static void main(String[] args)
    {
        new Drawing();
    }

}
