package ija.ija2018.homework2;

import ija.ija2018.homework2.common.Field;
import ija.ija2018.homework2.common.Disk;
import ija.ija2018.homework2.common.Figure;
import ija.ija2018.homework2.common.Game;
import ija.ija2018.homework2.common.GameClass;
import ija.ija2018.homework2.game.Board;

public abstract class GameFactory extends java.lang.Object
{
    public static Game createChessGame(Board board){
        
        
        Figure pW[] = new Figure[8];
        Figure pB[] = new Figure[8];
        Figure vW1 = null;
        Figure vW2 = null;
        Figure vB1 = null;
        Figure vB2 = null;
        Game hra=new GameClass(board);
        
        //nastavi board ze se na nem hraje hra
        //vytvori a rozhaze figurky na boardu
        for(int i=0;i<board.getSize();i++){
            //White pesaci
            pW[i]=new Disk(1,board,true);
            Field f=board.getField(i+1, 2);
            hra.create(pW[i],f);
            //Black pesaci
            pB[i]=new Disk(1,board,false) {};
            f=board.getField(i+1,7);
            hra.create(pB[i],f);
        } 
        //White vez         
        vW1=new Disk(0,board,true); 
        Field f=board.getField(1, 1);
        hra.create(vW1,f);
        
        vW2=new Disk(0,board,true); 
        f=board.getField(8, 1);
        hra.create(vW2,f);
        //Black vez
        vB1=new Disk(0,board,false); 
        f=board.getField(1, 8);
        hra.create(vB1,f);
        vB2=new Disk(0,board,false); 
        f=board.getField(8, 8);
        hra.create(vB2,f);

        return hra;
    
    }
    
    public static Game createCheckersGame(Board board){
        Figure pW[] = new Figure[8];
        Game hra=new GameClass(board);
        

        for (int i = 1; i <= 8; i=i+2){
            pW[i-1]=new Disk(2,board,true);
            Field f=board.getField(i, 1);
            hra.create(pW[i-1],f);
        } 
        for (int i = 1; i <= 8; i=i+2){
            pW[i]=new Disk(2,board,true);
            Field f=board.getField(i+1, 2);
            hra.create(pW[i],f);
            
        } 

        return hra;
    }


}