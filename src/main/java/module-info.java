module com.example.cw1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.example.cw1;
    opens com.example.cw1.Controllers.UserController to javafx.fxml;
    opens com.example.cw1.Controllers.Admin to javafx.fxml;
    opens com.example.cw1.Controllers to javafx.fxml;
    exports com.example.cw1;
    exports com.example.cw1.Controllers;
    exports com.example.cw1.Controllers.Admin;
    exports com.example.cw1.Controllers.UserController;
    exports com.example.cw1.Models;
    exports com.example.cw1.Views;
}