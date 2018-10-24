package untaek;

import untaek.server.Packet;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JPanel {
    static public  int usersIndex;
    static public BasePanel basePanel;
    static public Packet.UserStatus myUser; // names /loses/ wins
    static public int myUserID;
    static public int myUserGameID;
    static public Packet.UserStatus[] users;
    static public int owner;

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

        // login result packet 받기
        ClientHandler.getInstance().addOnLoginResultListener(packet -> {
            if(packet.getStatus()== Packet.LoginResult.SUCCESS) {
                usersIndex=0;

                myUser = new Packet.UserStatus(packet.getMe().getId(),packet.getMe().getName(), packet.getMe().getWins(),packet.getMe().getLoses());
                myUserID = packet.getId();
                myUserGameID = packet.getGameId();

                users = new Packet.UserStatus[5];
                for(int i = 0; i< 5; i++){
                    users[i] = null;
                }
                for(Packet.UserStatus user : packet.getUsers()){
                    if(user.getName().equals(myUser.getName())) {
                        // 같을때는 user에 저장 x
                    }else {
                        users[usersIndex].setName(user.getName());
                        users[usersIndex].setWins(user.getWins());
                        users[usersIndex].setLoses(user.getLoses());
                        usersIndex++;
                    }
                }
                owner = packet.getOwner();

                // 기존의 StartScreen component 삭제
                btnLogin.setFocusable(false);
                textID.setFocusable(false);
                textPW.setFocusable(false);
                setVisible(false);

                //  basePanel 생성
                Main.client.add(basePanel = new BasePanel());

                // 내 정보 출력
                basePanel.myPanel.labelID.setText(packet.getMe().getName());

                basePanel.myPanel.labelRecord.setText(
                        (packet.getMe().getLoses()+packet.getMe().getWins()) +"전 "+packet.getMe().getWins()+"승 "
                                +packet.getMe().getLoses()+"패");

            }
            else {
                //실패 dialog 띄우기
                JOptionPane.showMessageDialog(null, "try again!");
                textID.setText("");
                textPW.setText("");
            }
        });


//        join.addActionListener(e -> ClientHandler.getInstance().login(id.getText(), pw.getText()));

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //login packet 보내기
//                ClientHandler.getInstance().login(textID.getText(),String.valueOf(textPW.getPassword()));

                if(textID.getText().equals("1")){   // result 잘왓을때 (로긘 성공)
                    // login 성공
                    myUser = new Packet.UserStatus(333, "inje",30,20);
                    users = new Packet.UserStatus[2];
                    users[0] = new Packet.UserStatus(222, "untaek", 11, 20);
                    users[1] = new Packet.UserStatus(111, "Taeyang", 9, 2);

                    btnLogin.setFocusable(false);
                    textID.setFocusable(false);
                    textPW.setFocusable(false);
                    setVisible(false);
                    Main.client.add(basePanel = new BasePanel());

                }else{                              // result 잘 안왔을때  or 로그인 실패
                    //login 실패
                    JOptionPane.showMessageDialog(null, "try again!");
                }
            }
        });





    }
}
