module com.example.tax {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.yaryna.ch.tax to javafx.fxml;
    exports com.yaryna.ch.tax;
    opens com.yaryna.ch.tax.Taxes to javafx.base;
    exports com.yaryna.ch.tax.Controllers;
    opens com.yaryna.ch.tax.Controllers to javafx.fxml;

}
