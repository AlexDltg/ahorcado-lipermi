import java.rmi.Remote;
import java.rmi.RemoteException;

/*
	Declarar firma de métodos que serán sobrescritos
*/
public interface Interfaz extends Remote {
    String guess(String word) throws RemoteException;
    String init() throws RemoteException;
    Integer attempt() throws RemoteException;
}