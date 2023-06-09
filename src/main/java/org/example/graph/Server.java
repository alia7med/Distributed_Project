package org.example.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Server{

    public static void main(String args[]) {
        ArrayList<String> edges = getEdges();
        try {
            DyGraphInterface obj = new DyGraph(edges);
            Registry registry = LocateRegistry.createRegistry(1099);
            synchronized (obj) {
                registry.bind("Update", obj);
            }
            System.out.println("R");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    private static ArrayList<String> getEdges(){
        String fileName = "src/main/java/org/example/graph/edges.txt";
        ArrayList<String> lines = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(!line.equals("S"))
                    lines.add(line);
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }
}
