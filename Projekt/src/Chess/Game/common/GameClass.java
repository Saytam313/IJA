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
    private Stack<Figure> zapis=new Stack<Figure>();
    private final Board board;
    private Game hra;
    
    //typ:0 Pesak
    //typ:1 Vez
    //typ:2 Strelec
    //typ:3 Kun
    //typ:4 Kralovna
    //typ:5 Kral
    public GameClass(Board board) {
        this.from_Pos=new Stack<Field>();
        this.from_Fig=new Stack<Figure>();
        this.to_Pos=new Stack<Field>();
        this.to_Fig=new Stack<Figure>();
        this.Remove_Fig=new Stack<Figure>();
        this.zapis=new Stack<Figure>();
        this.board=board;
    }

    public void create(Figure Figurka,Field StartOn){
        

        if(Figurka.getBoard()==this.board){
            StartOn.put(Figurka);
        }
    }
    public String move(Figure figurka,Field  moveTo){
        
        String zapis=zapisCreator(figurka,moveTo);
        //System.out.println(zapisCreator(figurka,moveTo));
        //System.out.println(zapisReader(zapisCreator(figurka,moveTo)));
        From_addMove(figurka,moveTo);
        if(moveTo.get()!=null && figurka.isWhite()!=moveTo.get().isWhite()){
            Remove_addMove(moveTo.get());
        }else{
            Remove_addMove(null);
        }
        
        if(figurka.move(moveTo) == 0){ 
            To_addMove(figurka);
            return zapis; 
        } else if(figurka.move(moveTo) == 1) {
            To_addMove(figurka);
            return zapis + "+";
        } else if(figurka.move(moveTo) == 2) {
            To_addMove(figurka);
            return zapis + "#";
        } else {
            from_Pos.pop();
            from_Fig.pop();
            return "";
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
    public String zapisCreator(Figure figurka,Field  moveTo){
        String type="";

        switch (figurka.getType()) {
            case 0:
                type="";
                break;
            case 1:
                type="V";
                break;
            case 2:
                type="S";
                break;
            case 3:
                type="J";
                break;
            case 4:
                type="D";
                break;
            case 5:
                type="K";
                break;
            default:
                break;
        }
        String sebrani;
        if(!moveTo.isEmpty()){
            sebrani="x";
        }else{
            sebrani="";
        }
        String ColFrom=colNumToStr(figurka.myfield().getCol());
        String ColTo=colNumToStr(moveTo.getCol());
        
        String ret=type+ColFrom+figurka.myfield().getRow()+sebrani+ColTo+moveTo.getRow();
        return ret;
    }
    private String colNumToStr(int num){
        switch (num) {
            case 1:
                return "a";
            case 2:
                return "b";
            case 3:
                return "c";
            case 4:
                return "d";
            case 5:
                return "e";
            case 6:
                return "f";
            case 7:
                return "g";
            case 8:
                return "h";
        }
        return "";
    }
    private int colStrToNum(String str){
        switch (str) {
            case "a":
                return 1;
            case "b":
                return 2;
            case "c":
                return 3;
            case "d":
                return 4;
            case "e":
                return 5;
            case "f":
                return 6;
            case "g":
                return 7;
            case "h":
                return 8;
        }
        return 0;
    }
    public String zapisReader(String zapis){
        int xFrom=-1;
        char yFrom=' ';
        int xTo=-1;
        char yTo=' ';
        if(Character.isUpperCase(zapis.charAt(0))){
            zapis=zapis.substring(1);            
        }
        for(int i=0;i<zapis.length();i++){
            if(Character.isLowerCase(zapis.charAt(i)) && zapis.charAt(i)!='x'){
                if(xFrom==-1){
                    xFrom=colStrToNum(String.valueOf(zapis.charAt(i)));
                    yFrom=zapis.charAt(i+1);
                }else{
                    xTo=colStrToNum(String.valueOf(zapis.charAt(i)));
                    yTo=zapis.charAt(i+1);
                }
            }
            
        }
        //System.out.println(xFrom);
        //System.out.println(yFrom);
        //System.out.println(xTo);
        //System.out.println(yTo);
        return Integer.toString(xFrom)+yFrom+"="+xTo+yTo;
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
