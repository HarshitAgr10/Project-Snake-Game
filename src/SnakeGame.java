import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;   // To store the segments of snake body 
import java.util.Random;    // To place random x and y values on screen as food 
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private class Tile{
        int x;
        int y;

        Tile(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    // Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    // Food 
    Tile food;
    Random random;   // To place food at random positions on board 

    // Game Logic 
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    SnakeGame(int boardWidth, int boardHeight)
    {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight; 
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);    // Snake 
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10, 10);       // Food 
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this);    // 100 milliseconds
        gameLoop.start();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Grid 
        // for (int i=0; i < boardWidth/tileSize; i++)   // boardWidth = 600 tileSize = 25
        // {
        //     // (x1, y1, x2, y2)   Draw lines for grid (row, column)
        //     g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);  // Vertical lines
        //     g.drawLine(0, i*tileSize, boardWidth, i*tileSize);   // Horizontal lines
        // }

        // Food
        g.setColor(Color.red);
        // g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);


        // Snake Head
        g.setColor(Color.green);
        // g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);


        // Snake Body 
        for (int i=0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            // g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);

        }

        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("GAME OVER: " + String.valueOf(snakeBody.size()), tileSize -16, tileSize);
        } else 
        {
            g.drawString("SCORE: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }

    public void placeFood()
    {
        food.x = random.nextInt(boardWidth / tileSize);   // 600 / 25 = 24
        food.y = random.nextInt(boardHeight / tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        // Eat food 
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        // Snake Body 
        for (int i = snakeBody.size()-1; i>=0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else 
            {
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        // Snake Head 
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // Game Over Conditions 
        for (int i=0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            // Coliide with the snake head 
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        // If snake hits one of the four walls 
        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth || 
            snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > boardHeight) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {   // Every 100 ms, call this, it will repaint 
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else 
        if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else 
        if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else 
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    // Do not Need 
    @Override
    public void keyTyped(KeyEvent e) {
    }

   
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
