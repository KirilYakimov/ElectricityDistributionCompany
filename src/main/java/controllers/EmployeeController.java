package controllers;

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
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField salary;
    @FXML
    private TextField email;
    @FXML
    private DatePicker birthDate;
    //--------------UPDATE-----------------//
    @FXML
    private TextField firstNameUpd;
    @FXML
    private TextField lastNameUpd;
    @FXML
    private TextField emailUpd;
    @FXML
    private TextField salaryUpd;
    @FXML
    private DatePicker birthDateUpd;

    //buttons for toggle visibility of employee form//
    @FXML
    private AnchorPane updateView;
    @FXML
    private AnchorPane addEmployeeView;

    public Employee employee;
    //
    @FXML
    private void initialize() {
        final ObservableList<Employee> employees = FXCollections.observableArrayList(EmployeeDAO.getEmployees());

        employeeListTableViewFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        employeeListTableViewLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        employeeListTableViewEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        employeeListTableViewBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        employeeListTableViewSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        employeeListTableView.setItems(employees);

    }


    public void saveEmployee() {
        employee = new Employee(
                firstName.getText(),
                lastName.getText(),
                email.getText(),
                birthDate.getValue(),
                Double.parseDouble(salary.getText()));

        EmployeeDAO.saveEmployee(employee);
        addEmployeeView.setVisible(false);
    }

    //toggle visibility of employee form
    public void toggleAddEmployee() {
        boolean flag = addEmployeeView.isVisible();
        addEmployeeView.setVisible(!flag);
    }

    public void updateEmployee() {
        boolean flag = updateView.isVisible();

        employee = employeeListTableView.getSelectionModel().getSelectedItem();
        employee.setFirstName(firstNameUpd.getText());
        employee.setLastName(lastNameUpd.getText());
        employee.setEmail(emailUpd.getText());
        employee.setBirthDate(birthDateUpd.getValue());
        employee.setSalary(Double.parseDouble(salaryUpd.getText()));

        EmployeeDAO.saveOrUpdateEmployee(employee);
        updateView.setVisible(!flag);
    }

    public void toggleUpdate() {
        boolean flag = updateView.isVisible();
        updateView.setVisible(!flag);
        if (employeeListTableView.getSelectionModel().getSelectedItem() != null) {
            employee = employeeListTableView.getSelectionModel().getSelectedItem();
            firstNameUpd.setText(employee.getFirstName());
            lastNameUpd.setText(employee.getLastName());
            emailUpd.setText(employee.getEmail());
            birthDateUpd.setValue(LocalDate.parse(employee.getBirthDate().toString()));
            salaryUpd.setText(Double.toString(employee.getSalary()));
        }
    }

    public void doubleClickUpd(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            toggleUpdate();
        }
    }

    public void deleteEmployee() {
        employee = employeeListTableView.getSelectionModel().getSelectedItem();
        EmployeeDAO.deleteEmployee(employee);
    }

    public void backToMain(ActionEvent actionEvent) throws IOException {
        Parent employeeParent = FXMLLoader.load(getClass().getResource("/view/Index.fxml"));
        Scene employeeScene = new Scene(employeeParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(employeeScene);
        window.show();
    }
}