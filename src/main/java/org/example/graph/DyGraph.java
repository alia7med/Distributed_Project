package org.example.graph;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class DyGraph extends UnicastRemoteObject implements DyGraphInterface {
    private Graph graph;

    protected DyGraph(ArrayList<String> edgesLines) throws RemoteException {
        ArrayList<Graph.Edge> edges = new ArrayList<>();
        for (String edge: edgesLines) {
            String[] nodes = edge.split(" ");
            edges.add(new Graph.Edge(Integer.parseInt(nodes[0]), Integer.parseInt(nodes[1])));
        }
        graph = new Graph(edges);
    }

    @Override
    public String sayHello() throws RemoteException {
        return null;
    }

    @Override
    public ArrayList<Integer> update(ArrayList<String> operations) throws RemoteException {
        ArrayList<Integer> queryResults = new ArrayList<>();
        for(String operationLine: operations)
        {
            String[] operation = operationLine.split(" ");
            if(operation[0] == "A")
                add(operation[1],operation[2]);
            else if(operation[0] == "D")
                delete(operation[1],operation[2]);
            else if(operation[0] == "Q")
                queryResults.add(query(operation[1],operation[2]));
        }
        return queryResults;
    }

    @Override
    public void add(String node1, String node2) {
        graph.add(Integer.parseInt(node1), Integer.parseInt(node2));
    }

    @Override
    public void delete(String node1, String node2) {
        graph.delete(Integer.parseInt(node1), Integer.parseInt(node2));
    }

    @Override
    public int query(String node1, String node2) {
        return graph.query(Integer.parseInt(node1), Integer.parseInt(node2));
    }
}
