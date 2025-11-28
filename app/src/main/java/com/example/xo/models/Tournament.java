package com.example.xo.models;

import java.io.Serializable;

public class Tournament implements Serializable {
    private int id;
    private int scoreX;
    private int scoreO;
    private int drawCount;
    private int totalGames;
    private String winner;
    private long timestamp;

    public Tournament(int scoreX, int scoreO, int drawCount, int totalGames, String winner) {
        this.scoreX = scoreX;
        this.scoreO = scoreO;
        this.drawCount = drawCount;
        this.totalGames = totalGames;
        this.winner = winner;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getScoreX() { return scoreX; }
    public void setScoreX(int scoreX) { this.scoreX = scoreX; }

    public int getScoreO() { return scoreO; }
    public void setScoreO(int scoreO) { this.scoreO = scoreO; }

    public int getDrawCount() { return drawCount; }
    public void setDrawCount(int drawCount) { this.drawCount = drawCount; }

    public int getTotalGames() { return totalGames; }
    public void setTotalGames(int totalGames) { this.totalGames = totalGames; }

    public String getWinner() { return winner; }
    public void setWinner(String winner) { this.winner = winner; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}