package untaek.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends JPanel {
    static int rows = 25;   // 20 + 1 + 4
    static int columns = 12; // 10 + 2

    static int MARGINE = 30;
    static int SIZE = 20;
    static int MAX_HEIGHT = SIZE * (rows + 2);
    static int MAX_WIDTH = SIZE * (columns + 1);
    static int HEIGHT = SIZE * (rows - 5) ;
    static int WIDTH = SIZE * (columns - 2);

    Box [][] save_field = new Box[rows][columns];
    Box [][] field = new Box[rows][columns];
    Box [][] field_ = new Box[rows][columns];

    public Block block;
    Block block_new;
    boolean fall_complete;
    boolean fall_block_result;
    boolean timer_flag = true;
    boolean gameover_flag = false;

    int gameover_row=rows-1;

    int count=0;
    int delay = 500;
    private Timer timer;

    TimerTask task;

    KeyAdapter keyadapter;
    public Game() {

        // 초기화
        reset_field(field);
        reset_field(save_field);
        reset_field_();

        this.addKeyListener(keyadapter = new MyKeyAdapter());

        block = new Block();
        block_new = new Block();

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
        timer.schedule(task,0,delay);


    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        //draw_edge(g);
        if(gameover_flag){
            gameover_effect();
        }
        draw_Box(g);
        repaint();
        invalidate();

    }
    public void draw_Box(Graphics g){
        for(int x = 0; x < columns; x++){     // 0~ 11
            for (int y = 4; y < rows; y++){   // 4~ 24
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
                    g.fillRect(SIZE/2 + SIZE * x, SIZE/2 + SIZE * (y-4), SIZE,SIZE);
                    g.setColor(Color.BLACK);
                    g.drawRect(SIZE/2 + SIZE * x, SIZE/2 + SIZE * (y-4), SIZE,SIZE);
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
        print_field(field);
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
            reset_field_();     // field = 0
            switch(a){
                case 0:             // fall block
                    fall_block_result = false;
                    fall_complete = true;
                    block.row= block.row - 1;
                    fill_field_();          //  field_ = 0 + block
                    fill_field();           // field = field + field_
                    explode_block();
                    save_field();
                    if (confirm_gameover()) {
                        System.out.println("*****************GAME OVER *****************");
                        this.removeKeyListener(keyadapter);
                        timer.cancel();
                        timer.purge();
                        return;
                    } else {
                        block = block_new;
                        block_new = new Block();
                    }
                    return;
                case 1:             // left
                    block.column = block.column + 1;
                    break;
                case 2:             // right
                    block.column = block.column - 1;
                    break;
                case 3:             // up
                    block.return_block();
                    break;
            }
            fill_field_();          //  field_ = 0 + block
            fill_field();           // field = field + field_
        }
        print_field(field);
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

                    block_new = new Block();
                    break;
            }

        }
    }

}