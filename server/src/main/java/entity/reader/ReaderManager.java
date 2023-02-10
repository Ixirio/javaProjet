package entity.reader;

import Repository.ReaderRepository;
import database.Database;
import exception.ActionException;
import org.json.JSONException;
import org.json.JSONObject;

/*
* ReaderManager class
* */
public class ReaderManager {

    private final ReaderFactory readerFactory;
    private final ReaderRepository readerRepository;

    // constructeur ReaderManager
    public ReaderManager(Database database) {
        this.readerFactory = new ReaderFactory();
        this.readerRepository = new ReaderRepository(database, this.readerFactory);
    }

    // Fonction permettant de gérer une requete associé à l'entite lecteur
    public String manageRequest(JSONObject request) throws ActionException {
        switch (request.getString("action")) {
            case "create" -> {
                return this.createReader(request);
            }
            case "visualize" -> {
                return this.visualizeReaders(request);
            }
            case "delete" -> {
                return this.readerRepository.deleteReader(request.getInt("id"));
            }
            default -> throw new ActionException(request.getString("action"));
        }
    }

    // fonction permettant de créer un lecteur et l'inserer dans la base de donnée
    private String createReader(JSONObject request) {
        Reader reader = this.readerFactory.createReaderFromRequest(request.getJSONObject("reader"));
        this.readerRepository.insertReader(reader.getInsertQuery());
        return "Le lecteur a bien été ajouté";
    }

    // fonction permettant de visualiser un ou plusieurs lecteurs
    private String visualizeReaders(JSONObject request) {
        if (request.getString("method").equals("getById")) {
            return this.getReaderById(request.getInt("id"));
        }
        if (request.getString("method").equals("getAll")) {
            return this.getAllReaders();
        }
        return "Méthode inconu";
    }

    // méthode permettant de récupérer tout les lecteurs
    private String getAllReaders() {
        return this.readerRepository.getAllReaders();
    }

    // méthode permettant de récupérer un lecteur selon un id
    private String getReaderById(int id) {
        try {
            return this.readerRepository.getReaderById(id).get("reader").toString();
        } catch (JSONException e) {
            return "Aucun lecteur trouvé";
        }
    }
}
