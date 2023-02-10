package exception;

/*
* ActionException class
* */
public class ActionException extends Exception {
    public ActionException(String action) {
        super("action inconue : " + action);
    }
}
