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
import Chess.Game.game.Board;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;

/**
 *
 * @author Šimon
 */
public class BoardController implements Initializable {

    @FXML
    private static int VyberFigurky=0;
    private static boolean StartMove=false;
    private static int VyberX; 
    private static int VyberY;
    private static Button VyberButton;
    private static String VyberButtonStyle;
    private static Board board;
    private static Game game;
    private static Field fieldStart;
    private static Figure figureStart;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
        board = new Board(8);
        game = GameFactory.createChessGame(board);
    }  
        

    @FXML
    private void fieldClick(ActionEvent event) {
        int x=Character.getNumericValue(event.getSource().toString().charAt(15));
        int y=Character.getNumericValue(event.getSource().toString().charAt(16));
        if(!board.getField(x, y).isEmpty()){    
            System.out.println(board.getField(x, y).get().getState());
            StartMove=true;
        }else{  
            //System.out.println("prazdna pozice");   
        }
        if(VyberFigurky==0 && StartMove){
            fieldStart=board.getField(x,y);
            figureStart=fieldStart.get();
            VyberButton=(Button) event.getSource();
            VyberButtonStyle=VyberButton.getStyle();
            VyberButton.setStyle(VyberButtonStyle+"-fx-border-color: #373737; -fx-border-width: 5px;");
            VyberFigurky=1;
            VyberX=x;
            VyberY=y;
        }else if(StartMove){
            VyberFigurky=0;
            StartMove=false;
            if(game.move(figureStart, board.getField(x, y))){
                System.out.println("Move z "+VyberX+VyberY+" na :"+x+y+" probehl uspesne");
            }
            else
            {  
               
                System.out.println("Move z "+VyberX+VyberY+" na :"+x+y+" failnul");
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
    
}
