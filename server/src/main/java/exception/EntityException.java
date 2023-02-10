package exception;

/*
* EntityException class
* */
public class EntityException extends Exception {
    public EntityException(String entity) {
        super("L'entité " + entity + " est inconnue");
    }
}
