package org.example.graph;


import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList<graph.edge> edges= new ArrayList<>();
        edges.add(new graph.edge(1,2));
        edges.add(new graph.edge(2,3));
        edges.add(new graph.edge(3,1));
        edges.add(new graph.edge(4,1));
        edges.add(new graph.edge(2,4));
         graph g = new graph(edges);
         /** Test batch one from pdf **/
        System.out.println(g.query(1,3));
        g.add(4,5);
        System.out.println(g.query(1,5));
        System.out.println(g.query(5,1));

        /** Test batch two from pdf **/
        g.add(5,3);
        System.out.println(g.query(1,3));
        g.delete(2,3);
        System.out.println(g.query(1,3));

    }
}
