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
        if (preventionIndex != -1) {
            return preventionIndex;
        }

        for (int currentIndex : INDEX_PRIORITIES) {
            if (board.get(currentIndex).equals(Entry.EMPTY)) {
                return currentIndex;
            }
        }

        throw new IllegalStateException(ERROR_BOARD_IS_FULL);
    }
}
