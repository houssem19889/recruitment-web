package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.library.Library;

/**
 * A member is a person who can borrow and return books to a {@link Library}
 * A member can be either a student or a resident
 */
public abstract class Member {
    /**
     * The Max days to keep book.
     */
    protected int MaxDaysToKeepBook;
    /**
     * An initial sum of money the member has
     */
    private float wallet;

    /**
     * Instantiates a new Member.
     *
     * @param wallet the wallet
     */
    public Member(int wallet) {
        this.wallet = wallet;
    }

    /**
     * Instantiates a new Member.
     */
    public Member() {

    }

    /**
     * Gets max days to keep book.
     *
     * @return the max days to keep book
     */
    public int getMaxDaysToKeepBook() {
        return MaxDaysToKeepBook;
    }

    /**
     * The member should pay their books when they are returned to the library
     *
     * @param numberOfDays the number of days they kept the book
     */
    public abstract void payBook(int numberOfDays);

    /**
     * Gets wallet.
     *
     * @return the wallet
     */
    public float getWallet() {
        return wallet;
    }

    /**
     * Sets wallet.
     *
     * @param wallet the wallet
     */
    public void setWallet(float wallet) {
        this.wallet = wallet;
    }

    /**
     * Has late book.
     *
     * @param numberOfDays the number of days
     * @return the boolean
     */
    public abstract boolean hasLateBook(int numberOfDays);
}
