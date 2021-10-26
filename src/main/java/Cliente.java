import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;

import java.io.IOException;

public class Cliente {
    CallHandler callHandler = new CallHandler();
    String remoteHost = "localhost";
    int puerto = 4455;
    Client client = new Client(remoteHost, puerto, callHandler);

    public Cliente() throws IOException {
        Interfaz remoteObject;
        remoteObject = (Interfaz) client.getGlobal(Interfaz.class);
        System.out.println(remoteObject.init());
    }
}
