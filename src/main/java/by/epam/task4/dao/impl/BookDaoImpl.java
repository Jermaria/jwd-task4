package by.epam.task4.dao.impl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task4.dao.BookDao;
import by.epam.task4.dao.DaoException;
import by.epam.task4.entity.Book;
import by.epam.task4.entity.Cover;
import by.epam.task4.entity_storage.Warehouse;
import by.epam.task4.entity_storage.WarehouseException;
import by.epam.task4.validation.ValidationException;

public class BookDaoImpl implements BookDao {
   
    private static final BookDaoImpl instance = new BookDaoImpl();
    
    private static final Warehouse warehouse = Warehouse.getInstance();
    private static final Logger logger = LogManager.getLogger();
    
    private static final String TITLE = "TITLE";
    private static final String AUTHOR_NAME = "AUTHOR_NAME";
    private static final String AUTHOR_SURNAME = "AUTHOR_SURNAME";
    private static final String PUBLISHER_NAME = "PUBLISHER_NAME";
    private static final String PUBLISHER_COUNTRY = "PUBLISHER_COUNTRY";
    private static final String YEAR = "YEAR";
    private static final String PAGES_NUMBER = "PAGES";
    private static final String PRICE = "PRICE";
    private static final String COVER = "COVER";
    
    private BookDaoImpl() {}

    public static BookDao getInstance() {
        return instance;
    }

    @Override
    public void addBook(Book book) throws DaoException {
        if (warehouse.hasBook(book)) {
            logger.log(Level.ERROR, "failed to add book as it is already in stock");
            throw new DaoException("such book already exists in the storage" + book);
        }
        warehouse.addBook(book);
    }
    
    @Override
    public void removeBook(Book book) throws DaoException {
        if (!warehouse.hasBook(book)) {
            logger.log(Level.ERROR, "failed to remove book as it's not in stock ", book);
            throw new DaoException("the book wasn't found in the storage" + book);
        }
        warehouse.removeBook(book);
    }

    @Override
    public void changeBookData(int bookId, Map<String, Object> newValues) throws ValidationException, DaoException {
        for (Map.Entry<String, Object> value : newValues.entrySet()) {
            resolveMapEntryValues(bookId, value);
        }       
    }
    
    public void resolveMapEntryValues(int bookId, Map.Entry<String, Object> newValue) throws ValidationException, DaoException {
        if (!(newValue.getValue() instanceof String)
            || !(newValue.getValue() instanceof Number)
            || !(newValue.getValue() instanceof Cover)) {
            logger.log(Level.ERROR, "invalid value type", newValue.getValue().getClass().getName());
            throw new DaoException("invalid value type " + newValue.getValue().getClass().getName());
        }
        
        try { 
            if (newValue.getValue() instanceof String) {
                resolveStringDataToUpdate(bookId, newValue);      // throws exception if name/key of the value is invalid
            } else if (newValue.getValue() instanceof Number) {
                resolveNumberDataToUpdate(bookId, newValue);
            } else {
                resolveCoverDataToUpdate(bookId, newValue);     
            }
        } catch (WarehouseException e) {
                throw new DaoException("invalid book index " + bookId);
        }
    }
    
    public void resolveStringDataToUpdate(int bookId, Map.Entry<String, Object> newValue) throws ValidationException, DaoException, WarehouseException {
        String value = (String)(newValue.getValue());
        if (newValue.getKey().equalsIgnoreCase(TITLE)) {
            warehouse.getBook(bookId).setTitle(value);
        } else if (newValue.getKey().equalsIgnoreCase(AUTHOR_NAME)) {
            warehouse.getBook(bookId).getAuthor().setName(value);
        } else if (newValue.getKey().equalsIgnoreCase(AUTHOR_SURNAME)) {
            warehouse.getBook(bookId).getAuthor().setSurname((value));
        } else if (newValue.getKey().equalsIgnoreCase(PUBLISHER_NAME)) {
            warehouse.getBook(bookId).getPublisher().setName((value));
        } else if (newValue.getKey().equalsIgnoreCase(PUBLISHER_COUNTRY)) {
            warehouse.getBook(bookId).getPublisher().setCountry((value));
        } else {
            logger.log(Level.ERROR, "undefined data name", newValue.getKey());
            throw new DaoException("undefined data name " + newValue.getKey());
        }
    }
    
