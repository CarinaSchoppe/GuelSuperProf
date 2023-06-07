package edu.kit.kastel;

public class BankingTest {
    public static void main(String[] args) {
        // Test der Klasse BankAccount
        int oneTwoThree = 123;
        BankAccount account1 = new BankAccount(oneTwoThree, 1);
        BankAccount account2 = new BankAccount(oneTwoThree, 2);

        // Einzahlung auf das Konto
        int hundred = 100;
        account1.deposit(hundred);
        System.out.println("Kontostand von Konto 1 nach Einzahlung: " + account1.getBalance());

        // Auszahlung vom Konto
        int fity = 50;
        boolean success = account1.withdraw(fity);
        System.out.println("Erfolgreiche Auszahlung von Konto 1: " + success);
        System.out.println("Kontostand von Konto 1 nach Auszahlung: " + account1.getBalance());

        // Überweisung zwischen Konten
        int thirty = 30;
        success = account1.transfer(account2, thirty);
        System.out.println("Erfolgreiche Überweisung von Konto 1 auf Konto 2: " + success);
        System.out.println("Kontostand von Konto 1 nach Überweisung: " + account1.getBalance());
        System.out.println("Kontostand von Konto 2 nach Überweisung: " + account2.getBalance());

        // Test der Klasse Bank
        Bank bank = new Bank(oneTwoThree);

        // Konto erstellen
        int accountNumber1 = bank.createAccount();
        int accountNumber2 = bank.createAccount();
        System.out.println("Kontonummer von neuem Konto 1: " + accountNumber1);
        System.out.println("Kontonummer von neuem Konto 2: " + accountNumber2);

        // Konto löschen
        boolean removed = bank.removeAccount(accountNumber1);
        System.out.println("Konto 1 gelöscht: " + removed);

        // Prüfen, ob ein Konto existiert
        boolean containsAccount = bank.containsAccount(accountNumber1);
        System.out.println("Konto 1 existiert: " + containsAccount);


        int twenty = 20;
        // Interne Überweisung zwischen Konten der Bank
        success = bank.internalTransfer(accountNumber2, accountNumber1, twenty);
        System.out.println("Erfolgreiche interne Überweisung von Konto 2 auf Konto 1: " + success);

        // Länge des Konten-Arrays und Anzahl der besetzten Felder
        int length = bank.length();
        int size = bank.size();
        System.out.println("Länge des Konten-Arrays: " + length);
        System.out.println("Anzahl der besetzten Felder: " + size);

        // Abrufen eines Kontos anhand des Index
        BankAccount account = bank.getAccount(0);
        if (account != null) {
            System.out.println("Kontonummer des ersten Kontos: " + account.getAccountNumber());
        } else {
            System.out.println("Kein Konto an Index 0 gefunden");
        }
    }
}
