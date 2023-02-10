package Repository;

import database.Database;
import entity.reader.ReaderFactory;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
* ReaderRepository class
* */
public class ReaderRepository {
    private final Database database;
    private final ReaderFactory readerFactory;

    // ReaderRepository constructeur
    public ReaderRepository(Database database, ReaderFactory readerFactory) {
        this.database = database;
        this.readerFactory = readerFactory;
    }

    // méthode permettant d'inserer un lecteur dans la bdd
    public void insertReader(String sql) {
        this.database.executeUpdateStatement(sql);
    }

    // méthode permettant de récupérer tout les lecteurs de la bdd
    public String getAllReaders() {
        ResultSet data = this.database.executeSelectQuery("SELECT * FROM Reader;");
        StringBuilder readers = new StringBuilder("{lecteurs: [\n");
        try {
            // tant qu'il y a des données dans le resultSet on continue l'itération
            while(data.next()) {
                readers
                    .append("\t")
                    .append(this.readerFactory.createReaderFromSqlRequest(data))
                    .append("\n")
                ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        readers.append("]}");
        return readers.toString();
    }

    // fonction permettant de récupérer un lecteur par id
    public JSONObject getReaderById(int id) {
        String sql = "SELECT * FROM Reader WHERE id = " + id + ";";
        ResultSet data = this.database.executeSelectQuery(sql);
        JSONObject reader = new JSONObject();
        try {
            while(data.next()) {
                reader.put("reader", this.readerFactory.createReaderFromSqlRequest(data));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reader;
    }

    // fonction permettant de supprimer un lecteur
    public String deleteReader(int id) {
        String sql = "DELETE FROM Reader WHERE id = " + id + ";";
        return this.database.executeUpdateStatement(sql) == 1
            ? "Le lecteur a été supprimé"
            : "Aucun lecteur trouvé"
        ;
    }
}
