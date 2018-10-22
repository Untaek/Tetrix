package untaek;

import untaek.game.Block;
import untaek.game.Game;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;


public class BasePanel extends JPanel {
    Game game;
    Block block_new;
    private static int SIZE = 20;
    private static int X = 13;
    private static int Y = 22;

    public BasePanel() {

        setLayout(new BorderLayout());

        JPanel user = new JPanel();
        user.setLayout(new BorderLayout());
        user.setBorder(new TitledBorder(new LineBorder(Color.black, 4),"User"));
        user.setPreferredSize(new Dimension(400, 900));

        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        add(user, BorderLayout.WEST);
        add(new PlayerPanel());

        user.add(BorderLayout.NORTH, top);
        user.add(BorderLayout.CENTER,new Game(0));
        user.add(BorderLayout.SOUTH, bottom);



        JLabel record = new JLabel("record");
        JButton start = new JButton("Start");
        JButton exit = new JButton("Exit");

        top.add(record);
        bottom.add(start);
        bottom.add(exit);

    }

//    public void paintComponent(Graphics g){
//
//        super.paintComponent(g);
//        repaint();
//        invalidate();
//
//    }
}