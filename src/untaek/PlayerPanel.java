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
        JPanel player2 = new JPanel();
        JPanel player3 = new JPanel();
        JPanel chat = new JPanel();
        JPanel player4 = new JPanel();
        JPanel player5 = new JPanel();

        JLabel record1 = new JLabel("score");
        JLabel record2 = new JLabel("score");
        JLabel record3 = new JLabel("score");
        JLabel record4 = new JLabel("score");
        JLabel record5 = new JLabel("score");

        player1.setBorder(new TitledBorder(new LineBorder(Color.black),"Player 1"));
        player2.setBorder(new TitledBorder(new LineBorder(Color.BLACK),"Player 2"));
        player3.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Player 3"));
        chat.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Chat"));
        player4.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Player 4"));
        player5.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Player 5"));

        player1.setLayout(new BorderLayout());
        player2.setLayout(new BorderLayout());
        player3.setLayout(new BorderLayout());
        player4.setLayout(new BorderLayout());
        player5.setLayout(new BorderLayout());

        player1.setPreferredSize(new Dimension(280, 300));
        player2.setPreferredSize(new Dimension(280, 300));
        player3.setPreferredSize(new Dimension(280, 300));
        chat.setPreferredSize(new Dimension(280, 300));
        player4.setPreferredSize(new Dimension(280,300));
        player5.setPreferredSize(new Dimension(280, 300));

        add(player1);
        add(player2);
        add(player3);
        add(chat);
        add(player4);
        add(player5);

        player1.add(BorderLayout.CENTER,new Receive_Field(1));
        player2.add(BorderLayout.CENTER,new Receive_Field(2));
        player3.add(BorderLayout.CENTER,new Receive_Field(3));
        player4.add(BorderLayout.CENTER,new Receive_Field(4));
        player5.add(BorderLayout.CENTER,new Receive_Field(5));

        player1.add(BorderLayout.NORTH, record1);
        player2.add(BorderLayout.NORTH, record2);
        player3.add(BorderLayout.NORTH, record3);
        player4.add(BorderLayout.NORTH, record4);
        player5.add(BorderLayout.NORTH, record5);
    }
}
