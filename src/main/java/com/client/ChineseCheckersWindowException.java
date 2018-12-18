package com.client;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

public class ChineseCheckersWindowException extends ChineseCheckersException {
    public ChineseCheckersWindowException(String message) {
        super(message);
    }


    public void showWindow(){
        Alert errorWindow = new Alert(Alert.AlertType.ERROR);
        errorWindow.setTitle("Error");
        errorWindow.setHeaderText("ChineseCheckersWindowException");
        errorWindow.setContentText(super.getMessage());
//        DialogPane dialogPane = errorWindow.getDialogPane();
//        dialogPane.getStylesheets().add(
//                getClass().getResource(".css").toExternalForm());
//        dialogPane.getStyleClass().add("errorWindow");
        errorWindow.initStyle(StageStyle.TRANSPARENT);
        errorWindow.initModality(Modality.APPLICATION_MODAL);
        errorWindow.showAndWait();
    }
}
