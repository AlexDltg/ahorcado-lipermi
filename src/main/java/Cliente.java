import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;

import java.io.IOException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Interfaz remoteObject;
        char character;
        // connect to the server
        Client client = new Client("192.168.0.189", 4455, new CallHandler());
        // get the server functions
        remoteObject = (Interfaz) client.getGlobal(Interfaz.class);
        // initialize the game getting the word to guess with "_" in every char
        System.out.println(remoteObject.init());
        // main loop where the game occurs
        while (remoteObject.fallos() != 8) {
            // ask the player a letter
            System.out.println("Escribe una letra:");
            character = scanner.next().charAt(0);
            // print the word replacing if correct the letter inputted
            System.out.println(remoteObject.guess(character));
            System.out.println("Fallos:" + remoteObject.fallos());
            if (remoteObject.fallos() == -1) {
                // break loop and finish the game if the player has guessed the word
                System.out.println("Has ganado!!");
                break;
            }
        }
        // close the socket client-side
        client.close();
    }
}
