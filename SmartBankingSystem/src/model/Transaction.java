package model;

public class Transaction {
    public String date;
    public String type;
    public double amount;

    public Transaction(String date, String type, double amount) {
        this.date = date;
        this.type = type;
        this.amount = amount;
    }
}