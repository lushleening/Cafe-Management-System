package com.example.cw1;

import com.example.cw1.DatabaseConnection;
import com.example.cw1.productData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiptController {

    @FXML
    private Label receipt_dateTimeLabel;

    @FXML
    private Label receipt_customerIDLabel;

    @FXML
    private TableView<productData> receipt_tableView;

    @FXML
    private TableColumn<productData, String> receipt_col_productName;

    @FXML
    private TableColumn<productData, Integer> receipt_col_quantity;

    @FXML
    private TableColumn<productData, Double> receipt_col_price;

    @FXML
    private Label receipt_subtotalLabel;

    @FXML
    private Label receipt_amountPaidLabel;

    @FXML
    private Label receipt_changeLabel;

    @FXML
    private Button receipt_closeBtn;

    private int customerID;
    private double amountPaid;
    private double change;

    public void initialize() {
        // Initialize table columns
        receipt_col_productName.setCellValueFactory(new PropertyValueFactory<>("productname"));
        receipt_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        receipt_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void setData(int customerID, double amountPaid, double change) {
        this.customerID = customerID;
        this.amountPaid = amountPaid;
        this.change = change;


        receipt_customerIDLabel.setText("Customer ID: " + customerID);

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        receipt_dateTimeLabel.setText("Date: " + formatter.format(new Date()));

        receipt_amountPaidLabel.setText("$" + String.format("%.2f", amountPaid));
        receipt_changeLabel.setText("$" + String.format("%.2f", change));


        loadPurchaseData();
    }

    private void loadPurchaseData() {
        ObservableList<productData> purchaseList = FXCollections.observableArrayList();
        double total = 0;

        try {
            Connection connectDb = DatabaseConnection.getConnection();
            String query = "SELECT * FROM receipt_items WHERE receipt_id = " +
                    "(SELECT MAX(id) FROM receipt WHERE customer_id = " + customerID + ")";

            PreparedStatement statement = connectDb.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                productData item = new productData(
                        resultSet.getInt("id"),
                        resultSet.getString("prod_id"),
                        resultSet.getString("prod_name"),
                        resultSet.getString("type"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("price"),
                        "",
                        resultSet.getDate("date")
                );

                purchaseList.add(item);
                total += resultSet.getDouble("price");
            }

            receipt_tableView.setItems(purchaseList);
            receipt_subtotalLabel.setText("$" + String.format("%.2f", total));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeReceipt() {
        Stage stage = (Stage) receipt_closeBtn.getScene().getWindow();
        stage.close();
    }
}