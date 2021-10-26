import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Interfaz extends Remote {
    String guess(String word) throws RemoteException;

    String init() throws RemoteException;
}