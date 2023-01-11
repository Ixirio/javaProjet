import MySocket.MySocket;

import java.io.IOException;

public class Main {

    private static MySocket socket;

    public static void main(String[] args) {

        MySocket socket = new MySocket();
        socket.sendOutputStream("hello server");
        socket.closeSocket();

    }
}
