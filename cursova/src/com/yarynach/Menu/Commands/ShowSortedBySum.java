package com.yarynach.Menu.Commands;

import com.yarynach.Menu.Command;
import com.yarynach.User;

import java.sql.*;

public class ShowSortedBySum implements Command {
    @Override
    public void execute() {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            Statement st = connection.createStatement();
            String sql = "SELECT b.id_emp, b.first_name, b.last_name, a.income, a.tax, a.taxed_income FROM summarized AS a"+
                    " INNER JOIN employees as b"+
                    " ON a.id_emp=b.id_emp"+
                    " WHERE added_by=?"+
                    " ORDER BY tax DESC";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, User.user.getLogin());
            ResultSet rs = ps.executeQuery();
            System.out.println("--------------------------------");
            System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s | %-15s |\n", "Employee id", "First name", "Last name","Income","Tax","Income with tax");
            System.out.println("--------------------------------");
            while (rs.next()){

                System.out.printf("| %-15d | %-15s | %-15s | %-15.3f | %-15.2f | %-15.2f |\n",rs.getInt("id_emp"),rs.getString("first_name"),rs.getString("last_name"),
                        rs.getDouble("income"),rs.getDouble("tax"),rs.getDouble("taxed_income"));
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

