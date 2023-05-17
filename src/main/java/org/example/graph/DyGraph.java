import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class DyGraph extends UnicastRemoteObject implements DyGraphInterface{


    protected DyGraph() throws RemoteException {
    }

    @Override
    public String sayHello() throws RemoteException {
        return null;
    }

    @Override
    public ArrayList<Integer> update(ArrayList<String> operations) throws RemoteException {
        return null;
    }

    @Override
    public void add(String node1, String node2) {

    }

    @Override
    public void remove(String node1, String node2) {

    }

    @Override
    public int query(String node1, String node2) {
        return 0;
    }
}
