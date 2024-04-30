import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 600;
        int boardHeight = boardWidth;

        JFrame frame = new JFrame("Snake");  // Name of window 
        frame.setVisible(true);  // Set window to visible 
        frame.setSize(boardWidth, boardHeight);  // Set size of window (600 pixels * 600 pixels)
        frame.setLocationRelativeTo(null);  // To open up the window at center of screen 
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Window will be closed when clicked on X symbol 
 
        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
        frame.add(snakeGame);
        frame.pack();  // To set windowSize 600p*600p(Title Bar was taking space, size of panel wasn't 600*600)
        snakeGame.requestFocus();
    }
}
