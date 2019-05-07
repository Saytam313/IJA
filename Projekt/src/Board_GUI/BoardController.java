/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board_GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 *
 * @author Šimon
 */
public class BoardController implements Initializable {

    @FXML
    private static int VyberFigurky=0;
    private static char VyberX; 
    private static char VyberY;
    private static Button VyberButton;
    private static String VyberButtonStyle;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }  
        

    @FXML
    private void fieldClick(ActionEvent event) {
        char x=event.getSource().toString().charAt(15);
        char y=event.getSource().toString().charAt(16);
        
        if(VyberFigurky==0){
            VyberButton=(Button) event.getSource();
            
            
            VyberButtonStyle=VyberButton.getStyle();
            VyberButton.setStyle(VyberButtonStyle+"-fx-border-color: #373737; -fx-border-width: 5px;");
            VyberFigurky=1;
            VyberX=x;
            VyberY=y;
        }else{
            VyberFigurky=0;
            System.out.println("Move z "+VyberX+VyberY+" na :"+x+y);
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
