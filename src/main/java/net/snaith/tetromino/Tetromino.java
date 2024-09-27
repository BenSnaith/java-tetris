package net.snaith.tetromino;

import net.snaith.main.GameManager;
import net.snaith.main.InputManager;

import java.awt.*;

public abstract class Tetromino {

    public Block[] blocks = new Block[4];
    public Block[] tmpBlocks = new Block[4];
    int autoDropCounter = 0;
    int direction = 1;
    public boolean leftCollision, rightCollision, bottomCollision;
    public boolean active = true;
    public boolean deactivating = false;
    public int deactivateCounter = 0;

    public void create(Color c) {
        for(int i = 0; i < blocks.length; i++) {
            blocks[i] = new Block(c);
            tmpBlocks[i] = new Block(c);
        }
    }

    public abstract void setPos(int x, int y);
    public abstract void getD1();
    public abstract void getD2();
    public abstract void getD3();
    public abstract void getD4();

    public void checkMovementCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkStaticBlockCollision();

        // Left Side
        for(int i = 0; i < blocks.length; i++) {
            if(blocks[i].x == GameManager.lX) {
                leftCollision = true;
            }
        }

        // Right Side
        for(int i = 0; i < blocks.length; i++) {
            if(blocks[i].x + Block.SIZE == GameManager.rX) {
                rightCollision = true;
            }
        }

        // Bottom Side
        for(int i = 0; i < blocks.length; i++) {
            if(blocks[i].y + Block.SIZE == GameManager.bY) {
                bottomCollision = true;
            }
        }
    }

    public void checkRotationCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkStaticBlockCollision();

        // Left Side
        for(int i = 0; i < blocks.length; i++) {
            if(tmpBlocks[i].x < GameManager.lX) {
                leftCollision = true;
            }
        }

        // Right Side
        for(int i = 0; i < blocks.length; i++) {
            if(tmpBlocks[i].x + Block.SIZE > GameManager.rX) {
                rightCollision = true;
            }
        }

        // Bottom Side
        for(int i = 0; i < blocks.length; i++) {
            if(tmpBlocks[i].y + Block.SIZE > GameManager.bY) {
                bottomCollision = true;
            }
        }
    }

    public void checkStaticBlockCollision() {
        for(Block block : GameManager.staticBlocks) {
            // Bottom
            for(int i = 0; i < blocks.length; i++) {
                if(blocks[i].y + Block.SIZE == block.y && blocks[i].x == block.x) {
                    bottomCollision = true;
                }
            }
            // Left
            for(int i = 0; i < blocks.length; i++) {
                if(blocks[i].x - Block.SIZE == block.x && blocks[i].y == block.y) {
                    leftCollision = true;
                }
            }
            // Right
            for(int i = 0; i < blocks.length; i++) {
                if(blocks[i].x + Block.SIZE == block.x && blocks[i].y == block.y) {
                    rightCollision = true;
                }
            }
        }
    }

    public void updatePos(int direction) {

        // check if there will be any collisions before performing rotation
        checkRotationCollision();

        if(!leftCollision && !rightCollision && !bottomCollision) {
            this.direction = direction;
            blocks[0].x = tmpBlocks[0].x;
            blocks[0].y = tmpBlocks[0].y;
            blocks[1].x = tmpBlocks[1].x;
            blocks[1].y = tmpBlocks[1].y;
            blocks[2].x = tmpBlocks[2].x;
            blocks[2].y = tmpBlocks[2].y;
            blocks[3].x = tmpBlocks[3].x;
            blocks[3].y = tmpBlocks[3].y;
        }
    }

    public void update() {

        if(deactivating) {
            deactivating();
        }

        // Movement
        if(InputManager.upPress) {
            // Matrix rotation
            switch(this.direction) {
                case 1:
                    getD2();
                    break;
                case 2:
                    getD3();
                    break;
                case 3:
                    getD4();
                    break;
                case 4:
                    getD1();
                    break;
            }
            InputManager.upPress = false;
        }

        checkMovementCollision();

        if(InputManager.leftPress) {
            if(!leftCollision) {
                for (Block block : blocks) {
                    block.x -= Block.SIZE;
                }
                InputManager.leftPress = false;
            }
        }
        if(InputManager.downPress) {
            if(!bottomCollision) {
                for (Block block : blocks) {
                    block.y += Block.SIZE;
                }
                // reset the autoDropCounter
                autoDropCounter = 0;
                InputManager.downPress = false;
            }
        }
        if(InputManager.rightPress) {
            if(!rightCollision) {
                for (Block block : blocks) {
                    block.x += Block.SIZE;
                }
                InputManager.rightPress = false;
            }
        }

        if(bottomCollision) {
            deactivating = true;
        }
        else {
            autoDropCounter++;
            if(autoDropCounter == GameManager.dropInterval) {
                for (Block block : blocks) {
                    block.y += Block.SIZE;
                }
                autoDropCounter = 0;
            }
        }
    }

    public void deactivating() {
        deactivateCounter++;

        if(deactivateCounter == 45) {
            deactivateCounter = 0;
            checkMovementCollision();
            if(bottomCollision) {
                active = false;
            }
        }
    }

    public void draw(Graphics2D g2) {
        // Get the color of the current mino
        g2.setColor(blocks[0].c);

        final int MARGIN = 2;
        // Draw each block in the currentTetromino
        for(int i = 0; i < blocks.length; i++) {
            g2.fillRect(blocks[i].x + MARGIN, blocks[i].y + MARGIN,
                    Block.SIZE - (MARGIN * 2), Block.SIZE - (MARGIN * 2));
        }
    }
}
