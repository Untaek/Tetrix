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
    public Main(){
        Game game = new Game();
        setContentPane(game); // KeyPanel설정

        setSize(500,500); // size
        setVisible(true); // 표시여부
        game.requestFocus(); // 권한 요청

    }



    public static void main(String[] args) {
        new Main();
    }
}
