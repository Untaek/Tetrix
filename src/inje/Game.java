package inje;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;

public class Game extends JPanel {
    static int rows = 25;   // 20 + 1 + 4
    static int columns = 12; // 10 + 2

    static int SIZE = 20;
    static int MAX_HEIGHT = SIZE * (rows + 2);
    static int MAX_WIDTH = SIZE * (columns + 1);
    static int HEIGHT = SIZE * (rows - 5) ;
    static int WIDTH = SIZE * (columns - 2);

    int [][] save_field = new int[rows][columns];
    int [][] field = new int[rows][columns];
    int [][] field_ = new int[rows][columns];

    Block block;
    Block block_new;
    boolean fall_complete;
    boolean fall_block_result;
    boolean timer_flag = true;

    int count=0;
    int delay = 500;
    Timer timer;

    TimerTask task;

    KeyAdapter keyadapter;
    public Game() {
        // 초기화
        reset_field(field);
        reset_field(save_field);
        reset_field_();

        this.addKeyListener(keyadapter = new KeyAdapter() {
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
                            timer.schedule(task,0,100);
                        }

                        break;

                    case KeyEvent.VK_SPACE:
                        while (fall_block()){
                        }
                        explode_block();
                        save_field();
                        block = block_new;
                        block_new = new Block();
                        break;
                }

            }
        });

        block = new Block();
        block_new = new Block();

        // field_ 에 block 넣기
        fill_field_();

        // field  = field + field_
        fill_field();

        System.out.println("초기화면 ");
        print_field(field);

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

        System.out.println("paintComponent(g);");
        super.paintComponent(g);
        g.setColor(Color.BLACK);


    }

    public boolean confirm_field(){
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<columns; j++){
                if(field[i][j] == 2)
                    return false;
            }
        }
        return true;
    }

    public boolean fall_block(){
//        System.out.println("fall block");
        block_event(0);
        return fall_block_result;
    }

    public void print_field(int[][] field){
        clearScreen();
        for (int i = 4; i < rows-1; i++) {
            System.out.print("□");
            for (int j = 1; j < columns-1; j++) {

                if(field[i][j] == 1){
                    System.out.print(" ■");
                }else{
                    System.out.print("   ");
                }
            }
            System.out.println(" □");
        }
        for(int j = 1; j<columns+1;j++){
            System.out.print("□ ");
        }

        System.out.println("");
        System.out.println("");
    }

    // field  = field + field_
    public void fill_field(){
        for(int i = 0; i<rows; i++){
            for (int j = 0; j<columns; j++){
                field[i][j] = field[i][j] + field_[i][j];
            }
        }
    }
    // field_ = field_ + block
    public void fill_field_(){
        reset_field_();
        for(int i = 0; i<block.area_length; i++){
            for(int j = 0; j<block.area_length; j++){
                if(i + block.row >= rows || j +block.column <=-1 || j + block.column >= columns){

                }else {
                    field_[i + block.row][j + block.column] = block.area[i][j].num;
                }
            }
        }
    }

    public void reset_field(int[][] field){
        for (int i = 0; i < rows; i++) {
            if(i == rows - 1){
                for(int j = 0; j<columns; j++){
                    field[i][j] = 1;
                }
            }else{
                for (int j = 0; j < columns; j++) {
                    if(j == 0 || j == columns-1){
                        field[i][j] = 1;
                    }else{
                        field[i][j] = 0;
                    }
                }
            }
        }
    }


    public void load_field(){
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<columns; j++){
                field[i][j] = save_field[i][j];
            }
        }
    }

    public void reset_field_(){
        for (int i = 0; i<rows; i++){
            for(int j = 0 ; j<columns; j++){
                field_[i][j] = 0;
            }
        }
    }

    public void save_field(){
        for(int i = 0; i<rows; i++){
            for (int j = 0; j<columns; j++){
                save_field[i][j] = field[i][j];
            }
        }
    }

    public void return_field(){
        for (int i = 0; i<rows; i++){
            for(int j = 0 ; j<columns; j++){
                field[i][j] = field[i][j] - field_[i][j];
            }
        }
    }

    public void block_event(int a){
        load_field();               // field = savefield
        reset_field_();             // field_ = 0

        switch(a){
            case 0:
                System.out.println("fall block");
                block.row= block.row + 1;
                break;
            case 1:
                System.out.println("left");
                block.column = block.column - 1;
                break;
            case 2:
                System.out.println("right");
                block.column = block.column + 1;
                break;
            case 3:
                System.out.println("spacebar");
                block.turn_block();
                break;
        }
        fill_field_();              // field_ = field + block
        fill_field();               // field = field + field_

        if(confirm_field()) {        // 2가 없으면 그대로 진행.
            fall_block_result = true;
            System.out.println("block.row = "+block.row + "  block.column = "+block.column);
            print_field(field);
        }else{                      // 2가 발견되면 다시 바꿈
            return_field();     // field - field_
            reset_field_();     // field = 0
            switch(a){
                case 0:             // fall block
                    fall_block_result = false;
                    block.row= block.row - 1;
                    break;
                case 1:             // left
                    block.column = block.column + 1;
                    break;
                case 2:             // right
                    block.column = block.column - 1;
                    break;
                case 3:             // spacebar
                    block.return_block();
                    break;
            }
            fill_field_();          //  field_ = 0 + block
            fill_field();           // field = field + field_
            fall_complete = false;
        }
    }

    public void explode_block(){
        int total;
        for(int a = rows -2; a > 3; a--){
            total = 0;
            for(int b = 1; b<columns-1; b++){
                total = total + field[a][b];
            }
            if(total == 10){
                //explode
                for(int i = a; i>3; i--){
                    for(int j = 1; j<columns-1; j++){
                        field[i][j] = field[i-1][j];
                    }
                }
                a = a+1;
            }
        }
    }

    public boolean confirm_gameover(){
        for(int i = 0; i<4; i++){
            for (int j = 1; j<columns-1; j++){
                if(field[i][j] == 1){
                    return true;
                }
            }
        }
        return false;
    }

    public void run_(){
        count = count + 1;
        fall_complete = false;
        if (fall_block()) {
        } else {
            fall_complete = true;
        }

        if (fall_complete) {
            // 라인 확인
            explode_block();
            save_field();
            if(confirm_gameover()){
                System.out.println("*****************GAME OVER *****************");
                this.removeKeyListener(keyadapter);
                timer.cancel();
                timer.purge();
                return;
            }else{
                block = block_new;
                block_new = new Block();
            }
        } else{
            print_field(field);
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}