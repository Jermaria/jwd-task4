package by.epam.task4.conversion;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.util.List;

import org.testng.annotations.Test;

import by.epam.task4.entity.Book;
import by.epam.task4.service.ServiceException;
import by.epam.task4.service.UnsupportedInputSourceServiceException;
import by.epam.task4.validation.ValidationException;

public class DataToBookConverterTest {
    private static final String FILE_PATH = "resources/data/books_db.txt";
    private static final DataToBookConverter converter = DataToBookConverter.getInstance();

    @Test
    public void readBookDataFromFileTest() {
        try {
            List<Book> booksList = converter.readBookDataFromFile(FILE_PATH).get();
            int actual = booksList.size();
            int expected = 10;
            assertEquals(actual, expected);
        } catch (UnsupportedInputSourceServiceException | ServiceException | ValidationException e) {
            fail();
        }
    }
}
