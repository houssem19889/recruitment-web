package fr.d2factory.libraryapp.member;

/**
 * Created by houssem89 on 26/10/2019.
 */
public class InsuffisantException extends RuntimeException {
    /**
     * Instantiates a new Insuffisant exception.
     *
     * @param message the message
     */
    public InsuffisantException(String message) {
        super(message);
    }
}
