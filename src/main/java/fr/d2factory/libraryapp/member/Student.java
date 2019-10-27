package fr.d2factory.libraryapp.member;

import java.time.LocalDate;

/**
 * Created by houssem89 on 26/10/2019.
 */
public class Student extends Member {
    private boolean firstYear;
    private LocalDate dateOfRegistration;
    /**
     * Instantiates a new Student.
     */
    public Student() {
        super();
    }

    /**
     * Instantiates a new Student.
     *
     * @param firstYear the first year
     * @param wallet the wallet
     */
    public Student(boolean firstYear, int wallet) {
        super(wallet);
        this.firstYear = firstYear;
        MaxDaysToKeepBook = Config.Max_days_Before_Late_Student;

    }

    /**
     * Is first year.
     *
     * @return the boolean
     */
    public boolean isFirstYear() {
        return firstYear;
    }

    @Override
    public void payBook(int numberOfDays) {
        float money;
        numberOfDays = isRealNumberOfDay(numberOfDays);
        if (hasLateBook(numberOfDays)) {
            money = (numberOfDays - Config.Max_days_Before_Late_Student) * Config.Student_Price_after_Late + Config.Max_days_Before_Late_Student * Config.Student_Price_before_Late;
        } else {
            money = numberOfDays * Config.Student_Price_before_Late;
        }

        if (this.getWallet() > money) {
            this.setWallet(this.getWallet() - money);
        } else {
            throw new InsuffisantException("Insuffisant money to pay");
        }

    }

    private int isRealNumberOfDay(int numberOfDay) {
        if (this.isFirstYear()) {
            if (numberOfDay <= Config.first_Year_Free) {
                return 0;
            } else {
                return numberOfDay - Config.first_Year_Free;
            }
        }
        return numberOfDay;
    }

    @Override
    public boolean hasLateBook(int numberOfDays) {
        return numberOfDays > Config.Max_days_Before_Late_Student;
    }

    public LocalDate getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(LocalDate dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }
}

