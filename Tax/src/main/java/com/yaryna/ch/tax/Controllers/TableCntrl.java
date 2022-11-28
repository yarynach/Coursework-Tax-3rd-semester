package com.yaryna.ch.tax.Controllers;

import com.yaryna.ch.tax.DBUtils;
import com.yaryna.ch.tax.Person;
import com.yaryna.ch.tax.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class TableCntrl implements Initializable {
    @FXML
    private Button bt_back;

    @FXML
    private TableColumn<Person, Integer> col_tbid;

    @FXML
    private TableColumn<Person, String> col_tbname;

    @FXML
    private TableColumn<Person, String> col_tbsurname;

    @FXML
    private TableColumn<Person, Double> col_tbtax;

    @FXML
    private TableView<Person> tb_tax;
    @FXML
    private TableColumn<Person, Double> col_tbinc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTable();
        bt_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"logIn.fxml","Log in", User.user.getFirstName(), User.user.getLastName());
            }
        });
    }
    @FXML
    private void showTable(){
            System.out.println("pick");
            ObservableList<Person> list = FXCollections.observableArrayList();
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursova", "root", "yarynach");
                Statement st = connection.createStatement();
                String sql= "SELECT b.id_emp, b.first_name, b.last_name, a.tax, a.income FROM summarized AS a"+
                        " INNER JOIN employees as b"+
                        " ON a.id_emp=b.id_emp"+
                        " WHERE added_by=?"+
                        " ORDER BY tax DESC";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1,User.user.getLogin());
                ResultSet rs = ps.executeQuery();
                col_tbid.setCellValueFactory(new PropertyValueFactory<Person,Integer>("id_emp"));
                col_tbname.setCellValueFactory(new PropertyValueFactory<Person,String>("name"));
                col_tbsurname.setCellValueFactory(new PropertyValueFactory<Person,String>("last_name"));
                col_tbtax.setCellValueFactory(new PropertyValueFactory<Person,Double>("taxedInc"));
                col_tbinc.setCellValueFactory(new PropertyValueFactory<Person,Double>("income"));
                while (rs.next()){
                    Person i = new Person();
                    i.setId_emp(Integer.parseInt(rs.getString("id_emp")));
                    i.setName(rs.getString("first_name"));
                    i.setLast_name(rs.getString("last_name"));
                    i.setTaxedInc(Double.parseDouble(rs.getString("tax")));
                    i.setIncome(Double.parseDouble(rs.getString("income")));
                    list.add(i);
                }
                tb_tax.setItems(list);

            }catch(Exception e){
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error");
                alert.show();
            }
        }
    }
