package org.example.graph;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class DyGraph extends UnicastRemoteObject implements DyGraphInterface {


    protected DyGraph() throws RemoteException {
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

    }

    @Override
    public void delete(String node1, String node2) {

    }

    @Override
    public int query(String node1, String node2) {
        return 0;
    }
}
