package com.example.football.models.dto.json;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TeamSeedDTO {
    @Expose
    @NotNull
    @Size(min = 3)
    private String name;
    @Expose
    @NotNull
    @Size(min = 3)
    private String stadiumName;
    @Expose
    @NotNull
    @Min(1000)
    private int fanBase;
    @Expose
    @NotNull
    @Size(min = 10)
    private String history;
    @Expose
    private String townName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public int getFanBase() {
        return fanBase;
    }

    public void setFanBase(int fanBase) {
        this.fanBase = fanBase;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }
}
