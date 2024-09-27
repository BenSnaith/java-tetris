package net.snaith.main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private final int FPS = 60;
    Thread gameThread;
    GameManager gm;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        this.addKeyListener(new InputManager());
        this.setFocusable(true);

        gm = new GameManager();
    }

    public void startGame() {
        gameThread = new Thread(this);
        // .start() calls the .run() method of the task (runnable) that the thread is assigned to, in this case, that
        // runnable would be this class.
        gameThread.start();
    }

    /**
     * Game Loop
     * In a game loop, we do two things, we update() our game logic and then we draw() this new information,
     * this is done constantly while the thread is running.
     */
    @Override
    public void run() {

        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {
        if(!InputManager.pausePressed && !gm.gameOver) {
            gm.update();
        }
    }

    // Paint the graphics to the screen
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        gm.draw(g2);
    }
}
