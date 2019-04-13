/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtool;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private List<Vertex> adjNode = new ArrayList<>();
    
    public Vertex(String label, double x, double y) {
        this.label = new Label(label);
        this.node = new Circle(x, y, 15, Color.CYAN);
        this.label.setTextFill(Color.BLACK);
        setLayoutX(x - 15);
        setLayoutY(y - 15);
        getChildren().addAll(this.node, this.label);
        layout();
    }

    public List<Vertex> getAdjNode() {
        return adjNode;
    }

    public static double getUSE_PREF_SIZE() {
        return USE_PREF_SIZE;
    }

    public static double getUSE_COMPUTED_SIZE() {
        return USE_COMPUTED_SIZE;
    }

    public static double getBASELINE_OFFSET_SAME_AS_HEIGHT() {
        return BASELINE_OFFSET_SAME_AS_HEIGHT;
    }
    
    public void addAdjNode(Vertex v){
        this.adjNode.add(v);
    }

    public Label getLabel() {
        return label;
    }

    public Circle getNode() {
        return node;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.label);
        hash = 97 * hash + Objects.hashCode(this.node);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vertex other = (Vertex) obj;
        if (!Objects.equals(this.label, other.label)) {
            return false;
        }
        if (!Objects.equals(this.node, other.node)) {
            return false;
        }
        if (!Objects.equals(this.adjNode, other.adjNode)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Vertex{" + "label=" + label + ", node=" + node + '}';
    }

}
