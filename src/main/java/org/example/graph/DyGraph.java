package org.example.graph;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.*;

public class DyGraph extends UnicastRemoteObject implements DyGraphInterface {
    private Graph graph;
    private Graph exp_graph;
    private Logger logger;
    boolean variant = false;
    protected DyGraph(ArrayList<String> edgesLines) throws IOException {
        logger = startLogger();
        ArrayList<Graph.Edge> edges = new ArrayList<>();
        for (String edge: edgesLines) {
            String[] nodes = edge.split(" ");
            edges.add(new Graph.Edge(Integer.parseInt(nodes[0]), Integer.parseInt(nodes[1])));
            log("A", nodes[0],nodes[1]);
        }
        graph = new Graph(edges);
        exp_graph = new Graph(edges);
    }

    @Override
    synchronized public ArrayList<Integer>  update(ArrayList<String> operations, String clientID,
                                                   boolean variant) throws RemoteException {
        logger.logp(Level.INFO,"","", "Client" + clientID);
        this.variant = variant;
        ArrayList<Integer> queryResults = new ArrayList<>();
        for(String operationLine: operations)
        {
            String[] operation = operationLine.split(" ");
            if(operation[0].equals("A"))
                add(operation[1],operation[2]);
            else if(operation[0].equals("D"))
                delete(operation[1],operation[2]);
            else if(operation[0].equals("Q"))
                queryResults.add(query(operation[1],operation[2]));
            log(operation[0], operation[1],operation[2]);
        }
        return queryResults;
    }

    @Override
    public void add(String node1, String node2) {
        if(this.variant){
            exp_graph.add(Integer.parseInt(node1), Integer.parseInt(node2));
        }
        else {
            graph.add(Integer.parseInt(node1), Integer.parseInt(node2));
        }
        log("add", node1, node2);
    }

    @Override
    public void delete(String node1, String node2) {
        if(this.variant){
            exp_graph.delete(Integer.parseInt(node1), Integer.parseInt(node2));
        }
        else {
            graph.delete(Integer.parseInt(node1), Integer.parseInt(node2));
        }
        log("delete", node1, node2);
    }

    @Override
    public int query(String node1, String node2) {
        if(this.variant)
            return exp_graph.query_variant(Integer.parseInt(node1),Integer.parseInt(node2));
        return graph.query(Integer.parseInt(node1), Integer.parseInt(node2));
    }


    private Logger startLogger() throws IOException {
        Logger logger = Logger.getLogger("MyLogger");
        FileHandler fileHandler = new FileHandler("serverlog.txt");
        SimpleFormatter formatter = new SimpleFormatter() {
            @Override
            public synchronized String format(LogRecord record) {
                return record.getLevel() + ": " + record.getMessage() + "\n";
            }
        };
        fileHandler.setFormatter(formatter);
        logger.addHandler(fileHandler);
        return logger;
    }

    private void log(String operation, String node1, String node2){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        String strDate= formatter.format(date);
        if(operation.equals("A"))
            logger.logp(Level.INFO,"","", "Add edge " +node1 + " -> " + node2 + " at "  + strDate);
        else if (operation.equals("D"))
            logger.logp(Level.INFO,"","", "Remove edge between " +node1 + " and " + node2 + " at "  + strDate);
        else if(operation.equals("Q"))
            logger.logp(Level.INFO,"","", "Query " +node1 + " to " + node2 + " at "  + strDate);
    }
}
