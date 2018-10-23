package untaek;

import untaek.server.DB;
import untaek.server.Server;
import untaek.game.MyWindow;

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
                handler.login("asaadsf", "324f24");
                break;
            case "t3":
                Server server = new Server();
                server.setScore(null, 0);
                server.finish();
                break;
            case "db":
                new DB();
                break;
            case "game":
              new MyWindow();
            default: break;
        }
    }
}