package com.yaryna.ch.tax;
import com.yaryna.ch.tax.Controllers.LogInController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.*;

import static com.yaryna.ch.tax.User.user;

public class DBUtils {
    public static void changeScene(ActionEvent event, String fxmlFile, String title,String name,String last_name){
        Parent root = null;
        if(name!=null&&last_name!=null){
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LogInController logInController = loader.getController();
                logInController.setUserInfo(name,last_name);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            try{
                root=FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Taxes");
        stage.setScene(new Scene(root, 956,573));
        stage.show();
    }
    public static void singUpUser(ActionEvent event,String name,String lastmame,String login, String password, String email){
        User p = new User();
        p.createUser(name,lastmame,login,password,email);
        if(user.getLogin()!=null){
            changeScene(event,"logIn.fxml","Welcome", name, lastmame);
        }
    }
    public  static void logInUser(ActionEvent event, String username,String password){
        Connection connection=null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            preparedStatement=connection.prepareStatement("SELECT * FROM users WHERE login=?");
            preparedStatement.setString(1,username);
            rs=preparedStatement.executeQuery();
            if(!rs.isBeforeFirst()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Неправильні дані");
                alert.show();
            }else{
                while (rs.next()){
                    String retrievedPass = rs.getString("password");
                    if(retrievedPass.equals(password)){
                        user.setLogin(rs.getString("login"));
                        user.setPassword(rs.getString("password"));
                        user.setEmail(rs.getString("email"));
                        user.setFirstName(rs.getString("first_name"));
                        user.setLastName(rs.getString("last_name"));
                        changeScene(event,"logIn.fxml","Welcome", user.getFirstName(), user.getLastName());
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Неправильні дані");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(rs!=null){
                try{rs.close();}
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement!=null){
                try{
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
