/*
 * Copyright (c) 2023, KASTEL. All rights reserved.
 */

package edu.kit.kastel.tictactoe.model.entity;

import edu.kit.kastel.tictactoe.model.Entry;

/**
 * Represents a player in the game
 *
 * @author Tobias Thirolf
 */
public class Player {

    private final Entry token;

    /**
     * Creates a new player.
     *
     * @param token the token that player uses on the board
     */
    public Player(Entry token) {
        this.token = token;
    }

    /**
     * Returns the token of the player
     *
     * @return the token of the player
     */
    public Entry getToken() {
        return token;
    }

    @Override
    public String toString() {
        return String.valueOf(token.getToken());
    }
}
