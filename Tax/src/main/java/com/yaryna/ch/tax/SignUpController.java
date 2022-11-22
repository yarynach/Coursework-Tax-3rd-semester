package com.yaryna.ch.tax;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private Button bt_register;
    @FXML
    private Button bt_signin;
    @FXML
    private TextField first_name;
    @FXML
    private TextField last_name;
    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private TextField email;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bt_register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!login.getText().trim().isEmpty()&&!password.getText().trim().isEmpty()){
                    DBUtils.singUpUser(event,first_name.getText(),last_name.getText(),login.getText(),password.getText(),email.getText());
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Заповність потрібні поля");
                    alert.show();
                }
            }
        });
        bt_signin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"startMenu.fxml","Log in",null,null);
            }
        });
    }
}
