package fr.d2factory.libraryapp.book;


/**
 * A simple representation of a book
 */

public class Book {
    /**
     * The Title.
     */
    String title;
    /**
     * The Author.
     */
    String author;
    /**
     * The Isbn.
     */
    ISBN isbn;

    /**
     * Instantiates a new Book.
     *
     * @param title the title
     * @param author the author
     * @param isbn the isbn
     */
    public Book(String title, String author, ISBN isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        if (isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) return false;
        if (title != null ? !title.equals(book.title) : book.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        return result;
    }

    /**
     * Gets isbn.
     *
     * @return the isbn
     */
    public ISBN getIsbn() {
        return isbn;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }
}
