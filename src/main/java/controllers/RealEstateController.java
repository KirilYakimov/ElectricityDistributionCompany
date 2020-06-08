package controllers;

import com.jfoenix.controls.JFXTextField;
import configuration.DataValidation;
import dao.RealEstateDAO;
import entity.RealEstate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;

public class RealEstateController {
    private RealEstate realEstate;
    //
    @FXML
    private TableView<RealEstate> realEstateListTableView;
    @FXML
    private TableColumn<Object, Object> realEstateListTableViewAddress;
    @FXML
    private TableColumn<Object, Object> realEstateListTableViewHBP;
    @FXML
    private TableColumn<Object, Object> realEstateListTableViewHBE;
    @FXML
    private TableColumn<Object, Object> realEstateListTableViewMaintenance;
    //
    @FXML
    private JFXTextField address;
    @FXML
    private JFXTextField headBranchPhone;
    @FXML
    private JFXTextField headBranchEmail;
    @FXML
    private JFXTextField maintenance;
    //
    @FXML
    private JFXTextField addressUpd;
    @FXML
    private JFXTextField headBranchPhoneUpd;
    @FXML
    private JFXTextField headBranchEmailUpd;
    @FXML
    private JFXTextField maintenanceUpd;
    //
    @FXML
    private AnchorPane addView;
    @FXML
    private AnchorPane updateView;

    @FXML
    private void initialize() {
        realEstateTableView();
    }

    public void toggleAdd() {
        boolean flag = addView.isVisible();
        addView.setVisible(!flag);
        updateView.setVisible(false);
    }

    public void doubleClickUpd(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            toggleUpdate();
        }
    }

    public void toggleUpdate() {
        boolean flag = updateView.isVisible();
        if (realEstateListTableView.getSelectionModel().getSelectedItem() != null) {
            realEstate = realEstateListTableView.getSelectionModel().getSelectedItem();

            addressUpd.setText(realEstate.getAddress());
            headBranchPhoneUpd.setText(Integer.toString(realEstate.getPhone()));
            headBranchEmailUpd.setText(realEstate.getEmail());
            maintenanceUpd.setText(realEstate.getMaintenance().toString());

            updateView.setVisible(!flag);
            addView.setVisible(false);
        }
    }

    public void addRealEstate() {
        boolean isEmailValid = DataValidation.emailValidation(headBranchEmail, "Please enter a valid email!");
        boolean isAddressValid = DataValidation.textValidation(address, "Please enter a valid address!");
        boolean isPhoneValid = DataValidation.phoneValidator(headBranchPhone, "Please enter a valid phone number!");
        boolean isMaintenanceValid = DataValidation.doubleValidator(maintenance, "Please enter valid maintenance!");

        if (isEmailValid && isAddressValid && isPhoneValid && isMaintenanceValid) {
            realEstate = new RealEstate(
                    address.getText(),
                    Integer.parseInt(headBranchPhone.getText()),
                    headBranchEmail.getText(),
                    new BigDecimal(maintenance.getText())
            );

            RealEstateDAO.saveRealEstate(realEstate);
            realEstateTableView();
            addView.setVisible(false);
        }
    }

    public void updateRealEstate() {
        boolean isEmailValid = DataValidation.emailValidation(headBranchEmailUpd, "Please enter a valid email!");
        boolean isAddressValid = DataValidation.textValidation(addressUpd, "Please enter a valid address!");
        boolean isPhoneValid = DataValidation.phoneValidator(headBranchPhoneUpd, "Please enter a valid phone number!");
        boolean isMaintenanceValid = DataValidation.doubleValidator(maintenanceUpd, "Please enter valid maintenance!");


        if (isEmailValid && isAddressValid && isPhoneValid && isMaintenanceValid) {
            realEstate = realEstateListTableView.getSelectionModel().getSelectedItem();

            realEstate.setAddress(addressUpd.getText());
            realEstate.setPhone(Integer.parseInt(headBranchPhoneUpd.getText()));
            realEstate.setEmail(headBranchEmailUpd.getText());
            realEstate.setMaintenance(new BigDecimal(maintenanceUpd.getText()));

            RealEstateDAO.saveOrUpdateRealEstate(realEstate);
            updateView.setVisible(false);
            realEstateTableView();
        }
    }

    public void deleteRealEstate() {
        if (realEstateListTableView.getSelectionModel().getSelectedItem() != null) {
            realEstate = realEstateListTableView.getSelectionModel().getSelectedItem();
            RealEstateDAO.deleteRealEstate(realEstate);
            realEstateTableView();
        }
    }

    private void realEstateTableView() {
        ObservableList<RealEstate> realEstates = FXCollections.observableList(RealEstateDAO.getRealEstates());

        realEstateListTableViewAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        realEstateListTableViewHBP.setCellValueFactory(new PropertyValueFactory<>("phone"));
        realEstateListTableViewHBE.setCellValueFactory(new PropertyValueFactory<>("email"));
        realEstateListTableViewMaintenance.setCellValueFactory(new PropertyValueFactory<>("maintenance"));

        realEstateListTableView.setItems(realEstates);
    }

    public void backToMain(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/Index.fxml"));
        parent.getStylesheets().add("/styling/main.css");
        Scene employeeScene = new Scene(parent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(employeeScene);
        window.show();
    }
}
