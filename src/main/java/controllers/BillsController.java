package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import configuration.DataValidation;
import dao.BillDAO;
import dao.ClientStatisticDAO;
import dao.KilowattDAO;
import entity.Bill;
import entity.Client;
import entity.KilowattPrice;
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

import java.io.IOException;
import java.math.BigDecimal;
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
    private JFXTextField privateSubscriberPrice;
    @FXML
    private JFXTextField legalEntityPrice;
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
    private JFXTextField createBillKwPrice;
    @FXML
    private JFXTextField createBillElectricConsumption;
    @FXML
    private DatePicker createBillDate;
    //
    @FXML
    private JFXTextField clientFilter;
    @FXML
    public JFXButton backToClientsView;

    boolean flag;
    private ObservableList<Client> clients = null;
    private ArrayList<KilowattPrice> kilowattPrices = null;

    @FXML
    private void initialize() {
        updateClientView();
        clientFilter.setOnMouseClicked(click -> clientListTableView.getSelectionModel().clearSelection());
        backToClientsView.setOnAction(click -> {
            updateClientView();
            updateView.setVisible(false);
        });
    }

    public void doubleClickUpd(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            toggleUpdate();
        }
    }

    public void toggleUpdate() {
        if (!clientFilter.getText().isEmpty()) {
            boolean numbers = DataValidation.doubleValidator(clientFilter, "Please enter valid number!");
            if (numbers) {
                getClientsWhoPaidEqualOrLess(new BigDecimal(clientFilter.getText()));
            }
        }

        if (clientListTableView.getSelectionModel().getSelectedItem() != null) {
            if (!updateView.isVisible()) {
                createBillView.setVisible(false);
            }
            flag = updateView.isVisible();
            updateView.setVisible(!flag);
            updateClientBills();

            Client client = clientListTableView.getSelectionModel().getSelectedItem();

            client_id.setText(String.valueOf(client.getId()));
            clientName.setText(client.getFirstName() + " " + client.getLastName());
            clientAddress.setText(client.getAddress());
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
        Client client = clientListTableView.getSelectionModel().getSelectedItem();
        Bill bill = updateTableView.getSelectionModel().getSelectedItem();
        flag = bill.isPaid();
        bill.setPaid(!flag);

        BillDAO.payBill(bill);

        client.getClientStatistics().setHighestPricePaid(ClientStatisticDAO.getClientHighestBillPaid(client));
        client.getClientStatistics().setTotalPricePaid(ClientStatisticDAO.getClientTotalBillsPaid(client));
        ClientStatisticDAO.saveOrUpdateClientStatistic(client.getClientStatistics());

        updateClientBills();
    }

    public void createBill() {
        kilowattPrices = new ArrayList<>(KilowattDAO.getKilowattPrice());
        Client client = clientListTableView.getSelectionModel().getSelectedItem();

        boolean isConsumptionValid = DataValidation.doubleValidator(createBillElectricConsumption, "Enter valid energy consumption!");

        BigDecimal energyConsumption = new BigDecimal(createBillElectricConsumption.getText());
        BigDecimal kwPrice = kilowattPrices.get(client.getType().ordinal()).getPrice();
        BigDecimal price = energyConsumption.multiply(kwPrice);

        if (isConsumptionValid) {
            Bill bill = new Bill(
                    client,
                    createBillDate.getValue(),
                    kwPrice,
                    energyConsumption,
                    price,
                    false
            );
            BillDAO.saveBill(bill);
            updateClientBills();
            createBillView.setVisible(false);
        }
    }

    public void deleteBill() {
        Bill bill = updateTableView.getSelectionModel().getSelectedItem();
        BillDAO.deleteBill(bill);
        updateClientBills();
    }

    public void kwPriceUpdate() {
        kilowattPrices = new ArrayList<>(KilowattDAO.getKilowattPrice());

        if (!kilowattPrices.get(0).getPrice().equals(new BigDecimal(legalEntityPrice.getText()))) {
            kilowattPrices.get(0).setChangedOnDate(LocalDate.now());
            kilowattPrices.get(0).setPrice(new BigDecimal(legalEntityPrice.getText()));
        }
        if (!kilowattPrices.get(1).getPrice().equals(new BigDecimal(legalEntityPrice.getText()))) {
            kilowattPrices.get(1).setChangedOnDate(LocalDate.now());
            kilowattPrices.get(1).setPrice(new BigDecimal(privateSubscriberPrice.getText()));
        }

        KilowattDAO.saveOrUpdateKilowatt(kilowattPrices.get(0), kilowattPrices.get(1));
        updateKilowattView.setVisible(false);
    }

    public void toggleKwUpdate() {
        kilowattPrices = new ArrayList<>(KilowattDAO.getKilowattPrice());
        flag = updateKilowattView.isVisible();
        updateKilowattView.setVisible(!flag);

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        legalEntityPrice.setText(String.valueOf(kilowattPrices.get(0).getPrice()));
        legalEntityPrice.setPromptText("Last changed:" + kilowattPrices.get(0).getChangedOnDate().format(pattern));
        privateSubscriberPrice.setText(String.valueOf(kilowattPrices.get(1).getPrice()));
        privateSubscriberPrice.setPromptText("Last changed:" + kilowattPrices.get(1).getChangedOnDate().format(pattern));
    }

    private void updateClientView() {
        clients = FXCollections.observableArrayList(ClientStatisticDAO.getClientStatistic());

        clientListTableViewFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        clientListTableViewLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        clientListTableViewEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        highestBillPaidTableView.setCellValueFactory(new PropertyValueFactory<>("highestPricePaid"));
        totalPaidTableView.setCellValueFactory(new PropertyValueFactory<>("totalPricePaid"));

        clientListTableView.setItems(clients);
    }

    private void getClientsWhoPaidEqualOrLess(BigDecimal number) {
        clients = FXCollections.observableArrayList(ClientStatisticDAO.ClientsWhoPaidEqualOrLess(number));

        clientListTableViewFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        clientListTableViewLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        clientListTableViewEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        highestBillPaidTableView.setCellValueFactory(new PropertyValueFactory<>("highestPricePaid"));
        totalPaidTableView.setCellValueFactory(new PropertyValueFactory<>("totalPricePaid"));

        clientListTableView.setItems(clients);

        clientFilter.clear();
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
        parent.getStylesheets().add("/styling/main.css");
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}