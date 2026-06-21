package com.example.cw1;

import com.example.cw1.Controllers.UserController.UserController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;

public class menuController implements Initializable {
    public ImageView prod_Image;
    @FXML
    private ImageView prod_image;

    @FXML
    private Label foodName;

    @FXML
    private Label prod_price;

    @FXML
    private ComboBox<Integer> prod_dropdown;

    @FXML
    private Button addButton;

    private Image image;

    private String prodID;
    private String type;
    private String prod_date;
    private String product_image;

    private productData prodData;

    private Alert alert;

    public void setData(productData prodData) {
        this.prodData = prodData;

        product_image = prodData.getImage();
        prod_date = String.valueOf(prodData.getDate());
        type = prodData.getType();
        prodID = prodData.getProductID();
        image = new Image(prodData.getImage(), 190, 94, false, true);
        prod_Image.setImage(image);
        foodName.setText(prodData.getProductname());
        prod_price.setText("$" + String.valueOf(prodData.getPrice()));
        pr = prodData.getPrice();
    }

    private int qty;
    public void populateQuantityDropdown() {
        for (int i = 0; i <= 100; i++) {
            prod_dropdown.getItems().add(i);
        }
        prod_dropdown.setValue(0);
    }

    private double totalP;
    private double pr;

    public void addButton() {
        UserController userController = new UserController();
        userController.customerID();

        qty = prod_dropdown.getValue();

        if (qty == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid quantity");
            alert.showAndWait();
            return;
        }

        Connection connectDb = null;
        PreparedStatement checkStockStmt = null;
        ResultSet resultSet = null;

        try {
            connectDb = DatabaseConnection.getConnection();


            String checkStock = "SELECT stock FROM product WHERE productID = ?";
            checkStockStmt = connectDb.prepareStatement(checkStock);
            checkStockStmt.setString(1, prodID);
            resultSet = checkStockStmt.executeQuery();

            int currentStock = 0;
            if (resultSet.next()) {
                currentStock = resultSet.getInt("stock");
            }


            if (currentStock <= 0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("This product is out of stock");
                alert.showAndWait();
                return;
            }

            if (currentStock < qty) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Not enough stock available. Available: " + currentStock);
                alert.showAndWait();
                return;
            }


            int updatedStock = currentStock - qty;


            String insertData = "INSERT INTO purchase (customer_id, prod_id, prod_name, type, quantity, price, date, image, em_username) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = connectDb.prepareStatement(insertData);
            insertStmt.setString(1, String.valueOf(data.cID));
            insertStmt.setString(2, prodID);
            insertStmt.setString(3, foodName.getText());
            insertStmt.setString(4, type);
            insertStmt.setInt(5, qty);

            totalP = (qty * pr);
            insertStmt.setDouble(6, totalP);

            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            insertStmt.setDate(7, sqlDate);
            insertStmt.setString(8, product_image);
            insertStmt.setString(9, data.username);
            insertStmt.executeUpdate();


            String updateStock = "UPDATE product SET productname = ?, type = ?, stock = ?, price = ?, image = ?, date = ? WHERE productID = ?";
            PreparedStatement updateStmt = connectDb.prepareStatement(updateStock);
            updateStmt.setString(1, prodData.getProductname());
            updateStmt.setString(2, type);
            updateStmt.setInt(3, updatedStock);
            updateStmt.setDouble(4, pr);
            updateStmt.setString(5, product_image);
            updateStmt.setString(6, prod_date);
            updateStmt.setString(7, prodID);
            updateStmt.executeUpdate();

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully added to cart!");
            alert.showAndWait();

            userController.menuGetTotal();

            userController.refreshTableView();


            prod_dropdown.setValue(0);

        } catch (Exception e) {
            e.printStackTrace();
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (checkStockStmt != null) checkStockStmt.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateQuantityDropdown();
    }
}