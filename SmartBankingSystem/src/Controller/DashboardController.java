package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.*;
import javafx.collections.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.chart.PieChart;

import model.Account;

import java.io.*;
import java.time.LocalDate;

public class DashboardController {
	

	    boolean darkMode = false;

    @FXML Label welcomeLabel;
    @FXML Label message;
    @FXML Label dateTimeLabel;

    @FXML TableView<String[]> tableView;
    @FXML TableColumn<String[], String> dateCol;
    @FXML TableColumn<String[], String> typeCol;
    @FXML TableColumn<String[], String> amountCol;

    static Account currentUser;

    boolean dark = true;
    
    private void showDateTime() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");

        dateTimeLabel.setText(
                LocalDateTime.now().format(formatter)
        );
    }
    
    @FXML
    public void toggleTheme() {

        if (!darkMode) {

            welcomeLabel.getScene().getRoot().setStyle(
                    "-fx-background-color:black;"
            );

            welcomeLabel.setStyle("-fx-text-fill:white;");
            message.setStyle("-fx-text-fill:white;");

        } else {

            welcomeLabel.getScene().getRoot().setStyle(
                    "-fx-background-color:white;"
            );

            welcomeLabel.setStyle("-fx-text-fill:black;");
            message.setStyle("-fx-text-fill:black;");
        }

        darkMode = !darkMode;
    }
    
    @FXML
    public void showPieChart() {

        ObservableList<PieChart.Data> pieData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Deposit", 4000),
                        new PieChart.Data("Withdraw", 2000)
                );

        PieChart chart = new PieChart(pieData);

        Stage stage = new Stage();

        stage.setScene(new Scene(chart, 400, 300));

        stage.setTitle("Analytics");

        stage.show();
    }
    

    // ================= INIT TABLE =================
    @FXML
    public void initialize() {

        dateCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue()[0]));

        typeCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue()[1]));

        amountCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue()[2]));
        
        showDateTime();
    }
    

    // ================= USER SET =================
    public void setUser(Account acc) {

        currentUser = acc;

        welcomeLabel.setText("Welcome, " + acc.name);

        message.setText("Balance : ₹" + acc.balance);
    }

    // ================= DEPOSIT =================
    public void openDeposit() {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Deposit Money");
        dialog.setHeaderText("Enter Amount");

        dialog.showAndWait().ifPresent(val -> {

            double amt = Double.parseDouble(val);
            currentUser.balance += amt;

            message.setText("✅ Deposited ₹" + amt);

            saveTransaction("Deposit", amt);
            updateUserFile();
        });
    }

    // ================= WITHDRAW =================
    public void openWithdraw() {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Withdraw Money");
        dialog.setHeaderText("Enter Amount");

        dialog.showAndWait().ifPresent(val -> {

            double amt = Double.parseDouble(val);

            if (amt > currentUser.balance) {
                message.setText("❌ Insufficient Balance");
                return;
            }

            currentUser.balance -= amt;

            message.setText("✅ Withdraw ₹" + amt);

            saveTransaction("Withdraw", amt);
            updateUserFile();
        });
    }

    // ================= BALANCE =================
    public void checkBalance() {
        message.setText("Balance: ₹" + currentUser.balance);
    }

    // ================= LOGOUT =================
    public void logout() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Logout");
        alert.setHeaderText("Logout Confirmation");
        alert.setContentText("Are you sure you want to logout?");

        if (alert.showAndWait().get() == ButtonType.OK) {

            try {

                FXMLLoader loader =
                        new FXMLLoader(getClass().getResource("/login.fxml"));

                Parent root = loader.load();

                Stage stage =
                        (Stage) welcomeLabel.getScene().getWindow();

                stage.setScene(new Scene(root));

                stage.setWidth(1000);
                stage.setHeight(700);

                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    public void showProfile() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Profile");

        alert.setHeaderText("User Profile");

        alert.setContentText(
                "Name : " + currentUser.name +
                "\nUsername : " + currentUser.username +
                "\nMobile : " + currentUser.mobile +
                "\nBalance : ₹" + currentUser.balance
        );

        alert.showAndWait();
    }
    // ================= LOAD HISTORY =================
    public void loadHistory() {

        ObservableList<String[]> list = FXCollections.observableArrayList();

        try {

            BufferedReader br =
                    new BufferedReader(
                            new FileReader("data/transactions.txt"));

            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                if (data.length < 4) {
                    continue;
                }

                if (data[3].equals(currentUser.username)) {

                    list.add(new String[]{
                            data[0],
                            data[1],
                            data[2]
                    });
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        tableView.setItems(list);
    }

    // ================= SAVE TRANSACTION =================
    private void saveTransaction(String type, double amt) {

        try {

            File folder = new File("data");
            if (!folder.exists()) {
                folder.mkdir();
            }

            FileWriter fw = new FileWriter("data/transactions.txt", true);

            String date = LocalDate.now().toString();

            fw.write(
                date + "," +
                type + "," +
                amt + "," +
                currentUser.username + "\n"
            );

            fw.close();

            System.out.println("Transaction Saved Successfully");

        } catch (Exception e) {

            System.out.println("Error Saving Transaction");
            e.printStackTrace();
        }
    }
    // ================= UPDATE USER FILE =================
    private void updateUserFile() {

        try {
            File file = new File("data/users.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                if (data[1].equals(currentUser.username)) {
                    data[4] = String.valueOf(currentUser.balance);
                }

                sb.append(String.join(",", data)).append("\n");
            }

            br.close();

            FileWriter fw = new FileWriter(file);
            fw.write(sb.toString());
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= PIE CHART =================
    public void showPieChart() {

        double deposit = 0;
        double withdraw = 0;

        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("data/transactions.txt"));

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                if (data[3].equals(currentUser.username)) {

                    if (data[1].equals("Deposit")) {
                        deposit += Double.parseDouble(data[2]);
                    } else {
                        withdraw += Double.parseDouble(data[2]);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        ObservableList<PieChart.Data> pieData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Deposit", deposit),
                        new PieChart.Data("Withdraw", withdraw)
                );

        PieChart chart = new PieChart(pieData);
        chart.setTitle("Banking Analytics");

        Stage stage = new Stage();
        stage.setScene(new Scene(chart, 400, 300));
        stage.show();
    }

    // ================= DARK MODE =================
    public void toggleTheme() {

        if (dark) {

            welcomeLabel.getScene().getRoot().setStyle(
                    "-fx-background-color: white;"
            );

            message.setStyle("-fx-text-fill: black;");
            welcomeLabel.setStyle("-fx-text-fill: black;");

            message.setText("🌞 Light Mode ON");

        } else {

            welcomeLabel.getScene().getRoot().setStyle(
                    "-fx-background-color: #1e1e1e;"
            );

            message.setStyle("-fx-text-fill: white;");
            welcomeLabel.setStyle("-fx-text-fill: white;");

            message.setText("🌙 Dark Mode ON");
        }

        dark = !dark;
    }

    // ================= 👤 PROFILE PAGE =================
    public void showProfile() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Profile");

        alert.setHeaderText("👤 " + currentUser.name);

        alert.setContentText(
                "Account: " + currentUser.username +
                "\nMobile: " + currentUser.mobile +
                "\nBalance: ₹" + currentUser.balance
        );

        alert.show();
    }
}