package fr.d2factory.libraryapp.library;

/**
 * This exception is thrown when a member who owns late books tries to borrow another book
 */
public class HasLateBooksException extends RuntimeException {
    /**
     * Instantiates a new Has late books exception.
     *
     * @param message the message
     */
    public HasLateBooksException(String message) {
        super(message);
    }
}
