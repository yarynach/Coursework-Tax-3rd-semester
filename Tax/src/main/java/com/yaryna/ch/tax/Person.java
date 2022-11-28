package com.yaryna.ch.tax;


import com.yaryna.ch.tax.Taxes.*;

import java.sql.*;
import java.util.Scanner;

import static com.yaryna.ch.tax.User.user;


public class Person {
    private int id_emp;
    private String name;
    private String last_name;
    private int kidsCount;
    public boolean isSingle;
    public int kidsDisabCount;
    public Salary salary;
    public Salary addSalary;
    public AuthorAward authAw;
    public MoneyTransfers MonTr;
    public FinancialAid finaid;
    public RealEstate rest;
    public Vehicles veh;
    public double taxedInc;
    public double income;

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getTaxedInc() {
        return taxedInc;
    }

    public void setTaxedInc(double taxedInc) {
        this.taxedInc = taxedInc;
    }

    public int getId_emp() {
        return id_emp;
    }

    public String getName() {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public int getKidsCount() {
        return kidsCount;
    }


    public int getKidsDisabCount() {
        return kidsDisabCount;
    }

    public Salary getSalary() {
        return salary;
    }

    public Salary getAddSalary() {
        return addSalary;
    }

    public AuthorAward getAuthAw() {
        return authAw;
    }

    public MoneyTransfers getMonTr() {
        return MonTr;
    }

    public FinancialAid getFinaid() {
        return finaid;
    }

    public RealEstate getRest() {
        return rest;
    }

    public Vehicles getVeh() {
        return veh;
    }

    public void setId_emp(int id_emp) {
        this.id_emp = id_emp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setKidsCount(int kidsCount) {
        this.kidsCount = kidsCount;
    }

    public boolean getIsSingle() {
        return isSingle;
    }

    public void setIsSingle(boolean single) {
        isSingle = single;
    }

    public void setKidsDisabCount(int kidsDisabCount) {
        this.kidsDisabCount = kidsDisabCount;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id_emp +
                ", name='" + name + '\'' +
                ", kidsCount=" + kidsCount +
                ", salary=" + salary +
                ", addSalary=" + addSalary +
                ", Авторські винагороди=" + authAw +
                '}';
    }
    public void addDataPerson(){
        try {
            Scanner scan = new Scanner(System.in);
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            Statement st = connection.createStatement();
            String sql = "INSERT INTO employees (id_emp, first_name, last_name, cnt_kids, cnt_dkids,is_single,added_by) " +
                    "VALUES (?, ?, ?, ?, ?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_emp);
            ps.setString(2, name);
            ps.setString(3, last_name);
            ps.setInt(4, kidsCount);
            ps.setInt(5, kidsDisabCount);
            ps.setBoolean(6, isSingle);
            ps.setInt(5, kidsDisabCount);
            ps.setString(7, user.getLogin());
            ps.executeUpdate();
            st.close();
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
