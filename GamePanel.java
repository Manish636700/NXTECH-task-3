package BrickBreakerGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;


public class GamePanel extends JPanel implements KeyListener, ActionListener {
    private int paddleX;
    private int ballX, ballY, ballSpeedX, ballSpeedY;
    private Timer timer;
    private boolean ballReleased;
    private static final int PADDLE_WIDTH = 80;
    private static final int PADDLE_HEIGHT = 10;
    private static final int BALL_SIZE = 15;
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    private static final int PADDLE_SPEED = 20;
    private static final int BALL_SPEED = 2;
    private static final int BRICK_ROWS = 5;
    private static final int BRICK_COLS = 8;
    private static final int BRICK_WIDTH = 60;
    private static final int BRICK_HEIGHT = 20;
    private static final int BRICK_GAP = 5;
    private boolean[][] bricks;

    public GamePanel() {
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        paddleX = WINDOW_WIDTH / 2 - PADDLE_WIDTH / 2;
        ballX = WINDOW_WIDTH / 2 - BALL_SIZE / 2;
        ballY = WINDOW_HEIGHT / 2 - BALL_SIZE / 2;
        ballSpeedX = BALL_SPEED;
        ballSpeedY = BALL_SPEED;
        ballReleased = false;

        bricks = new boolean[BRICK_ROWS][BRICK_COLS];
        for (int i = 0; i < BRICK_ROWS; i++) {
            for (int j = 0; j < BRICK_COLS; j++) {
                bricks[i][j] = true;
            }
        }
        timer = new Timer(10, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPaddle(g);
        drawBall(g);
        drawBricks(g);
    }

    private void drawPaddle(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(paddleX, WINDOW_HEIGHT - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);
    }

    private void drawBall(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);
    }

    private void drawBricks(Graphics g) {
        int brickX, brickY;
        for (int i = 0; i < BRICK_ROWS; i++) {
            for (int j = 0; j < BRICK_COLS; j++) {
                if (bricks[i][j]) {
                    brickX = j * (BRICK_WIDTH + BRICK_GAP);
                    brickY = i * (BRICK_HEIGHT + BRICK_GAP);
                    g.setColor(Color.RED);
                    g.fillRect(brickX, brickY, BRICK_WIDTH, BRICK_HEIGHT);
                }
            }
        }
    }

    private void movePaddle(int direction) {
        paddleX += direction * PADDLE_SPEED;
        if (paddleX < 0) {
            paddleX = 0;
        } else if (paddleX + PADDLE_WIDTH > WINDOW_WIDTH) {
            paddleX = WINDOW_WIDTH - PADDLE_WIDTH;
        }
    }

    private void moveBall() {
        ballX += ballSpeedX;
        ballY += ballSpeedY;

        if (ballX <= 0 || ballX + BALL_SIZE >= WINDOW_WIDTH) {
            ballSpeedX = -ballSpeedX;
        }
        if (ballY <= 0) {
            ballSpeedY = -ballSpeedY;
        }
        if (ballY + BALL_SIZE >= WINDOW_HEIGHT - PADDLE_HEIGHT && ballX + BALL_SIZE >= paddleX && ballX <= paddleX + PADDLE_WIDTH) {
            ballSpeedY = -ballSpeedY;
        }
        int brickX, brickY;

        for (int i = 0; i < BRICK_ROWS; i++) {
            for (int j = 0; j < BRICK_COLS; j++) {
                if (bricks[i][j]) {
                    brickX = j * (BRICK_WIDTH + BRICK_GAP);
                    brickY = i * (BRICK_HEIGHT + BRICK_GAP);

                    if (ballX + BALL_SIZE >= brickX && ballX <= brickX + BRICK_WIDTH && ballY + BALL_SIZE >= brickY && ballY <= brickY + BRICK_HEIGHT) {
                        bricks[i][j] = false;
                        ballSpeedY = -ballSpeedY;
                    }
                }
            }
        }
        if (ballY + BALL_SIZE >= WINDOW_HEIGHT) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if (ballReleased) {
            moveBall();
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            movePaddle(-1);
        } else if (key == KeyEvent.VK_RIGHT) {
            movePaddle(1);
        } else if (key == KeyEvent.VK_SPACE) {
            ballReleased = true;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}