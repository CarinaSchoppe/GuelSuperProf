/*
 * Copyright (c) 2023, KASTEL. All rights reserved.
 */

package edu.kit.kastel.tictactoe.model;

import edu.kit.kastel.tictactoe.model.entity.ArtificialPlayer;
import edu.kit.kastel.tictactoe.model.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class representing a Tic Tac Toe game
 *
 * @author Tobias Thirolf
 */
public class TicTacToe {

    private final Board board;
    private final List<Player> players = new ArrayList<>();
    private int playerPointer;

    /**
     * Creates a new Tic Tac Toe instance
     */
    public TicTacToe(int size, int winCrit, int maxTokens) {
        board = new Board(size, winCrit, maxTokens);
        players.add(new Player(Entry.X));
        players.add(new Player(Entry.O));
    }

    /**
     * Replaces the second player with an AI
     */
    public void useAI() {
        players.set(1, new ArtificialPlayer(Entry.O, players.get(0), board));
    }

    /**
     * Gets a copy of the board
     *
     * @return A copy of the current board
     */
    public Board getBoard() {
        return board.copy();
    }


    /**
     * Returns the player whose turn it is at the moment
     *
     * @return The player whose turn it is at the moment
     */
    public Player getCurrentPlayer() {
        return players.get(playerPointer);
    }

    private void nextPlayer() {

        playerPointer = playerPointer == players.size() - 1 ? 0 : ++playerPointer;
    }

    /**
     * Sets a token of the player whose current turn is on the specified position
     *
     * @param position The position at which the token should be placed
     */
    public void set(int position) {
        board.set(position, players.get(playerPointer).getToken());
        nextPlayer();

    }

    /**
     * Returns whether the given position is unoccupied
     *
     * @param position the position to check
     * @return whether the given position is unoccupied
     */
    public boolean isEmpty(int position) {
        return board.get(position).equals(Entry.EMPTY);
    }

    /**
     * Returns whether there are unoccupied places on the board
     *
     * @return whether there are unoccupied places on the board
     */
    public boolean hasEmpty() {
        return board.hasEmpty();
    }

    /**
     * Returns the player who won the game if exists
     *
     * @return the player who won the game, {@code null} otherwise
     */
    public Player evaluateWinner() {
        Entry winnerToken = board.getSameInLine();

        for (Player player : players) {
            if (player.getToken().equals(winnerToken)) {
                return player;
            }
        }
        return null;
    }
}
