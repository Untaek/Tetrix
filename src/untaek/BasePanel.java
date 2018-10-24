package untaek;

import untaek.game.Game;
import untaek.server.Packet;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BasePanel extends JPanel {




    static public MyPanel myPanel;
    static public PlayerPanel playerPanel;

    public BasePanel() {
        setLayout(new BorderLayout());

        add(myPanel = new MyPanel(StartScreen.myUser.getName(),StartScreen.myUser.getWins(),StartScreen.myUser.getLoses()), BorderLayout.WEST);
        add(playerPanel = new PlayerPanel());

        





        // startPacket receive ( client <- server )
        // 게임 시작

        // 게임 시작 listenr

    }

    //start button listener

}