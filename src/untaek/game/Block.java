package untaek.game;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class Block {
    static int SIZE = Game.SIZE;
    Random rand;

    Box[][] area;
    Box box;
    public int shape; //1~7
    public int degree_max;
    int degree;//0~3

    boolean [] bag = new boolean[7];
    boolean [] bag_ = new boolean[7];

    int row;
    int column;

    public int area_length;

    public Block(){
        rand = new Random();

        choose_shape();

        setting();
        reset_area();
        set_value(this.shape, this.degree);
    }
    public void choose_shape(){
        if(bag.equals(bag_)){       // 가방 모두 사용
            System.out.println("가방 모두 사용");
            Arrays.fill(bag,Boolean.FALSE);
        }

        while(Boolean.TRUE){
            shape = rand.nextInt(7)+1;
            if(bag[shape-1] == Boolean.FALSE){
                bag[shape-1] = Boolean.TRUE;
                break;
            }
        }
    }

    public void turn_block(){
        this.degree = this.degree + 1;
        reset_area();
        set_value(this.shape, this.degree);
    }

    public void return_block(){
        this.degree = this.degree - 1;
        reset_area();
        set_value(this.shape, this.degree);
    }

    public void setting(){
        Arrays.fill(bag, Boolean.FALSE);
        Arrays.fill(bag_, Boolean.TRUE);
        // degree_max
        if(this.shape ==1 || this.shape == 4 || this.shape == 5){
            this.degree_max=2;
        }else if(this.shape == 2){
            this.degree_max = 1;
        }else{
            this.degree_max = 4;
        }

        //area_length
        if(this.shape == 1 || this.shape == 2){
            area_length = 4;
            row = 0;
            column = 4;
        }else{
            area_length = 3;
            row = 1;
            column = 4;
        }

        degree = rand.nextInt(this.degree_max);
        area = new Box[area_length][area_length];
    }

    public void reset_area(){
        for (int i = 0; i < area_length; i++) {
            for (int j = 0; j < area_length; j++) {
                box = new Box(0,0);
                area[i][j] = box;
            }
        }
    }

    public void set_value(int shape, int degree){
        switch (shape) {
            case 1://
                switch (degree % this.degree_max) {
                    case 0:
                        set_area(0,1);
                        set_area(1,1);
                        set_area(2,1);
                        set_area(3,1);
                        break;
                    case 1:
                        set_area(2,0);
                        set_area(2,1);
                        set_area(2,2);
                        set_area(2,3);
                        break;
                }
                break;

            case 2:
                set_area(1,1);
                set_area(1,2);
                set_area(2,1);
                set_area(2,2);
                break;

            case 3:
                switch (degree% degree_max) {
                    case 0:
                        set_area(0,1);
                        set_area(1,0);
                        set_area(1,1);
                        set_area(1,2);
                        break;
                    case 1:
                        set_area(0,1);
                        set_area(1,1);
                        set_area(1,2);
                        set_area(2,1);
                        break;
                    case 2:
                        set_area(1,0);
                        set_area(1,1);
                        set_area(1,2);
                        set_area(2,1);
                        break;
                    case 3:
                        set_area(0,1);
                        set_area(1,0);
                        set_area(1,1);
                        set_area(2,1);
                        break;
                }
                break;
            case 4:
                switch (degree% degree_max) {
                    case 0:
                        set_area(0,0);
                        set_area(1,0);
                        set_area(1,1);
                        set_area(2,1);
                        break;
                    case 1:
                        set_area(1,1);
                        set_area(1,2);
                        set_area(2,0);
                        set_area(2,1);
                        break;
                }
                break;
            case 5:
                switch (degree% degree_max) {
                    case 0:
                        set_area(0,1);
                        set_area(1,0);
                        set_area(1,1);
                        set_area(2,0);
                        break;
                    case 1:
                        set_area(1,0);
                        set_area(1,1);
                        set_area(2,1);
                        set_area(2,2);
                        break;
                }
                break;
            case 6:
                switch (degree% degree_max) {
                    case 0:
                        set_area(0,2);
                        set_area(1,0);
                        set_area(1,1);
                        set_area(1,2);
                        break;
                    case 1:
                        set_area(0,1);
                        set_area(1,1);
                        set_area(2,1);
                        set_area(2,2);
                        break;
                    case 2:
                        set_area(1,0);
                        set_area(1,1);
                        set_area(1,2);
                        set_area(2,0);
                        break;
                    case 3:
                        set_area(0,0);
                        set_area(0,1);
                        set_area(1,1);
                        set_area(2,1);
                        break;
                }
                break;
            case 7:
                switch (degree% degree_max) {
                    case 0:
                        set_area(0,0);
                        set_area(1,0);
                        set_area(1,1);
                        set_area(1,2);
                        break;
                    case 1:
                        set_area(0,1);
                        set_area(0,2);
                        set_area(1,1);
                        set_area(2,1);
                        break;
                    case 2:
                        set_area(1,0);
                        set_area(1,1);
                        set_area(1,2);
                        set_area(2,2);
                        break;
                    case 3:
                        set_area(0,1);
                        set_area(1,1);
                        set_area(2,0);
                        set_area(2,1);
                        break;
                }
        }
    }

    public void print_block(){
        for(int i = 0; i<area_length; i++){
            for(int j = 0; j<area_length; j++){
                if(area[i][j].num == 1){
                    System.out.print(" ■");
                }else{
                    System.out.print("   ");
                }
            }
        System.out.println("");
        }
    }

    public void set_area(int row, int column){
        area[row][column].num = 1;
        area[row][column].color = shape;
    }
}
