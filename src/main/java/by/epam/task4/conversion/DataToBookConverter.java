package by.epam.task4.conversion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task4.entity.Author;
import by.epam.task4.entity.Book;
import by.epam.task4.entity.Cover;
import by.epam.task4.entity.Publisher;
import by.epam.task4.service.ServiceException;
import by.epam.task4.service.UnsupportedInputSourceServiceException;
import by.epam.task4.validation.ValidationException;

public class DataToBookConverter {
    
    private static final DataToBookConverter instance = new DataToBookConverter();
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
    
    private DataToBookConverter(){}

    public static DataToBookConverter getInstance() {
        return instance;
    }
    
    public Optional<List<Book>> readBookDataFromFile(String path) throws UnsupportedInputSourceServiceException, 
                                                                    ServiceException, ValidationException {
        List<Book> booksList = new ArrayList<Book>();
        List<String> booksData = readDataFromFile(path);
        int i = 0;
        while(i < booksData.size()) {
            Book book = convertDataToBook(booksData.get(i));
            booksList.add(book);
            i++;
        }
        Optional<List<Book>> booksListOptional = (booksList.size() > 0) ? Optional.of(booksList) : Optional.empty();
        return booksListOptional;
    }
    
    public List<String> readDataFromFile(String path) throws UnsupportedInputSourceServiceException, ServiceException {
        List<String> booksData = new ArrayList<String>();
        try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String bookData;
            while ((bookData = reader.readLine()) != null) {
                booksData.add(bookData);
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.ERROR, "no file found: ", e );
            throw new UnsupportedInputSourceServiceException("file not found ", e);
        } catch (IOException e) {
            logger.log(Level.ERROR, "buffered reader failed to read line or close ", e );
            throw new ServiceException("buffered reader failed to read line or close", e);
        }
        return booksData;        
    }
    
    public Book convertDataToBook(String bookData) throws ValidationException {
        String[] bookDataDetails = bookData.split(" ");
        Map<String, String> processedDetails= processBookDataDetails(bookDataDetails);
        Book book = createBookByData(processedDetails);
        return book;        
    }
    
    public Map<String, String> processBookDataDetails(String[] bookDataDetails) {
        Map<String, String> processedDetails = new HashMap<String, String>();
        for (int i = 2; i < bookDataDetails.length; i++ ) {
            
            String detailName = bookDataDetails[i].replaceAll("=.+", "");
            String detailValue = bookDataDetails[i].replaceAll("[A-Z]+[_]?[A-Z]+=", "").replaceAll("[,;]$", "")
                                                                                         .replaceAll("_", " ");
            processedDetails.put(detailName, detailValue);
        }
        return processedDetails;
    }
    
    public Book createBookByData(Map<String, String> processedDetails) throws ValidationException {
        String title = processedDetails.get(TITLE);
        Author author = new Author(processedDetails.get(AUTHOR_NAME), processedDetails.get(AUTHOR_SURNAME));
        Publisher publisher = new Publisher(processedDetails.get(PUBLISHER_NAME), processedDetails.get(PUBLISHER_COUNTRY));
        int year = Integer.parseInt(processedDetails.get(YEAR));
        int pagesNumber = Integer.parseInt(processedDetails.get(PAGES_NUMBER));
        BigDecimal price = new BigDecimal(processedDetails.get(PRICE));
        Cover cover = Cover.valueOf(processedDetails.get(COVER).toUpperCase());
        
        Book book = new Book (title, author, publisher, year, pagesNumber, price, cover);
        return book;
    }
}
