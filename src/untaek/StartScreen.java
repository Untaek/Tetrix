package untaek;

import untaek.server.Packet;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JPanel {

    public StartScreen() {

        JPanel ss = new JPanel();
        ss.setLayout(new BorderLayout());
        ss.setBorder(new LineBorder(Color.BLACK, 1, true));
        ss.setPreferredSize(new Dimension(1000, 750));
        add(ss, BorderLayout.CENTER);

        JPanel lf = new JPanel();

        JLabel id = new JLabel("ID");
        JTextField tf = new JTextField(10);
        JLabel pw = new JLabel("PW");
        JPasswordField pf = new JPasswordField(10);
        JButton join = new JButton("Join");

        ss.add(lf, BorderLayout.NORTH);
        ss.add(join);

        lf.add(id);
        lf.add(tf);
        lf.add(pw);
        lf.add(pf);



//        join.addActionListener(e -> ClientHandler.getInstance().login(id.getText(), pw.getText()));

        join.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lf.setVisible(false);
                join.setVisible(false);
                ss.add(new BasePanel());
            }
        });


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
