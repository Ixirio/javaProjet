package socketServer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    private static final int PORT = 8080;
    private static ServerSocket serverSocket;

    private static Socket socket;

    public SocketServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("waiting...");
            socket = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void printInputStream() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            System.out.println("message = " + dataInputStream.readUTF());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeSocketServer() {
        try {
            serverSocket.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
