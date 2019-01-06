package com.client;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

class ChineseCheckersWindowException extends ChineseCheckersException {
    ChineseCheckersWindowException(String message) {
        super(message);
    }


    void showWindow(){
        Alert errorWindow = new Alert(Alert.AlertType.ERROR);
        errorWindow.setTitle("Error");
        errorWindow.setHeaderText("ChineseCheckersWindowException");
        errorWindow.setContentText(super.getMessage());
        errorWindow.setGraphic(null);
        DialogPane dialogPane = errorWindow.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/css/errorWindow.css").toExternalForm());
        dialogPane.getStyleClass().add("errorAlert");
        errorWindow.initStyle(StageStyle.TRANSPARENT);
        errorWindow.initModality(Modality.APPLICATION_MODAL);
        ((Stage) errorWindow.getDialogPane().getScene().getWindow())
                .getIcons().add(new Image(getClass().getResourceAsStream("/images/cc_pro_ico.png")));
        errorWindow.showAndWait();
    }
}
