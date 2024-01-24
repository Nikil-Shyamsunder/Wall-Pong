import javax.swing.*;
import java.awt.*;

public class StartMenu extends JLabel{
    private boolean isGameReady;

    public boolean isGameReady() {
        return isGameReady;
    }

    public void setGameReady(boolean gameReady) {
        isGameReady = gameReady;
    }

    public StartMenu(){
        super("Press 's' to Start!", JLabel.CENTER);
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
