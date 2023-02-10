import database.Database;
import socketServer.SocketServer;
import socketServer.SocketServerManager;

/*
* Main class
* */
public class Main {

    public static void main(String[] args) {

        // on instancie les classes nécéssaires
        Database database = new Database();
        SocketServerManager socketServerManager = new SocketServerManager(new SocketServer(), database);

        while (socketServerManager.isRunning) {
            socketServerManager.manageRequest(); // on delegue la logique du serveur dans son manager
        }

        database.close(); // fermeture de la connexion avec la bdd
    }
}
