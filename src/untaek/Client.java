package untaek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Client extends JFrame {
  static int FIRST_WIDTH = 1000;
  static int FIRST_HEIGHT = 750;
  BasePanel basePanel;

  Client() {
    setTitle("Tetris");
    setSize(FIRST_WIDTH, FIRST_HEIGHT);
    setResizable(true);
    add(basePanel = new BasePanel());

    basePanel.requestFocus();


    setVisible(true);
    this.setLocationRelativeTo(null);   // 모니터 가운데 띄우기
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     // 폼 종료시 프로그램 종료
  }
}
