package untaek;

import untaek.server.Packet;

import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JPanel {

    UserPanel userPanel1;
    UserPanel userPanel2;
    UserPanel userPanel3;
    UserPanel userPanel4;
    UserPanel userPanel5;
    ChatPanel chatPanel;
    PlayerPanel() {

        setLayout(new GridLayout(2,3));
        setPreferredSize(new Dimension(850,1000));

        userPanel1 = new UserPanel();
        userPanel2 = new UserPanel();
        userPanel3 = new UserPanel();
        userPanel4 = new UserPanel();
        userPanel5 = new UserPanel();
        chatPanel = new ChatPanel();


        this.add(userPanel1);
        this.add(userPanel2);
        this.add(userPanel3);
        this.add(chatPanel);
        this.add(userPanel4);
        this.add(userPanel5);

        // join 시 이벤트
        ClientHandler.getInstance().addOnJoinListener(new ClientHandler.OnJoinListener() {
            @Override
            public void on(Packet.Join packet) {
                StartScreen.users[StartScreen.usersIndex].setName(packet.getUser().getName());
                StartScreen.users[StartScreen.usersIndex].setWins(packet.getUser().getWins());
                StartScreen.users[StartScreen.usersIndex].setLoses(packet.getUser().getLoses());
                StartScreen.usersIndex++;
            }
        });

        ClientHandler.getInstance().addOnLeaveListener(new ClientHandler.OnLeaveListener() {
            @Override
            public void on(Packet.Leave packet) {
                //

                int i = 0;
                boolean flag = false;
                for(Packet.UserStatus user : StartScreen.users){
                    if(user.getId()==packet.getId()){
                        flag = true;
                        if(i== 0){
                            StartScreen.owner = packet.getNextOwner();
                        }
                    }
                    if(flag || i+1 <5){
                        StartScreen.users[i] = StartScreen.users[i+1];
                    }
                    i++;
                }
            }
        });
    }
}
