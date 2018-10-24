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
    static public Game game = null;

    private static final int SIZE = 20;
    private static final int X = 13;
    private static final int Y = 22;
    JPanel user;
    JPanel top;
    JPanel bottom;
    JPanel panelGame;
    JLabel labelID;
    JLabel labelRecord;
    JButton btnReady;
    JButton btnRestart;

    public BasePanel() {

        user = new JPanel();
        top = new JPanel();
        bottom = new JPanel();
        panelGame = new JPanel();
        labelID = new JLabel("ID");
        labelRecord = new JLabel("Record");
        btnReady = new JButton("Start");
        btnRestart = new JButton("Restart");


        setLayout(new BorderLayout());
        user.setLayout(new BorderLayout());
        user.setBorder(new TitledBorder(new LineBorder(Color.black, 4), "User"));
        user.setPreferredSize(new Dimension(400, 900));

        btnReady.setFocusable(false);
        btnRestart.setFocusable(false);


        setLayout(new BorderLayout());
        user.setLayout(new BorderLayout());
        user.setBorder(new TitledBorder(new LineBorder(Color.black, 4), "User"));
        user.setPreferredSize(new Dimension(400, 900));

        game = new Game();
        user.add(BorderLayout.CENTER, game);
        game.init();
        game.setFocusable(true);

        add(user, BorderLayout.WEST);
        add(new PlayerPanel());
        user.add(BorderLayout.NORTH, top);
        user.add(BorderLayout.CENTER, game);
        user.add(BorderLayout.SOUTH, bottom);

        btnReady.setFocusable(false);
        btnRestart.setFocusable(false);

        top.add(BorderLayout.CENTER,labelID);
        top.add(BorderLayout.SOUTH,labelRecord);
        bottom.add(btnReady);
        bottom.add(btnRestart);


        // startPacket receive ( client <- server )
        // 게임 시작

        // 게임 시작 listenr
        btnReady.addActionListener(startActionListener);
    }

    //start button listener
    ActionListener startActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //startPacket send  (client -> server)

            //
            if(game.gameFlag == false) {
                game.gameFlag = true;
                game.start();

            }else{// 한번더 누르면 다시시작
                game.timer.cancel();
                game.timer.purge();
                game.removeKeyListener(game.keyadapter);
                game.game();
            }
        }
    };
}