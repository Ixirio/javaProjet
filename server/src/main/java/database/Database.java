package database;

import java.sql.*;

/*
* Database class
* */
public class Database {

    private final String driverName = "com.mysql.cj.jdbc.Driver";
    private final String databaseUrl = "jdbc:mysql://localhost:3306/mabd";
    private Connection connexion = null;
    private Statement statement = null;

    // Database constructeur
    public Database() {
        this
            .connect()
            .createStatement()
            .createTablesIfNotExists()
        ;
    }

    // fonction permettant d'établir la connexion à la bdd
    private Database connect() {
        try {
            Class.forName(this.driverName);
        } catch(ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            this.connexion = DriverManager.getConnection(this.databaseUrl, "root", "root");
            System.out.println("connexion elaborée");
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    // méthode permettant de créer les tables en base de données si elles n'existent pas
    private void createTablesIfNotExists() {
        this.executeStatement(
            "CREATE TABLE IF NOT EXISTS Book (" +
                "id INTEGER NOT NULL AUTO_INCREMENT," +
                "title VARCHAR(255) NOT NULL," +
                "isbn VARCHAR(255) NOT NULL," +
                "description VARCHAR(500)," +
                "pages INTEGER NOT NULL," +
                "author VARCHAR(255)," +
                "PRIMARY KEY (id)" +
            ");"
        );
        this.executeStatement(
            "CREATE TABLE IF NOT EXISTS Reader (" +
                "id INTEGER NOT NULL AUTO_INCREMENT," +
                "firstname VARCHAR(255) NOT NULL," +
                "lastname VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (id)" +
            ");"
        );
    }

    // méthode permettant de créer un statement
    private Database createStatement() {
        try {
            this.statement = this.connexion.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    // fonction permettant d'éxécuter des requetes sql de type INSERT, UPDATE, DELETE
    // retourne soit le nombre de colonne affecté soit 0
    public int executeUpdateStatement(String query) {
        try {
            return this.statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // fonction permettant d'executer une requete sql SELECT
    // retourne les colonnes trouvés via la requete
    public ResultSet executeSelectQuery(String query) {
        try {
            return this.statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // méthode permettant d'executer une requete
    private void executeStatement(String query) {
        try {
            this.statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // méthode utilisé pour fermer la connexion à la base de donnée
    public void close() {
        try {
            this.statement.close();
            this.connexion.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
