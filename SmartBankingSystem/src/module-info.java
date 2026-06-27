module SmartBankingSystem {

    requires javafx.controls;
    requires javafx.fxml;

    exports applications;

    opens applications to javafx.fxml;
    opens LoginController to javafx.fxml;
    opens RegisterController to javafx.fxml;
    opens Controller to javafx.fxml;
}