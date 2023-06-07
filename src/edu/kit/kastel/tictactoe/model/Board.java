/*
 * Copyright (c) 2023, KASTEL. All rights reserved.
 */

package edu.kit.kastel.tictactoe.model;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringJoiner;

/**
 * This class represents the board on which the tokens are placed. The board is indexed from 0 to 8, where 0
 * represents the upper left corner and 8 the lower right one.
 *
 * @author Tobias Thirolf
 */
public class Board {

    private static final int DIVISOR = 2;
    public static int SIZE = 4;
    public static int TOKENS = 0;
    private static int WIN_CRIT = 3;
    private static int MAX_TOKENS = 9;
    private static final Queue<Integer> SET_TOKENS = new ArrayDeque<>();

    private final Entry[] entries = new Entry[SIZE * SIZE];

    /**
     * Creates a new instance of a board. All entries are {@link Entry#EMPTY} by default
     */
    public Board(int size, int winCrit, int maxTokens) {
        Board.SIZE = size > 9 || size < 3 ? 3 : size;
        Board.WIN_CRIT = winCrit > size ? size : winCrit < 3 ? 3 : winCrit;
        Board.MAX_TOKENS = maxTokens > 81 || maxTokens < 9 ? 81 : maxTokens;
        Arrays.fill(entries, Entry.EMPTY);
    }

    private Board(Board board) {
        System.arraycopy(board.entries, 0, entries, 0, SIZE * SIZE);
    }

    /**
     * Changes the entry on the board on a specific position
     *
     * @param pos   the position on the board
     * @param entry the entry that will be set on the position
     */
    public void set(int pos, Entry entry) {
        entries[pos] = entry;
        TOKENS++;
        SET_TOKENS.add(pos);
        if (TOKENS > MAX_TOKENS) {
            TOKENS--;
            entries[SET_TOKENS.poll()] = Entry.EMPTY;
        }
    }

    /**
     * Gets the current entry on a specific position
     *
     * @param pos the position on the board
     * @return the entry on the given position
     */
    public Entry get(int pos) {
        return entries[pos];
    }

    /**
     * Creates a copy of the current board
     *
     * @return a copy of the current board
     */
    public Board copy() {
        return new Board(this);
    }

    /**
     * Returns whether {@link Entry#EMPTY} are currently present on the board
     *
     * @return whether the board currently contains {@link Entry#EMPTY}
     */
    public boolean hasEmpty() {
        return Arrays.asList(entries).contains(Entry.EMPTY);
    }

    /**
     * Returns the entry if it covers a whole line either diagonally, vertically, or horizontally. Never returns
     * {@link Entry#EMPTY}. Note that if multiple {@link Entry} cover whole lines, only the first one found will be
     * returned.
     *
     * @return the entry if it covers a whole line on the board, {@code null} otherwise
     */
    public Entry getSameInLine() {
        Entry diagonalFromTopLeft = checkDiagonal(0, SIZE + 1);
        if (diagonalFromTopLeft != null) return diagonalFromTopLeft;

        Entry diagonalFromTopRight = checkDiagonal(SIZE - 1, SIZE - 1);
        if (diagonalFromTopRight != null) return diagonalFromTopRight;

        for (int i = 0; i < SIZE; i++) {
            Entry horizontal = checkLine(i * SIZE, 1);
            if (horizontal != null) return horizontal;

            Entry vertical = checkLine(i, SIZE);
            if (vertical != null) return vertical;
        }

        return null;
    }

    private Entry checkLine(int startPos, int step) {
        Entry initial = entries[startPos];
        if (initial.equals(Entry.EMPTY)) {
            return null;
        }

        for (int i = startPos; i < startPos + step * SIZE; i += step) {
            if (!entries[i].equals(initial)) {
                return null;
            }
        }

        return initial;
    }

    private Entry checkDiagonal(int startPos, int step) {
        Entry initial = entries[startPos];
        if (initial.equals(Entry.EMPTY)) {
            return null;
        }

        for (int i = startPos; i < SIZE * SIZE; i += step) {
            if (!entries[i].equals(initial)) {
                return null;
            }
        }

        return initial;
    }


    /**
     * Calculates the index of the cell the given token has to place to win the game in
     * the next move. If more than one cell meets the requirement, the one with the lowest
     * index will be returned. If no cell meets the requirement, {@code -1} is returned.
     *
     * @param token the token
     * @return the index of the cell {@code token} has to play to win the game or {@code -1}
     */
    public int getWinningIndex(Entry token) {
        for (int i = 0; i < this.entries.length; i++) {
            if (!this.entries[i].equals(Entry.EMPTY)) {
                continue;
            }

            this.entries[i] = token;
            boolean hasWon = hasWon(token);
            this.entries[i] = Entry.EMPTY;

            if (hasWon) {
                return i;
            }
        }

        return -1;
    }

    private boolean hasWon(Entry token) {
        return token.equals(getSameInLine());
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(System.lineSeparator());
        for (int row = 0; row < SIZE; row++) {
            StringBuilder builder = new StringBuilder();
            for (int column = 0; column < SIZE; column++) {
                builder.append(entries[row * SIZE + column].getToken());
            }
            joiner.add(builder.toString());
        }
        return joiner.toString();
    }
}
