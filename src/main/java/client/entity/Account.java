package client.entity;


import lombok.Data;

import java.util.List;

@Data
public class Account {

    private int id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private int accountNumber;
    private int pinNumber;
    private float balance;
    private int loginAttempts;
    private boolean isActive;
    private List<Transaction> transactionList;

    public boolean getIsActive() {
        return isActive;
    }

    public Account(String firstName, String lastName, String password, int accountNumber, int pinNumber,String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.accountNumber = accountNumber;
        this.pinNumber = pinNumber;
        this.balance = 0;
        this.loginAttempts = 3;
        this.isActive = true;
        this.email=email;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

}
