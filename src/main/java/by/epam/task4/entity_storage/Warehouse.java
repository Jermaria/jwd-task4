package by.epam.task4.entity_storage;

import java.util.ArrayList;
import java.util.List;
import by.epam.task4.entity.Book;

public class Warehouse {
    private static final Warehouse instance = new Warehouse();
    private List<Book> books;
    
    private Warehouse() {}

    public static Warehouse getInstance() {
        return instance;
    }
    
    public boolean hasBook(Book book) {
        boolean hasBook = books.stream().anyMatch(b -> b.equals(book));
        return hasBook;      
    }
    
    public void addBook(Book book) {
        initIfStorageIsEmpty();
        generateBookId(book);  // as it's usually done by DB, let the warehouse do it too.
        books.add(book);
    }
    
    public void initIfStorageIsEmpty() {
        if (books == null) {
            books = new ArrayList<Book>();
        }
    }
    
    public void generateBookId(Book book) {
        book.setBookId(books.size() + 1);        
    }
    
    public void removeBook(Book book){
        books.remove(book);
    }
    
    public Book getBook(int index) throws WarehouseException {
        if (index < 0 || index > books.size()) {   // вернуться и отдать это дао + лог           
            throw new WarehouseException ("invalid index value " + index + "books size = " + books.size());
        }
        return books.get(index);
    }
    
    public void updateWarehouse(Book book) {    
        if (hasBook(book)) {
            int index = books.stream().filter(b -> b.equals(book)).findFirst().get().getBookId();
            removeBook(book);
            book.setBookId(index);
            addBook(book);
        }
    }

}
