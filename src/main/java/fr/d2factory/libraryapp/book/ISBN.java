package fr.d2factory.libraryapp.book;


/**
 * The type ISBN.
 */
public class ISBN {
    /**
     * The Isbn code.
     */
    long isbnCode;

    /**
     * Instantiates a new ISBN.
     *
     * @param isbnCode the isbn code
     */
    public ISBN(long isbnCode) {
        this.isbnCode = isbnCode;
    }

    /**
     * Gets isbn code.
     *
     * @return the isbn code
     */
    public long getIsbnCode() {
        return isbnCode;
    }
}
