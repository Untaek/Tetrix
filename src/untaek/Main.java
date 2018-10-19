package untaek;

import untaek.game.MyWindow;

public class Main {
    public static void main(String[] args) {
        new MyWindow();
    }
}


//package untaek;
//
//import untaek.server.DB;
//import untaek.server.Server;
//
//import java.io.IOException;
//import java.net.Socket;
//import java.util.Scanner;
//
//public class Main {
//
//    public static void main(String[] args) throws IOException {
//        Scanner s = new Scanner(System.in);
//        String input = s.nextLine().trim();
//        s.close();
//
//        switch (input) {
//            case "s": new Server(); break;
//            case "c": new Client(); break;
//            case "t1":
//                Socket socket = new Socket("localhost", 8765);
//                socket.getOutputStream().write(10);
//                break;
//            case "t2":
//                ClientHandler handler = new ClientHandler(channel -> {});
//
//                break;
//            case "t3":
//                Server server = new Server();
//                server.setScore(null, 0);
//                server.finish();
//                break;
//            case "db":
//                new DB();
//            default: break;
//        }
//    }
//}