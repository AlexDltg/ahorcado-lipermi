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
    // Declaring server port
    int puerto = 4455;
    // Creating a new CallHandler (will controll the connection with users)
    CallHandler callHandler = new CallHandler();
    // Create a new LipeRMI server object
    net.sf.lipermi.net.Server server = new net.sf.lipermi.net.Server();
    // Words array
    String[] words = new String[] {"PREGUNTA", "SERVIDOR", "CLIENTE", "PROGRAMACION", "ORDENADOR"};

    // Server functions (are common with the client thanks to the interface)
    public Server() throws LipeRMIException, IOException {
        Remote ImplementacionDeInterfaz = UnicastRemoteObject.exportObject(new Interfaz() {
            // Control variables
            int fallos = 0;
            String word = "";
            final ArrayList<Character> status = new ArrayList<>();

            // Implement interface functions (common with the client)
            @Override
            public String guess(char letter) throws RemoteException {
                boolean found = false; // Letter in word to guess flag
                // Check if the letter is in the word
                for (int i = 0; i < word.length(); i++) {
                    if( String.valueOf(letter).toUpperCase(Locale.ROOT).equals(String.valueOf(word.charAt(i)))) {
                        found = true;
                        status.set(i, letter);
                    }
                }
                // Increment by 1 the mistakes variable in case the letter is not found
                if (!found) {
                    fallos++;
                }
                // Win condition
                if (status.stream().map(Objects::toString).collect(Collectors.joining("")).equalsIgnoreCase(word)){
                    fallos = -1;
                }
                // Debugging status and word...
                System.out.println("Status: " + status.stream().map(Objects::toString).collect(Collectors.joining(" ")));
                System.out.println("Word: " + word);
                // Return the string with guessed letters to the user.
                return status.stream().map(Objects::toString).collect(Collectors.joining(" "));
            }

            @Override
            public String init() throws RemoteException {
                // Randomly choose a new word.
                word = words[(int)(Math.random() * (4)+1)];
                for (int i = 0; i < word.length(); i++) {
                    status.add('_');
                }
                // Show the chosen word on screen (Debugging purposes)
                System.out.println("Word: " + word);
                return status.stream().map(Objects::toString).collect(Collectors.joining(" "));
            }

            @Override
            public int fallos() throws RemoteException {
                // Return the user the amount of mistakes they have.
                System.out.println("Fallos: " + fallos);
                return fallos;
            }
        }, 0);

        // Make the functions public for clients.
        callHandler.registerGlobal(Interfaz.class, ImplementacionDeInterfaz);

        // Bind the callHandler and port to the server.
        server.bind(puerto, callHandler);

        // Create a server listener, so we can close the ports when users end the session.
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
        // Create a new server, and announce that on console.
        new Server();
        System.out.println("Server started");
    }
}