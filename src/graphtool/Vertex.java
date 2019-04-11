/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtool;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author junnguyen
 */
public class Vertex extends StackPane{
    private Label label;
    private Circle node;
    
    public Vertex(String label, double x, double y) {
        this.label = new Label(label);
        this.node = new Circle(x, y, 15, Color.CYAN);
        this.label.setTextFill(Color.BLACK);
        setLayoutX(x - 15);
        setLayoutY(y - 15);
        getChildren().addAll(this.node, this.label);
        layout();
    }

    public Label getLabel() {
        return label;
    }

    public Circle getNode() {
        return node;
    }
}