    public void resolveNumberDataToUpdate(int bookId, Map.Entry<String, Object> newValue) throws ValidationException, DaoException, WarehouseException {
        if (newValue.getValue() instanceof Integer) {
            resolveIntegerData(bookId, newValue);
        } else if (newValue.getValue() instanceof BigDecimal && newValue.getKey().equalsIgnoreCase(PRICE)) {
            BigDecimal value = (BigDecimal)newValue.getValue();
            warehouse.getBook(bookId).setPrice(value);
        } else {
            logger.log(Level.ERROR, "undefined data name", newValue.getKey());
            throw new DaoException("undefined data name " + newValue.getKey());
        }
    }
    
    public void resolveIntegerData(int bookId, Map.Entry<String, Object> newValue) throws ValidationException, DaoException, WarehouseException {
        int value = (Integer)newValue.getValue();
        if (newValue.getKey().equalsIgnoreCase(PAGES_NUMBER) && newValue.getValue() instanceof Integer) {
            warehouse.getBook(bookId).setPagesNumber(value);
        } else if (newValue.getKey().equalsIgnoreCase(YEAR)) {
            warehouse.getBook(bookId).setYear(value);
        } else {
            logger.log(Level.ERROR, "undefined data name", newValue.getKey());
            throw new DaoException("undefined data name " + newValue.getKey());
        }
    }
    
    public void resolveCoverDataToUpdate(int bookId, Map.Entry<String, Object> newValue) throws DaoException, WarehouseException {
        if (!newValue.getKey().equalsIgnoreCase(COVER)) {
            logger.log(Level.ERROR, "undefined data name", newValue.getKey());
            throw new DaoException("undefined data name " + newValue.getKey());
        }
        warehouse.getBook(bookId).setCover(Cover.valueOf((String)newValue.getValue()));
    }

    @Override
    public Optional<Book> findBook(Map<String, Object> criteria) throws DaoException {
        Optional<Book> optionalBook = Optional.empty();
        int i = 0;
        try {
            while (warehouse.getBook(i) != null) {
                if (isBookMatchingCriteria(warehouse.getBook(i), criteria)) {
                    optionalBook = Optional.of(warehouse.getBook(i));
                    break;
                }
            }
        } catch (WarehouseException e) {
            logger.log(Level.ERROR, "invalid index of book list", i);
            throw new DaoException("invalid index of book list " + i);
        }     
        return optionalBook;
    }
    
    public boolean isBookMatchingCriteria(Book book, Map<String, Object> criteria) throws DaoException {
        boolean isMatching = true;
        Iterator iterator = criteria.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry)iterator.next();
            if (!isMatchingCriterion(book, entry)) {
                isMatching = false;
                break;
            }
        }        
        return isMatching;
    }
    
    public boolean isMatchingCriterion(Book book, Map.Entry<String, Object> entry) throws DaoException {
        boolean isMatching = false;
        if (entry.getKey().equalsIgnoreCase(TITLE) && book.getTitle().equalsIgnoreCase((String)entry.getValue())
           || entry.getKey().equalsIgnoreCase(AUTHOR_NAME) && book.getAuthor().getName()
                                                               .equalsIgnoreCase((String)entry.getValue())
           || entry.getKey().equalsIgnoreCase(AUTHOR_SURNAME) && book.getAuthor().getSurname()
                                                               .equalsIgnoreCase((String)entry.getValue())
           || entry.getKey().equalsIgnoreCase(PAGES_NUMBER) && book.getPagesNumber() == (int) entry.getValue()
           || entry.getKey().equalsIgnoreCase(PUBLISHER_NAME) && book.getPublisher().getName()
                                                               .equalsIgnoreCase((String)entry.getValue())
           || entry.getKey().equalsIgnoreCase(PUBLISHER_COUNTRY) && book.getPublisher().getCountry()
                                                               .equalsIgnoreCase((String)entry.getValue())) {
                isMatching = true;
        } else {
            throw new DaoException("unsupported search criterion " + entry.getKey());
        }
        return isMatching;
    }
}
