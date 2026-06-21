package com.example.cw1.Controllers.Admin;

import java.util.Date;
import com.example.cw1.DatabaseConnection;
import com.example.cw1.customersData;
import com.example.cw1.data;
import com.example.cw1.productData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminController  {
    @FXML
    private Button customers_btn;
    @FXML
    private TableColumn<?, ?> Date;

    @FXML
    private AnchorPane MainForm;

    @FXML
    private Label Price;

    @FXML
    private Label ProductID;

    @FXML
    private Label ProductName;

    @FXML
    private Label Stock;

    @FXML
    private Label Type;

    @FXML
    private Button add_btn;

    @FXML
    private Button clear_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button delete_btn;

    @FXML
    private Button import_btn;

    @FXML
    private AnchorPane inventory;

    @FXML
    private TextField inventory_Price;

    @FXML
    private TextField inventory_ProductID;

    @FXML
    private TextField inventory_ProductName;

    @FXML
    private TextField inventory_Stock;

    @FXML
    private ComboBox<String> inventory_Type;

    @FXML
    private Button inventory_btn;

    @FXML
    private ImageView inventory_imageview;

    @FXML
    private TableView<productData> inventory_tableview;

    @FXML
    private Button logout_btn;

    @FXML
    private TableColumn<productData, String> table_Date;

    @FXML
    private TableColumn<productData, String> table_Price;

    @FXML
    private TableColumn<productData, String> table_ProductID;

    @FXML
    private TableColumn<productData, String> table_ProductName;

    @FXML
    private TableColumn<productData, String> table_Stock;

    @FXML
    private TableColumn<productData, String> table_Type;


    @FXML
    private Button update_btn;

    @FXML
    private Label username_label;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private AnchorPane inventory_form;

    @FXML
    private AnchorPane inventory_tableForm;

    @FXML
    private AnchorPane inventory_update;

    @FXML
    private TableColumn<customersData, String> customers_col_customerID;

    @FXML
    private TableColumn<customersData, String> customers_col_date;

    @FXML
    private TableColumn<customersData, String> customers_col_total;

    @FXML
    private AnchorPane customers_form;

    @FXML
    private TableView<customersData> customers_tableView;

    @FXML
    private BarChart<?, ?> dashboard_CustomerChart;

    @FXML
    private Label dashboard_NC;

    @FXML
    private Label dashboard_NSP;

    @FXML
    private Label dashboard_Ti;

    @FXML
    private Label dashboard_Total;

    @FXML
    private AreaChart<?, ?> dashboard_incomeChart;


    private Alert alert;

    private Image image;

    private Connection connect;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;


    public void dashboardDisplayNC(){

        String sql = "SELECT COUNT(id) FROM receipt";
                Connection connection = DatabaseConnection.getConnection();

        try{
            int nc = 0;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                nc = resultSet.getInt("COUNT(id)");
            }
            dashboard_NC.setText(String.valueOf(nc));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void dashbardDisplayTi(){
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String sql = "SELECT SUM(total) FROM receipt WHERE date = '" + sqlDate + "'";

        Connection connection = DatabaseConnection.getConnection();

        try{
            double ti = 0;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                ti = resultSet.getDouble("SUM(total)");
            }

            dashboard_Ti.setText("$" + ti);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void dashboardTotalI(){
        String sql = "SELECT SUM(total) FROM receipt";
        Connection connection = DatabaseConnection.getConnection();

        try{
            float ti = 0;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                ti = resultSet.getFloat("SUM(total)");
            }
            dashboard_Total.setText("$" + ti);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void dashboardNSP() {
        String sql = "SELECT SUM(quantity) FROM receipt_items";
        Connection connection = DatabaseConnection.getConnection();

        try {
            int q = 0;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                q = resultSet.getInt("SUM(quantity)");
            }

            this.dashboard_NSP.setText(String.valueOf(q));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dashboardIncomeChart() {
        this.dashboard_incomeChart.getData().clear();
        String sql = "SELECT date, SUM(total) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
        Connection connection = DatabaseConnection.getConnection();
        XYChart.Series chart = new XYChart.Series();

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                chart.getData().add(new XYChart.Data(resultSet.getString(1), resultSet.getFloat(2)));
            }

            this.dashboard_incomeChart.getData().add(chart);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dashboardCustomerChart() {
        this.dashboard_CustomerChart.getData().clear();
        String sql = "SELECT date, COUNT(id) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
        Connection connection = DatabaseConnection.getConnection();
        XYChart.Series chart = new XYChart.Series();

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                chart.getData().add(new XYChart.Data(resultSet.getString(1), resultSet.getInt(2)));
            }

            this.dashboard_CustomerChart.getData().add(chart);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void inventoryAddButton() {
        if(inventory_ProductID.getText().isEmpty()
                || inventory_ProductName.getText().isEmpty()
                || inventory_Type.getSelectionModel().isEmpty()
                || inventory_Stock.getText().isEmpty()
                || inventory_Price.getText().isEmpty()
                || data.path == null) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();

        } else {
            String checkProdID = "SELECT productID FROM product WHERE productID = '" + inventory_ProductID.getText() + "';";

            Connection connectDb = DatabaseConnection.getConnection();

            try{
                statement = connectDb.createStatement();
                resultSet = statement.executeQuery(checkProdID);

                if(resultSet.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(inventory_ProductID.getText() + " already exists");
                    alert.showAndWait();
                } else{
                    String insertData = "INSERT INTO product " + "(productID, productname,type, stock, price, image, date) " + "VALUES(?,?,?,?,?,?,?)";

                    preparedStatement = connectDb.prepareStatement(insertData);
                    preparedStatement.setString(1, inventory_ProductID.getText());
                    preparedStatement.setString(2, inventory_ProductName.getText());
                    preparedStatement.setString(3, inventory_Type.getSelectionModel().getSelectedItem());
                    preparedStatement.setString(4, inventory_Stock.getText());
                    preparedStatement.setString(5, inventory_Price.getText());

                    String path = "";
                    if(data.path != null) {
                        path = data.path;
                        path = path.replace("\\", "\\\\");
                    }
                    preparedStatement.setString(6, path);

                    java.util.Date date = new java.util.Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    preparedStatement.setDate(7, sqlDate);

                    preparedStatement.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Product added successfully");
                    alert.showAndWait();

                    inventoryShowData();
                    inventoryClearButton();

                }


            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void inventoryUpdateButton() {
        if (!this.inventory_ProductID.getText().isEmpty()
                && !this.inventory_ProductName.getText().isEmpty()
                && this.inventory_Type.getSelectionModel().getSelectedItem() != null
                && !this.inventory_Stock.getText().isEmpty()
                && !this.inventory_Price.getText().isEmpty() && data.path != null
                && data.id != 0)
        {
            String path = data.path;
            path = path.replace("\\", "\\\\");
            String updateData = "UPDATE product SET productID = '" + this.inventory_ProductID.getText()
                    + "', productname = '" + this.inventory_ProductName.getText()
                    + "', type = '" + this.inventory_Type.getSelectionModel().getSelectedItem()
                    + "', stock = '" + this.inventory_Stock.getText()
                    + "', price = '" + this.inventory_Price.getText()
                    +  "', image = '" + path
                    + "', date = '" + data.date
                    + "' WHERE ID = " + data.id;

            Connection connectDb = DatabaseConnection.getConnection();

            try {
                this.alert = new Alert(Alert.AlertType.CONFIRMATION);
                this.alert.setTitle("Error Message");
                this.alert.setHeaderText((String)null);
                this.alert.setContentText("Are you sure you want to UPDATE Product ID: " + this.inventory_ProductID.getText() + "?");
                Optional<ButtonType> option = this.alert.showAndWait();
                if (((ButtonType)option.get()).equals(ButtonType.OK)) {
                    this.preparedStatement = connectDb.prepareStatement(updateData);
                    this.preparedStatement.executeUpdate();
                    this.alert = new Alert(Alert.AlertType.INFORMATION);
                    this.alert.setTitle("Error Message");
                    this.alert.setHeaderText((String)null);
                    this.alert.setContentText("Successfully Updated!");
                    this.alert.showAndWait();

                    this.inventoryShowData();
                    this.inventoryClearButton();

                } else {
                    this.alert = new Alert(Alert.AlertType.ERROR);
                    this.alert.setTitle("Error Message");
                    this.alert.setHeaderText((String)null);
                    this.alert.setContentText("Cancelled.");
                    this.alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.alert = new Alert(Alert.AlertType.ERROR);
            this.alert.setTitle("Error Message");
            this.alert.setHeaderText((String)null);
            this.alert.setContentText("Please fill all blank fields");
            this.alert.showAndWait();
        }

    }

    public void inventoryClearButton() {
        boolean checkEmptyFields =
                inventory_ProductID.getText().isEmpty()
                && inventory_ProductName.getText().isEmpty()
                && inventory_Type.getSelectionModel().getSelectedItem() == null
                && inventory_Stock.getText().isEmpty()
                && inventory_Price.getText().isEmpty()
                && (data.path == null || data.path.isEmpty())
                && inventory_imageview.getImage() == null;


        if (checkEmptyFields) {
            this.alert = new Alert(Alert.AlertType.ERROR);
            this.alert.setTitle("Error Message");
            this.alert.setHeaderText(null);
            this.alert.setContentText("Fields are already empty.");
            this.alert.showAndWait();
            return;
        }

        inventory_ProductID.setText("");
        inventory_ProductName.setText("");
        inventory_Type.getSelectionModel().clearSelection();
        inventory_Stock.setText("");
        inventory_Price.setText("");
        data.path = "";
        inventory_imageview.setImage(null);
    }

    public void inventoryDeleteButton() {
        if (data.id == null || inventory_ProductID.getText().isEmpty()) {
            this.alert = new Alert(Alert.AlertType.ERROR);
            this.alert.setTitle("Error Message");
            this.alert.setHeaderText((String)null);
            this.alert.setContentText("Please fill all blank fields");
            this.alert.showAndWait();
        } else {
            this.alert = new Alert(Alert.AlertType.CONFIRMATION);
            this.alert.setTitle("Error Message");
            this.alert.setHeaderText((String)null);
            this.alert.setContentText("Are you sure you want to DELETE Product ID: " + this.inventory_ProductID.getText() + "?");
            Optional<ButtonType> option = this.alert.showAndWait();
            if (((ButtonType)option.get()).equals(ButtonType.OK)) {
                String deleteData = "DELETE FROM product WHERE ID = " + data.id;

                Connection connectDb = DatabaseConnection.getConnection();

                try {
                    this.preparedStatement = connectDb.prepareStatement(deleteData);
                    this.preparedStatement.executeUpdate();
                    this.alert = new Alert(Alert.AlertType.ERROR);
                    this.alert.setTitle("Error Message");
                    this.alert.setHeaderText((String)null);
                    this.alert.setContentText("successfully Deleted!");
                    this.alert.showAndWait();
                    this.inventoryShowData();
                    this.inventoryClearButton();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                this.alert = new Alert(Alert.AlertType.ERROR);
                this.alert.setTitle("Error Message");
                this.alert.setHeaderText((String)null);
                this.alert.setContentText("Cancelled");
                this.alert.showAndWait();
            }
        }

    }

    public void inventoryImportButton() {

        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image", "*.jpg", "*.png"));

        File file = openFile.showOpenDialog(MainForm.getScene().getWindow());

        if(file != null) {
            data.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 122, 135, false, true);
        }

        inventory_imageview.setImage(image);
    }

    //Merge all data
    public ObservableList<productData> inventoryDataList() {

        ObservableList<productData> listData = FXCollections.observableArrayList();

        String sql = "select * from product";

        DatabaseConnection connect = new DatabaseConnection();
        Connection connectDb = connect.getConnection();

        try{
            preparedStatement = connectDb.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                productData productData = new productData(
                        resultSet.getInt("ID"),
                        resultSet.getString("productID"),
                        resultSet.getString("productname"),
                        resultSet.getString("type"),
                        resultSet.getInt("stock"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image"),
                        resultSet.getDate("date"));

                listData.add(productData);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    // To show data in table
    private ObservableList<productData> inventoryListData;
    public void inventoryShowData(){
        inventoryListData = inventoryDataList();

        table_ProductID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        table_ProductName.setCellValueFactory(new PropertyValueFactory<>("productname"));
        table_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        table_Stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        table_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        table_Date.setCellValueFactory(new PropertyValueFactory<>("date"));

        inventory_tableview.setItems(inventoryListData);

    }

    public ObservableList<customersData> customersDataList() {

        ObservableList<customersData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM receipt";
        DatabaseConnection connect = new DatabaseConnection();

        try{
            PreparedStatement preparedStatement = connect.getConnection().prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            customersData cData;

            while(resultSet.next()) {
                cData = new customersData(resultSet.getInt("id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getDouble("total"),
                        resultSet.getDate("date"),
                        resultSet.getString("em_username"));

                listData.add(cData);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<customersData> customersListData;

    public void customersShowData() {
        customersListData = customersDataList();

        customers_col_customerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customers_col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        customers_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        customers_tableView.setItems(customersListData);
    }

    public void inventorySelectData() {
        productData prodData = inventory_tableview.getSelectionModel().getSelectedItem();
        int num = inventory_tableview.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) return;

        this.inventory_ProductID.setText(prodData.getProductID());
        this.inventory_ProductName.setText(prodData.getProductname());
        this.inventory_Stock.setText(String.valueOf(prodData.getStock()));
        this.inventory_Price.setText(String.valueOf(prodData.getPrice()));
        this.inventory_Type.getSelectionModel().select(prodData.getType());

        data.id = prodData.getID();
        System.out.println("Set data.id to: " + data.id);

        if (prodData.getImage() != null && !prodData.getImage().isEmpty()) {
            data.path = prodData.getImage();
            try {
                File imageFile = new File(prodData.getImage());
                if (imageFile.exists()) {
                    this.image = new Image(imageFile.toURI().toString(), 122.0, 135.0, false, true);
                    this.inventory_imageview.setImage(this.image);
                } else {
                    System.out.println("Image file doesn't exist: " + prodData.getImage());
                    // Set a placeholder image or clear the image view
                    this.inventory_imageview.setImage(null);
                }
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage());
                this.inventory_imageview.setImage(null);
            }
        }

        data.date = String.valueOf(prodData.getDate());

    }

    private String[] typeList = {"Meals", "Drinks"};

    public void inventoryTypeList() {

        List<String> list = new ArrayList<>();

        for(String data : typeList){
            list.add(data);
        }

        ObservableList<String> ListData = FXCollections.observableArrayList(typeList);
        inventory_Type.setItems(ListData);
    }

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

    public void displayUsername() {
        String user = data.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);

        username_label.setText(user);
    }

    public void switchForm(ActionEvent event) {
        if(event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            inventory_form.setVisible(false);
            customers_form.setVisible(false);

            dashboardDisplayNC();
            dashbardDisplayTi();
            dashboardTotalI();
            dashboardNSP();
            dashboardIncomeChart();
            dashboardCustomerChart();

        } else if (event.getSource() == inventory_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(true);
            inventory_tableview.setVisible(true);
            inventory_update.setVisible(true);
            customers_form.setVisible(false);

            displayUsername();
            inventoryTypeList();
            inventoryShowData();
        } else if (event.getSource() == customers_btn) {
            customers_form.setVisible(true);
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            inventory_tableview.setVisible(false);
            inventory_update.setVisible(false);
            customersShowData();
        }
    }

    @FXML
    public void initialize() {

        dashboardDisplayNC();
        dashbardDisplayTi();
        dashboardTotalI();
        dashboardNSP();

        displayUsername();
        inventoryTypeList();
        inventoryShowData();
        customersShowData();
        dashboardIncomeChart();
        dashboardCustomerChart();

        inventory_tableview.setStyle("-fx-text-fill: black; -fx-font-weight: normal;");

        table_ProductID.setStyle("-fx-text-fill: black;");
        table_ProductName.setStyle("-fx-text-fill: black;");
        table_Type.setStyle("-fx-text-fill: black;");
        table_Stock.setStyle("-fx-text-fill: black;");
        table_Price.setStyle("-fx-text-fill: black;");
        table_Date.setStyle("-fx-text-fill: black;");

        customers_tableView.setStyle("-fx-text-fill: black; -fx-font-weight: normal;");
        customers_col_customerID.setStyle("-fx-text-fill: black;");
        customers_col_total.setStyle("-fx-text-fill: black;");
        customers_col_date.setStyle("-fx-text-fill: black;");
    }

}
