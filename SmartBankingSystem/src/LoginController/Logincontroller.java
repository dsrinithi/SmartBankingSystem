package LoginController;

import Controller.DashboardController;
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

public class Logincontroller {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label message;

    @FXML
    public void login() {

        Account acc = FileManager.login(
                usernameField.getText(),
                passwordField.getText());

        if (acc != null) {

            try {

                FXMLLoader loader =
                        new FXMLLoader(getClass().getResource("/dashboard.fxml"));

                Parent root = loader.load();

                DashboardController controller =
                        loader.getController();

                controller.setUser(acc);

                Stage stage =
                        (Stage) usernameField.getScene().getWindow();

                Scene scene = new Scene(root, 1000, 700);

                stage.setScene(scene);
                stage.setResizable(true);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
                message.setText("Dashboard load failed");
            }

        } else {
            message.setText("Invalid Username or Password");
        }
    }

    @FXML
    public void openRegister(ActionEvent event) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/register.fxml"));

            Parent root = loader.load();

            Stage stage =
                    (Stage) ((Node) event.getSource())
                            .getScene()
                            .getWindow();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            message.setText("Register page not found");
        }
    }
}