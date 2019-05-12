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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 *
 * @author Šimon Matyáš Aleš Tetur
 * ovladani GUI
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
    private static boolean PlayerWhite=true;
    private static Scene thisScene=null;
    private static String zaznamPos;
    private static int ZaznamCounter=1;
    private static boolean ZaznamMove=false;
    private static int ZaznamLineLen;
    private static int ZaznamVpredBraniCounter;
    private static boolean SachMat=false;
    @FXML
    private ListView<String> zaznamList=new ListView<String>();
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
        board = new Board(8);
        game = GameFactory.createChessGame(board);
        
    }  
    /**
     * 
     * @param zaznam 
     * zaznam pro pridani do vypisu zaznamu
     */
    private void zaznamAdd(String zaznam){
        //zaznamTable.getItems().addAll(zaznam);
        zaznamList.getItems().addAll(zaznam);
    }  
    /**
     * 
     * @param zaznam 
     * uprava posledniho zaznamu ve vypisu, prida mezeru a potom parametr zaznam 
     */
    private void zaznamModify(String zaznam){
        //zaznamTable.getItems().addAll(zaznam);
        String LastZaznam=zaznamList.getItems().get(zaznamList.getItems().size() - 1);
        //System.out.println(LastZaznam);
        zaznam=LastZaznam+"          "+zaznam;
        zaznamList.getItems().remove(zaznamList.getItems().size() - 1);
        zaznamList.getItems().addAll(zaznam);
    }    
    /**
     * funkce pro tlacitka sachovnice
     */
    @FXML
    private void fieldClick(ActionEvent event) {
        if(SachMat){
            return;
        }
        if(thisScene==null){
            Button asd=(Button) event.getSource();
            thisScene=asd.getScene();
        }
        int x=Character.getNumericValue(event.getSource().toString().charAt(15));
        int y=Character.getNumericValue(event.getSource().toString().charAt(16));
        if(!board.getField(x, y).isEmpty()&& board.getField(x, y).get().isWhite()==PlayerWhite){    
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
                if(!PlayerWhite){
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
                PlayerWhite=!PlayerWhite;
                if(zapis.charAt(zapis.length()-1)=='#'){
                    SachMat=true;
                }
                if(ZaznamMove){
                    //smazat zbytek zaznamu
                    int zaznamSize=zaznamList.getItems().size();
 
                    for(int i=zaznamSize-2;i>=ZaznamCounter-2;i--){
                        zaznamList.getItems().remove(i);
                    }
                    zaznamSize=zaznamList.getItems().size();
                    ZaznamMove=false;
                }
            }
            else
            {           
                //System.out.println("Move z "+VyberX+VyberY+" na :"+x+y+" failnul");
            }
            VyberButton.setStyle(VyberButtonStyle); 
        }
    }
    /**
     * 
     * funkce tlacitka pro pridani tabu 
     */
    @FXML
    private void addTab(ActionEvent event) {
        if(thisScene==null){
            Button asd=(Button) event.getSource();
            thisScene=asd.getScene();
        }
        System.out.println("addTab");
    }
    /**
     * 
     * tlacitko pro reset hry 
     */
    @FXML
    private void resetGame(ActionEvent event) {
        SachMat=false;
        if(thisScene==null){
            Button asd=(Button) event.getSource();
            thisScene=asd.getScene();
        }
        while(ZaznamCounter-1>0){
            zpetButton(event);
        }
        
    }
    /**
     * 
     * tlacitko pro ulozeni zaznamu 
     */
    @FXML
    private void ulozeniZaznamu(ActionEvent event) {
        
        if(thisScene==null){
            Button asd=(Button) event.getSource();
            thisScene=asd.getScene();
        }
        FileChooser saveFileChooser=new FileChooser();
        saveFileChooser.setTitle("Soubor pro ulozeni zaznamu hry");
        File DefaultSaveDirectory=new File("./SavedGames/");
        saveFileChooser.setInitialDirectory(DefaultSaveDirectory);
        File saveDir= saveFileChooser.showSaveDialog(thisScene.getWindow());        
        String zaznam_string="";
        for(int i=0;i<zaznamList.getItems().size();i++){
            zaznam_string+=zaznamList.getItems().get(i)+"\n";
        }
        try {
            FileOutputStream out = new FileOutputStream(saveDir);     
            out.write(zaznam_string.getBytes());
        } catch (FileNotFoundException ex) {
            //
        } catch (IOException ex) {
            //
        }
    }
    /**
     * 
     * funkce pro tlacitka nahraniZaznamu 
     */
    @FXML
    private void nahraniZaznamu(ActionEvent event) {
        if(thisScene==null){
            Button asd=(Button) event.getSource();
            thisScene=asd.getScene();
        }
        FileChooser openFileChooser=new FileChooser();
        openFileChooser.setTitle("Soubor pro nahrani zaznamu hry");
        File DefaultSaveDirectory=new File("./SavedGames/");
        openFileChooser.setInitialDirectory(DefaultSaveDirectory);
        File openDir= openFileChooser.showOpenDialog(thisScene.getWindow());        
        String zaznam_string="";
        try {
            zaznam_string=new  String(Files.readAllBytes(Paths.get(openDir.toString())));
        } catch (IOException ex) {
            Logger.getLogger(BoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        resetGame(event);
        String[] zaznam_string_parts=zaznam_string.split("\n");
        zaznamList.getItems().clear();
        for(int i=0;zaznam_string_parts.length>i;i++){
            zaznamAdd(zaznam_string_parts[i]);
        }
    }
    /**
     * 
     * funkce pro tlacitko zpetButton 
     */
    @FXML
    private void zpetButton(ActionEvent event) {
        if(thisScene==null){
            Button asd=(Button) event.getSource();
            thisScene=asd.getScene();
        }
        if(ZaznamCounter-1>0){
            int zaznamSize=zaznamList.getItems().size();
            String[] Counter_zaznam=zaznamList.getItems().get(ZaznamCounter-2).split("\\.");
            String[] Zaznam_parts=Counter_zaznam[1].split("          ");
            ZaznamLineLen=Zaznam_parts.length;
            for(String a:Zaznam_parts){
                zaznamUndo(a);
                ZaznamLineLen--;
            }
            //zaznamList.getItems().remove(i);
            ZaznamCounter=Integer.parseInt(Counter_zaznam[0]);
            PlayerWhite=true;                        
            if(ZaznamCounter!=zaznamSize+1){
                ZaznamMove=true;
            }
        }
    }
    /**
     * 
     * funkce pro tlacitko vpredButton
     */
    @FXML
    private void vpredButton(ActionEvent event) {
        if(thisScene==null){
            Button asd=(Button) event.getSource();
            thisScene=asd.getScene();
        }
        int zaznamSize=zaznamList.getItems().size();   
        if(ZaznamCounter-1<zaznamSize){

            String[] Counter_zaznam=zaznamList.getItems().get(ZaznamCounter-1).split("\\.");
            String[] Zaznam_parts=Counter_zaznam[1].split("          ");
            ZaznamLineLen=Zaznam_parts.length;
            ZaznamVpredBraniCounter=0;
            for(int j=Zaznam_parts.length-1;j>=0;j--){  
                zaznamForw(Zaznam_parts[j]);
                ZaznamLineLen--;
            }
            if(ZaznamVpredBraniCounter==2){
                game.Switch_PosledniAPredposledni();
            }
            //game.Switch_PosledniAPredposledni();
            //zaznamList.getItems().remove(i);
             if(zaznamList.getItems().get(zaznamSize-1).length()<15){
                 PlayerWhite=false;
            }

             ZaznamCounter=Integer.parseInt(Counter_zaznam[0])+1;
             if(ZaznamCounter!=zaznamSize+1){
                 ZaznamMove=true;
            }
        }

    }
    /**
     * 
     * funkce pro tlacitko stopButton 
     */
    @FXML
    private void stopButton(ActionEvent event) {
        System.out.println("stop");
    }
    /**
     * 
     * funkce pro tlacitko playButton
     * @throws InterruptedException 
     */
    @FXML
    private void playButton(ActionEvent event) throws InterruptedException {
        if(thisScene==null){
            Button asd=(Button) event.getSource();
            thisScene=asd.getScene();
        }
        int zaznamSize=zaznamList.getItems().size();   
        while(ZaznamCounter-1<zaznamSize){
            vpredButton(event);
        }
    }
    /**
     * funkce pro vraceni se v zaznamu
     * @param zaznam string po ktery se bude zaznam prehravat
     */
    public void zaznamUndo(String zaznam){
        SachMat=false;
        String zapisForm=game.zapisReader(zaznam);
        int xFrom=Character.getNumericValue(zapisForm.charAt(0));
        int yFrom=Character.getNumericValue(zapisForm.charAt(1));
        int xTo=Character.getNumericValue(zapisForm.charAt(3));
        int yTo=Character.getNumericValue(zapisForm.charAt(4));
        
        
        Button FromButton=(Button) thisScene.lookup("#field"+xFrom+yFrom);
        Button ToButton=(Button) thisScene.lookup("#field"+xTo+yTo);
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
        
        if(zapisForm.charAt(2)=='x'){
            Figure Revive;
            if(ZaznamLineLen==2){
                Revive=game.Get_PredposledniDeadFig();
            }else{
                Revive=game.Get_DeadFig();
            }
            String FigureStyle;
            String FigColor;
            if(Revive.isWhite()){
                FigColor="White";
            }else{
                FigColor="Black";
            }
            switch(Revive.getType()){
                case 0:
                    FigureStyle="-fx-background-image: url('assets/"+FigColor+"/pesak.png');";
                    break;
                case 1:
                    FigureStyle="-fx-background-image: url('assets/"+FigColor+"/vez.png');";
                    break;
                case 2:
                    FigureStyle="-fx-background-image: url('assets/"+FigColor+"/strelec.png');";
                    break;
                case 3:
                    FigureStyle="-fx-background-image: url('assets/"+FigColor+"/kun.png');";
                    break;
                case 4:
                    FigureStyle="-fx-background-image: url('assets/"+FigColor+"/kralovna.png');";
                    break;
                case 5:
                    FigureStyle="-fx-background-image: url('assets/"+FigColor+"/kral.png');";
                    break; 
                default:
                    FigureStyle="";
                    break;
            }
            ToButton.setStyle(ToButton.getStyle()+FigureStyle);
            to.put(Revive);
            Revive.put(to);
            
        }
    }
    /**
     * funkce pro pokracovani v zaznamu
     * @param zaznam 
     */   
    public void zaznamForw(String zaznam){
        String zapisForm=game.zapisReader(zaznam);
        int xTo=Character.getNumericValue(zapisForm.charAt(0));
        int yTo=Character.getNumericValue(zapisForm.charAt(1));
        int xFrom=Character.getNumericValue(zapisForm.charAt(3));
        int yFrom=Character.getNumericValue(zapisForm.charAt(4));
        
        
        Button FromButton=(Button) thisScene.lookup("#field"+xFrom+yFrom);
        Button ToButton=(Button) thisScene.lookup("#field"+xTo+yTo);
        String line = ToButton.getStyle();
        String pattern = "-fx-background-image:.*";
        Pattern regex = Pattern.compile(pattern);
        Matcher match = regex.matcher(line);
        match.find( );
        String ToButtonFigurka=match.group(0);
            
        Field from=board.getField(xFrom,yFrom);
        Field to=board.getField(xTo,yTo);
        Figure figurka=to.get();
        game.move(figurka,from);
        String FromStyle=FromButton.getStyle();
        FromButton.setStyle(FromStyle+ToButtonFigurka);
        
        ToButton.setStyle(line.replace(ToButtonFigurka, ""));
        if(zapisForm.charAt(zapisForm.length()-1)=='#'){
            SachMat=true;
        }
        if(zapisForm.charAt(2)=='x'){
            ZaznamVpredBraniCounter++;
        }
    }
    /**
     * funkce pro ovladani tlacitek zaznamu
     * @param event 
     */
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
                        ZaznamLineLen=Zaznam_parts.length;
                        for(String a:Zaznam_parts){
                            zaznamUndo(a);
                            ZaznamLineLen--;
                        }
                        //zaznamList.getItems().remove(i);
                        ZaznamCounter=Integer.parseInt(Counter_zaznam[0]);
                        PlayerWhite=true;                        
                        if(ZaznamCounter!=zaznamSize+1){
                            ZaznamMove=true;
                        }
                        
                        break;                     
                    }else{
                        String[] Counter_zaznam=zaznamList.getItems().get(i).split("\\.");
                        String[] Zaznam_parts=Counter_zaznam[1].split("          ");
                        ZaznamLineLen=Zaznam_parts.length;
                        for(String a:Zaznam_parts){
                            zaznamUndo(a);
                            ZaznamLineLen--;
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
                        
                        for(int j=Zaznam_parts.length-1;j>=0;j--){  
                            zaznamForw(Zaznam_parts[j]); 
                        }
                        //game.Switch_PosledniAPredposledni();
                        //zaznamList.getItems().remove(i);
                        if(zaznamList.getItems().get(zaznamSize-1).length()<15){
                            PlayerWhite=false;
                        }
                        
                        ZaznamCounter=Integer.parseInt(Counter_zaznam[0])+1;
                        if(ZaznamCounter!=zaznamSize+1){
                            ZaznamMove=true;
                        }
                        break;
                    }else{
                        String[] Counter_zaznam=zaznamList.getItems().get(i).split("\\.");
                        String[] Zaznam_parts=Counter_zaznam[1].split("          ");
                        for(int j=Zaznam_parts.length-1;j>=0;j--){  
                            zaznamForw(Zaznam_parts[j]); 
                        }
                    }  
                }
                
            }
        }
    }
}
