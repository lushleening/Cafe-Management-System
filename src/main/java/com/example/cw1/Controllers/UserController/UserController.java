package com.example.cw1.Controllers.UserController;


import com.example.cw1.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import com.example.cw1.Controllers.Admin.AdminController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Optional;

public class UserController {
    @FXML
    private AnchorPane MainForm;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane inventory;

    @FXML
    private Button inventory_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Label username_label;


    @FXML
    private TextField menu_amount;

    @FXML
    private Label menu_change;

    @FXML
    private TableColumn<productData, String> menu_col_price;

    @FXML
    private TableColumn<productData, String > menu_col_productName;

    @FXML
    private TableColumn<productData, String> menu_col_quantity;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private Button menu_orderButton;

    @FXML
    private Button menu_receiptBtn;

    @FXML
    private Button menu_removeBtn;

    @FXML
    private ScrollPane menu_scrollPane;

    @FXML
    private TableView<productData> menu_tableView;

    @FXML
    private Label menu_total;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Button menu_btn;

    private ObservableList<productData> cardListData = FXCollections.observableArrayList();
    private ObservableList<productData> inventoryListData;
    private int getid;
    private double totalP;
    private double amount;
    private double change;
    private int cID;
    private ObservableList<userData> userListData;

    private Alert alert;

    public static UserController instance;


