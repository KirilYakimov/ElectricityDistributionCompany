package controllers;

import com.jfoenix.controls.JFXTextField;
import configuration.DataValidation;
import dao.ClientDAO;
import dao.ClientStatisticDAO;
import entity.Client;
import entity.ClientStatistic;
import entity.ClientType;
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

public class ClientController {
    //--------------TABLE-----------------//
    @FXML
    private TableView<Client> clientListTableView;
    @FXML
    private TableColumn<Object, Object> clientListTableViewFirstName;
    @FXML
    private TableColumn<Object, Object> clientListTableViewLastName;
    @FXML
    private TableColumn<Object, Object> clientListTableViewEmail;
    @FXML
    private TableColumn<Object, Object> clientListTableViewAddress;
    @FXML
    private TableColumn<Object, Object> clientListTableViewType;
    //--------------CREATE-----------------//
    @FXML
    private JFXTextField firstName;
    @FXML
    private JFXTextField lastName;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField address;
    @FXML
    private ComboBox<ClientType> clientType;
    //--------------UPDATE-----------------//
    @FXML
    private JFXTextField firstNameUpd;
    @FXML
    private JFXTextField lastNameUpd;
    @FXML
    private JFXTextField emailUpd;
    @FXML
    private JFXTextField addressUpd;
    @FXML
    private ComboBox<ClientType> clientTypeUpd;

    @FXML
    private AnchorPane addView;
    @FXML
    private AnchorPane updateView;

    private Client client;

    @FXML
    private void initialize() {
        final ObservableList<ClientType> types = FXCollections.observableArrayList(ClientType.PRIVATE_SUBSCRIBER, ClientType.LEGAL_ENTITY);
        ClientTableView();

        clientType.setItems(types);
        clientTypeUpd.setItems(types);
    }

    public void toggleAdd() {
        boolean flag = addView.isVisible();
        addView.setVisible(!flag);
        updateView.setVisible(false);
    }

    public void saveClient() {
        boolean isEmailValid = DataValidation.emailValidation(email, "Please enter a valid email!");
        boolean isFirstNameValid = DataValidation.textValidation(firstName, "Please enter a valid name!");
        boolean isLastNameValid = DataValidation.textValidation(lastName, "Please enter a valid name!");
        boolean isAddressValid = DataValidation.textValidation(address, "Please enter a valid address!");

        if (isEmailValid && isFirstNameValid && isLastNameValid && isAddressValid) {
            client = new Client(
                    firstName.getText(),
                    lastName.getText(),
                    email.getText(),
                    address.getText(),
                    clientType.getSelectionModel().getSelectedItem()
            );
        }

        ClientDAO.saveClient(client);
        ClientStatisticDAO.saveClientStatistic(new ClientStatistic(client));

        ClientTableView();
        addView.setVisible(false);
    }

    public void doubleClickUpd(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2 && !clientListTableView.getSelectionModel().getSelectedCells().isEmpty()) {
            toggleUpdate();
        }
    }

    public void toggleUpdate() {
        boolean flag = updateView.isVisible();

        if (clientListTableView.getSelectionModel().getSelectedItem() != null) {
            client = clientListTableView.getSelectionModel().getSelectedItem();
            firstNameUpd.setText(client.getFirstName());
            lastNameUpd.setText(client.getLastName());
            emailUpd.setText(client.getEmail());
            addressUpd.setText(client.getAddress());
            clientType.setValue(client.getType());

            updateView.setVisible(!flag);
            addView.setVisible(false);
        }
    }

    public void updateClient() {
        boolean flag = updateView.isVisible();

        boolean isEmailValid = DataValidation.emailValidation(email, "Please enter a valid email!");
        boolean isFirstNameValid = DataValidation.textValidation(firstName, "Please enter a valid name!");
        boolean isLastNameValid = DataValidation.textValidation(lastName, "Please enter a valid name!");
        boolean isAddressValid = DataValidation.textValidation(address, "Please enter a valid address!");

        if (isEmailValid && isFirstNameValid && isLastNameValid && isAddressValid) {
            client = clientListTableView.getSelectionModel().getSelectedItem();
            client.setFirstName(firstNameUpd.getText());
            client.setLastName(lastNameUpd.getText());
            client.setEmail(emailUpd.getText());
            client.setAddress(addressUpd.getText());
            client.setType(clientTypeUpd.getSelectionModel().getSelectedItem());
            ClientDAO.saveOrUpdateClient(client);
            ClientTableView();
            updateView.setVisible(!flag);
        }
    }

    public void deleteClient() {
        if (clientListTableView.getSelectionModel().getSelectedItem() != null) {
            client = clientListTableView.getSelectionModel().getSelectedItem();
            ClientDAO.deleteClient(client);
            ClientTableView();
        }
    }

    private void ClientTableView() {
        final ObservableList<Client> clients = FXCollections.observableArrayList(ClientDAO.getClient());
        final ObservableList<ClientType> types = FXCollections.observableArrayList(ClientType.PRIVATE_SUBSCRIBER, ClientType.LEGAL_ENTITY);

        clientListTableViewFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        clientListTableViewLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        clientListTableViewEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clientListTableViewAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        clientListTableViewType.setCellValueFactory(new PropertyValueFactory<>("type"));

        clientListTableView.setItems(clients);
        clientType.setItems(types);
        clientTypeUpd.setItems(types);
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
