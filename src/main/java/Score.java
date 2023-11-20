import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Score extends Rectangle{

    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int player1;
    int player2;
    Score(int GAME_WIDTH, int GAME_HEIGHT){
        Score.GAME_WIDTH = GAME_WIDTH;
        Score.GAME_HEIGHT = GAME_HEIGHT;
    }
    public void draw(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font("Times new roman", Font.PLAIN, 60));

        g.drawLine((GAME_WIDTH/2), 0, GAME_WIDTH/2, GAME_HEIGHT); // рисую "сетку"-линию посередине игрового поля: уже похоже на игровой стол

    //    g.drawString(String.valueOf(player1), (GAME_WIDTH/2)-85, 50); // упрощенная запись для отображения счета
    //    g.drawString(String.valueOf(player2), (GAME_WIDTH/2)+20, 50);

        g.drawString(String.valueOf(player1/10)+String.valueOf(player1%10), (GAME_WIDTH/2) -85, 50); // отображаю "счет" для первого игрока. Деление на 10 для того, чтобы отобразить счет в формате "00:00"
        g.drawString(String.valueOf(player2/10)+String.valueOf(player2%10), (GAME_WIDTH/2) +20, 50); // отображаю "счет" для второго игрока
    }
}
