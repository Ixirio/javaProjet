package entity.book;

import org.json.JSONObject;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
* BookFactory class
* */
public class BookFactory {

    // fonction qui retourne un livre
    private Book createBook() {
        return new Book();
    }

    // fonction retournant un livre basée sur une requete avec un contenu json
    public Book createBookFromRequest(JSONObject request) {
        return this
                .createBook()
                .setTitle(request.getString("title"))
                .setDescription(request.getString("description"))
                .setIsbn(request.getString("isbn"))
                .setAuthor(request.getString("author"))
                .setPages(request.getInt("pages"))
        ;
    }

    // fonction retournant un livre basée sur une requete sql
    public Book createBookFromSqlRequest(ResultSet data) throws SQLException {
        return this
                .createBook()
                .setId(data.getInt("id"))
                .setTitle(data.getString("title"))
                .setDescription(data.getString("description"))
                .setIsbn(data.getString("isbn"))
                .setAuthor(data.getString("author"))
                .setPages(data.getInt("pages"))
        ;
    }
}
