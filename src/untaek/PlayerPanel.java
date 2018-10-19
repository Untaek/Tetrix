package untaek;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PlayerPanel extends JPanel {

    PlayerPanel() {

        setLayout(new GridLayout(2,3));
        setPreferredSize(new Dimension(850,1000));

        JPanel player1 = new JPanel();
        player1.setBorder(new TitledBorder(new LineBorder(Color.black),"Player 1"));
        player1.setPreferredSize(new Dimension(280, 300));
        add(player1);

        JPanel player2 = new JPanel();
        player2.setBorder(new TitledBorder(new LineBorder(Color.BLACK),"Player 2"));
        player2.setPreferredSize(new Dimension(280, 300));
        add(player2);

        JPanel player3 = new JPanel();
        player3.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Player 3"));
        player3.setPreferredSize(new Dimension(280, 300));
        add(player3);

        JPanel chat = new JPanel();
        chat.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Chat"));
        chat.setPreferredSize(new Dimension(280, 300));
        add(chat);

        JPanel player4 = new JPanel();
        player4.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Player 4"));
        player4.setPreferredSize(new Dimension(280,300));
        add(player4);

        JPanel player5 = new JPanel();
        player5.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Player 5"));
        player5.setPreferredSize(new Dimension(280, 300));
        add(player5);

    }
}
