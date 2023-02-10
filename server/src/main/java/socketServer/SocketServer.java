package socketServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
* SocketServer class
* */
public class SocketServer {

    private static final int PORT = 1000;
    private static ServerSocket serverSocket;
    private static Socket socket;

    // SocketServer constructeur
    public SocketServer() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // méthode permettant d'accepter les reauetes entrantes
    public void acceptRequest() {
        System.out.println("En attente du client...");
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Fonction permettant de récupérer le contenu de la requete du client
    public String getInputStream() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            return dataInputStream.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Fonction permettant de récuperer le flux sortant
    private DataOutputStream getDataOutputStream() {
        try {
            return new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // méthode permettant de renvoyer un message au client
    public void returnMessageToClient(String message) {
        DataOutputStream dataOutputStream = this.getDataOutputStream();
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
            System.out.println("sent : " + message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // méthode permettant de fermer la conexion au socket
    public void closeSocketServer() {
        try {
            serverSocket.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
