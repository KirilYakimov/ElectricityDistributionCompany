package controllers;

import com.jfoenix.controls.JFXButton;
import dao.ClientStatisticDAO;
import dao.KilowattDAO;
import entity.KilowattPrice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class MainController {
    @FXML
    private Label incomeLabel;
    @FXML
    private Label expensesLabel;
    @FXML
    private Label profitLabel;
    @FXML
    private Label netProfitLabel;
    @FXML
    private AnchorPane CompanyStatisticsView1;
    @FXML
    private JFXButton backToMain;
    @FXML
    private JFXButton statisticsButton;

    @FXML
    private TextField legalEntityPrice;
    @FXML
    private TextField privateSubscriberPrice;
    @FXML
    private AnchorPane kilowattPriceView;

    @FXML
    private void initialize() {
        final ArrayList<KilowattPrice> kilowattPrices = new ArrayList<>(KilowattDAO.getKilowattPrice());

        if (kilowattPrices.size() == 0) {
            kilowattPriceView.setVisible(true);
        }

        backToMain.setOnAction(event -> CompanyStatisticsView1.setVisible(false));

    }

    public void saveKilowattPrice() {
        LocalDate localDate = LocalDate.now();

        KilowattPrice legalEntity = new KilowattPrice(localDate, new BigDecimal(legalEntityPrice.getText()));
        KilowattPrice privateSubscriber = new KilowattPrice(localDate, new BigDecimal(privateSubscriberPrice.getText()));

        KilowattDAO.saveKilowattPrice(legalEntity);
        KilowattDAO.saveKilowattPrice(privateSubscriber);
        kilowattPriceView.setVisible(false);
    }

    public void EmployeeButton(ActionEvent actionEvent) throws Exception {
        changeScene(actionEvent,"Employee");
    }

    public void ClientButton(ActionEvent actionEvent) throws Exception {
        changeScene(actionEvent, "Client");
    }

    public void RealEstateButton(ActionEvent actionEvent) throws Exception {
        changeScene(actionEvent, "RealEstate");
    }

    public void PayButton(ActionEvent actionEvent) throws Exception {
        changeScene(actionEvent, "Bills");
    }

    public void changeScene(ActionEvent actionEvent, String sceneName) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("/view/" + sceneName + ".fxml"));
        loginParent.getStylesheets().add("/styling/main.css");
        Scene mainScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }

    public void statisticsView( ) {
        BigDecimal income = ClientStatisticDAO.getIncome();
        BigDecimal expenses = ClientStatisticDAO.getExpenses();
        CompanyStatisticsView1.setVisible(true);

        if (income != null) {
            incomeLabel.setText(income.toString());
        } else {
            incomeLabel.setText("No income to calculate!");
        }
        if (expenses != null) {
            expensesLabel.setText(expenses.toString());
        }else {
            expensesLabel.setText("No expenses to calculate!");
        }if (income != null && expenses != null) {
            profitLabel.setText(income.subtract(expenses).toString());
            netProfitLabel.setText((income.subtract(expenses).multiply(BigDecimal.valueOf(10./100))).toString());
        }else {
            profitLabel.setText("No profit to calculate!");
            netProfitLabel.setText("No net profit to calculate!");
        }

    }
}
