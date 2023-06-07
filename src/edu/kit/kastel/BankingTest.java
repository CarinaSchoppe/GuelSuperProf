package edu.kit.kastel;

public class BankingTest {
    public static void main(String[] args) {
        // Test der Klasse BankAccount
        BankAccount account1 = new BankAccount(123, 1);
        BankAccount account2 = new BankAccount(123, 2);

        // Einzahlung auf das Konto
        account1.deposit(100);
        System.out.println("Kontostand von Konto 1 nach Einzahlung: " + account1.getBalance());

        // Auszahlung vom Konto
        boolean success = account1.withdraw(50);
        System.out.println("Erfolgreiche Auszahlung von Konto 1: " + success);
        System.out.println("Kontostand von Konto 1 nach Auszahlung: " + account1.getBalance());

        // Überweisung zwischen Konten
        success = account1.transfer(account2, 30);
        System.out.println("Erfolgreiche Überweisung von Konto 1 auf Konto 2: " + success);
        System.out.println("Kontostand von Konto 1 nach Überweisung: " + account1.getBalance());
        System.out.println("Kontostand von Konto 2 nach Überweisung: " + account2.getBalance());

        // Test der Klasse Bank
        Bank bank = new Bank(123);

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

        // Interne Überweisung zwischen Konten der Bank
        success = bank.internalTransfer(accountNumber2, accountNumber1, 20);
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
