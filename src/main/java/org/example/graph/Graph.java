package org.example.graph;


import java.util.*;

public class Graph {

    public static class Edge {
        public int start_node;
        public int end_node;

        public Edge(int start_node, int end_node) {
            this.start_node = start_node;
            this.end_node = end_node;
        }
    }

    private HashMap<Integer, HashSet<Integer>> adj_list, adj_list_backward;
    public Graph(ArrayList<Edge> Edges) {
        adj_list = new HashMap<>();
        adj_list_backward = new HashMap<>();
        for(int i = 0; i < Edges.size(); i ++){
            add(Edges.get(i).start_node, Edges.get(i).end_node);
        }
    }

    private void add_node(int node){
        if(!adj_list.containsKey(node)){
            adj_list.put(node, new HashSet<>());
        }
        if(!adj_list_backward.containsKey(node)){
            adj_list_backward.put(node, new HashSet<>());
        }
    }
    public void add(int start_node, int end_node ){
        add_node(start_node);
        add_node(end_node);
        adj_list.get(start_node).add(end_node);
        adj_list_backward.get(end_node).add(start_node);
    }
    public void delete(int start_node, int end_node){
        if(adj_list.containsKey(start_node)){
            adj_list.get(start_node).remove(end_node);
        }
        if(adj_list_backward.containsKey(end_node)){
            adj_list_backward.get(end_node).remove(start_node);
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
    int operate(HashMap<Integer,Integer> visited, HashMap<Integer,Integer> visited_backward,
                Queue<Node> queue, HashMap<Integer, HashSet<Integer>> adj_list ){
        Node top = queue.poll();
        if(visited_backward.containsKey(top.node)){
            return top.dist + visited_backward.get(top.node);
        }
        for(int node : adj_list.get(top.node)){
            if(!visited.containsKey(node)) {
                queue.add(new Node(node, top.dist + 1));
                visited.put(node, top.dist + 1);
            }
        }
        return -1;
    }
    int Bi_BFS(int start_node, int end_node){
        Queue<Node> queue =  new LinkedList<>();
        Queue<Node> queue_backward = new LinkedList<>();
        queue.add(new Node(start_node, 0));
        queue_backward.add(new Node(end_node,0));
        HashMap<Integer,Integer> visited = new HashMap<>();
        HashMap<Integer,Integer> visited_backward = new HashMap<>();
        visited.put(start_node, 0 );
        visited_backward.put(end_node,0);
        while (!queue.isEmpty() && !queue_backward.isEmpty()){
            if(!queue.isEmpty()){
              int ret = operate(visited, visited_backward, queue, adj_list);
              if(ret != -1 ) return ret;
            }
            if(!queue_backward.isEmpty()){
               int ret = operate(visited_backward, visited, queue_backward, adj_list_backward);
               if(ret != -1) return  ret;
            }
        }
        return -1;
    }
    public int query(int start_node, int end_node){
        if(start_node == end_node && adj_list.containsKey(start_node))
            return 0;
        if( !(adj_list.containsKey(start_node) && adj_list.containsKey(end_node)))
            return -1;
        return BFS(start_node,end_node);
    }
}
