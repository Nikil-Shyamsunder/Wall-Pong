import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class StartMenuListener extends KeyAdapter {
    StartMenu startMenu;

    public StartMenuListener(StartMenu startMenu){
        this.startMenu = startMenu;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        super.keyTyped(e);
        if (e.getKeyChar() == 's'){
            startMenu.setGameReady(true);
        }
    }
}
