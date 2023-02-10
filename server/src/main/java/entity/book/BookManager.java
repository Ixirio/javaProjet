package entity.book;

import Repository.BookRepository;
import database.Database;
import exception.ActionException;
import org.json.JSONException;
import org.json.JSONObject;

/*
* BookManager class
* */
public class BookManager {

    private final BookFactory bookFactory;

    private final BookRepository bookRepository;

    // constructeur BookManager
    public BookManager(Database database) {
        this.bookFactory = new BookFactory();
        this.bookRepository = new BookRepository(database, this.bookFactory);
    }

    // Fonction permettant de gérer une requete associé à l'entite livre
    public String manageRequest(JSONObject request) throws ActionException {
        switch (request.getString("action")) {
            case "create" -> {
                return this.createBook(request);
            }
            case "visualize" -> {
                return this.visualizeBooks(request);
            }
            case "delete" -> {
                return this.bookRepository.deleteBook(request.getInt("id"));
            }
            default -> throw new ActionException(request.getString("action"));
        }
    }

    // fonction permettant de créer un livre et l'inserer dans la base de donnée
    private String createBook(JSONObject request) {
        Book book = this.bookFactory.createBookFromRequest(request.getJSONObject("book"));
        this.bookRepository.insertBook(book.getInsertQuery());
        return "Le livre a bien été crée";
    }

    // fonction permettant de visualiser un ou plusieurs livres
    private String visualizeBooks(JSONObject request) {
        if (request.getString("method").equals("getById")) {
            return this.getBookById(request.getInt("id"));
        }
        if (request.getString("method").equals("getAll")) {
            return this.getAllBooks();
        }
        return "Méthode inconu";
    }

    // méthode permettant de récupérer tout les livres
    private String getAllBooks() {
        return this.bookRepository.getAllBooks();
    }

    // méthode permettant de récupérer un livre selon un id
    private String getBookById(int id) {
        try {
            return this.bookRepository.getBookById(id).toString();
        } catch (JSONException e) {
            return "Aucun livre trouvé";
        }
    }
}
