package Bookshelf;

import java.util.InputMismatchException;
import java.util.Scanner;

/*
* InputManager class
* */
public class InputManager {

    private final Scanner scanner = new Scanner(System.in);

    // fonction retournant une entrée utilisateur etant un entier
    public int getUserIntInput() {
        try {
            return this.scanner.nextInt();
        } catch (InputMismatchException e) {
            return 0;
        }
    }

    // fonction retournant une entrée utilisateur etant une chaine de caractère
    public String getUserStringInput() {
        return this.scanner.nextLine();
    }
}
