package model;
import model.Account;
import FileHandling.FileManager;

public class Account {
    public String name;
    public String username;
    public String password;
    public String mobile;
    public double balance;

    public Account(String name, String username, String password, String mobile, double balance) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.mobile = mobile;
        this.balance = balance;
    }
}