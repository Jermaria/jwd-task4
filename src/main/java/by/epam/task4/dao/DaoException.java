package by.epam.task4.dao;

public class DaoException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public DaoException() {}
    
    public DaoException(Exception e) {       
        super(e);
    }
    
    public DaoException(String message, Exception e) {
        super(message, e);
    }
    
    public DaoException(String message) {
        super(message);
    }

}
