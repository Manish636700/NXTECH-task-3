package BrickBreakerGame;

import javax.swing.*;
public class BrickBreakerGame extends JFrame {
    public BrickBreakerGame(){
        setTitle("Brick Breaker Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(()->new BrickBreakerGame());
    }
}
