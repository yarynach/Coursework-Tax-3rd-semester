package com.yarynach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;

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
    public void createUser(){
            Scanner scan = new Scanner(System.in);
            System.out.println("Введіть ваше ім'я");
            firstName = scan.nextLine();
            System.out.println("Введіть ваше прізвище");
            lastName = scan.nextLine();
            System.out.println("Введіть ваш логін");
            login = scan.nextLine();
            checkUnique();
            System.out.println("Введіть вашу електронну пошту");
            email = scan.nextLine();
            System.out.println("Введіть ваш пароль");
            password = scan.nextLine();

    }
    private void checkUnique(){
        try{
            Scanner scan = new Scanner(System.in);
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery("select * from users");
            while(res.next()){
                if (Objects.equals(res.getString("login"), login)){
                    System.out.println("Вже існує користувач з таким логіном! Оберіть інший");
                        System.out.println("Введіть логін:");
                        login=scan.nextLine();
                        checkUnique();
                }
                else{
                    return;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

