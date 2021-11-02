import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Interfaz extends Remote {
    String guess(char letter) throws RemoteException;

    int fallos() throws RemoteException;

    String init() throws RemoteException;
}