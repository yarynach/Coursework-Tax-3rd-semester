package com.yarynach.Menu.Commands;

import com.yarynach.Database;
import com.yarynach.Menu.Command;
import com.yarynach.User;

import java.sql.*;

public class ShowEmployees  implements Command {
    @Override
    public void execute() {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM employees " +
                    "WHERE added_by=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, User.user.getLogin());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                System.out.print("\nEmployee id:"+rs.getInt("id_emp"));
                System.out.print(" First name:"+rs.getString("first_name"));
                System.out.print(" Last name:"+rs.getString("last_name"));
                System.out.print(" Кількість дітей:"+rs.getString("cnt_kids"));
            }
            rs.close();
            ps.close();
            connection.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
