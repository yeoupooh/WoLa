package com.subakstudio.wola;

import com.subakstudio.wifi.WifiMacOSX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Created by yeoupooh on 4/12/16.
 */
public class WoLa extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        System.out.println(new WifiMacOSX().updateSignals());
        Parent root = null;
        try {
            URL fxml = getClass().getResource("/com/subakstudio/wola/WoLa.fxml");
            System.out.println(fxml);
            root = FXMLLoader.load(fxml);
            Scene scene = new Scene(root, 600, 400);
            stage.setTitle("WoLa");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
