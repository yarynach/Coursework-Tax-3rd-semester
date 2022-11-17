package com.yarynach.Menu;

import com.yarynach.Menu.Commands.CommandMenu;
import com.yarynach.User;

import java.sql.*;
import java.util.Scanner;

import static com.yarynach.User.user;

public class LoginCommand implements Command{
    @Override
    public void execute() {
        login();
        if((user.getLogin()==null)){
            System.out.println("Авторизація невдала");
        }
    }
    private User login(){
        try {
            String log,pass;
            Scanner scan = new Scanner(System.in);
            System.out.println("Введіть ваш логін:");
            log = scan.nextLine();
            System.out.println("Введіть ваш пароль:");
            pass = scan.nextLine();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM users WHERE login=? AND password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, log);
            preparedStatement.setString(2, pass);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setFirstName(resultSet.getString("first_name"));
                user.setEmail(resultSet.getString("email"));
                user.setLogin(resultSet.getString("login"));
                user.setLastName( resultSet.getString("last_name"));
                user.setPassword(resultSet.getString("password"));
                System.out.println("Авторизація успішна. Вітаю, "+user.getFirstName()+" "+user.getLastName()+"!");
                CommandMenu menu = new CommandMenu();
                while(true){
                    System.out.println("""
                    \nAvailable commands:
                    1.Add employee
                    2.Show
                    3.Exit
                    4.Show sorted
                    """);
                    String command = scan.next();
                    menu.execute(command);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }

}
