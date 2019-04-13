/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtool;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;

/**
 *
 * @author junnguyen
 */
public class Graph {
    private List<Vertex> nodes;
    private List<Edge> edges;
    
    public Graph(){
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();        
    }
    
    public Graph(ArrayList<Vertex> nodes, ArrayList<Edge> edges){
        this.nodes = new ArrayList<>(nodes);
        this.edges = new ArrayList<>(edges);        
    }
    
    public void addNode(Vertex v){
        this.nodes.add(v);
    }
    
    public void addEdge(Edge e){
        this.edges.add(e);
    }
    
    public void removeNode(Vertex v){
        for(Vertex vertex: this.nodes){
            if(vertex.equals(v)){
                this.nodes.remove(vertex);
                break;
            }
        }
    }
    
    public void removeEdge(Edge e){
        for(Edge edge: this.edges){
            if(edge.equals(e)){
                this.edges.remove(edge);
                break;
            }
        }
    }
    
    public Edge getEdge(Line line){
        for(Edge edge: this.edges){
            if(edge.getLine().equals(line)){
                return edge;
            }
        }
        return null;
    }
    
    public Edge getEdge(StackPane weight){
        for(Edge edge: this.edges){
            if(edge.getWeightPane().equals(weight)){
                return edge;
            }
        }
        return null;
    }

    public List<Vertex> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
