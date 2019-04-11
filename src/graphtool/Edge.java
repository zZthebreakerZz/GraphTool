/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtool;

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
    private StackPane weight;
    private Line line;
    
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
        Label cost = new Label(w);
        cost.setTextFill(Color.WHITE);
        this.weight = new StackPane();
        this.weight.setStyle("-fx-background-color:grey;-fx-border-width:1px;-fx-border-color:black;");
        this.weight.setPrefSize(size, size);
        this.weight.setMaxSize(size, size);
        this.weight.setMinSize(size, size);
        this.weight.getChildren().add(cost);
        
        DoubleBinding wgtSqrHalfWidth = this.weight.widthProperty().divide(2.0);
        DoubleBinding wgtSqrHalfHeight = this.weight.heightProperty().divide(2.0);
        DoubleBinding lineXHalfLength = this.line.endXProperty()
                                            .subtract(this.line.startXProperty())
                                            .divide(2.0);
        DoubleBinding lineYHalfLength = this.line.endYProperty()
                                            .subtract(this.line.startYProperty())
                                            .divide(2.0);
        
        weight.translateXProperty().bind(line.startXProperty().add(lineXHalfLength.subtract(wgtSqrHalfWidth)));
        weight.translateYProperty().bind(line.startYProperty().add(lineYHalfLength.subtract(wgtSqrHalfHeight)));
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

    public StackPane getWeight() {
        return weight;
    }
    
    
}
