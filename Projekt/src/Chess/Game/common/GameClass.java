package Chess.Game.common;

import Chess.Game.common.Game;
import Chess.Game.game.Board;
import java.util.Stack;
/**
 *
 * @author Šimon Matyáš Aleš Tetur
 * Ovládá tvorbu zaznamu a zasobnik odebraných figurek
 */
public class GameClass extends java.lang.Object implements Game{
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
        this.Remove_Fig=new Stack<Figure>();
        this.zapis=new Stack<Figure>();
        this.board=board;
    }
    /**
     * polozeni figurek na sachovnici pri zacatku hry
     * @param Figurka Figurka kterou chci polozit
     * @param StartOn Pole kam chci figurku polozit
     */
    public void create(Figure Figurka,Field StartOn){
        

        if(Figurka.getBoard()==this.board){
            StartOn.put(Figurka);
        }
    }
    /**
     * Kontrola tahu v dane hre, vytvoreni zaznamu pro dany pohyb
     * @param figurka Figurka kterou chci pohnout
     * @param moveTo Pole na ktere figurkou hýbu
     * @return string se zapisem
     */
    public String move(Figure figurka,Field  moveTo){
        
        String zapis=zapisCreator(figurka,moveTo);
        //System.out.println(zapisCreator(figurka,moveTo));
        //System.out.println(zapisReader(zapisCreator(figurka,moveTo)));
        boolean removed=false;
        if(moveTo.get()!=null && figurka.isWhite()!=moveTo.get().isWhite()){
            removed=true;
            Add_DeadFig(moveTo.get());
        }
        
        int m = figurka.move(moveTo);
        if(m == 0){ 
            return zapis; 
        } else if(m == 1) {
            return zapis + "+";
        } else if(m == 2) {
            return zapis + "#";
        } else {
            if(removed){
                Remove_DeadFig();
            }
            return "";
        }
    }
    /**
     * Tvorba zapisu
     * @param figurka Figurka kterou chci pohnout
     * @param moveTo Místo na které se ma figurka pohnout
     * @return string se zapisem
     */
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
    /**
     * Prevod souradnice sloupce na pismeno na sachovnici
     * @param num souradnice sloupce
     * @return pismeno na sachovnici
     */
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
    /**
     * Prevod pismena na sachovici na souradnici sloupce
     * @param str pismeno na sachovnici
     * @return souradnice sloupce
     */
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
    /**
     * Prevod zapisu na souradnice 
     * @param zapis zapis
     * @return souradnice odkud a kam se figurka pohla 
     */
    public String zapisReader(String zapis){
        int xFrom=-1;
        char yFrom=' ';
        int xTo=-1;
        char yTo=' ';
        char brani='=';
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
            }else if(zapis.charAt(i)=='x'){
                brani='x';
            }
            
        }
        return Integer.toString(xFrom)+yFrom+brani+xTo+yTo;
    }
    /**
     * Pridani odebrane figurky na zasobnik
     * @param figurka odebrana figurka
     */
    private void Add_DeadFig(Figure figurka){
        Remove_Fig.push(figurka);
    }
    /**
     * Odebrani posledni figurky ze zasobniku
     */
    private void Remove_DeadFig(){
        Remove_Fig.pop();
    }
    /**
     * Vrátí posledni odebranou figurku ze zasobniku a tu ze zasobniku smaže
     * @return posledni figurku
     */
    public Figure Get_DeadFig(){
        Figure posledni=Remove_Fig.pop();
        return posledni;               
    }
    /**
     * Vrátí predposledni figurku a smaze ji ze zasobniku
     * @return predposledni figurka
     */
    public Figure Get_PredposledniDeadFig(){
        if(Remove_Fig.size()>1){
            Figure posledni=Remove_Fig.pop();
            Figure predposledni=Remove_Fig.pop();
            Remove_Fig.push(posledni);
            return predposledni;   
        }else{
            return Get_DeadFig();
        }
    }
    /**
     * prohodi predposledni a posledni figurku v zasobniku odebranych figurek
     */
    public void Switch_PosledniAPredposledni(){
        Figure predposledni=Get_PredposledniDeadFig();
        Figure posledni=Get_DeadFig();
        Remove_Fig.push(posledni);
        Remove_Fig.push(predposledni);
    }
}
