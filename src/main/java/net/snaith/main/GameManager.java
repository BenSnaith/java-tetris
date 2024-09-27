package net.snaith.main;

import net.snaith.tetromino.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameManager {

    // Game Area
    private static final int WIDTH = 360;
    private static final int HEIGHT = 600;

    // Left and right Xcoords
    public static int lX;
    public static int rX;

    // Top and Bottom Ycoords
    public static int tY;
    public static int bY;

    // Current Tetromino
    Tetromino currentTetromino;
    private final int TETROMINO_START_X;
    private final int TETROMINO_START_Y;

    // Next Tetromino
    Tetromino nextTetromino;
    private final int NEXT_TETROMINO_X;
    private final int NEXT_TETROMINO_Y;

    // All of our blocks
    public static ArrayList<Block> staticBlocks = new ArrayList<>();

    // Misc
    public static int dropInterval = 60; // Every 60 frames (1/s)
    private int effectCounter;
    private boolean effectCounterOn = false;
    ArrayList<Integer> effectY = new ArrayList<>();
    public boolean gameOver;

    // Scoring
    int level = 1;
    int lines = 0;
    int score = 0;

    public GameManager() {
        lX = (GamePanel.WIDTH / 2) - (WIDTH / 2);
        rX = lX + WIDTH;

        tY = 50;
        bY = tY + HEIGHT;

        TETROMINO_START_X = lX + (WIDTH / 2) - Block.SIZE;
        TETROMINO_START_Y = tY + Block.SIZE;

        NEXT_TETROMINO_X = rX + 175;
        NEXT_TETROMINO_Y = tY + 110;

        currentTetromino = pickTetronimo();
        currentTetromino.setPos(TETROMINO_START_X, TETROMINO_START_Y);

        nextTetromino = pickTetronimo();
        nextTetromino.setPos(NEXT_TETROMINO_X, NEXT_TETROMINO_Y);
    }

    private Tetromino pickTetronimo() {
        Tetromino result = null;
        int i = new Random().nextInt(7);

        switch(i) {
            case 0:
                result = new Tetromino_L1();
                break;
            case 1:
                result = new Tetromino_L2();
                break;
            case 2:
                result = new Tetromino_S();
                break;
            case 3:
                result = new Tetromino_B();
                break;
            case 4:
                result = new Tetromino_T();
                break;
            case 5:
                result = new Tetromino_Z1();
                break;
            case 6:
                result = new Tetromino_Z2();
                break;
        }

        return result;
    }

    public void update() {
        // Check if the current Tetromino is active
        if(currentTetromino.active) {
            currentTetromino.update();
        }
        else {
            for(Block block : currentTetromino.blocks) {
                staticBlocks.add(block);
            }

            if(currentTetromino.blocks[0].x == TETROMINO_START_X && currentTetromino.blocks[0].y == TETROMINO_START_Y) {
                gameOver = true;
            }

            currentTetromino.deactivating = false;

            currentTetromino = nextTetromino;
            currentTetromino.setPos(TETROMINO_START_X, TETROMINO_START_Y);
            nextTetromino = pickTetronimo();
            nextTetromino.setPos(NEXT_TETROMINO_X, NEXT_TETROMINO_Y);

            checkLines();
        }
    }

    private void checkLines() {
        int x = lX;
        int y = tY;
        int blockCount = 0;
        int lineCount = 0;

        while(x < rX && y < bY) {
            for(int i = 0; i < staticBlocks.size(); i++) {
                if(staticBlocks.get(i).x == x && staticBlocks.get(i).y == y) {
                    blockCount++;
                }
            }

            x += Block.SIZE;
            if(x == rX) {

                if(blockCount == 12) {

                    effectCounterOn = true;
                    effectY.add(y);

                    for(int i = staticBlocks.size() - 1; i >= 0; i--) {
                        if(staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);
                        }
                    }

                    lineCount++;
                    lines++;
                    // Drop Speed
                    // Increase the drop speed after the score hits a certain number
                    if(lines % 10 == 0 && dropInterval > 1) {
                        level++;
                        if(dropInterval > 10) {
                            dropInterval -= 10;
                        }
                        else {
                            dropInterval -= 1;
                        }
                    }

                    for(int i = 0; i < staticBlocks.size(); i++) {
                        if(staticBlocks.get(i).y < y) {
                            staticBlocks.get(i).y += Block.SIZE;
                        }
                    }
                }

                blockCount = 0;
                x = lX;
                y += Block.SIZE;
            }
        }

        if(lineCount > 0) {
            int singleLineScore = 10 * level;
            score += singleLineScore * lineCount;
        }
    }

    public void draw(Graphics2D g2) {
        final int strokeVal = 4;

        // Draw the game area
        g2.setColor(Color.blue);
        g2.setStroke(new BasicStroke((float) strokeVal));
        g2.drawRect(lX - strokeVal, tY - strokeVal,
                WIDTH + (2 * strokeVal), HEIGHT + (2 * strokeVal));

        // Draw the next Tetromino area.
        final int x = rX + 100;
        final int y = tY;
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Chalkboard", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x + 60, y + 60);

        // Draw the Score Frame
        g2.drawRect(x, bY - 300, 250, 300);
        g2.drawString("LEVEL: " + level, x + 20, bY - 250);
        g2.drawString("LINES: " + lines, x + 20, bY - 150);
        g2.drawString("SCORE: " + score, x + 20, bY - 50);

        // Draw the current Tetromino
        if(currentTetromino != null) {
            currentTetromino.draw(g2);
        }
        nextTetromino.draw(g2);

        // Draw the Array of static blocks
        for(Block block : staticBlocks) {
            block.draw(g2);
        }

        if(effectCounterOn) {
            effectCounter++;

            g2.setColor(Color.red);
            for(int i = 0; i < effectY.size(); i++) {
                g2.fillRect(lX, effectY.get(i), WIDTH, Block.SIZE);
            }

            if(effectCounter == 10) {
                effectCounterOn = false;
                effectCounter = 0;
                effectY.clear();
            }
        }

        // Draw the Pause screen
        g2.setColor(Color.GRAY);
        g2.setFont(g2.getFont().deriveFont(50.0f));
        if(gameOver) {
            g2.drawString("GAME OVER", (GamePanel.WIDTH / 2) - (g2.getFontMetrics().stringWidth("GAME OVER") / 2), GamePanel.HEIGHT / 2);
        }
        if(InputManager.pausePressed) {
            g2.drawString("PAUSED", (GamePanel.WIDTH / 2) - (g2.getFontMetrics().stringWidth("PAUSED") / 2), GamePanel.HEIGHT / 2);
        }

        g2.setColor(Color.blue);
        g2.setFont(g2.getFont().deriveFont(40.0f));
        g2.drawString("Tetris: Java Edition",
                        (150 - (g2.getFontMetrics().stringWidth("PAUSED") / 2)),
                        GamePanel.HEIGHT / 2);
    }
}
