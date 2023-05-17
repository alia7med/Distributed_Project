package org.example.graph;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server{

    public static void main(String args[]) {

        try {
            DyGraphInterface obj = new DyGraph();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("Update", obj);
            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}