package untaek.game;

import java.util.Arrays;
import java.util.Random;

public class Block implements Cloneable {
    static int SIZE = Game.SIZE;
    Random rand;

    Box[][] area;
    Box box;

    public int shape; //1~7
    public int degree_max;
    int degree;//0~3

    public static boolean [] bag =  {false, false,false, false,false, false,false};
    boolean [] bag_ ={true,true,true,true,true,true,true};


    int row;
    int column;

    boolean full_flag;

    public int area_length;

    public Block(){
        rand = new Random();


        chooseShape();
        setting();

        resetArea();
        setValue(this.shape, this.degree);
    }

    public Block clone(){
        Block b = null;
        try{
            b = (Block)super.clone();
        }catch (Exception e){}
        return b;
    }

    public void chooseShape(){
        if(Arrays.equals(bag, bag_)){
            System.out.println("가방 모두 사용");
            Arrays.fill(bag,Boolean.FALSE);
        }

        while(Boolean.TRUE){
            shape = rand.nextInt(7)+1;
            if(bag[shape-1] == Boolean.FALSE){
                bag[shape-1] = Boolean.TRUE;
                System.out.println(shape);
                break;
            }
        }


    }

    public void turnBlock(){
        this.degree = this.degree + 1;
        resetArea();
        setValue(this.shape, this.degree);
    }

    public void returnBlock(){
        this.degree = this.degree - 1;
        resetArea();
        setValue(this.shape, this.degree);
    }

    public void setting(){

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

    public void resetArea(){
        for (int i = 0; i < area_length; i++) {
            for (int j = 0; j < area_length; j++) {
                box = new Box(0,0);
                area[i][j] = box;
            }
        }
    }

    public void setValue(int shape, int degree){
        switch (shape) {
            case 1://
                switch (degree % this.degree_max) {
                    case 0:
                        setArea(0,1);
                        setArea(1,1);
                        setArea(2,1);
                        setArea(3,1);
                        break;
                    case 1:
                        setArea(2,0);
                        setArea(2,1);
                        setArea(2,2);
                        setArea(2,3);
                        break;
                }
                break;

            case 2:
                setArea(1,1);
                setArea(1,2);
                setArea(2,1);
                setArea(2,2);
                break;

            case 3:
                switch (degree% degree_max) {
                    case 0:
                        setArea(0,1);
                        setArea(1,0);
                        setArea(1,1);
                        setArea(1,2);
                        break;
                    case 1:
                        setArea(0,1);
                        setArea(1,1);
                        setArea(1,2);
                        setArea(2,1);
                        break;
                    case 2:
                        setArea(1,0);
                        setArea(1,1);
                        setArea(1,2);
                        setArea(2,1);
                        break;
                    case 3:
                        setArea(0,1);
                        setArea(1,0);
                        setArea(1,1);
                        setArea(2,1);
                        break;
                }
                break;
            case 4:
                switch (degree% degree_max) {
                    case 0:
                        setArea(0,0);
                        setArea(1,0);
                        setArea(1,1);
                        setArea(2,1);
                        break;
                    case 1:
                        setArea(1,1);
                        setArea(1,2);
                        setArea(2,0);
                        setArea(2,1);
                        break;
                }
                break;
            case 5:
                switch (degree% degree_max) {
                    case 0:
                        setArea(0,1);
                        setArea(1,0);
                        setArea(1,1);
                        setArea(2,0);
                        break;
                    case 1:
                        setArea(1,0);
                        setArea(1,1);
                        setArea(2,1);
                        setArea(2,2);
                        break;
                }
                break;
            case 6:
                switch (degree% degree_max) {
                    case 0:
                        setArea(0,2);
                        setArea(1,0);
                        setArea(1,1);
                        setArea(1,2);
                        break;
                    case 1:
                        setArea(0,1);
                        setArea(1,1);
                        setArea(2,1);
                        setArea(2,2);
                        break;
                    case 2:
                        setArea(1,0);
                        setArea(1,1);
                        setArea(1,2);
                        setArea(2,0);
                        break;
                    case 3:
                        setArea(0,0);
                        setArea(0,1);
                        setArea(1,1);
                        setArea(2,1);
                        break;
                }
                break;
            case 7:
                switch (degree% degree_max) {
                    case 0:
                        setArea(0,0);
                        setArea(1,0);
                        setArea(1,1);
                        setArea(1,2);
                        break;
                    case 1:
                        setArea(0,1);
                        setArea(0,2);
                        setArea(1,1);
                        setArea(2,1);
                        break;
                    case 2:
                        setArea(1,0);
                        setArea(1,1);
                        setArea(1,2);
                        setArea(2,2);
                        break;
                    case 3:
                        setArea(0,1);
                        setArea(1,1);
                        setArea(2,0);
                        setArea(2,1);
                        break;
                }
        }
    }

    public void printBlock(){
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

    public void setArea(int row, int column){
        area[row][column].num = 1;
        area[row][column].color = shape;
    }
}
