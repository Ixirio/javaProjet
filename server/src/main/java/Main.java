import database.Database;
import socketServer.SocketServer;

public class Main {

    public static void main(String[] args) {
        Database database = new Database();
        SocketServer socketServer = new SocketServer();
        socketServer.printInputStream();

        socketServer.closeSocketServer();
        database.close();

    }

}
