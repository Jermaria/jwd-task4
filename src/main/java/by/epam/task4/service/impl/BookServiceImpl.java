package by.epam.task4.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.epam.task4.dao.BookDao;
import by.epam.task4.dao.DaoException;
import by.epam.task4.dao.impl.BookDaoImpl;
import by.epam.task4.entity.Book;
import by.epam.task4.service.BookService;
import by.epam.task4.service.ServiceException;
import by.epam.task4.validation.BookValidator;
import by.epam.task4.validation.ValidationException;

public class BookServiceImpl implements BookService {
 
    private static final BookService instance = new BookServiceImpl();
    private BookDao bookDao = BookDaoImpl.getInstance();  // returns interface type reference;
    private BookValidator validator  = BookValidator.getInstance();
    
    private BookServiceImpl() {}

    public static BookService getInstance() {
        return instance;
    }

    @Override
    public void sortByTitle(List<Book> books) {
        Comparator<Book> titleComparator = Comparator.comparing(Book::getTitle);
        books.sort(titleComparator);
    }
    
    @Override
    public void sortByAuthorSurname(List<Book> books) {
        Comparator<Book> authorSurnameComparator = (b1, b2) -> 
                                    b1.getAuthor().getSurname().compareToIgnoreCase(b2.getAuthor().getSurname()); 
        books.sort(authorSurnameComparator);
    }
    
    @Override
    public void sortByYear(List<Book> books) {
        Comparator<Book> yearComaparator = Comparator.comparingInt(Book::getYear);
        books.sort(yearComaparator);
    }
    
    @Override
    public void sortByPublisherName(List<Book> books) {
        Comparator<Book> publisherNameComparator = (b1, b2) -> 
                        b1.getPublisher().getName().compareToIgnoreCase(b2.getPublisher().getName());
        books.sort(publisherNameComparator);
    }
    
    @Override
    public void sortByPrice(List<Book> books) {
        Comparator<Book> priceComparator = Comparator.comparing(Book::getPrice);
        books.sort(priceComparator);
    }

    @Override
    public void addBook(Book book) throws ServiceException {
        if (!validator.isBookDataPresent(book)) {
            throw new ServiceException("book data is not present");
        }
        try {
            bookDao.addBook(book);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }        
    }

    @Override
    public void removeBook(Book book) throws ServiceException {
        try {
            bookDao.removeBook(book);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeBookData(int bookId, Map<String, Object> newValues) throws ValidationException, ServiceException {
        try {
            bookDao.changeBookData(bookId, newValues);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } 
    }

    @Override
    public Optional<Book> findBook(Map<String, Object> criteria) throws ServiceException {
        Optional<Book> bookOptional = Optional.empty();
        try {
            bookOptional = bookDao.findBook(criteria);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return bookOptional;
    }  
}
