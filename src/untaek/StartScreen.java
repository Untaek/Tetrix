package untaek;

import untaek.Client;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JPanel {

    public StartScreen() {

        JPanel ss = new JPanel();
        JPanel lf = new JPanel();
        JLabel id = new JLabel("ID");
        JTextField tf = new JTextField(10);
        JLabel pw = new JLabel("PW");
        JPasswordField pf = new JPasswordField(10);
        JButton join = new JButton("Join");

        ss.setLayout(new BorderLayout());
        ss.setBorder(new LineBorder(Color.BLACK, 1, true));
        ss.setPreferredSize(new Dimension(500, 700));

        add(ss, BorderLayout.CENTER);
        ss.add(lf);
        lf.add(id);
        lf.add(tf);
        lf.add(pw);
        lf.add(pf);
        lf.add(join);
    }
}