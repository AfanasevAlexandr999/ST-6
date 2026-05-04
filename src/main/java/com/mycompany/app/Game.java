package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    public static final int INF = 100;
    private static final int[][] LINES = {
        {0, 1, 2},
        {3, 4, 5},
        {6, 7, 8},
        {0, 3, 6},
        {1, 4, 7},
        {2, 5, 8},
        {0, 4, 8},
        {2, 4, 6}
    };

    public char[] createEmptyBoard() {
        return new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
    }

    public State checkState(char[] board) {
        if (board == null || board.length != 9) {
            throw new IllegalArgumentException("board must have length 9");
        }

        if (isWinning(board, 'X')) {
            return State.XWIN;
        }
        if (isWinning(board, 'O')) {
            return State.OWIN;
        }

        for (char square : board) {
            if (square == ' ') {
                return State.PLAYING;
            }
        }
        return State.DRAW;
    }

    public List<Integer> generateMoves(char[] board) {
        List<Integer> moves = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                moves.add(i);
            }
        }
        return moves;
    }

    public int evaluatePosition(char[] board, char playerSymbol) {
        State state = checkState(board);
        if (state == State.DRAW) {
            return 0;
        }
        if (state == State.XWIN) {
            return playerSymbol == 'X' ? INF : -INF;
        }
        if (state == State.OWIN) {
            return playerSymbol == 'O' ? INF : -INF;
        }
        return -1;
    }

    public int findBestMove(char[] board, char playerSymbol) {
        if (board == null || board.length != 9) {
            throw new IllegalArgumentException("board must have length 9");
        }
        if (playerSymbol != 'X' && playerSymbol != 'O') {
            throw new IllegalArgumentException("playerSymbol must be X or O");
        }

        List<Integer> moves = generateMoves(board);
        if (moves.isEmpty()) {
            return -1;
        }

        int bestValue = -INF;
        int bestMove = -1;
        for (int move : moves) {
            board[move] = playerSymbol;
            int value = minMove(board, playerSymbol);
            board[move] = ' ';

            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private int minMove(char[] board, char playerSymbol) {
        int evaluation = evaluatePosition(board, playerSymbol);
        if (evaluation != -1) {
            return evaluation;
        }
        int bestValue = INF;
        for (int move : generateMoves(board)) {
            board[move] = opponent(playerSymbol);
            bestValue = Math.min(bestValue, maxMove(board, playerSymbol));
            board[move] = ' ';
        }
        return bestValue;
    }

    private int maxMove(char[] board, char playerSymbol) {
        int evaluation = evaluatePosition(board, playerSymbol);
        if (evaluation != -1) {
            return evaluation;
        }
        int bestValue = -INF;
        for (int move : generateMoves(board)) {
            board[move] = playerSymbol;
            bestValue = Math.max(bestValue, minMove(board, playerSymbol));
            board[move] = ' ';
        }
        return bestValue;
    }

    private char opponent(char symbol) {
        return symbol == 'X' ? 'O' : 'X';
    }

    private boolean isWinning(char[] board, char symbol) {
        for (int[] line : LINES) {
            if (board[line[0]] == symbol && board[line[1]] == symbol && board[line[2]] == symbol) {
                return true;
            }
        }
        return false;
    }
}
