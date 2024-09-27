package net.snaith.tetromino;

import java.awt.*;

public class Tetromino_S extends Tetromino {

    public Tetromino_S() {
        create(Color.yellow);
    }

    @Override
    public void setPos(int x, int y) {
        //
        //   # #
        //   # #

        // blocks[0] should be the point of rotation, denoted by 0 above
        blocks[0].x = x;
        blocks[0].y = y;
        blocks[1].x = blocks[0].x;
        blocks[1].y = blocks[0].y + Block.SIZE;
        blocks[2].x = blocks[0].x + Block.SIZE;
        blocks[2].y = blocks[0].y;
        blocks[3].x = blocks[0].x + Block.SIZE;
        blocks[3].y = blocks[0].y + Block.SIZE;
    }

    @Override
    public void getD1() {

    }

    @Override
    public void getD2() {

    }

    @Override
    public void getD3() {

    }

    @Override
    public void getD4() {

    }
}
