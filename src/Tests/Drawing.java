package Tests;

import MapGeneration.*;
import MapGeneration.GenerationSettings.Options;

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
        Map map = new Map();
        Screen s;
        Options settings = new Options(1200,800,20400);
        s = map.getMap(settings);
        add(s);
        setVisible(true);
    }
    public static void main(String[] args)
    {
        new Drawing();
    }

}
