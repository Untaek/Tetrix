package untaek;

import untaek.server.Packet;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class StartScreen extends JPanel {

    public StartScreen() {

        JPanel ss = new JPanel();
        ss.setLayout(new BorderLayout());
        ss.setBorder(new LineBorder(Color.BLACK, 1, true));
        ss.setPreferredSize(new Dimension(500, 700));
        add(ss, BorderLayout.CENTER);

        JPanel lf = new JPanel();
        ss.add(lf, BorderLayout.NORTH);

        JLabel id = new JLabel("ID");
        lf.add(id);

        JTextField tf = new JTextField(10);
        lf.add(tf);

        JLabel pw = new JLabel("PW");
        lf.add(pw);

        JPasswordField pf = new JPasswordField(10);
        lf.add(pf);

        JButton join = new JButton("Join");

        ss.add(join);

        join.addActionListener(e -> ClientHandler.getInstance().login(id.getText(), pw.getText()));

        ClientHandler.getInstance().addOnLoginResultListener(packet -> {
            if(packet.getStatus() == Packet.LoginResult.SUCCESS) {
                // 성공 넘어가
                 //lose = packet.getMe().getLoses();
            }
            else {
                //실패 알아서


            }
        });

    }
}
