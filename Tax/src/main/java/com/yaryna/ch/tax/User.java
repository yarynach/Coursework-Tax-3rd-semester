package com.yaryna.ch.tax;

import javafx.scene.control.Alert;

import java.sql.*;

public final class User {
    public String firstName;
    public String lastName;
    private String login;
    private String email;
    private String password;
    public static User user = new User();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void createUser(String name,String lName,String lg, String pass, String mail){
            user.setFirstName(name);
            user.setLastName(lName);
            user.setEmail(mail);
            user.setLogin(lg);
            user.setPassword(pass);
            Connection connection = null;
        PreparedStatement insert = null;
        PreparedStatement check = null;
        ResultSet rs = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            check = connection.prepareStatement("SELECT * FROM users WHERE login=?");
            check.setString(1,login);
            rs=check.executeQuery();
            if(rs.isBeforeFirst()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Цей логін вже зайнятий. Оберіть інший");
                alert.show();
                login=null;
            }else{
                insert = connection.prepareStatement("INSERT INTO users (login, password, first_name, last_name, email) " +
                        "VALUES (?, ?, ?, ?, ?)");
                insert.setString(1,user.getLogin());
                insert.setString(2, user.getPassword());
                insert.setString(3, user.getFirstName());
                insert.setString(4, user.getLastName());
                insert.setString(5, user.getEmail());
                insert.executeUpdate();

            }

        }catch (Exception e){e.printStackTrace();}
        finally {
            if(rs!=null){
                try{
                    rs.close();
                }catch(Exception e){e.printStackTrace();}
            }
            if (check!=null){
                try{
                    check.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(insert!=null){
                try{
                insert.close();
            }catch(Exception e){
                e.printStackTrace();
            }}
                if(connection!=null){
                try{
                    connection.close();
                }catch (Exception e){
                    e.printStackTrace();
                }}
            }
        }
}

