package by.epam.task4.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.epam.task4.entity.Book;
import by.epam.task4.validation.ValidationException;

public interface BookService {
    
    void sortByTitle(List<Book> books);
    void sortByAuthorSurname(List<Book> books);
    void sortByYear(List<Book> books);
    void sortByPublisherName(List<Book> books);
    void sortByPrice(List<Book> books);
    void addBook(Book book) throws ServiceException;
    void removeBook(Book book) throws ServiceException;
    void changeBookData(int bookId, Map<String, Object> newValues) throws ValidationException, ServiceException;
    Optional<Book> findBook(Map<String, Object> criteria) throws ServiceException;

}
