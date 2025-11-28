package com.example.xo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xo.adapters.TournamentAdapters;
import com.example.xo.database.TournamentDatabase;
import com.example.xo.models.PlayerStats;
import com.example.xo.models.Tournament;

import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    private TextView tvWinStreak, tvLoseStreak, tvWinPercentage, tvTotalTournaments;
    private RecyclerView recyclerViewHistory;
    private TournamentDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        initializeViews();
        database = new TournamentDatabase(this);
        loadStatistics();
    }

    private void initializeViews() {
        tvWinStreak = findViewById(R.id.tv_win_streak);
        tvLoseStreak = findViewById(R.id.tv_lose_streak);
        tvWinPercentage = findViewById(R.id.tv_win_percentage);
        tvTotalTournaments = findViewById(R.id.tv_total_tournaments);
        recyclerViewHistory = findViewById(R.id.recycler_view_history);

        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadStatistics() {
        PlayerStats stats = database.getPlayerStats();
        List<Tournament> tournaments = database.getAllTournaments();

        tvWinStreak.setText(getString(R.string.win_streak, stats.getMaxWinStreak()));
        tvLoseStreak.setText(getString(R.string.lose_streak, stats.getMaxLoseStreak()));

        if (stats.getTotalTournaments() > 0) {
            double winPercentage = (double) stats.getTournamentsWon() / stats.getTotalTournaments() * 100;
            tvWinPercentage.setText(getString(R.string.win_percentage, winPercentage));
        } else {
            tvWinPercentage.setText(getString(R.string.win_percentage, 0.0));
        }

        tvTotalTournaments.setText(getString(R.string.total_tournaments, stats.getTotalTournaments()));

        TournamentAdapters adapter = new TournamentAdapters(tournaments);
        recyclerViewHistory.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
