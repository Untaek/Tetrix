package untaek;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;


public class BasePanel extends JPanel {


    BasePanel() {

        setLayout(new BorderLayout());

        JPanel user = new JPanel();
        user.setLayout(new BorderLayout());
        user.setBorder(new TitledBorder(new LineBorder(Color.black, 4),"User"));
        user.setPreferredSize(new Dimension(400, 900));
        add(user, BorderLayout.WEST);

        JPanel bottom = new JPanel();
        user.add(BorderLayout.SOUTH, bottom);

        add(new PlayerPanel());

        JButton start = new JButton("Start");
        bottom.add(start);

        JButton exit = new JButton("Exit");
        bottom.add(exit);
    }
}