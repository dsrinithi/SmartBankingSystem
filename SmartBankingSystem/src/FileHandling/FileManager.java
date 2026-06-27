package FileHandling;

import java.io.*;
import java.util.*;

import model.Account;

public class FileManager {

    static String file = "data/users.txt";

    public static void saveAccount(Account a) {
        try {
            FileWriter fw = new FileWriter(file, true);
            fw.write(a.name + "," + a.username + "," + a.password + "," + a.mobile + "," + a.balance + "\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Account login(String user, String pass) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(user) && data[2].equals(pass)) {
                    return new Account(data[0], data[1], data[2], data[3], Double.parseDouble(data[4]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}