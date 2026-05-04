package com.mycompany.app;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private final Game game = new Game();

    @Test
    void testCheckStateEmptyBoard() {
        char[] board = game.createEmptyBoard();
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    void testCheckStateXWins() {
        char[] board = new char[]{'X','X','X','O','O',' ',' ',' ',' '};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateOWins() {
        char[] board = new char[]{'O',' ',' ','X','O','X',' ',' ','O'};
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testCheckStateDraw() {
        char[] board = new char[]{'X','O','X','X','O','O','O','X','X'};
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    void testGenerateMovesOnEmptyBoard() {
        char[] board = game.createEmptyBoard();
        List<Integer> moves = game.generateMoves(board);
        assertEquals(9, moves.size());
        assertTrue(moves.contains(0));
        assertTrue(moves.contains(8));
    }

    @Test
    void testEvaluatePositionXWin() {
        char[] board = new char[]{'X','X','X',' ',' ',' ',' ',' ',' '};
        assertEquals(Game.INF, game.evaluatePosition(board, 'X'));
        assertEquals(-Game.INF, game.evaluatePosition(board, 'O'));
    }

    @Test
    void testEvaluatePositionDraw() {
        char[] board = new char[]{'X','O','X','X','O','O','O','X','X'};
        assertEquals(0, game.evaluatePosition(board, 'X'));
        assertEquals(0, game.evaluatePosition(board, 'O'));
    }

    @Test
    void testFindBestMoveBlocksOpponentWin() {
        char[] board = new char[]{'X','X',' ','O','O',' ',' ',' ',' '};
        int bestMove = game.findBestMove(board, 'O');
        assertEquals(2, bestMove);
    }

    @Test
    void testFindBestMoveWinsWhenAvailable() {
        char[] board = new char[]{'O','O',' ','X','X',' ',' ',' ',' '};
        int bestMove = game.findBestMove(board, 'O');
        assertEquals(2, bestMove);
    }

    @Test
    void testFindBestMoveOnFullBoardReturnsMinusOne() {
        char[] board = new char[]{'X','O','X','X','O','O','O','X','X'};
        assertEquals(-1, game.findBestMove(board, 'X'));
    }
}
