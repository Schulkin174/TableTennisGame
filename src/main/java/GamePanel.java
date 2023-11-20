import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable{

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555)); // можно написать проще, данная логика исходя из размеров реальных столов для наст.тенниса
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT); // SCREEN_SIZE устанавливаем равным новому объекту Dimension с шириной, равной GAME_WIDTH, и высотой, равной GAME_HEIGHT
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 90; // при установке g.setColor, оптимальный размер 25, см. метод draw класса paddle
    static final int PADDLE_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;

    GamePanel(){
        newPaddles();
        newBall();
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true); // в нашем случае, метод позволяет делать фокус ввода с клавиатуры (передвижение ракеток)
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }
    public void newBall(){
        random = new Random();
        ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2), random.nextInt(GAME_HEIGHT - BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER); // добавил появление мяча из рандомного места доски относительно центра
    }
    public void newPaddles(){
        paddle1 = new Paddle(0, (GAME_HEIGHT/2)-(PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 1, "src/images/table-tennis-racket.png"); // чтобы разобраться что здесь происходит, см. параметры класса "Paddle"
        paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH, (GAME_HEIGHT/2)-(PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 2, "src/images/table-tennis-racket.png");

    }
    public void paint(Graphics g){
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this); // отображаем Image по координатам x и y
    }
    public void draw(Graphics g){
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
    }
    public void move(){
        paddle1.move(); // включаю ускорение ракеток
        paddle2.move();
        ball.move();
    }
    public void checkCollision(){
        // настраиваю ограничение, чтобы ракетки не уходили за рамки игрового поля
        if (paddle1.y <= 0)
            paddle1.y = 0;
        if (paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
        if (paddle2.y <= 0)
            paddle2.y = 0;
        if (paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;

        // настраиваю ограничение, чтобы мячь не вылетал за пределы игрового поля
        if (ball.y <=0){ // 0 в нашем контексте - это верх
            ball.setYDirection(-ball.yVelocity); // произвожу реверс движения мяча
        }
        if (ball.y >= GAME_HEIGHT - BALL_DIAMETER){ // высота поля минус диаметр мяча, в нашем случае - это низ поля
            ball.setYDirection(-ball.yVelocity); // реверс движения мяча по тому же принципу, что и от верхнего края
        }

        // настраиваю отскок мяча от ракеток
        if (ball.intersects(paddle1)){
            ball.xVelocity = Math.abs(ball.xVelocity); // чтобы мяч менял направление, но сохранял скорость
            ball.xVelocity++; // ускоряем мяч после каждого отскока от ракетки для сложности и интереса
            if (ball.yVelocity > 0)
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.setXDirection(ball.xVelocity); // устанавливаю новые значения скорости по горизонтали после отскока от ракетки
            ball.setYDirection(ball.yVelocity); // устанавливаю новые значения скорости по вертикали
        }
        if (ball.intersects(paddle2)){
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;
            if (ball.yVelocity > 0)
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.setXDirection(-ball.xVelocity); // чтобы отправить мяч в противоположную сторону, делаю реверс предыдущего кода
            ball.setYDirection(ball.yVelocity);
        }
        // настраиваю систему начисления баллов, очков (счет, он же score)
        if (ball.x <= 0){
            score.player2++;
            newPaddles();
            newBall();
            System.out.println("Игрок 2:"+score.player2);
        }
        if (ball.x >= GAME_WIDTH - BALL_DIAMETER){
            score.player1++;
            newPaddles();
            newBall();
            System.out.println("Игрок 1:"+score.player1);
        }
    }

    public void run(){
        // создаю игровой цикл
        long lastTime = System.nanoTime(); // измеряю время между итерациями цикла while
        double amountOfTicks = 60.0; // обновляем картинку 60 раз в секунду. (Поэксперементировать со временем)
        double ns = 1000000000 / amountOfTicks; // ns = nano second. Наносекунды между обновлениями цикла.
        double delta = 0; // отслеживаю время с последнего обновления
        while (true){
            long now = System.nanoTime(); // получаю текущее время в наносекундах
            delta += (now - lastTime) / ns; // рассчитываю сколько времени прошло с последнего обновления и добавляю к дельте
            lastTime = now;
            if (delta >= 1){ // проверяю, прошло ли достаточно времени для следующего обновления. Если "да", то вызываю методы:
                move();
                checkCollision();
                repaint();
                delta--; // и после вызова мув, чекколижн, репэйнт - уменьшаю дельту на -1.
                // Вышеописанный механизм позволяет игровому циклу обновлять игровой мир с заданной частотой, что полезно для поддержания стабильной скорости и плавности в играх.
                //    System.out.println("TEST"); // цикл сложный, поэтому провожу тест работы цикла: в рабочем режиме проверка не нужна.
            }
        }
    }
    // создаю внутренний класс ActionListener с двумя методами: нажимаем и отпускаем клавиши
    public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }
        public void keyReleased(KeyEvent e){
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);

        }
    }
}