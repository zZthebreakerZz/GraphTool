/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtool;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 *
 * @author junnguyen
 */
public class GraphController extends BorderPane implements Initializable {
    
    @FXML
    private AnchorPane main_pane;
    
    @FXML
    private JFXButton btnAdd, btnDrag, btnRemove, btnConnect;
    
    @FXML
    private JFXComboBox<String> cbxAlgorithm;
    
    boolean addNode = true, 
            removeNode = false, 
            dragNode = false, 
            connectNode = false,
            directed = false,
            undirected = false,
            dijkstra = false;
    
    int totalNode = 0;
    
    double orgSceneX, orgSceneY,
           orgTranslateX, orgTranslateY;
    
    Vertex selectedNode = null;
    
    PathTransition ptr;
    
    Path path;
    
    Circle circle;
    
    Graph g = new Graph();
        
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
        connectNode = false;
        selectedBtn(this.btnAdd);
        resetBtn(btnRemove);
        resetBtn(btnDrag);
        resetBtn(btnConnect);
        resetNodes();
    }
    
    @FXML
    private void removeHandle(ActionEvent event){
        addNode = false;
        removeNode = true;
        dragNode = false;
        connectNode = false;
        selectedBtn(this.btnRemove);
        resetBtn(btnAdd);
        resetBtn(btnDrag);
        resetBtn(btnConnect);
        resetNodes();
        addGraphEvent(onRemoveEvent);
    }
    
    @FXML
    private void dragHandle(ActionEvent event){
        addNode = false;
        removeNode = false;
        dragNode = true;
        connectNode = false;
        selectedBtn(this.btnDrag);
        resetBtn(btnAdd);
        resetBtn(btnRemove);
        resetBtn(btnConnect);
    }
    
    @FXML
    private void connectHandle(ActionEvent event){
        selectedNode = null;
        addNode = false;
        removeNode = false;
        dragNode = false;
        connectNode = true;
        selectedBtn(this.btnConnect);
        resetBtn(btnAdd);
        resetBtn(btnRemove);
        resetBtn(btnDrag);
        resetNodes();
        addGraphEvent(onAddEdgeEvent);
    }
    
    @FXML
    private void addNodeHandle(MouseEvent event) {
        System.out.println("You clicked me!");
        if (addNode){
            String total = Character.toString((char)(totalNode + 65));
            Vertex vertex = new Vertex(total, event.getX(), event.getY());
            totalNode += 1;
            g.addNode(vertex);
            vertex.setOnMousePressed(onPressedEvent);
            vertex.setOnMouseDragged(onDraggedEvent);
            main_pane.getChildren().add(vertex);
        }
    }
    
    @FXML
    private void optAlgorithm(ActionEvent e){
        System.out.println(cbxAlgorithm.getValue());
        if (cbxAlgorithm.getValue().equals("Dijkstra")){
            dijkstra = true;
            resetNodes();
            addGraphEvent(onDijkstraEvent);
        }
    }
    
    EventHandler<MouseEvent> onDijkstraEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            if (dijkstra){
                Vertex node = (Vertex) t.getSource();
                node.getNode().setFill(Color.ORANGE);
                if(selectedNode != null){
                    main_pane.getChildren().removeAll(circle, path);
                    DijkstraAlgorithm algDijkstra = new DijkstraAlgorithm(g);
                    algDijkstra.execute(selectedNode);
                    LinkedList<Vertex> path = algDijkstra.getPath(node);
                    for (Vertex vertex : path) {
                        System.out.println(vertex.toString());
                    }
                    animationAlg(path);
                    node.getNode().setFill(Color.CYAN);
                    selectedNode.getNode().setFill(Color.CYAN);
                    selectedNode = null;
                } else {
                    selectedNode = node;
                }
            } 
        }
    };
    
    EventHandler<MouseEvent> onAddEdgeEvent = new EventHandler<MouseEvent>() {
        
        @Override
        public void handle(MouseEvent e){
            if(connectNode) {
                Vertex node = (Vertex)e.getSource();
                node.getNode().setFill(Color.ORANGE);
                if(selectedNode != null){
                    String weight = showInputDialog();
                    Edge edge = edgeExist(selectedNode, node);
                    if (edge != null){
                        g.removeEdge(edge);
                        main_pane.getChildren().removeAll(edge.getWeightPane(), edge.getLine());
                    }
                    edge = new Edge(selectedNode, node, weight);
                    edge.getWeightPane().setOnMouseClicked(onRemoveEvent);
                    edge.getLine().setOnMouseClicked(onRemoveEvent);
                    g.addEdge(edge);
                    selectedNode.addAdjNode(node);
                    node.getNode().setFill(Color.CYAN);
                    selectedNode.getNode().setFill(Color.CYAN);
                    selectedNode = null;
                    main_pane.getChildren().addAll(edge.getLine(), 
                                                   edge.getWeightPane());
                    edge.getWeightPane().toBack();
                    edge.getLine().toBack();
                } else {
                    selectedNode = node;
                }
            }
        }
    };
    
    EventHandler<MouseEvent> onRemoveEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            if(removeNode){
                if (t.getSource() instanceof Vertex) {
                    g.removeNode((Vertex) t.getSource());
                    main_pane.getChildren().remove(t.getSource());
                } else if (t.getSource() instanceof Line){
                    Line line = (Line) t.getSource();
                    Edge edge = g.getEdge(line);
                    g.removeEdge(edge);
                    main_pane.getChildren().removeAll(edge.getWeightPane(), edge.getLine());
                } else {
                    StackPane weight = (StackPane) t.getSource();
                    Edge edge = g.getEdge(weight);
                    g.removeEdge(edge);
                    main_pane.getChildren().removeAll(edge.getLine(), edge.getWeightPane());
                }
            }
        }
    };
    
    EventHandler<MouseEvent> onPressedEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            
            Vertex node = (Vertex) t.getSource();
            
            orgTranslateX = node.getTranslateX();
            orgTranslateY = node.getTranslateY();
        }
    };
    
    EventHandler<MouseEvent> onDraggedEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            if(dragNode){
                double offsetX = t.getSceneX() - orgSceneX;
                double offsetY = t.getSceneY() - orgSceneY;
                double newTranslateX = orgTranslateX + offsetX;
                double newTranslateY = orgTranslateY + offsetY;

                Vertex node = (Vertex) t.getSource();

                node.setTranslateX(newTranslateX);
                node.setTranslateY(newTranslateY);

            }
        }
    };
    
    private Edge edgeExist(Vertex s, Vertex d) {
        for(Edge e: g.getEdges()){
            if (e.getSource().equals(s) && e.getDest().equals(d)){
                return e;
            }
        }
        return null;
    }
    
    private void selectedBtn(JFXButton btn){
        btn.setTextFill(Color.WHITE);
        btn.setStyle("-fx-background-color: #337ab7");
    }
    
    private void resetBtn(JFXButton btn){
        btn.setTextFill(Color.BLACK);
        btn.setStyle(null);
    }
    
    private void resetNodes(){
        for(Vertex vertex: g.getNodes()){
            vertex.setOnMouseClicked(null);
            vertex.getNode().setFill(Color.CYAN);
        }
    }
    
    private void addGraphEvent(EventHandler<MouseEvent> e){
        for(Vertex vertex: g.getNodes()){
            vertex.setOnMouseClicked(e);
        }
    }
    
    private void setUpCbx(){
        cbxAlgorithm.getItems().add("Dijkstra");
    }
    
    private void animationAlg(LinkedList<Vertex> vertexes){
        path = new Path();
        path.getElements().add(new MoveTo(vertexes.get(0).getLayoutX() + 15, 
                                          vertexes.get(0).getLayoutY() + 15));
        for(int i = 1; i < vertexes.size(); i++){
            path.getElements().add(new LineTo(vertexes.get(i).getLayoutX() + 15, 
                                              vertexes.get(i).getLayoutY() + 15));
        }
        circle = new Circle(vertexes.get(0).getLayoutX() + 15, vertexes.get(0).getLayoutY() + 15, 10);
        circle.setFill(Color.RED);
        
        ptr = new PathTransition();

        ptr.setDuration(Duration.seconds(3));
        ptr.setDelay(Duration.seconds(1));
        ptr.setPath(path);
        ptr.setNode(circle);
        ptr.setCycleCount(Integer.MAX_VALUE);
        ptr.play();     

        main_pane.getChildren().addAll(path, circle);
        circle.toBack();
        path.toBack();
    }
    
    private String showInputDialog(){
        Dialog dialog = new Dialog();
        dialog.setTitle("Add Edge");
        dialog.setHeaderText("Enter Weight of the Edge :");
        dialog.setResizable(true);

        TextField weight = new TextField("0");
        
        GridPane grid = new GridPane();
        grid.add(weight, 2, 1);
        dialog.getDialogPane().setContent(grid);
        
        ButtonType btnDirected = new ButtonType("Directed", ButtonData.OK_DONE);
        ButtonType btnUndirected = new ButtonType("Undirected", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnUndirected, btnDirected);
 
        dialog.setResultConverter(new Callback<ButtonType, String>() {
            @Override
            public String call(ButtonType b) {

                if (b == btnDirected) {
                    undirected = false;
                    directed = true;
                    return weight.getText();
                }
                undirected = true;
                directed = false;
                return weight.getText();
            }
        });

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            return result.get();
        }
        return "0";
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setUpCbx();
        
        Vertex node1 = new Vertex("A", 15, 15);
        Vertex node2 = new Vertex("A", 65, 15);
        Vertex node3 = new Vertex("A", 115, 15);
        Vertex node4 = new Vertex("A", 165, 15);
        
        

        
    }
    
}
