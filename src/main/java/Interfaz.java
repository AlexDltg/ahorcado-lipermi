import java.rmi.Remote;
import java.rmi.RemoteException;

/*
	Declarar firma de métodos que serán sobrescritos
*/
public interface Interfaz extends Remote {
    String guess(char letter) throws RemoteException;

    String init() throws RemoteException;

    int fallos() throws RemoteException;
}