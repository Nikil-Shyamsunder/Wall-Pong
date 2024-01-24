import javax.swing.*;
import java.awt.*;
import java.lang.Math;
import java.sql.SQLOutput;

public class Ball extends JPanel implements Runnable{
    public static String ballBounceSoundFilePath = "src/assets/hit.wav";
    public static final Color BALL_COLOR = Color.WHITE;
    public static final double FRAME_RATE = 60.0;
    private int ballSideLength; // ball is a square
    private double ballX;
    private double ballY;
    private double baseBallSpeed;
    private double ballSpeed;
    private UnitVector directionVector;
    private Thread ballThread;
    private int panelHeight;
    private int panelWidth;
    private Paddle paddle;

    public Thread getBallThread() {
        return ballThread;
    }



    public Ball(int ballX, int ballY, int ballSideLength, double baseBallSpeed, int panelSideLength, Paddle paddle) {
        this.ballX = ballX;
        this.ballY = ballY;
        this.ballSideLength = ballSideLength;
        this.baseBallSpeed = baseBallSpeed;
        this.ballSpeed = baseBallSpeed; // Ball speed is initally the base speed
        this.panelHeight = panelSideLength - 30; // to account for 30 px of nav bar;
        this.panelWidth = panelSideLength;
        this.paddle = paddle;

        // randomly generate initial direction
        int randomAngle = ((int) (Math.random() * 90) + 1);
        if (Math.random() < 0.5){ // 50% chance of negating the angle, to create greater angle range
            randomAngle *= -1;
        }
        //directionVector = new UnitVector(randomAngle);
        directionVector = new UnitVector(1, 0);

        updateBounds(); // sets bounds initially

        ballThread = new Thread(this);
    }

    private void updateBounds() {
        //System.out.println(ballX + ", " + ballY);
        setBounds((int)ballX,(int)ballY, ballSideLength, ballSideLength);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(BALL_COLOR); // Set ball color
        g.fillRect(0, 0, ballSideLength, ballSideLength);
    }

    public void start(){
        ballThread.start();
    }

    @Override
    public void run() {
        double xChange = directionVector.getX() * ballSpeed;
        double yChange = directionVector.getY() * ballSpeed;
        while (true){
            if (isRightWallCollision()){
                directionVector.setX(-directionVector.getX());
                SoundEffectPlayer soundEffectPlayer = new SoundEffectPlayer(ballBounceSoundFilePath); // play bounce sound
                baseBallSpeed = baseBallSpeed +0.1;
            }
            if (isTopAndBottomWallCollision()){
                directionVector.setY(-directionVector.getY());
                SoundEffectPlayer soundEffectPlayer = new SoundEffectPlayer(ballBounceSoundFilePath); // play bounce sound
                baseBallSpeed = baseBallSpeed + 0.1;
            }
            if (isPaddleCollision()){
                // factor by which to change return angle determined by relative distance
                // of ball from paddles center
                double factor = distanceFromCenter() / (paddle.getPaddleHeight() / 2);
                ballSpeed = baseBallSpeed + Math.abs(baseBallSpeed * factor * 0.75);

                // Note that negation is done to make the paddle go down/up depending on
                // if it hits the upper/lower half of the paddle respectively
                int angle = (int) -(factor * 90);
                directionVector.setByAngle(angle);
                //directionVector.setX(-directionVector.getX());
                SoundEffectPlayer soundEffectPlayer = new SoundEffectPlayer(ballBounceSoundFilePath); // play bounce sound
                baseBallSpeed = baseBallSpeed + 0.1;
            }
            if (isLeftWallCollision()){
                break; // loop is over, and thus game is over.
            }

            // recalculate the distance to move
            xChange = directionVector.getX() * ballSpeed;
            yChange = directionVector.getY() * ballSpeed;

            try {
                Thread.sleep((long) ((1/FRAME_RATE) * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ballX += xChange;
            ballY += yChange;
            updateBounds();
        }
    }

    private boolean isRightWallCollision() {
        return ballX + ballSideLength > panelWidth;
    }

    private boolean isLeftWallCollision(){
        return ballX + ballSideLength < 0;
    }

    private boolean isTopAndBottomWallCollision() {
        return ballY < 0 || ballY + ballSideLength > panelHeight;
    }

    private boolean isPaddleCollision() {
        return ((ballX > 0 && ballX < paddle.getPaddleWidth())
                && ((ballY > paddle.getPaddleY()) &&
                (ballY <  paddle.getPaddleY() + paddle.getPaddleHeight())));
    }

    /* Calculates distance of the ball from the center of the paddle on collision*/
    private double distanceFromCenter(){
        double paddleCenterCoordinate = paddle.getPaddleY() + paddle.getPaddleHeight()/2;
        return paddleCenterCoordinate - ballY;
    }
}
