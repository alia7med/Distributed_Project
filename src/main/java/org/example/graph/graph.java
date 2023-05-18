package org.example.graph;


import java.util.*;

public class graph {

    public static class edge{
        public int start_node;
        public int end_node;

        public edge(int start_node, int end_node) {
            this.start_node = start_node;
            this.end_node = end_node;
        }
    }

    private HashMap<Integer, HashSet<Integer>> adj_list;
    public graph(ArrayList<edge> edges) {
        adj_list = new HashMap<>();
        for(int i = 0 ; i < edges.size();i ++){
            add(edges.get(i).start_node, edges.get(i).end_node);
        }
    }

    private void add_node(int node){
        if(!adj_list.containsKey(node)){
            adj_list.put(node, new HashSet<Integer>());
        }
    }
    public void add(int start_node, int end_node ){
        add_node(start_node);
        add_node(end_node);
        adj_list.get(start_node).add(end_node);
    }
    public void delete(int start_node, int end_node){
        if(adj_list.containsKey(start_node)){
            adj_list.get(start_node).remove(end_node);
        }
    }
    private class Node{
        public int node;
        public int dist;

        public Node(int node, int dist) {
            this.node = node;
            this.dist = dist;
        }
    }
    private int BFS(int start_node, int end_node){
        Queue<Node> queue =  new LinkedList<>();
        queue.add(new Node(start_node, 0));
        HashSet<Integer> visited = new HashSet<>();
        visited.add(start_node);
        while ( !queue.isEmpty() ){
            Node top = queue.poll();
            if(top.node == end_node){
                return top.dist;
            }
            for(int node : adj_list.get(top.node)){
                if(!visited.contains(node)) {
                    queue.add(new Node(node, top.dist + 1));
                    visited.add(node);
                }
            }
        }
        return  -1;
    }
    public int query(int start_node, int end_node){
        if(start_node == end_node && adj_list.containsKey(start_node))
            return 0;

        return BFS(start_node,end_node);
    }
}
