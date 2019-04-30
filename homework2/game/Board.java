package ija.ija2018.homework2.game;

import ija.ija2018.homework2.common.BoardField;
import ija.ija2018.homework2.common.Field;

public class Board extends java.lang.Object{
    private final int size;
    public Field[][] field;
    public boolean hra;

    public Board(int size){
        this.size = size;
        this.field=new Field[size+2][size+2];
        for(int i=0;i<size+1;i++){
            for(int j=0;j<size+1;j++){
                this.field[i][j]=new BoardField(i,j);
            }
        }
        
        
        for (int i=1;i<size;i++) {
            for (int j=1;j<size;j++) {
                field[i][j].addNextField(Field.Direction.D,  this.field[i+0][j-1]);
                field[i][j].addNextField(Field.Direction.L,  this.field[i-1][j+0]);
                field[i][j].addNextField(Field.Direction.LD, this.field[i-1][j-1]);
                field[i][j].addNextField(Field.Direction.LU, this.field[i-1][j+1]);
                field[i][j].addNextField(Field.Direction.R,  this.field[i+1][j+0]);
                field[i][j].addNextField(Field.Direction.RD, this.field[i+1][j-1]);
                field[i][j].addNextField(Field.Direction.RU, this.field[i+1][j+1]);
                field[i][j].addNextField(Field.Direction.U,  this.field[i+0][j+1]);        
            }
        }
    }
    
    public Field getField(int col,int row){
        if(col>this.size || row>this.size || col<1 || row<1 ){
            return null;
        }else{   
            return this.field[col][row];
        }
    }
    public int getSize(){
        return this.size;
    }
}