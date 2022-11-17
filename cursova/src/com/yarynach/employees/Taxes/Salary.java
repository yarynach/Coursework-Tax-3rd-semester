package com.yarynach.employees.Taxes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Salary extends Income{
    private int kids;
    private int dkids;
    private boolean isSingle;

    public Salary(int emp_id, double income, int kids, int dkids,boolean isSingle) {
        super(emp_id,income,"Податок зарплатні",0.18);
        this.kids=kids;
        this.dkids=dkids;
        this.isSingle=isSingle;
    }


    @Override
    public void setTaxedIncome() {
        if(isSingle){
            taxedIncome=(income-pspd*kids)*taxedPercentage;
        }
        else if (income<pm*1.4*kids&&kids>1){
            if(dkids>0){
                taxedIncome=(income-psp*kids)*taxedPercentage+(income-pspd*dkids)*taxedPercentage;
            }
            else {
                taxedIncome = (income - psp * kids) * taxedPercentage;
            }
            if(taxedIncome<0){taxedIncome=0;}
        }
        else {
            taxedIncome = income * taxedPercentage;
        }
    }

    @Override
    public void addToDB() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            Statement st = connection.createStatement();
            String sql = "INSERT INTO salaries (id_emp, income, tax) " +
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
