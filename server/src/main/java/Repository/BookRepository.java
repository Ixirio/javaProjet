package Repository;

import database.Database;
import entity.book.BookFactory;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
* BookRepository class
* */
public class BookRepository {
    private final Database database;
    private final BookFactory bookFactory;

    // BookRepository constructeur
    public BookRepository(Database database, BookFactory bookFactory) {
        this.database = database;
        this.bookFactory = bookFactory;
    }

    // méthode permettant d'inserer un livre dans la bdd
    public void insertBook(String sql) {
        this.database.executeUpdateStatement(sql);
    }

    // méthode permettant de récupérer tout les livres de la bdd
    public String getAllBooks() {
        ResultSet data = this.database.executeSelectQuery("SELECT * FROM Book;");
        StringBuilder books = new StringBuilder("{livres: [\n");
        try {
            // tant qu'il y a des données dans le resultSet on continue l'itération
            while(data.next()) {
                books
                    .append("\t")
                    .append(this.bookFactory.createBookFromSqlRequest(data))
                    .append(",\n")
                ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        books.append("]}");
        return books.toString();
    }

    // fonction permettant de récupérer un livre par id
    public JSONObject getBookById(int id) {
        String sql = "SELECT * FROM Book WHERE id = " + id + ";";
        ResultSet data = this.database.executeSelectQuery(sql);
        JSONObject book = new JSONObject();
        try {
            while(data.next()) {
                book.put("book", this.bookFactory.createBookFromSqlRequest(data));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return book;
    }

    // fonction permettant de supprimer un livre
    public String deleteBook(int id) {
        String sql = "DELETE FROM Book WHERE id = " + id + ";";
        return this.database.executeUpdateStatement(sql) == 1
            ? "Le livre a été supprimé"
            : "Aucun livre trouvé"
        ;
    }

}
