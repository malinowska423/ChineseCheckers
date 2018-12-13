package com.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class GUI extends Application {
    private Stage newWindow;
    private static GUI instance;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/roomList.fxml"));
        primaryStage.setTitle("Chinese Checkers Pro");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.show();


    }

    public static GUI getInstance() {
        if (instance == null) {
            synchronized (GUI.class) {
                if (instance == null) {
                    instance = new GUI();
                }
            }
        }
        return instance;
    }

    public void createRoomScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/createRoom.fxml"));
            newWindow = new Stage();
            newWindow.setScene(new Scene(root, 300,700));
            newWindow.setResizable(false);
            newWindow.setTitle("Create new room");
            newWindow.setAlwaysOnTop(true);
            newWindow.show();
        } catch (IOException e) {
            System.out.println("FXML exception");
        }
    }

    public void gameRoomScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/room.fxml"));
            newWindow = new Stage();
            newWindow.setScene(new Scene(root, 1280,720));
            newWindow.setResizable(false);
            newWindow.setTitle("Room #");
            newWindow.setAlwaysOnTop(true);
            newWindow.setOnCloseRequest(e -> Room.endGame());
            newWindow.show();
        } catch (IOException e) {
            System.out.println("FXML exception");
        }
    }
}
