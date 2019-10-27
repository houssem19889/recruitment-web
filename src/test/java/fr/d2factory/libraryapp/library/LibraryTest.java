package fr.d2factory.libraryapp.library;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.member.MemberRepository;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


public class LibraryTest {
    private Library library ;
    private BookRepository bookRepository;
    private MemberRepository memberRepository;

    @Before
    public void setup() throws IOException {
        //TODO instantiate the library and the repository

        //TODO add some test books (use BookRepository#addBooks)
        //TODO to help you a file called books.json is available in src/test/resources
        bookRepository = new BookRepository();
        memberRepository=new MemberRepository();
        library = new LibraryImplement(bookRepository,memberRepository);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File("src/test/resources/books.json"));
        List<Book> books = new ArrayList<>();
        for (JsonNode node : root) {
            String title = node.path("title").asText();
            String author = node.path("author").asText();
            Long isbnCode = node.path("isbn").path("isbnCode").asLong();
            Book book = new Book(title, author, new ISBN(isbnCode));
            books.add(book);
        }
        bookRepository.addBooks(books);

    }

    @Test
    public void member_can_borrow_a_book_if_book_is_available(){
       Student student = new Student();
        student.setWallet(100);
        student.setDateOfRegistration(LocalDate.of(2019, 3, 27));
        Book book = library.borrowBook(46578964513L, student, LocalDate.of(2019, 10, 28));

        Assert.assertEquals(book.getTitle(), "Harry Potter");

        Resident resident = new Resident();
        resident.setWallet(100);
        Book books = library.borrowBook(968787565445L, resident, LocalDate.now());
        Assert.assertEquals(books.getTitle(), "Catch 22");

        Assert.assertNotNull(books);

    }

    @Test(expected = BookNotFoundException.class)
    public void borrowed_book_is_no_longer_available(){
        Student student = new Student();
        student.setWallet(150);
        student.setDateOfRegistration(LocalDate.of(2019, 3, 1));
        Book book1= library.borrowBook(968787565445L, student, LocalDate.of(2019, 10, 28));
        Resident resident = new Resident();
        resident.setWallet(150);
        library.borrowBook(3326456467846L, resident, LocalDate.of(2019, 10, 28));


    }

    @Test
    public void residents_are_taxed_10cents_for_each_day_they_keep_a_book(){
        Resident resident = new Resident();
        resident.setWallet(150);
        LocalDate borrowedAt=LocalDate.of(2019, 10, 28);
        Book book = library.borrowBook(46578964513L, resident, borrowedAt);
        long days= ChronoUnit.DAYS.between(borrowedAt,LocalDate.now());
        library.returnBook(book,resident);
        Float result=150-days*0.10f;
        Assert.assertEquals(resident.getWallet(), result, 0);
    }

    @Test
    public void students_pay_10_cents_the_first_30days(){
        Student student = new Student();
        student.setWallet(120);
        student.setDateOfRegistration(LocalDate.of(2019, 3, 1));
        LocalDate borrowedAt=LocalDate.of(2019,10, 28);
        Book book = library.borrowBook(46578964513L, student, borrowedAt);
        int days= Period.between(borrowedAt, LocalDate.now()).getDays();
        library.returnBook(book,student);
        Float result=120-days*0.10f;
        Assert.assertEquals(student.getWallet(), result, 0);
    }

    @Test
    public void students_in_1st_year_are_not_taxed_for_the_first_15days(){
        Student student = new Student();
        student.setWallet(10);
        student.setDateOfRegistration(LocalDate.of(2019, 3, 1));
        LocalDate borrowedAt=LocalDate.of(2019,10,28);
        Book book = library.borrowBook(46578964513L, student, borrowedAt);
        int days= (int)ChronoUnit.DAYS.between(borrowedAt, LocalDate.now());
        library.returnBook(book,student);
        int realDays=days-15;
        Float result;
        if(realDays>0) {
            result = 10 -  realDays* 0.10f;
        }else{
            result = 10f;
        }
        Assert.assertEquals(student.getWallet(), result, 0);   }

    @Test
    public void students_pay_15cents_for_each_day_they_keep_a_book_after_the_initial_30days(){
        Student student = new Student();
        student.setWallet(100);;
        student.setDateOfRegistration(LocalDate.of(2019, 3, 1));
        LocalDate borrowedAt=LocalDate.of(2019, 10, 28);;
        Book book = library.borrowBook(46578964513L, student, borrowedAt);
        long days= ChronoUnit.DAYS.between(borrowedAt,LocalDate.now());
        library.returnBook(book,student);
        Float result = 100 -  ((30* 0.10f)+(days-30)*0.15f);
        Assert.assertEquals(student.getWallet(), result, 0);
    }

    @Test
    public void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days(){
        Resident resident = new Resident();
        resident.setWallet(106);
        LocalDate borrowedAt=LocalDate.of(2019, 10, 28);
        Book book = library.borrowBook(46578964513L, resident, borrowedAt);
        long days= ChronoUnit.DAYS.between(borrowedAt,LocalDate.now());
        library.returnBook(book,resident);
        Float result = 100 -  60* 0.10f-(days-60)*0.20f;
        Assert.assertEquals(resident.getWallet(), result, 0);
    }
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @Test(expected =HasLateBooksException.class )
    public void members_cannot_borrow_book_if_they_have_late_books(){

       Resident resident = new Resident();
        resident.setWallet(100);
        LocalDate borrowedAt=LocalDate.of(2019, 8, 28);
        Book book = library.borrowBook(46578964513L, resident, borrowedAt);
        library.borrowBook(3326456467846L, resident, LocalDate.of(2019, 10, 28));



    }
}
