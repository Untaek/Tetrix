package untaek.game;

import untaek.BasePanel;

import javax.swing.*;
import java.awt.*;

public class ReceiveField extends JPanel {
    private static final int ROWS = 25;   // 20 + 1 + 4
    private static final int COLUMNS = 12; // 10 + 2
    private static final int OTHER_MARGIN_X = 25;                 // othter player field's margin X
    private static final int OTHER_MARGIN_Y = 25;                 // othter player field's margin Y
    private static final int SIZE = 22;

    private int [][] fieldNum = null;
    private int [][] fieldColor = null;


    public ReceiveField(int[][] num, int[][] color){


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // server로부터 field 전달받음
        drawOthersFieldBorder(g);
        drawOthersField(g, fieldNum, fieldColor);

        repaint();
        invalidate();
    }

    // draw other Field's Border
    public void drawOthersFieldBorder(Graphics g){
        g.setColor(Color.BLACK);

        // top border
        g.fillRect(SIZE/4 +OTHER_MARGIN_X, SIZE/4+OTHER_MARGIN_Y, (COLUMNS -1) *(SIZE/2),SIZE/4);
        g.drawRect(SIZE/4+OTHER_MARGIN_X, SIZE/4+OTHER_MARGIN_Y, (COLUMNS -1) *(SIZE/2),SIZE/4);

        // bottom border
        g.fillRect(SIZE/4+OTHER_MARGIN_X, (SIZE/2) * (ROWS -4)+OTHER_MARGIN_Y, (COLUMNS -1) *(SIZE/2),SIZE/4);
        g.drawRect(SIZE/4+OTHER_MARGIN_X, (SIZE/2) * (ROWS -4)+OTHER_MARGIN_Y, (COLUMNS -1) *(SIZE/2),SIZE/4);

        // left border
        g.fillRect(SIZE/4+OTHER_MARGIN_X, SIZE/4+OTHER_MARGIN_Y, SIZE/4, (ROWS -4)*(SIZE/2));
        g.drawRect(SIZE/4+OTHER_MARGIN_X, SIZE/4+OTHER_MARGIN_Y, SIZE/4, (ROWS -4)*(SIZE/2));

        // right border
        g.fillRect((COLUMNS -1) *(SIZE/2)+OTHER_MARGIN_X, SIZE/4+OTHER_MARGIN_Y, SIZE/4, (ROWS -4)*(SIZE/2));
        g.drawRect((COLUMNS -1) *(SIZE/2)+OTHER_MARGIN_X, SIZE/4+OTHER_MARGIN_Y, SIZE/4, (ROWS -4)*(SIZE/2));
    }

    // draw ohter Field
    public void drawOthersField(Graphics g, int[][] fieldNum, int[][] fieldColor){
        for(int x = 1; x < COLUMNS -1; x++){     // 0~ 11
            for (int y = 4; y < ROWS -1; y++){   // 4~ 24
                if(fieldNum[y][x] == 1){
                    switch (fieldColor[y][x]) {
                        case -2:    // Gray
                            g.setColor(Color.gray);
                            break;
                        case -1:    // Black
                            g.setColor(Color.BLACK);
                            break;
                        case 0:     //  White
                            g.setColor(Color.WHITE);
                            break;
                        case 1:     //  Red (255,0,0)
                            g.setColor(Color.RED);
                            break;
                        case 2:     //  Yellow (255, 212, 0)
                            g.setColor(Color.YELLOW);
                            break;
                        case 3:     //  Blue    (0, 153, 255)
                            g.setColor(Color.BLUE);
                            break;
                        case 4:     // Green    (0, 153, 0)
                            g.setColor(Color.GREEN);
                            break;
                        case 5:     //  Pink    (255, 144, 190)
                            g.setColor(Color.PINK);
                            break;
                        case 6:     //  Purple  (128, 0, 255)
                            g.setColor(new Color(128, 0, 255));
                            break;
                        case 7:     //  Orange  (255, 127, 0)
                            g.setColor(Color.ORANGE);
                            break;
                    }
                    g.fillRect((SIZE/2) * x+OTHER_MARGIN_X, SIZE/2 + (SIZE/2) * (y-4)+OTHER_MARGIN_Y, SIZE/2,SIZE/2);
                    g.setColor(Color.BLACK);
                    g.drawRect((SIZE/2) * x+OTHER_MARGIN_X, SIZE/2 + (SIZE/2) * (y-4)+OTHER_MARGIN_Y, SIZE/2,SIZE/2);
                }
            }
        }
    }
}
