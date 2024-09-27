package net.snaith.main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame("Tetris: Java Edition");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        // Instantiate the panel and add it to our main window.
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        // Window will adapt WIDTH and HEIGHT to fit the passed in JPanel
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGame();
    }
}
