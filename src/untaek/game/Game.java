package untaek.game;

import untaek.BasePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.Timer;

public class Game extends JPanel {
    Random rand;

    static final int ROWS = 25;   // 20 + 1 + 4
    static final int COLUMNS = 12; // 10 + 2

    static final int SIZE = 22;
    static final int MAX_HEIGHT = SIZE * (ROWS + 2);
    static final int MAX_WIDTH = SIZE * (COLUMNS + 1);
    static final int HEIGHT = SIZE * (ROWS - 5);
    static final int WIDTH = SIZE * (COLUMNS - 2);
    static final int MARGIN_X = 13 * SIZE;
    static final int MARGIN_Y = 5 * SIZE;

    public int score;

    static int OTHER_MARGIN_X = 25;                 // othter player field's margin X
    static int OTHER_MARGIN_Y = 25;                 // othter player field's margin Y

    Box[][] save_field = new Box[ROWS][COLUMNS];   // field's back up field
    Box[][] field = new Box[ROWS][COLUMNS];        // main field
    Box[][] field_ = new Box[ROWS][COLUMNS];       // contain block

    int[][] sendNum = new int[20][10];
    int[][] sendColor = new int[20][10];

    static public Box[][] send_field = new Box[20][10];

    public Block block;                             // now block
    public Block block_pre_1;                       // next block
    public Block block_pre_2;                       // next next block
    public Block block_pre_3;                       // next next next block
    public Block block_pre_4;                       // next // next next next block

    public boolean fall_complete;                   // block fall complete flag
    public boolean fall_block_result;               // fallBlock() method result value
    public boolean timer_flag = true;               // normal or down(keyboard) fall block flag
    public boolean gameover_flag;           // game over flag
    public boolean attack_flag = false;

    public int combo = 0;
    public int delay = 500;                         // normal block fall delay (0.5s)
    public int exploded_block;                        // my attack point

    public Timer timer;
    public TimerTask task;

    Queue<Integer> queue;
    Thread thread;
    Color gray = new Color(210, 209, 208);

    public static KeyAdapter keyadapter;

    public void game() {
        // 초기화
        rand = new Random();
        Arrays.fill(Block.bag,false);
        queue = new LinkedList<>();
        score = 0;
        gameover_flag = false;

        resetField(field);
        resetField(save_field);
        resetField_();

        keyadapter = new MyKeyAdapter();
        this.addKeyListener(keyadapter);

        block = new Block();
        block_pre_1 = new Block();
        block_pre_2 = new Block();
        block_pre_3 = new Block();
        block_pre_4 = new Block();

        // field_ 에 block 넣기
        fillField_();

        // field  = field + field_
        fillField();

        task = new TimerTask() {
            @Override
            public void run() {
                // object 떨어짐
                fallBlock();
            }
        };
        timer = new Timer();
        timer.schedule(task, 0, delay);

        thread = new Thread(runnable);
        thread.start();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        if (gameover_flag) {
            gameOverEffect();
        }
        // my game
        drawPreview(g);
        drawField(g);
        drawFieldBorder(g);

        if (attack_flag) {
            //send attack_point to server
        }

        BasePanel.score.setText(String.valueOf(score));
        // send field to server

        repaint();
        invalidate();
    }

