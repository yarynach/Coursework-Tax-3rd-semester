package com.yarynach.employees.Taxes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class MoneyTransfers extends Income{
    public MoneyTransfers(int emp_id, double income) {
        super(emp_id, income, "Міжнародні перекази", 0.18);
    }
    @Override
    public void addToDB() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            Statement st = connection.createStatement();
            String sql = "INSERT INTO moneytransfers (id_emp, income, tax) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,emp_id );
            ps.setDouble(2,income);
            ps.setDouble(3, taxedIncome);
            ps.executeUpdate();
            st.close();
            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
