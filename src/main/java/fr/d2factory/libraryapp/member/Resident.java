package fr.d2factory.libraryapp.member;

/**
 * Created by houssem89 on 26/10/2019.
 */
public class Resident extends Member {

    /**
     * Instantiates a new Resident.
     *
     * @param wallet the wallet
     */
    public Resident(int wallet) {
        super(wallet);
    }

    /**
     * Instantiates a new Resident.
     */
    public Resident() {

    }

    @Override
    public void payBook(int numberOfDays) {
        float money;
        if (hasLateBook(numberOfDays)) {
            money = (numberOfDays - Config.Max_Day_Before_Late_Resident)
                    * Config.Resident_Price_after_Late + Config.Max_Day_Before_Late_Resident * Config.Resident_Price_Before_Late;
        } else {
            money = numberOfDays * Config.Resident_Price_Before_Late;
        }
        if (money > this.getWallet()) {
            throw new InsuffisantException("Insufficient money to pay");
        } else {
            this.setWallet(this.getWallet() - money);
        }

    }

    @Override
    public boolean hasLateBook(int numberOfDays) {
        return numberOfDays > Config.Max_Day_Before_Late_Resident;
    }
}
