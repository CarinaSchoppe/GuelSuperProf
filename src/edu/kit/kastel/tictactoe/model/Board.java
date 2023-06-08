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
    public static int size = 4;
    public static int tokens = 0;
    private static int winCrit = 3;
    private static int maxTokens = 9;
    private static final Queue<Integer> SET_TOKENS = new ArrayDeque<>();

    private final Entry[] entries;

    /**
     * Creates a new instance of a board. All entries are {@link Entry#EMPTY} by default
     */
    public Board(int size, int winCrit, int maxTokens) {
        Board.size = size > 9 || size < 3 ? 3 : size;
        entries = new Entry[Board.size * Board.size];
        Board.winCrit = winCrit > size ? size : winCrit < 3 ? 3 : winCrit;
        Board.maxTokens = maxTokens > 81 || maxTokens < 9 ? 81 : maxTokens;
        Arrays.fill(entries, Entry.EMPTY);
    }

    private Board(Board board) {
        entries = new Entry[Board.size * Board.size];
        System.arraycopy(board.entries, 0, entries, 0, board.entries.length);
    }

    /**
     * Changes the entry on the board on a specific position
     *
     * @param pos   the position on the board
     * @param entry the entry that will be set on the position
     */
    public void set(int pos, Entry entry) {
        entries[pos] = entry;
        tokens++;
        SET_TOKENS.add(pos);
        if (tokens > maxTokens) {
            tokens--;
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
        Entry diagonalFromTopLeft = checkDiagonal();
        if (diagonalFromTopLeft != null) return diagonalFromTopLeft;

        Entry diagonalFromTopRight = checkDiagonal();
        if (diagonalFromTopRight != null) return diagonalFromTopRight;

        for (int i = 0; i < size; i++) {
            Entry horizontal = checkLine(i * size, 1);
            if (horizontal != null) return horizontal;

            Entry vertical = checkLine(i, size);
            if (vertical != null) return vertical;
        }

        return null;
    }

    private Entry checkLine(int startPos, int step) {
        Entry initial = entries[startPos];
        if (initial.equals(Entry.EMPTY)) {
            return null;
        }

        int count = 0;
        for (int i = startPos; i < startPos + step * size; i += step) {
            if (entries[i].equals(initial)) {
                count++;
                if (count == winCrit) {
                    return initial;
                }
            } else {
                count = 0;
            }
        }

        return null;
    }

    private Entry checkDiagonal() {
        // Überprüfen der Hauptdiagonalen (von links oben nach rechts unten)
        for (int i = 0; i <= size - winCrit; i++) {
            Entry initial = entries[i * size + i];
            if (initial != Entry.EMPTY) {
                int count = 1;
                for (int j = 1; j < winCrit; j++) {
                    if (entries[(i + j) * size + (i + j)] == initial) {
                        count++;
                    } else {
                        break;
                    }
                }
                if (count == winCrit) {
                    return initial;
                }
            }
        }

        // Überprüfen der Nebendiagonalen (von rechts oben nach links unten)
        for (int i = 0; i <= size - winCrit; i++) {
            Entry initial = entries[i * size + (size - i - 1)];
            if (initial != Entry.EMPTY) {
                int count = 1;
                for (int j = 1; j < winCrit; j++) {
                    if (entries[(i + j) * size + (size - (i + j) - 1)] == initial) {
                        count++;
                    } else {
                        break;
                    }
                }
                if (count == winCrit) {
                    return initial;
                }
            }
        }

        return null;
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
        for (int row = 0; row < size; row++) {
            StringBuilder builder = new StringBuilder();
            for (int column = 0; column < size; column++) {
                builder.append(entries[row * size + column].getToken());
            }
            joiner.add(builder.toString());
        }
        return joiner.toString();
    }
}
