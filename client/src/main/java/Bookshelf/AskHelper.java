package Bookshelf;

import org.json.JSONObject;

import java.util.InputMismatchException;

/*
 * AskHelper class
 * */
public class AskHelper {

    private final InputManager inputManager = new InputManager();

    public AskHelper ask(JSONObject object, String property, String question, DataType type) {
        System.out.println(question);
        switch (type) {
            case STRING -> {
                String response = this.inputManager.getUserStringInput();
                if (response.isEmpty()) {
                    System.out.println("la réponse ne peut pas etre vide.");
                    this.ask(object, property, question, type);
                }
                object.put(property, response);
            }
            case INT -> {
                try {
                    int response = this.inputManager.getUserIntInput();
                    object.put(property, response);
                } catch (InputMismatchException e) {
                    System.out.println("la réponse ne peut pas etre vide.");
                    this.ask(object, property, question, type);
                }
            }
        }
        return this;
    }
}
