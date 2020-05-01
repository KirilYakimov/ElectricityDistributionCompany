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
        Parent loginParent = FXMLLoader.load(getClass().getResource("/view/Index.fxml"));
        Scene mainScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }
}
