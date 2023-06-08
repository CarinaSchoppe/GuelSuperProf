/*
 * Copyright (c) 2023, KASTEL. All rights reserved.
 */

package edu.kit.kastel.tictactoe.ui;

import edu.kit.kastel.tictactoe.model.Board;
import edu.kit.kastel.tictactoe.model.TicTacToe;
import edu.kit.kastel.tictactoe.model.entity.ArtificialPlayer;
import edu.kit.kastel.tictactoe.model.entity.Player;

import java.util.Scanner;

/**
 * This class handles every user interaction
 *
 * @author Tobias Thirolf
 */
public class InputHandler {

    private static final String INPUT_REQUEST_FORMAT = "%d. Zug: %s";
    private static final int MIN_POSITION = 0;
    private static final String TEXT_WINNER_FORMAT = "Sieger: %s";
    private static final String TEXT_NO_WINNER = "Kein Sieger";
    private static final int ODD_NUMBERS_MODULO = 2;
    private static int maxPosition;
    private final TicTacToe ticTacToe;
    private final boolean useAI;
    private final Scanner scanner;

    /**
     * Creates a new instance
     *
     * @param ticTacToe the {@link TicTacToe} game this instance shall be connected with
     * @param useAI     whether to use an AI as second player or not
     * @param scanner   the input source that will be used for player input
     */
    public InputHandler(TicTacToe ticTacToe, boolean useAI, Scanner scanner) {
        this.ticTacToe = ticTacToe;
        maxPosition = Board.size * Board.size - 1;
        this.useAI = useAI;
        this.scanner = scanner;
    }

    /**
     * Starts user interaction on console
     */
    public void interact() {
        System.out.println(ticTacToe.getBoard());
        boolean running = true;
        int move = 1;

        while (running) {

            int input = getInput(move);
            ticTacToe.set(input);
            System.out.println(ticTacToe.getBoard());

            Player winner = ticTacToe.evaluateWinner();

            if (winner != null) {
                System.out.printf(TEXT_WINNER_FORMAT + System.lineSeparator(), winner);
                running = false;
            } else if (!ticTacToe.hasEmpty()) {
                System.out.println(TEXT_NO_WINNER);
                running = false;
            }

            move++;
        }
    }

    private int getInput(int move) {
        int input = 0;
        boolean pendingInput = true;
        while (pendingInput) {
            System.out.printf((INPUT_REQUEST_FORMAT) + System.lineSeparator(), move, ticTacToe.getCurrentPlayer());

            if (useAI && move % ODD_NUMBERS_MODULO == 0) {
                input = ((ArtificialPlayer) ticTacToe.getCurrentPlayer()).nextMove();
            } else {
                String line = scanner.nextLine();
                try {
                    input = Integer.parseInt(line);
                } catch (NumberFormatException ignored) {
                    continue; //deliberately ignoring exception itself as it contains no further usable information here
                }
            }
            if (input >= MIN_POSITION && input <= maxPosition && ticTacToe.isEmpty(input)) {
                pendingInput = false;
            }
        }
        return input;
    }
}
