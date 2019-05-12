package Chess.Game.game;

import Chess.Game.common.BoardField;
import Chess.Game.common.Field;
/**
 * 
 * @author Aleš Tetur Šimon Matyáš
 */
public class Board extends java.lang.Object{
    private final int size;
    
    /**
     * Pole obsahující hrací desku.
     */
    public Field[][] field;
    
    /**
     * Hra
     */
    public boolean hra;
    
    /**
     * Bílí hráč je na řadě.
     */
    public boolean wTurn = true;

    /**
     * Metoda generuje hrací desku.
     * Metoda generuje hrací desku pomocí funkce addNextField(Field.Direction dirs, Field field).
     * 
     * @param size  velikost hrací desky
     */
    public Board(int size){
        this.size = size;
        this.field=new Field[size+2][size+2];
        for(int i=0;i<size+1;i++){
            for(int j=0;j<size+1;j++){
                this.field[i][j]=new BoardField(i,j);
            }
        }
        
        for (int i=1;i<=size;i++) {
            for (int j=1;j<=size;j++) {
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
    
    /**
     * Metoda vrací pole na určité pozici.
     * 
     * @param col   pozice pole ve sloupci
     * @param row   pozice pole v řádku
     * @return      vrací pole
     */
    public Field getField(int col,int row){
        if(col>this.size || row>this.size || col<1 || row<1 ){
            return null;
        }else{   
            return this.field[col][row];
        }
    }
    
    /**
     * Metoda vrací velikost hrací desky.
     * 
     * @return  velikost hrací desky
     */
    public int getSize(){
        return this.size;
    }
}
