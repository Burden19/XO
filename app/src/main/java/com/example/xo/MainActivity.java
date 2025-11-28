package com.example.xo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.xo.database.TournamentDatabase;
import com.example.xo.models.Tournament;
import com.example.xo.utils.FileManager;
import com.example.xo.utils.SoundManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final long COIN_FLIP_DURATION_PER_HALF = 120L;
    private static final int COIN_FLIP_COUNT = 10;

    private RadioGroup radioGroupSymbol;
    private Spinner spinnerGameCount;
    private Button btnPlay, btnPrinciple, btnStatistics, btnLoadLastTournament;
    private SoundManager soundManager;
    private TournamentDatabase database;
    private FileManager fileManager;
    private String startingPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        initializeManagers();
        setupListeners();
    }

    private void initializeViews() {
        radioGroupSymbol = findViewById(R.id.radio_group_symbol);
        spinnerGameCount = findViewById(R.id.spinner_game_count);
        btnPlay = findViewById(R.id.btn_play);
        btnPrinciple = findViewById(R.id.btn_principle);
        btnStatistics = findViewById(R.id.btn_statistics);
        btnLoadLastTournament = findViewById(R.id.btn_load_last_tournament);
    }

    private void initializeManagers() {
        soundManager = new SoundManager(this);
        database = new TournamentDatabase(this);
        fileManager = new FileManager(this);
    }

    private void setupListeners() {
        btnPlay.setOnClickListener(v -> showCoinFlipDialog());
        btnPrinciple.setOnClickListener(v -> showPrinciple());
        btnStatistics.setOnClickListener(v -> showStatistics());
        btnLoadLastTournament.setOnClickListener(v -> loadLastTournament());
    }

    private void showCoinFlipDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_coin_flip, null);
        builder.setView(dialogView);
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();

        ImageView ivCoin = dialogView.findViewById(R.id.iv_coin);
        TextView tvResult = dialogView.findViewById(R.id.tv_coin_flip_result);
        Button btnStartGame = dialogView.findViewById(R.id.btn_start_game);

        // Set camera distance for a proper 3D flip effect
        float scale = getResources().getDisplayMetrics().density;
        ivCoin.setCameraDistance(8000 * scale);

        soundManager.playCoinFlipSound();

        // --- Refactored Animation Logic ---
        AnimatorSet allFlips = new AnimatorSet();
        List<Animator> animatorList = new ArrayList<>();

        for (int i = 0; i < COIN_FLIP_COUNT; i++) {
            ObjectAnimator flipOut = ObjectAnimator.ofFloat(ivCoin, "rotationY", 0f, 90f);
            flipOut.setDuration(COIN_FLIP_DURATION_PER_HALF);

            final boolean isHeads = (i % 2 == 0);
            flipOut.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ivCoin.setImageResource(isHeads ? R.drawable.ic_coin_o : R.drawable.ic_coin_x);
                }
            });
            animatorList.add(flipOut);

            ObjectAnimator flipIn = ObjectAnimator.ofFloat(ivCoin, "rotationY", -90f, 0f);
            flipIn.setDuration(COIN_FLIP_DURATION_PER_HALF);
            animatorList.add(flipIn);
        }

        allFlips.playSequentially(animatorList);

        allFlips.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Random random = new Random();
                startingPlayer = random.nextBoolean() ? "X" : "O";
                if (startingPlayer.equals("X")) {
                    ivCoin.setImageResource(R.drawable.ic_coin_x);
                } else {
                    ivCoin.setImageResource(R.drawable.ic_coin_o);
                }
                tvResult.setText(getString(R.string.player_starts, startingPlayer));
                tvResult.setVisibility(View.VISIBLE);
                btnStartGame.setVisibility(View.VISIBLE);
            }
        });

        allFlips.start();

        btnStartGame.setOnClickListener(v -> {
            dialog.dismiss();
            startGame();
        });

        dialog.show();
    }


    private void startGame() {
        soundManager.playClickSound();

        int selectedSymbolId = radioGroupSymbol.getCheckedRadioButtonId();
        if (selectedSymbolId == -1) {
            Toast.makeText(this, R.string.select_symbol, Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadio = findViewById(selectedSymbolId);
        String playerSymbol = selectedRadio.getText().toString();
        String gameCountStr = spinnerGameCount.getSelectedItem().toString();
        int gameCount = Integer.parseInt(gameCountStr.split(" ")[0]);

        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("PLAYER_SYMBOL", playerSymbol);
        intent.putExtra("GAME_COUNT", gameCount);
        intent.putExtra("STARTING_PLAYER", startingPlayer);
        startActivity(intent);
    }

    private void showPrinciple() {
        soundManager.playClickSound();
        Intent intent = new Intent(MainActivity.this, PrincipleActivity.class);
        startActivity(intent);
    }

    private void showStatistics() {
        soundManager.playClickSound();
        Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
        startActivity(intent);
    }

    private void loadLastTournament() {
        soundManager.playClickSound();
        Tournament lastTournament = fileManager.loadLastTournament();

        if (lastTournament == null) {
            Toast.makeText(this, R.string.no_saved_tournament, Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_tournament_summary, null);
        builder.setView(dialogView);

        TextView tvWinner = dialogView.findViewById(R.id.tv_summary_winner);
        TextView tvScoreX = dialogView.findViewById(R.id.tv_summary_score_x);
        TextView tvScoreO = dialogView.findViewById(R.id.tv_summary_score_o);
        TextView tvDraws = dialogView.findViewById(R.id.tv_summary_draws);
        Button btnSave = dialogView.findViewById(R.id.btn_save_tournament);
        Button btnHome = dialogView.findViewById(R.id.btn_back_home);

        tvWinner.setText(getString(R.string.winner, lastTournament.getWinner()));
        tvScoreX.setText(getString(R.string.score_x, lastTournament.getScoreX()));
        tvScoreO.setText(getString(R.string.score_o, lastTournament.getScoreO()));
        tvDraws.setText(getString(R.string.draws, lastTournament.getDrawCount()));

        btnSave.setVisibility(View.GONE);
        btnHome.setText(R.string.ok);

        final AlertDialog dialog = builder.create();
        btnHome.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        soundManager.playBackgroundMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundManager.pauseBackgroundMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundManager.release();
        database.close();
    }
}
