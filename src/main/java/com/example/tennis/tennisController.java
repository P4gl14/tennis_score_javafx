package com.example.tennis;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Stack;

public class tennisController {

    @FXML
    private Button backClick;

    @FXML
    private Button resetClick;

    @FXML
    private Button player1;

    @FXML
    private Button player2;

    @FXML
    private Label player1Sets;

    @FXML
    private Label player1Games;

    @FXML
    private Label player1Points;

    @FXML
    private Label player2Sets;

    @FXML
    private Label player2Games;

    @FXML
    private Label player2Points;

    private int player1PointsCount = 0;
    private int player2PointsCount = 0;
    private int player1GamesCount = 0;
    private int player2GamesCount = 0;
    private int player1SetsCount = 0;
    private int player2SetsCount = 0;

    private static final String[] TENNIS_POINTS = {"0", "15", "30", "40"};

    // Stack per tenere traccia degli stati precedenti
    private final Stack<GameState> history = new Stack<>();

    @FXML
    private void onPlayer1Click() {
        saveCurrentState();
        player1PointsCount++;
        if (isGameWon(player1PointsCount, player2PointsCount)) {
            player1PointsCount = 0;
            player2PointsCount = 0;
            player1GamesCount++;
            if (isSetWon(player1GamesCount, player2GamesCount)) {
                player1GamesCount = 0;
                player2GamesCount = 0;
                player1SetsCount++;
            }
        }
        updateScores();
    }

    @FXML
    private void onPlayer2Click() {
        saveCurrentState();
        player2PointsCount++;
        if (isGameWon(player2PointsCount, player1PointsCount)) {
            player2PointsCount = 0;
            player1PointsCount = 0;
            player2GamesCount++;
            if (isSetWon(player2GamesCount, player1GamesCount)) {
                player2GamesCount = 0;
                player1GamesCount = 0;
                player2SetsCount++;
            }
        }
        updateScores();
    }

    @FXML
    private void onResetClick() {
        saveCurrentState();
        player1PointsCount = 0;
        player2PointsCount = 0;
        player1GamesCount = 0;
        player2GamesCount = 0;
        player1SetsCount = 0;
        player2SetsCount = 0;
        updateScores();
    }

    @FXML
    private void onBackClick() {
        if (!history.isEmpty()) {
            GameState previousState = history.pop();
            restoreState(previousState);
        }
    }

    private void updateScores() {
        player1Points.setText(getTennisPoint(player1PointsCount, player2PointsCount));
        player2Points.setText(getTennisPoint(player2PointsCount, player1PointsCount));
        player1Games.setText(String.valueOf(player1GamesCount));
        player2Games.setText(String.valueOf(player2GamesCount));
        player1Sets.setText(String.valueOf(player1SetsCount));
        player2Sets.setText(String.valueOf(player2SetsCount));
    }

    private String getTennisPoint(int playerPoints, int opponentPoints) {
        if (playerPoints >= 4 && playerPoints - opponentPoints >= 1) {
            return "ADV"; // Advantage
        } else if (playerPoints >= 3 && playerPoints == opponentPoints) {
            return "DEUCE"; // Deuce
        } else {
            return playerPoints < TENNIS_POINTS.length ? TENNIS_POINTS[playerPoints] : "40";
        }
    }

    private boolean isGameWon(int playerPoints, int opponentPoints) {
        return playerPoints >= 4 && playerPoints - opponentPoints >= 2;
    }

    private boolean isSetWon(int playerGames, int opponentGames) {
        return playerGames >= 6 && playerGames - opponentGames >= 2;
    }

    private void saveCurrentState() {
        history.push(new GameState(
                player1PointsCount, player2PointsCount,
                player1GamesCount, player2GamesCount,
                player1SetsCount, player2SetsCount
        ));
    }

    private void restoreState(GameState state) {
        player1PointsCount = state.player1PointsCount;
        player2PointsCount = state.player2PointsCount;
        player1GamesCount = state.player1GamesCount;
        player2GamesCount = state.player2GamesCount;
        player1SetsCount = state.player1SetsCount;
        player2SetsCount = state.player2SetsCount;
        updateScores();
    }

    // Classe per salvare lo stato del gioco
    private static class GameState {
        private final int player1PointsCount;
        private final int player2PointsCount;
        private final int player1GamesCount;
        private final int player2GamesCount;
        private final int player1SetsCount;
        private final int player2SetsCount;

        public GameState(int player1PointsCount, int player2PointsCount,
                         int player1GamesCount, int player2GamesCount,
                         int player1SetsCount, int player2SetsCount) {
            this.player1PointsCount = player1PointsCount;
            this.player2PointsCount = player2PointsCount;
            this.player1GamesCount = player1GamesCount;
            this.player2GamesCount = player2GamesCount;
            this.player1SetsCount = player1SetsCount;
            this.player2SetsCount = player2SetsCount;
        }
    }
}
