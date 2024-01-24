import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Paddle extends JPanel implements Runnable {
    public static final Color PADDLE_COLOR = Color.WHITE;
    private int paddleWidth;
    private int paddleHeight;
    private int panelSideLength;
    private int paddleY;
    private int speed;
    private int isMoving; //0 if not moving, 1 if moving up, -1 if moving down
    private Thread paddleThread;

    public int getPaddleWidth() {
        return paddleWidth;
    }

    public int getPaddleHeight() {
        return paddleHeight;
    }

    public int getPaddleY() {
        return paddleY;
    }

    public int getIsMoving() {
        return isMoving;
    }

    public void setIsMoving(int isMoving) {
        this.isMoving = isMoving;
    }

    public Paddle(int paddleWidth, int paddleHeight, int panelSideLength, int speed) {
        this.paddleWidth = paddleWidth;
        this.paddleHeight = paddleHeight;
        this.panelSideLength = panelSideLength - 30; // to account for 30 px of nav bar
        this.speed = speed;
        this.paddleY = panelSideLength /2 - paddleHeight/2;
        setPreferredSize(new Dimension(paddleWidth, paddleHeight));
        updateBounds(); // sets the Bounds of the Paddle for the first time

        paddleThread = new Thread(this);
    }

    private void updateBounds() {
        setBounds(0, paddleY, paddleWidth, paddleHeight);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(PADDLE_COLOR); // Set paddle color
        g.fillRect(0, 0, paddleWidth, paddleHeight);
    }

    public void movePaddle(int i) {
        //System.out.println("Moving paddle " + i);
        paddleY += i;
        if (paddleY < 0) {
            paddleY = 0;
        }
        if (paddleY + paddleHeight > panelSideLength){
            paddleY = panelSideLength - paddleHeight;
        }
        updateBounds(); // should force a repaint
        //this.repaint();
    }

    public void movePaddleUp() {
        movePaddle(-speed);
    }

    public void movePaddleDown() {
        movePaddle(speed);
    }

    public String toString(){
        return ("java.awt.Rectangle[x=0,y=" + paddleY +
                           ",width=" + paddleWidth+",height="+paddleHeight + "]");
    }

    public void start(){
        paddleThread.start();
    }

    @Override
    public void run() {
        repaint();
        while (true){
            try {
                Thread.sleep((long) ((1/ Ball.FRAME_RATE) * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (isMoving == -1){
                movePaddleDown();
            }
            if (isMoving == 1){
                movePaddleUp();
            }
        }

    }
}