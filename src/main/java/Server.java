import net.sf.lipermi.handler.CallHandler;

import java.rmi.RemoteException;

public class Server {
    CallHandler callHandler = new CallHandler();
    Interfaz ImplementacionDeInterfaz = new Interfaz() {
        @Override
        public String guess(String word) throws RemoteException {

            return null;
        }

        @Override
        public String init() throws RemoteException {
            return null;
        }

        @Override
        public Integer attempt() throws RemoteException {
            return null;
        }
    };
    callHandler.registerGlobal(Interfaz.class, ImplementacionDeInterfaz);

    Server server = new Server();
    int puerto = 4455;
    server.bind(puerto, callHandler);

}
