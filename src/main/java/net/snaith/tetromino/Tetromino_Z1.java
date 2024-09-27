package net.snaith.tetromino;

import java.awt.*;

public class Tetromino_Z1 extends Tetromino {

    public Tetromino_Z1() {
        create(Color.red);
    }


    @Override
    public void setPos(int x, int y) {
        //     #
        //   # 0
        //   #

        // blocks[0] should be the point of rotation, denoted by 0 above
        blocks[0].x = x;
        blocks[0].y = y;
        blocks[1].x = blocks[0].x;
        blocks[1].y = blocks[0].y - Block.SIZE;
        blocks[2].x = blocks[0].x - Block.SIZE;
        blocks[2].y = blocks[0].y;
        blocks[3].x = blocks[0].x - Block.SIZE;
        blocks[3].y = blocks[0].y + Block.SIZE;
    }

    @Override
    public void getD1() {
        //     #
        //   # 0
        //   #

        tmpBlocks[0].x = blocks[0].x;
        tmpBlocks[0].y = blocks[0].y;
        tmpBlocks[1].x = blocks[0].x;
        tmpBlocks[1].y = blocks[0].y - Block.SIZE;
        tmpBlocks[2].x = blocks[0].x - Block.SIZE;
        tmpBlocks[2].y = blocks[0].y;
        tmpBlocks[3].x = blocks[0].x - Block.SIZE;
        tmpBlocks[3].y = blocks[0].y + Block.SIZE;

        updatePos(1);
    }

    @Override
    public void getD2() {
        //   # #
        //     0 #
        //

        tmpBlocks[0].x = blocks[0].x;
        tmpBlocks[0].y = blocks[0].y;
        tmpBlocks[1].x = blocks[0].x + Block.SIZE;
        tmpBlocks[1].y = blocks[0].y;
        tmpBlocks[2].x = blocks[0].x;
        tmpBlocks[2].y = blocks[0].y - Block.SIZE;
        tmpBlocks[3].x = blocks[0].x - Block.SIZE;
        tmpBlocks[3].y = blocks[0].y - Block.SIZE;

        updatePos(2);
    }

    @Override
    public void getD3() {
        getD1();
    }

    @Override
    public void getD4() {
        getD2();
    }
}
