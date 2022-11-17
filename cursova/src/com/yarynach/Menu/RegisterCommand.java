package com.yarynach.Menu;

import java.sql.*;

import com.yarynach.User;

import static com.yarynach.User.user;

public class RegisterCommand implements Command{
    public void execute(){
        addData();
    }
    private void addData(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            Statement st = connection.createStatement();
            String sql = "INSERT INTO users (login, password, first_name, last_name, email) " +
                    "VALUES (?, ?, ?, ?, ?)";

            user.createUser();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.executeUpdate();
            st.close();
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
