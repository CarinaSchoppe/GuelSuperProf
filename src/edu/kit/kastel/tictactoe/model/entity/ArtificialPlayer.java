/*
 * Copyright (c) 2023, KASTEL. All rights reserved.
 */

package edu.kit.kastel.tictactoe.model.entity;

import edu.kit.kastel.tictactoe.model.Board;
import edu.kit.kastel.tictactoe.model.Entry;

/**
 * Represents an artificial Tic-Tac-Toe player.
 *
 * @author Moritz Hertler
 * @author Tobias Thirolf
 * @version 1.0
 */
public class ArtificialPlayer extends Player {

    /**
     * The order in which the artificial player will choose the cells
     * if there is no better option, i.e. winning or preventing loosing.
     */
    private static final int[] INDEX_PRIORITIES = new int[]{4, 0, 2, 6, 8, 1, 3, 5, 7};
    private static final String ERROR_BOARD_IS_FULL = "The board has no empty cells.";

    private final Player opponent;
    private final Board board;

    /**
     * Creates a new instance of an artificial player.
     *
     * @param token    the token that the AI will use
     * @param opponent the other player that the AI will try to prevent from winning if possible
     * @param board    the board on which the game is played
     */
    public ArtificialPlayer(Entry token, Player opponent, Board board) {
        super(token);
        this.opponent = opponent;
        this.board = board;
    }

    /**
     * Determines the next move of the given player.
     *
     * @return the index of the cell
     * @throws IllegalStateException if the board is full
     */
    public int nextMove() {
        int winningIndex = this.board.getWinningIndex(getToken());
        if (winningIndex != -1) {
            return winningIndex;
        }

        int preventionIndex = this.board.getWinningIndex(opponent.getToken());
        System.out.println(preventionIndex);
        if (preventionIndex != -1) {
            return preventionIndex;
        }

        // Rule 3: If the middle field is free, return its index
        int middleIndex = Board.size * Board.size / 2;
        if (board.get(middleIndex).equals(Entry.EMPTY)) {
            return middleIndex;
        }

        // Rule 4: If any corner is free, return its index.
        int[] corners = {0, Board.size - 1, Board.size * (Board.size - 1), Board.size * Board.size - 1};
        for (int corner : corners) {
            if (board.get(corner).equals(Entry.EMPTY)) {
                return corner;
            }
        }

        // Rule 5: Return the index of the first empty field
        for (int i = 0; i < Board.size * Board.size; i++) {
            if (board.get(i).equals(Entry.EMPTY)) {
                return i;
            }
        }

        throw new IllegalStateException(ERROR_BOARD_IS_FULL);
    }
}
