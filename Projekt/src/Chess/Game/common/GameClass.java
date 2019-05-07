package Chess.Game.common;

import Chess.Game.common.Game;
import Chess.Game.game.Board;
import java.util.Stack;

public class GameClass extends java.lang.Object implements Game{
    private Stack<Field> from_Pos=new Stack<>();
    private Stack<Figure> from_Fig=new Stack<>();
    private Stack<Field> to_Pos=new Stack<>();
    private Stack<Figure> to_Fig=new Stack<>();
    private Stack<Figure> Remove_Fig=new Stack<>();
    private final Board board;
    private Game hra;
    
    //typ:0 Vez
    //typ:1 Pesak
    //typ:2 Disk na damu
    public GameClass(Board board) {
        this.from_Pos=new Stack<Field>();
        this.from_Fig=new Stack<Figure>();
        this.to_Pos=new Stack<Field>();
        this.to_Fig=new Stack<Figure>();
        this.Remove_Fig=new Stack<Figure>();
        this.board=board;
    }

    public void create(Figure Figurka,Field StartOn){
        

        if(Figurka.getBoard()==this.board){
            StartOn.put(Figurka);
        }
    }
    public boolean move(Figure figurka,Field  moveTo){

        From_addMove(figurka,moveTo);
        if(moveTo.get()!=null && figurka.isWhite()!=moveTo.get().isWhite()){
            Remove_addMove(moveTo.get());
        }else{
            Remove_addMove(null);
        }
        if(figurka.move(moveTo)){ 

            To_addMove(figurka);
            return true; 
        }else{
            from_Pos.pop();
            from_Fig.pop();
            return false;
        }
    }
    
    public void undo(){

        Field back_to_Pos=to_Pos.pop();
        Figure back_to_Fig=to_Fig.pop();
        
        Field back_from_Pos=from_Pos.pop();
        Figure back_from_Fig=from_Fig.pop();
        
        Figure Revive=Remove_Fig.pop();

        back_to_Fig.reverse_move(back_from_Pos);        
        if(Revive!=null){
            back_to_Pos.put(Revive);
        }
        
    }

    
    private void From_addMove(Figure figurka,Field moveTo){
        from_Pos.push(figurka.myfield());
        from_Fig.push(figurka);
                
        
        
    }
    private void To_addMove(Figure figurka){
        to_Pos.push(figurka.myfield());
        to_Fig.push(figurka);
        
    }
    private void Remove_addMove(Figure figurka){
        Remove_Fig.push(figurka);
    }
    
}