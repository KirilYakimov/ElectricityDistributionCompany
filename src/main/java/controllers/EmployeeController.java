package controllers;

import com.jfoenix.controls.JFXTextField;
import configuration.DataValidation;
import dao.EmployeeDAO;
import entity.Employee;
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
import java.time.LocalDate;

public class EmployeeController {
    //--------------TABLE-----------------//
    @FXML
    private TableView<Employee> employeeListTableView;
    @FXML
    private TableColumn<Employee, String> employeeListTableViewFirstName;
    @FXML
    private TableColumn<Employee, String> employeeListTableViewLastName;
    @FXML
    private TableColumn<Employee, String> employeeListTableViewEmail;
    @FXML
    private TableColumn<Employee, LocalDate> employeeListTableViewBirthDate;
    @FXML
    private TableColumn<Employee, Double> employeeListTableViewSalary;
    //--------------CREATE-----------------//
    @FXML
    private JFXTextField firstName;
    @FXML
    private JFXTextField lastName;
    @FXML
    private JFXTextField salary;
    @FXML
    private JFXTextField email;
    @FXML
    private DatePicker birthDate;
    //--------------UPDATE-----------------//
    @FXML
    private JFXTextField firstNameUpd;
    @FXML
    private JFXTextField lastNameUpd;
    @FXML
    private JFXTextField emailUpd;
    @FXML
    private JFXTextField salaryUpd;
    @FXML
    private DatePicker birthDateUpd;
    //buttons for toggle visibility of employee form//
    @FXML
    private AnchorPane updateView;
    @FXML
    private AnchorPane addEmployeeView;

    private Employee employee;

    @FXML
    private void initialize() {
        employeeTableView();
    }

    public void saveEmployee() {
        boolean isEmailValid = DataValidation.emailValidation(email, "Please enter a valid email!");
        boolean isFirstNameValid = DataValidation.textValidation(firstName, "Please enter a valid name!");
        boolean isLastNameValid = DataValidation.textValidation(lastName, "Please enter a valid name!");
        boolean isSalaryValid = DataValidation.doubleValidator(salary, "Please enter valid salary!");

        if (isEmailValid && isFirstNameValid && isLastNameValid && isSalaryValid) {
            employee = new Employee(
                    firstName.getText(),
                    lastName.getText(),
                    email.getText(),
                    birthDate.getValue(),
                    Double.parseDouble(salary.getText()));

            EmployeeDAO.saveEmployee(employee);
            employeeTableView();
            addEmployeeView.setVisible(false);
        }
    }

    public void toggleAddEmployee() {
        boolean flag = addEmployeeView.isVisible();
        addEmployeeView.setVisible(!flag);
        updateView.setVisible(false);
    }

    public void updateEmployee() {
        boolean isEmailValid = DataValidation.emailValidation(emailUpd, "Please enter a valid email!");
        boolean isFirstNameValid = DataValidation.textValidation(firstNameUpd, "Please enter a valid name!");
        boolean isLastNameValid = DataValidation.textValidation(lastNameUpd, "Please enter a valid name!");
        boolean isSalaryValid = DataValidation.doubleValidator(salaryUpd, "Please enter valid salary!");

        if (isEmailValid && isFirstNameValid && isLastNameValid && isSalaryValid) {
            employee = employeeListTableView.getSelectionModel().getSelectedItem();

            employee.setFirstName(firstNameUpd.getText());
            employee.setLastName(lastNameUpd.getText());
            employee.setEmail(emailUpd.getText());
            employee.setBirthDate(birthDateUpd.getValue());
            employee.setSalary(Double.parseDouble(salaryUpd.getText()));

            EmployeeDAO.saveOrUpdateEmployee(employee);
            updateView.setVisible(false);
            employeeTableView();
        }
    }

    public void toggleUpdate() {
        boolean flag = updateView.isVisible();
        if (employeeListTableView.getSelectionModel().getSelectedItem() != null) {
            employee = employeeListTableView.getSelectionModel().getSelectedItem();

            firstNameUpd.setText(employee.getFirstName());
            lastNameUpd.setText(employee.getLastName());
            emailUpd.setText(employee.getEmail());
            birthDateUpd.setValue(LocalDate.parse(employee.getBirthDate().toString()));
            salaryUpd.setText(Double.toString(employee.getSalary()));

            updateView.setVisible(!flag);
            addEmployeeView.setVisible(false);
        }
    }

    public void doubleClickUpd(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            toggleUpdate();
        }
    }

    public void deleteEmployee() {
        if (employeeListTableView.getSelectionModel().getSelectedItem() != null) {
            employee = employeeListTableView.getSelectionModel().getSelectedItem();
            EmployeeDAO.deleteEmployee(employee);
            employeeTableView();
        }
    }

    private void employeeTableView() {
        ObservableList<Employee> observableList = FXCollections.observableList(EmployeeDAO.getEmployees());

        employeeListTableViewFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        employeeListTableViewLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        employeeListTableViewEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        employeeListTableViewBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        employeeListTableViewSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        employeeListTableView.setItems(observableList);
    }

    public void backToMain(ActionEvent actionEvent) throws IOException {
        Parent employeeParent = FXMLLoader.load(getClass().getResource("/view/Index.fxml"));
        Scene employeeScene = new Scene(employeeParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(employeeScene);
        window.show();
    }
}