package com.example.xo;

import android.os.Bundle;
import android.widget.ImageButton;
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

    private TextView tvWinStreak, tvLoseStreak;
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
        recyclerViewHistory = findViewById(R.id.recycler_view_history);
        ImageButton btnClose = findViewById(R.id.btn_close);

        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        btnClose.setOnClickListener(v -> finish());
    }

    private void loadStatistics() {
        PlayerStats stats = database.getPlayerStats();
        List<Tournament> tournaments = database.getAllTournaments();

        tvWinStreak.setText(String.valueOf(stats.getMaxWinStreak()));
        tvLoseStreak.setText(String.valueOf(stats.getMaxLoseStreak()));

        TournamentAdapters adapter = new TournamentAdapters(tournaments);
        recyclerViewHistory.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
