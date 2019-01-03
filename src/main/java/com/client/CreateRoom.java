package com.client;


import javafx.fxml.FXML;
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



    public void initialize(String options) throws ChineseCheckersWindowException{
        try {
            updateList(options);
        } catch (ChineseCheckersException e) {
            throw new ChineseCheckersWindowException(e.getMessage());
        }
    }
    @FXML
    private void createRoom(){
        //TODO: call method sending data to server and receive roomData before opening new window
        try {
            //getNewRoomData();
            String in =
                    "- - - - - - - 1 - - - - - - - \n" +
                            " - - - - - - 1 1 - - - - - - - \n" +
                            "- - - - - - 1 1 1 - - - - - - \n" +
                            " - - - - - 1 1 1 1 - - - - - - \n" +
                            "- o o o o o o o o o 2 2 2 2 - \n" +
                            " - o o o o o o o o o 2 2 2 - - \n" +
                            "- - o o o o o o o o o 2 2 - - \n" +
                            " - - o o o o o o o o o 2 - - - \n" +
                            "- - - o o o o o o o o o - - - \n" +
                            " - - o o o o o o o o o 3 - - - \n" +
                            "- - o o o o o o o o o 3 3 - - \n" +
                            " - o o o o o o o o o 3 3 3 - - \n" +
                            "- o o o o o o o o o 3 3 3 3 - \n" +
                            " - - - - - o o o o - - - - - - \n" +
                            "- - - - - - o o o - - - - - - \n" +
                            " - - - - - - o o - - - - - - - \n" +
                            "- - - - - - - o - - - - - - - \n";
            String roomData = "221;Go on!;3;Player #42;Player #15;Player #11;green;blue;red;Basic;3;" + in;
            GUI.getInstance().launchGameRoomScene(roomData);
        } catch (ChineseCheckersWindowException e) {
            e.showWindow();
        }
    }
    //gameOptions format: "numberOfModes;numberOfPlayers;numberOfAI;[modes];[players];[ai]"
    public void updateList(String gameOptions) throws ChineseCheckersException {
        if (gameOptions != null && !gameOptions.isEmpty()) {
            String [] options = gameOptions.split(";");
            int [] values = new int[3];
            for (int i=0; i < 3; i++ ) {
                values[i] = Integer.parseInt(options[i]);
            }
            String [][] info = new String[3][];
            int index = 3;
            for (int k = 0; k < 3; k++) {
                info[k] = new String [values[k]];
                for (int i = 0; i < values[k]; index++, i++) {
                    info[k][i] = options[index];
                }
            }
            setListsData(info[0], info[1], info[2]);
        } else {
            throw new ChineseCheckersException("Empty list of options");
        }
    }

    private void setListsData(String [] modes, String [] players, String [] ai){
        setListData(modes, gameMode, gameModeList, "mode_item");
        setListData(players, numberOfPlayers, numberOfPlayersList, "player_item");
        setListData(ai, numberOfAIPlayers, numberOfAIPlayersList, "player_item");
    }

    private void setListData(String [] data, HBox node, ToggleGroup list, String style) {
        list.getToggles().removeAll(list.getToggles());
        node.getChildren().removeAll(node.getChildren());
        ToggleButton mode;
        for (String datum : data) {
            mode = new ToggleButton(datum);
            list.getToggles().add(mode);
            node.getChildren().add(mode);
            mode.getStyleClass().add(style);
        }

    }

    // return pattern: "mode;players;ai"
    private String getNewRoomData(){
        return (((ToggleButton) gameModeList.getSelectedToggle()).getText() + ";") + (((ToggleButton) numberOfPlayersList.getSelectedToggle()).getText() + ";") + (((ToggleButton) numberOfAIPlayersList.getSelectedToggle()).getText());
    }
}
