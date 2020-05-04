package controllers;

import dao.ClientDAO;
import dao.KilowattDAO;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BillsController {

    //
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
    //
    @FXML
    private Label client_id;
    @FXML
    private Label clientName;
    @FXML
    private Label clientAddress;

    @FXML
    private AnchorPane updateView;

    @FXML
    private void initialize() {
        final ObservableList<Client> clients = FXCollections.observableArrayList(ClientDAO.getClient());

        clientListTableViewFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        clientListTableViewLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        clientListTableViewEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        highestBillPaidTableView.setCellValueFactory(new PropertyValueFactory<>("address"));
        totalPaidTableView.setCellValueFactory(new PropertyValueFactory<>("type"));

        clientListTableView.setItems(clients);
    }

    public void doubleClickUpd(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            toggleUpdate();
        }
    }

    public void toggleUpdate() {
        boolean flag = updateView.isVisible();
        updateView.setVisible(!flag);
        Client client = clientListTableView.getSelectionModel().getSelectedItem();
        client_id.setText(String.valueOf(client.getId()));
        clientName.setText(client.getFirstName() + " " + client.getLastName());
        clientAddress.setText(client.getAddress());
    }


    public void payBill() {
    }

    public void createBill() {
    }

    public void deleteBill() {
    }

    public void kwPriceUpdate() {
        final ArrayList<KilowattPrice> kilowattPrices = new ArrayList<>(KilowattDAO.getKilowattPrice());

        if(kilowattPrices.get(0).getPrice() != Double.parseDouble(legalEntityPrice.getText())){
            kilowattPrices.get(0).setChangedOnDate(LocalDate.now());
            kilowattPrices.get(0).setPrice(Double.parseDouble(legalEntityPrice.getText()));
        }
        if (kilowattPrices.get(1).getPrice() != Double.parseDouble(privateSubscriberPrice.getText())){
            kilowattPrices.get(1).setChangedOnDate(LocalDate.now());
            kilowattPrices.get(1).setPrice(Double.parseDouble(privateSubscriberPrice.getText()));
        }

        KilowattDAO.saveOrUpdateKilowatt(kilowattPrices.get(0), kilowattPrices.get(1));
        updateKilowattView.setVisible(false);
    }

    public void toggleKwUpdate() {
        final ArrayList<KilowattPrice> kilowattPrices = new ArrayList<>(KilowattDAO.getKilowattPrice());
        boolean flag = updateKilowattView.isVisible();
        updateKilowattView.setVisible(!flag);

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        legalEntityDate.setText("Changed on: " + kilowattPrices.get(0).getChangedOnDate().format(pattern));
        legalEntityPrice.setText(String.valueOf(kilowattPrices.get(0).getPrice()));
        privateSubscriberDate.setText("Changed on: " + kilowattPrices.get(1).getChangedOnDate().format(pattern));
        privateSubscriberPrice.setText(String.valueOf(kilowattPrices.get(1).getPrice()));
    }

    public void backToMain(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/Index.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}