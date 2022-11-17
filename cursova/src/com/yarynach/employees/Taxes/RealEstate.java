package com.yarynach.employees.Taxes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class RealEstate extends Income{

    public RealEstate(int emp_id) {
        super(emp_id, 0, "Продаж нерухомості", 0);
    }

    public void setTaxedIncome(double incomeR,int n) {
        double currentTax, percent;
        if(n==1){
            percent=0;
        }
        else if(n==2){
           percent=0.05+vz;
        }
        else{
           percent=0.18+vz;
        }
        currentTax= incomeR*percent;
        income+=incomeR;
        taxedIncome+=currentTax;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            Statement st = connection.createStatement();
            String sql = "INSERT INTO realestate (id_emp, income,num_sell, tax) " +
                    "VALUES (?, ?, ?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, emp_id);
            ps.setDouble(2,incomeR);
            ps.setInt(3,n+1);
            ps.setDouble(4,currentTax);
            ps.executeUpdate();
            st.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void calc() {
        setTaxedIncome();
    }
}
