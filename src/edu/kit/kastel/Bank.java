package edu.kit.kastel;

public class Bank {
    private static final int INITIAL_SIZE = 8; // Anfangsgröße des Arrays
    private int bankCode; // Bankleitzahl der Bank
    private BankAccount[] accounts; // Array von Bankkonten
    private int lastAccountNumber; // Letzte vergebene Kontonummer

    public Bank(int bankCode) {
        this.bankCode = bankCode;
        this.accounts = new BankAccount[INITIAL_SIZE]; // Array mit Anfangsgröße initialisieren
        this.lastAccountNumber = -1;
    }

    // Getter und Setter für die Bankleitzahl

    public int getBankCode() {
        return bankCode;
    }

    public void setBankCode(int bankCode) {
        this.bankCode = bankCode;
    }

    // Methode zum Erstellen eines neuen Bankkontos
    public int createAccount() {
        // Kontonummer ist die Anzahl der bereits vorhandenen Konten
        int accountNumber = ++lastAccountNumber;

        if (accountNumber >= accounts.length) {
            expandArray(); // Array vergrößern, wenn alle Plätze belegt sind
        }
        accounts[accountNumber] = new BankAccount(bankCode, accountNumber); // Neues Konto erstellen
        return accountNumber; // Kontonummer des neuen Kontos zurückgeben
    }

    private void expandArray() {
        BankAccount[] newArray = new BankAccount[accounts.length * 2]; // Array verdoppeln
        System.arraycopy(accounts, 0, newArray, 0, accounts.length); // Konten ins neue Array kopieren
        accounts = newArray; // Neues Array als aktuelles Array setzen
    }

    // Methode zum Löschen eines Bankkontos
    public boolean removeAccount(int accountNumber) {
        BankAccount accountToRemove = getAccount(accountNumber);
        if (accountToRemove != null) {
            // Restlicher Code ...
            if (accountNumber >= 0 && accountNumber < size()) {
                if (size() < accounts.length / 4 && accounts.length > INITIAL_SIZE) {
                    shrinkArray(); // Array verkleinern, wenn weniger als 1/4 belegt sind
                }
                for (int i = accountNumber; i < size() - 1; i++) {
                    accounts[i] = accounts[i + 1]; // Nachfolgende Konten nach vorne verschieben
                    accounts[i].setAccountNumber(i); // Update the accountNumber attribute of the shifted accounts
                }
                accounts[size() - 1] = null; // Letztes Konto im Array löschen
                lastAccountNumber--; // Update the lastAccountNumber after removing an account
                return true; // Konto erfolgreich gelöscht
            }
        }
        return false; // Konto nicht gefunden
    }

    private void shrinkArray() {
        BankAccount[] newArray = new BankAccount[accounts.length / 2]; // Array halbieren
        System.arraycopy(accounts, 0, newArray, 0, size()); // Konten ins neue Array kopieren
        accounts = newArray; // Neues Array als aktuelles Array setzen
    }


    // Methode zum Überprüfen, ob ein Konto mit bestimmter Kontonummer existiert
    public boolean containsAccount(int accountNumber) {
        return getAccount(accountNumber) != null; // Konto über getAccount() suchen und prüfen, ob es null ist
    }

    // Methode zum Abrufen eines Bankkontos anhand der Kontonummer
    public BankAccount getAccount(int accountNumber) {
        for (BankAccount account : accounts) {
            if (account != null && account.getAccountNumber() == accountNumber) {
                return account; // Konto mit der gesuchten Kontonummer gefunden
            }
        }
        return null; // Konto nicht gefunden
    }

    // Methode zur Berechnung der aktuellen Länge des Arrays
    public int length() {
        return accounts.length;
    }

    // Methode zur Berechnung der Anzahl der besetzten Array-Felder (Konten)
    public int size() {
        int count = 0;
        for (BankAccount account : accounts) {
            if (account != null) {
                count++;
            }
        }
        return count;
    }

    public boolean internalTransfer(int fromAccountNumber, int toAccountNumber, int amount) {
        if (amount <= 0) {
            return false;
        }
        BankAccount fromAccount = getAccount(fromAccountNumber);
        BankAccount toAccount = getAccount(toAccountNumber);

        if (fromAccount != null && toAccount != null) {
            if (fromAccount.withdraw(amount)) {
                toAccount.deposit(amount);
                return true;
            }
        }

        return false;
    }
}
