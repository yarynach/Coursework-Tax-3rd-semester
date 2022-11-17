package com.yarynach.employees;


import com.yarynach.employees.Taxes.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import static com.yarynach.User.user;

public class Person {
    private int id;
    private String name;
    private String last_name;
    private int kidsCount;
    private boolean isSingle=false;
    private int kidsDisabCount=0;
    private Salary salary;
    private Salary addSalary;
    private AuthorAward authAw;
    private MoneyTransfers MonTr;
    private FinancialAid finaid;
    private RealEstate rest;
    private Vehicles veh;

    public int getId() {
        return id;
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

    public boolean isSingle() {
        return isSingle;
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

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", kidsCount=" + kidsCount +
                ", salary=" + salary +
                ", addSalary=" + addSalary +
                ", Авторські винагороди=" + authAw +
                '}';
    }
    private void addDataPerson(){
        try {
            Scanner scan = new Scanner(System.in);
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            Statement st = connection.createStatement();
            String sql = "INSERT INTO employees (id_emp, first_name, last_name, cnt_kids, cnt_dkids,is_single,added_by) " +
                    "VALUES (?, ?, ?, ?, ?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            System.out.println("Enter id");
            id = checkUnique();
            ps.setInt(1, id);
            System.out.println("Введіть ім'я:");
            name = scan.nextLine();
            ps.setString(2, name);
            System.out.println("Введіть прізвище:");
            last_name = scan.nextLine();
            ps.setString(3, last_name);
            System.out.println("Введіть кількість діткй:(без вад)");
            kidsCount = scan.nextInt();
            ps.setInt(4, kidsCount);
            if (kidsCount > 0) {
                System.out.println("Чи дана виховує дитину самотужки(1- так /2 - ні)");
                scan.nextLine();
                if (scan.nextLine().equals("1")) {
                    isSingle = true;
                } else {
                    isSingle = false;
                }
                ps.setBoolean(6, isSingle);
                System.out.println("Введіть кількість дітей з інвалідністю");
                kidsDisabCount = scan.nextInt();
                ps.setInt(5, kidsDisabCount);
                }else{
                ps.setBoolean(6, false);
                ps.setInt(5, 0);
                }
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
        salary = new Salary(id, scan.nextDouble(), kidsCount,kidsDisabCount,isSingle);
        System.out.println("Внесіть дані про зарплатню з додаткового місця роботи");
        addSalary = new Salary(id, scan.nextDouble(), 0,0,false);
        System.out.println("Внесіть виплати за авторські винагороди");
        authAw = new AuthorAward(id, addMore());
        System.out.println("Внесіть перекази отримані з-за кордону");
        MonTr= new MoneyTransfers(id,addMore());
        System.out.println("Внесіть суму нараховану як матеріальну допомогу");
        double aid=scan.nextDouble();
        System.out.println("Чи є дана допомога систематичною 1 - так/2 - ні");
        boolean isSystematic;
        if(scan.nextInt()==1){
            isSystematic=true;
        }else{
            isSystematic=false;
        }
        finaid = new FinancialAid(id,aid,isSystematic);
        rest = new RealEstate(id);
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
        veh = new Vehicles(id);
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
            id = scan.nextInt();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            PreparedStatement ps = connection.prepareStatement("select id_emp from employees where id_emp=?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println("There is employee with this id");
                    System.out.println("Enter id");
                    checkUnique();
                }
                    rs.close();
                    ps.close();
                    connection.close();
                    return id;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
}
