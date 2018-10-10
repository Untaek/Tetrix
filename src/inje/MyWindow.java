package inje;

import javax.swing.*;
import java.awt.*;

public class MyWindow extends JFrame {
    static int SIZE = 20;
    static int MAX_WIDTH = SIZE * 11;
    static int MAX_HEIGHT = SIZE * 22;
    static int WIDTH = SIZE * 10;
    static int HEIGHT = SIZE * 20;


    public MyWindow(){
        System.out.println("myWindow 생성자 함수");
        this.setTitle("My Window");
        this.setSize(MAX_WIDTH,MAX_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // EDIT
        this.setResizable(true);
        this.setVisible(true);


        this.setLocationRelativeTo(null);
    }
}
