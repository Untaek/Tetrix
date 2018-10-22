package untaek;

import untaek.game.Game;
import untaek.game.Receive_Field;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PlayerPanel extends JPanel {

    PlayerPanel() {

        setLayout(new GridLayout(2,3));
        setPreferredSize(new Dimension(850,1000));

        JPanel player1 = new JPanel();
        JLabel record1 = new JLabel("score");
        player1.setBorder(new TitledBorder(new LineBorder(Color.black),"Player 1"));
        player1.setLayout(new BorderLayout());
        player1.setPreferredSize(new Dimension(280, 300));
        add(player1);
        player1.add(BorderLayout.CENTER,new Game(1));
        player1.add(BorderLayout.NORTH, record1);

        JPanel player2 = new JPanel();
        JLabel record2 = new JLabel("score");
        player2.setBorder(new TitledBorder(new LineBorder(Color.BLACK),"Player 2"));
        player2.setLayout(new BorderLayout());
        player2.setPreferredSize(new Dimension(280, 300));
        add(player2);
        player2.add(BorderLayout.CENTER,new Game(2));
        player2.add(BorderLayout.NORTH, record2);

        JPanel player3 = new JPanel();
        JLabel record3 = new JLabel("score");
        player3.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Player 3"));
        player3.setLayout(new BorderLayout());
        player3.setPreferredSize(new Dimension(280, 300));
        add(player3);
        player3.add(BorderLayout.CENTER,new Game(3));
        player3.add(BorderLayout.NORTH, record3);

        JPanel chat = new JPanel();

        chat.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Chat"));
        chat.setPreferredSize(new Dimension(280, 300));
        add(chat);

        JPanel player4 = new JPanel();
        JLabel record4 = new JLabel("score");
        player4.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Player 4"));
        player4.setLayout(new BorderLayout());
        player4.setPreferredSize(new Dimension(280,300));
        add(player4);
        player4.add(BorderLayout.CENTER,new Game(4));
        player4.add(BorderLayout.NORTH, record4);

        JPanel player5 = new JPanel();
        JLabel record5 = new JLabel("score");
        player5.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Player 5"));
        player5.setLayout(new BorderLayout());
        player5.setPreferredSize(new Dimension(280, 300));
        add(player5);
        player5.add(BorderLayout.CENTER,new Game(5));
        player5.add(BorderLayout.NORTH, record5);
    }
}