    public void logout() {

        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK) {

                logout_btn.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("/Fxml/Login.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Cafe Management System");

                stage.setScene(scene);
                stage.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ObservableList<productData> menuGetData() {
        String sql = "SELECT * FROM product";
        ObservableList<productData> listData = FXCollections.observableArrayList();
        Connection connectDb = DatabaseConnection.getConnection();

        try {
            PreparedStatement preparedStatement = connectDb.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                productData prod = new productData(resultSet.getInt("ID"),
                resultSet.getString("productID"),
                resultSet.getString("productname"),
                resultSet.getString("type"),
                resultSet.getInt("stock"),
                resultSet.getDouble("price"),
                resultSet.getString("image"),
                resultSet.getDate("date"));
                listData.add(prod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    public void menuGetTotal() {
        customerID();
        String total = "SELECT SUM(price) FROM purchase WHERE customer_id = " + cID;

        Connection connectDb = DatabaseConnection.getConnection();

        try {
            PreparedStatement preparedStatement = connectDb.prepareStatement(total);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                totalP = resultSet.getDouble("SUM(price)");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuDisplayTotal(){
        menuGetTotal();
        menu_total.setText("$" + totalP);
    }

    public void menuAmount() {
        menuGetTotal();
        if(menu_amount.getText().isEmpty() || totalP == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid amount");
            alert.showAndWait();
        } else{
            amount = Double.parseDouble(menu_amount.getText());
            if(amount < totalP) {
                menu_amount.setText("");
            }else{
                change = (amount - totalP);
                menu_change.setText("$" + change);
            }

        }
    }

    public void showReceipt() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Receipt.fxml"));
            Parent root = loader.load();


            ReceiptController receiptController = loader.getController();
            receiptController.setData(cID, amount, change);


            Stage receiptStage = new Stage();
            receiptStage.setTitle("Purchase Receipt");
            receiptStage.setScene(new Scene(root));
            receiptStage.setResizable(false);
            receiptStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuPayBtn() {
        if(totalP == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please choose your order first!");
            alert.showAndWait();
        } else {
            menuGetTotal();
            String insertPay = "INSERT INTO receipt (customer_id, total, date, em_username)" +
                    " VALUES (?,?,?,?)";

            Connection connectDb = DatabaseConnection.getConnection();

            try {
                if(amount == 0) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid");
                    alert.showAndWait();
                } else {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to pay this order?");
                    Optional<ButtonType> option = alert.showAndWait();

                    if(option.get() == ButtonType.OK) {
                        customerID();
                        menuGetTotal();

                        PreparedStatement preparedStatement = connectDb.prepareStatement(insertPay,
                                PreparedStatement.RETURN_GENERATED_KEYS);
                        preparedStatement.setString(1, String.valueOf(cID));
                        preparedStatement.setString(2, String.valueOf(totalP));

                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                        preparedStatement.setString(3, String.valueOf(sqlDate));
                        preparedStatement.setString(4, String.valueOf(data.username));
                        preparedStatement.executeUpdate();

                        // Get receipt ID
                        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                        int receiptId = 0;
                        if (generatedKeys.next()) {
                            receiptId = generatedKeys.getInt(1);
                        }


                        if (receiptId > 0) {
                            ObservableList<productData> items = menuGetOrder();
                            String insertItem = "INSERT INTO receipt_items (receipt_id, prod_id, prod_name, type, " +
                                    "quantity, price, date) VALUES (?,?,?,?,?,?,?)";

                            PreparedStatement itemStatement = connectDb.prepareStatement(insertItem);

                            for (productData item : items) {
                                itemStatement.setInt(1, receiptId);
                                itemStatement.setString(2, item.getProductID());
                                itemStatement.setString(3, item.getProductname());
                                itemStatement.setString(4, item.getType());
                                itemStatement.setInt(5, item.getQuantity() != null ? item.getQuantity() : item.getStock());
                                itemStatement.setDouble(6, item.getPrice());
                                itemStatement.setDate(7, new java.sql.Date(sqlDate.getTime()));
                                itemStatement.executeUpdate();
                            }
                        }

                        String clearPurchases = "DELETE FROM purchase WHERE customer_id = ?";
                        PreparedStatement clearStatement = connectDb.prepareStatement(clearPurchases);
                        clearStatement.setInt(1, cID);
                        clearStatement.executeUpdate();

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successful!");
                        alert.showAndWait();


                        showReceipt();

                        menuShowOrderData();
                        menuRestart();
                    } else {
                        alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Cancelled");
                        alert.showAndWait();
                    }
                }

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleReceiptButton() {

        Connection connectDb = DatabaseConnection.getConnection();
        try {
            String query = "SELECT id FROM receipt WHERE customer_id = ? ORDER BY id DESC LIMIT 1";
            PreparedStatement statement = connectDb.prepareStatement(query);
            statement.setInt(1, cID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // receipt most recent purchase only
                showReceipt();
            } else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("No recent receipt found.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void menuDisplayCard() {
        this.cardListData.clear();
        this.cardListData.addAll(this.menuGetData());
        int row = 0;
        int column = 0;
        this.menu_gridPane.getChildren().clear();
        this.menu_gridPane.getRowConstraints().clear();
        this.menu_gridPane.getColumnConstraints().clear();

        for (int q = 0; q < this.cardListData.size(); ++q) {
            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(this.getClass().getResource("/Fxml/menu.fxml"));
                VBox vbox = (VBox) load.load();
                menuController cardC = (menuController) load.getController();
                cardC.setData((productData) this.cardListData.get(q));
                vbox.setSpacing(15);
                vbox.setPadding(new Insets(15));

                if (column == 3) {
                    column = 0;
                    ++row;
                }

                this.menu_gridPane.add(vbox, column++, row);
                GridPane.setMargin(vbox, new Insets(10,60,10,60));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ObservableList<productData> menuGetOrder() {
        customerID();
        ObservableList<productData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM purchase WHERE customer_id = " + cID;

        Connection connectDb = DatabaseConnection.getConnection();
        try{
            PreparedStatement preparedStatement = connectDb.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            productData prod;

            while(resultSet.next()) {
                prod = new productData(resultSet.getInt("ID"),
                        resultSet.getString("prod_id"),
                        resultSet.getString("prod_name"),
                        resultSet.getString("type"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image"),
                        resultSet.getDate("date"));
                listData.add(prod);
            }

        } catch(Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<productData> menuOrderListData;
    public void menuShowOrderData(){
        menuOrderListData = menuGetOrder();

        menu_col_productName.setCellValueFactory(new PropertyValueFactory<>("productname"));
        menu_col_quantity.setCellValueFactory(new PropertyValueFactory<>("stock"));
        menu_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        menu_tableView.setItems(menuOrderListData);
    }


    public void switchForm(ActionEvent event) {
        if(event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            menu_form.setVisible(false);
        } else if (event.getSource() == menu_btn) {
            dashboard_form.setVisible(false);
            menu_form.setVisible(true);
            menuDisplayCard();
            displayUsername();
            menuDisplayTotal();
            menuShowOrderData();
        }
    }

    public void displayUsername() {
        String user = data.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);

        username_label.setText(user);
    }

    public void menuRestart(){
        totalP = 0;
        change = 0;
        amount = 0;
        menu_total.setText("$0.00");
        menu_amount.setText("");
        menu_change.setText("$0.00");
    }

    public void menuSelectOrder(){
        productData prod = (productData) this.menu_tableView.getSelectionModel().getSelectedItem();
        int num = menu_tableView.getSelectionModel().getSelectedIndex();

        if(num < 0) return;

        if(prod != null){
            getid = prod.getID();
        }
    }

    public void menuRemoveBtn(){

        if(getid == 0){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product to remove");
            alert.showAndWait();
        }else{
            String deleteData = "DELETE FROM purchase WHERE id = " + getid;
            Connection connectDb = DatabaseConnection.getConnection();
            try{
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this order?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    PreparedStatement preparedStatement = connectDb.prepareStatement(deleteData);
                    preparedStatement.executeUpdate();
                }
                menuShowOrderData();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void customerID(){

        String sql = "SELECT MAX(customer_id) FROM purchase";
        Connection connectDb = DatabaseConnection.getConnection();

        try{
            PreparedStatement preparedStatement = connectDb.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                cID = resultSet.getInt("MAX(customer_id)");
            }

            String checkCID = "SELECT MAX(customer_id) FROM receipt";
            PreparedStatement preparedStatement1 = connectDb.prepareStatement(checkCID);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            int checkID = 0;
            if(resultSet1.next()){
                checkID = resultSet1.getInt("MAX(customer_id)");
            }

            if(cID == 0){
                cID += 1;
            }else if(cID == checkID) {
                cID += 1;
            }

            data.cID = cID;


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void refreshTableView() {
        if (instance != null) {
            instance.menuShowOrderData();
            instance.menuDisplayTotal();
        }
    }


    @FXML
    public void initialize(){
        instance = this;
        menuDisplayCard();
        menuDisplayCard();
        displayUsername();
        menuGetOrder();
        menuDisplayTotal();
        menuShowOrderData();
        menuSelectOrder();
        menuRestart();

    }

}
