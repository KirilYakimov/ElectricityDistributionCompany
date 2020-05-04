package controllers;

import dao.ClientDAO;
import entity.Client;
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
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField address;
    @FXML
    private ComboBox<ClientType> clientType;
    //--------------UPDATE-----------------//
    @FXML
    private TextField firstNameUpd;
    @FXML
    private TextField lastNameUpd;
    @FXML
    private TextField emailUpd;
    @FXML
    private TextField addressUpd;
    @FXML
    private ComboBox<ClientType> clientTypeUpd;

    @FXML
    private AnchorPane addView;
    @FXML
    private AnchorPane updateView;

    private Client client;

    @FXML
    private void initialize() {
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

    public void toggleAdd() {
        boolean flag = addView.isVisible();
        addView.setVisible(!flag);
    }

    public void saveClient() {
        client = new Client(
                firstName.getText(),
                lastName.getText(),
                email.getText(),
                address.getText(),
                clientType.getSelectionModel().getSelectedItem()
        );
        ClientDAO.saveClient(client);
        addView.setVisible(false);
    }

    public void doubleClickUpd(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            toggleUpdate();
        }
    }

    public void toggleUpdate() {
        boolean flag = updateView.isVisible();
        updateView.setVisible(!flag);
        if (clientListTableView.getSelectionModel().getSelectedItem() != null) {
            client = clientListTableView.getSelectionModel().getSelectedItem();
            firstNameUpd.setText(client.getFirstName());
            lastNameUpd.setText(client.getLastName());
            emailUpd.setText(client.getEmail());
            addressUpd.setText(client.getAddress());
            clientType.setValue(client.getType());

        }
    }

    public void updateClient() {
        boolean flag = updateView.isVisible();

        client = clientListTableView.getSelectionModel().getSelectedItem();
        client.setFirstName(firstNameUpd.getText());
        client.setLastName(lastNameUpd.getText());
        client.setEmail(emailUpd.getText());
        client.setAddress(addressUpd.getText());
        client.setType(clientTypeUpd.getSelectionModel().getSelectedItem());

        ClientDAO.saveOrUpdateClient(client);
        updateView.setVisible(!flag);
    }

    public void deleteClient() {
        client = clientListTableView.getSelectionModel().getSelectedItem();
        ClientDAO.deleteClient(client);
    }

    public void backToMain(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/Index.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
