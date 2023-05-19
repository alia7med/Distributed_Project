package org.example.graph;


import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {

        ArrayList<Graph.Edge> Edges = new ArrayList<>();
        Edges.add(new Graph.Edge(1,2));
        Edges.add(new Graph.Edge(2,3));
        Edges.add(new Graph.Edge(3,1));
        Edges.add(new Graph.Edge(4,1));
        Edges.add(new Graph.Edge(2,4));
         Graph g = new Graph(Edges);
         /** Test batch one from pdf **/

        System.out.println("Output of Batch one:");
        System.out.println(g.query(1,3));
        g.add(4,5);
        System.out.println(g.query(1,5));
        System.out.println(g.query(5,1));

        /** Test batch two from pdf **/

        System.out.println("Output of Batch Two:");
        g.add(5,3);
        System.out.println(g.query(1,3));
        g.delete(2,3);
        System.out.println(g.query(1,3));


    }
}
