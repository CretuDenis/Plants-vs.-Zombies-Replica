import Control.LoginControl;
import Model.LoginModel;
import Model.User;
import View.LoginView;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException, URISyntaxException, SQLException {
        LoginControl loginPanel = new LoginControl(new LoginView(),new LoginModel());
        User currentUser = loginPanel.waitForLogin();

        JFrame gameFrame = new JFrame("Plants vs. Zombies");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.setLocationRelativeTo(null);
        GamePanel gamePanel = new GamePanel(currentUser);
        gamePanel.setIcon("logo",gameFrame);
        gameFrame.add(gamePanel);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);

        gamePanel.run();    
    }
}
