package com.yaryna.ch.tax;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("startMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),956,573);
        stage.setTitle("Taxes");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
