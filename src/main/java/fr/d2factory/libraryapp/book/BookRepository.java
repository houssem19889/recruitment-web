package fr.d2factory.libraryapp.book;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BookRepository {
    private Map<ISBN, Book> availableBooks = new HashMap<>();
    private Map<Book, LocalDate> borrowedBooks = new HashMap<>();

    /**
     * Add books.
     *
     * @param books the books
     */
    public void addBooks(List<Book> books) {
        //TODO implement the missing feature
        availableBooks = books.stream()
                .collect(Collectors.toMap(Book::getIsbn, book -> book));
    }

    /**
     * Find book.
     *
     * @param isbnCode the isbn code
     * @return the book
     */
    public Book findBook(long isbnCode) {
        //TODO implement the missing feature

        return availableBooks.entrySet().stream()
                .filter(e -> e.getKey().getIsbnCode() == isbnCode)
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    /**
     * Save book borrow.
     *
     * @param book the book
     * @param borrowedAt the borrowed at
     */
    public void saveBookBorrow(Book book, LocalDate borrowedAt) {
        //TODO implement the missing feature
        borrowedBooks.put(book, borrowedAt);
        availableBooks.remove(book.getIsbn(), borrowedAt);
    }

    /**
     * Find borrowed book date.
     *
     * @param book the book
     * @return the local date
     */
    public LocalDate findBorrowedBookDate(Book book) {
        //TODO implement the missing feature
        return borrowedBooks.get(book);
    }

    /**
     * Return borrowed book.
     *
     * @param book the book
     */
    public void returnBorrowedBook(Book book) {
        borrowedBooks.remove(book);
    }
}
