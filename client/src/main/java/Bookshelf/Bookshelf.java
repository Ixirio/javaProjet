package Bookshelf;

import MySocket.MySocket;
import org.json.JSONObject;
import java.util.Arrays;

/*
 * Bookshelf class
 * */
public class Bookshelf {

    private final InputManager inputManager = new InputManager();
    private final AskHelper askHelper = new AskHelper();
    private MySocket socket;
    private JSONObject request;
    private static boolean isRunning = true;

    // méthode pour demander à l'utilisateur quelle action il veut réaliser
    private Bookshelf askForAction() {
        System.out.println("""
        Que voulez vous faire ?
            1.Créer une nouvelle entité\s
            2.Supprimer une entité\s
            3.Visualiser une ou plusieurs entités\s
            4.Quitter
        """);
        int action = this.inputManager.getUserIntInput();
        // on vérifie que l'utilisateur a rentrer uen valeur attendue, sinon on réappelle la méthode
        if (!Arrays.asList(1, 2, 3, 4).contains(action)) {
            System.out.println("Erreur, valeur invalide");
            this.askForAction();
        } else {
            // on ajoute à la requete l'action
            switch (action) {
                case 1 -> this.request.put("action", "create");
                case 2 -> this.request.put("action", "delete");
                case 3 -> this.request.put("action", "visualize");
                case 4 -> this.request.put("action", "stop");
            }
        }
        return this;
    }

    // méthode pour demander à l'utilisateur sur quelle entité il veut agir
    private Bookshelf askForEntity() {
        String action = switch(request.getString("action")){
            case "create" -> "créer";
            case "delete" -> "supprimer";
            case "visualize" -> "visualiser";
            default -> "impossible";
        };
        System.out.println(
            "Quelle entité voulez-vous " + action + " ?\n" +
            "   1.Livre\n" +
            "   2.Lecteur"
        );
        int entity = this.inputManager.getUserIntInput();
        // on vérifie que l'utilisateur a rentrer une valeur attendue, sinon on réappelle la méthode
        if (!Arrays.asList(1, 2).contains(entity)) {
            System.out.println("Erreur, valeur invalide.");
            this.askForEntity();
        } else {
            // on ajoute à la requete l'entité
            switch (entity) {
                case 1 -> this.request.put("entity", "book");
                case 2 -> this.request.put("entity", "reader");
            }
        }
        return this;
    }

    // méthode pour demander à l'utilisateur la méthode pour récupérer les entités via le serveur
    private Bookshelf askForMethod() {
        if (request.getString("action").equals("visualize")) {
            System.out.println("""
                Que methode voulez-vous utiliser ?
                    1.getById\s
                    2.getAll
            """);
            int method = this.inputManager.getUserIntInput();
            // on vérifie que l'utilisateur a rentrer uen valeur attendue, sinon on réappelle la méthode
            if (!Arrays.asList(1, 2).contains(method)) {
                System.out.println("Erreur, valeur invalide.");
                this.askForMethod();
            } else {
                // on ajoute à la requete la methode
                switch (method) {
                    case 1 -> this.request.put("method", "getById");
                    case 2 -> this.request.put("method", "getAll");
                }
            }
        }
        return this;
    }

    // methode permettant de demander à l'utilisateur l'id d'une entité si il veut la voir
    private Bookshelf askForId() {
        if (
            request.getString("action").equals("delete")
            || request.getString("action").equals("visualize")
            && request.getString("method").equals("getById")
        ) {
            String action = switch(request.getString("action")){
                case "delete" -> "supprimer";
                case "visualize" -> "visualiser";
                default -> "impossible";
                // sans cette ligne le code ne compile pas car il pense qu'il peut trouver d'autre type d'action.
                // Cependant on les exclue avec la condition du début de la methode
            };
            // on demande à l'utilisateur l'id
            this.askHelper
                .ask(this.request, "id", "Quelle est l'id de l'entité que vous voulez " + action + " ?", DataType.INT)
            ;
        }
        return this;
    }

    // methode demandant à l'utilisateur les données d'un livre
    private void askForBookData() {
        JSONObject book = new JSONObject(); // instanciation de l'objet json qui va stocker les propriétés du lecteur

        // on utilise le askHelper pour poser toutes les question à l'utilisateur
        // et pour ajouter la réponse dans la bonne propriété
        this.askHelper
            .ask(book, "title", "Quelle est le nom du livre ?", DataType.STRING)
            .ask(book, "isbn", "Quelle est l'isbn ?", DataType.STRING)
            .ask(book, "description", "Quelle est la description ?", DataType.STRING)
            .ask(book, "author", "Qui est l'auteur ?", DataType.STRING)
            .ask(book, "pages", "Combien y a t'il de pages ?", DataType.INT)
        ;
        this.request.put("book", book);
    }

    // methode demandant à l'utilisateur les données d'un lecteur
    private void askForReaderData() {
        JSONObject reader = new JSONObject(); // instanciation de l'objet json qui va stocker les propriétés du lecteur
        // on utilise le askHelper pour poser toutes les question à l'utilisateur
        // et pour ajouter la réponse dans la bonne propriété
        this.askHelper
            .ask(reader, "firstName", "Quelle est le prénom du lecteur ?", DataType.STRING)
            .ask(reader, "lastName", "Quelle est le nom du lecteur ?", DataType.STRING)
        ;
        this.request.put("reader", reader);
    }

    // method permettant de demander à l'utilisateur les données d'une entité
    private Bookshelf askForData() {
        if (request.getString("action").equals("create")){
            switch (this.request.getString("entity")) {
                case "book" -> this.askForBookData();
                case "reader" -> this.askForReaderData();
            }
        }
        return this;
    }

    // methode permettant d'envoyer la requete utilisateur au serveur
    private void sendRequest() {
        socket.sendOutputStream(this.request.toString());
    }

    // boucle principale de la bibliothèque
    public void mainLoop() {
        while (isRunning) {
            // on établie la connexion au serveur à chaque itération
            this.socket = new MySocket();
            this.request = new JSONObject();
            // on demande à l'utilisateur ce qu'il veut faire
            this.askForAction();
            // arret du client et du serveur s'il l'action est stop
            if (request.get("action").equals("stop")) {
                System.out.println("Au revoir");
                // on informe le serveur de la fin de la communication
                this.socket.sendOutputStream(request.toString()).closeSocket();
                isRunning = false;
                break;
            }
            // on appelle les methodes pour demander à l'utilisateur ce qu'il veut faire
            // selon l'action qu'il a renseigné au préalable
            this
                .askForEntity()
                .askForData()
                .askForMethod()
                .askForId()
                .sendRequest()
            ;
            // on affiche la réponse du serveur
            System.out.println(socket.getInputStream());
        }
    }
}
