/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chess.Board_GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Šimon Matyáš Aleš Tetur
 */
public class MenuController implements Initializable {

    @FXML
    private AnchorPane menuPane;
    @FXML
    private Font x1;
    @FXML
    private Font x2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    @FXML
    private void loadBoard1(ActionEvent event) throws IOException {
        //AnchorPane pane = FXMLLoader.load(getClass().getResource("Board.fxml"));
       // menuPane.getChildren().setAll(pane);
        
        FXMLLoader firstPaneLoader = new FXMLLoader(getClass().getResource("Board.fxml"));
        Parent firstPane = firstPaneLoader.load();
        Scene firstScene = new Scene(firstPane);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(firstScene);
        app_stage.show();
    }
    @FXML
    private void loadBoard2(ActionEvent event) throws IOException {
        //AnchorPane pane = FXMLLoader.load(getClass().getResource("Board.fxml"));
       // menuPane.getChildren().setAll(pane);
        
        FXMLLoader firstPaneLoader = new FXMLLoader(getClass().getResource("Board_RedBlue.fxml"));
        Parent firstPane = firstPaneLoader.load();
        Scene firstScene = new Scene(firstPane);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(firstScene);
        app_stage.show();
    }
    @FXML
    private void loadBoard3(ActionEvent event) throws IOException {
        //AnchorPane pane = FXMLLoader.load(getClass().getResource("Board.fxml"));
       // menuPane.getChildren().setAll(pane);
        
        FXMLLoader firstPaneLoader = new FXMLLoader(getClass().getResource("Board_YellowBlue.fxml"));
        Parent firstPane = firstPaneLoader.load();
        Scene firstScene = new Scene(firstPane);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(firstScene);
        app_stage.show();
    }
    @FXML
    private void loadBoard4(ActionEvent event) throws IOException {
        //AnchorPane pane = FXMLLoader.load(getClass().getResource("Board.fxml"));
       // menuPane.getChildren().setAll(pane);
        
        FXMLLoader firstPaneLoader = new FXMLLoader(getClass().getResource("Board_GreenYellow.fxml"));
        Parent firstPane = firstPaneLoader.load();
        Scene firstScene = new Scene(firstPane);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(firstScene);
        app_stage.show();
    }
    @FXML
    private void end(ActionEvent event) throws IOException {
       Runtime.getRuntime().exit(1);
    }
    @FXML
    private void theme1(ActionEvent event) throws IOException {
        FXMLLoader firstPaneLoaderN = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent firstPaneN = firstPaneLoaderN.load();
        Scene firstSceneN = new Scene(firstPaneN);
        Stage app_stageN = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stageN.setScene(firstSceneN);
        app_stageN.show();
    }
    @FXML
    private void theme2(ActionEvent event) throws IOException {
        FXMLLoader firstPaneLoaderRB = new FXMLLoader(getClass().getResource("Menu_RedBlue.fxml"));
        Parent firstPaneRB = firstPaneLoaderRB.load();
        Scene firstSceneRB = new Scene(firstPaneRB);
        Stage app_stageRB = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stageRB.setScene(firstSceneRB);
        app_stageRB.show();
    }
    @FXML
    private void theme3(ActionEvent event) throws IOException {
        FXMLLoader firstPaneLoaderRB = new FXMLLoader(getClass().getResource("Menu_YellowBlue.fxml"));
        Parent firstPaneRB = firstPaneLoaderRB.load();
        Scene firstSceneRB = new Scene(firstPaneRB);
        Stage app_stageRB = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stageRB.setScene(firstSceneRB);
        app_stageRB.show();
    }
    @FXML
    private void theme4(ActionEvent event) throws IOException {
        FXMLLoader firstPaneLoaderRB = new FXMLLoader(getClass().getResource("Menu_GreenYellow.fxml"));
        Parent firstPaneRB = firstPaneLoaderRB.load();
        Scene firstSceneRB = new Scene(firstPaneRB);
        Stage app_stageRB = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stageRB.setScene(firstSceneRB);
        app_stageRB.show();
    }
}
