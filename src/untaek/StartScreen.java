package untaek;

import untaek.server.Packet;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JPanel {

    BasePanel basePanel;

    public StartScreen() {

        JPanel panelBody = new JPanel();
        JPanel panelLogin = new JPanel();

        JLabel labelID = new JLabel("ID");
        JLabel labelPW = new JLabel("PW");
        JTextField textID = new JTextField(10);
        JPasswordField textPW = new JPasswordField(10);
        JButton btnLogin = new JButton("Join");

        panelBody.setLayout(new BorderLayout());
        panelBody.setBorder(new LineBorder(Color.BLACK, 1, true));
        panelBody.setPreferredSize(new Dimension(1000, 750));

        add(panelBody, BorderLayout.CENTER);

        panelBody.add(panelLogin, BorderLayout.NORTH);
        panelBody.add(btnLogin);
        panelLogin.add(labelID);
        panelLogin.add(textID);
        panelLogin.add(labelPW);
        panelLogin.add(textPW);



//        join.addActionListener(e -> ClientHandler.getInstance().login(id.getText(), pw.getText()));

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textID.getText().equals("1")){
                    // login 성공
                    btnLogin.setFocusable(false);
                    textID.setFocusable(false);
                    textPW.setFocusable(false);
                    setVisible(false);
                    Main.client.add(basePanel = new BasePanel());
                }else{
                    //login 실패
                }




            }
        });

        //login packet 보내기
        //ClientHandler.getInstance().login(textID.getText(), textPW.getPassword().toString());

        // login result packet 받기
        ClientHandler.getInstance().addOnLoginResultListener(packet -> {
            if(packet.getStatus() == Packet.LoginResult.SUCCESS) {
                // 성공 넘어가
                 //packet.getMe().getLoses();
            }
            else {
                //실패 dialog 띄우기

            }
        });

    }
}
