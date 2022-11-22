package com.yaryna.ch.tax;

import com.yaryna.ch.tax.Taxes.Income;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bookkeeping {
    List<Income> incomes;
    double income;
    double tax;

    public void setIncomes(Person p) {
        incomes = new ArrayList<>();
        incomes.add(p.getSalary());
        incomes.add(p.getAddSalary());
        incomes.add((p.getAuthAw()));
        incomes.add(p.getRest());
        incomes.add(p.getVeh());
        incomes.add(p.getMonTr());
        incomes.add(p.getFinaid());


    }
    public void getSum(Person p){
        for (Income i: incomes){
            income+=i.getIncome();
        }
        for (Income i:incomes){
            tax+=i.getTaxedIncome();
        }
    }
    private void addSummarize(Person p){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            Statement st = connection.createStatement();
            String sql = "INSERT INTO summarized (id_emp, income, tax,taxed_income) " +
                    "VALUES (?, ?, ?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, p.getId_emp() );
            ps.setDouble(2,income);
            ps.setDouble(3, tax);
            ps.setDouble(4, income-tax);
            ps.executeUpdate();
            st.close();
            connection.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    private void addIncomesTable(Person p){
        try {
            Scanner scan = new Scanner(System.in);
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            Statement st = connection.createStatement();
            String sql = "INSERT INTO incomes (id_emp, sallary, add_salary, author_awards, real_estate,vehicles, int_money, fin_aid) " +
                    "VALUES (?, ?, ?, ?, ?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,p.getId_emp());
            for(int j=2;j<incomes.size()+2;j++) {
                ps.setDouble(j,incomes.get(j-2).getIncome());
            }
            ps.executeUpdate();
            st.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void addTaxesTable(Person p){
        try {
            Scanner scan = new Scanner(System.in);
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            Statement st = connection.createStatement();
            String sql = "INSERT INTO taxpaid (id_emp, sallary, add_salary, author_awards, real_estate,vehicles, int_money, fin_aid) " +
                    "VALUES (?, ?, ?, ?, ?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,p.getId_emp());
            for(int j=2;j<incomes.size()+2;j++) {
                ps.setDouble(j,incomes.get(j-2).getTaxedIncome());
            }
            ps.executeUpdate();
            st.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void calc(Person p){
        setIncomes(p);
        incomes.forEach(Income::calc);
        getSum(p);
        addSummarize(p);
        addTaxesTable(p);
        addIncomesTable(p);
    }
}
