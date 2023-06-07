package edu.kit.kastel;

public class BankAccount {
    private int accountNumber; // Kontonummer des Bankkontos
    private int bankCode; // Bankleitzahl des Bankkontos
    private int balance; // Kontostand des Bankkontos

    public BankAccount(int bankCode, int accountNumber) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
        this.balance = 0; // Kontostand beim Initialisieren ist 0
    }

    // Getter und Setter für die Attribute

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getBankCode() {
        return bankCode;
    }

    public void setBankCode(int bankCode) {
        this.bankCode = bankCode;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean withdraw(int amount) {
        // Ensure amount is greater than 0
        if (amount <= 0) {
            return false;
        }
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public void deposit(int amount) {
        // Ensure amount is greater than 0
        if (amount > 0) {
            balance += amount; // Kontostand um den Betrag erhöhen
        }
    }

    public boolean transfer(BankAccount account, int amount) {
        if (amount <= 0) {
            return false;
        }
        if (balance >= amount) {
            balance -= amount;
            account.deposit(amount);
            return true;
        }
        return false;
    }
}
