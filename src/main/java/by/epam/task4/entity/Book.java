package by.epam.task4.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import by.epam.task4.validation.ValidationException;

public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    private int bookId;
    private String title;
    private Author author;
    private Publisher publisher;
    private int year;
    private int pagesNumber;
    private BigDecimal price;
    private Cover cover;

    public Book() {}

    public Book(String title, Author author, Publisher publisher, int year, int pagesNumber, BigDecimal price,
            Cover cover) throws ValidationException {

        if ((title.isBlank() || author == null || publisher.getName() == null || publisher.getCountry() == null
                || year < 1 || year > Calendar.getInstance().get(Calendar.YEAR) || pagesNumber < 1)
                || price.signum() == -1) {
            throw new ValidationException("invalid title, author, publisher, year," + "pagesNumber or price");
        }
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.pagesNumber = pagesNumber;
        this.price = price;
        this.cover = cover;      
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws ValidationException {
        if (title.isBlank()) {
            throw new ValidationException("invalid title");
        }
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) throws ValidationException {
        if (author == null) {
            throw new ValidationException("no author provided");
        }
        this.author = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) throws ValidationException {
        if (publisher.getName() == null || publisher.getCountry() == null) {
            throw new ValidationException("lack in publisher data: name or country");
        }
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) throws ValidationException {
        if (year < 1 || year > Calendar.getInstance().get(Calendar.YEAR)) {
            throw new ValidationException("invalid year" + year);
        }
        this.year = year;
    }

    public int getPagesNumber() {
        return pagesNumber;
    }

    public void setPagesNumber(int pagesNum) throws ValidationException {
        if (pagesNum < 1) {
            throw new ValidationException("invalid number of pages" + pagesNum);
        }
        this.pagesNumber = pagesNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) throws ValidationException {
        if (price.signum() == -1) {
            throw new ValidationException("invalid price" + price);
        }
        this.price = price;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + ((cover == null) ? 0 : cover.hashCode());
        result = prime * result + bookId;
        result = prime * result + pagesNumber;
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + year;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book other = (Book) obj;
        if (author == null) {
            if (other.author != null)
                return false;
        } else if (!author.equals(other.author))
            return false;
        if (cover != other.cover)
            return false;
        if (bookId != other.bookId)
            return false;
        if (pagesNumber != other.pagesNumber)
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        } else if (!price.equals(other.price))
            return false;
        if (publisher == null) {
            if (other.publisher != null)
                return false;
        } else if (!publisher.equals(other.publisher))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (year != other.year)
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName());
        builder.append(" [bookId=");
        builder.append(bookId);
        builder.append(", title=");
        builder.append(title);
        builder.append(", authors=");
        builder.append(author);
        builder.append(", publisher=");
        builder.append(publisher);
        builder.append(", year=");
        builder.append(year);
        builder.append(", pagesNum=");
        builder.append(pagesNumber);
        builder.append(", price=");
        builder.append(price);
        builder.append(", cover=");
        builder.append(cover);
        builder.append("]");
        return builder.toString();
    } 
}