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
    private static int size;
    private int tokens = 0;
    private static int winCrit = 3;
    private static int maxTokens = 9;
    private Queue<Integer> setTokens = new ArrayDeque<>();

    private final Entry[] entries;


    /**
     * Creates a new instance of a board. All entries are {@link Entry#EMPTY} by default
     */
    public Board(int size, int winCrit, int maxTokens) {
        Board.size = size > 9 || size < 3 ? 3 : size;
        entries = new Entry[Board.size * Board.size];
        Board.winCrit = winCrit > size || winCrit < 3 ? 3 : winCrit;
        Board.maxTokens = maxTokens > 81 || maxTokens < 9 ? 9 : maxTokens;
        Arrays.fill(entries, Entry.EMPTY);
    }

    private Board(Board board) {
        entries = new Entry[Board.size * Board.size];
        System.arraycopy(board.entries, 0, entries, 0, board.entries.length);
    }

    public Board clone() {
        var board = new Board(size, winCrit, maxTokens);
        System.arraycopy(entries, 0, board.entries, 0, entries.length);
        board.tokens = tokens;
        setTokens = new ArrayDeque<>(setTokens);
        return board;
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
        setTokens.add(pos);
        if (tokens > maxTokens) {
            tokens--;
            entries[setTokens.poll()] = Entry.EMPTY;
        }
    }

    public void setExtra(int pos, Entry entry) {
     /*   for (var i = 0; i< entries.length; i++) {
        if (entries[i] != Entry.EMPTY) {
            tokens++;
        }
        }*/
        entries[pos] = entry;
        tokens++;
        setTokens.add(pos);
        if (tokens > maxTokens) {
            tokens--;
            var value = setTokens.poll();
            entries[value] = Entry.TMP;
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
        Entry checkDiagonal = checkDiagonal();
        if (checkDiagonal != null) return checkDiagonal;


        //check for a straight line horizontally or vertically

        //convert the 1d array with (size*size) to a 2d array
        Entry[][] twoDArray = new Entry[size][size];
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                twoDArray[i][j] = entries[count];
                count++;
            }
        }

        //check for a straight line horizontally in the amount of winCrit
        for (int i = 0; i < size; i++) {
            int countX = 0;
            int countO = 0;
            for (int j = 0; j < size; j++) {
                if (twoDArray[i][j] == Entry.X) {
                    countX++;
                    countO = 0;
                } else if (twoDArray[i][j] == Entry.O) {
                    countO++;
                    countX = 0;
                } else {
                    countX = 0;
                    countO = 0;
                }
                if (countX == winCrit) {
                    return Entry.X;
                } else if (countO == winCrit) {
                    return Entry.O;
                }
            }

        }

        //check for a straight line vertically in the amount of winCrit
        for (int i = 0; i < size; i++) {
            int countX = 0;
            int countO = 0;
            for (int j = 0; j < size; j++) {
                if (twoDArray[j][i] == Entry.X) {
                    countX++;
                    countO = 0;
                } else if (twoDArray[j][i] == Entry.O) {
                    countO++;
                    countX = 0;
                } else {
                    countX = 0;
                    countO = 0;
                }
                if (countX == winCrit) {
                    return Entry.X;
                } else if (countO == winCrit) {
                    return Entry.O;
                }
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
        for (int i = 0; i < Board.size * Board.size; i++) {
            if (!this.entries[i].equals(Entry.EMPTY)) {
                continue;
            }

            var boardCone = clone();
            boardCone.setExtra(i, token);
            boolean hasWon = boardCone.hasWon(token);

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

    public static int getSize() {
        return size;
    }
}
