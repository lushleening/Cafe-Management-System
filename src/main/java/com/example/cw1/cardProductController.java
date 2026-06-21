/*package com.example.cw1;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class cardProductController {
    @FXML
    private AnchorPane card_form;
    @FXML
    private Label prod_name;
    @FXML
    private Label prod_price;
    @FXML
    private ImageView prod_imageView;
    @FXML
    private Spinner<Integer> prod_spinner;
    @FXML
    private Button prod_addBtn;
    private productData prodData;
    private Image image;
    private String prodID;
    private String type;
    private String prod_date;
    private String prod_image;
    private SpinnerValueFactory<Integer> spin;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;
    private int qty;
    private double totalP;
    private double pr;

    public cardProductController() {
    }

    public void setData(productData prodData) {
        this.prodData = prodData;
        this.prod_image = prodData.getImage();
        this.prod_date = String.valueOf(prodData.getDate());
        this.type = prodData.getType();
        this.prodID = prodData.getProductId();
        this.prod_name.setText(prodData.getProductName());
        this.prod_price.setText("$" + String.valueOf(prodData.getPrice()));
        String path = "File:" + prodData.getImage();
        this.image = new Image(path, (double)190.0F, (double)94.0F, false, true);
        this.prod_imageView.setImage(this.image);
        this.pr = prodData.getPrice();
    }

    public void setQuantity() {
        this.spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        this.prod_spinner.setValueFactory(this.spin);
    }

    public void addBtn() {
        mainFormController mForm = new mainFormController();
        mForm.customerID();
        this.qty = (Integer)this.prod_spinner.getValue();
        String check = "";
        String checkAvailable = "SELECT status FROM product WHERE prod_id = '" + this.prodID + "'";
        Connection connectDb = DatabaseConnection.getConnection();

        try {
            int checkStck = 0;
            String checkStock = "SELECT stock FROM product WHERE prod_id = '" + this.prodID + "'";
            this.prepare = this.connect.prepareStatement(checkStock);
            this.result = this.prepare.executeQuery();
            if (this.result.next()) {
                checkStck = this.result.getInt("stock");
            }

            if (checkStck == 0) {
                String updateStock = "UPDATE product SET prod_name = '" + this.prod_name.getText() + "', type = '" + this.type + "', stock = 0, price = " + this.pr + ", status = 'Unavailable', image = '" + this.prod_image + "', date = '" + this.prod_date + "' WHERE prod_id = '" + this.prodID + "'";
                this.prepare = this.connect.prepareStatement(updateStock);
                this.prepare.executeUpdate();
            }

            this.prepare = this.connect.prepareStatement(checkAvailable);
            this.result = this.prepare.executeQuery();
            if (this.result.next()) {
                check = this.result.getString("status");
            }

            if (check.equals("Available") && this.qty != 0) {
                if (checkStck < this.qty) {
                    this.alert = new Alert(Alert.AlertType.ERROR);
                    this.alert.setTitle("Error Message");
                    this.alert.setHeaderText((String)null);
                    this.alert.setContentText("Invalid. This product is Out of stock");
                    this.alert.showAndWait();
                } else {
                    this.prod_image = this.prod_image.replace("\\", "\\\\");
                    String insertData = "INSERT INTO customer (customer_id, prod_id, prod_name, type, quantity, price, date, image, em_username) VALUES(?,?,?,?,?,?,?,?,?)";
                    this.prepare = this.connect.prepareStatement(insertData);
                    this.prepare.setString(1, String.valueOf(data.cID));
                    this.prepare.setString(2, this.prodID);
                    this.prepare.setString(3, this.prod_name.getText());
                    this.prepare.setString(4, this.type);
                    this.prepare.setString(5, String.valueOf(this.qty));
                    this.totalP = (double)this.qty * this.pr;
                    this.prepare.setString(6, String.valueOf(this.totalP));
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    this.prepare.setString(7, String.valueOf(sqlDate));
                    this.prepare.setString(8, this.prod_image);
                    this.prepare.setString(9, data.username);
                    this.prepare.executeUpdate();
                    int upStock = checkStck - this.qty;
                    System.out.println("Date: " + this.prod_date);
                    System.out.println("Image: " + this.prod_image);
                    String updateStock = "UPDATE product SET prod_name = '" + this.prod_name.getText() + "', type = '" + this.type + "', stock = " + upStock + ", price = " + this.pr + ", status = '" + check + "', image = '" + this.prod_image + "', date = '" + this.prod_date + "' WHERE prod_id = '" + this.prodID + "'";
                    this.prepare = this.connect.prepareStatement(updateStock);
                    this.prepare.executeUpdate();
                    this.alert = new Alert(Alert.AlertType.INFORMATION);
                    this.alert.setTitle("Information Message");
                    this.alert.setHeaderText((String)null);
                    this.alert.setContentText("Successfully Added!");
                    this.alert.showAndWait();
                    mForm.menuGetTotal();
                }
            } else {
                this.alert = new Alert(Alert.AlertType.ERROR);
                this.alert.setTitle("Error Message");
                this.alert.setHeaderText((String)null);
                this.alert.setContentText("Something Wrong :3");
                this.alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    } */

