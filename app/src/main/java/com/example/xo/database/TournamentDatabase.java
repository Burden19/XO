package com.example.xo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.xo.models.PlayerStats;
import com.example.xo.models.Tournament;

import java.util.ArrayList;
import java.util.List;

public class TournamentDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "XOTournament.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TOURNAMENTS = "tournaments";
    private static final String TABLE_STATS = "player_stats";

    private static final String COL_ID = "id";
    private static final String COL_SCORE_X = "score_x";
    private static final String COL_SCORE_O = "score_o";
    private static final String COL_DRAWS = "draws";
    private static final String COL_TOTAL_GAMES = "total_games";
    private static final String COL_WINNER = "winner";
    private static final String COL_TIMESTAMP = "timestamp";

    public TournamentDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTournamentsTable = "CREATE TABLE " + TABLE_TOURNAMENTS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_SCORE_X + " INTEGER, " +
                COL_SCORE_O + " INTEGER, " +
                COL_DRAWS + " INTEGER, " +
                COL_TOTAL_GAMES + " INTEGER, " +
                COL_WINNER + " TEXT, " +
                COL_TIMESTAMP + " INTEGER)";
        db.execSQL(createTournamentsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOURNAMENTS);
        onCreate(db);
    }

    public long insertTournament(Tournament tournament) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_SCORE_X, tournament.getScoreX());
        values.put(COL_SCORE_O, tournament.getScoreO());
        values.put(COL_DRAWS, tournament.getDrawCount());
        values.put(COL_TOTAL_GAMES, tournament.getTotalGames());
        values.put(COL_WINNER, tournament.getWinner());
        values.put(COL_TIMESTAMP, tournament.getTimestamp());
        long id = db.insert(TABLE_TOURNAMENTS, null, values);
        updatePlayerStats(tournament);
        return id;
    }

    public List<Tournament> getAllTournaments() {
        List<Tournament> tournaments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TOURNAMENTS, null, null, null, null, null,
                COL_TIMESTAMP + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Tournament tournament = new Tournament(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_SCORE_X)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_SCORE_O)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_DRAWS)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_TOTAL_GAMES)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_WINNER))
                );
                tournament.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                tournament.setTimestamp(cursor.getLong(cursor.getColumnIndexOrThrow(COL_TIMESTAMP)));
                tournaments.add(tournament);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tournaments;
    }

    public PlayerStats getPlayerStats() {
        PlayerStats stats = new PlayerStats();
        List<Tournament> tournaments = getAllTournaments();

        int totalTournaments = tournaments.size();
        int won = 0, lost = 0;
        int currentWinStreak = 0, currentLoseStreak = 0;
        int maxWinStreak = 0, maxLoseStreak = 0;
        int tempWinStreak = 0, tempLoseStreak = 0;
        boolean first = true;

        for (Tournament tournament : tournaments) {
            String winner = tournament.getWinner();
            if (winner.equals("Player X")) {
                won++;
                tempWinStreak++;
                if (first) {
                    currentWinStreak = tempWinStreak;
                }
                tempLoseStreak = 0;
            } else if (winner.equals("Player O")) {
                lost++;
                tempLoseStreak++;
                if (first) {
                    currentLoseStreak = tempLoseStreak;
                }
                tempWinStreak = 0;
            } else {
                tempWinStreak = 0;
                tempLoseStreak = 0;
            }
            maxWinStreak = Math.max(maxWinStreak, tempWinStreak);
            maxLoseStreak = Math.max(maxLoseStreak, tempLoseStreak);
            first = false;
        }

        stats.setTotalTournaments(totalTournaments);
        stats.setTournamentsWon(won);
        stats.setTournamentsLost(lost);
        stats.setCurrentWinStreak(currentWinStreak);
        stats.setCurrentLoseStreak(currentLoseStreak);
        stats.setMaxWinStreak(maxWinStreak);
        stats.setMaxLoseStreak(maxLoseStreak);

        return stats;
    }

    private void updatePlayerStats(Tournament tournament) {
        // This is a placeholder for a more complex stats update
    }
}
