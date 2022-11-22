package com.yaryna.ch.tax;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.yaryna.ch.tax.User.user;

public class DBTable {
    Connection con = null;
    public static Connection ConnectDB(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova");
            return con;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static ObservableList<Person> getDataUsers(){
        ObservableList<Person> list = FXCollections.observableArrayList();
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM employees WHERE added_by=?");
            ps.setString(1, user.getLogin());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Person p = new Person();
                p.setId_emp(Integer.parseInt(rs.getString("id_emp")));
                p.setName(rs.getString("first_name"));
                p.setLast_name(rs.getString("last_name"));
                p.setKidsCount(Integer.parseInt(rs.getString("cnt_kids")));
                p.setKidsDisabCount(Integer.parseInt(rs.getString("cnt_dkids")));
                p.setIsSingle(rs.getBoolean("is_single"));
                list.add(p);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
