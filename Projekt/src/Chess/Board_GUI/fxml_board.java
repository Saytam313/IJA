/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chess.Board_GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Šimon
 */
public class fxml_board extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("Board.fxml"));
        
        //Scene scene = new Scene(root);
        
        //stage.setScene(scene);
        //stage.show();
        
        // getting loader and a pane for the first scene. 
        // loader will then give a possibility to get related controller
        FXMLLoader firstPaneLoader = new FXMLLoader(getClass().getResource("Board.fxml"));
        Parent firstPane = firstPaneLoader.load();
        Scene firstScene = new Scene(firstPane);

        // getting loader and a pane for the second scene
        FXMLLoader secondPageLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent secondPane = secondPageLoader.load();
        Scene secondScene = new Scene(secondPane);
        
        primaryStage.setTitle("Šachy");
        primaryStage.setScene(secondScene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
