package com.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

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

    public void launchCreateRoomScene(String gameOptions) throws ChineseCheckersWindowException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/createRoom.fxml"));
            Parent root = loader.load();
            CreateRoom controller = loader.getController();
            controller.initialize(gameOptions);
            newWindow = new Stage();
            newWindow.setScene(new Scene(root, 400,500));
            newWindow.setResizable(false);
            newWindow.setTitle("Create new room");
            newWindow.getIcons().add(new Image(getClass().getResourceAsStream("/images/add.png")));
            newWindow.initModality(Modality.APPLICATION_MODAL);
            newWindow.show();
        } catch (ChineseCheckersWindowException e) {
            throw e;
        } catch (Exception e) {
            throw new ChineseCheckersWindowException("FXML error: " + e.getMessage());
        }
    }

    public void launchGameRoomScene(String roomData) throws ChineseCheckersWindowException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/room.fxml"));
            Parent root = loader.load();
            Room controller = loader.getController();
            controller.initialize(roomData);
            if (newWindow != null && newWindow.getTitle().equals("Create new room")) {
                newWindow.close();
            }
            String title = roomData.split(";")[0];
            newWindow = new Stage();
            newWindow.setScene(new Scene(root, 1280, 720));
            newWindow.setResizable(false);
            newWindow.setTitle(title);
            newWindow.setOnCloseRequest(e -> Room.endGame());
            newWindow.getIcons().add(new Image(getClass().getResourceAsStream("/images/room_ico.png")));
            newWindow.show();
        } catch (Exception e) {
            throw new ChineseCheckersWindowException("FXML error: " + e.getMessage());
        }
    }

}
