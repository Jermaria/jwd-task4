package by.epam.task4.entity_storage;

public class WarehouseException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public WarehouseException() {}
    
    public WarehouseException(Exception e) {       
        super(e);
    }
    
    public WarehouseException(String message, Exception e) {
        super(message, e);
    }
    
    public WarehouseException(String message) {
        super(message);
    }

}
