import javax.swing.*;
import java.awt.*;

public class GameOverLabel extends JLabel{
    public GameOverLabel(int score){
        super("Game Over! Score: " + score, JLabel.CENTER);
        setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        setForeground(Color.WHITE);
    }

    public void updateBounds(Dimension frameSize){
        // Calculate the label size and position to center and show it
        Dimension labelSize = getPreferredSize();
        int x = (frameSize.width - labelSize.width) / 2;
        int y = (frameSize.height - labelSize.height) / 2;
        setBounds(x, y, labelSize.width, labelSize.height);
    }
}
