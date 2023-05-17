package org.example.graph;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface DyGraphInterface extends Remote {

    String sayHello() throws RemoteException;
    ArrayList<Integer> update(ArrayList<String> operations) throws RemoteException;
     void add(String node1, String node2)throws RemoteException;
     void remove(String node1, String node2)throws RemoteException;
     int query(String node1, String node2)throws RemoteException;
}