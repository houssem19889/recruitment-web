package fr.d2factory.libraryapp.library;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.MemberRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * Created by houssem89 on 26/10/2019.
 */
public class LibraryImplement implements Library {
    private BookRepository bookRepository;
    private MemberRepository memberRepository;

    /**
     * Instantiates a new Library implement.
     *
     * @param bookRepository the book repository
     * @param memberRepository the member repository
     */
    public LibraryImplement(BookRepository bookRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException {
        List<Book> books = this.memberRepository.findBooksByMembers(member);
        books.forEach(item -> {
            LocalDate borroweDate = bookRepository.findBorrowedBookDate(item);
            if (borroweDate != null && ChronoUnit.DAYS.between(borroweDate, LocalDate.now()) > member.getMaxDaysToKeepBook()) {
                throw new HasLateBooksException("You can not borrow, you are late");
            }
        });
        Book book = this.bookRepository.findBook(isbnCode);
        Optional<LocalDate> isReadyBorrow = Optional.ofNullable(this.bookRepository.findBorrowedBookDate(book));
        if (isReadyBorrow.isPresent()) {
            throw new BookNotFoundException("book is not found");
        }
        this.bookRepository.saveBookBorrow(book, borrowedAt);
        this.memberRepository.saveMemberBorrow(book, member);
        return book;

    }

    @Override
    public void returnBook(Book book, Member member) {
        LocalDate localDate = this.bookRepository.findBorrowedBookDate(book);
        int numberOfDays = (int) ChronoUnit.DAYS.between(localDate, LocalDate.now());
        member.payBook(numberOfDays);
        this.bookRepository.returnBorrowedBook(book);
    }
}
