package untaek;

import javax.swing.*;

public class Client extends JFrame {
  static int WIDTH = 1000;
  static int HEIGHT = 750;

  Client() {
    setTitle("Tetris");
    setSize(WIDTH, HEIGHT);
    setResizable(false);

    add(new StartScreen());
    setVisible(true);
    this.setLocationRelativeTo(null);   // 모니터 가운데 띄우
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     // 폼 종료시 프로그램 종료
  }
}