    public void sendFieldSetting() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 20; y++) {
                sendNum[y][x] = field[y + 4][x + 1].num;
                sendColor[y][x] = field[y + 4][x + 1].color;
            }
        }
    }

    public void beAttacked(int beAttackedPoint){
        int blank;
        for(int x=1; x< COLUMNS -1; x++){
            for(int y = 4; y<ROWS-1; y++){
                field[y-beAttackedPoint][x].num = field[y][x].num;
                field[y-beAttackedPoint][x].color = field[y][x].color;
            }
        }

        for(int y = ROWS-2; y>ROWS-2-beAttackedPoint; y--){
            blank = rand.nextInt(10)+1;
            for(int x = 1; x<COLUMNS-1; x++) {
                if(x != blank) {
                    field[y][x].num = 1;
                    field[y][x].color = -2;
                }else{
                    field[y][x].num = 0;
                    field[y][x].color = 0;
                }
            }

        }
    }

    // draw my Field's Border
    public void drawFieldBorder(Graphics g) {
        g.setColor(Color.BLACK);

        // top border
        g.fillRect(SIZE / 2, 0, (COLUMNS - 1) * SIZE, SIZE / 2);
        g.drawRect(SIZE / 2, 0, (COLUMNS - 1) * SIZE, SIZE / 2);

        // bottom border
        g.fillRect(SIZE / 2, SIZE * (ROWS - 4) - SIZE / 2, (COLUMNS - 1) * SIZE, SIZE / 2);
        g.drawRect(SIZE / 2, SIZE * (ROWS - 4) - SIZE / 2, (COLUMNS - 1) * SIZE, SIZE / 2);

        // left border
        g.fillRect(SIZE / 2, 0, SIZE / 2, (ROWS - 4) * SIZE);
        g.drawRect(SIZE / 2, 0, SIZE / 2, (ROWS - 4) * SIZE);

        // right border
        g.fillRect((COLUMNS - 1) * SIZE, 0, SIZE / 2, (ROWS - 4) * SIZE);
        g.drawRect((COLUMNS - 1) * SIZE, 0, SIZE / 2, (ROWS - 4) * SIZE);
    }

    // draw my Field
    public void drawField(Graphics g) {
        // 눈금
        for (int x = 1; x < COLUMNS - 1; x++) {     // 0~ 11
            for (int y = 4; y < ROWS - 1; y++) {   // 4~ 24
                g.setColor(gray);
                g.drawRect(SIZE * x, SIZE / 2 + SIZE * (y - 4), SIZE, SIZE);
            }
        }

        for (int x = 1; x < COLUMNS - 1; x++) {     // 0~ 11
            for (int y = 4; y < ROWS - 1; y++) {   // 4~ 24
                if (field[y][x].num == 1) {
                    switch (field[y][x].color) {
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
                    g.fillRect(SIZE * x, SIZE / 2 + SIZE * (y - 4), SIZE, SIZE);
                    g.setColor(Color.BLACK);
                    g.drawRect(SIZE * x, SIZE / 2 + SIZE * (y - 4), SIZE, SIZE);
                }

            }
        }
    }

    // draw my Preview
    public void drawPreview(Graphics g) {
        drawBlock(g, 1);    // pre1
        drawBlock(g, 2);    // pre2
        drawBlock(g, 3);    // pre3
        drawBlock(g, 4);    // pre4
    }

    // darw my Block
    public void drawBlock(Graphics g, int block_num) {

        Block block_pre = null;
        int margin_y = 0;
        switch (block_num) {
            case 1:
                block_pre = block_pre_1;
                margin_y = 0;
                break;
            case 2:
                block_pre = block_pre_2;
                margin_y = 5;
                break;
            case 3:
                block_pre = block_pre_3;
                margin_y = 10;
                break;
            case 4:
                block_pre = block_pre_4;
                margin_y = 15;
                break;
        }

        for (int x = 0; x < block_pre.area_length; x++) {     // 0~ 11
            for (int y = 0; y < block_pre.area_length; y++) {   // 4~ 24
                if (block_pre.area[y][x].num == 1) {
                    switch (block_pre.area[y][x].color) {
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

                    g.fillRect(SIZE / 2 + SIZE * x + MARGIN_X, SIZE / 2 + SIZE * (y + margin_y), SIZE, SIZE);
                    g.setColor(Color.BLACK);
                    g.drawRect(SIZE / 2 + SIZE * x + MARGIN_X, SIZE / 2 + SIZE * (y + margin_y), SIZE, SIZE);
                }

            }
        }
    }

    // check to possible or Impossible
    public boolean confirmField() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (field[i][j].num == 2)
                    return false;
            }
        }
        return true;
    }

    // block fall complete
    public boolean fallBlock() {
        blockEvent(0);
        return fall_block_result;
    }

    // print my Field (Console)
    public void print_field(Box[][] field) {
        for (int i = 0; i < ROWS - 1; i++) {
            System.out.print("□");
            for (int j = 1; j < COLUMNS - 1; j++) {
                if (field[i][j].num == 1) {
                    System.out.print(" ■");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println(" □");
        }
        for (int j = 0; j < COLUMNS; j++) {
            System.out.print("□ ");
        }
        System.out.println("");


    }

    // field  = field + field_
    public void fillField() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                field[i][j].num = field[i][j].num + field_[i][j].num;
                field[i][j].color = field[i][j].color + field_[i][j].color;
            }
        }
    }

    // field_ = field_ (0) + block
    public void fillField_() {
        resetField_();
        for (int y = 0; y < block.area_length; y++) {
            for (int x = 0; x < block.area_length; x++) {
                if (y + block.row >= ROWS || x + block.column <= -1 || x + block.column >= COLUMNS) {
                } else {
                    field_[y + block.row][x + block.column].num = block.area[y][x].num;
                    field_[y + block.row][x + block.column].color = block.area[y][x].color;
                }
            }
        }
    }

    // field = 0
    public void resetField(Box[][] field) {
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) { // 0 ~ 11
                if (y == ROWS - 1 || x == 0 || x == COLUMNS - 1) {
                    field[y][x] = new Box(1, -1);
                } else {
                    field[y][x] = new Box(0, 0);
                }
            }
        }
    }

    // field_ = 0
    public void resetField_() {
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                field_[y][x] = new Box(0, 0);
            }
        }
    }

    // field = saveField
    public void loadField() {
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                field[y][x].num = save_field[y][x].num;
                field[y][x].color = save_field[y][x].color;
            }
        }
    }

    // saveField = field     (Back Up)
    public void saveField() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                save_field[i][j].num = field[i][j].num;
                save_field[i][j].color = field[i][j].color;
            }
        }
    }

    // field = field - field_    (back up fillField())
    public void returnField() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                field[i][j].num = field[i][j].num - field_[i][j].num;
                field[i][j].color = field[i][j].color - field_[i][j].color;
            }
        }
    }

    // block move event
    public void blockEvent(int a) {
        loadField();               // field = savefield
        resetField_();             // field_ = 0

        switch (a) {
            case 0:     // fallBlock
                block.row = block.row + 1;
                break;
            case 1:     //  keyboard left
                block.column = block.column - 1;
                break;
            case 2:     //  keyboard right
                block.column = block.column + 1;
                break;
            case 3:     //  keyboard up
                block.turnBlock();
                break;
        }
        fillField_();              // field_ = field + block
        fillField();               // field = field + field_

        if (confirmField()) {        // 2가 없으면 그대로 진행.
            fall_block_result = true;
            //System.out.println("block.row = "+block.row + "  block.column = "+block.column);

        } else {                      // 2가 발견되면 다시 바꿈
            returnField();     // field - field_
            resetField_();     // field_ = 0
            switch (a) {
                case 0:             // fall block
                    fall_block_result = false;
                    fall_complete = true;
                    block.row = block.row - 1;
                    fillField_();          //  field_ = 0 + block
                    fillField();           // field = field + field_

                    explodeBlock();
//                    if(exploded_block>0) {
//                        beAttacked(exploded_block);
//                    }
                    saveField();

                    blockPreChange();
                    if (confirmGameOver()) {
                        System.out.println("*****************GAME OVER *****************");
                        this.removeKeyListener(keyadapter);
                        timer.cancel();
                        timer.purge();
                        return;
                    } else {
                    }
                    return;
                case 1:             // left
                    block.column = block.column + 1;
                    fillField_();          //  field_ = 0 + block
                    fillField();           // field = field + field_
                    break;
                case 2:             // right
                    block.column = block.column - 1;
                    fillField_();          //  field_ = 0 + block
                    fillField();           // field = field + field_
                    break;
                case 3:             // up
                    block.returnBlock();
                    fillField_();          //  field_ = 0 + block
                    fillField();           // field = field + field_
                    break;
            }

        }
        //print_field(field);
    }

    // confirm block explode
    public void explodeBlock() {
        int total;
        exploded_block = 0;
        for (int a = ROWS - 2; a > 3; a--) {
            total = 0;
            for (int b = 1; b < COLUMNS - 1; b++) {
                total = total + field[a][b].num;
            }
            if (total == 10) {
                //explode
                for (int i = a; i > 3; i--) {
                    for (int j = 1; j < COLUMNS - 1; j++) {
                        field[i][j].num = field[i - 1][j].num;
                        field[i][j].color = field[i - 1][j].color;
                    }
                }
                a = a + 1;
                exploded_block = exploded_block + 1;
            }
        }


        if (exploded_block > 0) {
            //exploded_block
            //PacketManager.getInstance().pop(exploded_block);
            //send packet
        }

    }

    // confirm game over
    public boolean confirmGameOver() {
        for (int y = 0; y < 4; y++) {
            for (int x = 1; x < COLUMNS - 1; x++) {
                if (field[y][x].num == 1) {
                    gameover_flag = true;
                    return true;
                }
            }
        }
        return false;
    }

    // game over effect ( every block change color  >> gray )
    public void gameOverEffect() {
        for (int y = 4; y < ROWS - 1; y++) {
            for (int x = 1; x < COLUMNS - 1; x++) {
                if (field[y][x].num == 1) {
                    field[y][x].color = -2;
                }
            }
        }
    }

    // block preview change
    public void blockPreChange() {
        block = block_pre_1.clone();
        block_pre_1 = block_pre_2.clone();
        block_pre_2 = block_pre_3.clone();
        block_pre_3 = block_pre_4.clone();
        block_pre_4 = new Block();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            System.out.println("스ㅔㄹ드 시작");
            while (true) {
                if (gameover_flag) {
                    break;
                }
                if (queue.peek() != null) {
                    System.out.println("not empty");
                    switch ((int) queue.poll()) {
                        case 37:    // left
                            blockEvent(1);
                            break;
                        case 39:    //right
                            blockEvent(2);
                            break;
                        case 38:    // up
                            blockEvent(3);
                            break;
                        case 40:    // down
                            if (timer_flag) {
                                timer.cancel();
                                timer.purge();
                                timer_flag = false;

                                timer = new Timer();
                                task = new TimerTask() {
                                    @Override
                                    public void run() {
                                        // object 떨어짐
                                        fallBlock();
                                    }
                                };
                                timer.schedule(task, 0, delay / 4);
                            }
                            break;
                        case -40:   // down release
                            System.out.println("down release");
                            timer.cancel();
                            timer.purge();
                            timer_flag = true;

                            task = new TimerTask() {
                                @Override
                                public void run() {
                                    // object 떨어짐
                                    fallBlock();
                                }
                            };
                            timer = new Timer();
                            timer.schedule(task, 0, delay);
                            break;
                        case 32:
                            while (fallBlock()) {
                            }
                            break;
                    }
                    System.out.println("큐 사용");
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);

            int key = e.getKeyCode();
            if (key == KeyEvent.VK_DOWN) {
                System.out.println(key);
                queue.offer(key * -1);
            }

        }

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            System.out.println(key);
            queue.offer(key);
        }

    }
}