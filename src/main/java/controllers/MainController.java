package controllers;

import dao.KilowattDAO;
import entity.KilowattPrice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class MainController {
    @FXML
    private TextField legalEntityPrice;
    @FXML
    private TextField privateSubscriberPrice;
    @FXML
    private AnchorPane kilowattPriceView;

    @FXML
    private void initialize() {
        final ArrayList<KilowattPrice> kilowattPrices = new ArrayList<>(KilowattDAO.getKilowattPrice());
        if(kilowattPrices.size() == 0){
            kilowattPriceView.setVisible(true);
        }
    }

    public void saveKilowattPrice() {
        LocalDate localDate = LocalDate.now();

        KilowattPrice legalEntity = new KilowattPrice(localDate, Double.parseDouble(legalEntityPrice.getText()));
        KilowattPrice privateSubscriber = new KilowattPrice(localDate, Double.parseDouble(privateSubscriberPrice.getText()));

        KilowattDAO.saveKilowattPrice(legalEntity);
        KilowattDAO.saveKilowattPrice(privateSubscriber);
        kilowattPriceView.setVisible(false);
    }

    public void EmployeeButton(ActionEvent actionEvent) throws Exception {
        changeScene(actionEvent,"Employee");
    }

    public void ClientButton(ActionEvent actionEvent) throws Exception {
        changeScene(actionEvent,"Client");
    }

    public void RealEstateButton(ActionEvent actionEvent) throws Exception {
        changeScene(actionEvent,"RealEstate");
    }

    public void PayButton(ActionEvent actionEvent) throws Exception {
        changeScene(actionEvent,"Bills");
    }

    public void changeScene(ActionEvent actionEvent,String sceneName) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("/view/" + sceneName +".fxml"));
        loginParent.getStylesheets().add("/styling/main.css");
        Scene mainScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }
}
