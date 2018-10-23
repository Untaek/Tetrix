package untaek;

import untaek.game.Block;
import untaek.game.Game;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;


public class BasePanel extends JPanel {
    static public Game game;

    private static final int SIZE = 20;
    private static final int X = 13;
    private static final int Y = 22;
    JPanel user;
    JPanel top;
    JPanel bottom;
    static public JLabel score;
    JButton btn_start;
    JButton btn_exit;

    public BasePanel() {

        setLayout(new BorderLayout());

        user = new JPanel();
        user.setLayout(new BorderLayout());
        user.setBorder(new TitledBorder(new LineBorder(Color.black, 4), "User"));
        user.setPreferredSize(new Dimension(400, 900));

        game = new Game();
        top = new JPanel();
        bottom = new JPanel();
        add(user, BorderLayout.WEST);
        add(new PlayerPanel());

        user.add(BorderLayout.NORTH, top);
        user.add(BorderLayout.CENTER, game);
        user.add(BorderLayout.SOUTH, bottom);

        game.game();

        score = new JLabel("score");
        btn_start = new JButton("Start");
        btn_exit = new JButton("Exit");

        top.add(score);
        bottom.add(btn_start);
        bottom.add(btn_exit);

        // start 버튼 클릭시 게임 재시작  ( 테스트용 )
        btn_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setFocusable(false);
                game.timer.cancel();
                game.timer.purge();
                game.removeKeyListener(game.keyadapter);

                game.game();
            }
        });

    }
}