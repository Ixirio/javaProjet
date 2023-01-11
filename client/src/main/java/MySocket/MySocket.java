package MySocket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MySocket {

    private static final int PORT = 8080;
    private static Socket socket;

    private static DataOutputStream dataOutputStream;

    public MySocket() {
        try {
            socket = new Socket("localhost", PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendOutputStream(String message) {
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
            dataOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
