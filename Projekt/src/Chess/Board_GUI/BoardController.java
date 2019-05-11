/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chess.Board_GUI;

import Chess.Game.GameFactory;
import Chess.Game.common.Field;
import Chess.Game.common.Figure;
import Chess.Game.common.Game;
import Chess.Game.common.GameClass;
import Chess.Game.game.Board;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Šimon
 */
public class BoardController implements Initializable {
    ObservableList<String>   list=FXCollections.observableArrayList();
    private static int VyberFigurky=0;
    private static boolean StartMove=false;
    private static int VyberX; 
    private static int VyberY;
    private static Button VyberButton;
    private static Button TargetButton;
    private static String VyberButtonStyle;
    private static String TargetButtonStyle;
    private static String VyberButtonFigurka;
    private static Board board;
    private static Game game;
    private static Figure figureStart;
    private static Field fieldStart;
    private static boolean PlayerSwitch=true;
    private static Scene thisScene=null;
    private static String zaznamPos;
    private static int ZaznamCounter=1;
    private static int ZaznamCounterPos=1;
    @FXML
    private ListView<String> zaznamList=new ListView<String>();
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
        board = new Board(8);
        game = GameFactory.createChessGame(board);
        
    }  
 
    private void zaznamAdd(String zaznam){
        //zaznamTable.getItems().addAll(zaznam);
        zaznamList.getItems().addAll(zaznam);
    }  
 
    private void zaznamModify(String zaznam){
        //zaznamTable.getItems().addAll(zaznam);
        String LastZaznam=zaznamList.getItems().get(zaznamList.getItems().size() - 1);
        //System.out.println(LastZaznam);
        zaznam=LastZaznam+"          "+zaznam;
        zaznamList.getItems().remove(zaznamList.getItems().size() - 1);
        zaznamList.getItems().addAll(zaznam);
    }
    @FXML
    private void fieldClick(ActionEvent event) {
        if(thisScene==null){
            Button asd=(Button) event.getSource();
            thisScene=asd.getScene();
        }
        int x=Character.getNumericValue(event.getSource().toString().charAt(15));
        int y=Character.getNumericValue(event.getSource().toString().charAt(16));
        if(!board.getField(x, y).isEmpty()&& board.getField(x, y).get().isWhite()==PlayerSwitch){    
            //System.out.println(board.getField(x, y).get().getState());
            StartMove=true;
        }else{
            //System.out.println("prazdna pozice");   
        }
        if(VyberFigurky==0 && StartMove){
            figureStart=board.getField(x,y).get();
            fieldStart=board.getField(x,y);
            VyberButton=(Button) event.getSource();
            VyberButtonStyle=VyberButton.getStyle();
            VyberButton.setStyle(VyberButtonStyle+"-fx-border-color: #373737; -fx-border-width: 5px;");
            String line = VyberButtonStyle;
            String pattern = "-fx-background-image:.*";
            Pattern regex = Pattern.compile(pattern);
            Matcher match = regex.matcher(line);
            match.find( );
            VyberButtonFigurka=match.group(0);
            VyberFigurky=1;
            VyberX=x;
            VyberY=y;
        }else if(StartMove){
            VyberFigurky=0;
            StartMove=false;
            String zapis=game.move(figureStart, board.getField(x, y));
            if(zapis!=""){
                if(!PlayerSwitch){
                    zaznamModify(zapis);
                }else{
                    zaznamAdd(ZaznamCounter+". "+zapis);
                    ZaznamCounter++;
                }
                //System.out.println(GameClass.zapisCreator(figureStart,board.getField(x, y)));
                //System.out.println("Move z "+VyberX+VyberY+" na :"+x+y+" probehl uspesne");
                //presunuti ikony na sachovnici  
                TargetButton=(Button) event.getSource();
                TargetButtonStyle=TargetButton.getStyle();
                TargetButton.setStyle(TargetButtonStyle+VyberButtonFigurka);
                VyberButtonStyle=VyberButtonStyle.replace(VyberButtonFigurka, "");
                
                PlayerSwitch=!PlayerSwitch;
            }
            else
            {           
                //System.out.println("Move z "+VyberX+VyberY+" na :"+x+y+" failnul");
            }
            
            VyberButton.setStyle(VyberButtonStyle); 
        }
    }
    @FXML
    private void addTab(ActionEvent event) {
        System.out.println("addTab");
    }
    @FXML
    private void resetGame(ActionEvent event) {
        System.out.println("resetGame");
    }
    @FXML
    private void nahraniZaznamu(ActionEvent event) {
        System.out.println("nahraniZaznamu");
    }
    @FXML
    private void zpetButton(ActionEvent event) {
        System.out.println("zpet");
    }
    @FXML
    private void vpredButton(ActionEvent event) {
        System.out.println("vpred");
    }
    @FXML
    private void stopButton(ActionEvent event) {
        System.out.println("stop");
    }
    @FXML
    private void playButton(ActionEvent event) {
        System.out.println("play");
    }
    
    public void zaznamUndo(String zaznam){
        String zapisForm=game.zapisReader(zaznam);
        int xFrom=Character.getNumericValue(zapisForm.charAt(0));
        int yFrom=Character.getNumericValue(zapisForm.charAt(1));
        int xTo=Character.getNumericValue(zapisForm.charAt(3));
        int yTo=Character.getNumericValue(zapisForm.charAt(4));
        
        
        Button FromButton=(Button) thisScene.lookup("#field"+xFrom+yFrom);
        Button ToButton=(Button) thisScene.lookup("#field"+xTo+yTo);
        //System.out.println("field"+xFrom+yFrom+"=="+FromButton.toString());
        //System.out.println("field"+xTo+yTo+"=="+ToButton.toString());
        String line = ToButton.getStyle();
        String pattern = "-fx-background-image:.*";
        Pattern regex = Pattern.compile(pattern);
        Matcher match = regex.matcher(line);
        match.find( );
        String ToButtonFigurka=match.group(0);
            
        Field from=board.getField(xFrom,yFrom);
        Field to=board.getField(xTo,yTo);
        Figure figurka=to.get();
        figurka.reverse_move(from);
        String FromStyle=FromButton.getStyle();
        FromButton.setStyle(FromStyle+ToButtonFigurka);
        
        ToButton.setStyle(line.replace(ToButtonFigurka, ""));
    }
       
    public void zaznamPrev(String zaznam){
        String zapisForm=game.zapisReader(zaznam);
        int xTo=Character.getNumericValue(zapisForm.charAt(0));
        int yTo=Character.getNumericValue(zapisForm.charAt(1));
        int xFrom=Character.getNumericValue(zapisForm.charAt(3));
        int yFrom=Character.getNumericValue(zapisForm.charAt(4));
        
        
        Button FromButton=(Button) thisScene.lookup("#field"+xFrom+yFrom);
        Button ToButton=(Button) thisScene.lookup("#field"+xTo+yTo);
        //System.out.println("field"+xFrom+yFrom+"=="+FromButton.toString());
        //System.out.println("field"+xTo+yTo+"=="+ToButton.toString());
        String line = ToButton.getStyle();
        String pattern = "-fx-background-image:.*";
        Pattern regex = Pattern.compile(pattern);
        Matcher match = regex.matcher(line);
        match.find( );
        String ToButtonFigurka=match.group(0);
            
        Field from=board.getField(xFrom,yFrom);
        Field to=board.getField(xTo,yTo);
        Figure figurka=to.get();
        figurka.reverse_move(from);
        String FromStyle=FromButton.getStyle();
        FromButton.setStyle(FromStyle+ToButtonFigurka);
        
        ToButton.setStyle(line.replace(ToButtonFigurka, ""));
    }
           
    
    @FXML
    private void readZaznam(MouseEvent event) {
        String prvek=zaznamList.getSelectionModel().getSelectedItem();

        if(prvek==null||prvek.isEmpty()){
            System.out.println("nic nevybrany");
        }else{
            String[] Prvek_split=prvek.split("\\.");
            int Zaznam_pos=Integer.parseInt(Prvek_split[0]);
            if(Zaznam_pos<ZaznamCounter){
                int zaznamSize=zaznamList.getItems().size();
                for(int i=ZaznamCounter-2;i>=0;i--){
                    if(zaznamList.getItems().get(i)==prvek){
                        String[] Counter_zaznam=zaznamList.getItems().get(i).split("\\.");
                        String[] Zaznam_parts=Counter_zaznam[1].split("          ");
                        for(String a:Zaznam_parts){
                            zaznamUndo(a);
                        }
                        //zaznamList.getItems().remove(i);
                        ZaznamCounter=Integer.parseInt(Counter_zaznam[0]);
                        PlayerSwitch=true;
                        break;
                    }else{
                        String[] Counter_zaznam=zaznamList.getItems().get(i).split("\\.");
                        String[] Zaznam_parts=Counter_zaznam[1].split("          ");
                        for(String a:Zaznam_parts){
                            zaznamUndo(a);
                        }
                        //zaznamList.getItems().remove(i);
                    }  
                }
            }else{
                int zaznamSize=zaznamList.getItems().size();
                for(int i=ZaznamCounter-1;i<=zaznamSize;i++){
                    if(zaznamList.getItems().get(i)==prvek){
                        String[] Counter_zaznam=zaznamList.getItems().get(i).split("\\.");
                        String[] Zaznam_parts=Counter_zaznam[1].split("          ");
                        
                        zaznamPrev(Zaznam_parts[1]);
                        zaznamPrev(Zaznam_parts[0]);
                        //zaznamList.getItems().remove(i);
                        ZaznamCounter=Zaznam_pos-1;
                        break;
                    }else{
                        String[] Counter_zaznam=zaznamList.getItems().get(i).split("\\.");
                        String[] Zaznam_parts=Counter_zaznam[1].split("          ");
                        zaznamPrev(Zaznam_parts[1]);
                        zaznamPrev(Zaznam_parts[0]);
                        //zaznamList.getItems().remove(i);
                    }  
                }
                
            }
        }
    }
}
