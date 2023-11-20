import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class GameFrame extends JFrame{

     Color customColor = new Color(255, 150, 125);
    // Color customColor = new Color(54, 147, 46); // классика

    GamePanel panel;
    GameFrame(){
        panel = new GamePanel();
        this.add(panel);
        this.setTitle("Настольный теннис");
        this.setResizable(false);
        this.setBackground(customColor);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack(); // // определение оптимального размера окна
        this.setVisible(true);
        this.setLocationRelativeTo(null); // выставляю окно в центр
    }
}
