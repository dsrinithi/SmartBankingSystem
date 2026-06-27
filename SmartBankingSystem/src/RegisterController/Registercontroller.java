package RegisterController;

import FileHandling.FileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Account;

public class Registercontroller {

    @FXML
    private TextField name;

    @FXML
    private TextField user;

    @FXML
    private TextField mobile;

    @FXML
    private TextField deposit;

    @FXML
    private PasswordField pass;

    @FXML
    private Label msg;

    @FXML
    public void register() {

        try {

            double balance = Double.parseDouble(deposit.getText());

            Account acc = new Account(
                    name.getText(),
                    user.getText(),
                    pass.getText(),
                    mobile.getText(),
                    balance
            );

            FileManager.saveAccount(acc);

            msg.setText("Account Created Successfully!");

        } catch (NumberFormatException e) {

            msg.setText("Enter valid deposit amount");

        } catch (Exception e) {

            msg.setText("Registration Failed");
            e.printStackTrace();
        }
    }

    @FXML
    public void backToLogin(ActionEvent event) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/login.fxml"));

            Parent root = loader.load();

            Stage stage =
                    (Stage) ((Node) event.getSource())
                            .getScene()
                            .getWindow();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}