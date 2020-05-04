package controllers;

import dao.BillDAO;
import dao.ClientDAO;
import dao.KilowattDAO;
import entity.Bill;
import entity.Client;
import entity.KilowattPrice;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BillsController {
    //-----------ClientTableView--------//
    @FXML
    private TableView<Client> clientListTableView;
    @FXML
    private TableColumn<Object, Object> clientListTableViewFirstName;
    @FXML
    private TableColumn<Object, Object> clientListTableViewLastName;
    @FXML
    private TableColumn<Object, Object> clientListTableViewEmail;
    @FXML
    private TableColumn<Object, Object> highestBillPaidTableView;
    @FXML
    private TableColumn<Object, Object> totalPaidTableView;
    //-----------BillTableView--------//
    @FXML
    private TableView<Bill> updateTableView;
    @FXML
    private TableColumn<Object, Object> updateBillTableViewDate;
    @FXML
    private TableColumn<Object, Object> updateBillTableViewConsumption;
    @FXML
    private TableColumn<Object, Object> updateBillTableViewKwPrice;
    @FXML
    private TableColumn<Object, Object> updateBillTableViewPrice;
    @FXML
    private TableColumn<Object, Object> updateBillTableViewStatus;
    //-----------KW_Price_Update--------//
    @FXML
    private Label legalEntityDate;
    @FXML
    private Label privateSubscriberDate;
    @FXML
    private TextField privateSubscriberPrice;
    @FXML
    private TextField legalEntityPrice;
    @FXML
    private AnchorPane updateKilowattView;
    //-----------Client_Bills--------//
    @FXML
    private Label client_id;
    @FXML
    private Label clientName;
    @FXML
    private Label clientAddress;
    @FXML
    private AnchorPane updateView;
    //-----------Client_Bills--------//
    @FXML
    private AnchorPane createBillView;
    @FXML
    private TextField createBillKwPrice;
    @FXML
    private TextField createBillElectricConsumption;
    @FXML
    private DatePicker createBillDate;
    //

    boolean flag;
    private ArrayList<KilowattPrice> kilowattPrices = null;

    @FXML
    private void initialize() {
        updateClientView();
    }

    public void doubleClickUpd(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            toggleUpdate();
        }
    }

    public void toggleUpdate() {
        Client client = clientListTableView.getSelectionModel().getSelectedItem();
        updateClientBills();

        flag = updateView.isVisible();
        updateView.setVisible(!flag);


        client_id.setText(String.valueOf(client.getId()));
        clientName.setText(client.getFirstName() + " " + client.getLastName());
        clientAddress.setText(client.getAddress());

        if (!updateView.isVisible()) {
            createBillView.setVisible(false);
        }
    }

    public void toggleCreateBill() {
        kilowattPrices = new ArrayList<>(KilowattDAO.getKilowattPrice());
        Client client = clientListTableView.getSelectionModel().getSelectedItem();
        flag = createBillView.isVisible();
        createBillView.setVisible(!flag);

        createBillDate.setValue(null);
        createBillElectricConsumption.setText(null);
        createBillKwPrice.setText(kilowattPrices.get(client.getType().ordinal()).getPrice() + " kw/h");
    }

    public void payBill() {
        Bill bill = updateTableView.getSelectionModel().getSelectedItem();
        flag = bill.isPaid();
        bill.setPaid(!flag);
        BillDAO.payBill(bill);
        updateClientBills();
    }

    public void createBill() {
        kilowattPrices = new ArrayList<>(KilowattDAO.getKilowattPrice());
        Client client = clientListTableView.getSelectionModel().getSelectedItem();

        double energyConsumption = Double.parseDouble(createBillElectricConsumption.getText());
        double kwPrice = kilowattPrices.get(client.getType().ordinal()).getPrice();

        double price = energyConsumption * kwPrice;

        Bill bill = new Bill(
                client,
                createBillDate.getValue(),
                kwPrice,
                energyConsumption,
                price,
                false
        );

        BillDAO.saveBill(bill);
        createBillView.setVisible(false);
    }

    public void deleteBill() {
        Bill bill = updateTableView.getSelectionModel().getSelectedItem();
        BillDAO.deleteBill(bill);
        updateClientBills();
    }

    public void kwPriceUpdate() {
        kilowattPrices = new ArrayList<>(KilowattDAO.getKilowattPrice());

        if (kilowattPrices.get(0).getPrice() != Double.parseDouble(legalEntityPrice.getText())) {
            kilowattPrices.get(0).setChangedOnDate(LocalDate.now());
            kilowattPrices.get(0).setPrice(Double.parseDouble(legalEntityPrice.getText()));
        }
        if (kilowattPrices.get(1).getPrice() != Double.parseDouble(privateSubscriberPrice.getText())) {
            kilowattPrices.get(1).setChangedOnDate(LocalDate.now());
            kilowattPrices.get(1).setPrice(Double.parseDouble(privateSubscriberPrice.getText()));
        }

        KilowattDAO.saveOrUpdateKilowatt(kilowattPrices.get(0), kilowattPrices.get(1));
        updateKilowattView.setVisible(false);
    }

    public void toggleKwUpdate() {
        kilowattPrices = new ArrayList<>(KilowattDAO.getKilowattPrice());
        flag = updateKilowattView.isVisible();
        updateKilowattView.setVisible(!flag);

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        legalEntityDate.setText("Changed on: " + kilowattPrices.get(0).getChangedOnDate().format(pattern));
        legalEntityPrice.setText(String.valueOf(kilowattPrices.get(0).getPrice()));
        privateSubscriberDate.setText("Changed on: " + kilowattPrices.get(1).getChangedOnDate().format(pattern));
        privateSubscriberPrice.setText(String.valueOf(kilowattPrices.get(1).getPrice()));
    }

    private void updateClientView() {
        final ObservableList<Client> clients = FXCollections.observableArrayList(ClientDAO.getClient());

        clientListTableViewFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        clientListTableViewLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        clientListTableViewEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        highestBillPaidTableView.setCellValueFactory(new PropertyValueFactory<>("address"));
        totalPaidTableView.setCellValueFactory(new PropertyValueFactory<>("type"));

        clientListTableView.setItems(clients);
    }

    private void updateClientBills() {
        Client client = clientListTableView.getSelectionModel().getSelectedItem();
        final ObservableList<Bill> bills = FXCollections.observableArrayList(BillDAO.getClientsBills(client));

        updateBillTableViewConsumption.setCellValueFactory(new PropertyValueFactory<>("electricity_consumption"));
        updateBillTableViewDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        updateBillTableViewKwPrice.setCellValueFactory(new PropertyValueFactory<>("kilowatt_price"));
        updateBillTableViewPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        updateBillTableViewStatus.setCellValueFactory(new PropertyValueFactory<>("paid"));

        updateTableView.setItems(bills);
    }

    public void backToMain(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/Index.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}