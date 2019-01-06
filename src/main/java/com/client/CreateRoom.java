package com.client;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class CreateRoom {

    private ToggleGroup gameModeList = new ToggleGroup();
    private ToggleGroup numberOfPlayersList = new ToggleGroup();
    private ToggleGroup numberOfAIPlayersList = new ToggleGroup();

    @FXML
    HBox gameMode;
    @FXML
    HBox numberOfPlayers;
    @FXML
    HBox numberOfAIPlayers;
    @FXML
    Button createButton;



    void initialize(String gameModes) throws ChineseCheckersWindowException{
        createButton.setDisable(true);
        try {
            setListData(gameModes.split(";"), gameMode, gameModeList, "mode_item");
        } catch (ChineseCheckersException e) {
            throw new ChineseCheckersWindowException(e.getMessage());
        }
        for (Node option :
                gameMode.getChildren()) {
            option.setOnMouseClicked(mouseEvent -> {
                clearList(numberOfAIPlayers, numberOfAIPlayersList);
                try {
                    setListData(ClientThread.sendRequest("new-room-capacity;" + ((ToggleButton) option).getText()).split(";"), numberOfPlayers, numberOfPlayersList, "player_item");
                } catch (ChineseCheckersException e) {
                    new ChineseCheckersWindowException(e.getMessage()).showWindow();
                }
            });
        }
    }

    @FXML
    private void createRoom(){
        try {
            GUI.getInstance().launchGameRoomScene(ClientThread.sendRequest("create-new-room;" + getNewRoomData()));
        } catch (ChineseCheckersWindowException e) {
            e.showWindow();
        } catch (ChineseCheckersException e) {
            new ChineseCheckersWindowException(e.getMessage()).showWindow();
        }
    }

    private void setListData(String [] data, HBox node, ToggleGroup list, String style) throws ChineseCheckersException{
        if (data != null && data.length > 0) {
            clearList(node, list);
            ToggleButton option;
            for (String datum : data) {
                option = new ToggleButton(datum);
                list.getToggles().add(option);
                node.getChildren().add(option);
                option.getStyleClass().add(style);
            }
            for (Node optionPlayer :
                    numberOfPlayers.getChildren()) {
                optionPlayer.setOnMouseClicked(mouseEvent -> {
                    try {
                        int capacity = Integer.parseInt(((ToggleButton) optionPlayer).getText());
                        String [] aiList = new String[capacity];
                        for (int i=0; i < capacity; i++) {
                            aiList[i] = i + "";
                        }
                        setListData(aiList, numberOfAIPlayers, numberOfAIPlayersList, "player_item");
                    } catch (ChineseCheckersException e) {
                        new ChineseCheckersWindowException(e.getMessage()).showWindow();
                    }
                });
            }
            for (Node optionAI :
                    numberOfAIPlayers.getChildren()) {
                optionAI.setOnMouseClicked(mouseEvent -> createButton.setDisable(false));
            }
        } else {
            throw new ChineseCheckersException("Empty list of options");
        }
    }


    // return pattern: "mode;players;ai"
    private String getNewRoomData() throws ChineseCheckersException {
        if (gameModeList.getSelectedToggle() != null && numberOfAIPlayersList.getSelectedToggle() != null && numberOfAIPlayersList.getSelectedToggle() != null) {
            return (((ToggleButton) gameModeList.getSelectedToggle()).getText() + ";") + (((ToggleButton) numberOfPlayersList.getSelectedToggle()).getText() + ";") + (((ToggleButton) numberOfAIPlayersList.getSelectedToggle()).getText());
        } else {
            throw new ChineseCheckersException("Select all options");
        }
    }

    private void clearList(HBox node, ToggleGroup list) {
        list.getToggles().removeAll(list.getToggles());
        node.getChildren().removeAll(node.getChildren());
    }
}
