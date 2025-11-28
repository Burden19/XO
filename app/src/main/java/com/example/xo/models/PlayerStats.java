package com.example.xo.models;

public class PlayerStats {
    private int totalTournaments;
    private int tournamentsWon;
    private int tournamentsLost;
    private int currentWinStreak;
    private int currentLoseStreak;
    private int maxWinStreak;
    private int maxLoseStreak;

    public PlayerStats() {
        this.totalTournaments = 0;
        this.tournamentsWon = 0;
        this.tournamentsLost = 0;
        this.currentWinStreak = 0;
        this.currentLoseStreak = 0;
        this.maxWinStreak = 0;
        this.maxLoseStreak = 0;
    }

    public double getWinPercentage() {
        if (totalTournaments == 0) return 0.0;
        return (tournamentsWon * 100.0) / totalTournaments;
    }

    // Getters and Setters
    public int getTotalTournaments() { return totalTournaments; }
    public void setTotalTournaments(int totalTournaments) { this.totalTournaments = totalTournaments; }

    public int getTournamentsWon() { return tournamentsWon; }
    public void setTournamentsWon(int tournamentsWon) { this.tournamentsWon = tournamentsWon; }

    public int getTournamentsLost() { return tournamentsLost; }
    public void setTournamentsLost(int tournamentsLost) { this.tournamentsLost = tournamentsLost; }

    public int getCurrentWinStreak() { return currentWinStreak; }
    public void setCurrentWinStreak(int currentWinStreak) { this.currentWinStreak = currentWinStreak; }

    public int getCurrentLoseStreak() { return currentLoseStreak; }
    public void setCurrentLoseStreak(int currentLoseStreak) { this.currentLoseStreak = currentLoseStreak; }

    public int getMaxWinStreak() { return maxWinStreak; }
    public void setMaxWinStreak(int maxWinStreak) { this.maxWinStreak = maxWinStreak; }

    public int getMaxLoseStreak() { return maxLoseStreak; }
    public void setMaxLoseStreak(int maxLoseStreak) { this.maxLoseStreak = maxLoseStreak; }
}
