package com.yarynach;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {
    public Database() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            Statement st = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
