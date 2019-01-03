package com.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUI extends Application {
    private Stage newWindow;
    private static GUI instance;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Platform.runLater(new ClientThread());
            launchGetNickScene();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/roomList.fxml"));
            Parent root = loader.load();
//            RoomList controller = loader.getController();
            primaryStage.setTitle("Chinese Checkers Pro");
            primaryStage.setScene(new Scene(root, 1280, 720));
            primaryStage.setResizable(false);
            primaryStage.setOnCloseRequest(e -> {
                try {
                    Client.logOut();
                } catch (ChineseCheckersException ex) {
                    new ChineseCheckersWindowException(ex.getMessage()).showWindow();
                }
                Platform.exit();
            });
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/cc_pro_ico.png")));
//            controller.refresh();
            primaryStage.show();
        } catch (ChineseCheckersException e) {
            new ChineseCheckersWindowException("Cannot connect to server").showWindow();
        }
    }

    public void launchGetNickScene() throws ChineseCheckersWindowException {
        GridPane layout = new GridPane();
        Stage welcomeWindow = new Stage();
        welcomeWindow.setTitle("Welcome!");
        Label welcome = new Label("Welcome to Chinese Checkers Pro");
        Label nickLabel = new Label("Set your nick:");
        TextField nickField = new TextField("Player");
        layout.add(welcome,0,0,2,1);
        layout.add(nickLabel, 0,1);
        layout.add(nickField, 1,1);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setHgap(10);
        layout.setVgap(20);
        layout.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(layout, 400, 150);
        layout.setStyle("-fx-background-color: #2783e2; -fx-font-size: 15px;");
        welcome.setStyle("-fx-text-fill: #fff; -fx-text-alignment: center; -fx-font-family: 'Berlin Sans FB'; -fx-font-size: 22px");
        nickLabel.setStyle("-fx-text-fill: #fff;");
        nickField.setStyle("-fx-background-color: #2783a2; -fx-text-fill: #fff;");
        welcomeWindow.setScene(dialogScene);
        welcomeWindow.initModality(Modality.APPLICATION_MODAL);
        welcomeWindow.initStyle(StageStyle.UNDECORATED);
        welcomeWindow.getIcons().add(new Image(getClass().getResourceAsStream("/images/cc_pro_ico.png")));

        nickField.setOnKeyPressed(keyEvent -> {
            try {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    Client.logIn(nickField.getText());
                    welcomeWindow.close();
                }   else if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    Client.logOut();
                    welcomeWindow.close();
                    Platform.exit();
                }
            } catch (ChineseCheckersException e) {
                new ChineseCheckersWindowException(e.getMessage()).showWindow();
            }
        });
        welcomeWindow.showAndWait();
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
            String roomId = roomData.split(";")[0];
            newWindow = new Stage();
            newWindow.setScene(new Scene(root, 1280, 720));
            newWindow.setResizable(false);
            newWindow.setTitle("Chinese Checkers Pro - Room #" + roomId);
            newWindow.setOnCloseRequest(e -> Room.endGame());
            newWindow.getIcons().add(new Image(getClass().getResourceAsStream("/images/room_ico.png")));
            newWindow.show();
        } catch (Exception e) {
            throw new ChineseCheckersWindowException("FXML error: " + e.getMessage());
        }
    }

}
