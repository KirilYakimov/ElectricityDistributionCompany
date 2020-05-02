package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    public void EmployeeButton(ActionEvent actionEvent) throws Exception {
        changeScene(actionEvent,"Employee");
    }
    public void ClientButton(ActionEvent actionEvent) throws Exception {
        changeScene(actionEvent,"Client");
    }

    public void changeScene(ActionEvent actionEvent,String sceneName) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("/view/" + sceneName +".fxml"));
        Scene mainScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }
}
