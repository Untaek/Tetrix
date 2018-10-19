package untaek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentListener;

public class Client extends JFrame {

  Client() {
    setTitle("Tetris");
    setVisible(true);
    setSize(1260, 1000);
    setResizable(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(new BasePanel());
  }
}
