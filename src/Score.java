import javax.swing.*;
import java.awt.*;

public class Score extends JLabel implements Runnable {
    private int score;
    private Thread scoreThread;
    private Dimension frameSize;

    public Score(Dimension frameSize){
        super((""), JLabel.CENTER);
        setFont(new Font(Font.MONOSPACED, Font.BOLD, 45));
        setForeground(Color.WHITE);

        this.frameSize = frameSize;

        scoreThread = new Thread(this);
    }

    public void start(){
        scoreThread.start();
    }

    public int getScore(){
        return score;
    }


    @Override
    public void run() {
        setText("" + score);
        updateBounds();
        while (true){
            //System.out.println(score);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            score++;
            setText("" + score);
            updateBounds();
        }
    }

    private void updateBounds(){
        Dimension labelSize = getPreferredSize();
        int x = (frameSize.width - labelSize.width) - 10; // 10 pixel buffer between score and side
        int y = 0;
        setBounds(x, y, labelSize.width, labelSize.height);
    }
}
