/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtool;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 *
 * @author junnguyen
 */
public class GraphController extends BorderPane implements Initializable {
    
    @FXML
    private AnchorPane main_pane;
    
    @FXML
    private JFXButton btnAdd, btnDrag, btnRemove;
    
    boolean addNode = true, removeNode = false, dragNode = false;
    
    int totalNode = 0;
        
    public GraphController() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("Graph.fxml")
        );
        
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
        } catch(IOException exception){
            throw new RuntimeException(exception);
        }
    }
    
    @FXML
    private void addHandle(ActionEvent event){
        addNode = true;
        removeNode = false;
        dragNode = false;
        selectedBtn(this.btnAdd);
        resetBtn(btnRemove);
        resetBtn(btnDrag);
    }
    
    @FXML
    private void removeHandle(ActionEvent event){
        addNode = true;
        removeNode = false;
        dragNode = false;
        selectedBtn(this.btnRemove);
        resetBtn(btnAdd);
        resetBtn(btnDrag);
    }
    
    @FXML
    private void dragHandle(ActionEvent event){
        addNode = true;
        removeNode = false;
        dragNode = false;
        selectedBtn(this.btnDrag);
        resetBtn(btnAdd);
        resetBtn(btnRemove);
    }
    
    @FXML
    private void addNodeHandle(MouseEvent event) {
        System.out.println("You clicked me!");
        if (addNode){
            String total = Character.toString((char)(totalNode + 65));
            Vertex vertex = new Vertex(total, event.getX(), event.getY());
            totalNode += 1;
            main_pane.getChildren().add(vertex);
        }
    }
    
    EventHandler<MouseEvent> eventAddEdge = new EventHandler<MouseEvent>() {
        
        @Override
        public void handle(MouseEvent e){
            
        }
    };
    
    private void selectedBtn(JFXButton btn){
        btn.setTextFill(Color.WHITE);
        btn.setStyle("-fx-background-color: #337ab7");
    }
    
    private void resetBtn(JFXButton btn){
        btn.setTextFill(Color.BLACK);
        btn.setStyle(null);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }
    
}
