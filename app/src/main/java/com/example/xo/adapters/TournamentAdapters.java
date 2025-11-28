package com.example.xo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xo.R;
import com.example.xo.models.Tournament;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TournamentAdapters extends RecyclerView.Adapter<TournamentAdapters.TournamentViewHolder> {

    private List<Tournament> tournaments;
    private SimpleDateFormat dateFormat;

    public TournamentAdapters(List<Tournament> tournaments) {
        this.tournaments = tournaments;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
    }

    @NonNull
    @Override
    public TournamentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tournament, parent, false);
        return new TournamentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TournamentViewHolder holder, int position) {
        Tournament tournament = tournaments.get(position);

        holder.tvDate.setText(dateFormat.format(new Date(tournament.getTimestamp())));
        holder.tvScores.setText(String.format("X: %d | O: %d | Draws: %d", tournament.getScoreX(), tournament.getScoreO(), tournament.getDrawCount()));
        holder.tvWinner.setText(String.format("Winner: %s", tournament.getWinner()));
        holder.tvGames.setText(String.format("Games: %d", tournament.getTotalGames()));
    }

    @Override
    public int getItemCount() {
        return tournaments.size();
    }

    static class TournamentViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvScores, tvWinner, tvGames;

        public TournamentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_tournament_date);
            tvScores = itemView.findViewById(R.id.tv_tournament_scores);
            tvWinner = itemView.findViewById(R.id.tv_tournament_winner);
            tvGames = itemView.findViewById(R.id.tv_tournament_games);
        }
    }
}
