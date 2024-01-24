import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        // Creating and setting up the JFrame
        JFrame frame = new JFrame("Wall Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setResizable(false); // prevent user from resizing frame
        frame.getContentPane().setBackground(Color.BLACK); // Set background color to black
        frame.setLayout(null);
        frame.getContentPane().setLayout(null);

        // Creating and setting up StartMenu
        StartMenu startMenu = new StartMenu();
        frame.addKeyListener(new StartMenuListener(startMenu));
        frame.add(startMenu);
        startMenu.updateBounds(frame.getSize());

        // Displaying the frame
        frame.setVisible(true);
        while (!startMenu.isGameReady()){ // wait until the game is ready
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        startMenu.setVisible(false); // once the game is ready, make the start menu is invisible


        // Creating and setting up the Paddle
        Paddle paddle = new Paddle(10, 75, frame.getHeight(), 7);
        paddle.setLayout(null); // Set the layout to null
        paddle.setOpaque(false); // Make the panel transparent
        frame.add(paddle); // show paddle on screen
        frame.addKeyListener(new PaddleListener(paddle)); // for paddle controls
        paddle.start(); // start paddleThread for controls
        paddle.setVisible(true);

        // Creating and setting up the Paddle
        Ball ball = new Ball(frame.getWidth()/2, frame.getHeight()/2,10,5, frame.getHeight(), paddle);
        ball.setLayout(null); // Set the layout to null
        ball.setOpaque(false); // Make the panel transparent
        frame.add(ball); // show ball on screen
        ball.start(); // start ball motion Thread

        // Creating and setting up the Score
        Score score = new Score(frame.getSize());
        frame.add(score);
        score.start(); // start score count Thread

        // wait until the ball Thread ends to end game
        try {
            ball.getBallThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // ***GAME OVER CODE***
        paddle.setVisible(false); // Make paddle invisible
        ball.setVisible(false); // Make ball invisible (though already offscreen)
        score.setVisible(false); // Make score invisible

        GameOverLabel gameOverLabel = new GameOverLabel(score.getScore());
        frame.add(gameOverLabel);
        gameOverLabel.updateBounds(frame.getSize()); // update bounds to appear in frame
    }
}
