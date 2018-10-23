package untaek;

import untaek.game.ReceiveField;

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
        JPanel player4 = new JPanel();
        JPanel player5 = new JPanel();
        JPanel chat = new JPanel();
        JPanel chatBottom = new JPanel();

        JLabel record1 = new JLabel("score");
        JLabel record2 = new JLabel("score");
        JLabel record3 = new JLabel("score");
        JLabel record4 = new JLabel("score");
        JLabel record5 = new JLabel("score");
        JTextField fieldTxt = new JTextField();
        JTextField sendTxt = new JTextField();
        JButton sendBtn = new JButton("SEND");

        player1.setBorder(new TitledBorder(new LineBorder(Color.black),"Player 1"));
        player2.setBorder(new TitledBorder(new LineBorder(Color.BLACK),"Player 2"));
        player3.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Player 3"));
        player4.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Player 4"));
        player5.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Player 5"));
        chat.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Chat"));

        player1.setLayout(new BorderLayout());
        player2.setLayout(new BorderLayout());
        player3.setLayout(new BorderLayout());
        player4.setLayout(new BorderLayout());
        player5.setLayout(new BorderLayout());
        chat.setLayout(new BorderLayout());
        chatBottom.setLayout(new BorderLayout());

        player1.setPreferredSize(new Dimension(280, 300));
        player2.setPreferredSize(new Dimension(280, 300));
        player3.setPreferredSize(new Dimension(280, 300));
        player4.setPreferredSize(new Dimension(280,300));
        player5.setPreferredSize(new Dimension(280, 300));
        chat.setPreferredSize(new Dimension(280, 300));

        fieldTxt.setFocusable(false);
        sendBtn.setFocusable(false);

        add(player1);
        add(player2);
        add(player3);
        add(chat);
        add(player4);
        add(player5);

        player1.add(BorderLayout.CENTER,new ReceiveField(1));
        player2.add(BorderLayout.CENTER,new ReceiveField(2));
        player3.add(BorderLayout.CENTER,new ReceiveField(3));
        player4.add(BorderLayout.CENTER,new ReceiveField(4));
        player5.add(BorderLayout.CENTER,new ReceiveField(5));
        player1.add(BorderLayout.NORTH, record1);
        player2.add(BorderLayout.NORTH, record2);
        player3.add(BorderLayout.NORTH, record3);
        player4.add(BorderLayout.NORTH, record4);
        player5.add(BorderLayout.NORTH, record5);
        chat.add(BorderLayout.CENTER, fieldTxt);
        chat.add(BorderLayout.SOUTH,chatBottom);
        chatBottom.add(BorderLayout.CENTER, sendTxt);
        chatBottom.add(BorderLayout.EAST, sendBtn);
    }
}
