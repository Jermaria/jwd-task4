package by.epam.task4.validation;

public class ValidationException extends Exception {

    private static final long serialVersionUID = 1L;

    public ValidationException() {}

    public ValidationException(Exception e) {
        super(e);
    }

    public ValidationException(String message, Exception e) {
        super(message, e);
    }

    public ValidationException(String message) {
        super(message);
    }

}
