package com.client;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicBoardBuilderTest extends BoardBuilderTest {

    BasicBoardBuilderTest() throws ChineseCheckersWindowException {
        super();
    }

    @Test
    void buildBoard() {
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

        BasicBoardBuilder builder = new BasicBoardBuilder();
        Pane pane = new Pane();
        pane.setPrefSize(1600, 900);
//        String[] colors = new String[6];
        String[] colors = {"blue","yellow", "black", "green", "purple", "pink"};
        builder.buildBoard(1,in,pane,colors,new Room());

        assertThrows(IllegalStateException.class,() -> builder.updateBoard(4,7,5,7));
        builder.reset();
        for (Hexagon[] hexs: builder.getHexagons()) {
            for (Hexagon hex : hexs) {
                if(hex != null) {
                    assertTrue(hex.isDisable());
                }
            }
        }
        builder.activatePawns();

    }

    @Test
    void updateBoard() {
    }

    @Test
    void reset() {
    }

    @Test
    void activatePawns() {
    }

    @Test
    void disablePawns() {
    }
}