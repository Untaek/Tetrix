package untaek.game;

import untaek.BasePanel;

import javax.swing.*;
import java.awt.*;

public class MyWindow extends JFrame {


    public static int SIZE = 20;
    private static int X = 13;
    private static int Y = 22;

    static int MAX_WIDTH = SIZE * 11;
    static int MAX_HEIGHT = SIZE * 22;
    static int WIDTH = SIZE * 10;
    static int HEIGHT = SIZE * 20;

    Game game ;


    public MyWindow(){
        setSize(500,500);       // JFrame size
        setResizable(false);
        this.setLocationRelativeTo(null);   // 모니터 가운데 띄우기
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     // 폼 종료시 프로그램 종료

        game = new Game(0);
        game.setPreferredSize(new Dimension(200, 200));
        game.setLayout(null);
        System.out.println("game.block.shape : " +game.block.shape);
        //setContentPane(game); // KeyPanel설정

        add(game);
        game.requestFocus();                // 키 입력 포커스 요청
        setVisible(true);                   // JFrame 창 띄우기
    }

}
