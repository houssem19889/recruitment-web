package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.book.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by houssem89 on 26/10/2019.
 */
public class MemberRepository {
    private Map<Member, List<Book>> borrowedBookMembers = new HashMap<>();

    /**
     * Find books by members.
     *
     * @param member the member
     * @return the list
     */
    public List<Book> findBooksByMembers(Member member) {
        List<Book> results = borrowedBookMembers.get(member);
        if (results == null) {
            results = new ArrayList<>();
            borrowedBookMembers.put(member, results);
        }
        return results;
    }

    /**
     * Save member borrow.
     *
     * @param book the book
     * @param member the member
     */
    public void saveMemberBorrow(Book book, Member member) {
        List<Book> results = this.findBooksByMembers(member);
        results.add(book);
        borrowedBookMembers.put(member, results);
    }
}
