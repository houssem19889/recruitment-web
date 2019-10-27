package fr.d2factory.libraryapp.library;

/**
 * Created by houssem89 on 26/10/2019.
 */
public class BookNotFoundException extends RuntimeException {

    /**
     * Instantiates a new Book not found exception.
     *
     * @param message the message
     */
    public BookNotFoundException(String message) {
        super(message);
    }
}
