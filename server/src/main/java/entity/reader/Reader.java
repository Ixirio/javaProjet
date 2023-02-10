package entity.reader;

import org.json.JSONObject;

/*
* Book class
* */
public class Reader {

    private int id;
    private String firstName;
    private String lastName;

    public int getId() {
        return this.id;
    }

    public Reader setId(int id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Reader setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Reader setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public String toString() {
        return
            new JSONObject()
                .put("id", this.getId())
                .put("firstName", this.getFirstName())
                .put("lastName", this.getLastName())
                .toString()
        ;
    }

    public String getInsertQuery() {
        return "INSERT INTO Reader (firstname, lastname) VALUES (" +
                "'" + this.getFirstName() + "', " +
                "'" + this.getLastName() + "'" +
            ");"
        ;
    }
}
