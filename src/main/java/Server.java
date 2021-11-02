import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.IServerListener;

import java.io.IOException;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class Server {
    int puerto = 4455;
    CallHandler callHandler = new CallHandler();
    net.sf.lipermi.net.Server server = new net.sf.lipermi.net.Server();
    String[] words = new String[] {"PREGUNTA", "SERVIDOR", "CLIENTE", "PROGRAMACION", "ORDENADOR"};

    public Server() throws LipeRMIException, IOException {
        Remote ImplementacionDeInterfaz = UnicastRemoteObject.exportObject(new Interfaz() {
            int fallos = 0;
            String word = "";
            final ArrayList<Character> status = new ArrayList<>();

            @Override
            public String guess(char letter) throws RemoteException {
                boolean found = false;
                for (int i = 0; i < word.length(); i++) {
                    if( String.valueOf(letter).toUpperCase(Locale.ROOT).equals(String.valueOf(word.charAt(i)))) {
                        found = true;
                        status.set(i, letter);
                    }
                }
                if (!found) {
                    fallos++;
                }
                if (status.stream().map(Objects::toString).collect(Collectors.joining("")).equalsIgnoreCase(word)){
                    fallos = -1;
                }
                System.out.println("Status: " + status.stream().map(Objects::toString).collect(Collectors.joining(" ")));
                System.out.println("Word: " + word);
                return status.stream().map(Objects::toString).collect(Collectors.joining(" "));
            }

            @Override
            public String init() throws RemoteException {
                word = words[(int)(Math.random() * (4)+1)];
                for (int i = 0; i < word.length(); i++) {
                    status.add('_');
                }
                System.out.println("Word: " + word);
                return status.stream().map(Objects::toString).collect(Collectors.joining(" "));
            }

            @Override
            public int fallos() throws RemoteException {
                System.out.println("Fallos: " + fallos);
                return fallos;
            }
        }, 0);

        callHandler.registerGlobal(Interfaz.class, ImplementacionDeInterfaz);

        server.bind(puerto, callHandler);

        server.addServerListener(new IServerListener() {
            @Override
            public void clientConnected(Socket socket) {
                System.out.println("Usuario Conectado desde: " + socket.getInetAddress());
            }

            @Override
            public void clientDisconnected(Socket socket) {
                System.out.println("Usuario Desconectado");
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main (String[] args) throws LipeRMIException, IOException {
        new Server();
        System.out.println("Server started");
    }
}