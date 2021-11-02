import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;

import java.io.IOException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Interfaz remoteObject;
        char character;
        Client client = new Client("192.168.0.189", 4455, new CallHandler());
        remoteObject = (Interfaz) client.getGlobal(Interfaz.class);
        System.out.println(remoteObject.init());
        while (remoteObject.fallos() != 8) {
            System.out.println("Escribe una letra:");
            character = scanner.next().charAt(0);
            System.out.println(remoteObject.guess(character));
            System.out.println(remoteObject.fallos());
            if (remoteObject.fallos() == -1) {
                System.out.println("Has ganado!!");
                break;
            }
        }
        client.close();
    }
}
