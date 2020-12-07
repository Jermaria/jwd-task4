package by.epam.task4.dao;
import java.util.Map;
import java.util.Optional;

import by.epam.task4.entity.Book;
import by.epam.task4.validation.ValidationException;

public interface BookDao {
    void addBook(Book book) throws DaoException;
    void removeBook(Book book) throws DaoException;
    void changeBookData(int bookId, Map<String, Object> newValues) throws ValidationException, DaoException;
    Optional<Book> findBook(Map<String, Object> criteria) throws DaoException;
}
