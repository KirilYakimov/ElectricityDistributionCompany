package controllers.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {

    //Change scene on button action
    public void LoginButton(ActionEvent actionEvent) throws Exception {
        String j = System.getProperty("java.version");
        String fx = System.getProperty("javafx.version");
        System.out.println("v of fx : " + fx + " v of j : " + j);

        Parent loginParent = FXMLLoader.load(getClass().getResource("/view/Index.fxml"));
        Scene mainScene = new Scene(loginParent);
        mainScene.getStylesheets().add("/styling/main.css");
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }
}
