package socketServer;

import database.Database;
import entity.book.BookManager;
import entity.reader.ReaderManager;
import exception.ActionException;
import exception.EntityException;
import org.json.JSONException;
import org.json.JSONObject;

/*
* SocketServerManager class
* */
public class SocketServerManager {

    private final SocketServer socketServer;

    private final BookManager bookManager;
    private ReaderManager readerManager;
    private JSONObject request;
    public boolean isRunning = true;

    // SocketServerManager constructeur
    public SocketServerManager(SocketServer socketServer, Database database) {
        this.socketServer = socketServer;
        this.bookManager = new BookManager(database);
        this.readerManager = new ReaderManager(database);
    }

    // méthode permettant de gerer une requete selon le type
    private void manageRequestType() {
        // selon l'entité envoyé dans la requete, on délegue la logique.
        // si l'entité est inconnue, une erreur est renvoyé au client
        try {
            switch (this.request.getString("entity")) {
                case "book" -> this.manageBookRequest();
                case "reader" -> this.manageReaderRequest();
                default -> throw new EntityException(this.request.getString("entity"));
            }
        } catch (EntityException e) {
            this.socketServer.returnMessageToClient(e.getMessage());
        }
    }

    // méthode permettant de gerer les requetes
    public void manageRequest() {
        // on accepte les requetes entrantes
        this.socketServer.acceptRequest();
        this.request = new JSONObject(this.socketServer.getInputStream());
        try {
            if (this.request.getString("action").equals("stop")){
                this.stop();
                return;
            }
        } catch (JSONException e) {
            this.socketServer.returnMessageToClient(e.getMessage());
        }
        this.manageRequestType();
    }

    // méthode permettant de gérer les requetes lié au livre
    private void manageBookRequest() {
        try {
            // On retourne au client le resultat de sa requete
            this.socketServer.returnMessageToClient(this.bookManager.manageRequest(this.request));
        } catch (ActionException e) {
            this.socketServer.returnMessageToClient(e.getMessage());
        }
    }

    // méthode permettant de gérer les requetes lié au lecteur
    private void manageReaderRequest() {
        try {
            // On retourne au client le resultat de sa requete
            this.socketServer.returnMessageToClient(this.readerManager.manageRequest(this.request));
        } catch (ActionException e) {
            this.socketServer.returnMessageToClient(e.getMessage());
        }
    }

    // methode permettant de fermer la connexion
    private void stop() {
        this.isRunning = false;
        this.socketServer.closeSocketServer();
    }
}
