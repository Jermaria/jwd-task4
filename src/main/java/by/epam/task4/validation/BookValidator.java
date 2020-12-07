package by.epam.task4.validation;

import java.util.Calendar;

import by.epam.task4.entity.Book;


public class BookValidator {
    
    private static final BookValidator INSTANCE = new BookValidator();
    
    private BookValidator() {}

    public static BookValidator getInstance() {
        return INSTANCE;
    }

    public boolean isBookDataPresent(Book book) {       
        boolean isPresent = (book.getTitle() != null && !book.getTitle().isBlank()
                             && book.getAuthor() != null
                             && book.getPublisher().getName() != null 
                             && book.getPublisher().getCountry() != null
                             && book.getYear() > 0
                             && book.getYear() <= Calendar.getInstance().get(Calendar.YEAR)
                             && book.getPagesNumber() > 0
                             && book.getPrice().signum() != -1);
        return isPresent;
    }

}
