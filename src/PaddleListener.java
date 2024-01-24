import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PaddleListener extends KeyAdapter {

    Paddle paddle;
    public PaddleListener(Paddle paddle) {
        this.paddle = paddle;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            //System.out.println("Up arrow key was pressed!");
            //paddle.movePaddleUp();
            paddle.setIsMoving(1);
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            //System.out.println("Down arrow key was pressed!");
            //paddle.movePaddleDown();
            paddle.setIsMoving(-1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            paddle.setIsMoving(0);
        }
    }
}
