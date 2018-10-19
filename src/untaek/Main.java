package untaek;

import inje.Block;
import inje.Game;
import inje.Key_Listener;
import inje.MyWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class Main extends JFrame{
    private int size = 20;
    private static int X = 13;
    private static int Y = 22;

    public Main(){
        Game game = new Game();
        System.out.println("game.block.shape : " +game.block.shape);
//        Game game1 = new Game();
//        System.out.println("game1.block.shape : " +game1.block.shape);
        setContentPane(game); // KeyPanel설정
//        setContentPane(game1);
        game.setPreferredSize(new Dimension(size * X,size * Y));
        game.setLocation(size * X, size * Y);
//        game1.setLocation(0,0);
//        game1.setPreferredSize(new Dimension(size * X,size * Y));

        setSize(500,500); // siz
        // e

        setResizable(true);
        setVisible(true); // 표시여부

        game.requestFocus(); // 권한 요청

    }

    public int getWidth(){
        return size * X;
    }

    public static void main(String[] args) {
        new Main();
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