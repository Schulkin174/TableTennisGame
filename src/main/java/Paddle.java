import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Paddle extends Rectangle {

    int id;
    int yVelocity; // скорость передвижения ракетки
    int speed = 10;

    Image image;

    Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id, String imagePath) {
        super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT); // поскольку класс Paddle унаследован от суперкласса Rectangle, прописываем параметры, свойственные для Rectangle
        this.id = id; // инициализирую айдишники для определения ракеток. Это необходимо для прорисовки в методе "draw"

        // Загружаем изображение для ракетки
        ImageIcon icon = new ImageIcon("src/images/table-tennis-racket.png");
        image = icon.getImage();

    }

    public void keyPressed(KeyEvent e) {
        switch (id){
            case 1:
                if(e.getKeyCode()==KeyEvent.VK_W){
                    setYDirection(-speed);
                    move();
                }
                if(e.getKeyCode()==KeyEvent.VK_S){
                    setYDirection(speed);
                    move();
                }
                break;
            case 2:
                if(e.getKeyCode()==KeyEvent.VK_UP){
                    setYDirection(-speed);
                    move();
                }
                if(e.getKeyCode()==KeyEvent.VK_DOWN){
                    setYDirection(speed);
                    move();
                }
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (id) {
            case 1:
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    setYDirection(0);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    setYDirection(0);
                    move();
                }
                break;
            case 2:
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    setYDirection(0);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    setYDirection(0);
                    move();
                }
                break;
        }
    }

    public void setYDirection(int yDirection) {
        yVelocity = yDirection;
    }

    public void move() {
        y = y + yVelocity;
    }

    public void draw(Graphics g) {
        if (id == 1) {
            //  g.setColor(Color.red); // если потребуется убрать png иконки - раскомментировать
            g.drawImage(image, x, y, width, height, null);

        }
        else if (id == 2) { // да, вполне можно обойтись без "if (id == 2)" и оставить только "else", но так читабельнее
            //    g.setColor(Color.black); // если потребуется убрать png иконки - раскомментировать
            g.drawImage(image, x, y, width, height, null);

            }
          }
        }
