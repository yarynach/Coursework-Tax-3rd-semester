package com.yarynach.employees.Taxes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class FinancialAid extends Income{
    private boolean isSystematic;

    public FinancialAid(int emp_id, double income, boolean isSystematic) {
        super(emp_id, income, "Матеріальна допомога", 0.18);
        this.isSystematic = isSystematic;
    }

    @Override
    public void addToDB() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            Statement st = connection.createStatement();
            String sql = "INSERT INTO financialaid (id_emp, income, tax) " +
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

    @Override
    public void setTaxedIncome() {
        if(!isSystematic&&income<grd){
            taxedIncome=0;
        }
        else{
            taxedIncome=income*taxedPercentage;
        }
    }
}
