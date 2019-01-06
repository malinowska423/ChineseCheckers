package com.client;

import org.junit.jupiter.api.Test;
import com.client.*;

import static org.junit.jupiter.api.Assertions.*;


class BoardBuilderTest {
    BoardBuilder builder;

    BoardBuilderTest() throws ChineseCheckersWindowException {
        builder = BoardBuilder.runBuilder("Basic");
    }


    @Test
    void runBasicBuilder() throws ChineseCheckersWindowException {
        builder = BoardBuilder.runBuilder("Basic");
        assertTrue(builder instanceof BasicBoardBuilder);
    }

    @Test
    void runExceptionBuilder() {
        assertThrows(ChineseCheckersWindowException.class, () -> builder = BoardBuilder.runBuilder("test"));
    }

    @Test
     void getPossibleMoves() {
        try {
            builder.getPossibleMoves(1,2,3);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
        }
    }

}