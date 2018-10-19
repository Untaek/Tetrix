package untaek;

import inje.Game;
import untaek.server.DB;
import untaek.server.Server;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine().trim();
        s.close();

        switch (input) {
          case "s": new Server(); break;
          case "c": new Client(); break;
          case "t1":
            Socket socket = new Socket("localhost", 8765);
            socket.getOutputStream().write(10);
            break;
          case "t2":
            ClientHandler handler = new ClientHandler(channel -> {});

            break;
          case "t3":
            Server server = new Server();
            server.setScore(null, 0);
            server.finish();
            break;
          case "db":
            new DB();
          default: break;
        }
    }
}

/*
public class Main extends JFrame {
  public Main(){
    inje.Game game = new Game();
    setContentPane(game); // KeyPanel설정

    setSize(500,500); // size
    setVisible(true); // 표시여부
    game.requestFocus(); // 권한 요청

  }



  public static void main(String[] args) {
    new Main();
  }
}*/
