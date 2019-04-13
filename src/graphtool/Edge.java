/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtool;

import java.util.Objects;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author junnguyen
 */
public class Edge {
    private Vertex source;
    private Vertex dest;
    private StackPane weightPane;
    private Line line;
    private int weight;
    
    public Edge(Vertex s, Vertex d){
        this.source = s;
        this.dest = d;
                
        this.line = new Line();
        this.line.setStroke(Color.DARKGRAY);
        this.line.setStrokeWidth(2);
        this.line.startXProperty().bind(s.layoutXProperty()
                                    .add(s.translateXProperty()
                                    .add(s.widthProperty().divide(2.0)))
                                  );
        this.line.startYProperty().bind(s.layoutYProperty()
                                    .add(s.translateYProperty()
                                    .add(s.heightProperty().divide(2.0)))
                                  );
        this.line.endXProperty().bind(d.layoutXProperty()
                                    .add(d.translateXProperty()
                                    .add(d.widthProperty().divide(2.0)))
                                  );
        this.line.endYProperty().bind(d.layoutYProperty()
                                    .add(d.translateYProperty()
                                    .add(d.heightProperty().divide(2.0)))
                                  );
    }
    
    public Edge(Vertex s, Vertex d, String w){
        this.source = s;
        this.dest = d;
                
        this.line = new Line();
        this.line.setStroke(Color.DARKGRAY);
        this.line.setStrokeWidth(2);
        this.line.startXProperty().bind(s.layoutXProperty()
                                    .add(s.translateXProperty()
                                    .add(s.widthProperty().divide(2.0)))
                                  );
        this.line.startYProperty().bind(s.layoutYProperty()
                                    .add(s.translateYProperty()
                                    .add(s.heightProperty().divide(2.0)))
                                  );
        this.line.endXProperty().bind(d.layoutXProperty()
                                    .add(d.translateXProperty()
                                    .add(d.widthProperty().divide(2.0)))
                                  );
        this.line.endYProperty().bind(d.layoutYProperty()
                                    .add(d.translateYProperty()
                                    .add(d.heightProperty().divide(2.0)))
                                  );
        
        double size = 20;
        this.weight = Integer.parseInt(w);
        Label cost = new Label(w);
        cost.setTextFill(Color.WHITE);
        this.weightPane = new StackPane();
        this.weightPane.setStyle("-fx-background-color:grey;-fx-border-width:1px;-fx-border-color:black;");
        this.weightPane.setPrefSize(size, size);
        this.weightPane.setMaxSize(size, size);
        this.weightPane.setMinSize(size, size);
        this.weightPane.getChildren().add(cost);
        
        DoubleBinding wgtSqrHalfWidth = this.weightPane.widthProperty().divide(2.0);
        DoubleBinding wgtSqrHalfHeight = this.weightPane.heightProperty().divide(2.0);
        DoubleBinding lineXHalfLength = this.line.endXProperty()
                                            .subtract(this.line.startXProperty())
                                            .divide(2.0);
        DoubleBinding lineYHalfLength = this.line.endYProperty()
                                            .subtract(this.line.startYProperty())
                                            .divide(2.0);
        
        weightPane.translateXProperty().bind(line.startXProperty().add(lineXHalfLength.subtract(wgtSqrHalfWidth)));
        weightPane.translateYProperty().bind(line.startYProperty().add(lineYHalfLength.subtract(wgtSqrHalfHeight)));
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex getDest() {
        return dest;
    }

    public Line getLine() {
        return line;
    }

    public StackPane getWeightPane() {
        return weightPane;
    }
    
    public int getWeight(){
        return weight;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.source);
        hash = 67 * hash + Objects.hashCode(this.dest);
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
        final Edge other = (Edge) obj;
        if (!Objects.equals(this.source, other.source)) {
            return false;
        }
        if (!Objects.equals(this.dest, other.dest)) {
            return false;
        }
        return true;
    }

    
    
}
