package com.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/cc_pro_ico.png")));
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
            newWindow.setScene(new Scene(root, 400,500));
            newWindow.setResizable(false);
            newWindow.setTitle("Create new room");
            newWindow.setAlwaysOnTop(true);
            newWindow.getIcons().add(new Image(getClass().getResourceAsStream("/images/add.png")));
            newWindow.showAndWait();
        } catch (IOException e) {
            System.out.println("FXML exception");
        }
    }

    public void gameRoomScene(String label) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/room.fxml"));
            if (newWindow != null && newWindow.getTitle().equals("Create new room")) {
                newWindow.close();
            }
            newWindow = new Stage();
            newWindow.setScene(new Scene(root, 1280,720));
            newWindow.setResizable(false);
            newWindow.setTitle(label);
            newWindow.setAlwaysOnTop(false);
            newWindow.setOnCloseRequest(e -> Room.endGame());
            newWindow.getIcons().add(new Image(getClass().getResourceAsStream("/images/room_ico.png")));
            newWindow.show();
        } catch (IOException e) {
            System.out.println("FXML exception");
        }
    }

    public void closeCurrentWindow(Stage thisWindow){
        thisWindow.close();
    }
}
