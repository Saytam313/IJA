package Chess.Game;

import Chess.Game.common.Field;
import Chess.Game.common.Disk;
import Chess.Game.common.Figure;
import Chess.Game.common.Game;
import Chess.Game.common.GameClass;
import Chess.Game.game.Board;

public abstract class GameFactory extends java.lang.Object
{
    public static Game createChessGame(Board board){
        
        
        Figure pW[] = new Figure[8];
        Figure pB[] = new Figure[8];
        Figure vW1 = null;
        Figure vW2 = null;
        Figure vB1 = null;
        Figure vB2 = null;
        Figure sW1 = null;
        Figure sW2 = null;
        Figure sB1 = null;
        Figure sB2 = null;
        Figure kW1 = null;
        Figure kW2 = null;
        Figure kB1 = null;
        Figure kB2 = null;
        Figure krW1 = null;
        Figure qW1 = null;
        Figure krB1 = null;
        Figure qB1 = null;
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
        
        
        
        //White strelec         
        sW1=new Disk(2,board,true); 
        Field f=board.getField(3, 1);
        hra.create(sW1,f);
        
        sW2=new Disk(2,board,true); 
        f=board.getField(6, 1);
        hra.create(sW2,f);
        
        //Black strelec
        sB1=new Disk(2,board,false); 
        f=board.getField(3, 8);
        hra.create(sB1,f);
        
        sB2=new Disk(2,board,false); 
        f=board.getField(6, 8);
        hra.create(sB2,f);
        
        
        
        //White Kun         
        kW1=new Disk(3,board,true); 
        Field f=board.getField(2, 1);
        hra.create(kW1,f);
        
        kW2=new Disk(3,board,true); 
        f=board.getField(7, 1);
        hra.create(kW2,f);
        
        //Black Kun
        kB1=new Disk(3,board,false); 
        f=board.getField(2, 8);
        hra.create(kB1,f);
        
        kB2=new Disk(3,board,false); 
        f=board.getField(7, 8);
        hra.create(kB2,f);
        
        
        
        //White Kralovna         
        qW1=new Disk(4,board,true); 
        Field f=board.getField(4, 1);
        hra.create(qW1,f);
        
        //Black Kralovna
        qB1=new Disk(4,board,false); 
        f=board.getField(4, 8);
        hra.create(qB1,f);
        
        
        
        //White Kral         
        krW1=new Disk(5,board,true); 
        Field f=board.getField(5, 1);
        hra.create(krW1,f);
        
        //Black Kral
        krB1=new Disk(5,board,false); 
        f=board.getField(5, 8);
        hra.create(krB1,f);

        return hra;
    
    }
}
