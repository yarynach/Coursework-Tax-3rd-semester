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
    private void addDataIncome(){
        boolean answer = false;
        int i=0;
        Scanner scan = new Scanner(System.in);
        System.out.println("Внесіть дані про зарплатню");

        System.out.println("Внесіть дані про зарплатню з додаткового місця роботи");
        addSalary = new Salary(id_emp, scan.nextDouble(), 0,0,false);
        System.out.println("Внесіть виплати за авторські винагороди");
        authAw = new AuthorAward(id_emp, addMore());
        System.out.println("Внесіть перекази отримані з-за кордону");
        MonTr= new MoneyTransfers(id_emp,addMore());
        System.out.println("Внесіть суму нараховану як матеріальну допомогу");
        double aid=scan.nextDouble();
        System.out.println("Чи є дана допомога систематичною 1 - так/2 - ні");
        boolean isSystematic;
        if(scan.nextInt()==1){
            isSystematic=true;
        }else{
            isSystematic=false;
        }
        finaid = new FinancialAid(id_emp,aid,isSystematic);
        rest = new RealEstate(id_emp);
        while(true){
            i++;
            System.out.println("Ввевдіть суму нараховану з продажу нерухомості");
            rest.setTaxedIncome(scan.nextDouble(),i);
            System.out.println("Бажаєте продовжити 1 - так/2 - ні?");
            if(checkInput()==false){
                break;
            }
        }
        i=0;
        veh = new Vehicles(id_emp);
        while(true){
            i++;
            System.out.println("Введіть суму нараховану з продажу машин");
            veh.setTaxedIncome(scan.nextDouble(),i);
            System.out.println("Бажаєте продовжити 1 - так/2 - ні?");
            if(checkInput()==false){
                break;
            }
        }
    }

    public void createPerson() {
        addDataPerson();
        addDataIncome();
    }
    public double addMore() {
        double sum = 0;
        Scanner scan = new Scanner(System.in);
        boolean a = true;
        int answer;
        while (a) {
            sum += scan.nextDouble();
            System.out.println("Бажаєте продовжити введення нарахувань?\n 1 - так 2 - ні");
            answer = scan.nextInt();
            if (answer != 1 && answer != 2) {
                do {
                    System.out.println("Введено неправильне значення. Повторіть ще раз");
                    answer = scan.nextInt();
                } while (answer != 1 && answer != 2);
            }
            if (answer == 2) {
                a = false;
            }
        }
        return sum;
    }

    private boolean checkInput(){
        int answer;
        boolean a=true;
        Scanner scan = new Scanner(System.in);
        answer = scan.nextInt();
        if (answer != 1 && answer != 2) {
            do {
                System.out.println("Введено неправильне значення. Повторіть ще раз");
                answer = scan.nextInt();
            } while (answer != 1 && answer != 2);
        }
        if (answer == 2) {
            a = false;
        }
        return a;
    }
    private int checkUnique() {
        try {
            Scanner scan = new Scanner(System.in);
            id_emp = scan.nextInt();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            PreparedStatement ps = connection.prepareStatement("select id_emp from employees where id_emp=?");
            ps.setInt(1, id_emp);
            ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println("There is employee with this id");
                    System.out.println("Enter id");
                    checkUnique();
                }
                    rs.close();
                    ps.close();
                    connection.close();
                    return id_emp;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return id_emp;
    }
}
