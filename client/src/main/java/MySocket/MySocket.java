package MySocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/*
 * MySocket class
 * */
public class MySocket {

    private static final int PORT = 1000;

    private static Socket socket;

    private static DataOutputStream dataOutputStream;
    private static DataInputStream dataInputStream;

    // constructeur pour la classe MySocket
    public MySocket() {
        try {
            socket = new Socket("localhost", PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // methode pour envoyer au serveur des données
    public MySocket sendOutputStream(String message) {
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    // methode pour recupérer les données envoyées par le serveur
    public String getInputStream() {
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            return dataInputStream.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // methode permettant de fermer le socket
    public void closeSocket() {
        try {
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
            if (dataInputStream != null) {
                dataInputStream.close();
            }
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
