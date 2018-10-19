package untaek.game;

import javax.swing.*;
import java.awt.*;

public class MyWindow extends JFrame {


    private static int SIZE = 20;
    private static int X = 13;
    private static int Y = 22;

    static int MAX_WIDTH = SIZE * 11;
    static int MAX_HEIGHT = SIZE * 22;
    static int WIDTH = SIZE * 10;
    static int HEIGHT = SIZE * 20;



    public MyWindow(){
        Game game = new Game();
        System.out.println("game.block.shape : " +game.block.shape);
//        Game game1 = new Game();
//        System.out.println("game1.block.shape : " +game1.block.shape);
        setContentPane(game); // KeyPanel설정
//        setContentPane(game1);
        game.setPreferredSize(new Dimension(SIZE * X,SIZE * Y));
        game.setLocation(SIZE * X, SIZE * Y);
//        game1.setLocation(0,0);
//        game1.setPreferredSize(new Dimension(size * X,size * Y));

        setSize(500,500); // siz
        // e

        setResizable(true);
        setVisible(true); // 표시여부

        game.requestFocus(); // 권한 요청
        this.setLocationRelativeTo(null);
    }
    public int getWidth(){
        return SIZE * X;
    }
}
