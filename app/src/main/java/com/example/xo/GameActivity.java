package com.example.xo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.xo.database.TournamentDatabase;
import com.example.xo.models.Tournament;
import com.example.xo.utils.FileManager;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton[][] buttons = new ImageButton[3][3];
    private boolean player1Turn = true;
    private int roundCount;
    private int gameCount;
    private int totalGames;
    private int winsNeeded;

    private int player1Points;
    private int player2Points;
    private int drawCount;

    private TextView tvScore, tvGameNumber, tvStatus;

    private String startingPlayer;
    private String playerSymbol;
    private FileManager fileManager;
    private TournamentDatabase database;

    private enum RoundResult { PLAYER1, PLAYER2, DRAW }
    private RoundResult lastRoundWinner;
    private ImageButton[] winningLine = new ImageButton[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        fileManager = new FileManager(this);
        database = new TournamentDatabase(this);

        // Initialize top-bar controls
        ImageButton btnClose = findViewById(R.id.btn_close);
        ImageButton btnReset = findViewById(R.id.btn_reset);
        ImageButton btnPause = findViewById(R.id.btn_pause);

        // Initialize TextViews
        tvScore = findViewById(R.id.tv_score);
        tvGameNumber = findViewById(R.id.tv_game_number);
        tvStatus = findViewById(R.id.tv_status);

        btnClose.setOnClickListener(v -> finish());
        btnReset.setOnClickListener(v -> resetGame());
        btnPause.setOnClickListener(v -> {
            // TODO: Implement pause functionality
        });

        // Initialize game board
        GridLayout gridLayout = findViewById(R.id.grid_layout);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "btn_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        // Get data from MainActivity
        Intent intent = getIntent();
        totalGames = intent.getIntExtra("GAME_COUNT", 5);
        startingPlayer = intent.getStringExtra("STARTING_PLAYER");
        playerSymbol = intent.getStringExtra("PLAYER_SYMBOL");
        winsNeeded = (totalGames / 2) + 1;

        // Set initial turn based on player's choice and coin flip
        if (playerSymbol.equals(startingPlayer)) {
            player1Turn = true;
        } else {
            player1Turn = false;
        }

        updateUI();
    }

    @Override
    public void onClick(View v) {
        if (((ImageButton) v).getDrawable() != null) {
            return; // Cell already taken
        }

        if (player1Turn) {
            ((ImageButton) v).setImageResource(R.drawable.ic_modern_x);
            v.setTag("X");
        } else {
            ((ImageButton) v).setImageResource(R.drawable.ic_modern_o);
            v.setTag("O");
        }

        roundCount++;

        if (checkForWin()) {
            highlightWinningLine();
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
            updateStatus();
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getTag() != null ? buttons[i][j].getTag().toString() : "";
            }
        }

        // Check rows
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].isEmpty()) {
                winningLine = new ImageButton[]{buttons[i][0], buttons[i][1], buttons[i][2]};
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].isEmpty()) {
                winningLine = new ImageButton[]{buttons[0][i], buttons[1][i], buttons[2][i]};
                return true;
            }
        }

        // Check diagonals
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].isEmpty()) {
            winningLine = new ImageButton[]{buttons[0][0], buttons[1][1], buttons[2][2]};
            return true;
        }
        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].isEmpty()) {
            winningLine = new ImageButton[]{buttons[0][2], buttons[1][1], buttons[2][0]};
            return true;
        }

        return false;
    }

    private void highlightWinningLine() {
        for (ImageButton button : winningLine) {
            button.setBackgroundResource(R.drawable.cell_background_win);
        }
    }

    private void player1Wins() {
        player1Points++;
        lastRoundWinner = RoundResult.PLAYER1;
        Toast.makeText(this, "Player X wins!", Toast.LENGTH_SHORT).show();
        endRound();
    }

    private void player2Wins() {
        player2Points++;
        lastRoundWinner = RoundResult.PLAYER2;
        Toast.makeText(this, "Player O wins!", Toast.LENGTH_SHORT).show();
        endRound();
    }

    private void draw() {
        drawCount++;
        lastRoundWinner = RoundResult.DRAW;
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        endRound();
    }

    private void endRound() {
        gameCount++;
        disableBoard();
        updateUI();

        new Handler().postDelayed(() -> {
            if (player1Points == winsNeeded || player2Points == winsNeeded || gameCount == totalGames) {
                showTournamentResult();
            } else {
                resetBoard();
            }
        }, 1500); // Delay for showing the result before the next round or summary
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setImageResource(0);
                buttons[i][j].setTag(null);
                buttons[i][j].setEnabled(true);
                buttons[i][j].setBackgroundResource(R.drawable.modern_cell_background);
            }
        }
        roundCount = 0;

        if (lastRoundWinner == RoundResult.PLAYER1) {
            player1Turn = true;
        } else if (lastRoundWinner == RoundResult.PLAYER2) {
            player1Turn = false;
        } else { // On a draw, alternate the starting player
            player1Turn = !player1Turn;
        }
        updateUI();
    }

    private void disableBoard() {
        for (ImageButton[] row : buttons) {
            for (ImageButton button : row) {
                button.setEnabled(false);
            }
        }
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        drawCount = 0;
        gameCount = 0;
        resetBoard();
    }

    private void updateUI() {
        updateScoreText();
        updateGameNumber();
        updateStatus();
    }

    private void updateScoreText() {
        tvScore.setText(String.format("%d – %d", player1Points, player2Points));
    }

    private void updateGameNumber() {
        tvGameNumber.setText(String.format("Game %d of %d", gameCount + 1, totalGames));
    }

    private void updateStatus() {
        tvStatus.setText(player1Turn ? "Player X's Turn" : "Player O's Turn");
    }

    private void showTournamentResult() {
        String winner;
        if (player1Points > player2Points) {
            winner = "Player X";
        } else if (player2Points > player1Points) {
            winner = "Player O";
        } else {
            winner = "Draw";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_tournament_summary, null);
        builder.setView(dialogView);
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();

        TextView tvWinner = dialogView.findViewById(R.id.tv_summary_winner);
        TextView tvScoreX = dialogView.findViewById(R.id.tv_summary_score_x);
        TextView tvScoreO = dialogView.findViewById(R.id.tv_summary_score_o);
        TextView tvDraws = dialogView.findViewById(R.id.tv_summary_draws);
        Button btnSave = dialogView.findViewById(R.id.btn_save_tournament);
        Button btnHome = dialogView.findViewById(R.id.btn_back_home);

        tvWinner.setText(getString(R.string.winner, winner));
        tvScoreX.setText(getString(R.string.score_x, player1Points));
        tvScoreO.setText(getString(R.string.score_o, player2Points));
        tvDraws.setText(getString(R.string.draws, drawCount));

        btnSave.setOnClickListener(v -> {
            Tournament tournament = new Tournament(player1Points, player2Points, drawCount, totalGames, winner);
            fileManager.saveLastTournament(tournament);
            database.insertTournament(tournament);
            Toast.makeText(this, R.string.tournament_saved, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            finish();
        });

        btnHome.setOnClickListener(v -> {
            dialog.dismiss();
            finish();
        });

        dialog.show();
    }
}
