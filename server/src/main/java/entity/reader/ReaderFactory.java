package entity.reader;

import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
* ReaderFactory class
* */
public class ReaderFactory {

    // fonction retournant un lecteur
    private Reader createReader() { return new Reader(); }

    // fonction retournant un lecteur basée sur une requete avec un contenu json
    public Reader createReaderFromRequest(JSONObject request) {
        return this.createReader()
                .setFirstName(request.getString("firstName"))
                .setLastName(request.getString("lastName"))
        ;
    }

    // fonction retournant un lecteur basée sur une requete sql
    public Reader createReaderFromSqlRequest(ResultSet data) throws SQLException {
        return this.createReader()
                .setId(data.getInt("id"))
                .setFirstName(data.getString("firstname"))
                .setLastName(data.getString("lastname"))
        ;
    }

}
