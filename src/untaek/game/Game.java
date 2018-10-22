package untaek.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends JPanel{
    static int rows = 25;   // 20 + 1 + 4
    static int columns = 12; // 10 + 2

    static int SIZE = 22;
    static int MAX_HEIGHT = SIZE * (rows + 2);
    static int MAX_WIDTH = SIZE * (columns + 1);
    static int HEIGHT = SIZE * (rows - 5) ;
    static int WIDTH = SIZE * (columns - 2);
    static int MARGIN_X = 13 * SIZE;
    static int MARGIN_Y = 5 * SIZE;

    static int OTHER_MARGIN_X = 25;
    static int OTHER_MARGIN_Y = 25;

    Box [][] save_field = new Box[rows][columns];
    Box [][] field = new Box[rows][columns];
    Box [][] field_ = new Box[rows][columns];

    static public Box[][] send_field = new Box[20][10];

    public Block block;
    public Block block_pre_1;
    public Block block_pre_2;
    public Block block_pre_3;
    public Block block_pre_4;

    public boolean fall_complete;
    public boolean fall_block_result;
    public boolean timer_flag = true;
    public boolean gameover_flag = false;

    public int gameover_row=rows-1;

    public int combo=0;
    public int delay = 500;
    public int player_number;

    public  Timer timer;
    public TimerTask task;

    public static KeyAdapter keyadapter;
    public Game(int player) {
        // 초기화
        player_number = player;
        reset_field(field);
        reset_field(save_field);
        reset_field_();

        keyadapter = new MyKeyAdapter();
        this.addKeyListener(keyadapter);
        this.setFocusable(true);

        block = new Block();
        block_pre_1 = new Block();
        block_pre_2 = new Block();
        block_pre_3 = new Block();
        block_pre_4 = new Block();

        // field_ 에 block 넣기
        fill_field_();

        // field  = field + field_
        fill_field();

        task = new TimerTask(){
            @Override
            public void run(){
                // object 떨어짐
                run_();
            }
        };
        timer = new Timer();
        timer.schedule(task,0,delay);    }
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        //draw_edge(g);
        if(gameover_flag){
            gameover_effect();
        }
        if (player_number == 0){
            // my game
            draw_Preview(g);
            draw_Field(g);
            draw_Field_Border(g);
        }else{
            // others game
            draw_others_Field(g);
            draw_others_Field_Border(g);
        }

        // send_field

        repaint();
        invalidate();
    }

    public void send_Field_setting(){
        for(int x = 0; x< 10; x++){
            for (int y = 0; y<20; y++){
                send_field[y][x].num = field[y+4][x+1].num;
                send_field[y][x].color = field[y+4][x+1].color;
            }
        }
    }

    public void draw_Field_Border(Graphics g){
        g.setColor(Color.BLACK);

        // top border
        g.fillRect(SIZE/2, 0, (columns-1) *SIZE,SIZE/2);
        g.drawRect(SIZE/2, 0, (columns-1) *SIZE,SIZE/2);

        // bottom border
        g.fillRect(SIZE/2, SIZE * (rows-4) -SIZE/2, (columns-1) *SIZE,SIZE/2);
        g.drawRect(SIZE/2, SIZE * (rows-4) -SIZE/2, (columns-1) *SIZE,SIZE/2);

        // left border
        g.fillRect(SIZE/2, 0, SIZE/2, (rows-4)*SIZE);
        g.drawRect(SIZE/2, 0, SIZE/2, (rows-4)*SIZE);

        // right border
        g.fillRect((columns-1) *SIZE, 0, SIZE/2, (rows-4)*SIZE);
        g.drawRect((columns-1) *SIZE, 0, SIZE/2, (rows-4)*SIZE);
    }

    public void draw_others_Field_Border(Graphics g){
        g.setColor(Color.BLACK);

        // top border
        g.fillRect(SIZE/4 +OTHER_MARGIN_X, SIZE/4+OTHER_MARGIN_Y, (columns-1) *(SIZE/2),SIZE/4);
        g.drawRect(SIZE/4+OTHER_MARGIN_X, SIZE/4+OTHER_MARGIN_Y, (columns-1) *(SIZE/2),SIZE/4);

        // bottom border
        g.fillRect(SIZE/4+OTHER_MARGIN_X, (SIZE/2) * (rows-4)+OTHER_MARGIN_Y, (columns-1) *(SIZE/2),SIZE/4);
        g.drawRect(SIZE/4+OTHER_MARGIN_X, (SIZE/2) * (rows-4)+OTHER_MARGIN_Y, (columns-1) *(SIZE/2),SIZE/4);

        // left border
        g.fillRect(SIZE/4+OTHER_MARGIN_X, SIZE/4+OTHER_MARGIN_Y, SIZE/4, (rows-4)*(SIZE/2));
        g.drawRect(SIZE/4+OTHER_MARGIN_X, SIZE/4+OTHER_MARGIN_Y, SIZE/4, (rows-4)*(SIZE/2));

        // right border
        g.fillRect((columns-1) *(SIZE/2)+OTHER_MARGIN_X, SIZE/4+OTHER_MARGIN_Y, SIZE/4, (rows-4)*(SIZE/2));
        g.drawRect((columns-1) *(SIZE/2)+OTHER_MARGIN_X, SIZE/4+OTHER_MARGIN_Y, SIZE/4, (rows-4)*(SIZE/2));
    }

    public void draw_Field(Graphics g){
        for(int x = 1; x < columns-1; x++){     // 0~ 11
            for (int y = 4; y < rows-1; y++){   // 4~ 24
                if(field[y][x].num == 1){
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
                    g.fillRect(SIZE * x, SIZE/2 + SIZE * (y-4), SIZE,SIZE);
                    g.setColor(Color.BLACK);
                    g.drawRect(SIZE * x, SIZE/2 + SIZE * (y-4), SIZE,SIZE);
                }
            }
        }
    }

    public void draw_others_Field(Graphics g){
        for(int x = 1; x < columns-1; x++){     // 0~ 11
            for (int y = 4; y < rows-1; y++){   // 4~ 24
                if(field[y][x].num == 1){
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
                    g.fillRect((SIZE/2) * x+OTHER_MARGIN_X, SIZE/2 + (SIZE/2) * (y-4)+OTHER_MARGIN_Y, SIZE/2,SIZE/2);
                    g.setColor(Color.BLACK);
                    g.drawRect((SIZE/2) * x+OTHER_MARGIN_X, SIZE/2 + (SIZE/2) * (y-4)+OTHER_MARGIN_Y, SIZE/2,SIZE/2);
                }
            }
        }
    }

    public void draw_Preview(Graphics g) {
        draw_Block(g,1);    // pre1
        draw_Block(g,2);    // pre2
        draw_Block(g,3);    // pre3
        draw_Block(g,4);    // pre4
    }

    public void draw_Block(Graphics g, int block_num){

        Block block_pre = null;
        int margin_y = 0;
        switch (block_num){
            case 1:
                block_pre = block_pre_1;
                margin_y = 0;
                break;
            case 2:
                block_pre= block_pre_2;
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

        for(int x = 0; x < block_pre.area_length; x++){     // 0~ 11
            for (int y = 0; y < block_pre.area_length; y++){   // 4~ 24
                if(block_pre.area[y][x].num == 1) {
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

//    public void draw_edge(Graphics g){
//        g.setColor(Color.BLACK);
//        for(int y = 0; y<rows-4; y++){  //  0 ~ 20
//            g.fillRect(MARGINE + SIZE/2,MARGINE + SIZE* y + SIZE/2, SIZE, SIZE);
//            g.fillRect(MARGINE + SIZE * columns + SIZE/2,MARGINE + SIZE* y + SIZE/2, SIZE, SIZE);
//        }
//        for(int x = 0; x<columns; x++){   // 0 ~ 11
//            g.fillRect(MARGINE + SIZE *  x + SIZE/2, MARGINE + SIZE * (rows-5) + SIZE/2, SIZE, SIZE);
//        }
//    }

    public boolean confirm_field(){
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<columns; j++){
                if(field[i][j].num == 2)
                    return false;
            }
        }
        return true;
    }

    public boolean fall_block(){
        block_event(0);
        return fall_block_result;
    }

    public void print_field(Box[][] field){
        for (int i = 0; i < rows-1; i++) {
            System.out.print("□");
            for (int j = 1; j < columns-1; j++) {
                if(field[i][j].num == 1){
                    System.out.print(" ■");
                }else{
                    System.out.print("   ");
                }
            }
            System.out.println(" □");
        }
        for(int j = 0; j<columns;j++){
            System.out.print("□ ");
        }
        System.out.println("");


    }

    // field  = field + field_
    public void fill_field(){
        for(int i = 0; i<rows; i++){
            for (int j = 0; j<columns; j++){
                field[i][j].num = field[i][j].num + field_[i][j].num;
                field[i][j].color = field[i][j].color+ field_[i][j].color;
            }
        }
    }
    // field_ = field_ + block
    public void fill_field_(){
        reset_field_();
        for(int y = 0; y<block.area_length; y++){
            for(int x = 0; x<block.area_length; x++){
                if(y + block.row >= rows || x +block.column <=-1 || x + block.column >= columns){
                }else {
                    field_[y + block.row][x + block.column].num = block.area[y][x].num;
                    field_[y + block.row][x + block.column].color = block.area[y][x].color;
                }
            }
        }
    }

    public void reset_field(Box[][] field){
        for (int y = 0; y < rows; y++) {
            for(int x = 0; x<columns; x++) { // 0 ~ 11
                if (y == rows - 1 || x == 0 || x == columns - 1) {
                    field[y][x] = new Box(1, -1);
                } else {
                    field[y][x] = new Box(0, 0);
                }
            }
        }
    }
    public void reset_field_(){
        for (int y = 0; y<rows; y++){
            for(int x = 0 ; x<columns; x++){
                field_[y][x] = new Box(0,0);
            }
        }
    }

    public void load_field(){
        for(int y = 0; y<rows; y++){
            for(int x = 0; x<columns; x++){
                field[y][x].num = save_field[y][x].num;
                field[y][x].color = save_field[y][x].color;

            }
        }
    }
    public void save_field(){
        for(int i = 0; i<rows; i++){
            for (int j = 0; j<columns; j++){
                save_field[i][j].num = field[i][j].num;
                save_field[i][j].color = field[i][j].color;
            }
        }
    }

    public void return_field(){
        for (int i = 0; i<rows; i++){
            for(int j = 0 ; j<columns; j++){
                field[i][j].num = field[i][j].num - field_[i][j].num;
                field[i][j].color = field[i][j].color - field_[i][j].color;
            }
        }
    }
    public void run_(){
        fall_block();
        //print_field(field);
    }
    public void block_event(int a){
        load_field();               // field = savefield
        reset_field_();             // field_ = 0

        switch(a){
            case 0:     // fall_block
                block.row= block.row + 1;
                break;
            case 1:     //  keyboard left
                block.column = block.column - 1;
                break;
            case 2:     //  keyboard right
                block.column = block.column + 1;
                break;
            case 3:     //  keyboard up
                block.turn_block();
                break;
        }
        fill_field_();              // field_ = field + block
        fill_field();               // field = field + field_

        if(confirm_field()) {        // 2가 없으면 그대로 진행.
            fall_block_result = true;
            //System.out.println("block.row = "+block.row + "  block.column = "+block.column);

        }else{                      // 2가 발견되면 다시 바꿈
            return_field();     // field - field_
            reset_field_();     // field_ = 0
            switch(a){
                case 0:             // fall block
                    fall_block_result = false;
                    fall_complete = true;
                    block.row= block.row - 1;
                    fill_field_();          //  field_ = 0 + block
                    fill_field();           // field = field + field_
                    if (confirm_gameover()) {
                        System.out.println("*****************GAME OVER *****************");
                        this.removeKeyListener(keyadapter);
                        timer.cancel();
                        timer.purge();
                        return;
                    } else {
                    block_pre_change();
                    }

                    explode_block();
                    save_field();

                    return;
                case 1:             // left
                    block.column = block.column + 1;
                    fill_field_();          //  field_ = 0 + block
                    fill_field();           // field = field + field_
                    break;
                case 2:             // right
                    block.column = block.column - 1;
                    fill_field_();          //  field_ = 0 + block
                    fill_field();           // field = field + field_
                    break;
                case 3:             // up
                    block.return_block();
                    fill_field_();          //  field_ = 0 + block
                    fill_field();           // field = field + field_
                    break;
            }

        }
        //print_field(field);
    }



    public void explode_block(){
        int total;
        for(int a = rows -2; a > 3; a--){
            total = 0;
            for(int b = 1; b<columns-1; b++){
                total = total + field[a][b].num;
            }
            if(total == 10){
                //explode
                for(int i = a; i>3; i--){
                    for(int j = 1; j<columns-1; j++){
                        field[i][j].num = field[i-1][j].num;
                        field[i][j].color = field[i-1][j].color;
                    }
                }
                a = a+1;
                //combo = combo + 1;
            }else{

            }
        }
    }

    public boolean confirm_gameover(){
        for(int y = 0; y<4; y++){
            for (int x = 1; x<columns-1; x++){
                if(field[y][x].num == 1){
                    gameover_flag = true;
                    return true;
                }
            }
        }
        return false;
    }

    public void gameover_effect(){
        for(int y = 4; y<rows-1; y++) {
            for (int x = 1; x < columns - 1; x++) {
                if (field[y][x].num == 1) {
                    field[y][x].color = -2;
                }
            }
        }
    }

    public void block_pre_change(){
        block = block_pre_1.clone();
        block_pre_1 = block_pre_2.clone();
        block_pre_2 = block_pre_3.clone();
        block_pre_3 = block_pre_4.clone();
        block_pre_4 = new Block();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e){
            super.keyReleased(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_DOWN){
                timer.cancel();
                timer.purge();
                timer_flag = true;

                task = new TimerTask(){
                    @Override
                    public void run(){
                        // object 떨어짐
                        run_();
                    }
                };
                timer = new Timer();
                timer.schedule(task, 0, delay);
            }
        }


        @Override
        public void keyPressed(KeyEvent e) {

            super.keyPressed(e);
            int key = e.getKeyCode();
            switch (key){
                case KeyEvent.VK_LEFT:
                    block_event(1);
                    break;
                case KeyEvent.VK_RIGHT:
                    block_event(2);
                    break;
                case  KeyEvent.VK_UP:
                    block_event(3);
                    break;
                case KeyEvent.VK_DOWN:
                    if(timer_flag){
                        timer.cancel();
                        timer.purge();
                        timer_flag = false;

                        timer = new Timer();
                        task = new TimerTask(){
                            @Override
                            public void run(){
                                // object 떨어짐
                                run_();
                            }
                        };
                        timer.schedule(task,0,delay /4);
                    }

                    break;

                case KeyEvent.VK_SPACE:
                    while (fall_block()){
                    }

                    break;
            }
        }

    }

}