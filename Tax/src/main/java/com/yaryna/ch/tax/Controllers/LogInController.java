package com.yaryna.ch.tax.Controllers;

import com.yaryna.ch.tax.*;
import com.yaryna.ch.tax.Taxes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    @FXML
    private Button bt_logout;
    @FXML
    private Label lb_welcome;
    @FXML
    private Button bt_add;

    @FXML
    private Button bt_delete;

    @FXML
    private Button bt_upd;

    @FXML
    private
    TableColumn<Person, Integer> col_disab;

    @FXML
    private TableColumn<Person, Integer> col_id;

    @FXML
    private TableColumn<Person, Boolean> col_isSingle;

    @FXML
    private TableColumn<Person, Integer> col_kids;

    @FXML
    private TableColumn<Person, String> col_name;

    @FXML
    private TableColumn<Person, String> col_surname;
    @FXML
    private TableView<Person> table_users;
    @FXML
    private CheckBox isSingle;
    @FXML
    private TextField txt_dsb;

    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_kids;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_surname;

    @FXML
    private Button bt_cars;

    @FXML
    private Button bt_estate;
    @FXML
    private TextField txt_awards;
    @FXML
    private TextField txt_estate;
    @FXML
    private TextField txt_salary;

    @FXML
    private TextField txt_sumcars;

    @FXML
    private TextField txt_sumestate;
    @FXML
    private TextField txt_transfers;
    @FXML
    private TextField txt_addsalary;
    private int n=1;
    private int r=1;
    ObservableList<Person> listM;

    @FXML
    private CheckBox isSystematic;
    @FXML
    private TextField txt_cars;
    @FXML
    private ChoiceBox ch_tax;
    @FXML
    private TextField txt_id2;

    @FXML
    private TableColumn<Income, Integer> col_idtax;

    @FXML
    private TableColumn<Income, Double> col_inctax;
    @FXML
    private TableColumn<Income, Double> col_taxtax;
    @FXML
    private TableView<Income> table_taxes;
    @FXML
    private TextField txt_pick;
    @FXML
    private Button bt_sort;
    ObservableList<Object> listA;

    private String[] taxes = {"Зарплатні", "Авторські винагороди", "Перекази з-за кордону", "Матеріальна допомога", "Продаж нерухомості", "Продаж машин"};
    private Person p = new Person();
    int index = -1;
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    public void UpdateTable(){
        col_disab.setCellValueFactory(new PropertyValueFactory<Person,Integer>("kidsDisabCount"));
        col_id.setCellValueFactory(new PropertyValueFactory<Person,Integer>("id_emp"));
        col_name.setCellValueFactory(new PropertyValueFactory<Person,String>("name"));
        col_surname.setCellValueFactory(new PropertyValueFactory<Person,String>("last_name"));
        col_kids.setCellValueFactory(new PropertyValueFactory<Person,Integer>("kidsCount"));
        col_isSingle.setCellValueFactory(new PropertyValueFactory<Person,Boolean>("isSingle"));
        listM = DBTable.getDataUsers();
        table_users.setItems(listM);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txt_sumestate.setText(Double.toString(0));
        txt_sumcars.setText(Double.toString(0));
        UpdateTable();
        bt_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"startMenu.fxml","Log in!",null,null);
            }
        });
        bt_sort.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"table.fxml","Sorted",null,null);
            }
        });

    }
    public void setUserInfo(String name, String last_name){
        lb_welcome.setText("Вітаю,"+" "+name+" "+last_name+"!");
    }
    public void AddUser(){
        n=1;
        r=1;
        try {
           p.setId_emp(Integer.parseInt(txt_id.getText()));
           p.setName(txt_name.getText());
           p.setLast_name(txt_surname.getText());
           p.setKidsCount(Integer.parseInt(txt_kids.getText()));
           p.setKidsDisabCount(Integer.parseInt(txt_dsb.getText()));
           if(isSingle.isSelected()){
               p.setIsSingle(true);
           }else{
               p.setIsSingle(false);
           }

           p.addDataPerson();
           System.out.println("added");

        }catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Неправильні дані або користувач з таким айді вже існує");
            alert.show();
        }
        UpdateTable();
    }

    public void getSelected(javafx.scene.input.MouseEvent mouseEvent) {
        index = table_users.getSelectionModel().getSelectedIndex();
        if(index<=-1){
            return;
        }
        txt_id.setText(col_id.getCellData(index).toString());
        txt_name.setText(col_name.getCellData(index));
        txt_surname.setText(col_surname.getCellData(index));
        txt_kids.setText(col_kids.getCellData(index).toString());
        txt_dsb.setText(col_disab.getCellData(index).toString());
        isSingle.setSelected(col_isSingle.getCellData(index));
        txt_salary.setText("0");
        txt_addsalary.setText("0");
        txt_awards.setText("0");
        txt_transfers.setText("0");
        txt_sumcars.setText("0");
        txt_sumestate.setText("0");

        //txt_salary.setText();


    }
    public void addDataIncome(){
        try {
            p.salary = new Salary(p.getId_emp(), Double.parseDouble(txt_salary.getText()), p.getKidsCount(), p.kidsDisabCount, p.getIsSingle());
            p.addSalary = new Salary(p.getId_emp(), Double.parseDouble(txt_addsalary.getText()), 0, 0, false);
            p.authAw = new AuthorAward(p.getId_emp(), Double.parseDouble(txt_awards.getText()));
            p.MonTr = new MoneyTransfers(p.getId_emp(), Double.parseDouble(txt_transfers.getText()));
            if (isSystematic.isSelected()) {
                p.finaid = new FinancialAid(p.getId_emp(), Double.parseDouble(txt_transfers.getText()), true);
            } else {
                p.finaid = new FinancialAid(p.getId_emp(), Double.parseDouble(txt_transfers.getText()), false);
            }
            System.out.println("Add income");
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Введіть правильний тип даних або перевірте, чи доданий цей користувач");
            alert.show();
        }
    }
    public void addDataCars(){
        p.veh=new Vehicles(p.getId_emp());
        addCars();
        txt_sumcars.setText(Double.toString((Double.parseDouble(txt_sumcars.getText()))+Double.parseDouble(txt_cars.getText())));
    }
    public void startCalc(){
        Bookkeeping b = new Bookkeeping();
        b.calc(p);
    }
    public void addCars(){
        if(p!=null){
            p.veh.setTaxedIncome(Double.parseDouble(txt_cars.getText()),r++);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
        }
    }
    public void addDataEstate(){
        p.rest=new RealEstate(p.getId_emp());
        addEstateToPerson();
        txt_sumestate.setText(Double.toString((Double.parseDouble(txt_sumestate.getText()))+Double.parseDouble(txt_estate.getText())));
    }
    public void addEstateToPerson(){
        if(p!=null){
        p.rest.setTaxedIncome(Double.parseDouble(txt_estate.getText()),n++);}
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
        }
    }
    public void Edit(){
        try{
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            Integer v1 = Integer.parseInt(txt_id.getText());
            String  v2 = txt_name.getText();
            String  v3 = txt_surname.getText();
            Integer v4 = Integer.parseInt(txt_kids.getText());
            Integer v5 = Integer.parseInt(txt_dsb.getText());
            Boolean v6 = Boolean.parseBoolean(isSingle.getText());
            String sql = "UPDATE employees SET id_emp="+v1+
                    ",first_name= '"+v2+
                    "',last_name= '"+v3+
                    "' ,cnt_kids= "+v4+
                    " ,cnt_dkids= "+v5+
                    ",is_single="+v6+" where id_emp= "+v1;
            pst = con.prepareStatement(sql);
            pst.execute();
            UpdateTable();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Щось пішло не так");
            alert.show();
            e.printStackTrace();
        }
    }
    public void pickTax(){
        System.out.println("pick");
        ObservableList<Income> list = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
            Statement st = connection.createStatement();
            String sql=" SELECT  a.income,a.tax FROM summarized as a " +
                    " INNER JOIN employees as b"+
                    " ON a.id_emp=b.id_emp"+
                    " WHERE b.added_by=? AND a.id_emp=?";
            if (Objects.equals(txt_pick.getText(), "2")) {
                sql = " SELECT  a.id_emp, a.income,a.tax FROM salaries as a " +
                        " INNER JOIN employees as b"+
                        " ON a.id_emp=b.id_emp"+
                        " WHERE b.added_by=? AND a.id_emp=?";
                System.out.println("taxzzzz");
            }else if(Objects.equals(txt_pick.getText(), "1")){
                sql = " SELECT  a.id_emp, a.income,a.tax FROM authorawards as a " +
                        " INNER JOIN employees as b"+
                        " ON a.id_emp=b.id_emp"+
                        " WHERE b.added_by=? AND a.id_emp=?";

            }else if(Objects.equals(txt_pick.getText(), "3")){
                sql = " SELECT  a.id_emp,a.income,a.tax FROM moneytransfers as a " +
                        " INNER JOIN employees as b"+
                        " ON a.id_emp=b.id_emp"+
                        " WHERE b.added_by=? AND a.id_emp=?";

            }else if(Objects.equals(txt_pick.getText(), "4")){
                sql = " SELECT  a.id_emp,a.income,a.tax FROM financialaid as a " +
                        " INNER JOIN employees as b"+
                        " ON a.id_emp=b.id_emp"+
                        " WHERE b.added_by=? AND a.id_emp=?";
            } else if (Objects.equals(txt_pick.getText(), "5")) {
                sql = " SELECT  a.id_emp, a.income,a.tax FROM realestate as a " +
                        " INNER JOIN employees as b"+
                        " ON a.id_emp=b.id_emp"+
                        " WHERE b.added_by=? AND a.id_emp=?";

            } else if (Objects.equals(txt_pick.getText(), "6")) {
                sql = " SELECT a.id_emp, a.income,a.tax FROM vehicles as a " +
                        " INNER JOIN employees as b"+
                        " ON a.id_emp=b.id_emp"+
                        " WHERE b.added_by=? AND a.id_emp=?";

            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Неправильно введені дані");
            }

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, User.user.getLogin());
            ps.setInt(2,Integer.parseInt(txt_id2.getText()));
            ResultSet rs = ps.executeQuery();
            col_idtax.setCellValueFactory(new PropertyValueFactory<Income,Integer>("Emp_id"));
            col_inctax.setCellValueFactory(new PropertyValueFactory<Income,Double>("Income"));
            col_taxtax.setCellValueFactory(new PropertyValueFactory<Income,Double>("TaxedIncome"));
            while (rs.next()){
                Income i = new Income();
                i.setEmp_id(Integer.parseInt(rs.getString("id_emp")));
                i.setIncome(Double.parseDouble(rs.getString("income")));
                i.setTaxedIncome(Double.parseDouble(rs.getString("tax")));
                list.add(i);
            }
            table_taxes.setItems(list);
            System.out.println("oooo");
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error");
            alert.show();
        }
    }
}

