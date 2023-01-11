package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private String driverName = "com.mysql.cj.jdbc.Driver";
    private String databaseUrl = "jdbc:mysql://localhost:3306/mabd";
    private Connection connexion = null;
    private Statement statement = null;

    public Database() {
        this.connect().createStatement();
    }

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

    private Database createStatement() {
        try {
            this.statement = this.connexion.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    private void executeStatement(String query) {
        try {
            this.statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            this.statement.close();
            this.connexion.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
