package net.snaith.tetromino;

import java.awt.*;

/**
 * Tetromino
 * In Tetris, the pieces we fill the board with are called Tetromino's, they are comprised of 4 Blocks, this class will
 * represent each of the 4 blocks within a Tetromino.
 */
public class Block extends Rectangle {

    public int x, y;
    public static final int SIZE = 30;
    public Color c;

    public Block(Color c) {
        this.c = c;
    }

    public void draw(Graphics2D g2) {
        final int MARGIN = 2;

        g2.setColor(c);
        g2.drawRect(x + MARGIN, y + MARGIN, SIZE - (MARGIN * 2), SIZE - (MARGIN * 2));
    }
}
